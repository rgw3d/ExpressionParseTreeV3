import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Input/ input sanitation class for EquationParseTree
 * Created by rgw3d on 10/9/2014.
 */
public class ExpressionSanitizer {

    public final static String QUIT_KEYWORD = "quit";
    private final static String ERROR_PREFIX = "Bad Expression. Please Revise\n";

    private final String ReformattedExpression;
    private final String InputExpression;
    private final char[] Variables;

    /**
     * Constructor. Sanitizes an input expression, pulls Variables out and produces a Reformatted Expression
     *
     * @param input String to Sanitize
     * @throws InputException if there is a problem with the string, an InputException will be thrown
     */
    public ExpressionSanitizer(String input) throws InputException {
        InputExpression = input;
        if (input.equalsIgnoreCase(QUIT_KEYWORD))
            throw new InputException(QUIT_KEYWORD);

        isValidExpression(input);//checks for input errors

        Variables = extractVariables(input);//pull out all variables
        ReformattedExpression = reformatInput(input);//Reformat into parser friendly syntax
    }

    /**
     * Determines if the input string is appropriate to parse
     * If there is a problem, throws an InputException error.
     *
     * @param input the string to be tested
     */
    public void isValidExpression(String input) throws InputException {

        //Determine if the expression ends with an operation
        String endOfEq = input.charAt(input.length() - 1) + "";
        if (endOfEq.equals("+") || endOfEq.equals("-") || endOfEq.equals("*") || endOfEq.equals("/")) {
            throw new InputException(ERROR_PREFIX + "Ends with: " + endOfEq);
        }

        //Determine if the expression starts with an operation
        String beginOfEq = input.charAt(0) + "";
        if (beginOfEq.equals("+") || beginOfEq.equals("*") || beginOfEq.equals("/") || beginOfEq.equals("^")) {
            throw new InputException(ERROR_PREFIX + "Starts with " + beginOfEq);
        }

        //Determine if the expression has an "illegal" character
        //Find all characters that are not: 0-9, a-z, /, -, +, ^, ' '
        Pattern p = Pattern.compile("[^(0-9,a-z*,/,\\-,\\.,+,\\^,\\s)]");
        Matcher m = p.matcher(input);
        if (m.find()) {
            throw new InputException(ERROR_PREFIX + "Illegal Character: " + m.group());
        }

        //Determine if the expression has repeat operators: 5**6 or 7^^3 or 9//3 are not allowed
        p = Pattern.compile("[\\+,/,\\^,\\*]{2,}");
        m = p.matcher(input);
        if (m.find()) {
            throw new InputException(ERROR_PREFIX + "Two or more of an operator: " + m.group());
        }

        //Determine if the expression is using p as a variable. This is not allowed. The expression must contain "pi" if it contains "p"
        if (input.contains("p")) {
            if (!input.contains("pi")) {//cannot find pattern "pi"
                throw new InputException(ERROR_PREFIX + "\"p\" cannot be used as a variable");
            }
        }

        parenthesisCheck(input);

        //good syntax if no errors are thrown.
    }

    /**
     * Extract the variables from an expression
     * This is done by matching all letters, and then removing p and i and e (removing pi and i and e -- non variables)
     *
     * @param input Input Expression
     * @return char[] containing all variables in this expression
     */
    public char[] extractVariables(String input){
        Pattern p = Pattern.compile("[a-z]*");
        Matcher m = p.matcher(input);
        String output = "";
        while(m.find()) {
            output += m.group();
        }
        return output.replace("p","").replace("i","").replace("e","").toCharArray();

    }

    /**
     * If parenthesis line up, then no error is thrown.
     * If there is an problem, an error is thrown describing the problem.
     *
     * @param input the string to be tested
     */
    public void parenthesisCheck(String input) throws InputException {
        int openCount = 0;
        int closedCount = 0;
        for (int indx = 0; indx < input.length(); indx++) {
            if ((input.charAt(indx) + "").equals(")"))
                closedCount++;//increment closed count
            if ((input.charAt(indx) + "").equals("("))
                openCount++;//increment open count
        }

        if (!(openCount == closedCount)) {
            throw new InputException(ERROR_PREFIX + "Uneven amount of parenthesis\n" +
                    "\tOpen: " + openCount + "\n" +
                    "\tClosed: " + closedCount);
        }

    }

    /**
     * @param input the string to reformat so that the parser can easily parse it
     * @return returns the "fixed"  string
     */
    public String reformatInput(String input) {

        input = input.replace(" ", "");//Getting rid of spaces

        input = input.replace("--", "+"); //minus a minus is addition.  make it simple

        input = input.replace("-", "+-");  //replace a negative with just plus a minus.

        input = input.replace("^+-", "^-"); //common error that happens after one of the above methods run. negative exponents

        input = input.replace("*+-", "*-"); //common error that happens if multiplying by a negative

        input = input.replace("(+-", "(-"); //common error that happens if multiplying by a negative

        input = input.replace("-(", "-1*("); //error when subtracting parenthesis

        input = input.replace(")(", ")*(");//multiply by parenthesis

        input = inferParenthesisMultiplication(input);//for situations like this:  3(x+1) or (x^2-1)33, where there are parentheses, variables or numbers touching

        input = input.replace("++-", "+-");//for the longest time I didn't spot this. I assumed that everyone would put in -, and not +-

        if (input.startsWith("+-"))
            input = input.substring(1);//can't start with a +. Happens because of above replacements

        return input;
    }

    /**
     * Infer parenthesis multiplication, allowing for easier to parse syntax
     * For situations like this: 3(x+1) or (x^2-1)33 or pi(x-2) or e(2+1) or y(x+z)
     *
     * Strategy: Create a pattern containing all numbers, p, i, e, and all variables
     *          Create a variable result
     *          Loop though all but the last character of input
     *              Concat the current character to result
     *              if the current character is contained in the pattern and the next character is '(' OR:
     *                  the current character is ')' and the next character is contained in the pattern
     *              then concat "*" to result
     *          Add the last character of the input string to result
     *          return result
     *
     * @param input String input containing expression to infer multiplication on
     * @return String containing inferred parenthesis multiplication
     */
    public String inferParenthesisMultiplication(String input) {
        String result = "";
        String pattern = "0123456789pie";
        pattern += String.valueOf(getVariables());

        for (int i = 0; i + 1 < input.length(); i++) {
            result += input.charAt(i);
            if (((pattern.contains(input.charAt(i) + "") && input.charAt(i + 1) == '(')) ||
                    (input.charAt(i) == ')' && pattern.contains(input.charAt(i + 1) + ""))) {
                result += "*";
            }
        }
        result += input.charAt(input.length() - 1);//add last character of input string

        return result;
    }

    public String getReformattedExpression() {
        return ReformattedExpression;
    }

    public char[] getVariables() {
        return Variables;
    }

    public String getInputExpression() {
        return InputExpression;
    }

}