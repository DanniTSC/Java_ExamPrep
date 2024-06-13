import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersoaneReader {
    public List<Persoane> citestePersoane(String dbPath) {
        List<Persoane> persoane = new ArrayList<>();

        String Url = "jdbc:sqlite:" + dbPath;
        try (
                Connection conn = DriverManager.getConnection(Url);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * from Persoane")) {

            while (rs.next()) {
                int cod = rs.getInt("Cod");
                String cnp = rs.getString("CNP");
                String nume = rs.getString("Nume");
                Persoane persoana = new Persoane(cod,cnp,nume);
                persoane.add(persoana);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  persoane;
    }
}