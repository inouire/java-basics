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
    
    static String[] validation_keys_ok;
    static String[] validation_keys_nok;
           
    static String[] pattern_ok = new String[]{
        "database:",
        "    host: string",
        "    port: int",
        "another: bool"
    };
    
    static String[] pattern_nok = new String[]{
        "database:",
        "    port: bool",
        "    password: int"
    };
    
    public MyMlValidatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws MyMlException {
        ArrayList<String> lines = new ArrayList<String>();
        lines.addAll(Arrays.asList(MyMlTest.valid_content));
        valid_bench = MyMl.loadContent(lines);
        
        validator_ok = new MyMlValidator();
        validator_ok.setValidationPattern(pattern_ok);

        validator_nok = new MyMlValidator();
        validator_nok.setValidationPattern(pattern_nok);

    }
    
    /**
     * Test MyMl structure validation with MyMlValidator in keys mode
     */
    @Test
    public void testValidateKeys() throws Exception {
        System.out.println("testValidateKeys");
        
        validator_ok.useKeyValidationOnly();
        validator_nok.useKeyValidationOnly();
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
     * Test MyMl structure validation with MyMlValidator in type mode
     */
    @Test
    public void testValidateTypes() throws Exception {
        System.out.println("testValidateTypes");
        
        validator_ok.useTypeValidationOnly();
        validator_nok.useTypeValidationOnly();
        try{
            validator_ok.validate(valid_bench);
        }catch(MyMlException ex){
            fail(ex.getMessage());
        }
        try{
            validator_nok.validate(valid_bench);
            fail("Invalid structure is seen valid by the validator");
        }catch(Exception ex){
            //we should go here
        }
    }
    
    /**
     * Test MyMl structure validation with MyMlValidator in keys+types mode
     */
    @Test
    public void testValidateKeysAndTypes() throws Exception {
        System.out.println("testValidateKeysAndTypes");
        
        validator_ok.useFullValidation();
        validator_nok.useFullValidation();
        try{
            validator_ok.validate(valid_bench);
        }catch(MyMlException ex){
            fail(ex.getMessage());
        }
        try{
            validator_nok.validate(valid_bench);
            fail("Invalid structure is seen valid by the validator");
        }catch(Exception ex){
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