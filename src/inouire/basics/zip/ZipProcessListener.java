package inouire.basics;

import java.io.File;

/**
 * The listener to monitor a zip process
 * @see ZipAssistant
 * @author Edouard Garnier de Labareyre
 */
public interface ZipProcessListener {
    
    /**
     * This function is called each time a new file is handled by the zip process
     * @param current_file the file which is currently being handled by the zip process
     */
    public void setCurrentFile(File current_file);
    
    /**
     * This function is called periodically to inform the progress of the process
     * Progress is computed using the ratio between number of bytes handled and total number of bytes
     * @param percentage the progress percentage of the process
     */
    public void setProgress(int percentage);
    
}
