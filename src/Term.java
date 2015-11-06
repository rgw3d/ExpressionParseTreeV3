import java.util.ArrayList;

/**
 *
 * Created by rgw3d on 11/5/2015.
 */
public class Term implements EquationNode {
    private final Rational COEFFICIENT;
    private final ArrayList<Variable> VARIABLES;
    private final Imaginary IMAGINARY;

    /**
     * gets the number value for the Simplifier.EquationNode
     *
     * @return double number value
     */
    @Override
    public double getCoefficient() {
        return 0;
    }

    /**
     * gets the variable value for the Simplifier.EquationNode
     *
     * @return double Var value
     */
    @Override
    public ArrayList<Variable> getVariable() {
        return null;
    }

    /**
     * Get the list of simplified terms,
     *
     * @return simplified list of operation in Nominals and Fractions
     */
    @Override
    public ArrayList<EquationNode> evaluate() {
        return null;
    }

    /**
     * Treats everything as a fraction. Gets the "top".
     * Usually the top is just the evaluate() method.
     *
     * @return ArrayList of simplified "top" terms
     */
    @Override
    public ArrayList<EquationNode> getTop() {
        return null;
    }

    /**
     * Treats everything as a fraction. Gets the "bottom".
     * Usually the bottom is just a nominal with getCoefficient()=1 and getVariable()=0
     *
     * @return ArrayList of simplified "bottom" terms
     */
    @Override
    public ArrayList<EquationNode> getBottom() {
        return null;
    }
}
