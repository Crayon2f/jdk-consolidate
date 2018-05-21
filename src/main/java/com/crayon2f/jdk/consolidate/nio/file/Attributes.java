package com.crayon2f.jdk.consolidate.nio.file;

import com.crayon2f.jdk.consolidate.nio.base.Base;
import lombok.SneakyThrows;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by feiFan.gou on 2018/5/8 15:32.
 */
public class Attributes extends Base {

    @Test
    @SneakyThrows
    public void attributes() {

        Path path = Paths.get(file_path);
//        Path path = Paths.get(dir_path);
        BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
        System.out.printf("file creation time is ==> %s", attributes.creationTime()); //创建时间
        System.out.println();
        System.out.printf("file key is ==> %s", attributes.fileKey());
        System.out.println();
        System.out.printf("Is directory ? %s", attributes.isDirectory());
        System.out.println();
        System.out.printf("Is other ? %s", attributes.isOther());
        System.out.println();
        System.out.printf("file size ==> %dB", attributes.size()); // 单位：byte B
        System.out.println();
        System.out.printf("Is regular file ? %s", attributes.isRegularFile());
        System.out.println();
        System.out.printf("isSymbolicLink ? %s", attributes.isSymbolicLink()); //是否是软连接
        System.out.println();
        System.out.println("============================================================");

        new View().print();

    }

    class View {

        public void print() {

            Path path = Paths.get(file_path);
            BasicFileAttributeView view = Files.getFileAttributeView(path, BasicFileAttributeView.class);
            System.out.printf("file view name ==> %s",view.name());
        }
    }
}
