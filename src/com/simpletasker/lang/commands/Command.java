package com.simpletasker.lang.commands;

import com.simpletasker.common.exceptions.SimpleTaskException;
import com.simpletasker.lang.Task;
import com.simpletasker.lang.variables.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 9-8-2014.
 */
public abstract class Command implements Comparable<Command>{

    public static final String separator = ".";
    private final Command parent;

    private String name;

    private List<Command> children = new ArrayList<>();
    private int numParam = 0;


    public Command(String name) {
        this.name = name;
        parent = null;
    }

    public Command(Command parent,String name) {
        this.name = name;
        parent.addChildren(this);
        this.parent = parent;
    }
    private void addChildren(Command command) {
        children.add(command);
    }


    public String name() {
        return name;
    }

    public String getFullName() {
        if(parent==null) {
            return name();
        }
        return parent.getFullName() + name();
    }

    /**
     * Should return minimal number of parameters for this command
     * @return minimal number of parameters
     */
    public int getNumParam() {
        return  numParam;
    }

    public abstract void onCalled(Variable[] params,Task task) throws SimpleTaskException;

    public void invokeChildren(String pre, String last) {

    }
    
    @Override
    public int compareTo(Command o) {
    	return name.compareTo(o.name);
    }
}
