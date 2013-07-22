package inouire.basics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;

/**
 * A class to extract content from current jar
 * @author Edouard Garnier de Labareyre
 */
public class JarContentExtractor {

    /**
     * Extract the content of a file in the jar to a String
     * @param path_in_jar the path of the file in the ressource jar
     * @return the content of the file as a String, null if there is an error
     */
    public static String extractContentFromJarToString(String path_in_jar){
        byte[] content_bytes = extractContentFromJarToByteArray(path_in_jar);
        String content_string;
        if(content_bytes!=null){
            content_string = bytes2String(content_bytes);
        }else{
            content_string = null;
        }
        return content_string;
    }
    
    /**
     * Extract a file contained in the jar to a file on the disk
     * @param path_in_jar the absolute path in the jar of the file to export
     * @param file_path the path of the output file on the disk
     * @return the created file if success, null if error
     */
    public static File extractContentFromJarToFile(String path_in_jar,String file_path) throws FileNotFoundException{

        //load input stream from jar
        InputStream ressource = JarContentExtractor.class.getResourceAsStream(path_in_jar);

        // write the inputStream to a FileOutputStream
        File output_file = new File(file_path);
        OutputStream out = new FileOutputStream(output_file);

        //copy from one stream to another
        try{
            int bytesWritten = 0;
            int byteCount;
            byte[] bytes = new byte[1024];
            while ((byteCount = ressource.read(bytes)) != -1) {
                out.write(bytes, bytesWritten, byteCount);
                bytesWritten += byteCount;
            }
            ressource.close();
            out.flush();
            out.close();
            
        }catch(IOException ioe){
            output_file = null;
        }
        
        return output_file;
        
    }
    
    /**
     * Extract the content of a file in the jar  to a byte array
     * @param path_in_jar the path of the file in the ressource jar
     * @return the content of the file as a byte array, null if there is an error
     */
    public static byte[] extractContentFromJarToByteArray(String path_in_jar){
        InputStream resource = JarContentExtractor.class.getResourceAsStream(path_in_jar);
        byte[] buffer = new byte[1];
        LinkedList<Byte> content=new LinkedList<Byte>();
        try {
            //reading byte per byte
            while (resource.read(buffer) != -1) {
                content.add(buffer[0]);
            }
            resource.close();
            byte[] result = new byte[content.size()];
            int k=0;
            for(Byte b : content){
                result[k]=b;
                k++;
            }
            return result;
        } catch (IOException ioe){
            return null;
        }
    }
    
    /**
     * Convert a byte array into a String
     * @param bytes the byte array to convert
     * @return the converted String
     */
    private static String bytes2String( byte[] bytes ){
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < bytes.length; i++){
            stringBuffer.append( (char) bytes[i] );
        }
        return stringBuffer.toString();
    }
   
}
