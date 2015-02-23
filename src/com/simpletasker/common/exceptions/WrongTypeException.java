package com.simpletasker.common.exceptions;

/**
 * Created on 11-8-2014.
 */
public class WrongTypeException extends SimpleTaskException {

	private static final long serialVersionUID = -3988318137065325280L;

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
