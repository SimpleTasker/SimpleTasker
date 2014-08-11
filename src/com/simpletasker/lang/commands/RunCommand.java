package com.simpletasker.lang.commands;

import com.simpletasker.common.exceptions.WrongTypeException;
import com.simpletasker.lang.variables.StringVariable;
import com.simpletasker.lang.variables.Variable;

import java.io.File;

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
    public void onCalled(Variable[] params) throws WrongTypeException {
        String s = ((StringVariable)params[0].castToSpecific(Variable.Type.STRING)).getActualValue();
        File selectedFile;
        if(s.startsWith("~")) {
            File dir = task.getLocation();
        }
    }
}
