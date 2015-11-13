import junit.framework.TestCase;
import org.junit.Test;

import java.util.Arrays;

/**
 * Used to test methods in ExpressionSanitizer
 * Created by rgw3d on 9/19/2015.
 */
public class ExpressionSanitizerTest extends TestCase {

    @Test
    public void testInferMultiplication() throws InputException {
        String[] testInputs = {"2(x-1)", "(4+3)5(5+1)"};
        String[] outputs = {"2*(x+-1)", "(4+3)*5*(5+1)"};

        for (int i = 0; i < testInputs.length; i++) {
            ExpressionSanitizer es = new ExpressionSanitizer(testInputs[i]);
            System.out.println("@Test inferParenthesisMultiplication(): " + testInputs[i] + " = " + es.getReformattedExpression());
            assertEquals(outputs[i], es.getReformattedExpression());
        }

    }

    @Test
    public void testReformatInput() throws Exception {

        String[] testInputs = {"pi(5+1)","2  *3(x-1)", "4*5", "5(5(5(5(5(5)5)5)5)5)5", "x(x+1)", "pi(5+x)"};
        String[] outputs = {"pi*(5+1)","2*3*(x+-1)", "4*5", "5*(5*(5*(5*(5*(5)*5)*5)*5)*5)*5", "x*(x+1)","pi*(5+x)"};


        for (int i = 0; i < testInputs.length; i++) {
            ExpressionSanitizer es = new ExpressionSanitizer(testInputs[i]);
            String functionReturn = es.reformatInput(testInputs[i]);
            System.out.println("@Test reformatInput(): " + testInputs[i] + " = " + functionReturn);
            assertEquals(outputs[i], functionReturn);
        }
    }

    @Test
    public void testExtractVariables() throws Exception {
        String [] testInputs = {"x*2+1", "4*x*y*z", "5(5(5))","pi*e*pi*x"};
        String[] outputs = {"x","xyz", "","x"};
        for (int i = 0; i < testInputs.length; i++) {
            ExpressionSanitizer es = new ExpressionSanitizer(testInputs[i]);
            char[] functionReturn = es.getVariables();
            System.out.println("@Test extractVariables(): " + testInputs[i] + " = "+ Arrays.toString(functionReturn));
            assertEquals(outputs[i],new String(functionReturn));
        }
    }

}