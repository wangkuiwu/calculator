import java.util.Scanner;
import cn.skw.calculator.Calculator;
import cn.skw.calculator.UnparsableEquationException;

public class Test {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Test, 请输入算式(支持\"整数\"和\"小数\"的四则运算)：");
        String str = scanner.nextLine();
        try {
            // execute("2+(-2)^3+8*(1-(-1))");
            Calculator.execute(str);
        } catch (UnparsableEquationException e) {
            e.printStackTrace();
        }
    }
}
