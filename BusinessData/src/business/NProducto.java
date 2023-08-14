
package business;

import data.ProductoDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NProducto {
    private ProductoDAO _productoDAO;

    public NProducto() {
        _productoDAO=new ProductoDAO();
    }
    
    public void guardarProducto(List<String> parametros){
        try {
            _productoDAO.setId(_productoDAO.obtenerUltimoId()+1);
        } catch (SQLException ex) {
            Logger.getLogger(NProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        _productoDAO.setNombre(parametros.get(0));
        _productoDAO.setDescripcion(parametros.get(1));
        _productoDAO.setPrecio(Double.parseDouble(parametros.get(2)));
        _productoDAO.setStock(Integer.parseInt(parametros.get(3)));
        _productoDAO.setUnidad_medida(parametros.get(4));
        _productoDAO.setEstado(parametros.get(5));
        _productoDAO.setGrupo_id(Integer.parseInt(parametros.get(6)));
        _productoDAO.setFamilia_id(Integer.parseInt(parametros.get(7)));
        _productoDAO.setUser_id(Integer.parseInt(parametros.get(8)));
        try {
            _productoDAO.guardarProducto();
        } catch (SQLException ex) {
            Logger.getLogger(NProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void editarProducto(List<String> parametros){
        _productoDAO.setId(Integer.parseInt(parametros.get(0)));
        _productoDAO.setNombre(parametros.get(1));
        _productoDAO.setDescripcion(parametros.get(2));
        _productoDAO.setPrecio(Double.parseDouble(parametros.get(3)));
        _productoDAO.setStock(Integer.parseInt(parametros.get(4)));
        _productoDAO.setUnidad_medida(parametros.get(5));
        _productoDAO.setEstado(parametros.get(6));
        _productoDAO.setGrupo_id(Integer.parseInt(parametros.get(7)));
        _productoDAO.setFamilia_id(Integer.parseInt(parametros.get(8)));
        _productoDAO.setUser_id(Integer.parseInt(parametros.get(9)));
        try {
            _productoDAO.actualizarProducto();
        } catch (SQLException ex) {
            Logger.getLogger(NProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ArrayList<String[]> mostrarPrpductos() throws SQLException{
        return (ArrayList<String[]>) _productoDAO.mostrarProductos();
    }
    
    public int ultimoIdPorNombre(String nombre) throws SQLException{
        return _productoDAO.obtenerIdPorNombre(nombre);
    }
    public int ultimoId() throws SQLException{
        return _productoDAO.obtenerUltimoId();
    }
    public String[] headers(){
        return _productoDAO.HEADERS;
    }
}
