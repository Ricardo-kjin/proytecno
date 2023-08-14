package postgresqlconnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostgreSqlConnection {

    public static void main(String[] args) {
        try {
            SqlConnection sqlConnection =
                    new SqlConnection("postgres", "root", "127.0.0.1", "5432", "db_tecno");
            String query = "SELECT * FROM usuarios WHERE id = 1";
            PreparedStatement ps = sqlConnection.connect().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.println("resultado: " + rs.next());
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
