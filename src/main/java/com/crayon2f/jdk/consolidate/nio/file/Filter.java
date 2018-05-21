package com.crayon2f.jdk.consolidate.nio.file;

import java.io.File;
import java.io.FileFilter;
import java.util.Objects;

/**
 * Created by feiFan.gou on 2018/5/8 16:05.
 */
public class Filter {

    public static void main(String[] args) {

        File cwd = new File(System.getProperty("user.dir"));
        File[] htmlFiles = cwd.listFiles(new HTMLFileFilter());
        for (int i = 0, length = Objects.requireNonNull(htmlFiles).length; i < length; i++) {
            System.out.println(htmlFiles[i]);
        }
    }
}

class HTMLFileFilter implements FileFilter {

    public boolean accept(File pathname) {

        if (pathname.getName().endsWith(".html"))
            return true;
        return pathname.getName().endsWith(".htm");
    }
}