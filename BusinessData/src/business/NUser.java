package business;

import data.UserDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NUser {
    private UserDAO _userDAO;

    public NUser() {
        _userDAO=new UserDAO();
    }
    
    public void guardarUser(List<String> parametros) throws SQLException{
        int id=_userDAO.obtenerUltimoId()+1;
        
        _userDAO.setId(id);
        _userDAO.setNombre(parametros.get(0));
        _userDAO.setCorreo(parametros.get(1));
        _userDAO.setTelefono(parametros.get(2));
        _userDAO.setCedula(parametros.get(3));
        _userDAO.setDireccion(parametros.get(4));
        _userDAO.setRol(parametros.get(5));
        _userDAO.setEstado(parametros.get(6));
        _userDAO.setContraseña(parametros.get(7));
        _userDAO.guardarUser();

    }
    
    public ArrayList<String[]> mostrarUsers() throws SQLException{
        return (ArrayList<String[]>) _userDAO.mostrarUsers();
    }
    
    public ArrayList<String[]> listarUsuariosSinUbicacion() throws SQLException{
        return (ArrayList<String[]>) _userDAO.listarClientesSinUbicacion();
    }
    public ArrayList<String[]> listarUsuariosConUbicacion() throws SQLException{
        return (ArrayList<String[]>) _userDAO.listarClientesConUbicacion();
    }
    public ArrayList<String[]> listarVendedoresConRuta() throws SQLException{
        return (ArrayList<String[]>) _userDAO.listarVendedoresConRuta();
    }
    public ArrayList<String[]> listarVendedoresSinRuta() throws SQLException{
        return (ArrayList<String[]>) _userDAO.listarVendedoresSinRuta();
    }
    
    public ArrayList<String[]> comandosAyuda() throws SQLException{
        return (ArrayList<String[]>) _userDAO.listarAyuda();
    }
    
    public ArrayList<String[]> listarPromDiasVisitasVendedor() throws SQLException{
        return (ArrayList<String[]>) _userDAO.listarPromDiasPorVendedores();
    }
    
    public void editarUser(List<String> parametros){
        int id=-1;
        try {
            id=_userDAO.obtenerIdPorCorreo(parametros.get(1));
        } catch (SQLException ex) {
            Logger.getLogger(NUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        _userDAO.setId(Integer.parseInt(parametros.get(0)));
        _userDAO.setNombre(parametros.get(1));
        _userDAO.setCorreo(parametros.get(2));
        _userDAO.setTelefono(parametros.get(3));
        _userDAO.setCedula(parametros.get(4));
        _userDAO.setDireccion(parametros.get(5));
        _userDAO.setRol(parametros.get(6));
        _userDAO.setEstado(parametros.get(7));
        _userDAO.setContraseña(parametros.get(8));
        try {
            _userDAO.actualizarUser();
        } catch (SQLException ex) {
            Logger.getLogger(NUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int obtenerUltimoIdUSer() throws SQLException{
        return _userDAO.obtenerUltimoId();
    }
    public int obtenerIdPorCorreo(String correo) throws SQLException{
        return _userDAO.obtenerIdPorCorreo(correo);
    }
    
    public String obtenerRolPorCorreo(String correo) throws SQLException{
        return _userDAO.obtenerRolPorCorreo(correo);
    }
    
    public String[] getHeaders(){
        return _userDAO.HEADERS;
    }
    public String[] getHeadersGraf(){
        return _userDAO.HEADERSGRAF;
    }
    
    public String urlgrafico() throws SQLException{
        List<String[]> resultados = _userDAO.listarPromDiasPorVendedores();
        String enlaceGrafico = "https://chart.apis.google.com/chart?chs=400x200&cht=bhg&chco=e5f867|aaaaaa|596605";
        String urlGrafico = adaptar(resultados, enlaceGrafico);
        return urlGrafico;
    }
    
    
    public String adaptar(List<String[]> valores, String link) {
        StringBuilder chartUrl = new StringBuilder(link);
    
        StringBuilder data = new StringBuilder("&chd=t:");
        StringBuilder labels = new StringBuilder("&chdl=");
    
        for (String[] row : valores) {
            String nombreVendedor = row[0];
            String promedioDias = row[1];
        
            data.append(promedioDias).append(",");
            labels.append(nombreVendedor).append("+(").append(promedioDias).append("+dias)%7C");
        }
    
        data.deleteCharAt(data.length() - 1); // Eliminar la última coma
        labels.deleteCharAt(labels.length() - 1); // Eliminar el último "|" en los labels
    
        chartUrl.append(data);
        chartUrl.append(labels);
    
        return chartUrl.toString();
    }
}
