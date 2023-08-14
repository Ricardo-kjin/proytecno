
package data;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RutaDAO {
    public static final String[] HEADERS = 
        {"ID", "CODIGO RUTA","TIEMPO (DIAS)","ESTADO","ID USER","NOMBRE VENDEDOR"};
    Conexion con;
    private int _id;
    private String _codigo_ruta;
    private int _tiempo_total;
    private String _estado;
    private int _user_id;

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getCodigo_ruta() {
        return _codigo_ruta;
    }

    public void setCodigo_ruta(String _codigo_ruta) {
        this._codigo_ruta = _codigo_ruta;
    }

    public int getTiempo_total() {
        return _tiempo_total;
    }

    public void setTiempo_total(int _tiempo_total) {
        this._tiempo_total = _tiempo_total;
    }

    public String getEstado() {
        return _estado;
    }

    public void setEstado(String _estado) {
        this._estado = _estado;
    }

    public int getUser_id() {
        return _user_id;
    }

    public void setUser_id(int _user_id) {
        this._user_id = _user_id;
    }

    public RutaDAO() {
        this.con=new Conexion();
    }
    
    public void guardarRuta() throws SQLException {
        String query = "INSERT INTO grupo07sc.ruta (id,codigo_ruta,tiempo_total,estado,user_id) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = con.conectar().prepareStatement(query)) {
            ps.setInt(1, _id);
            ps.setString(2, _codigo_ruta);
            ps.setInt(3, _tiempo_total);
            ps.setString(4, _estado);
            ps.setInt(5, _user_id);
            //ps.executeUpdate();
            if (ps.executeUpdate()==0) {
                con.cerrarConexion();
                System.out.println("tw2datapass.DRuta dice: ocurrio un problema en el metodo guardarRuta()");
                //throw new SQLException();
            }
            con.cerrarConexion();
            System.out.println("tw2datapass.DFamilia dice: Se registro Exitosamente la Ruta.");
        }
    }
    
    public void eliminarRuta() throws SQLException {
        String query = "DELETE FROM ruta WHERE id=?";
        try (PreparedStatement ps = con.conectar().prepareStatement(query)) {
            ps.setInt(1, _id);
            if (ps.executeUpdate()==0) {
                con.cerrarConexion();
                System.out.println("tw2datapass.DRuta dice: ocurrio un problema en el metodo eliminarRuta()");
            }
            con.cerrarConexion();
            System.out.println("tw2datapass.DRuta dice: Se eliminó exitosamente la Ruta");
        }
    }
    
    public void actualizarRuta() throws SQLException {
        String query = "UPDATE ruta SET codigo_ruta=?,tiempo_total=?,estado=?,user_id=? WHERE id=?";
        try (PreparedStatement ps = con.conectar().prepareStatement(query)) {
            ps.setString(1, _codigo_ruta);
            ps.setInt(2, _tiempo_total);
            ps.setString(3, _estado);
            ps.setInt(4, _user_id);
            ps.setInt(5, _id);
            if (ps.executeUpdate()==0) {
                con.cerrarConexion();
                System.out.println("tw2datapass.DRuta dice: ocurrio un problema en el metodo actualizarRuta()");
            }
            con.cerrarConexion();
            System.out.println("tw2datapass.DRuta dice: Se actualizó exitosamente la Ruta");
        }
    }
    
    public List<String[]> mostrarRutas() throws SQLException {
        List<String[]> rutas = new ArrayList<>();
        String query = "SELECT id, codigo_ruta,tiempo_total,estado,user_id,nombre FROM grupo07sc.familia,grupo07sc.user where familia.user_id=user.id";
        try (PreparedStatement ps = con.conectar().prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                rutas.add(new String[] {
                    String.valueOf(rs.getInt("id")),
                    rs.getString("codigo_ruta"),
                    String.valueOf(rs.getInt("tiempo_total")),
                    rs.getString("estado"),
                    String.valueOf(rs.getString("user_id")),
                });                
            }
        }
        con.cerrarConexion();
        return rutas;
    }
    
    public int obtenerUltimoId() throws SQLException{
        int id=-1;
        String consulta= "SELECT MAX(id) AS ultimo_id FROM grupo07sc.ruta;";
        PreparedStatement ps = con.conectar().prepareStatement(consulta);
        ResultSet set= ps.executeQuery();
        if (set.next()) {
            id=set.getInt("ultimo_id");
        }
        con.cerrarConexion();
        return id;
    }
    public void actualizarTotalDias() throws SQLException{
        String consulta= "Update grupo07sc.ruta "
                + "SET tiempo_total=COALESCE((SELECT SUM(ur.fecha_fin-ur.fecha_ini)"
                + "FROM grupo07sc.rutaubicacion AS ur "
                + "WHERE ur.ruta_id=grupo07sc.ruta.id),0) "
                + "WHERE id=? ";
        try (PreparedStatement ps = con.conectar().prepareStatement(consulta)) {
            ps.setInt(1, _id);
            if (ps.executeUpdate()==0) {
                con.cerrarConexion();
                System.out.println("tw2datapass.DRuta dice: ocurrio un problema en el metodo ActualizarTotalDias()");
            }
            con.cerrarConexion();
            System.out.println("tw2datapass.DRuta dice: Se actualizo exitosamente la Ruta");
        }
    } 
}
