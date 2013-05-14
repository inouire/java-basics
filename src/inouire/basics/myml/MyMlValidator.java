package inouire.basics.myml;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class to validate easily a MyMl structure
 * The validator will check that the structure to validate contains a value for 
 * at least all the absolute keys of the leafs of the validation pattern 
 * @author Edouard Garnier de Labareyre
 */
public class MyMlValidator {
    
    private ArrayList<String> validation_keys;

    /**
     * Constructor from validation keys
     * @param validation_keys the validation keys as an array
     */
    public MyMlValidator(String[] keys){
        this.validation_keys = new ArrayList<String>();
        this.validation_keys.addAll(Arrays.asList(keys));
    }
    
    /**
     * Constructor from content
     * @param validation_keys the validation keys as an array
     */
    public MyMlValidator(ArrayList<String> content) throws MyMlException{
        MyMl validation_pattern = MyMl.loadContent(content);
        this.validation_keys = validation_pattern.getAbsoluteKeyList();
    }
    
    /**
     * Constructor from file path
     * @param file
     * @throws MyMlException 
     */
    public MyMlValidator(String file) throws MyMlException, FileNotFoundException{
        MyMl validation_pattern = MyMl.loadFile(file);
        this.validation_keys = validation_pattern.getAbsoluteKeyList();
    }
    
    /**
     * Validate a MyMl object against current validator
     * @param object the object to validate
     * @throws MyMlException if not valid
     */
    public void validate(MyMl object) throws MyMlException{
        for(String absolute_key : validation_keys){
            try{
                object.getValue(absolute_key);
            }catch(Exception e){
                throw new MyMlException("Missing mandatory key '"+absolute_key+"'");
            }
        }
    }
    
    /**
     * Get all absolute keys of the validator
     * @return an list containing the absolute keys of the validator 
     */
    public ArrayList<String> getValidationKeys(){
        return this.validation_keys;
    }
    
}
