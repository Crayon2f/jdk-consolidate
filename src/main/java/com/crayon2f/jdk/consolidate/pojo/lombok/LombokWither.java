package com.crayon2f.jdk.consolidate.pojo.lombok;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Wither;

/**
 * Created by feiFan.gou on 2018/5/8 10:38.
 *
 * Wither xx ：返回该类的一个方法，如果xx相等，返回this，否则new一个
 *
 * protected LombokWither withJob(String job) {
 *         return this.job == job ? this : new LombokWither(job, this.mills);
 * }
 */

public class LombokWither {

    @Wither(AccessLevel.PROTECTED)
    private String job;
    @Getter @Wither
    private long mills;

    private LombokWither(String job, long mills) {
        this.job = job;
        this.mills = mills;
    }

    public static void main(String[] args) {
        LombokWither wither = new LombokWither("programmer", 12L);
    }
}
