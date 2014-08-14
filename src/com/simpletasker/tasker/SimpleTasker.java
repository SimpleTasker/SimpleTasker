package com.simpletasker.tasker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by David on 13-8-2014.
 */
public class SimpleTasker implements Runnable {
    private static SimpleTasker ourInstance = new SimpleTasker();
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    private SimpleTasker() {
    }

    public static SimpleTasker getInstance() {
        return ourInstance;
    }

    public void init() {
        scheduler.scheduleAtFixedRate(getInstance(),0,1, TimeUnit.MINUTES);
    }

    public void run() {

    }

}
