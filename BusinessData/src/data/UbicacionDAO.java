
package data;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UbicacionDAO {
    private Conexion con;
    private int _id;
    private String _longitud;
    private String _latitud;
    private String _url_map;
    private String _estado;
    private int _user_id;

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getLongitud() {
        return _longitud;
    }

    public void setLongitud(String _longitud) {
        this._longitud = _longitud;
    }

    public String getLatitud() {
        return _latitud;
    }

    public void setLatitud(String _latitud) {
        this._latitud = _latitud;
    }

    public String getUrl_map() {
        return _url_map;
    }

    public void setUrl_map(String _url_map) {
        this._url_map = _url_map;
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

    public UbicacionDAO() {
        con=new Conexion();
    }
    
    public void guardarUbicacion() throws SQLException{
        String query = "INSERT INTO grupo07sc.ubicacion (id,longitud,latitud,url_map,estado,user_id) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = con.conectar().prepareStatement(query)) {
            ps.setInt(1, _id);
            ps.setString(2, _longitud);
            ps.setString(3, _latitud);
            ps.setString(4, _url_map);
            ps.setString(5, _estado);
            ps.setInt(6, _user_id);
            //ps.executeUpdate();
            if (ps.executeUpdate()==0) {
                con.cerrarConexion();
                System.out.println("tw2datapass.DUbicacion dice: ocurrio un problema en el metodo guardarUbicacion()");
                //throw new SQLException();
            }
            con.cerrarConexion();
            System.out.println("tw2datapass.DUbicacion dice: Se registro Exitosamente la Ubicacion.");
        }
    }
    
    public void actualizarUbicacion() throws SQLException {
        String query = "UPDATE grupo07sc.ubicacion SET longitud=?,latitud=?,url_map=?,estado=? WHERE user_id=?";
        try (PreparedStatement ps = con.conectar().prepareStatement(query)) {
            ps.setString(1, _longitud);
            ps.setString(2, _latitud);
            ps.setString(3, _url_map);
            ps.setString(4, _estado);
            ps.setInt(6, _id);
            if (ps.executeUpdate()==0) {
                con.cerrarConexion();
                System.out.println("tw2datapass.DUbicacion dice: ocurrio un problema en el metodo actualizarUbicacion()");
            }
            con.cerrarConexion();
            System.out.println("tw2datapass.DUbicacion dice: Se actualizó exitosamente la Ubicacion");
        }
    }
    
    public List<String> mostrarUbicaciones() throws SQLException {
        List<String> ubicaciones = new ArrayList<>();
        String query = "SELECT id,longitud,latitud,url_map,estado,user_id,user.nombre FROM ubicacion,user where ubicacion.user_id=user.id";
        try (PreparedStatement ps = con.conectar().prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String longitud = rs.getString("longitud");
                String latitud = rs.getString("latitud");
                String url_map = rs.getString("url_map");
                String estado = rs.getString("estado");
                String user_id = rs.getString("user_id");
                String nombre = rs.getString("nombre");
                ubicaciones.add("ID: " + id + ", Longitud: " +longitud+ ", Latitud: " +latitud+ ", Url Map: " +url_map
                            + ", Estado: " +estado+ ", Id Usuario: " +user_id+ ", Nombre Usuario: " +nombre);
            }
        }
        con.cerrarConexion();
        return ubicaciones;
    }
    //FALTA ESTAS FUNCIONES
    public List<String[]> mostrarUbicacion() throws SQLException {
        List<String[]> ubicacion = new ArrayList<>();
        String query = "SELECT id,longitud,latitud,url_map,estado,user_id,user.nombre "
                + "FROM grupo07sc.ubicacion,grupo07sc.user "
                + "where ubicacion.user_id=user.id and ubicacion.user_id=?";
        PreparedStatement ps = con.conectar().prepareStatement(query);
        ps.setInt(1, _user_id); 
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ubicacion.add(new String[] {
                String.valueOf(rs.getInt("id")),
                rs.getString("longitud"),
                rs.getString("latitud"),
                rs.getString("url_map"),
                rs.getString("estado"),
                String.valueOf(rs.getInt("user_id")),
                rs.getString("nombre"),
            }); 
            }
        con.cerrarConexion();
        return ubicacion;
    }
    
    public int obtenerIdPorUserID(int user_id) throws SQLException{
        int id=-1;
        String consulta= "SELECT * FROM public.ubicacion WHERE user_id=?";
        PreparedStatement ps = con.conectar().prepareStatement(consulta);
        ps.setInt(1, user_id);
        ResultSet set= ps.executeQuery();
        if (set.next()) {
            id=set.getInt("id");
        }
        con.cerrarConexion();
        return id;
    }
    
    public int obtenerUltimoId() throws SQLException{
        int id=-1;
        String consulta= "SELECT MAX(id) AS ultimo_id FROM grupo07sc.ubicacion;";
        PreparedStatement ps = con.conectar().prepareStatement(consulta);
        ResultSet set= ps.executeQuery();
        if (set.next()) {
            id=set.getInt("ultimo_id");
        }
        con.cerrarConexion();
        return id;
    }

}
