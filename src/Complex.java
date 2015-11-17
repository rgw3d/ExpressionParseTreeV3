import java.util.HashSet;

/**
 * This is for CS 196
 * This file does not really fit in with the rest of the project, but I just need to get it done
 * Yay
 * The rest of the project will (in the future) be able to handle simplifying (that means add/subtract/multiply/divide/power) any imaginary numbers, any amount of variables, and constants (like pi and e)
 * but its not ready yet
 * So there is this
 * Which kind sucks
 * Sorry
 * Created by rgw3d on 11/17/2015.
 */
public class Complex {
    private Imaginary Imag;
    private Number Real;

    public Complex(){
        Imag = Imaginary.ZERO;
        Real = Number.ZERO;
    }
    public Complex(double real, double imag){
        Imag = new Imaginary(new Number(imag),Number.ONE);
        Real = new Number(real);
    }
    public Complex(double real){
        this.Imag = Imaginary.ZERO;
        Real = new Number(real);
    }
    public Complex(String expression){
        Expression expression1 = null;
        try {
            expression1 = new Expression(expression);
        }
        catch (InputException e){
        }
        if(expression1 != null) {
            HashSet<NumberStructure> terms = expression1.getTerms();
            Imaginary part = Imaginary.ZERO;
            Number partReal = Number.ZERO;
            for (NumberStructure x : terms) {
                if (x instanceof Term) {
                    Term y = (Term) x;
                    if (y.getImaginary().equals(Imaginary.ZERO)) {
                        partReal = (Number) y.getCoefficient().toArray(new NumberStructure[1])[0];
                    } else {
                        part = new Imaginary((Number) y.getCoefficient().toArray(new NumberStructure[1])[0], y.getImaginary().getExponent());
                    }
                }
            }
            Real = partReal;
            Imag = part;
        }
        else{
            expression = expression.replace("-","+-");
            if(expression.startsWith("+"))
                expression = expression.substring(1);
            int plusSignIndex = expression.indexOf("+");
            String left = expression.substring(0,plusSignIndex);//get start of string to '+'
            String right = expression.substring(plusSignIndex+1);//get everything after '+'
            if(left.contains("i")){//left side has it!
                left = left.replace("i", "");//remove 'i' occurances
                double imaginary = Double.parseDouble(left);//parse the remaining numers
                double real = Double.parseDouble(right);//set the other side
                Imag = new Imaginary(new Number(imaginary),Number.ONE);
                Real = new Number(real);
            }
            else{//Do the same thing but to the opposite variables
                right = right.replace("i", "");
                double imaginary = Double.parseDouble(right);
                double real = Double.parseDouble(left);
                Imag = new Imaginary(new Number(imaginary),Number.ONE);
                Real = new Number(real);
            }
        }
    }

    public Imaginary getImag() {
        return Imag;
    }

    public void setImaginary(double imag) {
        Imag = new Imaginary(new Number(imag),Number.ONE);
    }

    public void setReal(double real) {
        Real = new Number(real);
    }

    public double getImaginary() {
        return Imag.getCoefficient().getCoefficient();
    }
    public static double getImaginary(Complex x){
        return x.getImaginary();
    }

    public double getReal() {
        return Real.getCoefficient();
    }
    public static double getReal(Complex x){
        return x.getReal();
    }

    public double getAngle(){
        return Math.toDegrees(Math.asin((Math.abs(getImaginary()) / Math.abs(getMagnitude()))));
    }

    public double getMagnitude() {
        return Math.sqrt(getReal() * getReal() + getImaginary() * getImaginary());
    }

    public void add(Complex x){
        Imag = new Imaginary(new Number(getImaginary() + x.getImaginary()), Number.ONE);
        Real = new Number(getReal() + x.getReal());
    }
    public void add(double real){
        Real = new Number(getReal()+real);
    }
    public static Complex add(Complex x, Complex y){
        return new Complex(x.getReal()+y.getReal(), x.getImaginary()+y.getImaginary());
    }
    public static Complex subtract(Complex x, Complex y){
        return add(x, new Complex(-y.getReal(), -y.getImaginary()));
    }
    public String conjugate(){
        return new Complex(getReal(),-getImaginary()).toString();
    }
    public static Complex square(Complex x){
        return multiply(x,x);
    }

    public static Complex multiply(Complex x, Complex y){
        double realpart = x.getReal() * y.getReal();
        double imgPart = x.getReal() * y.getImaginary();
        double imgPart2 = x.getImaginary() * y.getReal();
        double realPart2 = -1 * x.getImaginary() * y.getImaginary(); //-1 because i *i = -1
        return new Complex(realPart2+realpart, imgPart+imgPart2);
    }
    public void multiply(Complex x){
        Complex y = multiply(this,x);
        Imag  = new Imaginary(new Number(y.getImaginary()),Number.ONE);
        Real = new Number(y.getReal());
    }

    public static Complex divide(Complex x, double y){
        return new Complex(x.getReal()/y, x.getImaginary()/y);
    }
    public void divide(double y){
        Imag = new Imaginary(new Number(getImaginary()/y),Number.ONE);
        Real = new Number(getReal()/y);
    }

    @Override
    public String toString(){
        return getReal() + " + "+getImaginary()+"i";
    }
}
