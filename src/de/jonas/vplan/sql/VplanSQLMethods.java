package de.jonas.vplan.sql;

import de.jonas.vplan.main.Main;
import de.jonas.vplan.utils.Data;
import de.jonas.vplan.utils.Vertretung;
import org.w3c.dom.ls.LSOutput;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class VplanSQLMethods {

    private boolean done = true;

    public VplanSQLMethods() {

    }

    public void getVertretungsstundenAtDate(String date) {

        ArrayList<Vertretung> vertretungsList = new ArrayList<>();

        System.out.println(">>> Lade Daten vom " + date);
        Data.acceptClients = false;

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            ResultSet rs = Main.mysql.getResults("SELECT DISTINCT vpday, vphour, vpstart, vpend, vpclass, vptutor, vpsubject, vproom, vpctutor, vpcsubject, vpcroom, vpnote, vpinfo, vpcourse, vpschool, vptype, vpguid FROM fls_vplan WHERE vpday = '" + date + "'");

            int downloadCount = 0;
            @Override
            public void run() {

                try {

                    int vpid = 0;

                    if(rs.next()) {

                        String schultyp = String.valueOf(rs.getInt("vpschool"));
                        String datum = rs.getDate("vpday").toString();
                        String klasse = rs.getString("vpclass");
                        String stunde = rs.getString("vphour") + "|" + rs.getTime("vpstart").toString() + "|" + rs.getTime("vpend").toString();
                        String teacher = getVpTutor(rs.getInt("vptutor"));
                        String fach = getVpSubject(rs.getInt("vpsubject"));
                        String raum = rs.getString("vproom");
                        String vteacher = getVpcTutor(rs.getInt("vpctutor"));
                        String vfach = getVpcSubject(rs.getInt("vpcsubject"));
                        String vraum = rs.getString("vpcroom");
                        String merkmal = rs.getString("vpnote");
                        String info = rs.getString("vpinfo");

                        Vertretung vertretung = new Vertretung(schultyp, datum, klasse, stunde, teacher, fach, raum, vteacher, vfach, vraum, merkmal, info);
                        Data._vertretungsList.add(vertretung);
                        downloadCount++;
                        //System.out.println("Loading...  DAY(" + date + ")   >>   " + vertretung + "  (" + currentPos + " / " + lenght + ")");

                    }else {
                        System.out.println("\n<<< Download der Daten vom " + date + " abgeschlossen!  (Stunden: " + downloadCount + ")");
                        downloadCount = 0;
                        Data.acceptClients = true;
                        System.out.println("DOWNLOADCOUNTER +1");
                        Data.downloadCounter++;
                        Main.commandManager.setLastLineOfConsole();
                        this.cancel();
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }, 1000 * 2, 10);

    }

    public void removeDoubleStunden(Vertretung _vertretung) {

        if(Data._vertretungsList.size() != 0) {
            for (Vertretung vstunde : Data._vertretungsList) {

                String schultyp = _vertretung.getSchultyp();
                String datum = _vertretung.getDatum();
                String klasse = _vertretung.getKlasse();
                String stunde = _vertretung.getStunde();

                if (schultyp.equals(vstunde.getSchultyp())
                        && datum.equals(vstunde.getDatum())
                        && klasse.equals(vstunde.getKlasse())
                        && stunde.equals(vstunde.getStunde())) {

                    System.out.println(schultyp + " " + datum + " " + klasse + " " + stunde);

                }

            }
        }

    }

    /*public int getLenght(String day) {

        int lenght = 0;

        ResultSet rs = Main.mysql.getResults("SELECT DISTINCT COUNT(vpday, vphour, vpstart, vpend, vpclass, vptutor, vpsubject, vproom, vpctutor, vpcsubject, vpcroom, vpnote, vpinfo, vpcourse, vpschool, vptype, vpguid) AS total FROM fls_vplan WHERE vpday= '" + day + "';");
        try {
            while(rs.next()) {
                lenght = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lenght;

    }
     */



    public String getVpTutor(int vptutorID) throws SQLException {

        String vptutor = "";

        String _teacherFirstname = "";
        String _teacherLastname = "";
        String _teacherShortcut = "";
        ResultSet rsTeacherTable = Main.mysql.getResults("SELECT * FROM fls_teacher WHERE id = '" + vptutorID + "';");
        while (rsTeacherTable.next()) {
            _teacherFirstname = rsTeacherTable.getString("firstname");
            _teacherLastname = rsTeacherTable.getString("lastname");
            _teacherShortcut = rsTeacherTable.getString("shortcut");
        }

        vptutor = _teacherFirstname + "|" + _teacherLastname + "|" + _teacherShortcut;

        return vptutor;
    }

    public String getVpSubject(int vpsubjectID) throws SQLException {

        String _subjectName = "";
        String _subjectShortcut = "";
        ResultSet rsSubjectTable = Main.mysql.getResults("SELECT * FROM fls_subject WHERE id = '" + vpsubjectID + "';");
        while (rsSubjectTable.next()) {
            _subjectName = rsSubjectTable.getString("name");
            _subjectShortcut = rsSubjectTable.getString("shortcut");
        }
        String vpsubject = _subjectName + "|" + _subjectShortcut;

        return vpsubject;
    }


    public String getVpcTutor(int vpctutorID) throws SQLException {

        String vpctutor = "";

        String _teacherFirstname = "";
        String _teacherLastname = "";
        String _teacherShortcut = "";
        ResultSet rsTeacherTable = Main.mysql.getResults("SELECT * FROM fls_teacher WHERE id = '" + vpctutorID + "';");
        while (rsTeacherTable.next()) {
            _teacherFirstname = rsTeacherTable.getString("firstname");
            _teacherLastname = rsTeacherTable.getString("lastname");
            _teacherShortcut = rsTeacherTable.getString("shortcut");
        }

        vpctutor = _teacherFirstname + "|" + _teacherLastname + "|" + _teacherShortcut;

        return vpctutor;
    }

    public String getVpcSubject(int vpcsubjectID) throws SQLException {

        String _subjectName = "";
        String _subjectShortcut = "";
        ResultSet rsSubjectTable = Main.mysql.getResults("SELECT * FROM fls_subject WHERE id = '" + vpcsubjectID + "';");
        while (rsSubjectTable.next()) {
            _subjectName = rsSubjectTable.getString("name");
            _subjectShortcut = rsSubjectTable.getString("shortcut");
        }
        String vpcsubject = _subjectName + "|" + _subjectShortcut;

        return vpcsubject;
    }

    public String getAddedDate(int plusDays, String date) {

        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        //LocalDateTime now = LocalDateTime.now();
        //System.out.println(dtf.format(now));

        int year = Integer.valueOf(date.split("/")[0]);
        int month = Integer.valueOf(date.split("/")[1]);
        int day = Integer.valueOf(date.split("/")[2]);

        day += plusDays;

        String returnDateValue = year + "-" + month + "-" + day;

        return returnDateValue;

    }


}
