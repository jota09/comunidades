/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.GestionFachada;
import fachada.PerfilFachada;
import fachada.RegistroFachada;
import fachada.SeguridadUsuarioFachada;
import fachada.UsuarioFachada;
import fachada.UsuarioPerfilFachada;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
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
        int op = Integer.parseInt(request.getParameter("op"));
        try {
            switch (op) {
                case 1: {
                    validarCodigo(request, response);
                    break;
                }
                case 2: {
                    registrarUsuario(request, response);
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

    private void validarCodigo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        String codigoRegistro = request.getParameter("codigoRegistro");
        GestionFachada registroFachada = new RegistroFachada();
        Registro registro = new Registro();
        registro.setCodigoGenerado(codigoRegistro);
        registroFachada.getObject(registro);
        PrintWriter out = response.getWriter();
        if (registro.getComunidad() != null && registro.getFechaVencimiento().after(new Date())) {
            construyeFormulario(out, codigoRegistro,registro.getCorreo());
        } else {
            out.print("<h2 class='text-center'>EL CODIGO NO ES VALIDO</h2>");
        }

    }

    private void construyeFormulario(PrintWriter out, String codigo,String correo) {
        String html = "<form action='RegistroControlador' method='post'> <label>Documento:</label>";
        html += "<input type='text' id='documento' name='documento' class='form-control'>";
        html += "<label>Nombre:</label>";
        html += "<input type='text' name='nombre' id='nombre' class='form-control margenTop'>";
        html += "<label>Apellido:</label>";
        html += "<input type='text' name='apellido' id='apellido' class='form-control margenTop'>";
        html += "<label>Correo:</label>";
        html += "<input type='text' name='correo' value='"+correo+"' disabled id='correo' class='form-control margenTop'>";
        html += "<label>Celular:</label>";
        html += "<input type='text' name='celular' id='celular' class='form-control margenTop'>";
        html += "<label>Telefono:</label>";
        html += "<input type='text' name='telefono' id='telefono' class='form-control margenTop'>";
        html += "<label>Nombre de Usuario:</label>";
        html += "<input type='text' id='username' name='username' class='form-control margenTop'>";
        html += "<label>Contraseña:</label>";
        html += "<input type='password' name='contrasena' id='contrasena' class='form-control margenTop'>";
        html += "<label>Confirmar Contraseña:</label>";
        html += "<input type='password' name='confirmContrasena' id='confirmContrasena' class='form-control margenTop'>";
        html += "<input type='hidden' name='codigoRegistro' value='" + codigo + "' >";
        html += "<input type='hidden' name='op' value='2' >";
        html += "<input type='submit' value='Registrar'style='margin-top:5px' class='btn btn-primary pull-right'></form>";

        out.print(html);
    }

    private void registrarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        String codigoRegistro = request.getParameter("codigoRegistro");
        GestionFachada registroFachada = new RegistroFachada();
        GestionFachada usuarioFachada = new UsuarioFachada();
        GestionFachada seguridadFachada = new SeguridadUsuarioFachada();
        Registro registro = new Registro();
        registro.setCodigoGenerado(codigoRegistro);
        registroFachada.getObject(registro);
        if (registro.getComunidad() != null) {
            String userName = request.getParameter("username");
            Usuario user = new Usuario();
            user.setCodigoDocumento(Integer.parseInt(request.getParameter("documento")));
            user.setNombres(request.getParameter("nombre"));
            user.setApellidos(request.getParameter("apellido"));
            user.setCorreo(request.getParameter("correo"));
            user.setCelular(request.getParameter("celular"));
            user.setTelefono(request.getParameter("telefono"));
            user.setTipoDocumentoCodigo(1);
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
                String key = LecturaConfig.getValue("key").trim();
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

}
