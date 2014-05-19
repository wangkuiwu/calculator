/**
 * 运算操作接口
 *
 * @author skywang
 */
package com.skw.calculator.operation;

import java.util.List;
import com.skw.calculator.unit.Unit;
import com.skw.calculator.UnparsableEquationException;

public abstract class Operation {

    String name;

    public Operation(String name) {
        this.name = name;
    }

    /**
     * 运算方法
     *
     * @param list
     *   算式被解析之后得到的列表
     * @param position
     *   该算目运算符在list中所在的位置。
     * @return
     *   运算后的列表
     */
    public abstract List<Unit> calculate(List<Unit> list, int position) 
        throws UnparsableEquationException;

    /**
     * 运算符操作接口
     *
     * (01) 如果是双目运算符(BinaryOperation)，重载operation()时使用了2个参数
     * (02) 如果是单目运算符(UnaryOperation)，重载operation()时使用了1个参数
     * (03) 如果是阶乘类运算符(FactorialOperation)，重载operation()时使用了1个参数
     */
    public abstract double operation(double... args)
        throws UnparsableEquationException;
}
