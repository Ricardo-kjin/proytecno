
package connectioncore;

import business.NFamilia;
import business.NGrupo;
import business.NProducto;
import business.NRuta;
import business.NSubGrupo;
import business.NUbicacion;
import business.NUser;
import communication.MailVerificationThread;
import communication.SendEmailThread;
import interfaces.IEmailEventListener;
import interpreter.Main;
import interpreter.analex.Interpreter;
import interpreter.analex.interfaces.ITokenEventListener;
import interpreter.analex.utils.Token;
import interpreter.events.TokenEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Email;

public class ConnectionCore {

 
    public static void main(String[] args) {
        /*
        MailVerificationThread mail = new MailVerificationThread();
        mail.setEmailEventListener(new IEmailEventListener() {
            @Override
            public void onReceiveEmailEvent(List<Email> emails) {
                for (Email email : emails) {
                    System.out.println(email);
//                    interprete(email);
                }
            }
        });
        
        Thread thread = new Thread(mail);
        thread.setName("Mail Verification Thread");
        thread.start();
        */
        
        Email emailObject = new Email("rikrdoramirez1993@gmail.com", Email.SUBJECT,
                "Petición realizada correctamente");
        
        SendEmailThread sendEmail = new SendEmailThread(emailObject);
        Thread thread = new Thread(sendEmail);
        thread.setName("Send email Thread");
        thread.start();
        
    }
    
