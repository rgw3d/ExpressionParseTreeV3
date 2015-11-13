import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Created by rgw3d on 11/5/2015.
 */
public class Term extends NumberStructure {
    private final ArrayList<NumberStructure> Coefficient;
    private final ArrayList<Variable> Variables;
    private final Imaginary Imagine;

    public Term(String expression){
        ArrayList<NumberStructure> components = new ArrayList<NumberStructure>();

        //I get a string with no operators, just implied multiplication
        //so, I need to go through the string, and parse negative numbers, numbers, pi, e, and other variables
        for (int i = 0; i < expression.length(); i++) {
            String component = getComponent(expression, i);
            i+=component.length()-1;
            components.add(getNumberStructure(component));
        }

        ArrayList<NumberStructure> tempCoefficients = new ArrayList<NumberStructure>();
        Number tempCoefficient = Number.ONE;
        ArrayList<Variable> tempVariables = new ArrayList<Variable>();
        Imaginary tempImagine = Imaginary.ZERO;

        for(NumberStructure comp : components){
            if(comp instanceof Number){
                tempCoefficient = Number.multiply(tempCoefficient,(Number)comp);
            }
            else if(comp instanceof Variable){
                Variable.multiply(tempVariables, (Variable)comp);
            }
            else{
                tempImagine = Imaginary.multiply(tempImagine, (Imaginary)comp);
            }

        }

        Coefficient = new ArrayList<NumberStructure>(Arrays.asList(tempCoefficient));
        Variables = tempVariables;
        Imagine = tempImagine;


    }

    private String getComponent(String expression, int index){
        String component = "";
        if(expression.charAt(index) == '-') {
            component += "-";
            index++;
        }
        component+=expression.charAt(index++);

        for (; index < expression.length(); index++) {
            char charPre = expression.charAt(index-1);

            if(charPre == 'e' || charPre == 'i')//account for e and i
                break;
            else if(charPre == 'p'){// account for pi
                component+='i';
                break;
            }
            char charAt = expression.charAt(index);
            boolean charPreIsLetter = Character.isLetter(charPre);
            boolean charAtIsLetter = Character.isLetter(charAt);

            if(charPreIsLetter && !charAtIsLetter) //variable to number
                break;
            else if(!charPreIsLetter && charAtIsLetter && charAt != '.') //number to variable
                break;
            else if(charPreIsLetter && charAtIsLetter && charPre != charAt) //different variable (different letter)
                break;

            component += charAt;
        }
        return component;
    }

    private NumberStructure getNumberStructure(String part){
        Pattern pattern = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(part);
        if(part.contains("pi") || part.contains("e") || matcher.find())
            return new Number(part);
        else if(part.contains("i"))
            return new Imaginary(part);
        else
            return new Variable(part);
    }


    public Term(ArrayList<NumberStructure> coef, ArrayList<Variable> var, Imaginary img){
        if(coef == null)
            coef = new ArrayList<NumberStructure>(Arrays.asList(Number.ONE));
        if(var == null)
            var = new ArrayList<Variable>();
        if(img == null)
            img = Imaginary.ZERO;

        Coefficient = coef;
        Variables = var;
        Imagine = img;
    }

    /**
     *
     * @return double number value
     */
    public ArrayList<NumberStructure> getCoefficient() {
        return Coefficient;
    }

    /**
     *
     * @return double Var value
     */
    public ArrayList<Variable> getVariable() {
        return Variables;
    }

    public NumberStructure getImaginary() {
        return Imagine;
    }

    /**
     * Get the list of simplified terms,
     *
     * @return simplified list of operation in Nominals and Fractions
     */
    @Override
    public ArrayList<ExpressionNode> evaluate() {
        return new ArrayList<ExpressionNode>(Arrays.asList(this));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Term)) return false;

        Term term = (Term) o;

        if (!Coefficient.equals(term.Coefficient)) return false;
        if (!Variables.equals(term.Variables)) return false;
        return Imagine.equals(term.Imagine);

    }

    @Override
    public int hashCode() {
        int result = Coefficient.hashCode();
        result = 31 * result + Variables.hashCode();
        result = 31 * result + Imagine.hashCode();
        return result;
    }
}
