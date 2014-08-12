package com.simpletasker.lang.commands;

import com.simpletasker.common.exceptions.SimpleTaskException;
import com.simpletasker.lang.Task;
import com.simpletasker.lang.variables.Variable;

/**
 * Created by David on 12-8-2014.
 */
public class DialogCommand extends Command {
    public DialogCommand() {
        super("Dialog");
        new Command(this,"openSimple") {
            @Override
            public Variable onCalled(Variable[] params, Task task) throws SimpleTaskException {
                return Variable.voidVariable;
            }
        };
    }

    @Override
    public Variable onCalled(Variable[] params, Task task) throws SimpleTaskException {
        return null;
    }
}
