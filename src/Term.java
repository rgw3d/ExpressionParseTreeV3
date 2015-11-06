import java.util.ArrayList;

/**
 *
 * Created by rgw3d on 11/5/2015.
 */
public class Term extends NumberStructure {
    private final NumberStructure COEFFICIENT;
    private final ArrayList<Variable> VARIABLES;
    private final NumberStructure IMAGINARY;

    public Term(NumberStructure coef, ArrayList<Variable> var, NumberStructure img){
        if(coef == null)
            COEFFICIENT = Number.One;
        else
            COEFFICIENT = coef;
        if(var == null)
            VARIABLES = Variable.DEFAULT_VARIABLE_LIST;
        else
            VARIABLES = var;
        if(img == null)
            IMAGINARY =
        IMAGINARY = img;
    }

    /**
     * gets the number value for the Simplifier.EquationNode
     *
     * @return double number value
     */
    public NumberStructure getCoefficient() {
        return COEFFICIENT;
    }

    /**
     * gets the variable value for the Simplifier.EquationNode
     *
     * @return double Var value
     */
    public ArrayList<Variable> getVariable() {
        return VARIABLES;
    }

    public NumberStructure getImaginary() {
        return IMAGINARY;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Term)) return false;

        Term term = (Term) o;

        if (!COEFFICIENT.equals(term.COEFFICIENT)) return false;
        if (!VARIABLES.equals(term.VARIABLES)) return false;
        return IMAGINARY.equals(term.IMAGINARY);

    }

    @Override
    public int hashCode() {
        int result = COEFFICIENT.hashCode();
        result = 31 * result + VARIABLES.hashCode();
        result = 31 * result + IMAGINARY.hashCode();
        return result;
    }
}
