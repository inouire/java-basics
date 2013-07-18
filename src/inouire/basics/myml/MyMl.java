package inouire.basics.myml;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import inouire.basics.TxtFileLoader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A simple tree structure made to contain configuration information.
 * Values of the tree can then be accessed by string keys like 'foo.bar.lol'
 * @author Edouard Garnier de Labareyre
 */
public class MyMl {
 
    private Element root=null;

    private static Pattern key_pattern=Pattern.compile("(\\s*)([a-zA-Z0-9]+)\\s*:");
    private static Pattern key_value_pattern = Pattern.compile("(\\s*)([a-zA-Z0-9]+)\\s*:(.+)");
        
    public final static int INDENT_LENGTH = 4;
    
    public MyMl(){
        this.root = new Element("root");
    }
    
    /**
     * Explore the MyMl object to find a value for the given absolute key
     * @param absolute_key the absolute key of the object to look for
     * @return the value if it is found
     * @throws MyMlException if it can't be found
     */
    public String getValue(String absolute_key) throws MyMlException{
        String[] keys = absolute_key.trim().split("\\.");
        Element current = root;
        Element next;
        for(String key : keys){
            if(current.isLeaf()){
                throw new MyMlException(key+" is a value therefore cannot be explored. Please check the full key '"+absolute_key+"'");
            }
            next = current.get(key);
            if(next!=null){
                current = next;
            }else{
                throw new MyMlException("'"+key+"' key does not exist in '"+current.getKey()+"'. Please check the full key '"+absolute_key+"'");
            }
        }
        return current.getValue();
    }
     
    /**
     * Get the boolean value for the given absolute key
     * This function will silently ignore MyMl errors by using provided default value
     * So it should be used only on a MyMl structure that has been validate with MyMlValidator
     * @param absolute_key the absolute key of the value to look for
     * @default_value the value to use if key is not found or boolean conversion doesn't work
     * @return the boolean value found for this key
     */
    public boolean getBool(String absolute_key, boolean default_value){
        String value = "";
        try{
            value = getValue(absolute_key).toLowerCase();
        }catch(MyMlException ex){
            //silently ignore it, default value will be used
        }
        boolean boolean_value=default_value;
        if(value.equalsIgnoreCase("true")||value.equalsIgnoreCase("yes")||value.equals("1")){
            boolean_value=true; 
        }else if(value.equalsIgnoreCase("false")||value.equalsIgnoreCase("no")||value.equals("0")){
            boolean_value=false;
        }
        return boolean_value;
    }
    
     /**
     * Get the integer value for the given absolute key
     * This function will silently ignore MyMl errors by using provided default value
     * So it should be used only on a MyMl structure that has been validate with MyMlValidator
     * @param absolute_key the absolute key of the value to look for
     * @default_value the value to use if key is not found or integer conversion doesn't work
     * @return the integer value found for this key
     */
    public int getInt(String absolute_key, int default_value){
        String value = "";
        try{
            value = getValue(absolute_key).toLowerCase();
        }catch(MyMlException ex){
            //silently ignore it, default value will be used
        }
        int integer_value=default_value;
        try{
            integer_value = Integer.parseInt(value);
        }catch(NumberFormatException nfe){
            //silently ignore it, default value will be used
        }
        return integer_value;
    }
    
    /**
     * Get the list of the absolute keys of the structure
     * @return a list containing all the absolute keys
     */
    public ArrayList<String> getAbsoluteKeyList(){
        ArrayList<String> absolute_key_list = new ArrayList<String>();
        for(Element e : root.getSubEntries()){
            absolute_key_list.addAll(e.getRelativeKeyList());
        }
        return absolute_key_list;
    }
    
    /**
     * 
     * @param lines
     * @return
     * @throws MyMlException
     * @throws FileNotFoundException 
     */
    public static MyMl loadContent(ArrayList<String> lines)throws MyMlException{
        
        //create the object we are about to build
        MyMl building = new MyMl();
        
        //init temp variables
        int indent, expected_indent=0;
        String key, value;
        Element current = building.root;
        Matcher key_matcher, key_value_matcher;
        
        //parse lines of the file, one by one
        for(String line : lines){
            
            //try key/value matcher
            key_value_matcher= key_value_pattern.matcher(line);
            if(key_value_matcher.find() && key_value_matcher.groupCount()==3){
                indent = key_value_matcher.group(1).length();
                key    = key_value_matcher.group(2).trim();
                value  = key_value_matcher.group(3).trim();
            }else{
                //try key matcher
                key_matcher= key_pattern.matcher(line);
                if(key_matcher.find() && key_matcher.groupCount()==2){
                    indent = key_matcher.group(1).length();
                    key    = key_matcher.group(2).trim();
                    value  = null;
                }else{//ignore line
                    continue;
                }
            }
            
            //map key or key/value entry
            Element k = new Element(key, value);
            
            //check indentation
            if(indent > expected_indent){
                int delta_indent = indent-expected_indent;
                throw new MyMlException("Two big indentation on line '"+line.trim()+"' ("+delta_indent+ " unexpected spaces detected)");
            }
            
            //use indentation to navigate into the object we are currently building
            if(indent < expected_indent){
                while(current.getIndent()!=indent){
                    current = current.getParent();
                }
                current = current.getParent();
            }
            
            //populate structure and prepare next iteration
            current.add(k);
            if(k.isLeaf()){
                expected_indent = indent;
            }else{
                current = k;
                expected_indent = indent + MyMl.INDENT_LENGTH;
            }
        }
        return building;
    }
    
    /**
     * Load and parse a MyMl file
     * @param file the file to load
     * @throws FileNotFoundException if the file cannot be found
     * @throws MyMlException if file has any format issue
     */
    public static MyMl loadFile(String file) throws MyMlException, FileNotFoundException{
        
        //load file
        TxtFileLoader loader = new TxtFileLoader()
                                  .trimComments(true)
                                  .setCommentPattern("#")
                                  .ignoreEmptyLines(true)
                                  .trimLines(false);
        ArrayList<String> lines = loader.loadFile(file);
        
        //pass it to content loader 
        return loadContent(lines);
        
    }
    
    /**
     * Write the content of a MyMl object into a file
     * @param file the file in which to write
     * @throws IOException  if there is an error during writing
     */
    public void writeToFile(String file) throws IOException{
        File toWriteTo = new File(file);
        if(!toWriteTo.getParentFile().exists()) toWriteTo.getParentFile().mkdirs();
        FileWriter fw = new FileWriter(file);
        fw.write(this.toString());
        fw.close();
    }
    
    @Override
    public String toString(){
        String s = "";
        for(Element e : root.getSubEntries()){
            s+=e.toString();
        }
        return s;
    }
}
