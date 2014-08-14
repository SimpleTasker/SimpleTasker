package com.simpletasker.tasker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by David on 13-8-2014.
 */
public class SimpleTasker implements Runnable {
    private static SimpleTasker ourInstance = new SimpleTasker();
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> scheduledFuture;
    private List<TaskInfo> tasks = new ArrayList<>();
    private boolean initDone = false;

    private SimpleTasker() {
    }

    public static SimpleTasker getInstance() {
        if(!ourInstance.initDone) {
            ourInstance.init();
        }
        return ourInstance;
    }

    public void init() {
        initDone = true;
        if(scheduledFuture==null){
            scheduledFuture = scheduler.scheduleAtFixedRate(getInstance(), 0, 1, TimeUnit.MINUTES);
        }


    }

    public void init() {
        initDone = true;
        if(scheduledFuture==null){
            scheduledFuture = scheduler.scheduleAtFixedRate(getInstance(), 0, 1, TimeUnit.MINUTES);
        }


    }

    public void run() {
        Calendar calendar = Calendar.getInstance();
        int day = (calendar.get(Calendar.DAY_OF_WEEK) + 5)%7;
        int hour = calendar.get(Calendar.HOUR_OF_DAY) -1;
        int minute = calendar.get(Calendar.MINUTE) -1;
        System.out.println("TIME: " + day + " days " + hour + ":" + minute);
        for(TaskInfo info:tasks) {
            if(info.isEnabled()) {
                if(info.runAt(day,hour,minute)){
                    info.execute();
                }
            }
        }
    }    public void run() {
        Calendar calendar = Calendar.getInstance();
        int day = (calendar.get(Calendar.DAY_OF_WEEK) + 5)%7;
        int hour = calendar.get(Calendar.HOUR_OF_DAY) -1;
        int minute = calendar.get(Calendar.MINUTE) -1;
        System.out.println("TIME: " + day + " days " + hour + ":" + minute);
        for(TaskInfo info:tasks) {
            if(info.isEnabled()) {
                if(info.runAt(day,hour,minute)){
                    info.execute();
                }
            }
        }
    }public static void main(String[] args) {
        SimpleTasker.getInstance();
    }

}
