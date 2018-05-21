package com.crayon2f.jdk.consolidate.juc.container;

import org.junit.Test;

import java.util.Map;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by feiFan.gou on 2018/5/17 11:12.
 */
public class ConcurrentSkipListMapTest {

    private ConcurrentSkipListMap<String, String> skipListMap = new ConcurrentSkipListMap<String, String>(){{
        put("a", "1");
        put("b", "w");
        put("d", "4");
        put("c", "6");
        put("f", "7");
    }};


    /**
     * 按序
     * 返回大于或等于(有等于的优先显示等于儿)key 的最小key/entry，也就是离key右边最近的一个key/entry，如果没有，就返回null,
     */
    @Test
    public void ceiling(){

        Map.Entry<String, String> entry = skipListMap.ceilingEntry("f");
//        System.out.printf("ceiling entry ==> key = %s, value = %s", entry.getKey(), entry.getValue());
        System.out.println();
        String ceilingValue = skipListMap.ceilingKey("f");
        System.out.printf("ceiling key ==> { key = %s, value = %s}", "f", ceilingValue);
        skipListMap.firstEntry();
    }

    /**
     * 小于
     */
    @Test
    public void lower() {

        Map.Entry<String, String> entry = skipListMap.lowerEntry("1");
        System.out.printf("lower entry ==> %s\r\n", entry);

        System.out.printf("c lower key ==> %s", skipListMap.lowerKey("c"));
    }

    /**
     * 小于等于
     */
    @Test
    public void floor() {

        Map.Entry<String, String> entry = skipListMap.floorEntry("b");
        System.out.printf("floor entry ==> %s\r\n", entry);

        System.out.printf("c floor key ==> %s", skipListMap.floorKey("c"));
    }

    /**
     * 大于
     */
    @Test
    public void higher() {

        Map.Entry<String, String> entry = skipListMap.higherEntry("b");
        System.out.printf("higher entry ==> %s\r\n", entry);

        System.out.printf("c higher key ==> %s", skipListMap.higherKey("c"));
    }

    /**
     * 第一个元素
     */
    @Test
    public void first() {

        Map.Entry<String, String> entry = skipListMap.firstEntry();
        System.out.printf("first entry ==> { key = %s, value = %s}", entry.getKey(), entry.getValue());

        System.out.println();

        String firstKey = skipListMap.firstKey();
        System.out.printf("first key ==> %s", firstKey);

    }

    /**
     * 最后一个
     */
    @Test
    public void last() {

        Map.Entry<String, String> entry = skipListMap.lastEntry();
        System.out.printf("last entry ==> { key = %s, value = %s}", entry.getKey(), entry.getValue());

        System.out.println();

        String lastKey = skipListMap.lastKey();
        System.out.printf("last key ==> %s", lastKey);
    }

    /**
     * 移除首个/最后一个，并将其返回；map为空时，不会报错
     */
    @Test
    public void poll() {

        Map.Entry<String, String> pollFirstEntry = skipListMap.pollFirstEntry();
        System.out.printf("poll first entry ==> %s", pollFirstEntry);

        System.out.println();

        Map.Entry<String, String> pollLastEntry = skipListMap.pollLastEntry();
        System.out.printf("poll last entry ==> %s", pollLastEntry);

        System.out.println();
        System.out.println(skipListMap);
    }

    /**
     * 翻转
     */
    @Test
    public void descending() {

        ConcurrentNavigableMap<String, String> navigableMap = skipListMap.descendingMap();
        System.out.println(navigableMap);
        System.out.println(skipListMap.descendingKeySet());
    }

    /**
     * 截取到x 为止的map，默认不包含x，如果inclusive = true，则包含
     * x不存在时，最前：{}， 最后：{all}， 中间：{min - x}（inclusive 填不填结果一致）
     */
    @Test
    public void head() {

        ConcurrentNavigableMap<String, String> headMap = skipListMap.headMap("e");
        System.out.println(headMap);

        ConcurrentNavigableMap<String, String> headMapInclusive = skipListMap.headMap("e", true);
        System.out.println(headMapInclusive);
    }

    /**
     * 截取 位置控制很友好
     */
    @Test
    public void sub() {

        ConcurrentNavigableMap<String, String> subMap = skipListMap.subMap("a", "e");
        System.out.println(subMap);
        ConcurrentNavigableMap<String, String> map = skipListMap.subMap("a", false, "c", true);
        System.out.println(map);
    }

    /**
     * 尾部,用法同headMap
     */
    @Test
    public void tail() {

        ConcurrentNavigableMap<String, String> tailMap = skipListMap.tailMap("e");
        System.out.println(tailMap);
    }

    @Test
    public void navigable() {

        NavigableSet<String> navigableSet = skipListMap.navigableKeySet();
        System.out.println(navigableSet);

    }

}
