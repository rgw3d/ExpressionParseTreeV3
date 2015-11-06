import java.util.ArrayList;

/**
 * Division Simplifier.Operator.
 * This operator handles all the division and makes fractions
 * Created by rgw3d on 10/9/2014.
 */
public class DivisionOperator extends Operator {
    /**
     * Default constructor
     * @param node1 child in binary tree
     * @param node2 child in binary tree
     */
    public DivisionOperator(EquationNode node1, EquationNode node2) {
        super(node1, node2);
    }

    /**
     * Get the list of simplified terms,
     *
     * @return simplified list of operation in Nominals and Fractions
     */
    @Override
    public ArrayList<EquationNode> evaluate() {
        if(Terms == null) {
            MathOperations.divisionControl(Terms);
        }
        return Terms;
    }
}
