package com.crayon2f.jdk.consolidate.nio.file;

import com.crayon2f.jdk.consolidate.nio.base.Base;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.util.Optional;
import java.util.Random;

/**
 * Created by feiFan.gou on 2018/5/10 19:41.
 */
class FileLockTest extends Base {

    private static final int SIZEOF_INT = 4;
    private static final int INDEX_START = 0;
    private static final int INDEX_COUNT = 10;
    private static final int INDEX_SIZE = INDEX_COUNT * SIZEOF_INT;
    private ByteBuffer buffer = ByteBuffer.allocate(INDEX_SIZE);
    private IntBuffer indexBuffer = buffer.asIntBuffer();
    private Random rand = new Random();
    private int lastLineLen = 0;
    private int idxval = 1;



    @Test
    @SneakyThrows
    void test() {

        RandomAccessFile file = new RandomAccessFile(file_path, "rw");
        FileChannel channel = file.getChannel();

//        doQueries(channel);
        Thread thread = new Thread(() -> doQueries(channel));
        thread.setName("doQueries");
//        Thread thread1 = new Thread(() -> doUpdates(channel));
//        thread.setName("doUpdates");

        thread.start();
//        thread1.start();
//
        thread.join();
    }


    @SneakyThrows
    private void doQueries(FileChannel channel) {

        while (true) {
            Optional.ofNullable(channel.tryLock(INDEX_START, INDEX_SIZE, true)).ifPresent(lock -> {
                try {
                    println("trying for shared lock...");
                    int reps = rand.nextInt(60) + 20;
                    for (int i = 0; i < reps; i++) {
                        int n = rand.nextInt(INDEX_COUNT);
                        int position = INDEX_START + (n * SIZEOF_INT);
                        buffer.clear();
                        channel.read(buffer, position);
                        int value = indexBuffer.get(n);
                        println("Index entry " + n + "=" + value);
                        // Pretend to be doing some work
                        Thread.sleep(100);
                    }
                    println("<sleeping>");
                    Thread.sleep(rand.nextInt(3000) + 500);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        lock.release();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @SneakyThrows
    void doUpdates(FileChannel channel) {

        while (true) {
            println("trying for exclusive lock...");
            Optional.ofNullable(channel.tryLock(INDEX_START, INDEX_SIZE, false)).ifPresent(lock -> {
                try {
                    updateIndex(channel);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        lock.release();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                println("<sleeping>");
                try {
                    Thread.sleep(rand.nextInt(2000) + 500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            Thread.sleep(2000);
        }
    }

    @SneakyThrows
    private void updateIndex(FileChannel channel) {

        indexBuffer.clear();
        for (int i = 0; i < INDEX_COUNT; i++) {
            idxval++;
            println("Updating index " + i + "=" + idxval);
            indexBuffer.put(idxval);
            Thread.sleep(5000);
        }
        buffer.clear();
        channel.write(buffer, INDEX_START);
    }

    private void println(String msg) {
        System.out.println("\r ");
        System.out.println(msg);
//        for (int i = msg.length(); i < lastLineLen; i++) {
//            System.out.print(" ");
//        }
//        System.out.print("\r");
        System.out.flush();
        lastLineLen = msg.length();
    }


    @Test
    @SneakyThrows
    void test2() {

        FileLockTest test = new FileLockTest();
        RandomAccessFile file = new RandomAccessFile(file_path, "rw");
        FileChannel channel = file.getChannel();
        test.doUpdates(channel);
    }
}
