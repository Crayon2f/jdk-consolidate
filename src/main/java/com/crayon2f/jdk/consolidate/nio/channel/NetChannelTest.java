package com.crayon2f.jdk.consolidate.nio.channel;

import com.crayon2f.jdk.consolidate.nio.base.Base;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Created by feiFan.gou on 2018/5/11 20:51.
 */
class NetChannelTest extends Base {

    private static final String greeting = "hello channel !";

    @Test
    @SneakyThrows
    void serverSocketChannel() {

        int port = 1234;

        ByteBuffer buffer = ByteBuffer.wrap(greeting.getBytes());
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.socket().bind(new InetSocketAddress(port));
        channel.configureBlocking(false);
        while (true) {
            System.out.println("connecting process");
            Optional.ofNullable(channel.accept()).map(ths -> {
                try {
                    System.out.printf("Incoming connection from ==> %s", ths.getRemoteAddress());
                    buffer.rewind();
                    ths.write(buffer);
                    ths.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return ths;
            }).orElseGet(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            });
        }
    }

    @Test
    @SneakyThrows
    void socketChannel() {

        try (
                SocketChannel channel = SocketChannel.open(new InetSocketAddress("148.251.188.73", 80));
                FileChannel fileChannel = FileChannel.open(Paths.get(empty_file_path))
        ) {
            channel.configureBlocking(false);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (channel.read(buffer) > -1) {

            }
            fileChannel.write(buffer);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    @SneakyThrows
    void accept() {

        int port = 1234;
        String greeting = "Hello I must be going.\r\n";
        ByteBuffer buffer = ByteBuffer.wrap(greeting.getBytes(Charset.defaultCharset()));
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.socket().bind(new InetSocketAddress(port));
        socketChannel.configureBlocking(false);
        while (true) {
            System.out.println("Waiting for connections");
            Optional.ofNullable(socketChannel.accept()).map(ths -> {
                System.out.println("Incoming connection from :" + ths.socket().getRemoteSocketAddress());
                buffer.rewind();
                try {
                    ths.write(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                try {
//                    ths.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                return ths;
            }).orElseGet(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            });
        }
    }

    @Test
    @SneakyThrows
    void newSocket() {


        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    int port = 80;
                    SocketChannel channel = SocketChannel.open(new InetSocketAddress("60.10.117.125", port));
                    channel.configureBlocking(false);
                    while (!channel.finishConnect()) {
                        System.out.println("connection pending =====");
                    }
                    ByteBuffer buffer = ByteBuffer.allocate(100);
                    channel.read(buffer);
                    System.out.println(new String(buffer.array()));
                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
        thread.join();
    }
}
