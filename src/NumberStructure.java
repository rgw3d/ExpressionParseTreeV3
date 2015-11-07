import java.util.ArrayList;
import java.util.Arrays;

/**
 * Parent to Number, NumberStructurenary
 * Created by rgw3d on 11/5/2015.
 */
public abstract class NumberStructure implements EquationNode {
    public static final ArrayList<NumberStructure> DEFAULT_LIST = new ArrayList<NumberStructure>(Arrays.asList(java.lang.Number.One));

}
