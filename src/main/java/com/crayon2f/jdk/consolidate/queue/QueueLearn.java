package com.crayon2f.jdk.consolidate.queue;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by feiFan.gou on 2018/1/6 17:39.
 */
class QueueLearn {

    Queue<Integer> queue = new ArrayDeque<Integer>(){{
        add(23);
        add(24);
        add(25);
        add(26);
    }};

    @Test
    void add() {

        System.out.println(queue.peek());
//        System.out.println(queue.poll());
        System.out.println(queue);
    }

    @Test
    void get() {

        //poll & peek
        System.out.println(queue.peek()); //单纯的拿到第一个
//        System.out.println(queue.poll()); // 删除第一个，并返回
//        for (int i = 0, length = queue.size(); i < length; i++) {
//            System.out.println(queue.poll());
//        }

        System.out.println(queue);

        System.out.println(queue.offer(56));
        System.out.println(queue);

//        var string = "";
//        System.out.println(string);
    }
}
