import java.util.ArrayList;

/**
 * Simplifies all exponents
 * Created by rgw3d on 10/9/2014.
 */
public class PowerOperator extends Operator {

    /**
     * Default constructor
     */
    public PowerOperator(EquationNode node1, EquationNode node2) {
        super(node1, node2);
    }

    /**
     * Get the list of simplified terms,
     *
     * @return simplified list of operation in Nominals and Fractions
     */
    @Override
    public ArrayList<EquationNode> evaluate() {
        if(Terms == null){
            //MathOperations.powerControl(Terms);
        }
        return Terms;
    }
}
