/**
 * "操作运算符"
 *
 * @author skywang
 */
package com.skw.calculator.unit;

import java.util.List;
import com.skw.calculator.UnparsableEquationException;
import com.skw.calculator.operation.Operation;

public abstract class OperatorUnit extends Unit {

    private Operation operation;  // 操作函数

    /**
     * 操作运算符的构造函数
     *
     * @param value
     *   运算符对应的符号。例如，"加法"对应的value就是"+"
     * @param operation
     *   运算符对应的操作方法。该操作方法就是用来执行该操作的。
     */
    public OperatorUnit(String value, Operation operation) {
        super(value);
        this.operation = operation;
    }

    /**
     * 运算符的运算方法
     */
    public List<Unit> calculate(List<Unit> list, int position) 
        throws UnparsableEquationException {
        return operation.calculate(list, position);
    }

    public abstract void checkValidation(List<Unit> list, int position)
        throws UnparsableEquationException ;
}
