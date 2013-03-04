package inouire.basics;

import java.io.File;

/**
 * Some static methods to get informations about files and folders
 * @author Edouard Garnier de Labareyre
 */
public class FileStats {
    
    /**
     * Compute the total size of the content of a folder (recursive)
     * @param folder the folder to scan
     * @return the size of the content in bytes
     */
    public static long computeTotalSize(File folder)
    {
        long size_counter=0;
        if(folder.isFile()){
            size_counter+=folder.length();
        }else{
            for(File f : folder.listFiles()){
                if(f.isDirectory()){
                    size_counter+=computeTotalSize(f);
                }else{
                    size_counter+=f.length();
                }
            }
        }
        return size_counter;
    }
    
    /**
     * Get the path of a file relatively to another one
     * @param file the file for which to get the relative path
     * @param relative_to the other file to take as reference
     * @return the path of the file relative to the other file, null if impossible
     */
    public static String getRelativePath(File f , File relative_to){
        if(!f.getAbsolutePath().startsWith(relative_to.getAbsolutePath())){
            return null;
        }else{
            String A=f.getAbsolutePath().substring(relative_to.getAbsolutePath().length()+1);
            String B=f.getAbsolutePath().substring(relative_to.getAbsolutePath().length());
            String os = System.getProperty("os.name").toLowerCase();
            if(os.contains("windows")){
                if(relative_to.getAbsolutePath().length()<=3){//basic volume like C:\
                    return B;
                }else{
                    return A;
                }
            }else{
                if(B.startsWith("/")){
                    return A;
                }else{
                    return B;
                }
            }
        }
    }
}
