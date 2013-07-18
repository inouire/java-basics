package inouire.basics;

/**
 * Some static functions about int & bool manipulations
 * @author Edouard Garnier de Labareyre
 */
public class Value {
    
    /**
     * Make an integer stay between two bounds
     * @param value the initial value
     * @param b1 first bound value
     * @param b2 second bound value
     * @return the bounded value
     */
    public static int bound(int value, int b1, int b2){
        //inverting boundaries if needed
        int min=b1,max=b2;
        if(b1>b2){
            min=b2;
            max=b1;
        }
        //bounding original value
        int bounded;
        if(value>max){
            bounded = max;
        }else if(value<min){
            bounded = min;
        }else{
            bounded = value;
        }
        return bounded;
    }
    
    /**
     * WIsely parse a string to convert it into a boolean
     * true, yes, 1 -> true
     * false, no 0 -> false
     * It is case insensitive, and the string is trimmed
     * @param s the string to parse
     * @return true or false
     * @throws NumberFormatException if no value can be found
     */
    public static boolean parseBool(String bool_as_string) throws NumberFormatException{
        String value = bool_as_string.trim().toLowerCase();
        boolean boolean_value;
        if(value.equals("true")||value.equals("yes")||value.equals("1")){
            boolean_value=true; 
        }else if(value.equals("false")||value.equals("no")||value.equals("0")){
            boolean_value=false;
        }else{
            throw new NumberFormatException();
        }
        return boolean_value;
    }
}
