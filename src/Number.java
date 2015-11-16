import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Simplifier.Number Simplifier.NumberStructure
 * Most basic form of number storage
 * Created by rgw3d on 10/9/2014.
 */
public class Number extends NumberStructure<Number> {

    private final double Coefficient;
    private final double PIExponent;
    private final double eExponent;

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
        PIExponent =1;
        eExponent = 1;
    }

    /**
     * Secondary constructor
     * @param num numerical value
     */
    public Number(double num){
        Coefficient = num;
        PIExponent = 0;
        eExponent = 0;
    }

    public Number(double num, double pi, double e){
        Coefficient = num;
        PIExponent = pi;
        eExponent = e;
    }

    /**
     * Should only be used from the Term parser/constructor
     * Otherwise, results are unknown
     * @param input number/pi/e from Term constructor
     */
    public Number(String input){
        int coefficient;
        if(input.startsWith("-")){
            coefficient = -1;
            input = input.substring(1);//remove -
        }
        else
            coefficient = 1;


        if (input.contains("pi")) {
            PIExponent = input.length()/2;
            eExponent = 0;
            Coefficient = coefficient;
        } else if (input.contains("e")) {
            PIExponent = 0;
            eExponent = input.length();
            Coefficient = coefficient;
        } else {
            PIExponent = 0;
            eExponent = 0;
            Coefficient = coefficient * Double.parseDouble(input);
        }
    }

    public static void multiply(ArrayList<NumberStructure> list, Number toMultiply){
        //TODO implement
    }

    public static Number multiply(Number n1, Number n2){
        double num = n1.getCoefficient() * n2.getCoefficient();
        double piExp = n1.getPIExponent() + n2.getPIExponent();
        double eExp = n1.geteExponent() + n2.geteExponent();
        return new Number(num, piExp, eExp);
    }

    public static NumberStructure add(Number n1, Number n2){
        if(n1.geteExponent() == n2.geteExponent() && n1.geteExponent() == n2.getPIExponent()){
            return new Number(n1.getCoefficient() + n2.getCoefficient(), n1.getPIExponent(), n2.geteExponent());
        }
        else {
            return new Term(new HashSet<NumberStructure>(Arrays.asList(n1,n2)), null, null);
        }
    }

    public double getCoefficient() {
        return Coefficient;
    }

    public double getPIExponent() {
        return PIExponent;
    }

    public double geteExponent() {
        return eExponent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Number)) return false;

        Number number = (Number) o;

        if (Double.compare(number.getCoefficient(), getCoefficient()) != 0) return false;
        if (Double.compare(number.getPIExponent(), getPIExponent()) != 0) return false;
        return Double.compare(number.geteExponent(), geteExponent()) == 0;

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
        if(PIExponent != 0)
            string += "(pi^"+PIExponent+")";
        if(eExponent!= 0)
            string += "(e^"+eExponent+")";
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
    public HashSet<ExpressionNode> simplify() {
        return new HashSet<ExpressionNode>(Arrays.asList(this));
    }

    @Override
    public NumberStructure add(Number toAdd) {
        return null;
    }
}
