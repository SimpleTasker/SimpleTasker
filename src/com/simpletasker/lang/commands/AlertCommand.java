package com.simpletasker.lang.commands;

import com.simpletasker.common.exceptions.SimpleTaskException;
import com.simpletasker.lang.Task;
import com.simpletasker.lang.variables.Variable;

import javax.swing.JOptionPane;

/**
 * Created on 23-2-2015.
 */
public class AlertCommand extends Command{

    private static final String defaultText = "";
    private static final String defaultTitle = "Simpletasker alert";


    public AlertCommand() {
        super("alert");
    }

    //if param == 0 just show warning dialog with alert
    //if param == 1 show text as param 1
    //if param == 2 show text as param 1 and title as param 2


    @Override
    public Variable onCalled(Variable[] params, Task task) throws SimpleTaskException {
        if(params.length >= 2) {
            showAlert(params[0].asString(),params[1].asString());
        } else if(params.length == 1) {
            showAlert(params[0].asString(),defaultTitle);
        } else {
            showAlert(defaultText, defaultTitle);
        }
        return Variable.voidVariable;
    }

    protected void showAlert(String text, String title) {
        JOptionPane.showMessageDialog(null,text,title,JOptionPane.INFORMATION_MESSAGE);
    }

}
