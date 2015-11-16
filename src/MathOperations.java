import java.util.*;

/**
 * All the math operations used in each Simplifier.Operator class
 * Created by rgw3d on 10/10/2014.
 */
public class MathOperations {
    /**
     * Add two lists of ExpresionNodes together.
     * This is probably the easiest operation to do,
     * because it is easy to check to see if you can add things together
     * @param left left part of the addition
     * @param right right part of the addition
     * @return new ArrayList containing terms after simplification
     */
    public static HashSet<ExpressionNode> add(HashSet<ExpressionNode> left, HashSet<ExpressionNode> right){
        // strategy is for each node in left array, try to add with each element in the right array.
        // if this is possible, update the element in the left array with the result of the addition, and
        // remove the right array.
        // once every element in the left array has been loop through, put all of them into a new ArrayList, along with the
        // any remaining elements in the right array, and then return this arraylist
        HashSet<ExpressionNode> result = new HashSet<>();
        for (ExpressionNode leftNode: left){
            NumberStructure partialSum = (NumberStructure)leftNode;
            Iterator<ExpressionNode> rightItr = right.iterator();
            while (rightItr.hasNext()){
                NumberStructure rightNode = (NumberStructure)rightItr.next();
                if(canAdd(partialSum, rightNode)){
                    rightItr.remove();
                    partialSum = partialSum.add(rightNode);
                }
            }
            right = copyIteratorToHashSet(rightItr);
            result.add(partialSum);
        }
        for (ExpressionNode node : right){
            result.add(node);
        }

        return result;
    }


    private static boolean canAdd(NumberStructure left, NumberStructure right){
        if(left instanceof Number && right instanceof Number) {
            Number l = (Number) left;
            Number r = (Number) right;
            return l.getPIExponent() == r.getPIExponent() && l.geteExponent() == r.geteExponent();
        }
        if(left instanceof Variable && right instanceof  Variable){
            Variable l = (Variable) left;
            Variable r = (Variable) right;
            return l.getVariable() == r.getVariable() && l.getExponent().equals(r.getExponent());
        }
        if(left instanceof Imaginary && right instanceof  Imaginary){
            Imaginary l = (Imaginary) left;
            Imaginary r = (Imaginary) right;
            return l.getExponent().equals(r.getExponent());
        }
        if(left instanceof Fraction && right instanceof Fraction){
            Fraction l = (Fraction) left;
            Fraction r = (Fraction) right;
            return l.getBottom().equals(r.getBottom());
        }
        if(left instanceof Term && right instanceof Term) {
            Term l = (Term) left;
            Term r = (Term) right;
            return l.getVariable().equals(r.getVariable()) && l.getImaginary().equals(r.getImaginary());
        }
        if(left instanceof Exponent && right instanceof Exponent){
            Exponent l = (Exponent) left;
            Exponent r = (Exponent) right;
            return l.getBase().equals(r.getBase());
        }
        return false;
    }


    public static HashSet<ExpressionNode> multiply(HashSet<ExpressionNode> left, HashSet<ExpressionNode> right){

        return new HashSet<ExpressionNode>();
    }

    public static HashSet<ExpressionNode> divide(HashSet<ExpressionNode> left, HashSet<ExpressionNode> right){

        return new HashSet<ExpressionNode>();
    }

    public static HashSet<ExpressionNode> power(HashSet<ExpressionNode> left, HashSet<ExpressionNode> right){

        return new HashSet<ExpressionNode>();
    }


    public static <T> HashSet<T> copyIteratorToHashSet(Iterator<T> iter) {
        HashSet<T> copy = new HashSet<T>();
        while (iter.hasNext())
            copy.add(iter.next());
        return copy;
    }
}
