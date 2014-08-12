package com.simpletasker.lang.commands;

import com.simpletasker.common.exceptions.SimpleTaskException;
import com.simpletasker.lang.Task;
import com.simpletasker.lang.variables.Variable;

/**
 * Created by David on 12-8-2014.
 */
public class MathCommand extends Command {

    public MathCommand() {
        super("Math");
    }

    @Override
    public Variable onCalled(Variable[] params, Task task) throws SimpleTaskException {

        return Variable.voidVariable;
    }
}
