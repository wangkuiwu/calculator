package cn.skw.calculator;

import java.util.Scanner;
import cn.skw.calculator.util.Util;

/**
 * 计算器
 *
 * @desc Calculator类支持对运算表达式的计算。表达式中支持的数字、括号和运算符如下。
 *     1. 支持的数字： 0123456789+-.
 *     2. 支持的括号： ()
 *     3. 支持的的运算符
 *        (01) 双目运算符。支持的双目运算如下表格，
 *             ---------------------------------------
 *             |      运算名称    |      运算符      |
 *             ---------------------------------------
 *             |      加法        |        +         |
 *             |      减法        |        -         |
 *             |      乘法        |        *         |
 *             |      除法        |        /         |
 *             |      除法取余    |        %         |
 *             |      幂          |        ^         |
 *             ---------------------------------------
 *
 *        (02) 单目运算符。支持的单目运算如下表格，
 *             -------------------------------------------------------
 *             |      运算名称                    |      运算符      |
 *             -------------------------------------------------------
 *             |      正弦(sin)                   |        s         |
 *             |      余弦(cos)                   |        c         |
 *             |      正切(tan)                   |        t         |
 *             |      以常数(e)为底的对数(ln)     |        n         |
 *             |      以自然数(10)为底的对数(ln)  |        g         |
 *             |      绝对值(abs)                 |        a         |
 *             |      平方根(sqrt)                |        r         |
 *             -------------------------------------------------------
 *
 *        (03) 阶乘运算符。支持的阶乘运算如下表格，
 *             ----------------------------------------
 *             |      运算名称     |      运算符      |
 *             ----------------------------------------
 *             |      阶乘(!)      |        !         |
 *             ----------------------------------------
 *
 *
 *     4. 计算算式的调用示例
 *
 *           // 表达式
 *           String equation = "2+(-2)^3+8*(1-(-1))";
 *           // 计算表达式的结果
 *           // 计算成功的话，结果保存在result中；若表达式解析失败，则捕获异常UnparsableEquationException
 *           try {
 *               String result = Calculator.execute(equation);
 *           } catch (UnparsableEquationException e) {
 *               e.printStackTrace();
 *           }
 *
 *
 * @author skywang
 * @e-mail kuiwu-wang@163.com
 */
public class Calculator {

    /**
     * 计算等式equation，并返回结果
     *
     * @param equation 被计算的等式
     * @return 如果等式为null，则返回null；
     *         如果等式计算错误，则抛出UnparsableEquationException异常；
     *         如果等式计算成功，则返回结果。
     */
    public static String execute(String equation) throws UnparsableEquationException {
        if (equation==null) {
            return null;
        }

        String expr = equation.trim();
        String result = new Executor(expr).execute();

        Util.logd(equation+" = "+result);
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Util.logd("请输入算式(支持\"整数\"和\"小数\"的四则运算)：");
        // 读取运算符表达式
        String str = scanner.nextLine();
        try {
            // execute("2+(-2)^3+8*(1-(-1))");
            execute(str);
        } catch (UnparsableEquationException e) {
            e.printStackTrace();
        }
    }
}
