package ACB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Metodos {
    Connection conn;

    public Metodos(Connection conn) {
        this.conn = conn;
    }

    public void mostraEquips() throws SQLException {
        Statement st = conn.createStatement();
        Scanner reader = new Scanner(System.in);
        ResultSet rs;

        rs = st.executeQuery("SELECT * FROM team");
        while (rs.next()) System.out.print(
                "\nNombre: " +rs.getString("name") +
                "\nTipus: " + rs.getString("type") +
                "\nPais: " + rs.getString("country") +
                "\nCiutat: " + rs.getString("city") +
                "\nCampo: " + rs.getString("court_name") +
                "\n-----------------------------------");
        rs.close();
        st.close();
    }

    public void mostraJugadorsDeUnEquip() throws SQLException {

        Statement st = conn.createStatement();
        Scanner reader = new Scanner(System.in);
        ResultSet rs;
        System.out.println("Escribe el nombre un equipo");
        String nameteam = reader.nextLine();

        rs = st.executeQuery("SELECT * FROM player WHERE team_name='"+nameteam+"'");
        while (rs.next()) System.out.print(
                "\nNúmero de licencia: " +rs.getString("federation_license_code") +
                        "\nNombre: " + rs.getString("first_name") +
                        "\nApellido: " + rs.getString("last_name") +
                        "\nFecha de nacimiento: " + rs.getString("birth_date") +
                        "\nGenero: " + rs.getString("gender") +
                        "\nAltura: " + rs.getString("height") +
                        "\nNombre de equipo: " + rs.getString("team_name") +
                        "\nCantidad de MVP's: " + rs.getString("mvp_total") +
                        "\n-----------------------------------");
        rs.close();
        st.close();

    }

    public void creaEquipo() throws SQLException, NumberFormatException {
        Scanner reader = new Scanner(System.in);

        System.out.println("Introduce nombre del equipo");
        String name = reader.nextLine();

        System.out.println("Introduce tipo de equipo(National Team|Club)");
        String type = reader.nextLine();

        System.out.println("Introduce pais del equipo");
        String country = reader.nextLine();

        System.out.println("Introduce ciudad del equipo");
        String city = reader.nextLine();

        System.out.println("Introduce nombre de pabellon");
        String court_name = reader.nextLine();

        Statement statement = null;
        statement = conn.createStatement();
        statement.executeUpdate("INSERT INTO team VALUES ('"+name+"','"+type+"','"+city+"','"+country+"','"+court_name+"')");
    }

    public void creaJugador() throws SQLException, NumberFormatException, ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Scanner reader = new Scanner(System.in);
        Scanner readerint = new Scanner(System.in);

        System.out.println("Introduce codigo del jugador");
        int code = readerint.nextInt();

        System.out.println("Introduce nombre del jugador");
        String nombre = reader.nextLine();

        System.out.println("Introduce apellido del jugador");
        String apellido = reader.nextLine();

        System.out.println("Introduce fecha de nacimiento del jugador(yyyy-mm-dd)");
        Date nacimiento = format.parse(reader.nextLine());

        System.out.println("Introduce genero del jugador(M|F)");
        String genero = reader.nextLine();

        System.out.println("Introduce altura del jugador");
        int altura = readerint.nextInt();

        System.out.println("Introduce nombre del equipo del jugador");
        String nombreequipo = reader.nextLine();


        System.out.println("Introduce ocasiones que ha sido MVP el jugador");
        int mvp = readerint.nextInt();

        Statement statement = null;
        statement = conn.createStatement();

        if(nombreequipo.equals("")) {
            statement.executeUpdate("INSERT INTO player VALUES ('"+code+"','"+nombre+"','"+apellido+"','"+nacimiento+"','"+genero+"','"+altura+"',"+null+",'"+mvp+"')");
        }
        else{
            statement.executeUpdate("INSERT INTO player VALUES ('"+code+"','"+nombre+"','"+apellido+"','"+nacimiento+"','"+ genero+"','"+altura+"','"+nombreequipo+"','"+mvp+"')");
        }

        }

    public void creaPartit() throws ParseException, SQLException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Scanner reader = new Scanner(System.in);
        Scanner readerint = new Scanner(System.in);

        System.out.println("Introduce nombre del equipo local");
        String home_team = reader.nextLine();

        System.out.println("Introduce nombre del equipo visitante");
        String visitor_team = reader.nextLine();

        System.out.println("Introduce fecha del partido(yyyy-mm-dd)");
        Date match_date = format.parse(reader.nextLine());

        System.out.println("Introduce numero de asistencia");
        int attendance = readerint.nextInt();

        System.out.println("Introduce codigo del jugador MVP");
        String mvp_player = reader.nextLine();

        Statement statement = null;
        statement = conn.createStatement();
        statement.executeUpdate("INSERT INTO match VALUES ('"+home_team+"','"+visitor_team+"','"+match_date+"','"+attendance+"','"+mvp_player+"')");

    }

    public void mostraJugadorsSenseEquip() throws SQLException {

        Statement st = conn.createStatement();
        Scanner reader = new Scanner(System.in);
        ResultSet rs;

        rs = st.executeQuery("SELECT * FROM player WHERE team_name IS NULL");
        while (rs.next()) System.out.print(
                "\nNúmero de licencia: " +rs.getString("federation_license_code") +
                        "\nNombre: " + rs.getString("first_name") +
                        "\nApellido: " + rs.getString("last_name") +
                        "\nFecha de nacimiento: " + rs.getString("birth_date") +
                        "\nGenero: " + rs.getString("gender") +
                        "\nAltura: " + rs.getString("height") +
                        "\nNombre de equipo: " + rs.getString("team_name") +
                        "\nCantidad de MVP's: " + rs.getString("mvp_total") +
                        "\n-----------------------------------");
        rs.close();
        st.close();
    }

    public void assignarJugadorAUnEquip() throws SQLException {

        ResultSet rs = null;
        Statement st = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        try {
            rs = st.executeQuery("SELECT * FROM player WHERE team_name IS NULL");

            if (!rs.next()) {
                System.out.println("No hay jugadores sin equipo");
            } else {
                do {
                    System.out.println("Nombre: " + rs.getString("first_name"));

                    System.out.println("¿Quieres assignar un equipo a este jugador?(S|N)");
                    String resposta = br.readLine();

                    if (resposta.equals("S")) {
                        System.out.println("Introduce nombre del equipo");
                        String nombreequipo = br.readLine();
                        rs.updateString("first_name", nombreequipo);
                        rs.updateRow();
                    }
                }while (rs.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desvincularJugadorDeUnEquip() throws SQLException {
        ResultSet rs = null;
        Statement st = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        Scanner reader = new Scanner(System.in);

        try {
            System.out.println("Introduce codigo del jugador");
            int code = reader.nextInt();
            rs = st.executeQuery("SELECT * FROM player WHERE federation_license_code='"+code+"' AND team_name IS NOT NULL");

            if (!rs.next()) {
                System.out.println("Este jugador no tiene equipo");
            } else {
                do {
                    System.out.println("Nombre: " + rs.getString("first_name"));

                    System.out.println("¿Quieres desvincular del equipo a este jugador?(S|N)");
                    String resposta = br.readLine();

                    if (resposta.equals("S")) {
                        rs.updateString("team_name", null);
                        rs.updateRow();
                    }
                }while (rs.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void carregaEstadistiques() throws IOException, SQLException {

        String row;
        BufferedReader csvReader = new BufferedReader(new FileReader("estadistiques.csv"));
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");

            System.out.println("INSERT INTO match_statistics VALUES ('"+data[0]+"','"+data[1]+"','"+data[2]+"','"+data[3]+"','"+data[4]+"','"+data[5]+"','"+data[6]+"','"+data[7]+"','"+data[8]+"','"+data[9]+"','"+data[10]+"','"+data[11]+"','"+data[12]+"','"+data[13]+"','"+data[14]+"','"+data[15]+"','"+data[16]+"','"+data[17]+"','"+data[18]+"','"+data[19]+"','"+data[20]+"','"+data[21]+"')");
            Statement statement = null;
            statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO match_statistics VALUES ('"+data[0]+"','"+data[1]+"','"+data[2]+"','"+data[3]+"','"+data[4]+"','"+data[5]+"','"+data[6]+"','"+data[7]+"','"+data[8]+"','"+data[9]+"','"+data[10]+"','"+data[11]+"','"+data[12]+"','"+data[13]+"','"+data[14]+"','"+data[15]+"','"+data[16]+"','"+data[17]+"','"+data[18]+"','"+data[19]+"','"+data[20]+"','"+data[21]+"')");
            statement.close();

        }
        csvReader.close();
    }

    public void sortir() {
        System.out.println("ADÉU!");
        System.exit(0);
    }
}
