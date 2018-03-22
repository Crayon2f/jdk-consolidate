package com.crayon2f.jdk.consolidate.nio.file;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by feiFan.gou on 2018/2/7 17:59.
 */
public class PathTest {

    @Test
    public void test() throws IOException {
//        Path path = Paths.get("C:\\Users\\feifan.gou\\Desktop\\path.txt");
//        System.out.println(path.getFileName());
//        System.out.println(path.getParent());
//        System.out.println(path.getRoot());
//        System.out.println(path.getFileSystem());
        List<String> strings = new ArrayList<>();
        Path path = Paths.get("d:/increase_copper_cache.backup");
//        Files.createFile(path);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            strings.add("******09098098dlkfaj-----------" + i);
            stringBuilder.append(strings.get(i));
        }
        System.out.println(Files.exists(path));
        try {
            Files.write(path, stringBuilder.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {

        System.out.println(new Date(1520408008204L));
        System.out.println(Instant.ofEpochMilli(1520408008204L));

    }
}
