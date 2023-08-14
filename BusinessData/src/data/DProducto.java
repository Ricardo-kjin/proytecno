/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import postgresqlconnection.SqlConnection;

/**
 *
 * @author Ronaldo Rivero
 */
public class DProducto {
    
    private SqlConnection connection;
    
    public DProducto() {
        connection = new SqlConnection("postgres", "root", "127.0.0.1", "5432", "db_tecno");
    }
    
    public void guardar(String nombre, int stock, int usuarioId) throws SQLException {
        
        String query = "INSERT INTO productos(nombre,stock,usuario_id)"
                + " values(?,?,?)";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1, nombre);
        ps.setInt(2, stock);
        ps.setInt(3, usuarioId);
        
        if(ps.executeUpdate() == 0) {
            System.err.println("Class DProducto.java dice: "
                    + "Ocurrio un error al insertar un usuario guardar()");
            throw new SQLException();
        }
    }
    
    public void desconectar() {
        if(connection != null) {
            connection.closeConnection();
        }
    }
}
