package inouire.basics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * A class to help reading txt files by loading it into a String array
 * Provides some simple options to filter the content (empty lines, comments...)
 * @author Edouard Garnier de Labareyre
 */
public class TxtFileLoader {
    
    private String comment_pattern;
    private boolean ignore_empty_lines;
    private boolean trim_lines;
    private boolean trim_comments;
    
    /**
     * Default constructor, with default loading options
     */
    public TxtFileLoader(){
        this.comment_pattern = "//";
        this.ignore_empty_lines = true;
        this.trim_lines = true;
        this.trim_comments = false;
    }
    
    /**
     * Adjust comment pattern
     * @param comment_pattern the comment string pattern
     * @return this
     */
    public TxtFileLoader setCommentPattern(String comment_pattern){
        this.comment_pattern = comment_pattern;
        return this;
    }
    
    /**
     * Adjust 'ignore empty lines' option
     * @param ignore_empty_lines if true, empty lines will be ignored
     * @return this
     */
    public TxtFileLoader ignoreEmptyLines(boolean ignore_empty_lines){
        this.ignore_empty_lines = ignore_empty_lines;
        return this;
    }
    
    /**
     * Adjust 'trim lines' option
     * @param trim_lines if true, all lines will be trimed from starting and ending spaces
     * @return this
     */
    public TxtFileLoader trimLines(boolean trim_lines){
        this.trim_lines = trim_lines;
        return this;
    }
    
    /**
     * Adjust 'trim comments' option
     * @param trim_comments if true, comments will be trimed according to the comment pattern
     * @return this
     */
    public TxtFileLoader trimComments(boolean trim_comments){
        this.trim_comments = trim_comments;
        return this;
    }
    
    /**
     * Load the content of a txt file into a String array (one item per line)
     * according to the loader options
     * @param input_file path to the file to load
     * @return the content of the file as an array
     * @throws FileNotFoundException if the requested file cannot be found
     */
    public ArrayList<String> loadFile(String input_file) throws FileNotFoundException{
        return loadFile(new File(input_file));
    }
    
    /**
     * Load the content of a txt file into a String array (one item per line)
     * according to the loader options
     * @param input_file the file to load
     * @return the content of the file as an array
     * @throws FileNotFoundException if the requested file cannot be found
     */
    public ArrayList<String> loadFile(File input_file) throws FileNotFoundException{
        
        //init input, output and temp structures
        FileInputStream inputStream;
        inputStream = new FileInputStream(input_file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        ArrayList<String> file_raw_content = new ArrayList<String>();
        ArrayList<String> file_content = new ArrayList<String>();
        String line, line_temp;
        
        //read the content
        try{
            line=reader.readLine();
            while(line != null){
                file_raw_content.add(line);
                line=reader.readLine();
            }
        }catch (IOException ioe){
            return null;
        }
        
        //filter the content according to the loader options
        for(String raw_line : file_raw_content){
            line_temp = raw_line;
            if(trim_comments){
                if(line_temp.contains(comment_pattern)){
                    line_temp =  line_temp.split(comment_pattern)[0];
                }
            }
            if(trim_lines){
                line_temp = line_temp.trim();
            }
            if(ignore_empty_lines){
                if(line_temp.length()>0){
                    file_content.add(line_temp);
                }
            }else{
                file_content.add(line_temp);
            }
        }
        
        return file_content;
    }
     
}
