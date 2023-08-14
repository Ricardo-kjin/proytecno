
package data;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubGrupoDAO {
        public static final String[] HEADERS = 
        {"ID", "SUB GRUPO","GRUPO "};
    private Conexion con;
    private int _id;
    private String _nombreSubGrupo;
    private int _grupo_id;

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getNombreSubGrupo() {
        return _nombreSubGrupo;
    }

    public void setNombreSubGrupo(String _nombreSubGrupo) {
        this._nombreSubGrupo = _nombreSubGrupo;
    }

    public int getGrupo_id() {
        return _grupo_id;
    }

    public void setGrupo_id(int _grupo_id) {
        this._grupo_id = _grupo_id;
    }

    public SubGrupoDAO() {
        this.con=new Conexion();
    }
    
    public void guardarSubGrupo() throws SQLException {
        String query = "INSERT INTO grupo07sc.subgrupo (id, nombre_subgrupo, grupo_id) VALUES (?,?,?)";
        try (PreparedStatement ps = con.conectar().prepareStatement(query)) {
            ps.setInt(1, _id);
            ps.setString(2, _nombreSubGrupo);
            ps.setInt(3, _grupo_id);
            //ps.executeUpdate();
            if (ps.executeUpdate()==0) {
                con.cerrarConexion();
                System.out.println("Dato.SubGrupoDAO dice: ocurrio un problema en el metodo guardarGrupo()");
                //throw new SQLException();
            }
            con.cerrarConexion();
            System.out.println("Dato.SubGrupoDAO dice: Se registro Exitosamente el Grupo");
        }
    }
    
    public void eliminarSubGrupo() throws SQLException {
        String query = "DELETE FROM grupo07sc.subgrupo WHERE id=?";
        try (PreparedStatement ps = con.conectar().prepareStatement(query)) {
            ps.setInt(1, _id);
            if (ps.executeUpdate()==0) {
                con.cerrarConexion();
                System.out.println("Dato.SubGrupoDAO dice: ocurrio un problema en el metodo eliminarGrupo()");
            }
            con.cerrarConexion();
            System.out.println("Dato.SubGrupoDAO dice: Se eliminó exitosamente el Grupo");
        }
    }
    
    public void actualizarSubGrupo() throws SQLException {
        String query = "UPDATE grupo07sc.subgrupo SET nombre_subgrupo=?,grupo_id=? WHERE id=?";
        try (PreparedStatement ps = con.conectar().prepareStatement(query)) {
            ps.setString(1, _nombreSubGrupo);
            ps.setString(2, String.valueOf(_grupo_id));
            ps.setInt(3, _id);
            if (ps.executeUpdate()==0) {
                con.cerrarConexion();
                System.out.println("Dato.SubGrupoDAO dice: ocurrio un problema en el metodo actualizarGrupo()");
            }
            con.cerrarConexion();
            System.out.println("Dato.SubGrupoDAO dice: Se actualizó exitosamente el Grupo");
        }
    }
    
    public List<String[]> mostrarSubGrupos() throws SQLException {
        List<String[]> subgrupos = new ArrayList<>();
        String query = "SELECT subgrupo.id, nombre_subgrupo, grupo.nombre_grupo FROM grupo07sc.grupo, grupo07sc.subgrupo where subgrupo.id=grupo.id";
        try (PreparedStatement ps = con.conectar().prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                subgrupos.add(new String[] {
                    String.valueOf(rs.getInt("id")),
                    rs.getString("nombre_subgrupo"),
                    String.valueOf(rs.getString("nombre_grupo"))
                });
            }
        }
        con.cerrarConexion();
        return subgrupos;
    }
    
    public List<String> mostrarSubGrupo() throws SQLException {
        List<String> grupo = new ArrayList<>();
        String query = "SELECT subgrupo.id, nombre_subgrupo, grupo.nombre_grupo FROM grupo07sc.subgrupo, grupo07sc.grupo where subgrupo.id=?";
        PreparedStatement ps = con.conectar().prepareStatement(query);
        ps.setInt(1, _id); 
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String nombreSubGrupo = rs.getString("nombre_subgrupo");
            String nombreGrupo = rs.getString("nombre_grupo");
            grupo.add("ID: " + id + ", Nombre SubGgrupo: " + nombreSubGrupo +"Nombre Grupo: " + nombreGrupo);
        }
        con.cerrarConexion();
        return grupo;
    }
    
    public int obtenerUltimoId() throws SQLException{
        int id=-1;
        String consulta= "SELECT MAX(id) AS ultimo_id FROM grupo07sc.subgrupo;";
        PreparedStatement ps = con.conectar().prepareStatement(consulta);
        ResultSet set= ps.executeQuery();
        if (set.next()) {
            id=set.getInt("ultimo_id");
        }
        con.cerrarConexion();
        return id;
    }
}
