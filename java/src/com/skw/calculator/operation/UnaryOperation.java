/**
 * 运算操作接口
 *
 * @author skywang
 */
package com.skw.calculator.operation;

import java.util.List;
import com.skw.calculator.unit.Unit;
import com.skw.calculator.unit.NumberUnit;
import com.skw.calculator.UnparsableEquationException;

public abstract class UnaryOperation extends Operation {

    public UnaryOperation(String name) {
        super(name);
    }

    /**
     * 单目运算符的运算方法
     *
     * 例如，20+r9-10
     * 上面表达式对应的list如下：
     *   list[0]:  20
     *   list[1]:  +
     *   list[2]:  r
     *   list[3]:  9
     *   list[4]:  -
     *   list[5]:  10
     *
     * 对上面表达式中的平根根r进行运算(即，调用calculate(list, 2) )，运算之后list就变成：
     *   list[0]:  20
     *   list[1]:  +
     *   list[2]:  3
     *   list[3]:  -
     *   list[4]:  10
     *
     * @param list
     *   算式被解析之后得到的列表
     * @param position
     *   该算目运算符在list中所在的位置。
     * @return
     *   运算后的列表
     */
    public List<Unit> calculate(List<Unit> list, int position)
        throws UnparsableEquationException {

        // 有效性检查，单目运算符不能位于结尾。
        if (position<0 || position>=(list.size()-1)) {
            throw new UnparsableEquationException("invalidate UnaryOperation("+name+") position["+position+"]");
        }

        // 获取"单目运算符"的后一个值，即它操作的数据
        double next;
        try {
            next = ((NumberUnit)list.get(position+1)).getDouble();
        } catch (java.lang.ClassCastException e) {
            throw new UnparsableEquationException("UnaryOperation("+name+")'s next symbol is not a number.");
        }

        // 获取"单目运算符"的运算结果
        double dval = operation(next);

        // 将参与操作的2个运算单元从list中删除，然后将结果添加到list中。
        list.remove(position+1);
        list.remove(position);
        list.add(position, new NumberUnit(String.valueOf(dval), dval));

        return list;
    }
}
