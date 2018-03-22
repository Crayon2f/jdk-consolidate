package com.crayon2f.jdk.consolidate.pojo;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * Created by feiFan.gou on 2017/8/21 17:53.
 */
@Data
public class Article {

    private String title;

    private String author;

    private Integer count;

    private Double price = 0d;

    public static List<Article> data;

    private Boolean whetherPrivate = false;

    static {
        data = Lists.newArrayList(
                new Article("标题4", "作者1", 12),
                new Article("标题7", "作者4", 1),
                new Article("标题3", "作者5", 10),
                new Article("标题1", "作者3", 11, 3.4),
                new Article("标题10", "作者5", 3, 3d),
                new Article("标题6", "作者5", 4, 7d),
                new Article("标题2", "作者4", 7),
                new Article("标题8", "作者2", 5),
                new Article("标题9", "作者6", 2, 8.3),
                new Article("标题5", "作者6", 6),
                new Article("标题11", "作者11", 9),
                new Article("标题12", "作者12", 8)
//                ,
//                null
        );
    }

    public Article(String title, String author, Integer count) {
        this.title = title;
        this.author = author;
        this.count = count;
    }

    public Article(String title, String author, Integer count, Double price) {
        this.title = title;
        this.author = author;
        this.count = count;
        this.price = price;
    }

    public Article(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format("\r{title:%s,author:%s,count:%d,price:%.2f}\r\n",
                title, author, count, price);
    }

    public void read(Integer count) {

        System.out.println(String.format("this article count is %d", count));
    }

    public void read(String title) {

        System.out.println(String.format("this article title is %s", title));
    }
}
