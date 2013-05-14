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
public class MyMlValidatorTest {
    
    static MyMl valid_bench;
    static MyMlValidator validator_ok;
    static MyMlValidator validator_nok;
    
    static String[] pattern_ok = new String[]{
        "database:",
        "    host: x",
        "    port: x",
        "another: x"
    };
    
    static String[] pattern_nok = new String[]{
        "database:",
        "    port: x",
        "    password: x"
    };
    
    public MyMlValidatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws MyMlException {
        ArrayList<String> lines = new ArrayList<String>();
        lines.addAll(Arrays.asList(MyMlTest.valid_content));
        valid_bench = MyMl.loadContent(lines);
        
        ArrayList<String> content_ok = new ArrayList<String>();
        content_ok.addAll(Arrays.asList(pattern_ok));
        validator_ok = new MyMlValidator(content_ok);
        
        ArrayList<String> content_nok = new ArrayList<String>();
        content_nok.addAll(Arrays.asList(pattern_nok));
        validator_nok = new MyMlValidator(content_nok);

    }
    
    /**
     * Test MyMl structure validation with MyMlValidator.
     */
    @Test
    public void testValidate() throws Exception {
        try{
            validator_ok.validate(valid_bench);
        }catch(MyMlException ex){
            fail(ex.getMessage());
        }
        try{
            validator_nok.validate(valid_bench);
            fail("Invalid structure is seen valid by the validator");
        }catch(MyMlException ex){
            //we should go here
        }
    }

    /**
     * Test retrieval of validation keys of a MyMlValidator.
     */
    @Test
    public void testGetValidationKeys() {
        String[] keys_ok = new String[validator_ok.getValidationKeys().size()];
        keys_ok = validator_ok.getValidationKeys().toArray(keys_ok);
        String[] ref_ok = new String[]{
            "database.host",
            "database.port",
            "another"
        };
        assertArrayEquals(keys_ok, ref_ok);
        
        String[] keys_nok = new String[validator_nok.getValidationKeys().size()];
        keys_nok = validator_nok.getValidationKeys().toArray(keys_nok);
        String[] ref_nok = new String[]{
            "database.port",
            "database.password"
        };
        assertArrayEquals(keys_nok, ref_nok);
    }
}