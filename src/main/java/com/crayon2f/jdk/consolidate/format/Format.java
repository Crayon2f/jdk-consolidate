package com.crayon2f.jdk.consolidate.format;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

public class Format {
    private double f = 111231.5585;

    private void m1() {
        BigDecimal bg = new BigDecimal(f);
        double formatFloat = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(formatFloat);
    }

    /**
     * DecimalFormat转换最简便
     */
    private void m2() {
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println(df.format(f));
    }

    /**
     * String.format打印最简便
     */
    private void m3() {
        System.out.println(String.format("%.2f", f));
    }

    private void m4() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        System.out.println(nf.format(f));
    }

    public static void main(String[] args) {
        Format f = new Format();
        f.m1();
        f.m2();
        f.m3();
        f.m4();

        System.out.println(DateFormat.getDateInstance().format(new Date()));
    }
}