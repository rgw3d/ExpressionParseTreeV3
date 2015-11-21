import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Created by rgw3d on 11/5/2015.
 */
public class Term extends NumberStructure {
    private final HashSet<NumberStructure> Coefficient;
    private final HashSet<Variable> Variables;
    private final Imaginary Imagine;

    public Term(String expression){
        ArrayList<NumberStructure> components = new ArrayList<>();

        //I get a string with no operators, just implied multiplication
        //so, I need to go through the string, and parse negative numbers, numbers, pi, e, and other variables
        for (int i = 0; i < expression.length(); i++) {
            String component = getComponent(expression, i);
            i+=component.length()-1;
            components.add(getNumberStructure(component));
        }

        ArrayList<NumberStructure> updatedComponents = new ArrayList<>();
        //remove negative variables
        for (NumberStructure x : components) {
            if (x instanceof Variable) {
                Variable var = (Variable) x;
                if (var.getCoefficient().equals(new Number(-1))) {
                    updatedComponents.add(new Number(-1));
                    updatedComponents.add(new Variable(var.getVariable(), var.getExponent()));
                    continue;
                }
            }

            updatedComponents.add(x);
        }
        components = updatedComponents;

        Number tempCoefficient = Number.ONE;
        HashSet<Variable> tempVariables = new HashSet<>();
        Imaginary tempImagine = Imaginary.ZERO_EXPONENT;

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

        Coefficient = new HashSet<NumberStructure>(Arrays.asList(tempCoefficient));
        Variables = tempVariables;
        Imagine = tempImagine;


    }

    public Term(HashSet<NumberStructure> coef, HashSet<Variable> var, Imaginary img) {
        if (coef == null)
            coef = new HashSet<NumberStructure>(Arrays.asList(Number.ONE));
        if (var == null)
            var = new HashSet<>();
        if (img == null)
            img = Imaginary.ZERO_EXPONENT;

        Coefficient = coef;
        Variables = var;
        Imagine = img;
    }

    public Term(NumberStructure coef, Variable var, Imaginary img) {
        if (coef == null)
            Coefficient = new HashSet<NumberStructure>(Collections.singletonList(Number.ONE));
        else
            Coefficient = new HashSet<>(Collections.singletonList(coef));
        if (var == null)
            Variables = new HashSet<>();
        else
            Variables = new HashSet<>(Collections.singletonList(var));
        if (img == null)
            Imagine = Imaginary.ZERO_EXPONENT;
        else
            Imagine = img;
    }


    public Term(HashSet<NumberStructure> coef, Variable var, Imaginary img) {
        if (coef == null)
            Coefficient = new HashSet<NumberStructure>(Collections.singletonList(Number.ONE));
        else
            Coefficient = coef;
        if(var == null)
            Variables = new HashSet<>();
        else
            Variables = new HashSet<>(Collections.singletonList(var));
        if(img == null)
            Imagine = Imaginary.ZERO_EXPONENT;
        else
            Imagine = img;
    }

    /**
     * Assues that each term can be added together
     *
     * @param left  left term
     * @param right right term
     * @return combined term
     */
    public static Term add(Term left, Term right) {
        HashSet<NumberStructure> coefficients;
        coefficients = MathOperations.add(left.getCoefficient(), right.getCoefficient());
        return new Term(coefficients, left.getVariable(), left.getImaginary());
    }

    private String getComponent(String expression, int index) {
        String component = "";
        if (expression.charAt(index) == '-') {
            component += "-";
            index++;
        }
        component += expression.charAt(index++);

        for (; index < expression.length(); index++) {
            char charPre = expression.charAt(index - 1);

            if (charPre == 'e' || charPre == 'i')//account for e and i
                break;
            else if (charPre == 'p') {// account for pi
                component += 'i';
                break;
            }
            char charAt = expression.charAt(index);
            boolean charPreIsLetter = Character.isLetter(charPre);
            boolean charAtIsLetter = Character.isLetter(charAt);

            if (charPreIsLetter && !charAtIsLetter) //variable to number
                break;
            else if (!charPreIsLetter && charAtIsLetter && charAt != '.') //number to variable
                break;
            else if (charPreIsLetter && charAtIsLetter && charPre != charAt) //different variable (different letter)
                break;

            component += charAt;
        }
        return component;
    }

    private NumberStructure getNumberStructure(String part) {
        Pattern pattern = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(part);
        if (part.contains("pi") || part.contains("e") || matcher.find())
            return new Number(part);
        else if (part.contains("i"))
            return new Imaginary(part);
        else
            return new Variable(part);
    }

    /**
     *
     * @return double number value
     */
    public HashSet<NumberStructure> getCoefficient() {
        return Coefficient;
    }

    /**
     *
     * @return double Var value
     */
    public HashSet<Variable> getVariable() {
        return Variables;
    }

    public Imaginary getImaginary() {
        return Imagine;
    }

    /**
     * Get the list of simplified terms,
     *
     * @return simplified list of operation in Nominals and Fractions
     */
    @Override
    public HashSet<NumberStructure> simplify() {
        return new HashSet<NumberStructure>(Collections.singletonList(this));
    }
    @Override
    public String toString() {
        String toReturn = "";

        if(Coefficient.size() == 1 && Coefficient.toArray()[0] instanceof Number ){
            Number indx = (Number)Coefficient.toArray()[0];
            if(!indx.equals(Number.ONE))
                toReturn+="("+indx.toString()+")";
        }
        else {
            toReturn += "(";
            for(NumberStructure numberStructure: Coefficient){
                toReturn += numberStructure+"+";
            }
            toReturn = toReturn.substring(0,toReturn.length()-1) + ")";
        }

        if(Variables.size() != 0){
            toReturn +="(";
            for(Variable var: Variables){
                toReturn += var;
            }
            toReturn+=")";
        }

        if (!Imagine.equals(Imaginary.ZERO_EXPONENT)) {
            toReturn += "("+Imagine +")";
        }

        if(toReturn.length() == 0)
            toReturn = "1";
        return toReturn;
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