    public static void interprete(Email email){
        
//        String comando = "ruta addubixruta [2023-09-11;2023-09-25;Pendiente;2;1] ";
//        String correo = "ronaldorivero@uagrm.edu.bo";
        
        NFamilia _nfamilia=new NFamilia();
        NUser _nuser=new NUser();
        NProducto _nproducto=new NProducto();
        NGrupo _ngrupo= new NGrupo();
        NSubGrupo _nsubgrupo= new NSubGrupo();
        NUbicacion _nubicacion=new NUbicacion();
        NRuta _nruta=new NRuta();
        
        Interpreter interpreter = new Interpreter(email.getSubject(), email.getFrom());       
        interpreter.setListener(new ITokenEventListener() {

/*

            @Override
            public void producto(TokenEvent event) {
                System.out.println("CU: MASCOTA");
                System.out.println(event);
                try {
                    if(event.getAction() == Token.AGREGAR) {
                        bProducto.guardar(event.getParams(), event.getSender());   
                        System.out.println("OK");
                        //notificar al usuario que se ejecuto su comando
                    } else if(event.getAction() == Token.MODIFY) {

                    } else if(event.getAction() == Token.DELETE) {

                    } else {
                        System.out.println("La accion no es valida para el caso de uso");
                        //enviar al correo una notificacion
                    }                
                } catch (SQLException ex) {
                    System.out.println("Mensaje: "+ex.getSQLState());
                    //enviar notificacion de error
                }
            }
*/
            @Override
            public void error(TokenEvent event) {
                System.out.println("DESCONOCIDO");
                System.out.println(event);
                //enviar una notificacion
            }

            @Override
            public void usuario(TokenEvent event) {
                System.out.println("CU: USER");
                System.out.println(event);
                try {
                    if (event.getAction()==Token.AGREGAR) {
                        _nuser.guardarUser(event.getParams());

                    } else if (event.getAction()==Token.MODIFY) {
                        _nuser.editarUser(event.getParams());
                        
                    } else if (event.getAction()==Token.DELETE) {
                        //falta eliminar usuario
                    }else if (event.getAction()==Token.GET) {
                        ArrayList<String[]> lista =_nuser.mostrarUsers();

                        String s = "";
                        for(int i = 0; i < lista.size(); i++) {
                            s = s + "["+i+"] : ";
                            for(int j = 0; j <lista.get(i).length; j++) {
                                s = s + lista.get(i)[j] + " | ";
                            }
                            s = s + "\n";
                        }
                        System.out.println(s);
                    }else{
                        System.out.println("La accion no es valida para el caso de uso");
                    }
                } catch (SQLException ex) {
                    //enviar notificacion de error
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }

            @Override
            public void producto(TokenEvent event) {
                System.out.println("CU: PRODUCTO");
                System.out.println(event);

                try {
                    if (event.getAction()==Token.AGREGAR) {
                        _nproducto.guardarProducto(event.getParams());

                    } else if (event.getAction()==Token.MODIFY) {
                        //FALTA EDITAR PRODUCTO
                    } else if (event.getAction()==Token.DELETE) {
                        //FALTA ELIMINAR PRODUCTO
                    }else if (event.getAction()==Token.GET) {
                        ArrayList<String[]> lista =_nproducto.mostrarPrpductos();

                        String s = "";
                        for(int i = 0; i < lista.size(); i++) {
                            s = s + "["+i+"] : ";
                            for(int j = 0; j <lista.get(i).length; j++) {
                                s = s + lista.get(i)[j] + " | ";
                            }
                            s = s + "\n";
                        }
                        System.out.println(s);
                    }else{
                        System.out.println("La accion no es valida para el caso de uso");
                    }
                } catch (SQLException ex) {
                    //enviar notificacion de error
                    System.out.println("ERROR AL INGRESAR LOS PARAMETROS, VERIFIQUE LA CANTIDAD DE PARAMETROS");
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void familia(TokenEvent event) {
                System.out.println("CU: FAMILIA");
                System.out.println(event);
                
                try {
                    if (event.getAction()==Token.AGREGAR) {
                        _nfamilia.guardarFamilia(event.getParams());

                    } else if (event.getAction()==Token.MODIFY) {
                        _nfamilia.editarFamilia(event.getParams());
                        
                    } else if (event.getAction()==Token.DELETE) {
                        _nfamilia.eliminarFamilia(event.getParams());
                    }else{
                        System.out.println("La accion no es valida para el caso de uso");
                    }
                } catch (SQLException ex) {
                    //enviar notificacion de error
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void grupo(TokenEvent event) {
                System.out.println("CU: GRUPO");
                System.out.println(event);
                try {
                    if (event.getAction()==Token.AGREGAR) {
                        _ngrupo.guardarGrupo(event.getParams());

                    } else if (event.getAction()==Token.MODIFY) {
                        _ngrupo.actualizarGrupo(event.getParams());
                        
                    } else if (event.getAction()==Token.DELETE) {
                        _ngrupo.eliminarGrupo(event.getParams());
                    }else if (event.getAction()==Token.GET) {
                        ArrayList<String[]> lista =_ngrupo.mostrarGrupos();

                        String s = "";
                        for(int i = 0; i < lista.size(); i++) {
                            s = s + "["+i+"] : ";
                            for(int j = 0; j <lista.get(i).length; j++) {
                                s = s + lista.get(i)[j] + " | ";
                            }
                            s = s + "\n";
                        }
                        System.out.println(s);
                    }else{
                        System.out.println("La accion no es valida para el caso de uso");
                    }
                } catch (SQLException ex) {
                    //enviar notificacion de error
                    System.out.println("ERROR AL INGRESAR LOS PARAMETROS, VERIFIQUE LA CANTIDAD DE PARAMETROS");
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void subgrupo(TokenEvent event) {
                System.out.println("CU: SUBGRUPO");
                System.out.println(event);
                try {
                    if (event.getAction()==Token.AGREGAR) {
                        _nsubgrupo.guardarSubGrupo(event.getParams());

                    } else if (event.getAction()==Token.MODIFY) {
                        
                    } else if (event.getAction()==Token.DELETE) {
                        _nsubgrupo.eliminarSubGrupo(event.getParams());
                    }else if (event.getAction()==Token.GET) {
                        ArrayList<String[]> lista =_nsubgrupo.mostrarSubGrupos();

                        String s = "";
                        for(int i = 0; i < lista.size(); i++) {
                            s = s + "["+i+"] : ";
                            for(int j = 0; j <lista.get(i).length; j++) {
                                s = s + lista.get(i)[j] + " | ";
                            }
                            s = s + "\n";
                        }
                        System.out.println(s);
                    }else{
                        System.out.println("La accion no es valida para el caso de uso");
                    }
                } catch (SQLException ex) {
                    //enviar notificacion de error
                    System.out.println("ERROR AL INGRESAR LOS PARAMETROS, VERIFIQUE LA CANTIDAD DE PARAMETROS");
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void ubicacion(TokenEvent event) {
                
                System.out.println("CU: UBICACION");
                System.out.println(event);
                try {
                    if (event.getAction()==Token.AGREGAR) {
                        _nubicacion.guardarUbicacion(event.getParams());

                    } else if (event.getAction()==Token.MODIFY) {
                        //FALTA EL EDITAR
                    } else if (event.getAction()==Token.DELETE) {
                        //FALTA EL ELIMINAR
                    }else if (event.getAction()==Token.GET) {
                        //FALTA EL MOSTRAR
                        ArrayList<String[]> lista =_nsubgrupo.mostrarSubGrupos();

                        String s = "";
                        for(int i = 0; i < lista.size(); i++) {
                            s = s + "["+i+"] : ";
                            for(int j = 0; j <lista.get(i).length; j++) {
                                s = s + lista.get(i)[j] + " | ";
                            }
                            s = s + "\n";
                        }
                        System.out.println(s);
                    }else{
                        System.out.println("La accion no es valida para el caso de uso");
                    }
                } catch (SQLException ex) {
                    //enviar notificacion de error
                    System.out.println("ERROR AL INGRESAR LOS PARAMETROS, VERIFIQUE LA CANTIDAD DE PARAMETROS");
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }

            @Override
            public void ruta(TokenEvent event) {
                System.out.println("CU: RUTA");
                System.out.println(event);
                try {
                    if (event.getAction()==Token.AGREGAR) {
                        _nruta.guardarRuta(event.getParams());

                    } else if (event.getAction()==Token.MODIFY) {
                        //FALTA EL EDITAR RUTA
                    } else if (event.getAction()==Token.DELETE) {
                        //FALTA EL ELIMINAR RUTA
                    }else if (event.getAction()==Token.ADDUBIXRUTA) {
                        _nruta.addUbicacionARuta(event.getParams());
                    }else if (event.getAction()==Token.GET) {
                        //FALTA EL MOSTRAR RUTAS
                        ArrayList<String[]> lista =_nsubgrupo.mostrarSubGrupos();

                        String s = "";
                        for(int i = 0; i < lista.size(); i++) {
                            s = s + "["+i+"] : ";
                            for(int j = 0; j <lista.get(i).length; j++) {
                                s = s + lista.get(i)[j] + " | ";
                            }
                            s = s + "\n";
                        }
                        System.out.println(s);
                    }else{
                        System.out.println("La accion no es valida para el caso de uso");
                    }
                } catch (SQLException ex) {
                    //enviar notificacion de error
                    System.out.println("ERROR AL INGRESAR LOS PARAMETROS, VERIFIQUE LA CANTIDAD DE PARAMETROS");
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void reporte(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
        
        
        Thread thread = new Thread(interpreter);
        thread.setName("Interpreter Thread");
        thread.start();
    }
    
    
}
