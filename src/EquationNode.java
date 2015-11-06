import java.util.ArrayList;

/**
 * Parent interface to everything in this project
 * provides methods for all subclasses
 * Created by rgw3d on 11/5/2015.
 */
public interface EquationNode {
    /**
     * gets the number value for the Simplifier.EquationNode
     * @return double number value
     */
    public Rational getCoefficient();//returns the number from the operation

    /**
     * gets the variable value for the Simplifier.EquationNode
     * @return double Var value
     */
    public ArrayList<Variable> getVariable();//returns the variable from the operation

    public Imaginary getImaginary();

    /**
     * Get the list of simplified terms,
     * @return simplified list of operation in Nominals and Fractions
     */
    public ArrayList<EquationNode> evaluate();

}
