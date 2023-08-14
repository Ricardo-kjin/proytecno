
package business;

import data.SubGrupoDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NSubGrupo {
    private SubGrupoDAO subGrupoDAO;

    public NSubGrupo() {
        subGrupoDAO= new SubGrupoDAO();
    }
    public void guardarSubGrupo(List<String> parametros) throws SQLException{
        int id=subGrupoDAO.obtenerUltimoId()+1;
        subGrupoDAO.setId(id);
        subGrupoDAO.setNombreSubGrupo(parametros.get(0));
        subGrupoDAO.setGrupo_id(Integer.parseInt(parametros.get(1)));
        subGrupoDAO.guardarSubGrupo();
    }
    
    public void eliminarSubGrupo(List<String> parametros){
        subGrupoDAO.setId(Integer.parseInt(parametros.get(0)));
        try {
            subGrupoDAO.eliminarSubGrupo();
        } catch (SQLException ex) {
            Logger.getLogger(NSubGrupo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void actualizarSubGrupo(List<String> parametros){
        subGrupoDAO.setId(Integer.parseInt(parametros.get(0)));
        subGrupoDAO.setNombreSubGrupo(parametros.get(1));
        subGrupoDAO.setGrupo_id(Integer.parseInt(parametros.get(2)));
        try {
            subGrupoDAO.actualizarSubGrupo();
        } catch (SQLException ex) {
            Logger.getLogger(NSubGrupo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<String[]> mostrarSubGrupos() throws SQLException{
        return (ArrayList<String[]>) subGrupoDAO.mostrarSubGrupos();
    }
    
    public List<String> mostrarSubGrupo(int id) throws SQLException{
        subGrupoDAO.setId(id);
        return subGrupoDAO.mostrarSubGrupo();
    }
    public int obtenerUltimoId() throws SQLException{
        return subGrupoDAO.obtenerUltimoId();
    }
    public String[] getHeaders(){
        return subGrupoDAO.HEADERS;
    }
}
