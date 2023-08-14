/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessdata;

import business.BProducto;
import business.BUsuario;
import business.NFamilia;
import business.NGrupo;
import business.NProducto;
import business.NRuta;
import business.NSubGrupo;
import business.NUbicacion;
import business.NUser;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ronaldo Rivero
 */
public class BusinessData {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        guardarRutaUbicacion();
        actualizarDiasRuta();
    }
    
    public static void producto() {
        BProducto bProducto = new BProducto();
        List<String> producto = new ArrayList<String>();
        producto.add("Producto 1");
        producto.add("100");
                        
        try {
            bProducto.guardar(producto, "ronaldorivero3@uagrm.edu.bo");            
            
        } catch (SQLException ex) {
            Logger.getLogger(BusinessData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    public static void usuario() {
        BUsuario bUsuariro = new BUsuario();
        List<String> usuario = new ArrayList<String>();
        usuario.add("Carla");
        usuario.add("Romero Suarez");
        usuario.add("12788606");
        usuario.add("Femenino");
        usuario.add("carlaromero@uagrm.edu.bo");
        usuario.add("1996-06-24");
        usuario.add("76042143");
        
        try {
            bUsuariro.guardar(usuario);            
            
        } catch (SQLException ex) {
            Logger.getLogger(BusinessData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(BusinessData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //GRUPO
    public static void listarGrupo(){
        NGrupo grupos= new NGrupo();
        List<String[]> _grupos;
        try {
            _grupos=grupos.mostrarGrupos();
            for (String[] grupo : _grupos) {
                for (String valor : grupo) {
                    System.out.print(valor + " ");
                }
                System.out.println(); // Imprime una línea en blanco después de cada persona
            }
        } catch (SQLException ex) {
            Logger.getLogger(BusinessData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void guardarGrupo(){
        NGrupo grupo=new NGrupo();
        try {
            List<String> grupos= new ArrayList<>();
            grupos.add("REPUESTOS AUTOMOTIVOS");
            grupo.guardarGrupo(grupos);
//            grupo.mostrarGrupos();
        } catch (SQLException ex) {
            Logger.getLogger(BusinessData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void grupoActualizar(){
        NGrupo grupo=new NGrupo();
        try {
            List<String> grupos= new ArrayList<>();
            grupos.add("1");
            grupos.add("REPUESTOS");
            grupo.actualizarGrupo(grupos);
//            System.err.println(grupo.mostrarGrupos());
        } catch (SQLException ex) {
            Logger.getLogger(BusinessData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //SUBGRUPO
    public static void guardarSubgrupo(){
        NSubGrupo subgrupo= new NSubGrupo();
        NGrupo grupo= new NGrupo();
        try {
            int grupo_id= grupo.obtenerIdXNombreGrupo("REPUESTOS AUTOMOTIVOS");
            System.out.println("grupo_id:"+grupo_id+", subgrupo id:"+subgrupo.obtenerUltimoId()+1);
            List<String> subgrupos= new ArrayList<>();
            subgrupos.add("PROD. RTB");
            subgrupos.add(String.valueOf(grupo_id));
            subgrupo.guardarSubGrupo(subgrupos);
            System.out.println(subgrupo.mostrarSubGrupos());
        } catch (SQLException ex) {
            Logger.getLogger(BusinessData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void listarSubGrupos(){
        NSubGrupo subgrupos= new NSubGrupo();
        List<String[]> _subgrupos;
        try {
            _subgrupos=subgrupos.mostrarSubGrupos();
            for (String[] subgrupo : _subgrupos) {
                for (String valor : subgrupo) {
                    System.out.print(valor + " ");
                }
                System.out.println(); // Imprime una línea en blanco después de cada persona
            }
        } catch (SQLException ex) {
            Logger.getLogger(BusinessData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //FAMILIA
    public static void guardarFamilia(){
        try {
            NFamilia familia=new NFamilia();
            List<String> familias= new ArrayList<>();
            familias.add("REPUESTOS");
            familia.guardarFamilia(familias);
        } catch (SQLException ex) {
            Logger.getLogger(BusinessData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void listarFamilias(){
        NFamilia familias= new NFamilia();
        List<String[]> _familias;
        try {
            _familias=familias.mostrarFamilias();
            for (String[] familia : _familias) {
                for (String valor : familia) {
                    System.out.print(valor + " ");
                }
                System.out.println(); // Imprime una línea en blanco después de cada persona
            }
        } catch (SQLException ex) {
            Logger.getLogger(BusinessData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //USER
    public static void guardarUser(){
        NUser user=new NUser();
        List<String> usuario= new ArrayList<>();
        usuario.add("Gonzalo Ramirez");
        usuario.add("gonzaloramirez@gmail.com");
        usuario.add("76543368");
        usuario.add("1110301");
        usuario.add("Barrio Aguilera");
        usuario.add("Cliente");
        usuario.add("Activo");
        usuario.add("12345678");
        try {
            user.guardarUser(usuario);
        } catch (SQLException ex) {
            Logger.getLogger(BusinessData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void listarUsuarios(){
        NUser _users=new NUser();
        List<String[]> usuarios;
        try {
            usuarios=_users.mostrarUsers();
            for (String[] user : usuarios) {
                for (String valor : user) {
                    System.out.print(valor + " ");
                }
                System.out.println(); // Imprime una línea en blanco después de cada persona
            }
        } catch (SQLException ex) {
            Logger.getLogger(BusinessData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void mostrarUsuariosSinUbicacion(){
        NUser user=new NUser();
        try {
            System.err.println(user.listarUsuariosSinUbicacion());
        } catch (SQLException ex) {
            Logger.getLogger(BusinessData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //PRODUCTO
    public static void guardarProducto(){
            NProducto _producto=new NProducto();
            List<String> producto= new ArrayList<>();
            producto.add("JPK-001N");
            producto.add("45X60X7-TC RETEN DE COSECHADORA");
            producto.add(String.valueOf(350.50));
            producto.add(String.valueOf(10));
            producto.add("Pieza");
            producto.add("Habilitado");
            producto.add(String.valueOf(1));
            producto.add(String.valueOf(1));
            producto.add(String.valueOf(1));
            _producto.guardarProducto(producto);
    }
    
    public static void listarProductos(){
        NProducto productos=new NProducto();
        List<String[]> _productos;
        try {
            _productos=productos.mostrarPrpductos();
            for (String[] producto : _productos) {
                for (String valor : producto) {
                    System.out.print(valor + " ");
                }
                System.out.println(); // Imprime una línea en blanco después de cada persona
            }
        } catch (SQLException ex) {
            Logger.getLogger(BusinessData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //UBICACION
    public static void guardarUbicacion(){
        NUbicacion ubicacion=new NUbicacion();
        List<String> _ubicacion= new ArrayList<>();
            _ubicacion.add("111111.11101011");
            _ubicacion.add("-100101001.12254");
            _ubicacion.add("https://urldelaubicacion");
            _ubicacion.add("Activo");
            _ubicacion.add(String.valueOf(1));
        try {
            ubicacion.guardarUbicacion(_ubicacion);
        } catch (SQLException ex) {
            Logger.getLogger(BusinessData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //RUTA
    public static void guardarRuta(){
        NRuta ruta=new NRuta();
        List<String> _ruta=new ArrayList<>();
        _ruta.add("RUTA01");
        _ruta.add("Activo");
        _ruta.add(String.valueOf(2));
        ruta.guardarRuta(_ruta);
    }
    //RUTAUBICACION
    public static void guardarRutaUbicacion(){
        NRuta ruta=new NRuta();
        List<String> _rutaubicacion=new ArrayList<>();
        _rutaubicacion.add("2023-08-01");
        _rutaubicacion.add("2023-08-15");
        _rutaubicacion.add("Pendiente");
        _rutaubicacion.add(String.valueOf(1));
        _rutaubicacion.add(String.valueOf(1));
        System.out.println(_rutaubicacion);
        ruta.addUbicacionARuta(_rutaubicacion);
    }
    
    public static void actualizarDiasRuta(){
        NRuta ruta=new NRuta();
        ruta.actualizarTotalDias(1);
    }
    //reportes
    public static void listarClientesSinUbicacion(){
        NUser users=new NUser();
        List<String[]> _users;
        try {
            _users=users.listarUsuariosSinUbicacion();
            for (String[] user : _users) {
                for (String valor : user) {
                    System.out.print(valor + " ");
                }
                System.out.println(); // Imprime una línea en blanco después de cada persona
            }
        } catch (SQLException ex) {
            Logger.getLogger(BusinessData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void listarClientesConUbicacion(){
        NUser users=new NUser();
        List<String[]> _users;
        try {
            _users=users.listarUsuariosConUbicacion();
            for (String[] user : _users) {
                for (String valor : user) {
                    System.out.print(valor + " ");
                }
                System.out.println(); // Imprime una línea en blanco después de cada persona
            }
        } catch (SQLException ex) {
            Logger.getLogger(BusinessData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void listarPromVendedores(){
        NUser users=new NUser();
        List<String[]> _users;
        try {
            _users=users.listarPromDiasVisitasVendedor();
            for (String[] user : _users) {
                for (String valor : user) {
                    System.out.print(valor + " ");
                }
                System.out.println(); // Imprime una línea en blanco después de cada persona
            }
        } catch (SQLException ex) {
            Logger.getLogger(BusinessData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void obtenerurl(){
        NUser _user=new NUser();
        try {
            System.out.println(_user.urlgrafico());
        } catch (SQLException ex) {
            Logger.getLogger(BusinessData.class.getName()).log(Level.SEVERE, null, ex);
        }
;
    }
}
