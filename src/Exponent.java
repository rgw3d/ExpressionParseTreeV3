import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 *
 * Created by rgw3d on 11/6/2015.
 */
public class Exponent extends NumberStructure {

    private final NumberStructure Base;
    private final NumberStructure Exponent;

    public Exponent(NumberStructure base, NumberStructure exponent){
        Base = base;
        Exponent = exponent;
    }

    public NumberStructure getBase() {
        return Base;
    }

    public NumberStructure getExponent() {
        return Exponent;
    }

    /**
     * Assumes that you can add each exponent together (they have the same base)
     * @param left
     * @param right
     * @return
     */
    public static Exponent add(Exponent left, Exponent right){
        HashSet<NumberStructure> exp = MathOperations.add(new HashSet<NumberStructure>(Collections.singletonList(left.getExponent())),new HashSet<NumberStructure>(Collections.singletonList(right.getExponent())));
        if(exp.size() == 1){
            NumberStructure indx = exp.toArray(new NumberStructure[1])[0];
            return new Exponent(left.getBase(), indx);
        }
        else{
            return new Exponent(left.getBase(),new Term(exp,new HashSet<Variable>(),null));
        }

    }
    /**
     * Get the list of simplified terms,
     *
     * @return simplified list of operation in Nominals and Fractions
     */
    @Override
    public HashSet<NumberStructure> simplify() {
        return new HashSet<NumberStructure>(Collections.singletonList(this));
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Exponent)) return false;

        Exponent exponent = (Exponent) o;

        if (!Base.equals(exponent.Base)) return false;
        return Exponent.equals(exponent.Exponent);

    }

    @Override
    public int hashCode() {
        int result = Base.hashCode();
        result = 31 * result + Exponent.hashCode();
        return result;
    }
}
