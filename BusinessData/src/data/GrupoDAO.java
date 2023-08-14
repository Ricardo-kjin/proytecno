
package data;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class GrupoDAO {

        public static final String[] HEADERS = 
        {"ID", "NOMBRE GRUPO"};
    
    private Conexion con;
    private int _id;
    private String _nombreGrupo;
    
    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getNombreGrupo() {
        return _nombreGrupo;
    }

    public void setNombreGrupo(String _nombreGrupo) {
        this._nombreGrupo = _nombreGrupo;
    }

    public GrupoDAO() {
        con= new Conexion();
    }
    
    public void guardarGrupo() throws SQLException {
        String query = "INSERT INTO grupo07sc.grupo (id, nombre_grupo) VALUES (?,?)";
        try (PreparedStatement ps = con.conectar().prepareStatement(query)) {
            ps.setInt(1, _id);
            ps.setString(2, _nombreGrupo);
            //ps.executeUpdate();
            if (ps.executeUpdate()==0) {
                con.cerrarConexion();
                System.out.println("tw2datapass.DProducto dice: ocurrio un problema en el metodo guardarGrupo()");
                //throw new SQLException();
            }
            con.cerrarConexion();
            System.out.println("tw2datapass.DProducto dice: Se registro Exitosamente el Grupo");
        }
    }
    
    public void eliminarGrupo() throws SQLException {
        String query = "DELETE FROM grupo07sc.grupo WHERE id=?";
        try (PreparedStatement ps = con.conectar().prepareStatement(query)) {
            ps.setInt(1, _id);
            if (ps.executeUpdate()==0) {
                con.cerrarConexion();
                System.out.println("tw2datapass.DProducto dice: ocurrio un problema en el metodo eliminarGrupo()");
            }
            con.cerrarConexion();
            System.out.println("tw2datapass.DProducto dice: Se eliminó exitosamente el Grupo");
        }
    }
    
    public void actualizarGrupo() throws SQLException {
        String query = "UPDATE grupo07sc.grupo SET nombre_grupo=? WHERE id=?";
        try (PreparedStatement ps = con.conectar().prepareStatement(query)) {
            ps.setString(1, _nombreGrupo);
            ps.setInt(2, _id);
            if (ps.executeUpdate()==0) {
                con.cerrarConexion();
                System.out.println("tw2datapass.DProducto dice: ocurrio un problema en el metodo actualizarGrupo()");
            }
            con.cerrarConexion();
            System.out.println("tw2datapass.DProducto dice: Se actualizó exitosamente el Grupo");
        }
    }
    
    public List<String[]> mostrarGrupos() throws SQLException {
        List<String[]> grupos = new ArrayList<>();
        String query = "SELECT * FROM grupo07sc.grupo";
        try (PreparedStatement ps = con.conectar().prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                grupos.add(new String[] {
                    String.valueOf(rs.getInt("id")),
                    rs.getString("nombre_grupo")
                });
            }
        }
        con.cerrarConexion();
        return grupos;
    }
    
    public List<String> mostrarGrupo() throws SQLException {
        List<String> grupo = new ArrayList<>();
        String query = "SELECT id, nombre_grupo FROM grupo07sc.grupo where id=?";
        PreparedStatement ps = con.conectar().prepareStatement(query);
        ps.setInt(1, _id); 
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String nombreGrupo = rs.getString("nombre_grupo");
            grupo.add("ID: " + id + ", Nombre: " + nombreGrupo);
        }
        con.cerrarConexion();
        return grupo;
    }
    
    public int obtenerIdPorNombre(String nombreGrupo) throws SQLException{
        int id=-1;
        String consulta= "SELECT * FROM grupo07sc.grupo WHERE nombre_grupo=?";
        PreparedStatement ps = con.conectar().prepareStatement(consulta);
        ps.setString(1, nombreGrupo);
        ResultSet set= ps.executeQuery();
        if (set.next()) {
            id=set.getInt("id");
        }
        con.cerrarConexion();
        return id;
    }
    
    public int obtenerUltimoId() throws SQLException{
        int id=-1;
        String consulta= "SELECT MAX(id) AS ultimo_id FROM grupo07sc.grupo;";
        PreparedStatement ps = con.conectar().prepareStatement(consulta);
        ResultSet set= ps.executeQuery();
        if (set.next()) {
            id=set.getInt("ultimo_id");
        }
        con.cerrarConexion();
        return id;
    }
}
