/**
 * 单目运算符
 *
 * 例如，sin/cos/tan/ln/log/abs/sqrt等，都是单目运算符
 *
 * @author skywang
 */
package com.skw.calculator.unit;

import java.util.List;
import com.skw.calculator.UnparsableEquationException;
import com.skw.calculator.operation.Operation;

public class UnaryUnit extends OperatorUnit {

    /**
     * 单目运算符的构造函数
     *
     * @param value
     *   单目运算符对应的符号。各个单目运算符对应的value如下表：
     *
     *      名称    |  value  | 说明
     * (01) 平凡根     r        sqareroot中的r
     * (02) 绝对值     a        absolute的首字母a
     * (03) 正弦       s        sin中的首字母s
     * (04) 余弦       c        cos中的首字母c
     * (05) 正切       t        tan中的首字母t
     * (06) ln         n
     * (07) log        g
     *
     * @param operation
     *   单目运算符对应的操作方法。该操作方法就是用来执行该操作的。
     */
    public UnaryUnit(String value, Operation operation) {
        super(value, operation);
    }

    /**
     * 检查该"单目运算符"是否有效
     * 
     * 单目运算符必须满足以下基本要求：
     *  (01) 可以位于"开头",不能位于"结尾"
     *  (02) 前面是"双目运算符" 或 "("
     *
     * @param list
     *   算式被解析之后得到的列表
     * @param position
     *   该运算符在list中所在的位置
     * @throws UnparsableEquationException
     */
    public void checkValidation(List<Unit> list, int position)
        throws UnparsableEquationException {
        // 单目运算符不能位于算式的"结尾"
        if (position<0 || position>=(list.size()-1)) {
            throw new UnparsableEquationException(
                    "invalidate unary operator("+value+") position["+position+"]");
        }

        // 单目运算符的前面必须是"双目运算符" 或 "("
        if (position>0) {
            Unit prev = list.get(position-1);
            if (!(prev instanceof BinaryUnit || prev instanceof LParenthesisUnit)) {
                throw new UnparsableEquationException(
                        "invalidate unary operator("+value+") previous symbol["+prev.getValue()+"]");
            }
        }
    }
}
