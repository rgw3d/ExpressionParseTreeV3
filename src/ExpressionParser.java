/**
 * Used to parse equation.  will return one Simplifier.ExpressionNode.  This means that
 * Created by rgw3d on 10/9/2014.
 */
public class ExpressionParser {
    private final String Input;

    /**
     * Constructor. Accepts the input string to parse
     * Does not do any parsing until told to
     *
     * @param input Expression string to input
     */
    public ExpressionParser(String input ) {
        Input = input;
    }

    /**
     * Helper function used to parse Input expression
     * @return ExpressionNode that is the root of a parse tree
     * @throws InputException if expression is un-parsable
     */
    public ExpressionNode parseEquation() throws InputException {
        return parseEquation(Input);
    }

    /**
     * Recursive builds a complete binary parse tree.
     * Leaves on the tree are actual numbers/variables.
     * Internal nodes are operations.
     *
     * Strategy for Parsing:
     *      Search through the expression for operations in this order: {+,*,/,^}
     *          Why? This puts the earlier operation higher in the parse tree
     *          When operations are higher in the parse tree, they are done later when simplify() is called on
     *          the root of the parse tree. Simplify() is a Depth first operation, reaching the leaves of the tree
     *          first, and working its way up the tree. This fits the PEMDAS order of operations.
     *          If there is an operation found, then create an internal node with Left and Right children
     *          being the result of splitting the expression at that point
     *      If no operation is found:
     *          If there are parenthesis, parse/recurse inside the parenthesis
     *          Else, there is a term/number/variable that needs to be parsed. This is a leaf node
     *
     * @param expression Input expression to be parsed
     * @return ExpressionNode that is the root of a parse tree
     * @throws InputException if expression is un-parsable
     */
    private ExpressionNode parseEquation(String expression) throws InputException {
        char[] operations = new char[]{'+','*','/','^'};

        boolean hasParenthesis = false;
        for(char op : operations){
            if(!expression.contains(op+"")){//just skip this iteration if there is not this operator
                continue;
            }
            for (int i = 0; i < expression.length(); i++) {//Loop through entire expression
                char charAt = expression.charAt(i);
                if (charAt == '(') {//Need to skip over parenthesis
                    i = skipParen(expression, i);//jump over the parenthesis
                    hasParenthesis = true;
                } else if (charAt == op) {//Found an operation. Time to recurse on each part
                    ExpressionNode node1 = parseEquation(expression.substring(0, i));//Left side recursive call
                    ExpressionNode node2 = parseEquation(expression.substring(i + 1));//Right side recursive call

                    return new Operator(op, node1, node2);//create internal node that is an operator
                }
            }
        }
        //did not find an operator
        if (hasParenthesis) {//Need to continue parsing inside of the parenthesis
            //Do not need to check if there is a '-' outside of the parenthesis, because ExpressionSanitizer
            //should turn -(.....) into -1*(.....)
            return parseEquation(expression.substring(1, expression.length() - 1));//Cut of parenthesis and parse
        } else//Reached a Term/Number/Variable
            return new Term(expression);
    }

    /**
     * Used to skip over parenthesis -->
     * then it goes through the string until it completes the original outside parenthesis.
     *
     * @param input string current string being parsed
     * @param indx  int current indx
     * @return int index of the string where the parenthesis has been skipped
     */
    private int skipParen(String input, int indx) throws InputException {
        int openCount = 1;
        int closedCount = 0;
        while (indx < input.length()) {
            indx++;
            if ((input.charAt(indx) + "").equals(")"))
                closedCount++;//increment closed count

            if ((input.charAt(indx) + "").equals("("))
                openCount++;//increment open count

            if (openCount == closedCount)
                return indx;
        }

        throw new InputException("Parsing Failed - Missing Parenthesis Pair");
    }

}

