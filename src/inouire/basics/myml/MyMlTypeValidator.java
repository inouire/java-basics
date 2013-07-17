package inouire.basics.myml;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * A class to validate a MyMl structure with the types of the values
 * The validator will check that the structure to validate contains a value for 
 * at least all the absolute keys of the leafs of the validation pattern, and that it has the good type
 * @author Edouard Garnier de Labareyre
 */
public class MyMlTypeValidator {
    
    private ArrayList<String> validation_keys;
    private MyMl validation_pattern;
    
    /**
     * Constructor from content
     * @param validation_keys the validation keys as an array
     */
    public MyMlTypeValidator(ArrayList<String> content) throws MyMlException{
        this.validation_pattern = MyMl.loadContent(content);
        this.validation_keys = validation_pattern.getAbsoluteKeyList();
    }
    
    /**
     * Constructor from file path
     * @param file
     * @throws MyMlException 
     */
    public MyMlTypeValidator(String file) throws MyMlException, FileNotFoundException{
        this.validation_pattern = MyMl.loadFile(file);
        this.validation_keys = validation_pattern.getAbsoluteKeyList();
    }
    
    /**
     * Validate a MyMl object against current validator
     * @param object the object to validate
     * @throws MyMlException if not valid
     */
    public void validate(MyMl object) throws MyMlException{
        for(String absolute_key : validation_keys){
            
            //check that the key is here
            String value;
            try{
                value = object.getValue(absolute_key);
            }catch(Exception e){
                throw new MyMlException("Missing mandatory key '"+absolute_key+"'");
            }
            
            //check that it has the expected type
            TypeEnum type = TypeEnum.valueOf(validation_pattern.getValue(absolute_key).toUpperCase());
            switch(type){
                case BOOLEAN:
                case BOOL:
                    if(!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")
                    && !value.equalsIgnoreCase("yes") && !value.equalsIgnoreCase("no")
                    && !value.equals("0") && !value.equals("no")){
                        throw new MyMlException("Key '"+absolute_key+"' is expected to be a boolean (true/false, yes/no, 0/1)");
                    }
                    break;
                case INTEGER:
                case INT:
                    try{
                        Integer.parseInt(value);
                    }catch(NumberFormatException nfe){
                        throw new MyMlException("Key '"+absolute_key+"' is expected to be an integer");
                    }
                    break;
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