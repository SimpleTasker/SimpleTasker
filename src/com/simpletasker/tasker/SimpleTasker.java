package com.simpletasker.tasker;

import com.simpletasker.Lib;
import com.simpletasker.common.util.FileUtilities;
import com.simpletasker.lang.Executor;
import com.simpletasker.lang.Task;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by David on 13-8-2014.
 * SimpleTasker project
 */
public class SimpleTasker implements Runnable {
    private static SimpleTasker ourInstance = new SimpleTasker();
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> scheduledFuture;
    private List<TaskInfo> tasks = new ArrayList<>();
    private TrayIcon trayTasker;

    private Image noText;
    private Image noTextWarning;
    private Image text;
    private Image textGreen;
    private Image textRed;
    private Image textWarning;
    private boolean error;

    private SimpleTasker() {
        init();
    }

    public static SimpleTasker getInstance() {
        return ourInstance;
    }

    public static void main(String[] args) {
        SimpleTasker.getInstance().loadTaskFromFile(new File("test.stsk"));
    }

    private void init() {
        if(scheduledFuture==null){
            scheduledFuture = scheduler.scheduleAtFixedRate(this, 0, 1, TimeUnit.MINUTES);
        }

        String baseFolder = "res/icons/";
        noText = FileUtilities.loadImageFromResources(baseFolder + "noText.png");
        noTextWarning = FileUtilities.loadImageFromResources(baseFolder + "noTextWarning.png");
        text = FileUtilities.loadImageFromResources(baseFolder + "text.png");
        textGreen = FileUtilities.loadImageFromResources(baseFolder + "textGreen.png");
        textRed = FileUtilities.loadImageFromResources(baseFolder + "textRed.png");
        textWarning = FileUtilities.loadImageFromResources(baseFolder + "textWarning.png");


        if(SystemTray.isSupported() && trayTasker==null) {
            final SystemTray systemTray = SystemTray.getSystemTray();
            PopupMenu popupMenu = new PopupMenu();
            trayTasker = new TrayIcon(noText,"Simple Tasker",popupMenu);
            trayTasker.setImageAutoSize(true);
            MenuItem openItem = new MenuItem(Lib.getLang("main.open"));
            openItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
            popupMenu.add(openItem);

            MenuItem disableItem = new MenuItem(Lib.getLang("main.disAll"));
            disableItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for(TaskInfo ti:tasks) {
                        ti.setEnabled(false);
                    }
                }
            });
            popupMenu.add(disableItem);

            MenuItem enableItem = new MenuItem(Lib.getLang("main.enbAll"));
            enableItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for(TaskInfo ti:tasks) {
                        ti.setEnabled(true);
                    }
                }
            });
            popupMenu.add(enableItem);

            MenuItem exitItem = new MenuItem(Lib.getLang("main.exit"));
            exitItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    exit();
                }
            });
            popupMenu.add(exitItem);
            try {
                systemTray.add(trayTasker);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        if(tasks.isEmpty()) {
            trayTasker.setImage(noText);
            return;
        }
        if(!tasks.isEmpty() && trayTasker!=null) {
            trayTasker.setImage(textGreen);
        }
        Calendar calendar = Calendar.getInstance();
        int day = (calendar.get(Calendar.DAY_OF_WEEK) + 5)%7;
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        System.out.println("TIME: " + day + " days " + hour + ":" + minute);
        for(TaskInfo info:tasks) {
            if(info.isEnabled()) {
                if(info.runAt(day,hour,minute)){
                    info.execute();
                }
            }
        }
        error = Executor.getInstance().hasError();

        if(trayTasker!=null) {
            trayTasker.setImage(text);
            if(error) {
                if(tasks.isEmpty()) {
                    trayTasker.setImage(noTextWarning);
                } else {
                    trayTasker.setImage(textWarning);
                }
            }
        }
    }

    public void exit() {
        scheduledFuture.cancel(true);
        if(SystemTray.isSupported() && trayTasker!=null) {
            SystemTray systemTray = SystemTray.getSystemTray();
            systemTray.remove(trayTasker);
        }
        System.exit(0);
    }


    public void loadTaskFromFile(File f) {
        File parent = f.getParentFile();
        if(parent==null) {
            parent = new File("");
        }
        String taskData = FileUtilities.getStringfromFile(f);
        tasks.add(new TaskInfo(new Task(taskData),parent));
    }
}
