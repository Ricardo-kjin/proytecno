/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.DProducto;
import data.DUsuario;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Ronaldo Rivero
 */
public class BProducto {
    private DProducto dProducto;
    private DUsuario dUsuario;
    
    public BProducto() {
        dProducto = new DProducto();
        dUsuario = new DUsuario();
    }
    
    
    public void guardar(List<String> parametros, String correo) throws SQLException {
        int usuarioId = dUsuario.getIdByCorreo(correo);
        if(usuarioId != -1) {
            dProducto.guardar(parametros.get(0), Integer.parseInt(parametros.get(1)), usuarioId);
            dProducto.desconectar();
        }
        dUsuario.desconectar();
    }
}
