/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemacentral;

import business.BProducto;
import business.BUsuario;
import business.NFamilia;
import business.NGrupo;
import business.NProducto;
import business.NRuta;
import business.NSubGrupo;
import business.NUbicacion;
import business.NUser;
import com.sun.org.apache.bcel.internal.generic.AALOAD;
import communication.MailVerificationThread;
import communication.SendEmailThread;
import data.DProducto;
import data.DUsuario;
import interfaces.IEmailEventListener;
import interpreter.analex.Interpreter;
import interpreter.analex.interfaces.ITokenEventListener;
import interpreter.analex.utils.Token;
import interpreter.events.TokenEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Email;
import utils.HtmlBuilder;


public class MailApplication 
        implements IEmailEventListener, ITokenEventListener{
    
    private static final int CONSTRAINTS_ERROR = -2;
    private static final int NUMBER_FORMAT_ERROR = -3;
    private static final int INDEX_OUT_OF_BOUND_ERROR = -4;
    private static final int PARSE_ERROR = -5;
    private static final int AUTHORIZATION_ERROR = -6;
    
    private MailVerificationThread mailVerificationThread;
    private BUsuario bUsuario;
    private BProducto bProducto;
    private NUser _nuser;
    private NFamilia _nfamilia;
    private NGrupo _ngrupo;
    private NSubGrupo _nsubgrupo;
    private NProducto _nproducto;
    private NUbicacion _nubicacion;
    private NRuta _nruta;

    public MailApplication() {
        mailVerificationThread = new MailVerificationThread();
        mailVerificationThread.setEmailEventListener(MailApplication.this);
        bUsuario = new BUsuario();
        bProducto = new BProducto();
        _nuser=new NUser();
        _nproducto=new NProducto();
        _nfamilia=new NFamilia();
        _nsubgrupo=new NSubGrupo();
        _ngrupo=new NGrupo();
        _nruta=new NRuta();
        _nubicacion=new NUbicacion();
    }
    
    public void start() {
        Thread thread = new Thread(mailVerificationThread);
        thread.setName("Mail Verfication Thread");
        thread.start();
    }
    
    @Override
    public void onReceiveEmailEvent(List<Email> emails) {
        for (Email email : emails) {
            Interpreter interpreter = new Interpreter(email.getSubject(), email.getFrom());
            interpreter.setListener(MailApplication.this);
            Thread thread = new Thread(interpreter);
            thread.setName("Interpreter Thread");
            thread.start();
        }
    }
    
    
    
    @Override
    public void usuario(TokenEvent event) {
        
        switch (event.getAction()) {
                case Token.VERIFY:
            {
                try {
                    tableNotifySuccess(event.getSender(), "Lista de Comandos", new String[] {"COMANDOS"}, _nuser.comandosAyuda());
                } catch (SQLException ex) {
                    Logger.getLogger(MailApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                    break;
        }
        
        try {
            
            if (_nuser.obtenerIdPorCorreo(event.getSender())>0) {
                if ("Admin".equals(_nuser.obtenerRolPorCorreo(event.getSender()))) {
                
                switch (event.getAction()) {
                    case Token.AGREGAR:
                        //System.out.println("Paso por aqui");
                        _nuser.guardarUser(event.getParams());
                        simpleNotifySuccess(event.getSender(), "Usuario guardado correctamente");
                        break;
                    case Token.MODIFY:
                        _nuser.editarUser(event.getParams());
                        simpleNotifySuccess(event.getSender(), "Usuario actualizado correctamente");
                        break;
                    case Token.GET:
                        tableNotifySuccess(event.getSender(), "Lista de Usuarios", _nuser.getHeaders(), _nuser.mostrarUsers());
                        break;
                    case Token.ACTION:
                        tableNotifySuccess(event.getSender(), "Lista de Usuarios", _nuser.getHeaders(), _nuser.mostrarUsers());
                        break;
                    case Token.GRAFBARRA:
                        tableNotifySuccessGraf(event.getSender(), "Lista de Promedios de Visitas (dias)", _nuser.getHeadersGraf(), _nuser.listarPromDiasVisitasVendedor(),_nuser.urlgrafico());
                        break;
                }
                }else{
                    handleError(AUTHORIZATION_ERROR, event.getSender(), null);
                }
            }else{
                //simpleNotifySuccess(event.getSender(), "No tiene permisos para realizar la acción");
                System.out.println("Esta pasando por aqui");
                handleError(AUTHORIZATION_ERROR, event.getSender(), null);
            }
        } catch (NumberFormatException ex) {
            handleError(NUMBER_FORMAT_ERROR, event.getSender(), null);
        } catch (SQLException exes) {
            handleError(CONSTRAINTS_ERROR, event.getSender(), null);
        } catch (IndexOutOfBoundsException ex) {
            handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
        }
    }

    @Override
    public void producto(TokenEvent event) {
        try {
            if (_nuser.obtenerIdPorCorreo(event.getSender())>0) {
                if ("Admin".equals(_nuser.obtenerRolPorCorreo(event.getSender()))) {
                switch (event.getAction()) {
                    case Token.AGREGAR:
                        _nproducto.guardarProducto(event.getParams());                        
                        simpleNotifySuccess(event.getSender(), "Producto guardado correctamente");
                        break;
                    case Token.MODIFY:
                        _nproducto.editarProducto(event.getParams());                        
                        simpleNotifySuccess(event.getSender(), "Producto actualizado correctamente");
                        break;
                    case Token.GET:
                        tableNotifySuccess(event.getSender(), "Lista de Productos",_nproducto.headers() ,_nproducto.mostrarPrpductos() );
                        break;
                }
                }else{
                    handleError(AUTHORIZATION_ERROR, event.getSender(), null);
                }
            } else {
                handleError(AUTHORIZATION_ERROR, event.getSender(), null);
            }
        } catch (NumberFormatException ex) {
            handleError(NUMBER_FORMAT_ERROR, event.getSender(), null);
        } catch (SQLException exes) {
            handleError(CONSTRAINTS_ERROR, event.getSender(), null);
        } catch (IndexOutOfBoundsException ex) {
            handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
        }
    }

    @Override
    public void error(TokenEvent event) {
        handleError(event.getAction(), event.getSender(), event.getParams());
    }
    
    
    private void handleError(int type, String email, List<String> args) {
        Email emailObject = null;

        switch (type) {
            case Token.ERROR_CHARACTER:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generateText(new String[]{
                    "Caracter desconocido",
                    "No se pudo ejecutar el comando [" + args.get(0) + "] debido a: ",
                    "El caracter \"" + args.get(1) + "\" es desconocido."
                }));
                break;
            case Token.ERROR_COMMAND:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generateText(new String[]{
                    "Comando desconocido",
                    "No se pudo ejecutar el comando [" + args.get(0) + "] debido a: ",
                    "No se reconoce la palabra \"" + args.get(1) + "\" como un comando válido"
                }));
                break;
            case CONSTRAINTS_ERROR:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generateText(new String[]{
                    "Error al interactuar con la base de datos",
                    "Referencia a información inexistente"
                }));
                break;
            case NUMBER_FORMAT_ERROR:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generateText(new String[]{
                    "Error en el tipo de parámetro",
                    "El tipo de uno de los parámetros es incorrecto"
                }));
                break;
            case INDEX_OUT_OF_BOUND_ERROR:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generateText(new String[]{
                    "Cantidad de parámetros incorrecta",
                    "La cantidad de parámetros para realizar la acción es incorrecta"
                }));
                break;
            case PARSE_ERROR:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generateText(new String[]{
                    "Error al procesar la fecha",
                    "La fecha introducida posee un formato incorrecto"
                }));
                break;
            case AUTHORIZATION_ERROR:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generateText(new String[]{
                    "Acceso denegado",
                    "Usted no posee los permisos necesarios para realizar la acción solicitada"
                }));
                break;
        }
        sendEmail(emailObject);
    }

    private void simpleNotifySuccess(String email, String message) {
        Email emailObject = new Email(email, Email.SUBJECT,
                HtmlBuilder.generateText(new String[]{
            "Petición realizada correctamente",
            message
        }));
        sendEmail(emailObject);
    }

    private void simpleNotify(String email, String title, String topic, String message) {
        Email emailObject = new Email(email, Email.SUBJECT,
                HtmlBuilder.generateText(new String[]{
            title, topic, message
        }));
        sendEmail(emailObject);
    }

    private void tableNotifySuccess(String email, String title, String[] headers, ArrayList<String[]> data) {
        Email emailObject = new Email(email, Email.SUBJECT,
                HtmlBuilder.generateTable(title, headers, data));
        sendEmail(emailObject);
    }
    private void tableNotifySuccessGraf(String email, String title, String[] headers, ArrayList<String[]> data,String Link) {
        Email emailObject = new Email(email, Email.SUBJECT,
                HtmlBuilder.generateTableGraf(title, headers, data, Link));
        sendEmail(emailObject);
    }

    private void simpleTableNotifySuccess(String email, String title, String[] headers, String[] data) {
        Email emailObject = new Email(email, Email.SUBJECT,
                HtmlBuilder.generateTableForSimpleData(title, headers, data));
        sendEmail(emailObject);
    }

    private void sendEmail(Email email) {
        SendEmailThread sendEmail = new SendEmailThread(email);
        Thread thread = new Thread(sendEmail);
        thread.setName("Send email Thread");
        thread.start();
    }

    @Override
    public void familia(TokenEvent event) {
        try {
            if (_nuser.obtenerIdPorCorreo(event.getSender())>0) {
                if ("Admin".equals(_nuser.obtenerRolPorCorreo(event.getSender()))) {
                switch (event.getAction()) {
                    case Token.AGREGAR:
                        _nfamilia.guardarFamilia(event.getParams());                        
                        simpleNotifySuccess(event.getSender(), "Catalogo guardado correctamente");
                        break;
                    case Token.MODIFY:
                        _nfamilia.editarFamilia(event.getParams());                        
                        simpleNotifySuccess(event.getSender(), "Catalogo actualizado correctamente");
                        break;
                    case Token.GET:
                        tableNotifySuccess(event.getSender(), "Lista de Catalogo",_nfamilia.getHeaders(),_nfamilia.mostrarFamilias());
                        break;
                }
                }else{
                    handleError(AUTHORIZATION_ERROR, event.getSender(), null);
                }
            } else {
                handleError(AUTHORIZATION_ERROR, event.getSender(), null);
            }
        } catch (NumberFormatException ex) {
            handleError(NUMBER_FORMAT_ERROR, event.getSender(), null);
        } catch (SQLException exes) {
            handleError(CONSTRAINTS_ERROR, event.getSender(), null);
        } catch (IndexOutOfBoundsException ex) {
            handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
        }
    }

    @Override
    public void grupo(TokenEvent event) {
                try {
            if (_nuser.obtenerIdPorCorreo(event.getSender())>0) {
                if ("Admin".equals(_nuser.obtenerRolPorCorreo(event.getSender()))) {
                switch (event.getAction()) {
                    case Token.AGREGAR:
                        _nproducto.guardarProducto(event.getParams());                        
                        simpleNotifySuccess(event.getSender(), "Grupo guardado correctamente");
                        break;
                    case Token.MODIFY:
                        _nproducto.editarProducto(event.getParams());                        
                        simpleNotifySuccess(event.getSender(), "Grupo actualizado correctamente");
                        break;
                    case Token.GET:
                        tableNotifySuccess(event.getSender(), "Lista de Grupos",_nproducto.headers() ,_nproducto.mostrarPrpductos() );
                        break;
                }
                }else{
                    handleError(AUTHORIZATION_ERROR, event.getSender(), null);
                }
            } else {
                handleError(AUTHORIZATION_ERROR, event.getSender(), null);
            }
        } catch (NumberFormatException ex) {
            handleError(NUMBER_FORMAT_ERROR, event.getSender(), null);
        } catch (SQLException exes) {
            handleError(CONSTRAINTS_ERROR, event.getSender(), null);
        } catch (IndexOutOfBoundsException ex) {
            handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
        }
    }

    @Override
    public void subgrupo(TokenEvent event) {
        try {
            if (_nuser.obtenerIdPorCorreo(event.getSender())>0) {
                if ("Admin".equals(_nuser.obtenerRolPorCorreo(event.getSender()))) {
                switch (event.getAction()) {
                    case Token.AGREGAR:
                        _nsubgrupo.guardarSubGrupo(event.getParams());                        
                        simpleNotifySuccess(event.getSender(), "Subgrupo guardado correctamente");
                        break;
                    case Token.MODIFY:
                        _nsubgrupo.actualizarSubGrupo(event.getParams());                        
                        simpleNotifySuccess(event.getSender(), "Subgrupo actualizado correctamente");
                        break;
                    case Token.GET:
                        tableNotifySuccess(event.getSender(), "Lista de Subgrupo",_nsubgrupo.getHeaders() ,_nsubgrupo.mostrarSubGrupos());
                        break;
                }
                }else{
                    handleError(AUTHORIZATION_ERROR, event.getSender(), null);
                }
            } else {
                handleError(AUTHORIZATION_ERROR, event.getSender(), null);
            }
        } catch (NumberFormatException ex) {
            handleError(NUMBER_FORMAT_ERROR, event.getSender(), null);
        } catch (SQLException exes) {
            handleError(CONSTRAINTS_ERROR, event.getSender(), null);
        } catch (IndexOutOfBoundsException ex) {
            handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
        }
    }

    @Override
    public void ubicacion(TokenEvent event) {
        try {
            if (_nuser.obtenerIdPorCorreo(event.getSender())>0) {
                switch (event.getAction()) {
                    case Token.AGREGAR:
                        _nubicacion.guardarUbicacion(event.getParams());                        
                        simpleNotifySuccess(event.getSender(), "Ubicacion se ha guardado correctamente");
                        break;
                    case Token.MODIFY:
                        _nubicacion.editarUbicacion(event.getParams());                        
                        simpleNotifySuccess(event.getSender(), "Ubicacion se ha actualizado correctamente");
                        break;
                    case Token.GET:
                        tableNotifySuccess(event.getSender(), "Lista de Ubicaciones",_nproducto.headers() ,_nproducto.mostrarPrpductos() );
                        break;
                }
            } else {
                handleError(AUTHORIZATION_ERROR, event.getSender(), null);
            }
        } catch (NumberFormatException ex) {
            handleError(NUMBER_FORMAT_ERROR, event.getSender(), null);
        } catch (SQLException exes) {
            handleError(CONSTRAINTS_ERROR, event.getSender(), null);
        } catch (IndexOutOfBoundsException ex) {
            handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
        }        
    }

    @Override
    public void ruta(TokenEvent event) {
        try {    
            System.out.println(_nuser.obtenerRolPorCorreo(event.getSender()));
        } catch (SQLException ex) {
            Logger.getLogger(MailApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if ((_nuser.obtenerIdPorCorreo(event.getSender())>0)  ) {
                if ("Admin".equals(_nuser.obtenerRolPorCorreo(event.getSender()))) {
                
                switch (event.getAction()) {
                    case Token.AGREGAR:
                        _nruta.guardarRuta(event.getParams());                        
                        simpleNotifySuccess(event.getSender(), "La ruta se ha guardado correctamente");
                        break;
                    case Token.MODIFY:
                        _nruta.actualizarRuta(event.getParams());                        
                        simpleNotifySuccess(event.getSender(), "La ruta se ha actualizado correctamente");
                        break;
                    case Token.ADDUBIXRUTA:
                        _nruta.addUbicacionARuta(event.getParams());                        
                        simpleNotifySuccess(event.getSender(), "Ubicacion añadida a la Ruta correctamente");
                        break;
                    case Token.GET:
                        tableNotifySuccess(event.getSender(), "Lista de rutas",_nruta.getHeaders(),_nruta.mostrarRutas());
                        break;
                        
                    }
                }else{
                    handleError(AUTHORIZATION_ERROR, event.getSender(), null);
                }
            
            } else {
                handleError(AUTHORIZATION_ERROR, event.getSender(), null);
            }
        } catch (NumberFormatException ex) {
            handleError(NUMBER_FORMAT_ERROR, event.getSender(), null);
        } catch (SQLException exes) {
            handleError(CONSTRAINTS_ERROR, event.getSender(), null);
        } catch (IndexOutOfBoundsException ex) {
            handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
        } 
    }
    @Override
    public void reporte(TokenEvent event) {
                try {
            if (_nuser.obtenerIdPorCorreo(event.getSender())>0) {
                if ("Admin".equals(_nuser.obtenerRolPorCorreo(event.getSender()))) {
                switch (event.getAction()) {
                    case Token.REPCLISINUBI:
                        tableNotifySuccess(event.getSender(), "Lista de Clientes sin ubicación asociada",_nuser.getHeaders(),_nuser.listarUsuariosSinUbicacion());
                        break;
                    case Token.REPCLICONUBI:
                        tableNotifySuccess(event.getSender(), "Lista de Clientes con una ubicación asociada",_nuser.getHeaders(),_nuser.listarUsuariosConUbicacion());
                        break;
                    case Token.REPVENSINRUTA:
                        tableNotifySuccess(event.getSender(), "Lista de Vendedores sin una ruta asociada",_nuser.getHeaders(),_nuser.listarVendedoresSinRuta());
                        break;
                    case Token.REPVENCONRUTA:
                        tableNotifySuccess(event.getSender(), "Lista Vendedores con ruta asociada",_nuser.getHeaders(),_nuser.listarVendedoresConRuta());
                        break;
                }
                }else{
                    handleError(AUTHORIZATION_ERROR, event.getSender(), null);
                }
            } else {
                handleError(AUTHORIZATION_ERROR, event.getSender(), null);
            }
        } catch (NumberFormatException ex) {
            handleError(NUMBER_FORMAT_ERROR, event.getSender(), null);
        } catch (SQLException exes) {
            handleError(CONSTRAINTS_ERROR, event.getSender(), null);
        } catch (IndexOutOfBoundsException ex) {
            handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
        } 
    }
    
}
