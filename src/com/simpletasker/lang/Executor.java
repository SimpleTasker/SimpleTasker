package com.simpletasker.lang;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.simpletasker.common.exceptions.SimpleTaskException;
import com.simpletasker.common.util.FileUtilities;
import com.simpletasker.lang.commands.Command;
import com.simpletasker.lang.commands.DialogCommand;
import com.simpletasker.lang.commands.MathCommand;
import com.simpletasker.lang.commands.RunCommand;

public class Executor {

    public static final String rawArg = "-raw";
    public static final String testArg = "-test";
    private static final List<Command> commands = new ArrayList<>();
    private static Executor theExecutor;
    private SimpleTaskException error = null;
    private boolean hasError = false;

    private Executor() {
        init();
    }

    public static Executor getInstance() {
    	if(theExecutor == null)
    		theExecutor = new Executor();
        return theExecutor;
    }

    public static void rawRun(final String task) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new Task(task).run();
                } catch (SimpleTaskException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        Executor.getInstance().test("Math.");
        Executor.getInstance().test("math.abs");
        Executor.getInstance().test("math.abs.");
        for(int i = 0; i < args.length; i++) {
            if(args[i].equals(rawArg)&&i<args.length-1) {
                rawRun(args[i+1]);
            } else if(args[i].equals(testArg)) {
                System.out.println("Running test file");
                rawRun(FileUtilities.getStringfromFile(new File("test.stsk")));
            }
        }

    }

    /**
     * Will return all possible commands starting with the string given.<br/>
     * For example "Ma" will return Math and "Math." will return all possible commands in the Math tree
     * @param nm String of the command searched
     * @return a sorted array.
     */
    public Command[] getCommands(String nm) {
        int lastPoint = nm.lastIndexOf(".");
        lastPoint = lastPoint < 0 ? 0 : lastPoint + 1;
        String last = nm.substring(lastPoint,nm.length());
        String pre = nm.substring(0, lastPoint);
        List<Command> found = new ArrayList<>();
        for(Command c:commands) {
            if(pre.isEmpty()&&c.name().startsWith(last)) {
                found.add(c);
                continue;
            }
            if(pre.equalsIgnoreCase(c.name() + '.')) {
                for(Command cChill:c.getChildren()) {
                    if(cChill.name().startsWith(last)) {
                        found.add(cChill);
                    }
                }
            }
        }
        
        //always get a sorted array.
        Command[] foundArr = found.toArray(new Command[0]);
        Arrays.sort(foundArr);
        return foundArr;
    }

    public void init() {
        commands.add(new RunCommand());
        commands.add(new MathCommand());
        commands.add(new DialogCommand());
    }


    public void runTaskThread(final Task task) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    task.run();
                } catch (SimpleTaskException e) {
                    showError(e);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showError(SimpleTaskException e) {
        hasError = true;
        this.error = e;
    }

    public boolean hasError() {
        return hasError;
    }

    public SimpleTaskException getError() {
        return error;
    }

    private void test(String nm) {
        System.out.println("Name " + nm);
        for(Command c:getCommands(nm)) {
            System.out.print(c.getFullName() + ",");
        }
        System.out.println();
    }
}
