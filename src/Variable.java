import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * Created by rgw3d on 11/5/2015.
 */
public class Variable extends NumberStructure{

    private final NumberStructure coefficient;
    private final char variable;
    private final NumberStructure exponent;
    public static final ArrayList<Variable> DEFAULT_VARIABLE_LIST = new ArrayList<Variable>(Arrays.asList(new Variable(NumberStructure.Number.One, 'x', NumberStructure.Number.Zero)));

    @Override
    public String toString() {
        return variable + "^"+ exponent.toString();
    }

    public Variable(NumberStructure coefficient, char variable, NumberStructure exponent){
        if(coefficient == null)
            this.coefficient = NumberStructure.Number.One;
        else
            this.coefficient = coefficient;

        this.variable = variable;

        if(exponent == null)
            this.exponent = NumberStructure.Number.One;
        else
            this.exponent = exponent;
    }

    /**
     * Get the list of simplified terms,
     *
     * @return simplified list of operation in Nominals and Fractions
     */
    @Override
    public ArrayList<EquationNode> evaluate() {
        ArrayList<EquationNode> result = new ArrayList<EquationNode>();
        result.add(this);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Variable)) return false;

        Variable variable1 = (Variable) o;

        if (variable != variable1.variable) return false;
        if (!coefficient.equals(variable1.coefficient)) return false;
        return exponent.equals(variable1.exponent);

    }

    @Override
    public int hashCode() {
        int result = coefficient.hashCode();
        result = 31 * result + (int) variable;
        result = 31 * result + exponent.hashCode();
        return result;
    }
}
