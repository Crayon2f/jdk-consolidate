package com.crayon2f.jdk.consolidate.nio.demo.nio;

import com.crayon2f.jdk.consolidate.nio.demo.Base;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Optional;

/**
 * Created by feiFan.gou on 2018/5/16 20:32.
 */
public class Server extends Base {

    private ServerSocketChannel serverSocketChannel;
    private volatile boolean started;
    private Selector selector;

    public static void main(String[] args) {

        Server server = new Server();
        server.start();
    }

    public Server() {

        handle = this::receive;
    }

    public void start() {

        new Thread(this::handle, "server-handler").start();
    }

    public void stop() {

        started = false;
    }

    private void handle() {

        try {
            //创建选择器
            selector = Selector.open();
            //打开监听通道
            serverSocketChannel = ServerSocketChannel.open();
            //设置为非阻塞
            serverSocketChannel.configureBlocking(false);
            //绑定端口 backlog = 1024
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
            //监听客户端请求
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            //标记服务器开启
            started = true;
            System.out.println("服务器已经开启，端口为：" + port);
            while (started) {
                polling(selector);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void receive(SelectionKey key) {

        Optional.ofNullable(key).ifPresent(ths -> {
            try {
                if (ths.isValid()) {
                    //处理新接入的请求信息
                    if (ths.isAcceptable()) {
                        // 通过accept 建立SocketChannel实例 完成该操作，意味着三次握手完成，TCP物理链路正式建立
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        //注册为读
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }
                    //读消息
                    if (ths.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) ths.channel();
                        //开辟一个1M的缓冲区
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        //读取请求码流，返回读取的字节数
                        int read = socketChannel.read(buffer);
                        if (read > 0) {
                            //flip 翻转
                            buffer.flip();
                            byte[] bytes = new byte[buffer.remaining()];
                            buffer.get(bytes);
                            System.out.println("server receives message ==> " + new String(bytes, Charset.defaultCharset()));
                            //发送应答消息
                            ByteBuffer responseBuffer = ByteBuffer.allocate(100);
                            responseBuffer.put("server has received message !".getBytes(Charset.defaultCharset()));
                            responseBuffer.flip();
                            socketChannel.write(responseBuffer);
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
}
