import java.util.ArrayList;
import java.util.Arrays;

/**
 * Simplifier.Number Simplifier.NumberStructure
 * Most basic form of number storage
 * Created by rgw3d on 10/9/2014.
 */
public class Number extends NumberStructure {

    private final double Coefficient;
    private final double PICount;
    private final double eCount;

    public static final Number ZERO = new Number(0);
    public static final Number ONE = new Number(1);
    public static final Number PI = new Number("pi");
    public static final Number e = new Number("e");

    /**
     * Default constructor.
     * Sets nominal to have a value of zero
     */
    public Number(){
        Coefficient =0;
        PICount = 0;
        eCount = 0;
    }

    /**
     * Secondary constructor
     * @param num numerical value
     */
    public Number(double num){
        Coefficient = num;
        PICount = 0;
        eCount = 0;
    }

    public Number(double num, double pi, double e){
        Coefficient = num;
        PICount = pi;
        eCount = e;
    }

    public Number(String special){
        if (special.equalsIgnoreCase("pi")) {
            PICount = 1;
            eCount = 0;
            Coefficient = 0;
        } else if (special.equalsIgnoreCase("e")) {
            PICount = 0;
            eCount = 1;
            Coefficient = 0;
        } else {
            PICount = 0;
            eCount = 0;
            Coefficient = Double.parseDouble(special);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Number)) return false;

        Number number = (Number) o;

        return Double.compare(number.Coefficient, Coefficient) == 0;

    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(Coefficient);
        return (int) (temp ^ (temp >>> 32));
    }

    @Override
    public String toString() {
        if(Coefficient == 0)
            return 0+"";
        String string = "";
        if(PICount != 0)
            string += "("+PICount+"pi)";
        if(eCount != 0)
            string += "("+eCount+"e)";
        if(Coefficient != 1 || string.length() == 0)
            string = Coefficient + string;
        return string;
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
}
