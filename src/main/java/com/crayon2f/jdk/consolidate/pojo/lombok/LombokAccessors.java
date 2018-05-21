package com.crayon2f.jdk.consolidate.pojo.lombok;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.junit.Test;

/**
 * Created by feiFan.gou on 2018/5/8 10:56.
 * Accessors(加类上：一般和getter，setter配合使用) : 不配参数，不起作用；
 *      fluent=true：
 *                  public long mills() {
 *                      return this.mills;
 *                  }
 *
 *                  public LombokAccessors mills(long mills) {
 *                      this.mills = mills;
 *                      return this;
 *                  }
 * 加属性上 ：
 *  @ Accessors(prefix = "x") @Getter @Setter
 *     private String xName = "hello accessors";
 *      ==>
 *      ==>
 *      ==>
 *
 *      public String getName() {
 *         return this.xName;
 *     }
 *
 *     public void setName(String xName) {
 *         this.xName = xName;
 *     }
 *  chain = true：
 *
 *      public Inner setName(String xName) {
 *         this.xName = xName;
 *         return this;
 *     }
 *
 */
@Accessors(fluent = true)
public class LombokAccessors {

    @Getter
    @Setter
    private long mills;

    @Test
    public void test() {
        LombokAccessors accessors = new LombokAccessors();

        System.out.println(accessors);

        Inner inner = new Inner();
        System.out.println(inner);
    }
}

class Inner {
    @Accessors(prefix = "x", chain = true) @Getter @Setter
    private String xName = "hello accessors";
}
