package com.simpletasker.lang;

import com.simpletasker.common.exceptions.TaskException;
import com.simpletasker.lang.commands.Command;

import java.io.File;

/**
 * Created by David on 10-8-2014.
 */
public class Task {

    private String task;
    private File location;

    public Task(String task) {
        this.task = task;
    }

    public void run() throws TaskException {
        String[] lines = task.split("\\n?\\r");
        int line = 0;
        for(String s:lines) {
            String[] operations = s.split(";");
            for(String current:operations) {
                String nm = current.substring(0,current.indexOf("("));
                Command[] commandsFound = Executor.getInstance().getCommands(nm);
                if(commandsFound.length != 0) {
                    throw new TaskException("Amount of commands found too large, Name=" + nm +", commands found=" + commandsFound.length,line);
                }
                Command command = commandsFound[0];
                String[] params = (current.substring(current.indexOf("("),current.lastIndexOf(")"))).split(",");
                if(params.length < command.getNumParam()) {
                    throw new TaskException("Too little parameters given, needed at least "+ command.getNumParam() + " got " + params.length,line);
                }
            }
            line++;
        }
    }

    public File getLocation() {
        return location==null ? new File(""):location;
    }

    public void setLocation(File location) {
        this.location = location;
    }
}
