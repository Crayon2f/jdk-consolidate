package com.crayon2f.jdk.consolidate.nio.demo.nio.template;

public class Client {
    private static ClientHandle clientHandle;

    public static void start() {
        String default_host = "127.0.0.1";
        int default_port = 12345;
        start(default_host, default_port);
    }

    public static synchronized void start(String ip, int port) {
        if (clientHandle != null)
            clientHandle.stop();
        clientHandle = new ClientHandle(ip, port);
        new Thread(clientHandle, "Server").start();
    }

    //向服务器发送消息  
    public static boolean sendMsg(String msg) throws Exception {
        if (msg.equals("q")) return false;
        clientHandle.sendMsg(msg);
        return true;
    }

    public static void main(String[] args) {
        start();
    }
}  