package inouire.basics.myml;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Edouard Garnier de Labareyre
 */
public class MyMlTest {
    
    static MyMl valid_bench;
    
    //the content as no comments because MyMl is not reponsible for removing comments
    //this is done by TxtFileLoader
    static String[] valid_content= new String[] {
        "database:",
        "    host: localhost",
        "    port : 1000 ",
        "    login: aaa",
        "    enable: yes",
        "foo:",
        "    bar:",
        "        key : value with spaces",
        "another: yes",
        "alone:"
    };
            
    public MyMlTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws MyMlException {
        ArrayList<String> lines = new ArrayList<String>();
        lines.addAll(Arrays.asList(valid_content));
        valid_bench = MyMl.loadContent(lines);
    }

    /**
     * Test value retrieval from a MyMl structure.
     */
    @Test
    public void testGetValue(){
        try {
            assertEquals("1000", valid_bench.getValue("database.port"));
            assertEquals("value with spaces", valid_bench.getValue("foo.bar.key"));
            assertEquals("yes", valid_bench.getValue("another"));
        } catch (MyMlException ex) {
            fail(ex.getMessage());
        }
        try{
            valid_bench.getValue("impossible.key");
            fail("Trying to access 'impossible.key' should raise an exception");
        }catch(MyMlException ex){
            //we should go here
        }
    }
    
    @Test
    public void testGetBool(){
        assertEquals(true,valid_bench.getBool("database.enable", false));
        assertEquals(false,valid_bench.getBool("database.host", false));
        assertEquals(false,valid_bench.getBool("impossible.key", false));
    }
    
    @Test 
    public void testGetInt(){
        assertEquals(1000,valid_bench.getInt("database.port", 22));
        assertEquals(22,valid_bench.getInt("database.host", 22));   
        assertEquals(22,valid_bench.getInt("impossible.key", 22));   
    }
    
    /**
     * Test absolute key list retrieval from a MyMl structure.
     */
    @Test
    public void testGetAbsoluteKeyList(){
        String[] keys = new String[valid_bench.getAbsoluteKeyList().size()];
        keys = valid_bench.getAbsoluteKeyList().toArray(keys);
        String[] ref = new String[]{
            "database.host",
            "database.port",
            "database.login",
            "database.enable",
            "foo.bar.key",
            "another"
        };
        assertArrayEquals(keys, ref);
    }
    
}