package inouire.basics.myml;

/**
 * An Exception used for MyMl dedicated errors
 * @author Edouard Garnier de Labareyre
 */
public class MyMlException extends Exception{

  public MyMlException() {
      super();
  }
  
  public MyMlException(String message){
      super(message);
  }

}
