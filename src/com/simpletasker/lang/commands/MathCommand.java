package com.simpletasker.lang.commands;

import com.simpletasker.common.exceptions.SimpleTaskException;
import com.simpletasker.lang.Task;
import com.simpletasker.lang.variables.DoubleVariable;
import com.simpletasker.lang.variables.Variable;

/**
 * Created by David on 12-8-2014.
 */
public class MathCommand extends Command {

    public MathCommand() {
        super("Math");
        new Command(this,"pi") {
            @Override
            public Variable onCalled(Variable[] params, Task task) throws SimpleTaskException {
                return new DoubleVariable(Math.PI);
            }
        };
    }


    @Override
    public Variable onCalled(Variable[] params, Task task) throws SimpleTaskException {
        return Variable.voidVariable;
    }
}
