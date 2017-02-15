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
import fachada.UsuarioFachada;
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
import utilitarias.Utilitaria;

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
                case 9:
                    recuperarInicioClasificado(request, response);
                    break;
            }
        }
    }

    private void recuperarCategorias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            CategoriaFachada catFachada=new CategoriaFachada();                    
            out.print(Utilitaria.construirCategorias(catFachada.getListObject()));
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
            Articulo art = new Articulo();
            String ref = "cantidadUltimosClasificados";
            Estructura estruc = new Estructura(ref);
            estruc = (Estructura) estrucFachada.getObject(estruc);
            art.setRango("0,"+estruc.getValor());
            String ref2 = "tipoClasificado";
            Estructura estruc2 = new Estructura(ref2);
            estruc2 = (Estructura) estrucFachada.getObject(estruc2);
            art.setTipoArticulo(new TipoArticulo(Integer.parseInt(estruc2.getValor())));
            art.setEstado(new ArticuloEstado(2));
            ArticuloFachada artFachada = new ArticuloFachada();
            System.out.println(art);
            List<Articulo> listArticulo = artFachada.getListObject(art);
            JSONArray array = new JSONArray();
            for (Articulo art2 : listArticulo) {
                JSONObject obj = new JSONObject();
                System.out.println(art.getCodigo());
                obj.put("codigo", art2.getCodigo());
                obj.put("nombre", art2.getTitulo());
                array.add(obj);
            }
            out.print(array);
        }
    }

    private void recuperarInicioClasificado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            EstructuraFachada estrucFachada = new EstructuraFachada();
            String ref = "clasificadoMostrarInicio";
            Estructura estruc = new Estructura(ref);
            estruc = (Estructura) estrucFachada.getObject(estruc);
            int rangoSup = Integer.parseInt(request.getParameter("limIni"))+Integer.parseInt(estruc.getValor());
            ref = "tipoClasificado";
            estruc.setReferencia(ref);
            estruc = (Estructura) estrucFachada.getObject(estruc);
            Articulo art = new Articulo();
            art.setTipoArticulo(new TipoArticulo(Integer.parseInt(estruc.getValor())));
            art.setUsuario(new Usuario(0));
            art.setRango(request.getParameter("limIni")+","+rangoSup);
            art.setBusqueda(request.getParameter("busqueda"));
            ArticuloFachada artFachada = new ArticuloFachada();
            List<Articulo> listArticulo = artFachada.getListByPagination(art);
            JSONArray array = new JSONArray();
            for (Articulo art2 : listArticulo) {
                JSONObject obj = new JSONObject();
                obj.put("codigo", art2.getCodigo());
                obj.put("titulo", art2.getTitulo());
                obj.put("precio", Utilitaria.conversionNatural(art2.getPrecio()));
                array.add(obj);
            }
            out.print(array);
        }
    }

    private void recuperarClasificado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            Articulo art = new Articulo(Integer.parseInt(request.getParameter("id")));
            ArticuloFachada artFachada = new ArticuloFachada();
            art = (Articulo) artFachada.getObject(art);
            Usuario user = new Usuario(art.getUsuario().getCodigo());
            UsuarioFachada userFachada = new UsuarioFachada();
            user = (Usuario) userFachada.getObject(user);
            art.setUsuario(user);
            JSONObject obj = new JSONObject();
            obj.put("codigo", art.getCodigo());
            obj.put("nombreUsuario", art.getUsuario().getUserName());
            if (art.getUsuario().getCelular() != null) {
                obj.put("telefono", art.getUsuario().getCelular());
            } else {
                obj.put("telefono", art.getUsuario().getTelefono());
            }
            obj.put("titulo", art.getTitulo());
            obj.put("descripcion", art.getDescripcion());
            obj.put("fechaPublicacion", Utilitaria.convertirFecha(art.getFechaPublicacion()));
            obj.put("descripcion", art.getDescripcion());
            obj.put("precio", Utilitaria.conversionNatural(art.getPrecio()));
            System.out.println(obj);
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
