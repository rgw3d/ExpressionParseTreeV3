import junit.framework.TestCase;

/**
 * Used to test the expression parsing abilities
 * Created by rgw3d on 11/13/2015.
 */
public class ExpressionParserTest extends TestCase {

    public void testParseEquation() throws Exception {
        String[] inputs = {"2*(x+-1)", "(4+3)*5*(5+1)", "xxxxxx*y", "pi*5", "pipipie", "piepiepiepie", "5*5*pi*x*y*z*(10+-1)", "i*i*x", "iiii",
                "5x+2y","(5*5*(x*(41+-78x)))","5+-1*(3x+1)","1","5/3","x/yy+1","pi/i","7/-1*(x+1)","8/(x+1)","8^x","x^x^x","6*(x+-1)^x"};
        for(String in: inputs){
            ExpressionParser ep = new ExpressionParser(in);
            System.out.println(in);
            System.out.println(ep.parseEquation());
            System.out.println();
        }
    }

    public void testParseEquation1() throws Exception {
        String[] inputs = {"7/-1*(x+1)"};
        for(String in: inputs){
            ExpressionParser ep = new ExpressionParser(in);
            System.out.println(in);
            System.out.println(ep.parseEquation());
            System.out.println();
        }

    }

}