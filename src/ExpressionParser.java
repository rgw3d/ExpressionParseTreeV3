/**
 * Used to parse equation.  will return one Simplifier.ExpressionNode.  This means that
 * Created by rgw3d on 10/9/2014.
 */
public class ExpressionParser {
    private final String Input;

    public ExpressionParser(String input ) {
        Input = input;
    }

    public ExpressionNode parseEquation() throws InputException {
        return parseEquation(Input);
    }

    private ExpressionNode parseEquation(String expression) throws InputException {
        char[] operations = new char[]{'+','*','/','^'};

        boolean hasParenthesis = false;
        for(char op : operations){
            if(!expression.contains(op+"")){//just skip this iteration if there is not this operator
                continue;
            }
            for (int i = 0; i < expression.length() ; i++) {
                char charAt = expression.charAt(i);
                if(charAt == '('){
                    i = skipParen(expression, i);
                    hasParenthesis = true;
                }
                else if(charAt == op){
                    ExpressionNode node1 = parseEquation(expression.substring(0,i));
                    ExpressionNode node2 = parseEquation(expression.substring(i + 1));

                    return new Operator(op,node1,node2);
                }
            }
        }
        //did not find an operator
        if(hasParenthesis) {
            //if(expression.startsWith("-"))
            return parseEquation(expression.substring(1, expression.length() - 1));
        }
        else
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

