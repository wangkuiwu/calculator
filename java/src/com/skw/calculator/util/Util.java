package com.skw.calculator.util;

public class Util {

    // 调试开关
    private static boolean DEBUG=true;

    // 调试接口
    public static void logd(String str) {
        if (DEBUG) System.out.println(str);
    }

    // 出错打印接口
    public static void loge(String str) {
        System.out.println(str);
    }
}
