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

    /**
     * By default, returns -1
     * Exists to satisfy EquationNode implementation
     * @return double -1
     */
    public double getCoefficient() {
        return -1;
    }

    /**
     * By default, just returns -1
     * Exists to satisfy EquationNode implementation
     * @return double -1
     */
    public double getVariable() {
        return -1;
    }

    /**
     * Default action of the getTop(), returns the list.  essentially just evaluate()
     * @return ArrayList that is also returned from evaluate()
     */
    public ArrayList<EquationNode> getTop(){
        return evaluate();
    }

    /**
     * Default action of the getBottom(), returns a number(0,0) wrapped in an ArrayList
     * @return ArrayList that contains a number(0,0)
     */
    public ArrayList<EquationNode> getBottom(){
        ArrayList<EquationNode> result = new ArrayList<EquationNode>();
        result.add(Number.Zero);
        return result;
    }

    @Override
    public String toString() {
        return Node1.toString()+"+"+ Node2.toString();
    }
}
