package com.crayon2f.jdk.consolidate.thread;

import org.junit.jupiter.api.Test;

/**
 * Created by feifan.gou@gmail.com on 2018/7/24 17:51.
 */
class ThreadLocalTest {

    private ThreadLocal<String> local = new ThreadLocal<>();

    @Test
    void test() throws InterruptedException {

        Runnable runnable = () -> {
            local.set("123");
            try {
                Thread.sleep(5*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(local.get());
//            try {
//                Thread.sleep(5*1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        Runnable runnable1 = () -> {
            local.set("1232");
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(local.get());
//            thread.stop();
        };

        new Thread(runnable1).start();

        Thread.sleep(10 * 1000);
        System.out.println(local.get());
    }
}
