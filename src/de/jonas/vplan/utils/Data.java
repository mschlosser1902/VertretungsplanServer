package de.jonas.vplan.utils;

import de.jonas.vplan.server.Client;

import java.util.ArrayList;
import java.util.HashMap;

public class Data {

   //public static final String host = "45.85.217.201";
   //public static final String database = "fls";
   //public static final String user = "phpmyadmin";
   //public static final String password = "REPLQ1zri7JSfHGj";

    public static final String host = "localhost";
    public static final String database = "fls";
    public static final String user = "phpmyadmin";
    public static final String password = "";

    public static ArrayList<Vertretung> _vertretungsList = new ArrayList<>();
    public static ArrayList<Vertretung> _vstundenToAdd = new ArrayList<>();

    public static HashMap<String, Boolean> clientUpdateList = new HashMap<>();

    public static ArrayList<String> clientList = new ArrayList<>();

    public static boolean acceptClients = true;

    public static int downloadCounter = 0;



}
