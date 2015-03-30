package cn.skw.calculator;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.skw.calculator.util.Util;
import cn.skw.calculator.unit.Unit;
import cn.skw.calculator.unit.BinaryUnit;
import cn.skw.calculator.unit.BracketUnit;
import cn.skw.calculator.unit.FactorialUnit;
import cn.skw.calculator.unit.LParenthesisUnit;
import cn.skw.calculator.unit.NumberUnit;
import cn.skw.calculator.unit.OperatorUnit;
import cn.skw.calculator.unit.RParenthesisUnit;
import cn.skw.calculator.unit.UnaryUnit;
import cn.skw.calculator.operation.Operation;
import cn.skw.calculator.operation.BinaryOperation;
import cn.skw.calculator.operation.FactorialOperation;
import cn.skw.calculator.operation.UnaryOperation;

/**
 * 计算器执行者
 *
 * @author skywang
 * @e-mail kuiwu-wang@163.com
 */
public class Executor {

    // 运算表达式
    private String mExp;
    // mDouble为true，表示结果是"小数"；否则，结果是整数。
    private boolean mDouble=false;

    private Map<String, Operation> mBinaryOperations;

    private Map<String, Operation> mUnaryOperations;

    private Map<String, Operation> mFactorialOperations;

    /**
     * 构造函数
     */
    public Executor(String exp) {
        mExp = new String(exp);
    }

    /**
     * 运算执行函数，返回运算结果
     */
    public String execute() throws UnparsableEquationException {

        if (mExp==null || mExp.length()==0)
            return "";

        mBinaryOperations = getBinaryperations();
        mUnaryOperations = getUnaryperations();
        mFactorialOperations = getFactorialOperations();

        // 解析"算式"：将mExp分解成"运算单元列表"
        List<Unit> list = parse(mExp);

        // 检查"算式有效性"
        checkValidation(list);

        // 计算
        double dval = calEquation(list);

        return trimZero(String.valueOf(dval));
    }

    /**
     * 获取双目运算符
     */
    private Map<String, Operation> getBinaryperations() {
        // 双目运算符: + - * / % ^
        Operation plus = new BinaryOperation("+") {
            @Override
            public double operation(double... args) {
                return args[0]+args[1];
            }
        };
        Operation minus = new BinaryOperation("-") {
            @Override
            public double operation(double... args) {
                return args[0]-args[1];
            }
        };
        Operation multiply = new BinaryOperation("*") {
            @Override
            public double operation(double... args) {
                return args[0]*args[1];
            }
        };
        Operation divide = new BinaryOperation("/") {
            @Override
            public double operation(double... args) {
                return args[0]/args[1];
            }
        };
        Operation mod = new BinaryOperation("%") {
            @Override
            public double operation(double... args) {
                return args[0]%args[1];
            }
        };
        Operation power = new BinaryOperation("^") {
            @Override
            public double operation(double... args) {
                return Math.pow(args[0], args[1]);
            }
        };

        // 双目运算符
        Map<String, Operation> map = new HashMap<String, Operation>();
        map.put("+", plus);
        map.put("-", minus);
        map.put("*", multiply);
        map.put("/", divide);
        map.put("%", mod);
        map.put("^", power);

        return map;
    }

