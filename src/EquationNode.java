import java.util.ArrayList;

/**
 * Parent interface to everything in this project
 * provides methods for all subclasses
 * Created by rgw3d on 11/5/2015.
 */
public interface EquationNode {

    /**
     * Get the list of simplified terms,
     * @return simplified list of operation in Nominals and Fractions
     */
    public ArrayList<EquationNode> evaluate();

}
