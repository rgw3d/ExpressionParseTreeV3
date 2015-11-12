import java.util.ArrayList;

/**
 * Wrapper Object to all of the math operations
 * Created by rgw3d on 11/5/2015.
 */
public class Expression {
    private ArrayList<EquationNode> Terms;//used for the final simplification
    private final char[] Variables;//
    //private final Operator RootOperator;
    private final String inputExpression;
    private final String ReformattedExpression;

    public Expression(String expression) throws InputException{
        inputExpression = expression;
        ExpressionSanitizer expressionSanitizer = new ExpressionSanitizer(expression);
        ReformattedExpression = expressionSanitizer.getReformattedExpression();
        Variables = expressionSanitizer.getVariables();

        //RootOperator = ExpressionParser.parse(ReformattedExpression,Variables);
    }

    public char[] getVariables() {
        return Variables;
    }

   // public Operator getRootOperator() {
    //j    return RootOperator;
    //}

    public String getInputExpression() {
        return inputExpression;
    }

    public String getReformattedExpression() {
        return ReformattedExpression;
    }



}
