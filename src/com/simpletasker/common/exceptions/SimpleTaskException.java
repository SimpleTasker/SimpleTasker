package com.simpletasker.common.exceptions;

/**
 * Created by David on 11-8-2014.
 */
public class SimpleTaskException extends Exception{

	private static final long serialVersionUID = 1770398066112854944L;

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
