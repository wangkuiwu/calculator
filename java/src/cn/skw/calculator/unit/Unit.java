package cn.skw.calculator.unit;

import java.util.List;
import cn.skw.calculator.UnparsableEquationException;

/**
 * 算式的运算单元
 *
 *
 *
 * 大体分为3大类： 操作类的运算单元、 数字类的运算单元、括号类的运算单元。
 *
 * 第1大类，操作类的运算单元(OperatorUnit)。包括三个子类：
 *     子类一，双目运算符(BinaryUnit)。
 *         例如，+ - * / % ^
 *     子类二，单目运算符(UnaryUnit)。
 *         例如，sin cos tan ln log abs sqrt
 *     子类三，阶乘类运算符(FactorialUnit)。
 *         例如，!
 *
 * 第2大类，数字类的运算单元(NumberUnit)。数字类运算单元只包括小数。
 *
 * 第3大类，括号类的运算单元(BracketUnit)。包括两个子类：
 *     子类一，左括号(LParenthesisUnit)。
 *     子类二，右括号(RParenthesisUnit)。
 *
 *
 *
 * 例如，对于算式: 30/(-10)-8.3*s0
 *  (01) 30   -- 是NumberUnit(即，"数字"单元)
 *  (02) /    -- 是BinaryUnit(即，"双目运算符"单元)
 *  (03) (    -- 是LParenthesisUnit(即，"左括号"单元)
 *  (04) -10  -- 是NumberUnit(即，"数字"单元)
 *  (05) )    -- 是RParenthesisUnit(即，"右括号"单元)
 *  (06) -    -- 是BinaryUnit(即，"双目运算符"单元)
 *  (07) 8.3  -- 是NumberUnit(即，"数字"单元)
 *  (08) *    -- 是BinaryUnit(即，"双目运算符"单元)
 *  (09) s    -- 是UnaryUnit(即，"单目运算符"单元)；其中，s表示sin
 *  (10) 0    -- 是NumberUnit(即，"数字"单元)
 *
 *
 * @author skywang
 * @e-mail kuiwu-wang@163.com
 */
public abstract class Unit {

    String value;

    public Unit(String value) {
        this.value = value;
    }

    public void setValue(String value) { 
        this.value = value; 
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "[\""+value+"\"]";
    }

    public abstract void checkValidation(List<Unit> list, int position)
        throws UnparsableEquationException;
}
