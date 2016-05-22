import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Wrapper Object to all of the math operations and parsing
 * Created by rgw3d on 11/5/2015.
 */
public class Expression implements ExpressionNode{
    private final HashSet<NumberStructure> Terms;//Hold Simplified Expression
    private final char[] Variables;//All of the variables in the expression
    private final ExpressionNode RootOperator;//Root of the Parse Tree
    private final String InputExpression;//The inputted Expression
    private final String ReformattedExpression;//The same expression as the input, but reformatted to be more parser friendly
    private final String SimplifiedExpression;//String version of the Terms variable -- Simplified expression

    public Expression(String expression) throws InputException{
        InputExpression = expression;
        ExpressionSanitizer expressionSanitizer = new ExpressionSanitizer(expression);//Create ExpressionSanitizer variable
        ReformattedExpression = expressionSanitizer.getReformattedExpression();//Get Reformatted Expression
        Variables = expressionSanitizer.getVariables();//Get variables in the Reformatted Expression

        ExpressionParser expressionParser = new ExpressionParser(ReformattedExpression);//Create ExpressionParser Variable
        RootOperator = expressionParser.parseEquation();//Get Root of the Parse Tree
        Terms = removeZeros(simplify());//Simplify expression and Remove any extra Zeros that were left behind

        SimplifiedExpression = printSimplifiedExpression(Terms);//Get string of simplified expression
    }

    /**
     * Helper function to return the simplified expression from a HashSet containing the simplified terms
     * Since we cannot immediately iterate though a HashSet, we convert it to an ArrayList and pass it
     * to the the other printSimplifiedExpression() method
     *
     * @param list HashSet containing the simplified terms
     * @return String containing Simplified Expression
     */
    public static String printSimplifiedExpression(HashSet<NumberStructure> list) {
        return printSimplifiedExpression(new ArrayList<>(list));
    }

    /**
     * Return the simplified expression String from an input List
     * Usually called from the other printSimplifiedExpression(HashSet) method
     *
     * @param list List containing all the simplified terms to print
     * @return String containing Simplified Expression
     */
    public static String printSimplifiedExpression(List<NumberStructure> list) {
        String result = "";
        for (NumberStructure x : list) {
            result = result + x.toString() + " + ";
        }

        if (result.length() > 0) {//remove additional "+"
            result = result.substring(0, result.length() - 3);//TODO: Ensure that this Works
        }
        return result;
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

    /**
     * Remove extra Zero terms that may result from simplification
     * If all the terms are zeros, then the result will contain a zero.
     * @param simplify HashSet containing all terms to remove zeros
     * @return HashSet without zero terms (Or one zero if all terms were zeros)
     */
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
     * Get the list of simplified terms
     * @return simplified list of operation in Nominals and Fractions
     */
    @Override
    public HashSet<NumberStructure> simplify() {
        return getRootOperator().simplify();
    }


}
