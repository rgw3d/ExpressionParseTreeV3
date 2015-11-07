import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * Created by rgw3d on 11/5/2015.
 */
public class Variable extends NumberStructure{

    private final NumberStructure Coefficient;
    private final char Variable;
    private final NumberStructure Exponent;
    public static final ArrayList<Variable> DEFAULT_VARIABLE_LIST = new ArrayList<Variable>(Arrays.asList(new Variable(Number.ONE, 'x', Number.ZERO)));

    @Override
    public String toString() {
        return Variable + "^"+ Exponent.toString();
    }

    public Variable(NumberStructure coefficient, char variable, NumberStructure exponent){
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

    /**
     * Get the list of simplified terms,
     *
     * @return simplified list of operation in Nominals and Fractions
     */
    @Override
    public ArrayList<EquationNode> evaluate() {
        return new ArrayList<EquationNode>(Arrays.asList(this));
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
