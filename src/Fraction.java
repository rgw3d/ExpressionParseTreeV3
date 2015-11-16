import java.util.Arrays;
import java.util.HashSet;

/**
 * Simplifier.NumberStructurefier.NumberStructure used to represent rational fractions.
 * Created by rgw3d on 10/9/2014.
 */
public class Fraction extends NumberStructure<Fraction> {
    private final HashSet<NumberStructure> Top;
    private final HashSet<NumberStructure> Bottom;

    public static final HashSet<NumberStructure> DEFAULT_LIST = new HashSet<NumberStructure>(Arrays.asList(Number.ONE));

    /**
     * Primary constructor
     * @param top ArrayList to set as the top value
     * @param bottom ArrayList to set as bottom value
     */
    public Fraction(HashSet<NumberStructure> top, HashSet<NumberStructure> bottom) {
        if(top == null)
            top = DEFAULT_LIST;
        else if (top.size() == 0 ){
            top = DEFAULT_LIST;
        }
        Top = top;

        if(bottom == null){
            bottom = DEFAULT_LIST;
        }
        else if(bottom.size() == 1){//if it is zero, then the zero has been removed and it is a divide by zero error
            NumberStructure indx = (NumberStructure)bottom.toArray()[0];
            if(indx.equals(Number.ZERO))
                throw new IllegalArgumentException("Error: Divisor is 0");
        }
        Bottom = bottom;

    }

    public HashSet<NumberStructure> getTop() {
        return Top;
    }

    public HashSet<NumberStructure> getBottom() {
        return Bottom;
    }

    /**
     * returns the Top list
     * @return ArrayList of top
     */
    @Override
    public HashSet<ExpressionNode> simplify() {
        return new HashSet<ExpressionNode>(Arrays.asList(this));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fraction)) return false;

        Fraction fraction = (Fraction) o;

        if (!Top.equals(fraction.Top)) return false;
        return Bottom.equals(fraction.Bottom);

    }

    @Override
    public int hashCode() {
        int result = Top.hashCode();
        result = 31 * result + Bottom.hashCode();
        return result;
    }

    @Override
    public NumberStructure add(Fraction toAdd) {
        return null;
    }
}
