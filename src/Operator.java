import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *
 * Created by rgw3d on 10/9/2014.
 */
public class Operator implements ExpressionNode {
    private ArrayList<ExpressionNode> Terms = null;
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
        return Node1.toString()+Type+ Node2.toString();
    }

    @Override
    public ArrayList<ExpressionNode> evaluate(){
        if(Terms == null) {
//            MathOperations.additionControl(Terms);
        }
        //else if(Type == '+')
            //return

        return Terms;
    }
}
