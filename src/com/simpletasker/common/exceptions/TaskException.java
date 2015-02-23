package com.simpletasker.common.exceptions;

/**
 * Created on 12-8-2014.
 */
public class TaskException extends SimpleTaskException{

	public static final String atLine = " at line ";
	private static final long serialVersionUID = -2198019733905049170L;

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
