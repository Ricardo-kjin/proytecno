
package data;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class FamiliaDAO {
    public static final String[] HEADERS = 
        {"ID", "NOMBRE CATALOGO"};
    
    private Conexion con;
    private int _id;
    private String _nombreFamilia;

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getNombreFamilia() {
        return _nombreFamilia;
    }

    public void setNombreFamilia(String _nombreFamilia) {
        this._nombreFamilia = _nombreFamilia;
    }

    public FamiliaDAO() {
        this.con = new Conexion();
    }
    
    public void guardarFamilia() throws SQLException {
        String query = "INSERT INTO grupo07sc.familia (id, nombre_familia) VALUES (?,?)";
        try (PreparedStatement ps = con.conectar().prepareStatement(query)) {
            ps.setInt(1, _id);
            ps.setString(2, _nombreFamilia);
            //ps.executeUpdate();
            if (ps.executeUpdate()==0) {
                con.cerrarConexion();
                System.out.println("tw2datapass.DFamilia dice: ocurrio un problema en el metodo guardarFamiliai()");
                //throw new SQLException();
            }
            con.cerrarConexion();
            System.out.println("tw2datapass.DFamilia dice: Se registro Exitosamente la familia.");
        }
    }
    
    public void eliminarFamilia() throws SQLException {
        String query = "DELETE FROM grupo07sc.familia WHERE id=?";
        try (PreparedStatement ps = con.conectar().prepareStatement(query)) {
            ps.setInt(1, _id);
            if (ps.executeUpdate()==0) {
                con.cerrarConexion();
                System.out.println("tw2datapass.DFamilia dice: ocurrio un problema en el metodo eliminarFamilia()");
            }
            con.cerrarConexion();
            System.out.println("tw2datapass.DFamilia dice: Se eliminó exitosamente la Familia");
        }
    }
    
    public void actualizarFamilia() throws SQLException {
        String query = "UPDATE grupo07sc.familia SET nombre_familia=? WHERE id=?";
        try (PreparedStatement ps = con.conectar().prepareStatement(query)) {
            ps.setString(1, _nombreFamilia);
            ps.setInt(2, _id);
            if (ps.executeUpdate()==0) {
                con.cerrarConexion();
                System.out.println("tw2datapass.DFamilia dice: ocurrio un problema en el metodo actualizarFamilia()");
            }
            con.cerrarConexion();
            System.out.println("tw2datapass.DFamilia dice: Se actualizó exitosamente la familia");
        }
    }
    
    public List<String[]> mostrarFamilias() throws SQLException {
        List<String[]> familias = new ArrayList<>();
        String query = "SELECT id, nombre_familia FROM grupo07sc.familia";
        try (PreparedStatement ps = con.conectar().prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
//            familias.add(HEADERS);
            while (rs.next()) {
                familias.add(new String[] {
                    String.valueOf(rs.getInt("id")),
                    rs.getString("nombre_familia")
                });                
            }
        }
        con.cerrarConexion();
        return familias;
    }
    
    /*public List<String[]> listar() throws SQLException {
        List<String[]> usuarios = new ArrayList<>();
        String query = "SELECT * FROM usuarios";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ResultSet set = ps.executeQuery();
        while(set.next()) {
            usuarios.add(new String[] {
                String.valueOf(set.getInt("id")),
                set.getString("nombre"),
                set.getString("apellido"),
                set.getString("ci"),
                set.getString("genero"),
                set.getString("correo"),
                set.getString("fecha_nacimiento"),
                set.getString("telefono")
            });
        }
        return usuarios;
    }*/
    
    //FALTA ESTAS FUNCIONES
    public String[] mostrarFamilia() throws SQLException {
        String[] familia = null;
        String query = "SELECT id, nombre_familia FROM grupo07sc.familia where id=?";
        PreparedStatement ps = con.conectar().prepareStatement(query);
        ps.setInt(1, _id); 
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            familia= new String[] {
                String.valueOf(rs.getInt("id")),
                rs.getString("nombre_familia")                
            };
        }
        con.cerrarConexion();
        return familia;
    }
    
    public int obtenerIdPorNombre(String nombreFamilia) throws SQLException{
        int id=-1;
        String consulta= "SELECT * FROM grupo07sc.familia WHERE nombre_familia=?";
        PreparedStatement ps = con.conectar().prepareStatement(consulta);
        ps.setString(1, nombreFamilia);
        ResultSet set= ps.executeQuery();
        if (set.next()) {
            id=set.getInt("id");
        }
        con.cerrarConexion();
        return id;
    }
    
    public int obtenerUltimoId() throws SQLException{
        int id=-1;
        String consulta= "SELECT MAX(id) AS ultimo_id FROM grupo07sc.familia;";
        PreparedStatement ps = con.conectar().prepareStatement(consulta);
        ResultSet set= ps.executeQuery();
        if (set.next()) {
            id=set.getInt("ultimo_id");
        }
        con.cerrarConexion();
        return id;
    }
}
