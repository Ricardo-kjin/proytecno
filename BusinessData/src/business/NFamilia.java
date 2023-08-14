
package business;

import data.FamiliaDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class NFamilia {

    private FamiliaDAO _datoFamilia;

    public NFamilia() {
        _datoFamilia=new FamiliaDAO();
    }
    public void guardarFamilia(List<String> parametros) throws SQLException{
        int id=_datoFamilia.obtenerUltimoId()+1;
        _datoFamilia.setId(id);
        _datoFamilia.setNombreFamilia(parametros.get(0));
        _datoFamilia.guardarFamilia();
    }
    public void eliminarFamilia(List<String> parametros) throws SQLException{
        _datoFamilia.setId(Integer.parseInt(parametros.get(0)));
        _datoFamilia.eliminarFamilia();
    }
    public void editarFamilia(List<String> parametros) throws SQLException{
        _datoFamilia.setId(Integer.parseInt(parametros.get(0)));
        _datoFamilia.setNombreFamilia(parametros.get(1));
        _datoFamilia.actualizarFamilia();
    }
    public ArrayList<String[]> mostrarFamilias() throws SQLException{
        return (ArrayList<String[]>) _datoFamilia.mostrarFamilias();
    }
    public String[] mostrarFamilia(List<String> parametros) throws SQLException{
        _datoFamilia.setId(Integer.parseInt(parametros.get(0)));
        return _datoFamilia.mostrarFamilia();
    }
    public int obtenerIdPorNombreFamilia(String nombreFamilia) throws SQLException{
        return _datoFamilia.obtenerIdPorNombre(nombreFamilia);
    }
    
    public int obtenerUltimoIdFamilia() throws SQLException{
        return _datoFamilia.obtenerUltimoId();
    }
    
    public String[] getHeaders(){
        return _datoFamilia.HEADERS;
    }


}
