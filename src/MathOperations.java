import java.util.HashSet;
import java.util.Iterator;

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
    public static HashSet<NumberStructure> add(HashSet<NumberStructure> left, HashSet<NumberStructure> right){
        // strategy is for each node in left array, try to add with each element in the right array.
        // if this is possible, update the element in the left array with the result of the addition, and
        // remove the right array.
        // once every element in the left array has been loop through, put all of them into a new ArrayList, along with the
        // any remaining elements in the right array, and then return this arraylist
        HashSet<NumberStructure> result = new HashSet<>();
        for (NumberStructure leftNode: left){
            NumberStructure partialSum = leftNode;
            HashSet<NumberStructure> toRemove = new HashSet<>();
            for (NumberStructure rightNode : right) {
                NumberStructure tmp = add(partialSum,rightNode);
                if(tmp != null) {
                    toRemove.add(rightNode);
                    partialSum = tmp;
                }
            }
            for (NumberStructure x : toRemove) {
                right.remove(x);
            }
            result.add(partialSum);
        }
        for (NumberStructure node : right){
            result.add(node);
        }

        return result;
    }

    /**
     * If they can be added together, than they are added together. If not, then null is returned
     * @param left left NumberStructure to add
     * @param right right NumberStructure to add
     * @return
     */
    private static NumberStructure add(NumberStructure left, NumberStructure right){
        if(left instanceof Number && right instanceof Number) {
            Number l = (Number) left;
            Number r = (Number) right;
            if(l.getPIExponent() == r.getPIExponent() && l.geteExponent() == r.geteExponent()){
                return Number.add(l,r);
            }
        }
        if(left instanceof Variable && right instanceof  Variable){
            Variable l = (Variable) left;
            Variable r = (Variable) right;
            if(l.getVariable() == r.getVariable() && l.getExponent().equals(r.getExponent())){
                return Variable.add(l,r);
            }
        }
        if(left instanceof Imaginary && right instanceof  Imaginary){
            Imaginary l = (Imaginary) left;
            Imaginary r = (Imaginary) right;
            if(l.getExponent().equals(r.getExponent())){
                return Imaginary.add(l,r);
            }
        }
        if(left instanceof Fraction && right instanceof Fraction){
            Fraction l = (Fraction) left;
            Fraction r = (Fraction) right;
            if(l.getBottom().equals(r.getBottom())){
                //TODO Implement
            }
        }
        if(left instanceof Term && right instanceof Term) {
            Term l = (Term) left;
            Term r = (Term) right;
            if(l.getVariable().equals(r.getVariable()) && l.getImaginary().equals(r.getImaginary())){
                return Term.add(l,r);
            }
        }
        if(left instanceof Exponent && right instanceof Exponent){
            Exponent l = (Exponent) left;
            Exponent r = (Exponent) right;
            if(l.getBase().equals(r.getBase())){
                return Exponent.add(l,r);
            }
        }
        if((left instanceof Variable && right instanceof Term) || (left instanceof  Term && right instanceof Variable)){ //we could add a variable to a term
            Variable l = (Variable)((left instanceof Variable)?left:right);
            Term r = (Term)((left instanceof Term)?left:right);

            if(r.getVariable().size() == 1 && r.getVariable().toArray(new Variable[1])[0].equals(l) && r.getImaginary().equals(Imaginary.ZERO)){//must only be 1 variuable
                return Variable.add(l,r);
            } //TODO probably a problem with coefficient in Variable
        }
        //TODO ability to add differnt types together

        return null;
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


    public static HashSet<NumberStructure> multiply(HashSet<NumberStructure> left, HashSet<NumberStructure> right){

        return new HashSet<NumberStructure>();
    }

    public static HashSet<NumberStructure> divide(HashSet<NumberStructure> left, HashSet<NumberStructure> right){

        return new HashSet<NumberStructure>();
    }

    public static HashSet<NumberStructure> power(HashSet<NumberStructure> left, HashSet<NumberStructure> right){

        return new HashSet<NumberStructure>();
    }


    public static <T> HashSet<T> copyIteratorToHashSet(Iterator<T> iter) {
        HashSet<T> copy = new HashSet<T>();
        while (iter.hasNext())
            copy.add(iter.next());
        return copy;
    }
}
