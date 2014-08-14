package com.simpletasker.tasker;

import com.simpletasker.lang.Executor;
import com.simpletasker.lang.Task;

import java.io.File;

/**
 * Created by David on 13-8-2014.
 */
public class TaskInfo {

    private Task task;

    private File location;

    private boolean activated = true;

    private boolean[] days = new boolean[7];

    private boolean[] hours = new boolean[24];

    private boolean[] minutes = new boolean[60];

    public TaskInfo(Task task,File loc) {
        this.task = task;
        location = loc;
    }

    public void scheduleDays(boolean[] newDays) {
        if(newDays.length!=7) {
            return;
        }
        days = newDays;
    }

    public void scheduleHours(boolean[] newHours) {
        if(newHours.length!=24) {
            return;
        }
        days = newHours;
    }

    public void scheduleMinutes(boolean[] newMinutes) {
        if(newMinutes.length!=7) {
            return;
        }
        days = newMinutes;
    }

    public void execute() {
        Executor.getInstance().runTaskThread(task);
    }

}
