package cn.skw.calculator.operation;

import java.util.List;
import cn.skw.calculator.unit.Unit;
import cn.skw.calculator.unit.NumberUnit;
import cn.skw.calculator.UnparsableEquationException;

/**
 * 双目运算操作
 *
 * @author skywang
 * @e-mail kuiwu-wang@163.com
 */
public abstract class BinaryOperation extends Operation {

    public BinaryOperation(String name) {
        super(name);
    }

    /**
     * 双目运算符的运算方法
     *
     * 例如，20+30*2
     * 上面表达式对应的list如下：
     *   list[0]:  20
     *   list[1]:  +
     *   list[2]:  30
     *   list[3]:  *
     *   list[4]:  2
     *
     * 对上面表达式中的*进行运算(即，调用calculate(list, 3) )，运算之后list就变成：
     *   list[0]:  20
     *   list[1]:  +
     *   list[2]:  60
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

        // 有效性检查，双目运算符不能位于开头和结尾。
        if (position<=0 || position>=(list.size()-1)) {
            throw new UnparsableEquationException("invalidate BinaryOperation("+name+") position["+position+"]");
        }

        // 获取"双目运算符"的前后数据
        double prev, next;
        try {
            prev = ((NumberUnit)list.get(position-1)).getDouble();
            next = ((NumberUnit)list.get(position+1)).getDouble();
        } catch (java.lang.ClassCastException e) {
            throw new UnparsableEquationException("BinaryOperation("+name+")'s previous or next symbol is not a number.");
        }

        // 获取"双目运算符"的运算结果
        double dval = operation(prev, next);

        // 将参与操作的3个运算单元从list中删除，然后将结果添加到list中。
        list.remove(position+1);
        list.remove(position);
        list.remove(position-1);
        list.add(position-1, new NumberUnit(String.valueOf(dval), dval));

        return list;
    }
}
