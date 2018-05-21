package com.crayon2f.jdk.consolidate.nio.demo.nio;

import com.crayon2f.jdk.consolidate.nio.demo.Base;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.Scanner;

/**
 * Created by feiFan.gou on 2018/5/17 16:02.
 */
public class Client extends Base {

    private volatile boolean started;

    private String clientName;
    private SocketChannel socketChannel;
    private Selector selector;

    public Client(String clientName) {

        this.clientName = clientName;
        handle = this::receive;

        try {
            //创建选择器
            selector = Selector.open();
            //打开监听通道
            socketChannel = SocketChannel.open();
            //非阻塞
            socketChannel.configureBlocking(false);
//            socketChannel.register(selector, SelectionKey.OP_READ);
            started = true;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void start() {

        new Thread(this::handle, clientName).start();
    }

    public void stop() {

        started = false;
    }


    private void handle() {

        try {
            if (socketChannel.connect(new InetSocketAddress(host, port)));
            else {
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (started) {
            polling(selector);
        }
    }

    private void receive(SelectionKey key) {

        Optional.ofNullable(key).ifPresent(ths -> {
            try {
                if (ths.isValid()) {
                    SocketChannel socketChannel = (SocketChannel) ths.channel();
                    if (ths.isConnectable()) {
                        //保证正常连接
                        if (!socketChannel.finishConnect()) {
                            System.exit(1);
                        }
                    }
                    //读消息
                    if (ths.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int read = socketChannel.read(buffer);
                        if (read > 0) {
                            buffer.flip();
                            byte[] bytes = new byte[buffer.remaining()];
                            buffer.get(bytes);
                            System.out.println(clientName +" receive message ==> " + new String(bytes, Charset.defaultCharset()));
                        } else if (read < 0) {
                            ths.cancel();
                            socketChannel.close();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Optional.of(key).ifPresent(SelectionKey::cancel);
            }
        });
    }

    @SneakyThrows
    public void sendMessage(String message) {

        socketChannel.register(selector, SelectionKey.OP_READ);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(message.getBytes(Charset.defaultCharset()));
        buffer.flip();
        socketChannel.write(buffer);
    }

    public static void main(String[] args) {

        Client client = new Client("client-two");
        client.start();

        for (; ; ) {
            client.sendMessage(new Scanner(System.in).nextLine());
        }
    }

}
