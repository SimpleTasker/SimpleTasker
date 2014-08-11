package com.simpletasker.lang.commands;

import com.simpletasker.common.exceptions.SimpleTaskException;
import com.simpletasker.lang.Task;
import com.simpletasker.lang.variables.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 9-8-2014.
 */
public abstract class Command {

    public static final String separator = ".";

    private String name;

    private List<Command> children = new ArrayList<>();
    private int numParam = 0;
    Task task;


    public Command(String name) {
        this.name = name;
    }

    public Command(Command parent,String name) {
        this.name = name;
        parent.addChildren(this);
    }

    public void setTask(Task task) {
        this.task = task;
    }

    private void addChildren(Command command) {
        children.add(command);
    }


    public String getFullName() {
        return name;
    }

    /**
     * Should return minimal number of parameters for this command
     * @return minimal number of parameters
     */
    public int getNumParam() {
        return  numParam;
    }

    public abstract void onCalled(Variable[] params) throws SimpleTaskException;
}
