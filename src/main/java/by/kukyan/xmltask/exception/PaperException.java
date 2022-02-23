package by.kukyan.xmltask.exception;

public class PaperException extends Exception{

    public PaperException(){
        super();
    }

    public PaperException(String message){
        super(message);
    }

    public PaperException(Throwable cause){
        super(cause);
    }

    public PaperException(String message, Throwable cause){
        super(message, cause);
    }
}
