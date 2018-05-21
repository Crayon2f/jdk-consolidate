package com.crayon2f.jdk.consolidate.nio.demo.aio;

import com.crayon2f.jdk.consolidate.nio.demo.Base;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

/**
 * Created by feiFan.gou on 2018/5/18 16:06.
 */
public class Client extends Base implements CompletionHandler<Void, Client> {


    private String clientName;
    private CountDownLatch latch;
    private AsynchronousSocketChannel socketChannel;

    @SneakyThrows
    public Client(String clientName) {

        this.clientName = clientName;
        this.socketChannel = AsynchronousSocketChannel.open();

    }

    public void start() {

        new Thread(this::handle).start();
    }

    @SneakyThrows
    private void handle() {

        latch = new CountDownLatch(1);

        //发起异步连接操作，回调参数就是本身，如果成功，会钓鱼completed方法
        socketChannel.connect(new InetSocketAddress(host, port), this, this);
        latch.await();

        //await 以后，关闭资源
        if (socketChannel.isOpen()) {
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void completed(Void result, Client attachment) {

        print(this.clientName + " 客户端连接服务器成功");
    }

    @Override
    public void failed(Throwable exc, Client attachment) {

        print(this.clientName + " 客户端连接服务器失败");

        exc.printStackTrace();

        latch.countDown();
    }

    public void sendMessage(String message) {

        Optional.ofNullable(message).filter(ths -> !ths.isEmpty()).ifPresent(ths -> {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(ths.getBytes(Charset.defaultCharset()));
            buffer.flip();
            socketChannel.write(buffer, buffer, new WriteHandler());
        });
    }

    class WriteHandler implements CompletionHandler<Integer, ByteBuffer> {

        @Override
        public void completed(Integer result, ByteBuffer attachment) {

            //数据全部写入
            if (attachment.hasRemaining()) {
                Client.this.socketChannel.write(attachment, attachment, this);
            } else {
                //读取数据
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                Client.this.socketChannel.read(readBuffer, readBuffer, new ReadHandler());
            }
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {

            print("%s write fail", Client.this.clientName);
            Client.this.latch.countDown();
            if (Client.this.socketChannel.isOpen()) {
                try {
                    Client.this.socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {

        @Override
        public void completed(Integer result, ByteBuffer attachment) {

            attachment.flip();
            byte[] bytes = new byte[attachment.remaining()];
            attachment.get(bytes);
            print("%s receive message ==> %s", Client.this.clientName, new String(bytes, Charset.defaultCharset()));

        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {

            print("%s read fail", Client.this.clientName);
            Client.this.latch.countDown();
            if (Client.this.socketChannel.isOpen()) {
                try {
                    Client.this.socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) {

        Client client = new Client("client-two");
        client.start();
        for (; ; ) {
            client.sendMessage(new Scanner(System.in).nextLine());
        }
    }

}
