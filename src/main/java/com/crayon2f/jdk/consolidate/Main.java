package com.crayon2f.jdk.consolidate;

import com.crayon2f.common.pojo.Article;
import com.crayon2f.jdk.consolidate.annotations.Of;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import javax.script.ScriptException;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Created by feiFan.gou on 2018/1/6 17:38.
 */
class Main {

    @Test
    void main() {

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
//        String s = "00";
//        System.out.println(s.hashCode());
//        s = "34";
//        System.out.println(s.hashCode());
//        System.out.println(s);
//        String file = "D:\\download\\video\\VUE.mp4";
//        String typeFromName = URLConnection.guessContentTypeFromName(file);
//        System.out.println(typeFromName);
        System.out.println("Default Charset=" + Charset.defaultCharset());
        System.out.println("file.encoding=" + System.getProperty("file.encoding"));
        System.out.println("Default Charset=" + Charset.defaultCharset());
        System.out.println("Default Charset in Use=" + getDefaultCharSet());
    }

    private static String getDefaultCharSet() {
        OutputStreamWriter writer = new OutputStreamWriter(new ByteArrayOutputStream());
        return writer.getEncoding();
    }

    private boolean matches(String title) {

        //jooq
        return title.matches("[a-zA-Z]+");
    }

    private String source = "C:\\Users\\Lenovo\\Desktop\\temp.pdf";

    private String target = "C:\\Users\\Lenovo\\Desktop\\temp2.pdf";

    @Test
    @SneakyThrows
    void test() {

        Path path = Paths.get(source);
        @Cleanup
        FileChannel channel = FileChannel.open(path, StandardOpenOption.READ, StandardOpenOption.WRITE);
        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, channel.size());
        @Cleanup
        FileChannel outChannel = new FileOutputStream(target).getChannel();
        outChannel.write(buffer);

    }

    @Test
    @SneakyThrows
    void oldCopy() {

        Files.copy(Paths.get(source), new FileOutputStream(target));
    }

    @Test
    void test2() throws ScriptException {

        ConcurrentSkipListSet<String> set = new ConcurrentSkipListSet<>();
//        Set<String> set = Sets.newConcurrentHashSet();
        set.add("123");
        set.add("124");
        set.add("125");
        set.add("163");

        set.forEach(item -> {
            if (item.equals("124")) {
                set.remove(item);
            }
        });
        Optional.of(set.iterator()).ifPresent(ths -> ths.forEachRemaining(item -> {
            if (item.equals("124")) {
                ths.remove();
            }
        }));
        System.out.println(set);


        System.out.println(1 << 10);

        System.out.println((1 << 20) * (5 << 2));
    }


    @Test
    void test3() {

        System.out.println(ZoneId.systemDefault());

        Function<String, Integer> fn = Integer::parseInt;

        Map<String, Integer> map = Maps.asMap(Sets.newHashSet("iii"), value -> 3);

        System.out.println(map);
    }


    @Test
    void test4() {

        List<Integer> list = new ArrayList<>();
        IntStream.range(0,20).parallel().forEach(i -> {
            list.add(i);
            System.out.println("thread-"+Thread.currentThread().getId());
            System.out.println(i);
            System.out.println("================");
        });

        System.out.println(list);
    }

    void test5() {

        App app = new App();
    }

    @Of
    private class App {

    }
}
