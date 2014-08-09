package com.simpletasker.lang;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 9-8-2014.
 */
public class Command {

    public static final String separator = ".";

    private String name;

    private List<Command> childeren = new ArrayList<>();
    private int numParam = 0;


    public Command(String name) {
        this.name = name;
    }

    public Command(Command parent,String name) {
        this.name = name;
        parent.addChilderen(this);
    }

    private void addChilderen(Command command) {
        childeren.add(command);
    }


    public String getFullName() {
        return name;
    }

    public int getNumParam() {
        return  numParam;
    }

    public void onCalled(Param[] params) {

    }
}
