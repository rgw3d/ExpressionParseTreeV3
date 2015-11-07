import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * Created by rgw3d on 11/5/2015.
 */
public class Term extends NumberStructure {
    private final NumberStructure Coefficient;
    private final ArrayList<Variable> Variables;
    private final NumberStructure Imagine;

    public Term(NumberStructure coef, ArrayList<Variable> var, NumberStructure img){
        if(coef == null)
            coef = Number.One;
        if(var == null)
            var = Variable.DEFAULT_VARIABLE_LIST;
        if(img == null)
            img = Imaginary.ZERO;

        Coefficient = coef;
        Variables = var;
        Imagine = img;
    }

    /**
     * gets the number value for the Simplifier.EquationNode
     *
     * @return double number value
     */
    public NumberStructure getCoefficient() {
        return Coefficient;
    }

    /**
     * gets the variable value for the Simplifier.EquationNode
     *
     * @return double Var value
     */
    public ArrayList<Variable> getVariable() {
        return Variables;
    }

    public NumberStructure getImaginary() {
        return Imagine;
    }

    /**
     * Get the list of simplified terms,
     *
     * @return simplified list of operation in Nominals and Fractions
     */
    @Override
    public ArrayList<EquationNode> evaluate() {
        return new ArrayList<EquationNode>(Arrays.asList(this));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Term)) return false;

        Term term = (Term) o;

        if (!Coefficient.equals(term.Coefficient)) return false;
        if (!Variables.equals(term.Variables)) return false;
        return Imagine.equals(term.Imagine);

    }

    @Override
    public int hashCode() {
        int result = Coefficient.hashCode();
        result = 31 * result + Variables.hashCode();
        result = 31 * result + Imagine.hashCode();
        return result;
    }
}
