import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Wrapper Object to all of the math operations and parsing
 * Created by rgw3d on 11/5/2015.
 */
public class Expression implements ExpressionNode{
    private final HashSet<NumberStructure> Terms;//used for the final simplification
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
        Terms = removeZeros(simplify());

        SimplifiedExpression = printSimplifiedExpression(Terms);
    }

    public static String printSimplifiedExpression(HashSet<NumberStructure> list) {
        return printSimplifiedExpression(new ArrayList<NumberStructure>(list));
    }

    public static String printSimplifiedExpression(List<NumberStructure> list) {
        if (list.size() < 2)
            return list.get(0).toString();
        return list.get(0).toString() + " + " + printSimplifiedExpression(list.subList(1, list.size()));
    }

    public static String simplify(String toSimplify) {
        try {
            Expression expression = new Expression(toSimplify);
            return expression.getSimplifiedExpression();
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String add(String expression1, String expression2) {
        try {
            Expression exp1 = new Expression(expression1);
            Expression exp2 = new Expression(expression2);
            return printSimplifiedExpression(MathOperations.add(exp1.getTerms(), exp2.getTerms()));
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String add(Expression expression1, String expression2) {
        try {
            Expression exp2 = new Expression(expression2);
            return printSimplifiedExpression(MathOperations.add(expression1.getTerms(), exp2.getTerms()));
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String add(String expression1, Expression expression2) {
        try {
            Expression exp1 = new Expression(expression1);
            return printSimplifiedExpression(MathOperations.add(exp1.getTerms(), expression2.getTerms()));
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String add(Expression expression1, Expression expression2) {
        return printSimplifiedExpression(MathOperations.add(expression1.getTerms(), expression2.getTerms()));
    }

    public static String multiply(String expression1, String expression2) {
        try {
            Expression exp1 = new Expression(expression1);
            Expression exp2 = new Expression(expression2);
            return printSimplifiedExpression(MathOperations.multiply(exp1.getTerms(), exp2.getTerms()));
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String multiply(Expression expression1, String expression2) {
        try {
            Expression exp2 = new Expression(expression2);
            return printSimplifiedExpression(MathOperations.multiply(expression1.getTerms(), exp2.getTerms()));
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String multiply(String expression1, Expression expression2) {
        try {
            Expression exp1 = new Expression(expression1);
            return printSimplifiedExpression(MathOperations.multiply(exp1.getTerms(), expression2.getTerms()));
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String multiply(Expression expression1, Expression expression2) {
        return printSimplifiedExpression(MathOperations.multiply(expression1.getTerms(), expression2.getTerms()));
    }

    public static String divide(String expression1, String expression2) {
        try {
            Expression exp1 = new Expression(expression1);
            Expression exp2 = new Expression(expression2);
            return printSimplifiedExpression(MathOperations.divide(exp1.getTerms(), exp2.getTerms()));
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String divide(Expression expression1, String expression2) {
        try {
            Expression exp2 = new Expression(expression2);
            return printSimplifiedExpression(MathOperations.divide(expression1.getTerms(), exp2.getTerms()));
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String divide(String expression1, Expression expression2) {
        try {
            Expression exp1 = new Expression(expression1);
            return printSimplifiedExpression(MathOperations.divide(exp1.getTerms(), expression2.getTerms()));
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String divide(Expression expression1, Expression expression2) {
        return printSimplifiedExpression(MathOperations.divide(expression1.getTerms(), expression2.getTerms()));
    }

    public static String power(String expression1, String expression2) {
        try {
            Expression exp1 = new Expression(expression1);
            Expression exp2 = new Expression(expression2);
            return printSimplifiedExpression(MathOperations.power(exp1.getTerms(), exp2.getTerms()));
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String power(Expression expression1, String expression2) {
        try {
            Expression exp2 = new Expression(expression2);
            return printSimplifiedExpression(MathOperations.power(expression1.getTerms(), exp2.getTerms()));
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String power(String expression1, Expression expression2) {
        try {
            Expression exp1 = new Expression(expression1);
            return printSimplifiedExpression(MathOperations.power(exp1.getTerms(), expression2.getTerms()));
        } catch (InputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String power(Expression expression1, Expression expression2) {
        return printSimplifiedExpression(MathOperations.power(expression1.getTerms(), expression2.getTerms()));
    }

    private HashSet<NumberStructure> removeZeros(HashSet<NumberStructure> simplify) {
        HashSet<NumberStructure> toRemove = new HashSet<>();
        for (NumberStructure x : simplify) {
            if (x instanceof Number && x.equals(Number.ZERO)) {
                toRemove.add(x);
            }
            if (x instanceof Term && (((Term) x).getCoefficient().size() == 1 && ((Term) x).getCoefficient().toArray(new NumberStructure[1])[0].equals(Number.ZERO)) || ((Term) x).getCoefficient().size() == 0) {
                toRemove.add(x);
            }
            if (x instanceof Variable && ((Variable) x).getCoefficient().equals(Number.ZERO)) {
                toRemove.add(x);
            }
            if (x instanceof Imaginary && ((Imaginary) x).getCoefficient().equals(Number.ZERO)) {
                toRemove.add(x);
            }
            if (x instanceof Fraction) {
                if ((((Fraction) x).getTop().size() == 1 && ((Fraction) x).getTop().toArray(new NumberStructure[1])[0].equals(Number.ZERO)) || ((Fraction) x).getTop().size() == 0) {
                    toRemove.add(x);
                }
            }
            if (x instanceof Exponent && ((Exponent) x).getBase().equals(Number.ZERO)) {
                toRemove.add(x);
            }
        }

        for (NumberStructure x : toRemove) {
            simplify.remove(x);
        }

        if (simplify.size() == 0)
            simplify.add(Number.ZERO);
        return simplify;
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

    public HashSet<NumberStructure> getTerms() {
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
    public HashSet<NumberStructure> simplify() {
        return getRootOperator().simplify();
    }
}
