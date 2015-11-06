import java.util.ArrayList;

/**
 * Simplifier.Fraction Simplifier.NumberStructure used to represent rational fractions.
 * Created by rgw3d on 10/9/2014.
 */
public class Fraction extends NumberStructure{

    private final ArrayList<NumberStructure> Top;
    private final ArrayList<NumberStructure> Bottom;

    /**
     * Primary constructor
     * @param top ArrayList to set as the top value
     * @param bottom ArrayList to set as bottom value
     */
    public Fraction(ArrayList<NumberStructure> top, ArrayList<NumberStructure> bottom) {
        if(top == null)
            top = NumberStructure.DEFAULT_LIST;
        else if (top.size() == 0 ){
            top = NumberStructure.DEFAULT_LIST;
        }
        Top = top;

        if(bottom == null){
            bottom = NumberStructure.DEFAULT_LIST;
        }
        else if(bottom.size() == 1){//if it is zero, then the zero has been removed and it is a divide by zero error
            if(bottom.get(0).equals(Number.Zero))
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
    public ArrayList<EquationNode> evaluate() {
        return null;
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
