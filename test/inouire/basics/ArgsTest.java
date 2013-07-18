package inouire.basics;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Class for testing Args class
 * @author Edouard Garnier de Labareyre
 */
public class ArgsTest {
    
    public ArgsTest() {
    }

    private static final String option_name="--option";
    private static final String[] option_list=new String[]{"-o","--option","opt"};
        
    /**
     * Test of getOption method, of class Args.
     */
    @Test
    public void testGetOption() {
        System.out.println("getOption");
        
        //first test: flag is expected to be found
        String[] args_with_flag = new String[]{"-h","test","--option","-f"};
        assertEquals(Args.getOption(option_name, args_with_flag), true);
        
        //second test: flag is not expected to be found
        String[] args_without_flag = new String[]{"-h","test","--add","-f"};
        assertEquals( Args.getOption(option_name, args_without_flag), false);
    }

    /**
     * Test of getOption method, of class Args.
     */
    @Test
    public void testGetOptionMultipleName() {
        System.out.println("getOption with a multiple name flag");
        
        //first test: flag is expected to be found
        String[] args_with_flag = new String[]{"-h","test","-o","-f"};
        assertEquals(Args.getOption(option_list, args_with_flag), true);
        
        //second test: flag is not expected to be found
        String[] args_without_flag = new String[]{"-h","test","--add","-f"};
        assertEquals( Args.getOption(option_list, args_without_flag), false);
    }

    /**
     * Test of getIntegerOption method, of class Args.
     */
    @Test
    public void testGetIntegerOption() {
        System.out.println("getIntegerOption");
        
        //first test: option is expected to be found
        String[] args_with_flag = new String[]{"-h","test","--option","34"};
        assertEquals(Args.getIntegerOption(option_name, args_with_flag, 88), 34);
        
        //second test: option is not expected to be found, default value should be used
        String[] args_without_flag = new String[]{"-h","test","--add","-f"};
        assertEquals( Args.getIntegerOption(option_name, args_without_flag,88), 88);
        
    }

    /**
     * Test of getIntegerOption method, of class Args.
     */
    @Test
    public void testGetIntegerOptionMultipleName() {
        System.out.println("getIntegerOption with a multiple name option");
        
        //first test: option is expected to be found
        String[] args_with_flag = new String[]{"-h","test","-o","34"};
        assertEquals(Args.getIntegerOption(option_list, args_with_flag, 88), 34);
        
        //second test: option is not expected to be found, default value should be used
        String[] args_without_flag = new String[]{"-h","test","--add","-f"};
        assertEquals( Args.getIntegerOption(option_list, args_without_flag,88), 88);
    }

    /**
     * Test of getStringOption method, of class Args.
     */
    @Test
    public void testGetStringOption() {
        System.out.println("getStringOption");
        
        //first test: option is expected to be found
        String[] args_with_flag = new String[]{"-h","test","--option","wonderful"};
        assertEquals(Args.getStringOption(option_name, args_with_flag, "well..."), "wonderful");
        
        //second test: option is not expected to be found, default value should be used
        String[] args_without_flag = new String[]{"-h","test","--add","-f"};
        assertEquals( Args.getStringOption(option_name, args_without_flag,"well..."), "well...");
    }

    /**
     * Test of getStringOption method, of class Args.
     */
    @Test
    public void testGetStringOptionMultipleName() {
        System.out.println("getStringOption with a multiple name option");
        
        //first test: option is expected to be found
        String[] args_with_flag = new String[]{"-h","test","opt","wonderful"};
        assertEquals(Args.getStringOption(option_list, args_with_flag, "well..."), "wonderful");
        
        //second test: option is not expected to be found, default value should be used
        String[] args_without_flag = new String[]{"-h","test","--add","-f"};
        assertEquals( Args.getStringOption(option_list, args_without_flag,"well..."), "well...");
    }
    
    /**
     * Test of the object oriented mode.
     */
    @Test
    public void testOOVersion(){
        System.out.println("object oriented mode with 3 types of arguments");
        String[] input = new String[]{"--p","22","--verbose","--name","Largo", };
        Args parser = new Args(input);
        assertEquals(parser.getOption("--verbose"), true);
        assertEquals(parser.getIntegerOption("--p",0), 22);
        assertEquals(parser.getStringOption("--name","bof"), "Largo");
    }
}
