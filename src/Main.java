import java.util.Scanner;

public class Main {

    /**
     * so I want to accept a string
     * and just send it off to the intermediaary object that will handle pparsing
     * and have all of the wrapper methods that will be used to calculate whatever we want
     *
     * @param args
     */

    public static void main(String[] args) {

        System.out.println("Enter an expression to see it simplified");
        System.out.println("\tPI and E can be approximated.  Type pi for PI and e for E");
        System.out.println("\tType \""+ExpressionSanitizer.QUIT_KEYWORD+"\" to quit");

        String input;
        Expression expression;
        do {
            System.out.print("Enter Expression:  ");
            input = new Scanner(System.in).nextLine().toLowerCase();
            try {
                expression = new Expression(input);
                System.out.println(expression.getSimplifiedExpression());
            } catch (InputException ie) {
                if (ie.getMessage().equals(ExpressionSanitizer.QUIT_KEYWORD))//error message to stop the loop
                    input = null;
                else //the exception message if we are not stopping
                    System.out.println(ie.getMessage() + "\n");
                continue;//after the error message jump to the end of the loop
            }

            //ArrayList<ExpressionNode> result = simplifyExpression(input);
            //System.out.println("\tResult: " + printSimplifiedExpression(result));//get the formatted result here



        } while (input != null);
    }
}
