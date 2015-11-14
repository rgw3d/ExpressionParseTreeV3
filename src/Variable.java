import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * Created by rgw3d on 11/5/2015.
 */
public class Variable extends NumberStructure{

    private final Number Coefficient;
    private final char Variable;
    private final NumberStructure Exponent;

    public static final Variable ZERO = new Variable(Number.ONE,'x',Number.ZERO);

    @Override
    public String toString() {
        return Variable + "^"+ Exponent.toString();
    }

    /**
     * Constructor called from Term, when Term is parsing input. If used otherwise, results are unexpected
     * @param input input string
     */
    public Variable(String input){
        if(input.startsWith("-")) {
            Coefficient = new Number(-1);
            input = input.substring(1);
        }
        else
            Coefficient = Number.ONE;

        Variable = input.charAt(0);//variable should be the first one
        Exponent = new Number(input.length());
    }

    public Variable(Number coefficient, char variable, NumberStructure exponent){
        if(coefficient == null)
            this.Coefficient = Number.ONE;
        else
            this.Coefficient = coefficient;

        this.Variable = variable;

        if(exponent == null)
            this.Exponent = Number.ONE;
        else
            this.Exponent = exponent;
    }

    public Number getCoefficient() {
        return Coefficient;
    }

    public char getVariable() {
        return Variable;
    }

    public NumberStructure getExponent() {
        return Exponent;
    }

    public static void multiply(ArrayList<Variable> list, Variable var){
        for(Variable component: list){
            if(var.getVariable() == component.getVariable()){ //same variable
                Number coef = Number.multiply(var.getCoefficient(), component.getCoefficient());//get multiplied coefficient
                NumberStructure exp = Number.add((Number)var.getExponent(), (Number)component.getExponent());//TODO implement addition of everything
                list.remove(component);
                list.add(new Variable(coef, var.getVariable(), exp)); //add new variable
                return;
            }
        }
        list.add(var);
    }

    /**
     * Get the list of simplified terms,
     *
     * @return simplified list of operation in Nominals and Fractions
     */
    @Override
    public ArrayList<ExpressionNode> simplify() {
        return new ArrayList<ExpressionNode>(Arrays.asList(this));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Variable)) return false;

        Variable variable1 = (Variable) o;

        if (Variable != variable1.Variable) return false;
        if (!Coefficient.equals(variable1.Coefficient)) return false;
        return Exponent.equals(variable1.Exponent);

    }

    @Override
    public int hashCode() {
        int result = Coefficient.hashCode();
        result = 31 * result + (int) Variable;
        result = 31 * result + Exponent.hashCode();
        return result;
    }
}
