import java.util.ArrayList;

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
    public ArrayList<EquationNode> evaluate() {
        return null;
    }
}
