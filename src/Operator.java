import java.util.ArrayList;

/**
 * Parent to all of the Simplifier.Operator classes
 * Deals with getVariable() getCoefficient() methods that are not used in the operator classes.
 * Provides addTerm()
 * Created by rgw3d on 10/9/2014.
 */
public abstract class Operator implements EquationNode {
    protected ArrayList<EquationNode> Terms = null;
    protected final EquationNode Node1;
    protected final EquationNode Node2;

    /**
     * Primary constructor
     * @param node1 child in binary tree
     * @param node2 child in binary tree
     */
    Operator(EquationNode node1, EquationNode node2){
        Node1 = node1;
        Node2 = node2;
    }

    @Override
    public String toString() {
        return Node1.toString()+"+"+ Node2.toString();
    }
}
