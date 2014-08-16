package com.simpletasker.lang.commands;

import com.simpletasker.common.exceptions.WrongTypeException;
import com.simpletasker.lang.Task;
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
    public Variable onCalled(Variable[] params,Task task) throws WrongTypeException {
        String s = params[0].asString();
        File selectedFile;
        if(s.startsWith("~")) {
            File dir = task.getLocation();
            selectedFile = new File(dir,s.substring(s.indexOf("~") + 1));
        } else {
            selectedFile = new File(s);
        }
        System.out.println("File loc:" + selectedFile.getAbsolutePath());
        try {
            Desktop.getDesktop().open(selectedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Variable.voidVariable;
    }
}
