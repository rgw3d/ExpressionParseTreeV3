import java.util.Collections;
import java.util.HashSet;

/**
 *
 * Created by rgw3d on 11/5/2015.
 */
public class Variable extends NumberStructure{

    private final Number Coefficient;
    private final char Variable;
    private final NumberStructure Exponent;

    public static final Variable ZERO = new Variable(Number.ONE,'x',Number.ZERO);

    @Override
    public String toString() {
        return Coefficient +""+ Variable + "^"+ Exponent.toString();
    }

    /**
     * Constructor called from Term, when Term is parsing input. If used otherwise, results are unexpected
     * @param input input string
     */
    public Variable(String input){
        if(input.startsWith("-")) {
            Coefficient = new Number(-1);
            input = input.substring(1);
        }
        else
            Coefficient = Number.ONE;

        Variable = input.charAt(0);//variable should be the first one
        Exponent = new Number(input.length());
    }

    public Variable(Number coefficient, char variable, NumberStructure exponent){
        if(coefficient == null)
            this.Coefficient = Number.ONE;
        else
            this.Coefficient = coefficient;

        this.Variable = variable;
        this.Exponent = exponent;

    }

    public Variable(char variable, NumberStructure exponent){
        this.Coefficient = Number.ONE;
        this.Variable = variable;
        if(exponent == null)
            exponent = Number.ONE;
        this.Exponent = exponent;

    }

    public Number getCoefficient() {
        return Coefficient;
    }

    public char getVariable() {
        return Variable;
    }

    public NumberStructure getExponent() {
        return Exponent;
    }

    public static void multiply(HashSet<Variable> list, Variable var){
        for(Variable component: list){
            if(var.getVariable() == component.getVariable()){ //same variable
                Number coef = Number.multiply(var.getCoefficient(), component.getCoefficient());//get multiplied coefficient
                NumberStructure exp = Number.add((Number)var.getExponent(), (Number)component.getExponent());//TODO implement addition of everything
                list.remove(component);
                list.add(new Variable(coef, var.getVariable(), exp)); //add new variable
                return;
            }
        }
        list.add(var);
    }

    /**
     * Assumes that the variables can be added together
     * @param left Left variable
     * @param right Right Variable to add
     * @return Number or Term
     */
    public static NumberStructure add(Variable left, Variable right){
        NumberStructure coefficient = Number.add(left.getCoefficient(), right.getCoefficient());
        if(Number.ZERO.equals(coefficient))
            return Number.ZERO;
        return new Term(coefficient,new Variable(left.getVariable(),left.getExponent()), null);
    }

    /**
     * Assumes that the variable and the term can be added
     * @param left Variable
     * @param right Term
     * @return Term (or number if result is zero)
     */
    public static NumberStructure add(Variable left, Term right){
        HashSet<NumberStructure> coef = right.getCoefficient();
        coef = MathOperations.add(coef,new HashSet<NumberStructure>(Collections.singletonList(left.getCoefficient())));
        return new Term(coef,new Variable(left.getVariable(),left.getExponent()),null);
    }

    /**
     * Get the list of simplified terms,
     *
     * @return simplified list of operation in Nominals and Fractions
     */
    @Override
    public HashSet<NumberStructure> simplify() {
        return new HashSet<NumberStructure>(Collections.singletonList(this));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Variable)) return false;

        Variable variable1 = (Variable) o;

        if (Variable != variable1.Variable) return false;
        //Does not check coefficient. This way you can check if you can add variables together
        return Exponent.equals(variable1.Exponent);

    }

    @Override
    public int hashCode() {
        int result = Coefficient.hashCode();
        result = 31 * result + (int) Variable;
        result = 31 * result + Exponent.hashCode();
        return result;
    }

}
