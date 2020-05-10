import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataBase {

    private static Connection con;
    private static boolean hasData = false;

    public ResultSet getNotesResultSet() throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }

        assert con != null;
        Statement state = con.createStatement();
        return state.executeQuery("SELECT header, body, time FROM notes");
    }

    private static void getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:Notes.db");
        initialise();
    }

    private static void initialise() throws SQLException {
        if (!hasData) {
            hasData = true;

            Statement state = con.createStatement();
            ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name = 'notes'");

            if (!res.next()) {
                System.out.println("Creating and prepopulating");
                Statement state2 = con.createStatement();
                state2.execute("CREATE TABLE notes(id integer,"
                        + "header varchar(60)," + "body text," + "time varchar(60),"
                        + "primary key(id));");

                PreparedStatement prep = con.prepareStatement("INSERT INTO notes values(?,?,?,?);");
                prep.setString(2, "Welcome");
                prep.setString(3, "This is a simple open source 'Notes' app written in Java.");
                prep.setString(4, "~This note is auto generated~");
                prep.execute();

//                PreparedStatement prep2 = con.prepareStatement("INSERT INTO notes values(?,?,?,?);");
//                prep2.setString(2, "Fix");
//                prep2.setString(3, "Fix the bugs in your code");
//                prep2.setString(4, "time");
//                prep2.execute();
            }


        }
    }

    public static void addNote(String header, String body, String formattedTime) throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        PreparedStatement prep = con.prepareStatement("INSERT INTO notes values(?,?,?,?)");
        prep.setString(2, header);
        prep.setString(3, body);
        prep.setString(4, formattedTime);
        prep.execute();

    }

    public static void updateNoteHeader(String oldHeader, String newHeader) throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        PreparedStatement prep = con.prepareStatement("UPDATE notes SET header=?"
                + "WHERE header=?");

        prep.setString(1, newHeader);
        prep.setString(2, oldHeader);
        prep.executeUpdate();
    }

    public static void updateNoteBody(String oldBody, String newBody) throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        PreparedStatement prep = con.prepareStatement("UPDATE notes SET body=?"
        + "WHERE body=?");

        prep.setString(1, newBody);
        prep.setString(2, oldBody);
        prep.executeUpdate();
    }

    public static void deleteNote(String deletedNoteHeader) throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        PreparedStatement prep = con.prepareStatement("DELETE FROM notes WHERE header=?");

        prep.setString(1, deletedNoteHeader);
        prep.executeUpdate();
    }

    public static void closeConnection() throws SQLException {
        con.close();
    }


}
