import java.util.ArrayList;

/**
 * Wrapper Object to all of the math operations and parsing
 * Created by rgw3d on 11/5/2015.
 */
public class Expression {
    private ArrayList<ExpressionNode> Terms;//used for the final simplification
    private final char[] Variables;//
    private final ExpressionNode RootOperator;
    private final String inputExpression;
    private final String ReformattedExpression;

    public Expression(String expression) throws InputException{
        inputExpression = expression;
        ExpressionSanitizer expressionSanitizer = new ExpressionSanitizer(expression);
        ReformattedExpression = expressionSanitizer.getReformattedExpression();
        Variables = expressionSanitizer.getVariables();

        ExpressionParser expressionParser = new ExpressionParser(ReformattedExpression,Variables);
        RootOperator = expressionParser.parseEquation();
    }

    public char[] getVariables() {
        return Variables;
    }

   // public Operator getRootOperator() {
    //    return RootOperator;
    //}

    public String getInputExpression() {
        return inputExpression;
    }

    public String getReformattedExpression() {
        return ReformattedExpression;
    }



}
