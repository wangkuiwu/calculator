package cn.skw.calculator.unit;

import java.util.List;
import cn.skw.calculator.UnparsableEquationException;

/**
 * 左括号
 *
 * @author skywang
 * @e-mail kuiwu-wang@163.com
 */
public class LParenthesisUnit extends BracketUnit {

    /**
     * 左括号的构造函数
     *
     * @param value
     *   左括号对应的字符串
     */
    public LParenthesisUnit(String value) {
        super(value);
    }

    /**
     * 检查该"左括号"是否有效
     * 
     * 左括号必须满足以下基本要求：
     *  (01) 可以位于"开头"，不能位于"结尾"
     *  (02) 前面必须是"(" 或 "双目运算符" 或 "单目运算符"
     *
     * @param list
     *   算式被解析之后得到的列表
     * @param position
     *   该运算符在list中所在的位置
     * @throws UnparsableEquationException
     */
    public void checkValidation(List<Unit> list, int position)
        throws UnparsableEquationException {
        // 左括号不能位于算式的 "结尾"
        if (position<0 || position>=(list.size()-1)) {
            throw new UnparsableEquationException(
                    "invalidate left parenthesis("+value+") position["+position+"]");
        }

        // 左括号的前面必须是"(" 或 "双目运算符" 或 "单目运算符"
        if (position>0) {
            Unit prev = list.get(position-1);
            if (!(prev instanceof LParenthesisUnit
               || prev instanceof BinaryUnit 
               || prev instanceof UnaryUnit)) {
                throw new UnparsableEquationException(
                        "invalidate left parenthesis("+value+")'s previous symbol["+prev.getValue()+"]");
            }
        }
    }
}
