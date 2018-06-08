package com.crayon2f.jdk.consolidate.lombok;

import com.crayon2f.jdk.consolidate.pojo.lombok.*;
import lombok.Generated;
import lombok.SneakyThrows;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;

import java.io.FileInputStream;
import java.time.LocalDate;

/**
 * Created by feiFan.gou on 2018/5/7 17:02.
 */
//@Slf4j
public class LombokTest {

    @Test
    public void test() {

        LombokConstructor constructor = LombokConstructor.of("1", 3);
        System.out.println(constructor);
    }

    @Test
    public void val() {

        val string = "99"; // this is a final variables
        System.out.println(string);
    }

    @Test
    public void equalsAndHashCode() {

        LombokFromObject object = LombokFromObject.of(12, "6");
        LombokFromObject object2 = LombokFromObject.of(12, "4");

        System.out.println(object.equals(object2));

    }

    @Test
    public void value() {

        LombokValue value = LombokValue.of("test", 45);
        System.out.printf("value = %s", value);

    }

    @Test
    public void builder() {

        LombokBuilder builder = LombokBuilder.builder().date(LocalDate.now()).string("string").build();
        System.out.printf("builder => %s", builder);
    }

    @Test
    @SneakyThrows
    public void sneakyThrows() {

        FileInputStream fis = new FileInputStream(""); // 此处应该throw IOException 的，但是@SneakyThrows 处理了

        System.out.println(fis);
    }

    @Synchronized
    @Test
    public void synchronizedMethod() {

        System.out.println("this is a synchronized method "); //编译如下

        // 全局变量 private final Object $lock = new Object[0];

        /*
         *  Object var1 = this.$lock;
            synchronized(this.$lock) {
                System.out.println("this is a synchronized method ");
            }
         */
    }

    @Test
    @Generated
    public void getLazy() {

        LombokGetLazy lazy = new LombokGetLazy();
        System.out.println(lazy.getId());
        System.out.println(lazy.getName());
    }

}
