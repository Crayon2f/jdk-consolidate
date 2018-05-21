package com.crayon2f.jdk.consolidate.nio.demo;

import com.crayon2f.jdk.consolidate.nio.demo.nio.Client;
import com.crayon2f.jdk.consolidate.nio.demo.nio.Server;
import lombok.SneakyThrows;

import java.util.Scanner;

/**
 * Created by feiFan.gou on 2018/5/17 18:07.
 */
public class Test {


    public static void main(String[] args) {

//        nio();
        aio();
    }

    @SneakyThrows
    private static void nio() {

        new Server().start();

        Thread.sleep(1000);

        Client client = new Client("client-one");
        client.start();

        for (; ; ) {
            client.sendMessage(new Scanner(System.in).nextLine());
        }
    }

    @SneakyThrows
    private static void aio() {

        new com.crayon2f.jdk.consolidate.nio.demo.aio.Server().start();

        Thread.sleep(1000);

        com.crayon2f.jdk.consolidate.nio.demo.aio.Client
                client = new com.crayon2f.jdk.consolidate.nio.demo.aio.Client("client-one");
        client.start();

        for (; ; ) {
            client.sendMessage(new Scanner(System.in).nextLine());
        }
    }

}
