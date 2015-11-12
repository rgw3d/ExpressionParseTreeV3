import java.util.ArrayList;
import java.util.Arrays;

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
     * Get the list of simplified terms,
     *
     * @return simplified list of operation in Nominals and Fractions
     */
    @Override
    public ArrayList<ExpressionNode> evaluate() {
        return new ArrayList<ExpressionNode>(Arrays.asList(this));
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
