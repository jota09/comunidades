/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.EstructuraFachada;
import fachada.GestionFachada;
import fachada.SeguridadUsuarioFachada;
import fachada.UsuarioFachada;
import fachada.UsuarioPerfilFachada;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import persistencia.entidades.Estructura;
import persistencia.entidades.Perfil;
import persistencia.entidades.SeguridadUsuario;
import persistencia.entidades.Usuario;
import utilitarias.Cifrar;
import utilitarias.LecturaConfig;
import utilitarias.Utilitaria;
import persistencia.entidades.Error;
import persistencia.entidades.TipoError;

/**
 *
 * @author ferney.medina
 */
@WebServlet(name = "Inicio", urlPatterns = {"/validacion"})
public class Inicio extends HttpServlet {

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
            throws ServletException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            String clave = LecturaConfig.getValue("key");
            //out.println("esto es clave2:"+clave);
            String cifrado = Cifrar.setEncryp(clave, request.getParameter("password"));
            String username = request.getParameter("username");
            Usuario usr = new Usuario();
            int op = validadorCampos(username);
            switch (op) {
                case 1:
                    usr.setCodigoDocumento(Integer.parseInt(username));
                    break;
                case 2:
                    usr.setCorreo(username);
                    break;
                case 3:
                    usr.setUserName(username);
                    break;
            }
     
            SeguridadUsuario sgUsr = new SeguridadUsuario();
            sgUsr.setContrasena(cifrado);
            sgUsr.setIpUltimaSesion(request.getRemoteUser());
            usr.setListaSeguridad(sgUsr);
            SeguridadUsuarioFachada sgdadFach = new SeguridadUsuarioFachada();
            UsuarioFachada usrFachada = new UsuarioFachada();
            usr = (Usuario) usrFachada.getObject(usr);
            //out.println("nombre:"+usr.getNombres());
            if (usr.getNombres() != null) {
                HttpSession sesion = request.getSession(true);

                GestionFachada estructuraFachada = new EstructuraFachada();
                GestionFachada usuarioPerfilFachada = new UsuarioPerfilFachada();
                List<Perfil> perfiles = usuarioPerfilFachada.getListObject(usr);
                if (perfiles.size() == 1) {
                    usr.setPerfilCodigo(perfiles.get(0));
                    request.getSession().setAttribute("view", "menuprincipal.html");
                } else {
                    request.getSession().setAttribute("perfiles", perfiles);
                    request.getSession().setAttribute("view", "validacomunidad.html");
                }
                sgdadFach.updateObject(sgUsr);
                sesion.setAttribute("user", usr);
                Estructura estructura = new Estructura();
                estructura.setReferencia("tiempoSesion");
                estructuraFachada.getObject(estructura);
                sesion.setMaxInactiveInterval(Integer.parseInt(estructura.getValor()));
                sesion.setAttribute("tiempoSesion", estructura.getValor());
                response.sendRedirect("/Comunidades");
                //crear sesion, validar perfil y redireccionar a menu
            } else {
                HttpSession sesion = request.getSession();
                if (op == 1) {
                    sesion.setAttribute("message", Utilitaria.createAlert("Error", "Documento no Valido", "danger"));
                    response.sendRedirect("/Comunidades");
                } else if (op == 2) {
                    sesion.setAttribute("message", Utilitaria.createAlert("Error", "Correo no Valido", "danger"));
                    response.sendRedirect("/Comunidades");
                } else if (op == 3) {
                    sesion.setAttribute("message", Utilitaria.createAlert("Error", "Usuario no Valido", "danger"));
                    response.sendRedirect("/Comunidades");
                }
            }
        } catch (NoSuchAlgorithmException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("processRequest");
            error.setTipoError(new TipoError(7));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (NoSuchPaddingException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("processRequest");
            error.setTipoError(new TipoError(8));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (InvalidKeyException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("processRequest");
            error.setTipoError(new TipoError(9));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IllegalBlockSizeException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("processRequest");
            error.setTipoError(new TipoError(10));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (BadPaddingException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("processRequest");
            error.setTipoError(new TipoError(11));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("processRequest");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        }

    }

    public int validadorCampos(String campo) {
        int validador = 0;
        Pattern pat = Pattern.compile("^\\d+$");
        Matcher mat = pat.matcher(campo);
        if (mat.matches()) {//como codigo_documento
            //System.out.println("solo digitos");
            validador = 1;
        } else {
            pat = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            mat = pat.matcher(campo);
            if (mat.matches()) {//como correo
                //System.out.println("Es correo");
                validador = 2;
            } else//como username
            {
                //System.out.println("Es otra cosa");
                validador = 3;
            }
        }
        return validador;
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
        //processRequest(request, response);
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

}
