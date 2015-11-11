import java.util.ArrayList;

/**
 * Wrapper Object to all of the math operations
 * Created by rgw3d on 11/5/2015.
 */
public class Expression {
    private ArrayList<EquationNode> Terms;//used for the final simplification
    private final char[] variables;//
    private final Operator rootOperator;
    private final String inputExpression;
    private final String reformattedExpression;

    public Expression(String expression) throws InputException{
        inputExpression = expression;
        ExpressionSanitizer expressionSanitizer = new ExpressionSanitizer(expression);
        reformattedExpression = expressionSanitizer.getReformattedExpression();
        variables = expressionSanitizer.getVariables();

        ExpressionParser expressionParser = new ExpressionParser(reformattedExpression, variables);
    }

    public char[] getVariables() {
        return variables;
    }

    public Operator getRootOperator() {
        return rootOperator;
    }

    public String getInputExpression() {
        return inputExpression;
    }

    public String getReformattedExpression() {
        return reformattedExpression;
    }



}
