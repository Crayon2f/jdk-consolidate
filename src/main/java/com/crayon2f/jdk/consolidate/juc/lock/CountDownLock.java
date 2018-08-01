package com.crayon2f.jdk.consolidate.juc.lock;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 倒计时器
 */
class CountDownLock implements Runnable {

    static final CountDownLatch latch = new CountDownLatch(10);
    static final CountDownLock lock = new CountDownLock();


    @Override
    public void run() {

        try {
            //模拟检查任务
            Thread.sleep(new Random().nextInt(10) * 1000);
            System.out.println(Thread.currentThread().getName() + " ==> check complete !");
            latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {

//        ExecutorService exec = Executors.newFixedThreadPool(10);
//        for (int i = 0; i < 10; i++) {
//            exec.submit(lock);
//        }
//        latch.await(1,TimeUnit.MILLISECONDS);
//        System.out.println("Fire !!");
//        exec.shutdown();

//        CountDownLatch latch = new CountDownLatch(1);
//        System.out.println("123");
//        latch.await(2, TimeUnit.SECONDS);
//        System.out.println("finished !!");

    }
}
