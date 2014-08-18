package com.simpletasker.lang;

import com.simpletasker.common.exceptions.SimpleTaskException;
import com.simpletasker.common.exceptions.TaskException;
import com.simpletasker.lang.commands.Command;
import com.simpletasker.lang.variables.Variable;

import java.io.File;

/**
 * Created by David on 10-8-2014.
 */
public class Task {

    public static final String REGEX_BETWEENSTRING = "(?![^\\\"]*\\\")";
    public static final String REGEX_BETWEENDIGIT = "(?![^\\d]*\\d)";

    private String task;
    private File location;

    public Task(String task) {
        this.task = task;
    }

    public void run() throws SimpleTaskException {
        String[] lines = task.split("\\r?\\n|\\r");
        int line = 0;
        System.out.println("Found " + lines.length + " lines");
        for(String s:lines) {
            String[] operations = s.split(";");
            for(String current:operations) {
                System.out.println("Operation::" + current);
                String nm = current.substring(0,current.indexOf("("));
                Command[] commandsFound = Executor.getInstance().getCommands(nm);
                if(commandsFound.length != 1) {
                    throw new TaskException("Amount of commands found not one, Name=" + nm +", commands found=" + commandsFound.length,line);
                }
                Command command = commandsFound[0];
                System.out.println(command.getFullName());
                String[] paramsStr = (current.substring(current.indexOf("(") + 1,current.lastIndexOf(")"))).split("," + REGEX_BETWEENSTRING);//|[," + REGEX_BETWEENDIGIT + "]");
                if(paramsStr.length < command.getNumParam()) {
                    throw new TaskException("Too little parameters given, needed at least "+ command.getNumParam() + " got " + paramsStr.length,line);
                }
                Variable[] params = new Variable[paramsStr.length];
                for(int i = 0; i < params.length; i++) {
                    params[i] = Variable.getVariableFromString(paramsStr[i]);
                    System.out.println(params[i].toString());
                }
                command.onCalled(params,this);
            }
            line++;
        }
    }

    public File getLocation() {
        return (location == null) ? new File(".").getParentFile() : location;
    }

    public void setLocation(File location) {
        this.location = location;
    }
}
