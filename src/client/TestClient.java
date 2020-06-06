package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TestClient {

    public static void main(String[] args) throws IOException {


        Socket socket = new Socket("45.85.217.201", 1111);

        System.out.println("Client wurde verbunden");

        while(true) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String read = reader.readLine();
                System.out.println(read);
        }

    }

}
