package inouire.basics;

import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author edouard
 */
public class FileStatsTest {
    
    public FileStatsTest() {
    }
    
    /**
     * Test of getRelativePath method, of class FileStats.
     */
    @Test
    public void testGetRelativePath()
    {
        System.out.println("getRelativePath");
        File ref = new File("/this/is");
        File file = new File("/this/is/a/test.txt");
        
        String result = FileStats.getRelativePath(file,ref);
        assertEquals("a/test.txt", result);
    }
}