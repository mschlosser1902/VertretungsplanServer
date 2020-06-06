package de.jonas.vplan.sql;

import de.jonas.vplan.main.Main;
import de.jonas.vplan.utils.Data;
import de.jonas.vplan.utils.Vertretung;

import java.sql.SQLOutput;
import java.util.Timer;
import java.util.TimerTask;

public class VplanUpdater{

    private int loadDays;
    public int lenght = 0;

    public VplanUpdater(int loadDays) {
        this.loadDays = loadDays;
        VplanUpdateTasks();
    }

    public synchronized void update() {

        for(String client : Data.clientUpdateList.keySet()) {
            Data.clientUpdateList.put(client, false);
        }
        for(int i = 0; i <= loadDays; i++) {
            Main.vplanSQLMethods.getVertretungsstundenAtDate(Main.vplanSQLMethods.getAddedDate(i, "2020/02/10"));
        }

        //for(Vertretung vertretung : Data._vertretungsList) Main.vplanSQLMethods.removeDoubleStunden(vertretung);

    }

    public void VplanUpdateTasks() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(Data.downloadCounter == loadDays+1) {



                }
            }
        }, 0 , 2000);

    }

}
