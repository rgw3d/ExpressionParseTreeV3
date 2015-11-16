import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Wrapper Object to all of the math operations and parsing
 * Created by rgw3d on 11/5/2015.
 */
public class Expression implements ExpressionNode{
    private final HashSet<ExpressionNode> Terms;//used for the final simplification
    private final char[] Variables;//
    private final ExpressionNode RootOperator;
    private final String InputExpression;
    private final String ReformattedExpression;
    private final String SimplifiedExpression;

    public Expression(String expression) throws InputException{
        InputExpression = expression;
        ExpressionSanitizer expressionSanitizer = new ExpressionSanitizer(expression);
        ReformattedExpression = expressionSanitizer.getReformattedExpression();
        Variables = expressionSanitizer.getVariables();

        ExpressionParser expressionParser = new ExpressionParser(ReformattedExpression);
        RootOperator = expressionParser.parseEquation();
        Terms = simplify();
        SimplifiedExpression = printSimplifiedExpression(Terms);
    }

    public char[] getVariables() {
        return Variables;
    }

    public ExpressionNode getRootOperator() {
        return RootOperator;
    }

    public String getInputExpression() {
        return InputExpression;
    }

    public String getReformattedExpression() {
        return ReformattedExpression;
    }

    public HashSet<ExpressionNode> getTerms() {
        return Terms;
    }

    public String getSimplifiedExpression() {
        return SimplifiedExpression;
    }


    /**
     * Get the list of simplified terms,
     *
     * @return simplified list of operation in Nominals and Fractions
     */
    @Override
    public HashSet<ExpressionNode> simplify() {
        return getRootOperator().simplify();
    }

    public static String printSimplifiedExpression(HashSet<ExpressionNode> list){
        return printSimplifiedExpression(new ArrayList<ExpressionNode>(list));
    }
    public static String printSimplifiedExpression(List<ExpressionNode> list){
        if(list.size() == 1)
            return list.get(0).toString();
        return list.get(0).toString() + " + " + printSimplifiedExpression(list.subList(1, list.size() - 1));
    }


    public static String simplify(String toSimplify){
        try {
            Expression expression = new Expression(toSimplify);
            return expression.getSimplifiedExpression();
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String add(String expression1, String expression2){
        try {
            Expression exp1 = new Expression(expression1);
            Expression exp2 = new Expression(expression2);
            return printSimplifiedExpression(MathOperations.add(exp1.getTerms(), exp2.getTerms()));
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String add(Expression expression1, String expression2){
        try {
            Expression exp2 = new Expression(expression2);
            return printSimplifiedExpression(MathOperations.add(expression1.getTerms(), exp2.getTerms()));
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String add(String expression1, Expression expression2){
        try {
            Expression exp1 = new Expression(expression1);
            return printSimplifiedExpression(MathOperations.add(exp1.getTerms(), expression2.getTerms()));
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String add(Expression expression1, Expression expression2){
        return printSimplifiedExpression(MathOperations.add(expression1.getTerms(), expression2.getTerms()));
    }

    public static String multiply(String expression1, String expression2){
        try {
            Expression exp1 = new Expression(expression1);
            Expression exp2 = new Expression(expression2);
            return printSimplifiedExpression(MathOperations.multiply(exp1.getTerms(), exp2.getTerms()));
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String multiply(Expression expression1, String expression2){
        try {
            Expression exp2 = new Expression(expression2);
            return printSimplifiedExpression(MathOperations.multiply(expression1.getTerms(), exp2.getTerms()));
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String multiply(String expression1, Expression expression2){
        try {
            Expression exp1 = new Expression(expression1);
            return printSimplifiedExpression(MathOperations.multiply(exp1.getTerms(), expression2.getTerms()));
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String multiply(Expression expression1, Expression expression2){
        return printSimplifiedExpression(MathOperations.multiply(expression1.getTerms(), expression2.getTerms()));
    }

    public static String divide(String expression1, String expression2){
        try {
            Expression exp1 = new Expression(expression1);
            Expression exp2 = new Expression(expression2);
            return printSimplifiedExpression(MathOperations.divide(exp1.getTerms(), exp2.getTerms()));
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String divide(Expression expression1, String expression2){
        try {
            Expression exp2 = new Expression(expression2);
            return printSimplifiedExpression(MathOperations.divide(expression1.getTerms(), exp2.getTerms()));
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String divide(String expression1, Expression expression2){
        try {
            Expression exp1 = new Expression(expression1);
            return printSimplifiedExpression(MathOperations.divide(exp1.getTerms(), expression2.getTerms()));
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String divide(Expression expression1, Expression expression2){
        return printSimplifiedExpression(MathOperations.divide(expression1.getTerms(), expression2.getTerms()));
    }

    public static String power(String expression1, String expression2){
        try {
            Expression exp1 = new Expression(expression1);
            Expression exp2 = new Expression(expression2);
            return printSimplifiedExpression(MathOperations.power(exp1.getTerms(), exp2.getTerms()));
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String power(Expression expression1, String expression2){
        try {
            Expression exp2 = new Expression(expression2);
            return printSimplifiedExpression(MathOperations.power(expression1.getTerms(), exp2.getTerms()));
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String power(String expression1, Expression expression2){
        try {
            Expression exp1 = new Expression(expression1);
            return printSimplifiedExpression(MathOperations.power(exp1.getTerms(), expression2.getTerms()));
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String power(Expression expression1, Expression expression2){
        return printSimplifiedExpression(MathOperations.power(expression1.getTerms(), expression2.getTerms()));
    }
}
