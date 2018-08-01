package com.crayon2f.jdk.consolidate.nio.demo.nio.template;

class Server {
    private static ServerHandle serverHandle;

    public static void start() {
        start(12345);
    }

    public static synchronized void start(int port) {
        if (serverHandle != null)
            serverHandle.stop();
        serverHandle = new ServerHandle(port);
        new Thread(serverHandle, "Server").start();
    }

    public static void main(String[] args) {
        start();
    }
}  