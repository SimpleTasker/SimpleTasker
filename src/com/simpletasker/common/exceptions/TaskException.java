package com.simpletasker.common.exceptions;

/**
 * Created by David on 12-8-2014.
 */
public class TaskException extends SimpleTaskException{

    public static final String atLine = " at line ";

    public TaskException() {
        super();
    }

    public TaskException(String message,int line) {
        super(message+atLine+line);
    }

    public TaskException(String message,int line, Throwable cause) {
        super(message+atLine+line, cause);
    }


    public TaskException(Throwable cause) {
        super(cause);
    }

}
