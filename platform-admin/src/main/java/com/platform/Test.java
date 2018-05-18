package com.platform;

/**
 * 名称：Test <br>
 * 描述：<br>
 *
 * @author 李鹏军
 * @version 1.0
 * @since 1.0.0
 */
public class Test {
    public static void main(String[] args) {
        //Integer使用equals判断
        Integer a = 127;
        Integer b = 127;
        System.out.println(a == b);
        Integer c = 129;
        Integer d = 129;
        System.out.println(c == d);

        System.out.println(c.equals(d));

    }
}
