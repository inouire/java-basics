package inouire.basics;

import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 * Class for testing JarContentExtractor class
 * @author Edouard Garnier de Labareyre
 */
public class JarContentExtractorTest {
    
    @Rule
    public TemporaryFolder temp_folder = new TemporaryFolder();
    
    /**
     * Test of extractContentFromJarToString method, of class JarContentExtractor.
     */
    @Test
    public void testExtractContentFromJarToString() {
        System.out.println("extractContentFromJarToString");
        String path_in_jar = "/inouire/basics/data/sample-file.txt";
        String expResult = "Hello World !";
        String result = JarContentExtractor.extractContentFromJarToString(path_in_jar);
        assertEquals(expResult, result);
    }

    /**
     * Test of extractContentFromJarToFile method, of class JarContentExtractor.
     */
    @Test
    public void testExtractContentFromJarToFile() throws Exception {
        System.out.println("extractContentFromJarToFile");
        File temp_file = temp_folder.newFile("extracted-sample.txt");
        String path_in_jar = "/inouire/basics/data/sample-file.txt";
        File extracted = JarContentExtractor.extractContentFromJarToFile(path_in_jar, temp_file.getAbsolutePath());
        if(!extracted.exists()){
            fail("File has not been created");
        }
        TxtFileLoader txt_loader = new TxtFileLoader();
        assertEquals("Hello World !", txt_loader.loadFile(temp_file).get(0));
    }

}