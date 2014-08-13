package com.simpletasker.lang.commands;

import com.simpletasker.common.exceptions.SimpleTaskException;
import com.simpletasker.lang.Task;
import com.simpletasker.lang.variables.BooleanVariable;
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
                return 2;
            }

            @Override
            public Variable onCalled(Variable[] params, Task task) throws SimpleTaskException {
                String message = params[0].asString();
                String title = params[1].asString();
                JOptionPane.showMessageDialog(null,message,title,JOptionPane.INFORMATION_MESSAGE);
                return Variable.voidVariable;
            }
        };
        new Command(this,"openChoice") {

            @Override
            public int getNumParam() {
                return 2;
            }

            @Override
            public Variable onCalled(Variable[] params, Task task) throws SimpleTaskException {
                String message = params[0].asString();
                String title = params[1].asString();
                int result = JOptionPane.showConfirmDialog(null,message,title,JOptionPane.DEFAULT_OPTION);
                boolean value = false;
                if(result==JOptionPane.OK_OPTION) {
                    value = true;
                }
                return new BooleanVariable(value);
            }
        };
    }

    @Override
    public Variable onCalled(Variable[] params, Task task) throws SimpleTaskException {
        return Variable.voidVariable;
    }
}
