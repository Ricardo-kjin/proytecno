
package business;


import data.GrupoDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NGrupo {
    public GrupoDAO datoGrupo;

    public NGrupo() {
        this.datoGrupo = new GrupoDAO();
    }
    public void guardarGrupo(List<String> parametros) throws SQLException {
        
        datoGrupo.setId(datoGrupo.obtenerUltimoId()+1);
        datoGrupo.setNombreGrupo(parametros.get(0));
        datoGrupo.guardarGrupo();
    }

    public void eliminarGrupo(List<String> parametros) throws SQLException{
        datoGrupo.setId(Integer.parseInt(parametros.get(0)));
        datoGrupo.eliminarGrupo();
    }

    public void actualizarGrupo(List<String> parametros) throws SQLException{
        datoGrupo.setId(Integer.parseInt(parametros.get(0)));
        datoGrupo.setNombreGrupo(parametros.get(1));
        datoGrupo.actualizarGrupo();
    }

    public ArrayList<String[]> mostrarGrupos() throws SQLException {
        return (ArrayList<String[]>) datoGrupo.mostrarGrupos();
    }
    public int grupoUltimoId() throws SQLException{
        return datoGrupo.obtenerUltimoId();
    }
    public int obtenerIdXNombreGrupo(String nombreGrupo) throws SQLException{
        return datoGrupo.obtenerIdPorNombre(nombreGrupo);
    }
}
