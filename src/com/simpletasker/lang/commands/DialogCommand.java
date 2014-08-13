package com.simpletasker.lang.commands;

import com.simpletasker.common.exceptions.SimpleTaskException;
import com.simpletasker.lang.Task;
import com.simpletasker.lang.variables.StringVariable;
import com.simpletasker.lang.variables.Variable;

import javax.swing.JOptionPane;

/**
 * Created by David on 12-8-2014.
 */
public class DialogCommand extends Command {
    public DialogCommand() {
        super("Dialog");
        new Command(this,"openSimple") {

            @Override
            public int getNumParam() {
                return 1;
            }

            @Override
            public Variable onCalled(Variable[] params, Task task) throws SimpleTaskException {
                String message = ((StringVariable)params[0].castToSpecific(Variable.Type.STRING)).getActualValue();
                JOptionPane.showMessageDialog(null,message);
                return Variable.voidVariable;
            }
        };
    }

    @Override
    public Variable onCalled(Variable[] params, Task task) throws SimpleTaskException {
        return null;
    }
}
