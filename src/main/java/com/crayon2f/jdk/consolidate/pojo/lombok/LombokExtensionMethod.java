package com.crayon2f.jdk.consolidate.pojo.lombok;

import lombok.experimental.ExtensionMethod;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by feiFan.gou on 2018/5/7 20:55.
 */
@ExtensionMethod(java.util.Arrays.class)
public class LombokExtensionMethod {

    @Test
    public void test() {

        int[] arr = {3, 5, 2, 5};
//        arr.sort(); //idea 插件不支持，ε=(´ο｀*)))唉

        System.out.println(Arrays.toString(arr));
    }
}
