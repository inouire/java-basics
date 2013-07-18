package inouire.basics;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Class for testing Args class
 * @author Edouard Garnier de Labareyre
 */
public class ValueTest {

    /**
     * Test of bound method, of class Value.
     */
    @Test
    public void testBound() {
        System.out.println("bound");
        assertEquals(12, Value.bound(12, 0, 20));
        assertEquals(20, Value.bound(22, 0, 20));
        assertEquals(-5, Value.bound(-8, -5, 5));
        assertEquals(-5, Value.bound(-8, 5, -5));
    }

    /**
     * Test of parseBool method, of class Value.
     */
    @Test
    public void testParseBool() {
        System.out.println("parseBool");
        
        try{
            assertEquals(true, Value.parseBool("true"));
            assertEquals(true, Value.parseBool("yes"));
            assertEquals(true, Value.parseBool("1"));
            assertEquals(false, Value.parseBool("false"));
            assertEquals(false, Value.parseBool("no"));
            assertEquals(false, Value.parseBool("0"));
            assertEquals(true, Value.parseBool("TrUe"));
            assertEquals(false, Value.parseBool(" false  "));
        }catch(NumberFormatException nfe){
            fail("Impossible to parse one of the valid strings as a boolean");
        }
        
        try{
            Value.parseBool("plop");
            fail("Parsing 'plop' as a boolean should raise an exception");
        }catch(NumberFormatException nfe){
            //we should go there
        }
    }
}