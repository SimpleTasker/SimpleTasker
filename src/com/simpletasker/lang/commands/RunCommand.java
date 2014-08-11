package com.simpletasker.lang.commands;

import com.simpletasker.common.exceptions.WrongTypeException;
import com.simpletasker.lang.variables.StringVariable;
import com.simpletasker.lang.variables.Variable;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

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
            selectedFile = new File(dir,s.substring(s.indexOf("~")));
        } else {
            selectedFile = new File(s);
        }
        try {
            Desktop.getDesktop().open(selectedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
