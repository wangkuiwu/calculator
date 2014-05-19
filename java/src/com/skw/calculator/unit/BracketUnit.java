/**
 * 括号
 *
 * @author skywang
 */
package com.skw.calculator.unit;

import java.util.List;
import com.skw.calculator.UnparsableEquationException;

public abstract class BracketUnit extends Unit {

    public BracketUnit(String value) {
        super(value);
    }

    public abstract void checkValidation(List<Unit> list, int position)
        throws UnparsableEquationException ;
}
