package com.crayon2f.jdk.consolidate.juc.container;

import lombok.SneakyThrows;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by feiFan.gou on 2018/5/18 14:50.
 */
public class SynchronousQueueTest {

    private SynchronousQueue<String> queue = new SynchronousQueue<>();
    private CountDownLatch latch = new CountDownLatch(1);

    @Test
    public void test() throws InterruptedException {

        new Thread(this::provide).start();
        new Thread(this::consumer).start();
        latch.await();


    }

    @SneakyThrows
    private void provide() {

        System.out.println("provider starting ");
        Thread.sleep(5000);
        queue.put("hi synchronous queue");
        System.out.println("provider ending ");

    }

    @SneakyThrows
    private void consumer() {

        System.out.println("consumer starting ");
        queue.take();
        System.out.println("consumer ending ");
        latch.countDown();
    }
}
