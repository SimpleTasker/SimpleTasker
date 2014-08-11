package com.simpletasker.lang.commands;

import com.simpletasker.lang.Variable;

/**
 * Created by David on 11-8-2014.
 */
public class RunCommand extends Command{


    public RunCommand(String name) {
        super(name);
    }

    public RunCommand(Command parent, String name) {
        super(parent, name);
    }

    @Override
    public int getNumParam() {
        return 1;
    }

    @Override
    public void onCalled(Variable[] params) {

    }
}