    /**
     * 获取单目类运算符
     */
    private Map<String, Operation> getUnaryperations() {
        // 单目运算符: sin cos tan ln log abs sqrt
        Operation sin = new UnaryOperation("s") {
            @Override
            public double operation(double... args) {
                return Math.sin(args[0]);
            }
        };
        Operation cos = new UnaryOperation("c") {
            @Override
            public double operation(double... args) {
                return Math.cos(args[0]);
            }
        };
        Operation tan = new UnaryOperation("t") {
            @Override
            public double operation(double... args) {
                return Math.tan(args[0]);
            }
        };
        Operation ln = new UnaryOperation("n") {
            @Override
            public double operation(double... args) {
                return Math.log(args[0]);
            }
        };
        Operation log = new UnaryOperation("g") {
            @Override
            public double operation(double... args) {
                return Math.log10(args[0]);
            }
        };
        Operation abs = new UnaryOperation("a") {
            @Override
            public double operation(double... args) {
                return Math.abs(args[0]);
            }
        };
        Operation sqrt = new UnaryOperation("r") {
            @Override
            public double operation(double... args) {
                return Math.sqrt(args[0]);
            }
        };

        Map<String, Operation> map = new HashMap<String, Operation>();
        map.put("s", sin);
        map.put("c", cos);
        map.put("t", tan);
        map.put("n", ln);
        map.put("g", log);
        map.put("a", abs);
        map.put("r", sqrt);

        return map;
    }

    /**
     * 获取阶乘类运算符
     */
    private Map<String, Operation> getFactorialOperations() {

        // 阶乘类运算符
        Operation fac = new FactorialOperation("!") {
            @Override
            public double operation(double... args) 
                throws UnparsableEquationException {
                int i = (int)args[0];

                if (i<1) 
                    throw new UnparsableEquationException("invalidate Factorial parameter: "+args[0]);
    
                int sum = 1;
                for (; i>0; i--)
                    sum *= i;

                return (double)sum;
            }
        };

        // 阶乘类运算符
        Map<String, Operation> map = new HashMap<String, Operation>();
        map.put("!", fac);

        return map;
    }

    /**
     * 将string解析为"运算单元组成的List列表"
     */
    private List<Unit> parse(String exp) throws UnparsableEquationException {
        List<Unit> list = new ArrayList<Unit>();
        char[] arr = exp.toCharArray();

        for (int i=0; i < arr.length; i++) {
            Unit unit;
            char ch = arr[i];

            // 空格
            if (ch==' ')
                continue;

            // 数字
            if (Character.isDigit(ch)) {
                int j = getFirstIndexOfNoneNumberic(arr, i+1) ;
                String value = new String(arr, i, j-i);

                double dval = toDouble(value);
                unit = new NumberUnit(value, dval);
                i = j-1;
            // "派π"
            } else if (ch=='p') {
                unit = new NumberUnit(String.valueOf(ch), Math.PI);
            // "常数e"
            } else if (ch=='e') {
                unit = new NumberUnit(String.valueOf(ch), Math.E);
            // "("
            } else if (ch=='(') {
                unit = new LParenthesisUnit(String.valueOf(ch));
            // ")"
            } else if (ch==')') {
                unit = new RParenthesisUnit(String.valueOf(ch));
            // "+"
            } else if (ch=='+') {
                // 正号
                // 条件：位于第1位，或者前面是"("。
                // 处理：删除
                int size = list.size();
                if (size==0 || (list.get(size-1) instanceof LParenthesisUnit)) {
                    continue;
                // 加号：即双目操作符
                } else {
                    Operation operation = mBinaryOperations.get(String.valueOf(ch));
                    unit = new BinaryUnit(String.valueOf(ch), operation);
                }
            // "-"
            } else if (ch=='-') {
                // 负号
                // 条件：位于第1位，或者前面是"("。
                // 处理：转换成 -1  *
                int size = list.size();
                if (size==0 || (list.get(size-1) instanceof LParenthesisUnit)) {
                    Operation operation = mBinaryOperations.get("*");

                    list.add(new NumberUnit("-1", -1));
                    list.add(new BinaryUnit("*", operation));
                    continue;
                // 减号：即双目操作符
                } else {
                    Operation operation = mBinaryOperations.get(String.valueOf(ch));
                    unit = new BinaryUnit(String.valueOf(ch), operation);
                }
            // 双目运算符
            } else if (isBinaryOperation(ch)) {
                Operation operation = mBinaryOperations.get(String.valueOf(ch));
                unit = new BinaryUnit(String.valueOf(ch), operation);
            // 单目运算符
            } else if (isUnaryOperation(ch)) {
                Operation operation = mUnaryOperations.get(String.valueOf(ch));
                unit = new UnaryUnit(String.valueOf(ch), operation);
            // 阶乘类运算符
            } else if (isFactorialOperation(ch)) {
                Operation operation = mFactorialOperations.get(String.valueOf(ch));
                unit = new FactorialUnit(String.valueOf(ch), operation);
            // 无效字符
            } else {
                throw new UnparsableEquationException("invalidate character("+ch+")");
            }

            list.add(unit);
        }

        return list;
    }

