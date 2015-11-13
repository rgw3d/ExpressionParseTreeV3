import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * Created by rgw3d on 11/6/2015.
 */
public class Imaginary extends NumberStructure {

    private final Number Coefficient;
    private final NumberStructure Exponent;

    public static final Imaginary ZERO = new Imaginary(Number.ONE, Number.ZERO);
    public static final Imaginary ONE = new Imaginary(Number.ONE, Number.ONE);

    public Imaginary(String input){
        if(input.startsWith("-")) {
            Coefficient = new Number(-1);
            input = input.substring(1);
        }
        else
            Coefficient = Number.ONE;

        Exponent = new Number(input.length());
    }
    public Imaginary(Number coefficient, NumberStructure exponent){
        if (coefficient == null)
            coefficient = Number.ONE;
        if (exponent == null)
            exponent = Number.ONE;
        Coefficient = coefficient;
        Exponent = exponent;

    }

    public static Imaginary multiply(Imaginary i1, Imaginary i2){
        Number coef = Number.multiply(i1.getCoefficient(), i2.getCoefficient());
        NumberStructure exp = Number.add((Number) i1.getExponent(), (Number) i2.getExponent());
        return new Imaginary(coef, exp);
    }

    public Number getCoefficient() {
        return Coefficient;
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
        if (!(o instanceof Imaginary)) return false;

        Imaginary imaginary = (Imaginary) o;

        if (!Coefficient.equals(imaginary.Coefficient)) return false;
        return Exponent.equals(imaginary.Exponent);

    }

    @Override
    public int hashCode() {
        int result = Coefficient.hashCode();
        result = 31 * result + Exponent.hashCode();
        return result;
    }
}
