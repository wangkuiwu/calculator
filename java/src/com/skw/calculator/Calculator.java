/**
 * 计算器入口
 *
 * @author skywang
 */
package com.skw.calculator;

import java.util.Scanner;
import com.skw.calculator.util.Util;

public class Calculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Util.logd("请输入算式(支持\"整数\"和\"小数\"的四则运算)：");
        // 读取运算符表达式
        String str = scanner.nextLine();

        // 创建"四则运算执行者"
        CalculatorExecutor executor = CalculatorExecutor.getInstance();
        try {
            // 对算式执行四则运算
            String ret = executor.execute(str);
            Util.logd("结果："+ret);
        } catch (UnparsableEquationException e) {
            e.printStackTrace();
        }
    }
}
