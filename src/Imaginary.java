import java.util.ArrayList;

/**
 *
 * Created by rgw3d on 11/6/2015.
 */
public class Imaginary extends NumberStructure {

    private final NumberStructure Coefficient;
    private final NumberStructure Exponent;

    public static final Imaginary ZERO = new Imaginary(Number.Zero, Number.One);
    public static final Imaginary ONE = new Imaginary(Number.One, Number.One);

    public Imaginary(NumberStructure coefficient, NumberStructure exponent){
        if (coefficient == null)
            coefficient = Number.One;
        if (exponent == null)
            exponent = Number.One;
        Coefficient = coefficient;
        Exponent = exponent;

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
