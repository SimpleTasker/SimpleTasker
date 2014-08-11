package com.simpletasker.lang;

import com.simpletasker.common.util.FileUtilities;
import com.simpletasker.lang.commands.Command;
import com.simpletasker.lang.commands.RunCommand;
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
    public Command[] getCommand(String nm) {
//        String last = nm.substring(nm.lastIndexOf("."),nm.length()-1);
//        String pre = nm.substring(0,nm.lastIndexOf("."));
//        for(Command c:commands) {
//
//        }
        return new Command[]{new RunCommand(),new Command("test") {

			@Override
			public void onCalled(Variable[] params) {
			}
        }, new Command("taata") {

			@Override
			public void onCalled(Variable[] params) {
			}
        }};
    }

    public static void rawRun(String task) {
        new Task(task).run();
    }

    public void init() {
        commands.add(new RunCommand());
    }

    public static final String rawArg = "-raw";

    public static final String testArg = "-test";

    public static void main(String[] args) {
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
