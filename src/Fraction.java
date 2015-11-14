import java.util.ArrayList;
import java.util.Arrays;

/**
 * Simplifier.NumberStructurefier.NumberStructure used to represent rational fractions.
 * Created by rgw3d on 10/9/2014.
 */
public class Fraction extends NumberStructure {
    private final ArrayList<NumberStructure> Top;
    private final ArrayList<NumberStructure> Bottom;

    public static final ArrayList<NumberStructure> DEFAULT_LIST = new ArrayList<NumberStructure>(Arrays.asList(Number.ONE));

    /**
     * Primary constructor
     * @param top ArrayList to set as the top value
     * @param bottom ArrayList to set as bottom value
     */
    public Fraction(ArrayList<NumberStructure> top, ArrayList<NumberStructure> bottom) {
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
            if(bottom.get(0).equals(Number.ZERO))
                throw new IllegalArgumentException("Error: Divisor is 0");
        }
        Bottom = bottom;

    }

    public ArrayList<NumberStructure> getTop() {
        return Top;
    }

    public ArrayList<NumberStructure> getBottom() {
        return Bottom;
    }

    /**
     * returns the Top list
     * @return ArrayList of top
     */
    @Override
    public ArrayList<ExpressionNode> simplify() {
        return new ArrayList<ExpressionNode>(Arrays.asList(this));
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
}
