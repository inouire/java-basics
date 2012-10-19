package inouire.basics;


/**
 * Some static methods to help handling command line arguments in a simple manner.
 * @author Edouard Garnier de Labareyre
 */
public class Args {

    /**
     * Get the value of a flag (true/false) contained in the arguments
     * @param flag_name the complete name of the flag to look for (for example "--help" or "-v")
     * @param args the arguments array to scan
     * @return the value for the flag (true if the flag is present, false if not)
     */
    public static boolean getOption(String flag_name, String[] args) {
        for(int k = 0 ; k < args.length ; k++){
            if( args[k].equals(flag_name) ){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get the value (true/false) of a flag with multiple names, contained in the arguments
     * @param flag_names the array of complete flag names to look for (for example: {"--help","help","-h"} )
     * @param args the arguments array to scan
     * @return the value for the flag (true if the flag is present, false if not)
     */
    public static boolean getOption(String[] flag_names, String[] args) {
        for( String flag_name : flag_names){
            if(getOption(flag_name, args)==true){
                return true;
            }
        }
        return false;
    }

    /**
     * Get the value of an integer option contained in the arguments
     * @param option_name the complete name of the option to look for (for example "--count" or "-port")
     * @param args the arguments array to scan
     * @param default_option the default integer value if the option is not found or if there is an error during cast to integer
     * @return the value for the integer option
     */
    public static int getIntegerOption(String option_name, String[] args, int default_option)  {
        for(int k = 0 ; k < args.length-1 ; k++){
            if( args[k].equals(option_name) ){
                try{
                    return Integer.parseInt(args[k+1]);
                }catch(NumberFormatException nfe){
                    return default_option;
                }
            }
        }
        return default_option;
    }
    
    /**
     * Get the value of an integer option with multiple names, contained in the arguments
     * @param option_names the array of complete option names to look for (for example: {"--port","-p","port"} )
     * @param args the arguments array to scan
     * @param default_option the default interger value if the option is not found or if there is an error during cast
     * @return the value for the integer option
     */
    public static int getIntegerOption(String[] option_names, String[] args, int default_option)  {
        for(int k = 0 ; k < args.length-1 ; k++){
            for( String option_name : option_names){
                if( args[k].equals(option_name) ){
                    try{
                        return Integer.parseInt(args[k+1]);
                    }catch(NumberFormatException nfe){
                        return default_option;
                    }
                }
            }
        }
        return default_option;
    }

    /**
     * Get the value of a string option contained in the arguments
     * @param option_name the complete name of the option to look for (for example "--host" or "-file")
     * @param args the arguments array to scan
     * @param default_option the default value if the option is not found
     * @return the value for the string option
     */
    public static String getStringOption(String option_name , String[] args, String default_option) {
        for(int k = 0 ; k < args.length-1 ; k++){
            if( args[k].equals(option_name) ){
                return args[k+1];
            }
        }
        return default_option;
    }
    
    /**
     * Get the value of a string option (with multiple names) contained in the arguments
     * @param option_names the array of complete options names of the option to look for (for example {"--host","host"})
     * @param args the arguments array to scan
     * @param default_option the default value if the option is not found
     * @return the value for the string option
     */
    public static String getStringOption(String[] option_names , String[] args, String default_option) {
        for(int k = 0 ; k < args.length-1 ; k++){
            for( String option_name : option_names){
                if( args[k].equals(option_name) ){
                    return args[k+1];
                }
            }
        }
        return default_option;
    }
}