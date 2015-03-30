package cn.skw.calculator;

/**
 * 计算异常
 *
 * @author skywang
 * @e-mail kuiwu-wang@163.com
 */
public class UnparsableEquationException extends Exception {

    public UnparsableEquationException(String msg) {
        super(msg);
    }
}
