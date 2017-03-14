/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.lowagie.text.DocumentException;
import fachada.GestionFachada;
import fachada.PerfilFachada;
import fachada.RegistroFachada;
import fachada.SeguridadUsuarioFachada;
import fachada.TipoDocumentoFachada;
import fachada.UsuarioFachada;
import fachada.UsuarioPerfilFachada;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import persistencia.entidades.Perfil;
import persistencia.entidades.Registro;
import persistencia.entidades.SeguridadUsuario;
import persistencia.entidades.TipoDocumento;
import persistencia.entidades.Usuario;
import persistencia.entidades.UsuarioPerfil;
import utilitarias.Cifrar;
import utilitarias.LecturaConfig;
import utilitarias.Utilitaria;

/**
 *
 * @author manuel.alcala
 */
@WebServlet(name = "RegistroControlador", urlPatterns = {"/RegistroControlador"})
public class RegistroControlador extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int op = Integer.parseInt(request.getParameter("op"));
        try {
            switch (op) {
                case 1: {
                    validarCodigo(request, response);
                    break;
                }
                case 2: {
                    registrarUsuario(request, response);
                    break;
                }
                case 3: {
                    validarDocumento(request, response);
                    break;
                }
                case 4: {
                    validarUsername(request, response);
                    break;
                }
            }
        } catch (IOException ex) {
            
            ex.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RegistroControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(RegistroControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(RegistroControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(RegistroControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(RegistroControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(RegistroControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(RegistroControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void validarCodigo(HttpServletRequest request, HttpServletResponse response) throws IOException, JRException, DocumentException {
        response.setContentType("text/html;charset=UTF-8");
        String codigoRegistro = request.getParameter("codigoRegistro");
        GestionFachada registroFachada = new RegistroFachada();
        Registro registro = new Registro();
        registro.setCodigoGenerado(codigoRegistro);
        registroFachada.getObject(registro);
//        PrintWriter out = response.getWriter();
//        if (registro.getComunidad() != null && registro.getFechaVencimiento().after(new Date())) {
//            construyeFormulario(out, codigoRegistro, registro.getCorreo());
//        } else {
//            out.print("<h2 class='text-center'>EL CODIGO NO ES VALIDO</h2>");
//        }
         Utilitaria.exportarPDF("report1.jasper",new HashMap<String,Object>(), response, "hola.pdf");

    }

    private void construyeFormulario(PrintWriter out, String codigo, String correo) {
        GestionFachada tipoDocumentoFachada = new TipoDocumentoFachada();
        String html = "<form action='RegistroControlador' method='post'> ";
        html += "<label>Tipo Documento(*):</label>";
        html += "<select class='form-control' id='tipoDocumento' name='tipoDocumento' required>";
        for (TipoDocumento tipo : (List<TipoDocumento>) tipoDocumentoFachada.getListObject()) {
            html += "<option value='" + tipo.getCodigo() + "'>" + tipo.getNombre() + "</option>";
        }
        html += "</select><label>Documento(*):</label>";
        html += "<input type='text' onBlur='validarDocumento()' id='documento' minlength='8' maxlength='15' onKeyPress='return soloNumeros(event)' name='documento' class='form-control' required>";
        html += "<div id='mensajeDocumento'/>";
        html += "<label>Nombre(*):</label>";
        html += "<input type='text' name='nombre' id='nombre' class='form-control margenTop' required>";
        html += "<label>Apellido(*):</label>";
        html += "<input type='text' name='apellido' id='apellido' class='form-control margenTop' required>";
        html += "<label>Correo(*):</label>";
        html += "<input type='text'  value='" + correo + "' disabled  class='form-control margenTop' required>";
        html += "<label>Celular(*):</label>";
        html += "<input type='text' name='celular' id='celular' class='form-control margenTop' required>";
        html += "<label>Telefono(*):</label>";
        html += "<input type='text' name='telefono' id='telefono' class='form-control margenTop' required>";
        html += "<label>Nombre de Usuario(*):</label>";
        html += "<input type='text' id='usern' onBlur='validarUserName()' minlength='5' maxlength='12' name='username' class='form-control margenTop' required>";
        html += "<div id='mensajeUsuario'/>";
        html += "<label>Contraseña(*):</label>";
        html += "<input type='password' maxlength='20' minlength='6' name='contrasena' id='contrasena' class='form-control margenTop' required>";
        html += "<label>Confirmar Contraseña(*):</label>";
        html += "<input type='password' maxlength='20' minlength='6' onBlur='confirmarContrasena()' name='confirmContrasena' id='confirmContrasena' class='form-control margenTop' required>";
        html += "<input type='hidden' name='codigoRegistro' value='" + codigo + "' >";
        html += "<input type='hidden' name='op' value='2' >";
        html += "<input type='submit' id='btnRegistro' value='Registrar'style='margin-top:5px' class='btn btn-primary pull-right'>"
                + "<input type='hidden' value=\"" + correo + "\" id='email' name='email' />"
                + "</form>";

        out.print(html);
    }

    private void registrarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        String codigoRegistro = request.getParameter("codigoRegistro");
        GestionFachada registroFachada = new RegistroFachada();
        GestionFachada usuarioFachada = new UsuarioFachada();
        GestionFachada seguridadFachada = new SeguridadUsuarioFachada();
        Registro registro = new Registro();
        registro.setCodigoGenerado(codigoRegistro);
        String key = LecturaConfig.getValue("key").trim();
        registroFachada.getObject(registro);
        if (registro.getComunidad() != null) {
            String userName = request.getParameter("username");
            Usuario user = new Usuario();
            user.setCodigoDocumento(Integer.parseInt(request.getParameter("documento")));
            user.setNombres(request.getParameter("nombre"));
            user.setApellidos(request.getParameter("apellido"));
            user.setCorreo(request.getParameter("email"));
            user.setCelular(request.getParameter("celular"));
            user.setTelefono(request.getParameter("telefono"));
            user.setTipoDocumentoCodigo(Integer.parseInt(request.getParameter("tipoDocumento")));
            user.setUserName(userName);
            if (usuarioFachada.insertObject(user) > 0) {
                GestionFachada perfilFachada = new PerfilFachada();
                GestionFachada usuarioPerfilFachada = new UsuarioPerfilFachada();
                Perfil perfil = new Perfil();
                perfil.setComunidad(registro.getComunidad());
                perfil.setNombre("USUARIO");
                perfilFachada.getObject(perfil);
                UsuarioPerfil usuarioPerfil = new UsuarioPerfil();
                usuarioPerfil.setPerfil(perfil);
                usuarioPerfil.setUsuario(user);
                usuarioPerfilFachada.insertObject(usuarioPerfil);
                SeguridadUsuario seguridadUsuario = new SeguridadUsuario();
                seguridadUsuario.setUsuarioCodigo(user.getCodigo());
                seguridadUsuario.setIpUltimaSesion(request.getRemoteAddr());
                seguridadUsuario.setContrasena(Cifrar.setEncryp(key, request.getParameter("contrasena").trim()));
                if (seguridadFachada.insertObject(seguridadUsuario) > 0) {
                    request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se creo el usuario " + userName, "success"));
                } else {
                    request.getSession().setAttribute("message", Utilitaria.createAlert("Error", "No se Creo el usuario " + userName, "danger"));
                }
            } else {
                request.getSession().setAttribute("message", Utilitaria.createAlert("Error", "No se Creo el usuario " + userName, "danger"));
            }

        }
        response.sendRedirect("/Comunidades");

    }

    private void validarDocumento(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int documento = Integer.parseInt(request.getParameter("documento"));
        GestionFachada usuarioFachada = new UsuarioFachada();
        Usuario usuario = new Usuario();
        usuario.setCodigoDocumento(documento);
        PrintWriter out = response.getWriter();
        out.print(usuarioFachada.getCount(usuario));

    }

    private void validarUsername(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameter("username");
        GestionFachada usuarioFachada = new UsuarioFachada();
        Usuario usuario = new Usuario();
        usuario.setUserName(userName);
        PrintWriter out = response.getWriter();
        out.print(usuarioFachada.getCount(usuario));
    }

}
