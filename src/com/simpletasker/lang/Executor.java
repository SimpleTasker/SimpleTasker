package com.simpletasker.lang;

import com.simpletasker.common.exceptions.SimpleTaskException;
import com.simpletasker.common.exceptions.WrongTypeException;
import com.simpletasker.common.util.FileUtilities;
import com.simpletasker.lang.commands.Command;
import com.simpletasker.lang.commands.RunCommand;
import com.simpletasker.lang.variables.StringVariable;
import com.simpletasker.lang.variables.Variable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Executor {

    private static final List<Command> commands = new ArrayList<>();



    /**
     * Will return all possible commands starting with the string given.<br/>
     * For example "Ma" will return Math and "Math." will return all possible commands in the Math tree
     * @param nm
     * @return
     */
    public Command[] getCommands(String nm) {
        String last = nm.substring(nm.lastIndexOf("."),nm.length()-1);
        String pre = nm.substring(0,nm.lastIndexOf("."));
        for(Command c:commands) {

        }
        return new Command[0];
    }

    public static void rawRun(final String task) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new Task(task).run();
            }
        }).start();
    }

    public void init() {
        commands.add(new RunCommand());
        try {
            ((RunCommand)commands.get(0)).onCalled(new Variable[]{new Variable(Variable.Type.STRING,"test.bat")});
        } catch (WrongTypeException e) {
            e.printStackTrace();
        }
    }

    public static final String rawArg = "-raw";

    public static final String testArg = "-test";

    public static void main(String[] args) {
        Executor exec = new Executor();
        exec.init();
        System.out.println(new File("").getAbsolutePath());
        for(int i = 0; i < args.length; i++) {
            if(args[i].equals(rawArg)&&i<args.length-1) {
                rawRun(args[i+1]);
            } else if(args[i].equals(testArg)) {
                rawRun(FileUtilities.getStringfromFile(new File("test.stsk")));
            }
        }
    }


}
