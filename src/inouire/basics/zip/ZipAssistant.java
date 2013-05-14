package inouire.basics.zip;

import inouire.basics.FileStats;
import java.io.*;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * A simple class to zip a file or a folder, keeping the directory structure
 * @author Edouard Garnier de Labareyre
 */
public class ZipAssistant {
    
    private static final int BUFFER = 2048;

    public File file_to_compress;
    public long total_size=0;
    public long file_size_counter=0;
    
    private ZipOutputStream zos;
    private ZipProcessListener process_listener;
  
    /**
     * Default constructor.
     */
    public ZipAssistant()
    {
        this.process_listener = new DefaultProcessListener();
    }
    
    /**
     * Constructor with a listener that will be called during compression process
     * @param listener 
     */
    public ZipAssistant(ZipProcessListener listener)
    {
        this.process_listener = listener;
    }
    
    /**
     * Compress a folder as a zip file.
     * The original files are not modified, and the zip has the same directory structure
     * @param input_folder the folder to compress
     * @param output_zip_file the resulting zip file
     * @throws Exception 
     */
    public void compressToZip(File input_folder, File output_zip_file) throws FileNotFoundException, IOException
    {
        this.file_to_compress = input_folder;
        this.total_size = FileStats.computeTotalSize(input_folder);
        
        FileOutputStream output_zip_stream = new FileOutputStream(output_zip_file);
        BufferedOutputStream buff_output_zip_stream = new BufferedOutputStream(output_zip_stream);
        zos = new ZipOutputStream(buff_output_zip_stream);
        zos.setMethod(ZipOutputStream.DEFLATED);
        zos.setLevel(Deflater.BEST_COMPRESSION);

        try{
            addToZip(file_to_compress);
        }finally{
            zos.close();
            buff_output_zip_stream.close();
            output_zip_stream.close();
        }
    }
    
    /**
     * Add a folder to the zip (called recursively)
     * @param folder the folder to add to the zip
     * @throws IOException
     */
    private void addToZip(File folder) throws IOException{
        
        //case of an empty directory       
        if(folder.listFiles().length==0){
            String entry = FileStats.getRelativePath(folder,file_to_compress)+"/";
            zos.putNextEntry(new ZipEntry(entry));
            zos.closeEntry();
        }else{
            for(File f : folder.listFiles()){
                if(f.isDirectory()){
                    //folder: recursive call
                    addToZip(f);
                }else{
                    String current_entry = FileStats.getRelativePath(f,file_to_compress);
                    process_listener.setCurrentFile(f);
                    //file: writing to buffer to complete zip
                    BufferedInputStream bis=null;
                    try{
                        int count;
                        byte data[] = new byte[BUFFER];
                        bis = new BufferedInputStream(new FileInputStream(f), BUFFER);
                        zos.putNextEntry(new ZipEntry(current_entry));
                        while((count = bis.read(data, 0, BUFFER)) != -1) {
                            zos.write(data, 0, count);
                            file_size_counter+=count;
                            process_listener.setProgress((int)((100*file_size_counter)/total_size));
                        }
                         zos.closeEntry();
                    }finally{
                        if(bis!=null){
                            bis.close();
                        }
                    }
                }
            }
        }
    }
}

class DefaultProcessListener implements ZipProcessListener {

    @Override
    public void setCurrentFile(File current_file) {
        //do nothing
    }

    @Override
    public void setProgress(int percentage) {
        //do nothing
    }
    
}


