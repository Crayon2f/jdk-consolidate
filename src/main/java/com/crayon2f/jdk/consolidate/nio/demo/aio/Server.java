package com.crayon2f.jdk.consolidate.nio.demo.aio;

import com.crayon2f.jdk.consolidate.nio.demo.Base;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;

/**
 * Created by feiFan.gou on 2018/5/18 11:11.
 */
public class Server extends Base {

    private CountDownLatch latch;
    private AsynchronousServerSocketChannel serverSocketChannel;
    private AsynchronousSocketChannel socketChannel;

    private volatile static long client_count = 0;

    @SneakyThrows
    public Server() {

        //创建服务器通道
        serverSocketChannel = AsynchronousServerSocketChannel.open();
        //绑定端口
        serverSocketChannel.bind(new InetSocketAddress(port));
        System.out.println("服务器已打开，端口号：" + port);
    }

    public void start() {

        new Thread(this::handle).start();
    }

    @SneakyThrows
    private void handle() {

        //CountDownLatch 初始化
        //作用：允许在完成一组正在执行的操作之前，允许当前线程一直堵塞
        //此处让线程阻塞，防止服务端执行完以后退出，也可以使用while（true） + sleep
        //生产环境不需要考虑这个问题，因为服务是不会退出的
        latch = new CountDownLatch(1);
        {
            serverSocketChannel.accept(this, new AcceptHandle());
        }

        latch.await();

        if (socketChannel.isOpen()) {
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    class AcceptHandle implements CompletionHandler<AsynchronousSocketChannel, Server> {


        @Override
        public void completed(AsynchronousSocketChannel channel, Server server) {

            socketChannel = channel;
            //继续接受其他client
            client_count++;
            print("客户端连接数 ：" + client_count);
            serverSocketChannel.accept(server, this);
            //创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //异步读
            channel.read(buffer, buffer, new ReadHandler());
        }

        @Override
        public void failed(Throwable exc, Server attachment) {

            print("server accept fail");
            exc.printStackTrace();
            latch.countDown();
            if (serverSocketChannel.isOpen()) {
                try {
                    serverSocketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {

        @Override
        public void completed(Integer result, ByteBuffer attachment) {

            //flip
            attachment.flip();
            byte[] message = new byte[attachment.remaining()];
            attachment.get(message);
            print("aio server receive message ==> " + new String(message, Charset.defaultCharset()));

            //向客户端发送消息
            String greeting = "Hi, I'm received message, Thank you";

            ByteBuffer responseBuffer = ByteBuffer.allocate(100);
            responseBuffer.put(greeting.getBytes(Charset.defaultCharset()));
            responseBuffer.flip();
            Server.this.socketChannel.write(responseBuffer, responseBuffer, new WriteHandler());
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {

            print("server read fail");
            exc.printStackTrace();
            latch.countDown();
            if (serverSocketChannel.isOpen()) {
                try {
                    serverSocketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    class WriteHandler implements CompletionHandler<Integer, ByteBuffer> {

        @Override
        public void completed(Integer result, ByteBuffer attachment) {

            //如果没写完，continue
            if (attachment.hasRemaining()) {
                Server.this.socketChannel.write(attachment, attachment, this);
            } else {
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                Server.this.socketChannel.read(readBuffer, readBuffer, new ReadHandler());
            }
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {

            print("server write fail");
            exc.printStackTrace();
            Server.this.latch.countDown();
            if (Server.this.socketChannel.isOpen()) {
                try {
                    Server.this.socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


}
