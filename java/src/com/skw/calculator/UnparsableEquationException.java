package com.skw.calculator;
/**
 * 异常
 *
 * @author skywang
 */

public class UnparsableEquationException extends Exception {

    public UnparsableEquationException(String msg) {
        super(msg);
    }
}
