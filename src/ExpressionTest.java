import junit.framework.TestCase;

/**
 * Created by rgw3d on 11/16/2015.
 */
public class ExpressionTest extends TestCase {


    public void testExpression() throws Exception {
        String[] inputs = {"1+2", "5+pi", "2+x", "x+2-2", "-x+2", "xx+2", "2pi+3pi", "2e+3pi", "2x+3x"};

        for (String x : inputs) {
            Expression expression = new Expression(x);
            System.out.println(expression.getSimplifiedExpression());
        }
    }

    public void testExpression1() throws Exception {
        String[] inputs = {"3i+1","3i-1","i","2pi+3pi", "2e+3pi", "2x+3x", "4x+4y+8z", "4xy+5yx", "5i+3i", "xy-xy"};

        for (String x : inputs) {
            Expression expression = new Expression(x);
            System.out.println(expression.getSimplifiedExpression());
        }
    }
}