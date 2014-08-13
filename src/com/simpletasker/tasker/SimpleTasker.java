package com.simpletasker.tasker;

/**
 * Created by David on 13-8-2014.
 */
public class SimpleTasker {
    private static SimpleTasker ourInstance = new SimpleTasker();

    private SimpleTasker() {
    }

    public static SimpleTasker getInstance() {
        return ourInstance;
    }



}
