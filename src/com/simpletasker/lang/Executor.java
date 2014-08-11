package com.simpletasker.lang;

import com.simpletasker.lang.commands.Command;
import com.simpletasker.lang.commands.RunCommand;

import java.util.ArrayList;
import java.util.List;

public class Executor {

    private static final List<Command> commands = new ArrayList<>();



    /**
     * Will return all possible commands starting with the string given.<br/>
     * For example "Ma" will return Math and "Math." will return all possible commands in the Math tree
     * @param nm
     * @return
     */
    public Command[] getCommand(String nm) {
        String last = nm.substring(nm.lastIndexOf("."),nm.length()-1);
        String pre = nm.substring(0,nm.lastIndexOf("."));
        for(Command c:commands) {

        }
        return new Command[0];
    }

    public void rawRun(String task) {
        new Task(task).run();
    }

    public void init() {
        commands.add(new RunCommand());
    }


}
