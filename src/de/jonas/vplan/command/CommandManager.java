package de.jonas.vplan.command;

import de.jonas.vplan.main.Main;
import de.jonas.vplan.sql.SQL;
import de.jonas.vplan.utils.Data;
import de.jonas.vplan.utils.Vertretung;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class CommandManager extends Thread{

    @Override
    public void run() {
        manager();
    }

    public CommandManager() {

    }

    public void manager() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String command = "";

        do {

            setLastLineOfConsole();
            try {
                command = reader.readLine();
                handler(command);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }while(!command.toUpperCase().equalsIgnoreCase("exit"));
        System.exit(0);
    }

    public void handler(String command) throws IOException {
        if(command.toUpperCase().equalsIgnoreCase("CREATE")) {
            System.out.println("Test");
        }else if(command.toUpperCase().equalsIgnoreCase("VERTRETUNG.LIST")) {

            System.out.println(" ");
            System.out.println("---------------- VERTRETUNGSSTUNDEN ----------------");
            System.out.println(" ");

            for(Vertretung _vertretung : Data._vertretungsList) {

                String schultyp = _vertretung.getSchultyp();
                String datum = _vertretung.getDatum();
                String klasse = _vertretung.getKlasse();
                String stunde = _vertretung.getStunde();
                String teacher = _vertretung.getTeacher();
                String fach = _vertretung.getFach();
                String raum = _vertretung.getRaum();
                String vteacher = _vertretung.getVteacher();
                String vfach = _vertretung.getVfach();
                String vraum = _vertretung.getVraum();
                String merkmal = _vertretung.getMerkmal();
                String info = _vertretung.getInfo();

                String _vstundeToSend = schultyp + ";" + datum + ";" + klasse + ";" + stunde + ";" + teacher + ";" + fach + ";" + raum + ";" + vteacher + ";" + vfach + ";" + vraum + ";" + merkmal + ";" + info;
                System.out.println(" >> " + _vstundeToSend);

            }
            System.out.println(" ");
            System.out.println("-----------------------------------------------");
            System.out.println(" -> Es wurden " + Data._vertretungsList.size() + " Stunden auf dem Server geladen!");
            System.out.println("-----------------------------------------------");

        }else if(command.toUpperCase().equalsIgnoreCase("CLEAR")) {

            Data.clientUpdateList.clear();
            System.out.println(" ");
            System.out.println("Die Client-Update-Liste wurde geleert!");
            System.out.println(" ");

        }else if(command.toUpperCase().equalsIgnoreCase("CLIENTS.LIST")) {

            System.out.println(" ");
            System.out.println("---------------- CLIENTS ----------------");
            System.out.println(" ");
            if(Data.clientUpdateList.size() == 0) {
                System.out.println(" >> Es sind keine Clients auf dem Server registriert!");
            }
            for(String client : Data.clientUpdateList.keySet()) {
                System.out.println("  >> " + client + "     |     " + Data.clientUpdateList.get(client));
            }
            System.out.println("  ");
            System.out.println("  ");

        }else if(command.toUpperCase().equalsIgnoreCase("VERTRETUNG.UPDATE")) {

            System.out.println(" ");
            System.out.println("  -> Update Vertretungsliste...");
            Main.updater.update();
            System.out.println("  ");
            System.out.println("  ");

        }else if(command.toUpperCase().equalsIgnoreCase("CONSOLE.CLEAR")) {

            for(int i = 0; i < 50; i++) {
                System.out.println(" ");
            }

        }else if(command.toUpperCase().equalsIgnoreCase("HELP")) {

            System.out.println(" ");
            System.out.println("---------------- HELP ----------------");
            System.out.println(" ");
            System.out.println("  >> clear             -     Leert die Client-List");
            System.out.println("  >> vertretung.list   -     Listet alle Vertretungsstunden auf");
            System.out.println("  >> vertretung.update -     Lädt die Vertretungsliste neu");
            System.out.println("  >> console.clear     -     Leert die Konsole");
            System.out.println("  >> clients.list      -     Listet alle registrierten Clients auf");
            System.out.println("  >> help              -     zeigt alle Kommandos des Servers an");
            System.out.println(" ");
            System.out.println(" ");

        }else {
            System.out.println("Unbekannter Befehl! >> \"help\" für alle Kommandos!");
        }
    }


    public void setLastLineOfConsole() {
        System.out.print("\nadministrator@root >> ");
    }

}
