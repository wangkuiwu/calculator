package cn.skw.calculator.unit;

import java.util.List;
import cn.skw.calculator.UnparsableEquationException;
import cn.skw.calculator.operation.Operation;

/**
 * 双目运算符
 *
 * 例如，"+ - * / % ^" 这些符号都是双目运算符。
 *
 * @author skywang
 * @e-mail kuiwu-wang@163.com
 */
public class BinaryUnit extends OperatorUnit {

    /**
     * 双目运算符的构造函数
     *
     * @param value
     *   双目运算符对应的符号。例如，"加法"对应的value就是"+"
     * @param operation
     *   双目运算符对应的操作方法。该操作方法就是用来执行该操作的。
     */
    public BinaryUnit(String value, Operation operation) {
        super(value, operation);
    }

    /**
     * 检查该"双目运算符"是否有效
     * 
     * 双目运算符(例如, +)必须满足以下基本要求：
     *  (01) 不能位于"开头" 或 "结尾"
     *  (02) 前面是"数字" 或 ")" 或 "!"
     *
     * @param list
     *   算式被解析之后得到的列表
     * @param position
     *   该运算符在list中所在的位置。
     * @throws UnparsableEquationException
     */
    public void checkValidation(List<Unit> list, int position)
        throws UnparsableEquationException {
        // 双目运算符不能位于算式的"开头" 或 "结尾"
        if (position<=0 || position>=(list.size()-1)) {
            throw new UnparsableEquationException(
                    "invalidate binary operator("+value+") position["+position+"]");
        }

        // 双目运算符的前面必须是"数字" 或 ")" 或 "!(阶乘)"
        Unit prev = list.get(position-1);
        if (!(prev instanceof NumberUnit 
           || prev instanceof RParenthesisUnit 
           || prev instanceof FactorialUnit)) {
            throw new UnparsableEquationException(
                    "invalidate binary operator("+value+")'s previous symbol["+prev.getValue()+"]");
        }
    }
}
