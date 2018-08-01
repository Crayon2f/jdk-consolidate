package com.crayon2f.jdk.consolidate.sort;

import org.junit.jupiter.api.Test;

import javax.security.auth.Subject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

class ShellSort extends BaseSort {

    @Test
    void test() {

        int aa = 127;
        int bb = 256;
        Integer a = 127;
        Integer b = 127;
        Integer c = 256;
        Integer d = 256;
        Integer cc = new Integer(256);
        System.out.println(c.hashCode());
        System.out.println(cc.hashCode());
        System.out.println(System.identityHashCode(c));
        System.out.println(System.identityHashCode(cc));
        System.out.println(System.identityHashCode(bb));

        System.out.println(System.identityHashCode(a));
        System.out.println(System.identityHashCode(aa));
        System.out.println(System.identityHashCode(b));
        System.out.println(a == b);
        System.out.println(c == d);
        System.out.println(aa == a);
        System.out.println(bb == c);
        System.out.println(bb == cc);
        System.out.println(cc == c);

//        Subject subject =
    }

    @Override
    protected void sort(int[] array) {


    }

    @Test
    void test2() {

        Map<String, String> map = new HashMap<>();

        map.put("1", "a3");
        map.put("2", "a2");
        map.put("3", "a1");
        map.put("3", "a4");

        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            System.out.println(stringStringEntry.getKey());
            System.out.println(stringStringEntry.getValue());
        }


    }

    public static void main(String[] args) {

        final Object lock1 = new Object(), lock2 = new Object();

        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {

                synchronized (lock1) {

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (lock2) {
                        System.out.println("runnable1 get lock2");
                    }
                }
            }
        };

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {

                synchronized (lock2) {

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (lock1) {
                        System.out.println("runnable2 get lock1");
                    }
                }
            }
        };

        new Thread(runnable1).start();
        new Thread(runnable2).start();
    }
    @Test
    void tt() {

    }
}
