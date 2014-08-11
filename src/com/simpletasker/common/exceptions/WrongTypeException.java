package com.simpletasker.common.exceptions;

/**
 * Created by David on 11-8-2014.
 */
public class WrongTypeException extends SimpleTaskException {

    public WrongTypeException() {
        super();
    }

    public WrongTypeException(String message) {
        super(message);
    }

    public WrongTypeException(String message, Throwable cause) {
        super(message, cause);
    }


    public WrongTypeException(Throwable cause) {
        super(cause);
    }

}
