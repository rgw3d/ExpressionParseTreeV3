import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * Created by rgw3d on 10/9/2014.
 */
public class Operator implements ExpressionNode {
    private HashSet<ExpressionNode> Terms = null;
    private final ExpressionNode Node1;
    private final ExpressionNode Node2;
    private final char Type;

    /**
     * Primary constructor
     * @param type type of operation (+,*,/,^)
     * @param node1 child in binary tree
     * @param node2 child in binary tree
     */
    public Operator(char type, ExpressionNode node1, ExpressionNode node2){
        Type = type;
        Node1 = node1;
        Node2 = node2;
    }

    @Override
    public String toString() {
        return "("+Node1.toString()+Type+ Node2.toString()+")";
    }

    @Override
    public HashSet<ExpressionNode> simplify(){
        if(Terms == null) {
            switch (Type){
                case '+':
                    Terms = MathOperations.add(Node1.simplify(),Node2.simplify());
                    break;
                case '*':
                    Terms = MathOperations.multiply(Node1.simplify(),Node2.simplify());
                    break;
                case '/':
                    Terms = MathOperations.divide(Node1.simplify(),Node2.simplify());
                    break;
                case '^':
                    Terms = MathOperations.power(Node1.simplify(), Node2.simplify());
            }
        }
        return Terms;
    }
}
