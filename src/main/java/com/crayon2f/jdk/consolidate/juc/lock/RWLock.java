package com.crayon2f.jdk.consolidate.juc.lock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 */
class RWLock {

    private static Lock lock = new ReentrantLock();
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static Lock readLock = readWriteLock.readLock();
    private static Lock writeLock = readWriteLock.writeLock();
    private int value;

    public Object handleRead(Lock lock) throws InterruptedException {
        try {
            lock.lock();
            Thread.sleep(1000);
            System.out.println(value);
            return value;
        } finally {
            lock.unlock();
        }
    }

    void handleWrite(Lock lock, int index) throws InterruptedException {

        try {
            lock.lock();
            Thread.sleep(1000);
            value = index;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        final RWLock rwLock = new RWLock();
        Runnable readRunnable = () -> {
            try {
                rwLock.handleRead(readLock);
//                    lock.handleRead(lock);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable writeRunnable = () -> {
            try {
                rwLock.handleWrite(writeLock, new Random().nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        };

        for (int i = 0; i < 18; i++) {
            new Thread(readRunnable).start();
        }

        for (int i = 18; i < 20; i++) {
            new Thread(writeRunnable).start();
        }
//
//        Thread.sleep(5000);
//        System.out.println(rwLock.value);
    }

}
