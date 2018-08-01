package com.crayon2f.jdk.consolidate.lombok;

import lombok.*;
import org.junit.jupiter.api.Test;

/**
 * Created by feiFan.gou on 2018/5/7 17:21.
 */
@ToString
@EqualsAndHashCode(of = {"id"})
//exclude :排除的字段; of: 包含的字段，exclude和of写一个就行;
//callSuper: 是否继承父类的 equals 和 hashCode，如果父类是直接使用的object的 equals 和 hashCode，则true没有意义；
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor
class LombokFromObject {

    @NonNull
    private Integer id;
    @NonNull
    private String name;

    @EqualsAndHashCode
    class Father {

        protected String parentName;

    }
    @EqualsAndHashCode(callSuper = false)
    @AllArgsConstructor
    @ToString
    private class Son extends Father{

        private String parentName;
    }

    @Test
    void test() {

        Son son = new Son("su");
        Son son2 = new Son("su");
        System.out.println(son);
        System.out.println(son.equals(son2));

    }
}
