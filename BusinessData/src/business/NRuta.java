
package business;

import data.RutaDAO;
import data.RutaUbicacionDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NRuta {
    private RutaUbicacionDAO _Druta_ubicacion;
    private RutaDAO _Druta;

    public NRuta() {
        _Druta=new RutaDAO();
        _Druta_ubicacion=new RutaUbicacionDAO();
    }
    
    public void guardarRuta(List<String> parametros){
        try {
            int id=_Druta.obtenerUltimoId()+1;
            _Druta.setId(id);
            _Druta.setCodigo_ruta(parametros.get(0));
            _Druta.setTiempo_total(0);
            _Druta.setEstado(parametros.get(1));
            _Druta.setUser_id(Integer.parseInt(parametros.get(2)));
            _Druta.guardarRuta();
        } catch (SQLException ex) {
            Logger.getLogger(NRuta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void actualizarRuta(List<String> parametros){
        try {
            _Druta.setId(Integer.parseInt(parametros.get(0)));
            _Druta.setCodigo_ruta(parametros.get(1));
            _Druta.setTiempo_total(Integer.parseInt(parametros.get(2)));
            _Druta.setEstado(parametros.get(3));
            _Druta.setUser_id(Integer.parseInt(parametros.get(4)));
            _Druta.guardarRuta();
        } catch (SQLException ex) {
            Logger.getLogger(NRuta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addUbicacionARuta(List<String> parametros){
        try {
            _Druta_ubicacion.setId(_Druta_ubicacion.obtenerUltimoId()+1);
            _Druta_ubicacion.setFecha_ini(parametros.get(0));
            _Druta_ubicacion.setFecha_fin(parametros.get(1));
            _Druta_ubicacion.setEstadoVisita(parametros.get(2));
            _Druta_ubicacion.setUbicacion_id(Integer.parseInt(parametros.get(3)));
            _Druta_ubicacion.setRuta_id(Integer.parseInt(parametros.get(4)));
            _Druta_ubicacion.guardarRutaUbicacion();
            actualizarTotalDias(_Druta_ubicacion.getRuta_id());
        } catch (SQLException ex) {
            Logger.getLogger(NRuta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void eliminarXUbicacion(List<String> parametros){
        _Druta_ubicacion.setUbicacion_id(Integer.parseInt(parametros.get(0)));
        try {
            _Druta_ubicacion.eliminarXUbicacion();
        } catch (SQLException ex) {
            Logger.getLogger(NRuta.class.getName()).log(Level.SEVERE, null, ex);
        }
        actualizarTotalDias(Integer.parseInt(parametros.get(1)));
    }
    
    public void eliminarXRuta(List<String> parametros){
        _Druta_ubicacion.setRuta_id(Integer.parseInt(parametros.get(0)));
        try {
            _Druta_ubicacion.eliminarXRuta();
        } catch (SQLException ex) {
            Logger.getLogger(NRuta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public  void actualizarTotalDias(int ruta_id){
        try {
            _Druta.setId(ruta_id);
            _Druta.actualizarTotalDias();
        } catch (SQLException ex) {
            Logger.getLogger(NRuta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<String[]> mostrarRutas() throws SQLException{
        return (ArrayList<String[]>) _Druta.mostrarRutas();
    }
    public String[] getHeaders(){
        return _Druta.HEADERS;
    }
}
