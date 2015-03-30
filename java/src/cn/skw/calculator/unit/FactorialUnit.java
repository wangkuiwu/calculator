package cn.skw.calculator.unit;

import java.util.List;
import cn.skw.calculator.UnparsableEquationException;
import cn.skw.calculator.operation.Operation;

/**
 * 阶乘
 *
 * @author skywang
 * @e-mail kuiwu-wang@163.com
 */
public class FactorialUnit extends OperatorUnit {

    /**
     * 阶乘运算符的构造函数
     *
     * @param value
     *   阶乘类运算符对应的符号。各个阶乘类运算符对应的value如下表：
     *
     *      名称    |  value  | 说明
     * (01) 阶乘       !        
     *
     * @param operation
     *   阶乘类运算符对应的操作方法。该操作方法就是用来执行该操作的。
     */
    public FactorialUnit(String value, Operation operation) {
        super(value, operation);
    }

    /**
     * 检查该"阶乘类运算符"是否有效
     * 
     * 阶乘类运算符必须满足以下基本要求：
     *  (01) 不可以位于"开头"，可以位于"结尾"
     *  (02) 前面是"数字" 或 ")"
     *
     * @param list
     *   算式被解析之后得到的列表
     * @param position
     *   该运算符在list中所在的位置
     * @throws UnparsableEquationException
     */
    public void checkValidation(List<Unit> list, int position)
        throws UnparsableEquationException {
        // 阶乘类运算符不能位于算式的"开头"
        if (position<=0 || position>(list.size()-1)) {
            throw new UnparsableEquationException(
                    "invalidate factorial operator("+value+") position["+position+"]");
        }

        // 阶乘类运算符的前面必须是"数字" 或 ")"
        Unit prev = list.get(position-1);
        if (!(prev instanceof NumberUnit || prev instanceof RParenthesisUnit)) {
            throw new UnparsableEquationException(
                    "invalidate factorial operator("+value+")'s previous symbol["+prev.getValue()+"]");
        }
    }
}
