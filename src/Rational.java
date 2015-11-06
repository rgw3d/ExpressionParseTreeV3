import java.util.ArrayList;

/**
 * Simplifier.Rational Simplifier.NumberStructure used to represent rational fractions.
 * Created by rgw3d on 10/9/2014.
 */
public class Rational extends NumberStructure{

    private ArrayList<NumberStructure> Top = new ArrayList<NumberStructure>();
    private ArrayList<NumberStructure> Bottom = new ArrayList<NumberStructure>();

    /**
     * secondary constructor
     * @param top ArrayList to set as the top value
     * @param bottom ArrayList to set as bottom value
     */
    public Rational(ArrayList<NumberStructure> top, ArrayList<NumberStructure> bottom) {
        if(top.size() == 0){
            top.add(new Number(0,0));
        }
        Top = top;
        //Simplifier.MathOperations.removeZeros(bottom);
        if(bottom.size() ==0){//if it is zero, then the zero has been removed and it is a divide by zero error
            throw new IllegalArgumentException("Error: Divisor is 0");
        }
        Bottom = bottom;

    }

    /**
     * Secondary Constructor - set the bottom of the fraction, default top is 1
     * @param bottom ArrayList to set as bottom value
     */
    public Rational(ArrayList<EquationNode> bottom) {
        Top.add(Number.One);
        if(bottom.size() ==0){//if it is zero, then the zero has been removed and it is a divide by zero error
            throw new IllegalArgumentException("Error: Divisor is 0");
        }
        Bottom = bottom;
    }

    /**
     * Secondary Constructor - set the top of a fraction, default bottom value is 1
     * @param top Simplifier.NumberStructure (fraction or nominal) to set as top
     */
    public Rational(NumberStructure top) {
        Top.add(top);
        Bottom.add(Number.One);
    }

    /**
     * returns the Top list
     * @return ArrayList of top
     */
    @Override
    public ArrayList<EquationNode> evaluate() {
        return getTop();
    }

    /**
     * Returns the Top list
     * Also cleans the returned value by removing zeros
     * @return ArrayList of the top values
     */
    @Override
    public ArrayList<EquationNode> getTop() {
        return Top;
    }

    /**
     * returns the bottom list
     * @return ArrayList of the bottom values
     */
    @Override
    public ArrayList<EquationNode> getBottom() {
        return Bottom;
    }

    /**
     *
     * @return String output value
     */
    @Override
    public String toString(){
        String toReturn ="";
        String top = "";
        String bot = "";

        if(Bottom.size() ==1 && Bottom.get(0).equals(Number.One)) {//if denominator is 1
            for (EquationNode fract : Top) {
                if (top.equals("")) {
                    top += fract.toString();
                } else {
                    top += "+" + fract.toString();
                }
            }
        }
        else {
            top = "((";
            for (EquationNode fract : Top) {
                if(top.equals("((")){
                    top += fract.toString();
                }
                else{
                    top+= "+"+fract.toString();
                }
            }
            top+=")/(";
            for (EquationNode fract : Bottom) {
                if(bot.equals("")){
                    bot += fract.toString();
                }
                else{
                    bot+= "+"+fract.toString();
                }
            }
            bot += "))";

        }
        toReturn+=top;
        toReturn+=bot;
        return toReturn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rational rational = (Rational) o;

        return Bottom.equals(rational.Bottom) && Top.equals(rational.Top);

    }

    @Override
    public int hashCode() {
        int result = Top.hashCode();
        result = 31 * result + Bottom.hashCode();
        return result;
    }
}
