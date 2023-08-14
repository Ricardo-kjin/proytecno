
package data;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public static final String[] HEADERS = 
        {"ID", "NOMBRE", "CORREO", "TELEFONO", "CEDULA", "DIRECCION", "ROL", "ESTADO"};
    public static final String[] HEADERSGRAF = 
        {"NOMBRE", "PROMEDIO"};
    
    private Conexion con;
    private int _id;
    private String _nombre;
    private String _correo;
    private String _telefono;
    private String _cedula;
    private String _direccion;
    private String _rol;
    private String _estado;
    private String _contraseña;

    public String getContraseña() {
        return _contraseña;
    }

    public void setContraseña(String _contraseña) {
        this._contraseña = _contraseña;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getNombre() {
        return _nombre;
    }

    public void setNombre(String _nombre) {
        this._nombre = _nombre;
    }

    public String getCorreo() {
        return _correo;
    }

    public void setCorreo(String _correo) {
        this._correo = _correo;
    }

    public String getTelefono() {
        return _telefono;
    }

    public void setTelefono(String _telefono) {
        this._telefono = _telefono;
    }

    public String getCedula() {
        return _cedula;
    }

    public void setCedula(String _cedula) {
        this._cedula = _cedula;
    }

    public String getDireccion() {
        return _direccion;
    }

    public void setDireccion(String _direccion) {
        this._direccion = _direccion;
    }

    public String getRol() {
        return _rol;
    }

    public void setRol(String _rol) {
        this._rol = _rol;
    }

    public String getEstado() {
        return _estado;
    }

    public void setEstado(String _estado) {
        this._estado = _estado;
    }

    public UserDAO() {
        this.con=new Conexion();
    }
    
    public void guardarUser() throws SQLException{
        String query = "INSERT INTO grupo07sc.user (id,nombre,correo,telefono,cedula,direccion,rol,estado,contraseña) VALUES (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = con.conectar().prepareStatement(query)) {
            ps.setInt(1, _id);
            ps.setString(2,_nombre);
            ps.setString(3,_correo);
            ps.setString(4,_telefono);
            ps.setString(5,_cedula);
            ps.setString(6,_direccion);
            ps.setString(7,_rol);
            ps.setString(8,_estado);
            ps.setString(9,_contraseña);
            //ps.executeUpdate();
            if (ps.executeUpdate()==0) {
                System.out.println("tw2datapass.DUser dice: ocurrio un problema en el metodo guardarUser()");
                //throw new SQLException();
            }
            con.cerrarConexion();
            System.out.println("tw2datapass.DUser dice: Se registro Exitosamente el Usuario.");
        }
    }
    
    public void eliminarUser() throws SQLException {
        String query = "DELETE FROM user WHERE id=?";
        try (PreparedStatement ps = con.conectar().prepareStatement(query)) {
            ps.setInt(1, _id);
            if (ps.executeUpdate()==0) {
                con.cerrarConexion();
                System.out.println("tw2datapass.DUser dice: ocurrio un problema en el metodo eliminarUser()");
            }
            con.cerrarConexion();
            System.out.println("tw2datapass.DUser dice: Se eliminó exitosamente al Usuario");
        }
    }
    
    public void actualizarUser() throws SQLException {
        String query = "UPDATE user SET nombre=?,correo=?,telefono=?,cedula=?,direccion=?,rol=?,estado=?,contraseña=? WHERE id=?";
        try (PreparedStatement ps = con.conectar().prepareStatement(query)) {
            ps.setString(1, _nombre);
            ps.setString(2, _correo);
            ps.setString(3, _telefono);
            ps.setString(4, _cedula);
            ps.setString(5, _direccion);
            ps.setString(6, _rol);
            ps.setString(7, _estado);
            ps.setString(8, _contraseña);
            ps.setInt(9, _id);
            if (ps.executeUpdate()==0) {
                con.cerrarConexion();
                System.out.println("tw2datapass.DUser dice: ocurrio un problema en el metodo actualizarUser()");
            }
            con.cerrarConexion();
            System.out.println("tw2datapass.DUser dice: Se actualizó exitosamente al Usuario");
        }
    }
    public List<String[]> mostrarUsers() throws SQLException {
        List<String[]> users = new ArrayList<>();
        String query = "SELECT id,nombre,correo,telefono,cedula,direccion,rol,estado FROM grupo07sc.user";
        try (PreparedStatement ps = con.conectar().prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
//            users.add(HEADERS);
            while (rs.next()) {
                users.add(new String[] {
                    String.valueOf(rs.getInt("id")),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("telefono"),
                    rs.getString("cedula"),
                    rs.getString("direccion"),
                    rs.getString("rol"),
                    rs.getString("estado")
                });
            }
        }
        con.cerrarConexion();
        return users;
    }
    public String[] mostrarUser() throws SQLException {
        String[] user = null;
        String query = "SELECT id,nombre,correo,telefono,cedula,direccion,rol,estado FROM grupo07sc.user where id=?";
        PreparedStatement ps = con.conectar().prepareStatement(query);
        ps.setInt(1, _id); 
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            user= new String[] {
                String.valueOf(rs.getInt("id")),
                rs.getString("nombre"),
                rs.getString("correo"),
                rs.getString("telefono"),
                rs.getString("cedula"),
                rs.getString("direccion"),
                rs.getString("rol"),
                rs.getString("estado")
            };
        }
        con.cerrarConexion();
        return user;
    }
    
    public int obtenerIdPorCorreo(String correo) throws SQLException{
        int id=-1;
        String consulta= "SELECT * FROM grupo07sc.user WHERE correo=?";
        PreparedStatement ps = con.conectar().prepareStatement(consulta);
        ps.setString(1, correo);
        ResultSet set= ps.executeQuery();
        if (set.next()) {
            id=set.getInt("id");
        }
        return id;
    }
    
    public String obtenerRolPorCorreo(String correo) throws SQLException{
        String rol="";
        String consulta= "SELECT rol FROM grupo07sc.user WHERE correo=?";
        PreparedStatement ps = con.conectar().prepareStatement(consulta);
        ps.setString(1, correo);
        ResultSet set= ps.executeQuery();
        if (set.next()) {
            rol=set.getString("rol");
        }
        return rol;
    }
    
    public int obtenerUltimoId() throws SQLException{
        int id=-1;
        String consulta= "SELECT MAX(id) AS ultimo_id FROM grupo07sc.user;";
        PreparedStatement ps = con.conectar().prepareStatement(consulta);
        ResultSet set= ps.executeQuery();
        if (set.next()) {
            id=set.getInt("ultimo_id");
        }
        con.cerrarConexion();
        return id;
    }
    
    public List<String[]> listarClientesSinUbicacion() throws SQLException{
        List<String[]> users = new ArrayList<>();
        String query = "SELECT id,nombre,correo,telefono,cedula,direccion,rol,estado "
                + "FROM grupo07sc.user "
                + "WHERE rol='Cliente' AND NOT EXISTS (SELECT 1 FROM grupo07sc.ubicacion WHERE grupo07sc.user.id = grupo07sc.ubicacion.user_id)";
        try (PreparedStatement ps = con.conectar().prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                users.add(new String[] {
                    String.valueOf(rs.getInt("id")),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("telefono"),
                    rs.getString("cedula"),
                    rs.getString("direccion"),
                    rs.getString("rol"),
                    rs.getString("estado"),
                        
                });
            }
        }
        con.cerrarConexion();
        return users;
    }
    public List<String[]> listarClientesConUbicacion() throws SQLException{
        List<String[]> users = new ArrayList<>();
        String query = "SELECT id,nombre,correo,telefono,cedula,direccion,rol,estado "
                + "FROM grupo07sc.user "
                + "WHERE rol='Cliente' AND EXISTS (SELECT 1 FROM grupo07sc.ubicacion WHERE grupo07sc.user.id = grupo07sc.ubicacion.user_id)";
        try (PreparedStatement ps = con.conectar().prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                users.add(new String[] {
                    String.valueOf(rs.getInt("id")),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("telefono"),
                    rs.getString("cedula"),
                    rs.getString("direccion"),
                    rs.getString("rol"),
                    rs.getString("estado"),
                        
                });
            }
        }
        con.cerrarConexion();
        return users;
    }
    
    public List<String[]> listarVendedoresConRuta() throws SQLException{
        List<String[]> users = new ArrayList<>();
        String query = "SELECT id,nombre,correo,telefono,cedula,direccion,rol,estado "
                + "FROM grupo07sc.user "
                + "WHERE rol='Vendedor' AND EXISTS (SELECT 1 FROM grupo07sc.ruta WHERE grupo07sc.user.id = grupo07sc.ruta.user_id)";
        try (PreparedStatement ps = con.conectar().prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                users.add(new String[] {
                    String.valueOf(rs.getInt("id")),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("telefono"),
                    rs.getString("cedula"),
                    rs.getString("direccion"),
                    rs.getString("rol"),
                    rs.getString("estado"),
                        
                });
            }
        }
        con.cerrarConexion();
        return users;
    }
    public List<String[]> listarVendedoresSinRuta() throws SQLException{
        List<String[]> users = new ArrayList<>();
        String query = "SELECT id,nombre,correo,telefono,cedula,direccion,rol,estado "
                + "FROM grupo07sc.user "
                + "WHERE rol='Vendedor' AND NOT EXISTS (SELECT 1 FROM grupo07sc.ruta WHERE grupo07sc.user.id = grupo07sc.ruta.user_id)";
        try (PreparedStatement ps = con.conectar().prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                users.add(new String[] {
                    String.valueOf(rs.getInt("id")),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("telefono"),
                    rs.getString("cedula"),
                    rs.getString("direccion"),
                    rs.getString("rol"),
                    rs.getString("estado"),
                        
                });
            }
        }
        con.cerrarConexion();
        return users;  
    }
    public List<String[]> listarPromDiasPorVendedores() throws SQLException{
        List<String[]> users = new ArrayList<>();
        String query =  "SELECT nombre , AVG(DATE_PART('day', AGE(RU.fecha_fin, RU.fecha_ini))) AS promedio_dias " +
                        "FROM grupo07sc.user " +
                        "JOIN grupo07sc.ruta R ON grupo07sc.user.id = R.user_id " +
                        "JOIN grupo07sc.rutaubicacion RU ON R.id = RU.ruta_id " +
                        "GROUP BY nombre ";
        try (PreparedStatement ps = con.conectar().prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                users.add(new String[] {
                    rs.getString("nombre"),
                    rs.getString("promedio_dias"),   
                });
            }
        }
        con.cerrarConexion();
        return users;  
    }
    public List<String[]> listarAyuda() throws SQLException{
        List<String[]> lista = new ArrayList<>();
        
            lista.add(new String[]{"PARA LOS USUARIO ADMIN"});
            lista.add(new String[]{"G. USUARIO"});
            lista.add(new String[]{"INSERTAR USUARIO: usuario agregar [Jorge Coronel;jorgecoronel@gmail.com;75896458;1201254;Barrio Fatima;Cliente;Activo;12345678]"});
            lista.add(new String[]{"EDITAR USUARIO: usuario modify  [5;Maribel;mari@gmail.com;77007767;1201254;Barrio Tarija;Vendedor;Activo;234999]"});
            lista.add(new String[]{"LISTAR USUARIOs: usuario get "});
            
            lista.add(new String[]{"G. GRUPO"});
            lista.add(new String[]{"grupo modify  [1;REPUESTOS AUTOMOTIVOS]"});
            lista.add(new String[]{"grupo agregar [REPUESTOS AUTOMOTIVOS]"});
            lista.add(new String[]{"grupo get "});
            lista.add(new String[]{"grupo delete [1] "});
            
            lista.add(new String[]{"G. FAMILIA"});
            lista.add(new String[]{"familia modify  [1;REPUESTOS AUTOMOTIVOS]"});
            lista.add(new String[]{"familia agregar [REPUESTOS AUTOMOTIVOS]"});
            lista.add(new String[]{"familia get "});
            lista.add(new String[]{"familia delete [1] "});
            
            lista.add(new String[]{"G. SUBGRUPO"});
            lista.add(new String[]{"subgrupo modify  [1;subgrupo1]"});
            lista.add(new String[]{"subgrupo agregar [subgruponew]"});
            lista.add(new String[]{"subgrupo get "});
            lista.add(new String[]{"subgrupo delete [1] "});
            
            lista.add(new String[]{"G. PRODUCTO"});
            lista.add(new String[]{"producto agregar [nombre;descripcion;precio;stock;unidad_medida;estado;grupo_id;familia_id;user_id]"});
            lista.add(new String[]{"producto modify  [id;nombre;descripcion;precio;stock;unidad_medida;estado;grupo_id;familia_id;user_id]"});
            lista.add(new String[]{"producto get "});
            lista.add(new String[]{"producto delete [id] "});
            
            lista.add(new String[]{"G. RUTA"});
            lista.add(new String[]{"ruta agregar [codigoruta;tiempo_total;estado;user_id]"});
            lista.add(new String[]{"ruta modify  [id;codigoruta;tiempo_total;estado;user_id]"});
            lista.add(new String[]{"ruta get "});
            lista.add(new String[]{"ruta delete [id] "});
            
            lista.add(new String[]{"G. UBICACION"});
            lista.add(new String[]{"ubicacion agregar [longitud;latitud;url_map;estado_visita;user_id]"});
            lista.add(new String[]{"ubicacion modify  [id;codigoruta;tiempo_total;estado]"});
            lista.add(new String[]{"ubicacion delete [id]"});
            
            lista.add(new String[]{"G. REPORTES"});
            lista.add(new String[]{"reporte repclisinubi"});
            lista.add(new String[]{"reporte repcliconubi"});
            lista.add(new String[]{"reporte repvenconruta"});
            lista.add(new String[]{"reporte repvenconruta"});
            
            lista.add(new String[]{"GRAFICO BARRA"});
            lista.add(new String[]{"usuario grafbarra"});
          
            lista.add(new String[]{"COMANDOS"});
            lista.add(new String[]{"usuario verify"});
            
        return lista;  
    }
}
