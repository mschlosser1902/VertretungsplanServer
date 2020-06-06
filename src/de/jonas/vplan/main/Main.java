package de.jonas.vplan.main;

import de.jonas.vplan.Date;
import de.jonas.vplan.command.CommandManager;
import de.jonas.vplan.server.Server;
import de.jonas.vplan.sql.SQL;
import de.jonas.vplan.sql.VplanSQLMethods;
import de.jonas.vplan.sql.VplanUpdater;
import de.jonas.vplan.utils.Data;

import java.sql.SQLException;

public class Main {

    public static Server server;
    public static CommandManager commandManager;
    public static Date date;

    public static SQL mysql;
    public static VplanSQLMethods vplanSQLMethods;
    public static VplanUpdater updater;

    public static String _dateToSend;

    public static void main(String[] args) {

        mysql = new SQL(Data.host, Data.database, Data.user, Data.password);
        mysql.connect();

        server = new Server(1111);
        server.boot();

        date = new Date();
        _dateToSend = date.getYear() + "" + date.getMonth() + "" + date.getDay();

        System.out.println(" ");
        vplanSQLMethods = new VplanSQLMethods();
        updater = new VplanUpdater(2);
        updater.update();

        commandManager = new CommandManager();
        commandManager.start();

    }

}
