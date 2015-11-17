import com.sun.org.apache.xpath.internal.SourceTree;
import junit.framework.TestCase;

/**
 *
 * Created by rgw3d on 11/17/2015.
 */
public class ComplexTest extends TestCase {

    public void testComplex() throws Exception {
        Complex a = new Complex(); // 0 + 0i
        System.out.println(a);
        Complex b = new Complex(4); // 4 + 0i
        System.out.println(b);
        Complex c = new Complex(3, 4); // 3 + 4i
        System.out.println(c);
        Complex z = new Complex("5 - 7i"); //5 - 7i
        System.out.println(z);

        System.out.println(Complex.getImaginary(c)); //return 4
        System.out.println(b.getReal()); //return 4

        System.out.println(Complex.multiply(c, z));
        System.out.println(Complex.square(z));
        System.out.println(c.getAngle());

    }

}