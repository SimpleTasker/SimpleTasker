package com.simpletasker.common.exceptions;

/**
 * Created by David on 11-8-2014.
 */
public class SimpleTaskException extends Exception{

    public SimpleTaskException() {
        super();
    }

    public SimpleTaskException(String message) {
        super(message);
    }

    public SimpleTaskException(String message, Throwable cause) {
        super(message, cause);
    }


    public SimpleTaskException(Throwable cause) {
        super(cause);
    }

}
