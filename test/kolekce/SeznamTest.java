
package kolekce;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Function;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kasi
 */
public class SeznamTest {
    
    public SeznamTest() {
       
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

    /**
     * Test of getVelikost method, of class Seznam.
     * @throws kolekce.KolekceException
     */
    
    @Test
    public void testZvetsiPole001() throws KolekceException{
        System.out.println("zvetsiPole");
        Seznam instance = new Seznam();
        Object data = null;
        for (int i = 0; i < 10; i++) {
            instance.pridej(data);
        }
        
        int expResultAfterResize = 20;
        int result = instance.getAktualniVelikost();
        assertEquals(expResultAfterResize, result);
    }
    
    /*@Test
    public void testZvetsiPole002() throws KolekceException{
        //EXCEPTION
        //TODO Pokud se bude chtít zvětšit o víc než je limit
    }*/
    
    @Test
    public void testZmensiPole() throws KolekceException{
        System.out.println("zmensiPole");
        Seznam instance = new Seznam();
        Object data = null;
        for (int i = 0; i < 10; i++) {
            instance.pridej(data);
        }
        for (int i = 0; i < 5; i++) {
            instance.odeberPosledni();
        }
        
        int expResult = 10;
        int result = instance.getAktualniVelikost();
        assertEquals(expResult, result); //TODO OVĚŘIT MEZE, MÁŠ V TOM BORDEL
    }
    
    @Test
    public void test001GetVelikost() throws KolekceException {
        System.out.println("getVelikost");
        Seznam instance = new Seznam();
        int expResult = Integer.MAX_VALUE;
        //int expResult = 10; //DefaultniVelikostPole
        int result = instance.getVelikost();
        assertEquals(expResult, result);        
    }

    @Test
    public void test002GetVelikost() throws KolekceException {
        System.out.println("getVelikost");
        Seznam instance = new Seznam(111);
        int expResult = 111;
        int result = instance.getVelikost();
        assertEquals(expResult, result);   
    }
    
    /**
     * Test of getPocet method, of class Seznam.
     */
    @Test
    public void testGetPocet() throws KolekceException {
        System.out.println("getPocet");
        Seznam instance = new Seznam();
        int expResult = 0;
        int result = instance.getPocet();
        assertEquals(expResult, result);   
        
        Object data = null;
        instance.pridej(data);
        int expResultAfterAddedElemnt = 1;
        int result2 = instance.getPocet();
        assertEquals(expResultAfterAddedElemnt, result2);
        
        instance.odeberPrvni();
        int expResultAfterRemoveElement = 0;
        int result3 = instance.getPocet();
        assertEquals(expResultAfterRemoveElement, result3);
    }

    /**
     * Test of jePlny method, of class Seznam.
     */
    @Test
    public void testJePlny() throws KolekceException {
        System.out.println("jePlny");
        Seznam instance = new Seznam(5);
        boolean expResult = false;
        boolean result = instance.jePlny();
        assertEquals(expResult, result);
        Object data = null;
        
        for (int i = 0; i < 5; i++) {
            instance.pridej(data);
        }
        boolean expResultAfterFill = true;
        boolean result2 = instance.jePlny();
        assertEquals(expResultAfterFill, result2);
    }

    /**
     * Test of jePrazdny method, of class Seznam.
     */
    @Test
    public void testJePrazdny() throws KolekceException {
        System.out.println("jePrazdny");
        Seznam instance = new Seznam();
        boolean expResult = true;
        boolean result = instance.jePrazdny();
        assertEquals(expResult, result);
        
        Object data = null;
        instance.pridej(data);
        boolean expResultAfterAddedElement = false;
        boolean result2 = instance.jePrazdny();
        assertEquals(expResultAfterAddedElement, result2);
    }

    /**
     * Test of zrus method, of class Seznam.
     */
    @Test
    public void testZrus() throws KolekceException {
        System.out.println("zrus");
        Object data = null;
        Seznam instance = new Seznam();
        instance.pridej(data);
        instance.zrus();
        
        Integer expResult = 0;
        Integer result = instance.getPocet();
        assertEquals(expResult, result);
    }

    /**
     * Test of pridej method, of class Seznam.
     */
    @Test
    public void testPridej() throws Exception {
        System.out.println("pridej");
        Object data = null;
        Seznam instance = new Seznam();
        instance.pridej(data);
        
        Integer expResult = 1;
        Integer result = instance.getPocet();
        assertEquals(expResult, result);
        
        Object result2 = instance.odeberPrvni();
        assertEquals(data, result2);
        
    }

    /**
     * Test of odeberPrvni method, of class Seznam.
     */
    @Test
    public void testOdeberPrvni() throws Exception {
        System.out.println("odeberPrvni");
        Seznam<Integer> instance = new Seznam();
        instance.pridej(10);
        instance.pridej(20);
        instance.pridej(30); //Testovací data
        
        Object expResultPrvniElement = 10;
        Object result = instance.odeberPrvni();
        assertEquals(expResultPrvniElement, result);
        
        Integer expResultPocetElementu = 2;  
        Integer result2 = instance.getPocet();        
        assertEquals(expResultPocetElementu, result2);
    }

    /**
     * Test of odeberPosledni method, of class Seznam.
     */
    @Test
    public void testOdeberPosledni() throws Exception {
        System.out.println("odeberPosledni");
        Seznam<Integer> instance = new Seznam();
        instance.pridej(10);
        instance.pridej(20);
        instance.pridej(30); //Testovací data
        
        Object expResultPosledniElement = 30;
        Object result = instance.odeberPosledni();
        assertEquals(expResultPosledniElement, result);
        
        Integer expResultPocetElementu = 2;
        Integer result2 = instance.getPocet();
        assertEquals(expResultPocetElementu, result2);      
    }

    /**
     * Test of toArray method, of class Seznam.
     */
    @Test
    public void testToArray_0args() throws KolekceException {
        System.out.println("toArray");
        Seznam instance = new Seznam();
        instance.pridej(1);
        Object[] expResult = new Integer[1];
        expResult[0] = 1;
        Object[] result = instance.toArray();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of toArray method, of class Seznam.
     */
    @Test
    public void testToArray_GenericType() throws KolekceException {
        System.out.println("toArray");
        Integer[] pole = new Integer[2];
        Seznam<Integer> instance = new Seznam<>();
        instance.pridej(1);
        Integer[] expResult = new Integer[1];
        expResult[0] = 1;
        Integer[] result = instance.toArray(pole);
        assertArrayEquals(expResult, result); //TODO Další toArray a Iterator
    }
//
//    /**
//     * Test of toArray method, of class Seznam.
//     */
//    @Test
//    public void testToArray_Function() throws KolekceException {
//        System.out.println("toArray");
//        Function s = null;
//        Seznam instance = new Seznam();
//        Object[] expResult = null;
//        Object[] result = instance.toArray(s);
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of iterator method, of class Seznam.
//     */
//    @Test
//    public void testIterator() throws KolekceException {
//        System.out.println("iterator");
//        Seznam instance = new Seznam();
//        Iterator expResult = null;
//        Iterator result = instance.iterator();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of nastavPrvni method, of class Seznam.
//     */
//    @Test
//    public void testNastavPrvni() throws Exception {
//        System.out.println("nastavPrvni");
//        Seznam instance = new Seznam();
//        instance.nastavPrvni();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of prejdiNaDalsi method, of class Seznam.
//     */
//    @Test
//    public void testPrejdiNaDalsi() throws Exception {
//        System.out.println("prejdiNaDalsi");
//        Seznam instance = new Seznam();
//        instance.prejdiNaDalsi();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of jeDalsi method, of class Seznam.
//     */
//    @Test
//    public void testJeDalsi() throws Exception {
//        System.out.println("jeDalsi");
//        Seznam instance = new Seznam();
//        boolean expResult = false;
//        boolean result = instance.jeDalsi();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of zpristupni method, of class Seznam.
//     */
//    @Test( )
//    public void testZpristupni() throws Exception {
//        System.out.println("zpristupni");
//        Seznam instance = new Seznam();
//        Object expResult = null;
//        Object result = instance.zpristupni();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
