package com.simpletasker.lang;

import java.io.File;

/**
 * Created by David on 10-8-2014.
 */
public class Task {

    private String task;
    private File location;

    public Task(String task) {
        this.task = task;
    }

    public void setLocation(File location) {
        this.location = location;
    }

    public void run() {
        String[] lines = task.split("\\n?\\r");
        for(String s:lines) {
            String[] operations = s.split(";");
            for(String current:operations) {

            }
        }
    }

    public File getLocation() {
        return location==null ? new File(""):location;
    }
}
