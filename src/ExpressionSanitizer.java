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
    private final char[] Variables;

    public ExpressionSanitizer(String input) throws InputException {
        if (input.equalsIgnoreCase(QUIT_KEYWORD))
            throw new InputException(QUIT_KEYWORD);

        isEquation(input);//checks for input errors

        Variables = extractVariables(input);
        ReformattedExpression = reformatInput(input);
    }

    public String getReformattedExpression() {
        return ReformattedExpression;
    }

    public char[] getVariables() {
        return Variables;
    }

    /**
     * Determines if the input string is appropriate to parse
     * If there is a problem, throws an InputException error.
     *
     * @param input the string to be tested
     */
    public void isEquation(String input) throws InputException {
        if (!(input.length() >= 3)) { //to short
            throw new InputException(ERROR_PREFIX + "Too Short to be considered an expression");
        }
        if (!(input.contains("+") || input.contains("*") || input.contains("/") || input.contains("^") || input.contains("("))) {
            throw new InputException(ERROR_PREFIX + "Does not contain an operator");
        }

        String endOfEq = input.charAt(input.length() - 1) + "";//ends bad
        if (endOfEq.equals("+") || endOfEq.equals("-") || endOfEq.equals("*") || endOfEq.equals("/")) {
            throw new InputException(ERROR_PREFIX + "Ends with: " + endOfEq);
        }

        String beginOfEq = input.charAt(0) + ""; //starts bad
        if (beginOfEq.equals("+") || beginOfEq.equals("*") || beginOfEq.equals("/")) {
            throw new InputException(ERROR_PREFIX + "Starts with " + beginOfEq);
        }

        Pattern p = Pattern.compile("[^(0-9,a-z*,/,\\-,\\.,+,\\^,\\s)]");
        Matcher m = p.matcher(input);
        if (m.find()) {//detects illegal character
            throw new InputException(ERROR_PREFIX + "Illegal Character: " + m.group());
        }

        p = Pattern.compile("[\\+,/,\\^,\\*]{2,}");
        m = p.matcher(input);
        if (m.find()) {
            throw new InputException(ERROR_PREFIX + "Two or more of an operator: " + m.group());
        }

        parenthesisCheck(input);

        //good syntax if no errors are thrown.
    }

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
                    "\tClosded: " + closedCount);
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

        input = input.replace(")(", ")*(");//multiply by parenthesis

        input = inferMultiplication(input);//for situations like this:  3(x+1) or (x^2-1)33, where there are parentheses and numbers touching

        input = input.replace("++-", "+-");//for the longest time I didn't spot this. I assumed that everyone would put in -, and not +-

        if (input.startsWith("+-"))
            input = input.substring(1);//can't start with a +. Happens because of above replacements

        return input;
    }

    public String inferMultiplication(String input) {
        String result = "";
        String pattern = "[0-9pie";
        for(char c: getVariables()){
            pattern+=c;
        }
        pattern+="]";


        //for(char c: getVariables()){

        //}
        Pattern p = Pattern.compile(pattern);
        for (int i = 0; i + 1 < input.length(); i++) {
            result += input.charAt(i);
            if ((p.matcher(input.charAt(i) + "").find() && input.charAt(i + 1) == '(') ||
                    (input.charAt(i) == ')' && p.matcher(input.charAt(i + 1) + "").find())) {
                result += "*";
            }
        }
        result += input.charAt(input.length() - 1);

        return result;
    }
}