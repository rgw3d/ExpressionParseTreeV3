import java.util.ArrayList;

/**
 * Addition Simplifier.Operator with all the necessary actions to deal with addition
 * Created by rgw3d on 10/9/2014.
 */
public class AdditionOperator extends Operator {
    /**
     * Default constructor
     */
    public AdditionOperator(EquationNode node1, EquationNode node2) {
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
            MathOperations.additionControl(Terms);
        }
        return Terms;
    }
}
