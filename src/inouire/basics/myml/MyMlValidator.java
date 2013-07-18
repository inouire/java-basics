package inouire.basics.myml;

import inouire.basics.Value;
import static inouire.basics.myml.TypeEnum.BOOLEAN;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class to validate a MyMl structure
 * The validator will check that the structure to validate contains a value for 
 * at least all the absolute keys of the leafs of the validation pattern 
 * @author Edouard Garnier de Labareyre
 */
public class MyMlValidator {
    
    private ArrayList<String> validation_keys;
    private MyMl validation_structure;
    
    /* 
     * 0 -> keys & type
     * 1 -> keys only
     * 2 -> type only
     */
    private int validation_mode;
    
    public MyMlValidator(){
        this.validation_mode=0;
        this.validation_structure=null;
        this.validation_keys=null;
    }
    
    /**
     * Change validation mode to check keys and types (this is default validation policy)
     * Each key of the validation pattern should exist in the structure to validate, and have the good type
     * @return this
     */
    public MyMlValidator useFullValidation(){
        this.validation_mode = 0;
        return this;
    }
    
    /**
     * Change validation mode to check keys only
     * Each key of the validation pattern should exist in the structure to validate
     * @return this
     */
    public MyMlValidator useKeyValidationOnly(){
        this.validation_mode = 1;
        return this;
    }
    
    /**
     * Change validation mode to check types only
     * Each value for a key in the structure to validate should have the type given in the validation structure
     * If no type is found in the validation structure, the values is considered valid
     * @return this
     */
    public MyMlValidator useTypeValidationOnly(){
        this.validation_mode = 2;
        return this;
    }
    
    /**
     * Set the validation pattern from an array of keys
     * @param validation_keys the validation keys as an array
     */
    public MyMlValidator setValidationKeys(String[] keys){
        this.validation_keys = new ArrayList<String>();
        this.validation_keys.addAll(Arrays.asList(keys));
        return this;
    }
    
    /**
     * Set the validation pattern from another MyMl structure
     * The validation structure can contain the expected type as values
     * @param validation_structure the MyMl validation structure
     */
    public MyMlValidator setValidationPattern(MyMl validation_structure){
        this.validation_structure = validation_structure;
        this.validation_keys = validation_structure.getAbsoluteKeyList();
        return this;
    }
    
    /**
     * Set the validation pattern from content
     * @param content the content of the validation pattern, as lines array
     */
    public MyMlValidator setValidationPattern(String[] lines)throws MyMlException{
        ArrayList<String> lines_as_list = new ArrayList<String>();
        lines_as_list.addAll(Arrays.asList(lines));
        MyMl validation_pattern = MyMl.loadContent(lines_as_list);
        this.setValidationPattern(validation_pattern);
        return this;
    }

    /**
     * Validate a MyMl object
     * @param object the MyMl structure to validate
     * @throws MyMlException if the structure is invalid
     */
    public void validate(MyMl object) throws MyMlException{
        
        checkValidationPolicy();
        
        //keys validation
        if(this.validation_mode == 0 || validation_mode == 1){
            for(String absolute_key : validation_keys){
                try{
                    object.getValue(absolute_key);
                }catch(MyMlException e){
                    throw new MyMlException("Missing mandatory key '"+absolute_key+"'");
                }
            }
        }
        
        //types validation
        if(this.validation_mode == 0 || validation_mode == 2){
            TypeEnum type;
            String value;
            for(String absolute_key : object.getAbsoluteKeyList()){
                try{
                    type = TypeEnum.valueOf(validation_structure.getValue(absolute_key).toUpperCase());
                }catch(MyMlException e){
                    //means no constraint for this key, silent ignore
                    continue;
                }
                value = object.getValue(absolute_key);
                switch(type){
                    case BOOLEAN:
                    case BOOL:
                        try{
                            Value.parseBool(value);
                        }catch(NumberFormatException nfe){
                            throw new NumberFormatException("Key '"+absolute_key+"' is expected to be a boolean (true/false, yes/no, 0/1)");
                        }
                        break;
                    case INTEGER:
                    case INT:
                        try{
                            Integer.parseInt(value);
                        }catch(NumberFormatException nfe){
                            throw new NumberFormatException("Key '"+absolute_key+"' is expected to be an integer");
                        }
                        break;
                }
            }
        }
    }
    
    /**
     * Validate a MyMl object, without raising any exception
     * @param object the MyMl structure to validate
     * @return true if valid, false if not
     */
    public boolean validateQuiet(MyMl object){
        try{
            this.validate(object);
            return true;
        }catch(MyMlException mex){
            return false;
        }
    }
    
    /**
     * Check consistency of the given validation policy
     * @throws MyMlException if there is an error in the validation policy
     */
    private void checkValidationPolicy() throws MyMlException{
        if(validation_mode==0 || validation_mode==1){
            if(validation_structure==null){
                throw new MyMlException("Impossible to validate object with current policy. Validation pattern should be a complete MyMl structure");
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

enum TypeEnum{
    BOOLEAN,BOOL,INTEGER,INT,STRING;
}