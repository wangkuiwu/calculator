package cn.skw.calculator.operation;

import java.util.List;
import cn.skw.calculator.unit.Unit;
import cn.skw.calculator.unit.NumberUnit;
import cn.skw.calculator.UnparsableEquationException;

/**
 * 阶乘运算操作
 *
 * @author skywang
 * @e-mail kuiwu-wang@163.com
 */
public abstract class FactorialOperation extends Operation {

    public FactorialOperation(String name) {
        super(name);
    }

    /**
     * 阶乘算符的运算方法
     *
     * 例如，20+4!-10
     * 上面表达式对应的list如下：
     *   list[0]:  20
     *   list[1]:  +
     *   list[2]:  4
     *   list[3]:  !
     *   list[4]:  -
     *   list[5]:  10
     *
     * 对上面表达式中的阶乘符号!进行运算(即，调用calculate(list, 3) )，运算之后list就变成：
     *   list[0]:  20
     *   list[1]:  +
     *   list[2]:  24
     *   list[3]:  -
     *   list[4]:  10
     *
     * @param list
     *   算式被解析之后得到的列表
     * @param position
     *   该运算符在list中所在的位置
     * @return
     *   运算后的列表
     */
    public List<Unit> calculate(List<Unit> list, int position)
        throws UnparsableEquationException {

        // 有效性检查，阶乘类运算符不能位于开头
        if (position<=0 || position>(list.size()-1)) {
            throw new UnparsableEquationException("invalidate FactorialOperation("+name+") position["+position+"]");
        }

        // 获取"阶乘类运算符"的前一个值，即它操作的数据
        double prev;
        try {
            prev = ((NumberUnit)list.get(position-1)).getDouble();
        } catch (java.lang.ClassCastException e) {
            throw new UnparsableEquationException("FactorialOperation("+name+")'s prev symbol is not a number.");
        }

        // 获取"阶乘类运算符"的运算结果
        double dval = operation(prev);

        // 将参与操作的2个运算单元从list中删除，然后将结果添加到list中。
        list.remove(position);
        list.remove(position-1);
        list.add(position-1, new NumberUnit(String.valueOf(dval), dval));

        return list;
    }
}
