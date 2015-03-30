package cn.skw.calculator.unit;

import java.util.List;
import cn.skw.calculator.UnparsableEquationException;

/**
 * 数字类的运算单元
 *
 * 只包含小数
 *
 * @author skywang
 * @e-mail kuiwu-wang@163.com
 */
public class NumberUnit extends Unit {

    private double dval;

    /**
     * 小数的构造函数
     *
     * @param value
     *   小数对应的字符串
     * @param mBigInteger
     *   小数对应的double值
     */
    public NumberUnit(String value, double dval) {
        super(value);
        this.dval  = dval;
    }

    /**
     * 获取小数对应的double值
     *
     * @return
     *   value对应的double值
     */
    public double getDouble() {
        return dval;
    }

    @Override
    public String toString() {
        return "[\""+value+"\", "+dval+"]";
    }

    /**
     * 检查小数的有效性
     *
     * @param list
     *   算式被解析之后得到的列表
     * @param position
     *   该运算符在list中所在的位置
     * @throws UnparsableEquationException
     */
    public void checkValidation(List<Unit> list, int position) 
        throws UnparsableEquationException {

        // 前面必须是"双目运算符"、"单目运算符" 或 "("
        if (position>0) {
            Unit prev = list.get(position-1);
            if (!(prev instanceof BinaryUnit || prev instanceof UnaryUnit || prev instanceof LParenthesisUnit )) {
                throw new UnparsableEquationException("invalidate number("+value+")'s previous symbol["+prev.getValue()+"]");
            }
        }
    }
}
