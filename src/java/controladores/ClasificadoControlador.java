/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.ArticuloFachada;
import fachada.CategoriaFachada;
import fachada.PrioridadFachada;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import persistencia.entidades.Articulo;
import persistencia.entidades.ArticuloEstado;
import persistencia.entidades.Categoria;
import persistencia.entidades.Prioridad;
import persistencia.entidades.TipoArticulo;
import persistencia.entidades.Usuario;

/**
 *
 * @author Jesus.Ramos
 */
@WebServlet(name = "ClasificadoControlador", urlPatterns = {"/ClasificadoControlador"})
public class ClasificadoControlador extends HttpServlet {

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
        System.out.println(request);
        if (request.getParameter("opc") != null) {
            int opcion = Integer.parseInt(request.getParameter("opc"));
            switch (opcion) {
                case 1:
                    recuperarCategorias(request, response);
                    break;
                case 2:
                    recuperarPrioridad(request, response);
                    break;
                case 3:
                    crearRegistros(request, response);
                    break;
                case 4:
                    //pintarRegistros(request, response);
                    break;
                case 5:
                    //editarRegistros(request, response);
                    break;
            }
        }
    }

    private void recuperarCategorias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            CategoriaFachada catFachada = new CategoriaFachada();
            List<Categoria> listCategoria = catFachada.getListObject();
            JSONArray array = new JSONArray();
            for (Categoria cat : listCategoria) {
                JSONObject obj = new JSONObject();
                obj.put("codigo", cat.getCodigo());
                obj.put("nombre", cat.getNombre());
                obj.put("codigopadre", cat.getCodigoPadre());
                obj.put("activo", cat.getActivo());
                array.add(obj);
            }
            out.print(array);
        }
    }

    private void recuperarPrioridad(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            PrioridadFachada prioFachada = new PrioridadFachada();
            List<Prioridad> listPrioridad = prioFachada.getListObject();
            JSONArray array = new JSONArray();
            for (Prioridad prio : listPrioridad) {
                JSONObject obj = new JSONObject();
                obj.put("codigo", prio.getCodigo());
                obj.put("nombre", prio.getNombre());
                obj.put("valor", prio.getValor());
                obj.put("activo", prio.getActivo());
                array.add(obj);
            }
            out.print(array);
        }
    }

    private void crearRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            Articulo art = new Articulo();
            art.setTitulo(request.getParameter("tituloClasificado"));
            art.setDescripcion(request.getParameter("cuerpoClasificado"));
            art.setCategoria(new Categoria(Integer.parseInt(request.getParameter("categoria"))));
            art.setPrecio(Integer.parseInt(request.getParameter("precioClasificado")));
            Usuario usr = (Usuario) request.getSession().getAttribute("user");
            art.setUsuario(usr);
            art.setPrioridad(new Prioridad(Integer.parseInt(request.getParameter("prioridad"))));
            art.setPrecio(Integer.parseInt(request.getParameter("precioClasificado")));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date parsed = format.parse(request.getParameter("finPublicacion"));
            java.sql.Date fechaFin = new java.sql.Date(parsed.getTime());
            //art.setFechaFinPublicacion(fechaFin);
            art.setEstado(new ArticuloEstado(1));
            //art.setTipoArticulo(new TipoArticulo(1));
            art.setCategoria(new Categoria(Integer.parseInt(request.getParameter("categoria"))));
            ArticuloFachada artFach = new ArticuloFachada();
            artFach.insertObject(art);
            System.out.println(art);
            
        } catch (ParseException ex) {
            Logger.getLogger(ClasificadoControlador.class.getName()).log(Level.SEVERE, null, ex);
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

}
