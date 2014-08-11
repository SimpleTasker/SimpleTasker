package com.simpletasker.lang;

import com.simpletasker.common.util.FileUtilities;
import com.simpletasker.lang.commands.Command;
import com.simpletasker.lang.commands.RunCommand;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Executor {

    private static final List<Command> commands = new ArrayList<>();
    private static Executor theExecutor;

    public static Executor getInstance() {
        return theExecutor;
    }

    /**
     * Will return all possible commands starting with the string given.<br/>
     * For example "Ma" will return Math and "Math." will return all possible commands in the Math tree
     * @param nm
     * @return
     */
    public Command[] getCommands(String nm) {
        String last = nm.substring(nm.lastIndexOf("."),nm.length()-1);
        String pre = nm.substring(0, nm.lastIndexOf("."));
        List<Command> found = new ArrayList<>();
        for(Command c:commands) {
            if(pre.isEmpty()&&c.name().startsWith(last)) {
                found.add(c);
                continue;
            }
            if(pre.startsWith(c.name()) && (pre.charAt(c.name().length() + 1))=='.') {

            }
        }
        return found.toArray(new Command[0]);
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

    }

    public static final String rawArg = "-raw";

    public static final String testArg = "-test";

    public static void main(String[] args) {
        theExecutor = new Executor();
        getInstance().init();
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
