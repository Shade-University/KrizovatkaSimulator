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
public class MapaTest {

    public MapaTest() {
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
    public void vlozTest() throws KolekceException {
        Mapa<String, Integer> map = new Mapa<>();
        map.vloz("Test", 1);
        assertEquals(1, map.getSize());
        int result = map.dej("Test");
        assertEquals(1, result);
    }

    @Test
    public void dejTest() throws KolekceException {
        Mapa<String, Integer> map = new Mapa<>();
        map.vloz("Test", 1);
        int result = map.dej("Test");
        assertEquals(1, result);
    }

    @Test
    public void odeberTest() throws KolekceException {
        Mapa<String, Integer> map = new Mapa<>();
        map.vloz("Test", 1);
        int result = map.odeber("Test");
        assertEquals(1, result);
        assertEquals(0, map.getSize()); //TODO Velká refaktorizace testů, češtiny/angličtiny, testy na exception..
    }

    @Test
    public void iteratorTest() {
        //TODO Iterátor
    }
}
