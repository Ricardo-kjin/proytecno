
package data;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    
    public static final String[] HEADERS ={"ID","NOMBRE","DESCRIPCION","PRECIO","STOCK","UNIDAD MEDIDA","ESTADO","GRUPO","FAMILIA"};
    
    private Conexion con;
    private int _id;
    private String _nombre;
    private String _descripcion;
    private double _precio;
    private int _stock;
    private String _unidad_medida;
    private String _estado;
    private int _grupo_id;
    private int _familia_id;
    private int _user_id;

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

    public String getDescripcion() {
        return _descripcion;
    }

    public void setDescripcion(String _descripcion) {
        this._descripcion = _descripcion;
    }

    public double getPrecio() {
        return _precio;
    }

    public void setPrecio(double _precio) {
        this._precio = _precio;
    }

    public int getStock() {
        return _stock;
    }

    public void setStock(int _stock) {
        this._stock = _stock;
    }

    public String getUnidad_medida() {
        return _unidad_medida;
    }

    public void setUnidad_medida(String _unidad_medida) {
        this._unidad_medida = _unidad_medida;
    }

    public String getEstado() {
        return _estado;
    }

    public void setEstado(String _estado) {
        this._estado = _estado;
    }

    public int getGrupo_id() {
        return _grupo_id;
    }

    public void setGrupo_id(int _grupo_id) {
        this._grupo_id = _grupo_id;
    }

    public int getFamilia_id() {
        return _familia_id;
    }

    public void setFamilia_id(int _familia_id) {
        this._familia_id = _familia_id;
    }

    public int getUser_id() {
        return _user_id;
    }

    public void setUser_id(int _user_id) {
        this._user_id = _user_id;
    }

    public ProductoDAO() {
        con=new Conexion();
    }
    
    public void guardarProducto() throws SQLException{
        String query = "INSERT INTO grupo07sc.producto (id,nombre,descripcion,precio,stock,unidad_medida,estado,grupo_id,familia_id,user_id) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = con.conectar().prepareStatement(query)) {
            ps.setInt(1, _id);
            ps.setString(2, _nombre);
            ps.setString(3, _descripcion);
            ps.setDouble(4, _precio);
            ps.setInt(5, _stock);
            ps.setString(6, _unidad_medida);
            ps.setString(7, _estado);
            ps.setInt(8, _grupo_id);
            ps.setInt(9, _familia_id);
            ps.setInt(10, _user_id);
            //ps.executeUpdate();
            if (ps.executeUpdate()==0) {
                con.cerrarConexion();
                System.out.println("tw2datapass.DProducto dice: ocurrio un problema en el metodo guardarProducto()");
                //throw new SQLException();
            }
            con.cerrarConexion();
            System.out.println("tw2datapass.DProducto dice: Se registro Exitosamente el producto.");
        }
        
    }
    public void actualizarProducto() throws SQLException {
        String query = "UPDATE grupo07sc.producto SET nombre_familia=?,descripcion=?,precio=?,stock=?,unidad_medida=?,estado=?,"
                + "grupo_id=?,familia_id=? WHERE id=?";
        try (PreparedStatement ps = con.conectar().prepareStatement(query)) {
            ps.setString(1, _nombre);
            ps.setString(2, _descripcion);
            ps.setDouble(3, _precio);
            ps.setInt(4, _stock);
            ps.setString(5, _unidad_medida);
            ps.setString(6, _estado);
            ps.setInt(7, _grupo_id);
            ps.setInt(8, _familia_id);
            ps.setInt(9, _id);
            if (ps.executeUpdate()==0) {
                con.cerrarConexion();
                System.out.println("tw2datapass.DProducto dice: ocurrio un problema en el metodo actualizarProducto()");
            }
            con.cerrarConexion();
            System.out.println("tw2datapass.DProducto dice: Se actualizó exitosamente el Producto");
        }
    }
    
    public List<String[]> mostrarProductos() throws SQLException {
        List<String[]> productos = new ArrayList<>();
        String query = "SELECT producto.id,nombre,descripcion,precio,stock,unidad_medida,estado,grupo.nombre_grupo,familia.nombre_familia FROM grupo07sc.familia,grupo07sc.grupo,grupo07sc.producto where producto.grupo_id=grupo.id and producto.familia_id=familia.id";
        try (PreparedStatement ps = con.conectar().prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {                
                productos.add(new String[] {
                    String.valueOf(rs.getInt("id")),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    String.valueOf(rs.getDouble("precio")),
                    String.valueOf(rs.getInt("stock")),
                    rs.getString("unidad_medida"),
                    rs.getString("estado"),
                    rs.getString("nombre_grupo"),
                    rs.getString("nombre_familia"),
                }); 
                
            }
        }
        con.cerrarConexion();
        return productos;
    }
    //FALTA ESTAS FUNCIONES
    public List<String> mostrarProducto() throws SQLException {
        List<String> producto = new ArrayList<>();
        String query = "SELECT producto.id,nombre,descripcion,precio,stock,unidad_medida,estado,grupo.nombre_grupo,familia.nombre_familia "
                + "FROM grupo07sc.familia,grupo07sc.grupo,grupo07sc.producto "
                + "where producto.grupo_id=grupo.id and producto.familia_id=familia.id and producto.id=?";
        PreparedStatement ps = con.conectar().prepareStatement(query);
        ps.setInt(1, _id); 
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");
                String unidad_medida = rs.getString("unidad_medida");
                String estado = rs.getString("estado");
                String nombre_grupo = rs.getString("nombre_grupo");
                String nombre_familia = rs.getString("nombre_familia");
                producto.add("ID: " + id + ", Nombre: " + nombre+ ", Descripcion: " + descripcion+ ", Precio: " + precio+ ", Stock: " + stock
                            + ", Unidad Medida: " + unidad_medida+ ", Estado: " + estado+ ", Nombre Grupo: " + nombre_grupo+ ", Nombre Catalogo: " + nombre_familia);
            }
        con.cerrarConexion();
        return producto;
    }
    
    public int obtenerIdPorNombre(String nombreProducto) throws SQLException{
        int id=-1;
        String consulta= "SELECT * FROM grupo07sc.producto WHERE nombre=?";
        PreparedStatement ps = con.conectar().prepareStatement(consulta);
        ps.setString(1, nombreProducto);
        ResultSet set= ps.executeQuery();
        if (set.next()) {
            id=set.getInt("id");
        }
        return id;
    }
    
    public int obtenerUltimoId() throws SQLException{
        int id=-1;
        String consulta= "SELECT MAX(id) AS ultimo_id FROM grupo07sc.producto;";
        PreparedStatement ps = con.conectar().prepareStatement(consulta);
        ResultSet set= ps.executeQuery();
        if (set.next()) {
            id=set.getInt("ultimo_id");
        }
        return id;
    }
    
}
