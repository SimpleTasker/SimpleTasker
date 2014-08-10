package com.simpletasker.lang;

public class Executor {

    /**
     * Will return all possible commands starting with the string given.<br/>
     * For example "Ma" will return Math and "Math." will return all possible commands in the Math tree
     * @param nm
     * @return
     */
    public Command[] getCommand(String nm) {
        //todo fix
        return new Command[]{new Command("none")};
    }

    public void rawRun(String task) {
        new Task(task).run();
    }


}