    /**
     * 获取arr中从offset开始(包括)的第一个"既不是[0-9]，又不是."的字符的序号。
     */
    public int getFirstIndexOfNoneNumberic(char[] arr, int offset) {
        for (int i=offset; i<arr.length; i++) {
            if (!(Character.isDigit(arr[i]) || (arr[i]=='.'))) {
                return i;
            }
        }

        return arr.length;
    }

    /**
     * 将字符串转换为double
     */
    private double toDouble(String value) 
        throws UnparsableEquationException {

        double dval=0.0d;
        try {
            dval = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new UnparsableEquationException("invalidate number("+value+")");
        }

        return dval;
    }

    /**
     * 是不是双目运算符
     */
    private boolean isBinaryOperation(char ch) {
        return mBinaryOperations.containsKey(String.valueOf(ch));
    }

    /**
     * 是不是单目运算符
     */
    private boolean isUnaryOperation(char ch) {
        return mUnaryOperations.containsKey(String.valueOf(ch));
    }

    /**
     * 是不是阶乘类运算符
     */
    private boolean isFactorialOperation(char ch) {
        return mFactorialOperations.containsKey(String.valueOf(ch));
    }


    /**
     * 判断"运算单元列表"的有效性
     * 总体来说区别两部分：(01) 对"每个运算单元的位置" 和 "前后逻辑"进行判断
     *                     (02) 对"("和")"能否正确配对进行判断
     *
     * @param list -- "运算单元"列表。
     *                 列表中每一个元素都是一个运算单元。
     */
    private void checkValidation(List<Unit> list) throws UnparsableEquationException {

        List<Integer> llist = new ArrayList<Integer>();     // 左括号列表
        for (int j=0; j<list.size(); j++) {

            Unit unit = list.get(j);

            // 检查每个"运算单元"的位置和前后逻辑
            unit.checkValidation(list, j);

            // "("
            if (unit instanceof LParenthesisUnit) {
                llist.add(j);
            // ")"
            } else if (unit instanceof RParenthesisUnit) {
                int i;
                // 在"左括号列表llist"中找到和")"配对的"("，并将其从llist中删除。
                for (i=j-1; i>=0; i--) {
                    if ((list.get(i) instanceof LParenthesisUnit)
                     && (llist.contains(i))) {
                        break;
                    }
                }

                if (i!=-1) {
                    llist.remove(Integer.valueOf(i));
                } else {
                    throw new UnparsableEquationException("ERROR: no left-bracket to match right-bracket.");
                }
            }
        }

        if (llist.size()!=0)
            throw new UnparsableEquationException("ERROR: no right-bracket to match left-bracket.");
    }


