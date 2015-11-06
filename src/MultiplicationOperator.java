import java.util.ArrayList;

/**
 * Multiplication operator with all the actions to deal with Multiplication
 * Created by rgw3d on 10/9/2014.
 */
public class MultiplicationOperator extends Operator {

    /**
     * Secondary constructor
     *
     * @param node1 child in binary tree
     * @param node2 child in binary tree
     */
    public MultiplicationOperator(EquationNode node1, EquationNode node2) {
        super(node1,node2);
    }

    /**
     * Get the list of simplified terms,
     *
     * @return simplified list of operation in Nominals and Fractions
     */
    @Override
    public ArrayList<EquationNode> evaluate() {
        if (Terms == null) {
            MathOperations.multiplicationControl(Terms);
        }
        return Terms;
    }
}
