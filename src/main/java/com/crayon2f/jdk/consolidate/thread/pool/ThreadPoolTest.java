package com.crayon2f.jdk.consolidate.thread.pool;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by feiFan.gou on 2018/5/8 17:05.
 */
class ThreadPoolTest {

    @Test
    @SneakyThrows
    void test() {

//        ExecutorService executor = Executors.newFixedThreadPool(2);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Runnable runnable = () -> {
            System.out.println("this is a new thread !!");
            System.out.println("===============");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        for (int i = 0; i < 3; i++) {
            executor.submit(runnable);
        }
        Thread.sleep(10 * 1000);

        executor.shutdown();

    }
}
