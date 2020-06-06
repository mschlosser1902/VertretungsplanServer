package de.jonas.vplan.server;

import de.jonas.vplan.main.Main;
import de.jonas.vplan.utils.Data;

import java.net.Socket;

public class Client{

    private Socket client;

    public Client(Socket client) {
        this.client = client;
        System.out.println("\nClient wurde registriert!");

        //VPLAN ALREADY SENT CHECK


        if (!Data.clientUpdateList.containsKey(this.client.getInetAddress().toString())) {
            Data.clientUpdateList.put(this.client.getInetAddress().toString(), false);
            Main.server.sendNull(this.client);
            Main.commandManager.setLastLineOfConsole();
        }

        if (Data.clientUpdateList.get(this.client.getInetAddress().toString()) == false) {
            Main.server.sendVertretungsstunden(this.client);
            Data.clientUpdateList.put(this.client.getInetAddress().toString(), true);
        } else {
            Main.commandManager.setLastLineOfConsole();
        }

    }


    public Socket getClient() {
        return client;
    }
}
