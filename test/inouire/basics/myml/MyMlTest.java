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
        "foo:",
        "    bar:",
        "        key : value with spaces",
        "another: value",
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
            assertEquals(valid_bench.getValue("database.port"),"1000");
            assertEquals(valid_bench.getValue("foo.bar.key"),"value with spaces");
            assertEquals(valid_bench.getValue("another"),"value");
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
            "foo.bar.key",
            "another"
        };
        assertArrayEquals(keys, ref);
    }
    
}