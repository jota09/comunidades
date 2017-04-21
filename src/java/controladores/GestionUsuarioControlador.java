/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.CondicionPaginacionFachada;
import fachada.EstructuraFachada;
import fachada.GestionFachada;
import fachada.RegistroFachada;
import fachada.UsuarioFachada;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import persistencia.entidades.Comunidad;
import persistencia.entidades.CondicionPaginacion;
import persistencia.entidades.Estructura;
import persistencia.entidades.Registro;
import persistencia.entidades.Usuario;
import utilitarias.CondicionPaginado;
import utilitarias.LecturaConfig;
import utilitarias.ServicioDeEnvioMail;
import utilitarias.Utilitaria;

/**
 *
 * @author manuel.alcala
 */
@WebServlet(name = "GestionUsuarioControlador", urlPatterns = {"/GestionUsuarioControlador"})
public class GestionUsuarioControlador extends HttpServlet {

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
        try {
            response.setContentType("text/html;charset=UTF-8");
            int op = Integer.parseInt(request.getParameter("op"));
            switch (op) {
                case 1: {
                    getUsuarios(request, response);
                    break;
                }
                case 2: {
                    generarCodigo(request, response);
                    break;
                }
                case 3: {
                    validarCorreo(request, response);
                    break;
                }
                case 4: {
                    recuperarSesion(request, response);
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(GestionUsuarioControlador.class.getName()).log(Level.SEVERE, null, ex);
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

    private void getUsuarios(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONArray arrayUsuarios = new JSONArray();
        GestionFachada usuarioFachada = new UsuarioFachada();
        String rango = request.getParameter("rango");
        String condicionesPag = request.getParameter("condicionesPag");
        String busqueda = request.getParameter("busqueda");
        GestionFachada condicionFachada = new CondicionPaginacionFachada();
        CondicionPaginacion condicionPaginacion = new CondicionPaginacion(Integer.parseInt(condicionesPag));
        condicionFachada.getObject(condicionPaginacion);
        CondicionPaginado condicion = new CondicionPaginado();
        Usuario user = (Usuario) request.getSession().getAttribute("user");
        condicion.setComunidad(user.getPerfilCodigo().getComunidad());
        condicion.setCondicion(condicionPaginacion.getCondicion().replace("<?>", busqueda) + " limit " + rango);
        List<Usuario> usuarios = usuarioFachada.getListObject(condicion);
        PrintWriter out = response.getWriter();
        for (Usuario u : usuarios) {
            JSONObject obj = new JSONObject();
            obj.put("documento", u.getCodigoDocumento());
            obj.put("nombres", u.getNombres() + " " + u.getApellidos());
            obj.put("correo", u.getCorreo());
            obj.put("celular", u.getCelular());
            obj.put("telefono", u.getTelefono());
            obj.put("username", u.getUserName());
            arrayUsuarios.add(obj);
        }
        out.print(arrayUsuarios);
    }

    private void generarCodigo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GestionFachada estructuraFachada = new EstructuraFachada();
        GestionFachada registroFachada = new RegistroFachada();
        String correo = request.getParameter("correo");
        String tipo = request.getParameter("tipo");
        Registro registro = new Registro();
        Calendar tiempoInvalidacion = Calendar.getInstance();
        HttpSession session = request.getSession();
        Comunidad comunidad = ((Usuario) session.getAttribute("user")).getPerfilCodigo().getComunidad();
        Estructura estructura = new Estructura();
        estructura.setReferencia("tiempoVigenciaCodigoGenerado");
        estructuraFachada.getObject(estructura);
        int tiempoHoras = Integer.parseInt(estructura.getValor());
        long tiempoEnMilis = tiempoInvalidacion.getTimeInMillis() + (tiempoHoras * 60 * 1000);
        registro.setComunidad(comunidad);
        registro.setCodigoGenerado(Utilitaria.genCodigoRegComunidad(comunidad.getCodigo()));
        registro.setFechaVencimiento(new Timestamp(tiempoEnMilis));
        registro.setCorreo(correo);
        registroFachada.insertObject(registro);
        String host = ((Estructura) estructuraFachada.getObject(new Estructura("hostServerSMTP"))).getValor();
        int puerto = (Integer.parseInt(((Estructura) estructuraFachada.getObject(new Estructura("puertoSMTP"))).getValor()));
        String correoSoporte = ((Estructura) estructuraFachada.getObject(new Estructura("correoSoporte"))).getValor();
        String usuario = ((Estructura) estructuraFachada.getObject(new Estructura("usuarioMailSoporte"))).getValor();
        String password = ((Estructura) estructuraFachada.getObject(new Estructura("passCorreoSoporte"))).getValor();
        String serverSSL = ((Estructura) estructuraFachada.getObject(new Estructura("sslSMTP"))).getValor();
        String autenticacion = ((Estructura) estructuraFachada.getObject(new Estructura("autenticacionSMTP"))).getValor();
        String starttls = ((Estructura) estructuraFachada.getObject(new Estructura("tlsSMTP"))).getValor();
        ServicioDeEnvioMail envioMail = new ServicioDeEnvioMail(host, puerto, correoSoporte, usuario, password, starttls, autenticacion, serverSSL);
        String plantilla = Utilitaria.leerPlantilla("1.html");
        plantilla = plantilla.replace("<#comunidad#>", comunidad.getNombre());
        plantilla = plantilla.replace("<#codigoRegistro#>", registro.getCodigoGenerado());
        String rutaImg[] = {LecturaConfig.getValue("rutaImgPlantillas") + "logos" + File.separator + comunidad.getCodigo() + ".png"};
        envioMail.sendEmail(plantilla, "Generación de Codigo de Registro para la comunidad " + comunidad.getNombre(), correo, rutaImg, null, null);
        tiempoInvalidacion.setTimeInMillis(tiempoEnMilis);
        if (tipo.equals("0")) {
            request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se genero el codigo para el correo " + correo, "success"));
        } else {
            request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se generarón los codigos masivamente", "success"));
        }

    }

    private void validarCorreo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String correo = request.getParameter("correo");
        GestionFachada usuarioFachada = new UsuarioFachada();
        Usuario user = new Usuario();
        user.setCorreo(correo);
        CondicionPaginado condicion = new CondicionPaginado();
        condicion.setUser(user);
        PrintWriter out = response.getWriter();
        if (usuarioFachada.getCount(condicion) > 0) {
            out.print(1);
        } else {
            out.print(0);
        }
    }

    private void recuperarSesion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            Usuario u = (Usuario) request.getSession().getAttribute("user");
            JSONObject obj = new JSONObject();
            EstructuraFachada estrucFach = new EstructuraFachada();
            Estructura estru = ((Estructura) estrucFach.getObject(new Estructura("sinAvatarUser")));
            obj.put("documento", u.getCodigoDocumento());
            obj.put("nombres", u.getNombres());
            obj.put("apellidos", u.getApellidos());
            obj.put("correo", u.getCorreo());
            obj.put("celular", u.getCelular());
            obj.put("telefono", u.getTelefono());
            obj.put("username", u.getUserName());
            obj.put("profesion", u.getProfesion());
            obj.put("fecha", u.getFechanacimiento().toString());
            if (u.getAvatar()!= 0) {
                obj.put("avatar", LecturaConfig.getValue("rutaVisualizaUsuario"));
            } else {
                obj.put("avatar", LecturaConfig.getValue("rutaVisualizaUsuario") + "" + File.pathSeparator + "" + estru.getValor());
            }
            System.out.println("Avatar: "+estru.getValor());
            out.print(obj);
        }
    }
}
