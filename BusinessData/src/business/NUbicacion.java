package business;

import data.UbicacionDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class NUbicacion {
    private UbicacionDAO _ubicacionDAO;

    public NUbicacion() {
        _ubicacionDAO=new UbicacionDAO();
    }
    
    public void guardarUbicacion(List<String> parametros) throws SQLException{
        int id=_ubicacionDAO.obtenerUltimoId()+1;
        _ubicacionDAO.setId(id);
        _ubicacionDAO.setLongitud(parametros.get(0));
        _ubicacionDAO.setLatitud(parametros.get(1));
        _ubicacionDAO.setUrl_map(parametros.get(2));
        _ubicacionDAO.setEstado(parametros.get(3));
        _ubicacionDAO.setUser_id(Integer.parseInt(parametros.get(4)));
        _ubicacionDAO.guardarUbicacion();
    }
    public ArrayList<String[]> mostrarUbicacion(int user_id) throws SQLException{
        _ubicacionDAO.setId(user_id);
        return (ArrayList<String[]>) _ubicacionDAO.mostrarUbicacion();
    }
    
    public void editarUbicacion(List<String> parametros) throws SQLException{
        _ubicacionDAO.setId(Integer.parseInt(parametros.get(0)));
        _ubicacionDAO.setLongitud(parametros.get(1));
        _ubicacionDAO.setLatitud(parametros.get(2));
        _ubicacionDAO.setUrl_map(parametros.get(3));
        _ubicacionDAO.setEstado(parametros.get(5));
        _ubicacionDAO.actualizarUbicacion();
    }
    
    public int obtenerIdPorUserID(int user_id){
        try {
            return _ubicacionDAO.obtenerIdPorUserID(user_id);
        } catch (SQLException ex) {
            Logger.getLogger(NUbicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
}
