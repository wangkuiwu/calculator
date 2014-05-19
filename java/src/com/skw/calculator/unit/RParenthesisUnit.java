/**
 * 右括号
 *
 * @author skywang
 */
package com.skw.calculator.unit;

import java.util.List;
import com.skw.calculator.UnparsableEquationException;

public class RParenthesisUnit extends BracketUnit {

    /**
     * 右括号的构造函数
     *
     * @param value
     *   右括号对应的字符串
     */
    public RParenthesisUnit(String value) {
        super(value);
    }

    /**
     * 检查该"右括号"是否有效
     * 
     * 右括号必须满足以下基本要求：
     *  (01) 不可以位于"开头"，可以位于"结尾"
     *  (02) 前面必须是")" 或 "数字" 或 "阶乘"
     *
     * @param list
     *   算式被解析之后得到的列表
     * @param position
     *   该运算符在list中所在的位置
     * @throws UnparsableEquationException
     */
    public void checkValidation(List<Unit> list, int position)
        throws UnparsableEquationException {
        // 右括号不能位于算式的 "开头"
        if (position<=0 || position>(list.size()-1)) {
            throw new UnparsableEquationException(
                    "invalidate right parenthesis("+value+") position["+position+"]");
        }

        // 右括号的前面必须是")" 或 "数字" 或 "阶乘"
        Unit prev = list.get(position-1);
        if (!(prev instanceof RParenthesisUnit
           || prev instanceof NumberUnit 
           || prev instanceof FactorialUnit)) {
            throw new UnparsableEquationException(
                    "invalidate right parenthesis("+value+") previous symbol["+prev.getValue()+"]");
        }
    }
}
