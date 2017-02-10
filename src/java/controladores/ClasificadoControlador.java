/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;
import fachada.ArticuloFachada;
import fachada.CategoriaFachada;
import fachada.EstructuraFachada;
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
import persistencia.entidades.Estructura;
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
       // System.out.println(request);
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
                    recuperarMostrar(request, response);
                    break;
                case 5:
                    recuperarRangoPrecio(request, response);
                    break;
                case 6:
                    recuperarOrdenarPor(request, response);
                    break;
                case 7:
                    recuperarUltimosClasificados(request, response);
                    break;
                case 8:
                    recuperarClasificado(request, response);
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

    private void recuperarMostrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            EstructuraFachada estrucFachada = new EstructuraFachada();
            String ref = "rangoPaginas";
            Estructura estruc = new Estructura(ref);
            estruc = (Estructura) estrucFachada.getObject(estruc);
            // System.out.println(estruc);
            String[] mostrar = estruc.getValor().split(";");
            JSONArray array = new JSONArray();
            for (int i = 0; i < mostrar.length; i++) {
                JSONObject obj = new JSONObject();
                obj.put("cantidad", mostrar[i]);
                array.add(obj);
            }
            out.print(array);
        }
    }

    private void recuperarRangoPrecio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            EstructuraFachada estrucFachada = new EstructuraFachada();
            String ref = "rangoPrecio";
            Estructura estruc = new Estructura(ref);
            estruc = (Estructura) estrucFachada.getObject(estruc);
            // System.out.println(estruc);
            String[] rango = estruc.getValor().split(";");
            JSONArray array = new JSONArray();
            for (int i = 1; i < rango.length; i++) {
                JSONObject obj = new JSONObject();
                obj.put("rango", rango[i - 1] + " $ - " + rango[i] + " $");
                array.add(obj);
            }
            out.print(array);
        }
    }

    private void recuperarOrdenarPor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            EstructuraFachada estrucFachada = new EstructuraFachada();
            String ref = "ordenarPor";
            Estructura estruc = new Estructura(ref);
            estruc = (Estructura) estrucFachada.getObject(estruc);
            String[] rango = estruc.getValor().split(";");
            String ref2 = "clasificadoOrdenar";
            Estructura estruc2 = new Estructura(ref2);
            estruc2 = (Estructura) estrucFachada.getObject(estruc2);
            String[] rango2 = estruc2.getValor().split(";");
            JSONArray array = new JSONArray();
            for (int i = 0; i < rango2.length; i++) {
                String[] ordenar = rango2[i].split(",");
                System.out.println(rango2[i]);
                for (int j = 0; j < rango.length; j++) {
                    JSONObject obj = new JSONObject();
                    if (rango[j].equals("ASC")) {
                        obj.put("campo", ordenar[0]);
                        obj.put("nombre", ordenar[1] + " más reciente");
                        obj.put("orden", rango[j]);
                    }
                    if (rango[j].equals("DESC")) {
                        obj.put("campo", ordenar[0]);
                        obj.put("nombre", ordenar[1] + " más antiguo");
                        obj.put("orden", rango[j]);
                    }
                    array.add(obj);
                }
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
            Usuario usr = (Usuario) request.getSession().getAttribute("user");
            art.setUsuario(usr);
            art.setFechaPublicacion(null);
            art.setPrioridad(new Prioridad(Integer.parseInt(request.getParameter("prioridad"))));
            art.setPrecio(Integer.parseInt(request.getParameter("precioClasificado")));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date parsed = format.parse(request.getParameter("finPublicacion"));
            java.sql.Date sql = new java.sql.Date(parsed.getTime());
            System.out.println(sql);
            art.setFechaFinPublicacion(sql);
            art.setEstado(new ArticuloEstado(1));
            art.setTipoArticulo(new TipoArticulo(1));
            art.setCategoria(new Categoria(Integer.parseInt(request.getParameter("categoria"))));
            System.out.println(art);
            ArticuloFachada artFachada = new ArticuloFachada();
            artFachada.insertObject(art);
            out.print(1);
        } catch (ParseException ex) {
            Logger.getLogger(ClasificadoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void recuperarUltimosClasificados(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            EstructuraFachada estrucFachada = new EstructuraFachada();
            String ref = "cantidadUltimosClasificados";
            Estructura estruc = new Estructura(ref);
            estruc = (Estructura) estrucFachada.getObject(estruc);
            ArticuloFachada artFachada = new ArticuloFachada();
            List<Articulo> listArticulo = artFachada.getListObject(estruc);
            JSONArray array = new JSONArray();
            for (Articulo art : listArticulo) {
                JSONObject obj = new JSONObject();
                obj.put("codigo", art.getCodigo());
                obj.put("nombre", art.getTitulo());
                array.add(obj);
            }
            out.print(array);
        }
    }
    
    private void recuperarClasificado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            System.out.println("Entro a la opcion 8");
            Articulo art = new Articulo(Integer.parseInt(request.getParameter("id")));
            ArticuloFachada artFachada = new ArticuloFachada();
            art = (Articulo) artFachada.getObject(art);
            System.out.println(art);
            JSONObject obj = new JSONObject();
            obj.put("codigo", art.getCodigo());
            obj.put("titulo", art.getTitulo());
            obj.put("activo", art.getActivo());            
            out.print(obj);
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
