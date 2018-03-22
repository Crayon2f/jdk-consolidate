package com.crayon2f.jdk.consolidate.base;

import org.junit.Test;

import java.io.*;

/**
 * Created by feiFan.gou on 2018/2/7 16:49.
 * 将代码写在 try里面 可以自动关闭资源
 * 专业术语 : try-with-resources statement
 * 前提：资源必须实现 java.lang.AutoCloseable
 */
public class TryWithResources {

    @Test
    public void test() {

        try (
                BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\feifan.gou\\Desktop\\path.txt"));
                BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\feifan.gou\\Desktop\\path_backup.txt"))
        ) {
            String temp;
            while (null != (temp = reader.readLine())) {
                writer.write(temp);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
