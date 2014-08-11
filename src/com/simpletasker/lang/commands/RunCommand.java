package com.simpletasker.lang.commands;

import com.simpletasker.lang.variables.Variable;

/**
 * Created by David on 11-8-2014.
 */
public class RunCommand extends Command{


    public RunCommand() {
        super("run");
    }

    @Override
    public int getNumParam() {
        return 1;
    }

    @Override
    public void onCalled(Variable[] params) {

    }
}
