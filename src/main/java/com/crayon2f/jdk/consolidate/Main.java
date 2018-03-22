package com.crayon2f.jdk.consolidate;

import com.crayon2f.jdk.consolidate.pojo.Article;
import com.crayon2f.jdk.consolidate.pojo.FinalClass;
import org.junit.Test;

import java.nio.file.Path;
import java.util.Arrays;

/**
 * Created by feiFan.gou on 2018/1/6 17:38.
 */
public class Main {

    @Test
    public void main() {

        System.out.println("addSDb".matches("[a-zA-Z]+"));
        System.out.println("我的世界".matches("[\u4E00-\u9FA5]+"));
        System.out.println("点多de#".matches("[a-zA-Z\u4E00-\u9FA5]+"));
        Article.data.forEach(article -> {
            System.out.println("======");
            System.out.println(article.getAuthor());
            System.out.println(matches(article.getTitle()));
        });
    }

    public static void main(String[] args) {
        String s = "00";
        System.out.println(s.hashCode());
        s = "34";
        System.out.println(s.hashCode());
        System.out.println(s);
    }

    private boolean matches(String title) {

        //jooq
        return title.matches("[a-zA-Z]+");
    }
}
