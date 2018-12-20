package kolekce;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author user
 */
public class FrontaTest {

    public FrontaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void getPocetTest() throws KolekceException {

        Fronta queue = new Fronta();
        Object data = null;
        queue.vloz(data);
        int expResult = 1;
        int result = queue.getPocet();
        assertEquals(expResult, result);
    }

    @Test
    public void jePrazdnyTest() throws KolekceException {
        Fronta queue = new Fronta();
        boolean expResult = true;
        boolean result = queue.jePrazdny();
        assertEquals(expResult, result);
        Object data = null;
        queue.vloz(data);
        assertEquals(false, queue.jePrazdny());
    }

    @Test
    public void vlozTest() throws KolekceException {
        Fronta queue = new Fronta();
        Object data = null;
        queue.vloz(data);
        assertEquals(1, queue.getPocet());
    }

    @Test
    public void odeberTest() throws KolekceException {
        Fronta queue = new Fronta();
        Object data = null;
        queue.vloz(data);
        Object expData = queue.odeber();
        assertEquals(0, queue.getPocet());
        assertEquals(expData, data);
    }

    @Test
    public void zrusTest() throws KolekceException {
        Fronta queue = new Fronta();
        Object data = null;
        queue.vloz(data);
        queue.zrus();
        assertEquals(0, queue.getPocet());
    }

    @Test
    public void iteratorTest() {
        //TODO Iterator
    }
}
