package com.simpletasker.tasker;

import com.simpletasker.common.util.FileUtilities;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private TrayIcon trayTasker;

    private Image noText;
    private Image noTextWarning;
    private Image text;
    private Image textGreen;
    private Image textRed;
    private Image textWarning;

    private SimpleTasker() {
    }

    public static SimpleTasker getInstance() {
        if(!ourInstance.initDone) {
            ourInstance.init();
        }
        return ourInstance;
    }

    public static void main(String[] args) {
        SimpleTasker.getInstance();
    }

    public void init() {
        initDone = true;
        if(scheduledFuture==null){
            scheduledFuture = scheduler.scheduleAtFixedRate(getInstance(), 0, 1, TimeUnit.MINUTES);
        }

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
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
            MenuItem openItem = new MenuItem("Open");
            openItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
            popupMenu.add(openItem);

            MenuItem disableItem = new MenuItem("Disable all");
            disableItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
            popupMenu.add(disableItem);

            MenuItem enableItem = new MenuItem("Enable all");
            enableItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
            popupMenu.add(enableItem);

            MenuItem exitItem = new MenuItem("Exit");
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
    }

    public void exit() {
        scheduledFuture.cancel(false);
        if(SystemTray.isSupported() && trayTasker!=null) {
            SystemTray systemTray = SystemTray.getSystemTray();
            systemTray.remove(trayTasker);
        }
    }

}