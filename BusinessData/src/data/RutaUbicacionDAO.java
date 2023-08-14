
package data;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RutaUbicacionDAO {
    Conexion con;

    private int _id;
    private String _fecha_ini;
    private String _fecha_fin;
    private String _EstadoVisita;
    private int _ubicacion_id;
    private int _ruta_id;

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getFecha_ini() {
        return _fecha_ini;
    }

    public void setFecha_ini(String _fecha_ini) {
        this._fecha_ini = _fecha_ini;
    }

    public String getFecha_fin() {
        return _fecha_fin;
    }

    public void setFecha_fin(String _fecha_fin) {
        this._fecha_fin = _fecha_fin;
    }



    public String getEstadoVisita() {
        return _EstadoVisita;
    }

    public void setEstadoVisita(String _EstadoVisita) {
        this._EstadoVisita = _EstadoVisita;
    }

    public int getUbicacion_id() {
        return _ubicacion_id;
    }

    public void setUbicacion_id(int _ubicacion_id) {
        this._ubicacion_id = _ubicacion_id;
    }

    public int getRuta_id() {
        return _ruta_id;
    }

    public void setRuta_id(int _ruta_id) {
        this._ruta_id = _ruta_id;
    }
    
    
    public RutaUbicacionDAO() {
        this.con=new Conexion();
    }
    
    public void guardarRutaUbicacion() throws SQLException {
        String query = "INSERT INTO grupo07sc.rutaubicacion (id, fecha_ini,fecha_fin,estado_visita,ubicacion_id,ruta_id) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = con.conectar().prepareStatement(query)) {
            ps.setInt(1, _id);
            ps.setDate(2, (java.sql.Date) convertirStringAFecha(_fecha_ini));
            ps.setDate(3, (java.sql.Date) convertirStringAFecha(_fecha_fin));
            ps.setString(4, _EstadoVisita);
            ps.setInt(5, _ubicacion_id);
            ps.setInt(6, _ruta_id);
            //ps.executeUpdate();
            if (ps.executeUpdate()==0) {
                con.cerrarConexion();
                System.out.println("tw2datapass.DRutaUbicacion dice: ocurrio un problema en el metodo guardarRutaUbicacion()");
                //throw new SQLException();
            }
            con.cerrarConexion();
            System.out.println("tw2datapass.DRutaUbicacion dice: Se registro Exitosamente la RutaUbicacion.");
        }
    }
    
    public void eliminarXUbicacion() throws SQLException {
        String query = "DELETE FROM grupo07sc.rutaubicacion WHERE ubicacion_id=?";
        try (PreparedStatement ps = con.conectar().prepareStatement(query)) {
            ps.setInt(1, _ubicacion_id);
            //ps.setInt(2, _ruta_id);
            if (ps.executeUpdate()==0) {
                con.cerrarConexion();
                System.out.println("tw2datapass.DRutaUbicacion dice: ocurrio un problema en el metodo eliminarRutaUbicacion()");
            }
            con.cerrarConexion();
            System.out.println("tw2datapass.DRutaUbicacion dice: Se eliminó exitosamente la DRutaUbicacion");
        }
    }
        public void eliminarXRuta() throws SQLException {
        String query = "DELETE FROM grupo07sc.rutaubicacion WHERE ruta_id=?";
        try (PreparedStatement ps = con.conectar().prepareStatement(query)) {
            //ps.setInt(1, _ubicacion_id);
            ps.setInt(1, _ruta_id);
            if (ps.executeUpdate()==0) {
                con.cerrarConexion();
                System.out.println("tw2datapass.DRutaUbicacion dice: ocurrio un problema en el metodo eliminarRutaUbicacion()");
            }
            con.cerrarConexion();
            System.out.println("tw2datapass.DRutaUbicacion dice: Se eliminó exitosamente la DRutaUbicacion");
        }
    }
        
    public void actualizarRutaUbicacionEstado() throws SQLException {
        String query = "UPDATE grupo07sc.rutaubicacion SET estado_visita=? WHERE ruta_id=? and ubicacion_id=?";
        try (PreparedStatement ps = con.conectar().prepareStatement(query)) {
            ps.setString(1, _EstadoVisita);
            ps.setInt(2, _ruta_id);
            ps.setInt(3, _ubicacion_id);
            if (ps.executeUpdate()==0) {
                con.cerrarConexion();
                System.out.println("tw2datapass.DRutaUbicacion dice: ocurrio un problema en el metodo actualizarDRutaUbicacion()");
            }
            con.cerrarConexion();
            System.out.println("tw2datapass.DRutaUbicacion dice: Se actualizó exitosamente la DRutaUbicacion");
        }
    }
    public int obtenerUltimoId() throws SQLException{
        int id=-1;
        String consulta= "SELECT MAX(id) AS ultimo_id FROM grupo07sc.rutaubicacion;";
        PreparedStatement ps = con.conectar().prepareStatement(consulta);
        ResultSet set= ps.executeQuery();
        if (set.next()) {
            id=set.getInt("ultimo_id");
        }
        con.cerrarConexion();
        return id;
    }
    public java.sql.Date convertirStringAFecha(String fechaString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Ajusta el formato de fecha según tus necesidades
        java.util.Date utilDate;
        try {
            utilDate = dateFormat.parse(fechaString);
        } catch (ParseException e) {
            // Manejo de error si la conversión falla
            e.printStackTrace();
            return null;
        }
        return new java.sql.Date(utilDate.getTime());
    }
}
