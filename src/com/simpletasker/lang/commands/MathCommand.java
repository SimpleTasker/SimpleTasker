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
        new Command(this,"pow") {

            @Override
            public int getNumParam() {
                return 2;
            }

            @Override
            public Variable onCalled(Variable[] params, Task task) throws SimpleTaskException {
                return new DoubleVariable(Math.pow(params[0].asNumber(),params[1].asNumber()));
            }
        };
        new Command(this,"abs") {

            @Override
            public int getNumParam() {
                return 1;
            }

            @Override
            public Variable onCalled(Variable[] params, Task task) throws SimpleTaskException {
                return new DoubleVariable(Math.abs(params[0].asNumber()));
            }
        };
    }


    @Override
    public Variable onCalled(Variable[] params, Task task) throws SimpleTaskException {
        return Variable.voidVariable;
    }
}
