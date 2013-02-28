package inouire.basics;

import java.io.IOException;
import java.io.OutputStreamWriter;
import org.apache.log4j.*;

/**
 * A useful class to properly log simple things with log4j, without conf file
 * @author Edouard Garnier de Labareyre
 */
public class SimpleLog {

    public static Logger logger = Logger.getLogger(SimpleLog.class);
    private static PatternLayout LAYOUT = new PatternLayout("%d - %-5p - %m%n");
                    
    /**
     * Initiate the logger in a developement configuration (stdout + file in working directory).
     * Log level is DEBUG by default
     */
    public static void initDevConfig()
    {
        //add a console appender
        ConsoleAppender console = new ConsoleAppender();
        console.setWriter(new OutputStreamWriter(System.out));
        console.setLayout(LAYOUT);
        logger.setLevel(Level.DEBUG);
        logger.addAppender(console);
        
        //add a file appender in the current directory
        try{
            logger.addAppender(new FileAppender(LAYOUT,"application.log"));
        }catch(IOException ioe){
            logger.warn("Impossible to add log4j appender to file");
        }
    }
    
    /**
     * Init the logger in a production configuration (file only).
     * Log level is INFO by default
     * @param logFileName the file name where to write the log
     */
    public static void initProdConfig(String logFile)
    {
        try{
            FileAppender file = new FileAppender(LAYOUT,logFile);
            logger.addAppender(file);
            logger.setLevel(Level.INFO);
        }catch(IOException ioe){
            logger.warn("Impossible to add log4j appender to file "+logFile);
        }
    }
    
    /**
     * Specific log function for exceptions to avoid big stack traces in production
     * With this function, stack traces will only appear in TRACE or DEBUG level
     * @param e the exception to log
     */
    public static void logException(Exception e)
    {
        if(logger.getLevel()==Level.TRACE || logger.getLevel()==Level.DEBUG){
            logger.warn("["+e.getClass().getSimpleName()+"] ",e);
        }else{
            logger.warn("["+e.getClass().getSimpleName()+"] "+e.getMessage());
        }
    }
}
