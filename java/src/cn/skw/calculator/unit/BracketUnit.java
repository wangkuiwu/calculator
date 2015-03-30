package cn.skw.calculator.unit;

import java.util.List;
import cn.skw.calculator.UnparsableEquationException;

/**
 * 括号
 *
 * @author skywang
 * @e-mail kuiwu-wang@163.com
 */
public abstract class BracketUnit extends Unit {

    public BracketUnit(String value) {
        super(value);
    }

    public abstract void checkValidation(List<Unit> list, int position)
        throws UnparsableEquationException ;
}
