package com.crayon2f.jdk.consolidate.juc.lock;

import com.crayon2f.jdk.consolidate.sort.BaseSort;
import com.crayon2f.jdk.consolidate.sort.SelectSort;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReenterLock {

    public static void main(String[] args) throws InterruptedException {

        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        InnerClass innerClass = new InnerClass();
        innerClass.main();
        try {
            lock.lock();
//            condition.await();
            for (int i = 0; i < 1000; i++) {
                System.out.println("index ==> " + i);
            }
        } finally {
            lock.unlock();
        }
    }

    public static class InnerClass {

        public static void main() {

            System.out.println("this is a static class");

            BaseSort sort = new SelectSort();

            Runnable runnable = new Runnable() {
                final Semaphore semaphore = new Semaphore(5);

                @Override
                public void run() {

                    try {
                        semaphore.acquire();
                        Thread.sleep(2000);
                        System.out.println(Thread.currentThread().getId() + ":done !");
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            ExecutorService exec = Executors.newFixedThreadPool(20);
            for (int i = 0; i < 20; i++) {
                exec.submit(runnable);
            }
            exec.shutdown();
        }

        public static void main(String[] args) {

            main();
        }
    }
}
