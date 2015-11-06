import java.util.ArrayList;
import java.util.Set;

/**
 *
 * Created by rgw3d on 11/5/2015.
 */
public class Variable extends NumberStructure{
    private final char variable;
    private final NumberStructure exponent;

    public char getVariable() {
        return variable;
    }

    public NumberStructure getExponent() {
        return exponent;
    }

    @Override
    public String toString() {
        return variable + "^"+ exponent.toString();
    }

    public Variable(char variable, NumberStructure exponent){
        this.variable = variable;
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


}