    /**
     * 计算不包含括号的算式
     */
    public double calEquationWihoutBrackets(List<Unit> list)
        throws UnparsableEquationException {
        int i;

        // 首先，计算 "单目运算符"
        i = 0;
        while (i<list.size()) {

            Unit unit = list.get(i);
            if (unit instanceof UnaryUnit) {
                list = ((UnaryUnit)unit).calculate(list, i);
                i++;
            } else {
                i++;
            }
        }

        // 然后，计算 "阶乘类运算符"
        i = 0;
        while (i<list.size()) {

            Unit unit = list.get(i);
            if (unit instanceof FactorialUnit) {
                list = ((FactorialUnit)unit).calculate(list, i);
            } else {
                i++;
            }
        }

        // 接着，计算 "幂"(双目运算符)
        i = 0;
        while (i<list.size()) {

            Unit unit = list.get(i);
            String power = "^";
            if ((unit instanceof BinaryUnit)
             && (power.indexOf(unit.getValue())!=-1)) {
                list = ((BinaryUnit)unit).calculate(list, i);
            } else {
                i++;
            }
        }

        // 接着，计算 "乘/除/余"(双目运算符)
        i = 0;
        while (i<list.size()) {

            Unit unit = list.get(i);
            String mdm = "*%/";
            if ((unit instanceof BinaryUnit)
             && (mdm.indexOf(unit.getValue())!=-1)) {
                list = ((BinaryUnit)unit).calculate(list, i);
            } else {
                i++;
            }
        }

        // 最后，计算 "加/减"(双目运算符)
        i = 0;
        while (i<list.size()) {

            Unit unit = list.get(i);
            String pm = "+-";
            if ((unit instanceof BinaryUnit)
             && (pm.indexOf(unit.getValue())!=-1)) {
                list = ((BinaryUnit)unit).calculate(list, i);
            } else {
                i++;
            }
        }

        double dval = 0.0d;
        if (list.size()==1 && (list.get(0) instanceof NumberUnit)) {
            dval = ((NumberUnit)list.get(0)).getDouble();
        } else {
            throw new UnparsableEquationException("ERROR: calEquationWihoutBrackets failed.");
        }

        return dval;
    }

    /**
     * 计算"解析之后的表达式"。
     *
     * @param list -- "运算单元"列表。
     *                 列表中每一个元素都是一个运算单元。
     * @return 计算结果
     */
    private double calEquation(List<Unit> list) throws UnparsableEquationException {
        int left,right;             // 左右括号的序号

        // 计算"所有括号内的算式"
        while ((right=getFirstRParenthesis(list, 0)) != -1) {
            left = getReverseFirstLParenthesis(list, right);

            // 计算"子括号内的算式"
            double dval = calEquationWihoutBrackets(new ArrayList(list.subList(left+1, right)));
            // 删除"包含子括号在内的全部数据"
            for (int i=right; i>=left; i--)
                list.remove(i);
            // 将"子括号算式的计算结果"添加到clist中
            list.add(left, new NumberUnit(String.valueOf(dval), dval));
        }

        // 括号计算完毕之后，再计算不包含括号的算式。
        return calEquationWihoutBrackets(list);
    }

    /**
     * 获取list中从index开始往后查找的第一个")"的序号；不存在的话，返回-1。
     */
    private int getFirstRParenthesis(List<Unit> list, int index) {
        for (int i=index; i<list.size(); i++) {
            if (list.get(i) instanceof RParenthesisUnit)
                return i;
        }

        return -1;
    }

    /**
     * 获取list中从index开始往前查找的第一个"("的序号；不存在的话，返回-1。
     */
    private int getReverseFirstLParenthesis(List<Unit> list, int index) {
        for (int i=index; i>=0; i--) {
            if (list.get(i) instanceof LParenthesisUnit)
                return i;
        }

        return -1;
    }

    /**
     * 删除ori末尾的所有"0"，包括(.0)。
     */
    private String trimZero(String ori) {
        int len = ori.length();
        int i=0;

        for (i=len-1; i>=0; i--) {
            char ch = ori.charAt(i);
            if (ch!='0') {
                if (ch=='.') i--;
                break;
            }
        }

        return ori.substring(0, i+1);
    }

    public static void main(String[] args) {
        String str = "2+(-2)^3+8*(1-(-1))";

        Executor executor = new Executor(str);
        try {
            String ret =executor.execute();
            System.out.printf(" %s=%s\n", str, ret); 
        } catch (UnparsableEquationException e) {
            e.printStackTrace();
        }
    }
}

