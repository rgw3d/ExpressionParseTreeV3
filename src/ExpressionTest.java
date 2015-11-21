import junit.framework.TestCase;

/**
 * Created by rgw3d on 11/16/2015.
 * Simple visual tests to make sure everything is in working order.
 * There are no assert statements - print outs and visual confirmation
 */
public class ExpressionTest extends TestCase {


    public void testExpression() throws Exception {
        String[] inputs = {"1+2", "5+pi", "2+x", "x+2-2", "-x+2", "xx+2", "2pi+3pi", "2e+3pi", "2x+3x"};

        for (String x : inputs) {
            Expression expression = new Expression(x);
            System.out.println(x);
            System.out.println(expression.getSimplifiedExpression());
            System.out.println();
        }
    }

    public void testExpression1() throws Exception {
        String[] inputs = {"3i+1", "3i-1", "i", "2pi+3pi", "2e+3pi", "2x+3x", "4x+4y+8z", "4xy+5yx", "5i+3i", "xy-xy", "iii+5"};

        for (String x : inputs) {
            Expression expression = new Expression(x);
            System.out.println(x);
            System.out.println(expression.getSimplifiedExpression());
            System.out.println();
        }
    }

    public void testSpecificThingSoDebuggingIsEasy() throws Exception {


    }
}