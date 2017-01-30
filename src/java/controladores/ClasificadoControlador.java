/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.ArticuloFachada;
import fachada.CategoriaFachada;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
        if(request.getParameter("opc")!=null)
        {
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
            System.out.println("Entro aqui");
            CategoriaFachada catFachada = new CategoriaFachada();
            List<Categoria> listCategoria = catFachada.getListObject();
            System.out.println(listCategoria);
            JSONArray array = new JSONArray();
            for ( Categoria cat : listCategoria ){
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
            System.out.println("Entro aqui");
            CategoriaFachada catFachada = new CategoriaFachada();
            List<Categoria> listCategoria = catFachada.getListObject();
            System.out.println(listCategoria);
            JSONArray array = new JSONArray();
            for ( Categoria cat : listCategoria ){
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
    
    private void crearRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            Articulo art = new Articulo();
            art.setTitulo(request.getParameter("tituloClasificado"));
            art.setDescripcion(request.getParameter("cuerpoClasificado"));
            art.setCategoria(new Categoria(Integer.parseInt(request.getParameter("categoria"))));
            
            art.setDescripcion(request.getParameter("precioClasificado"));
            Usuario usr = (Usuario) request.getSession().getAttribute("user");
            art.setUsuario(usr);
            //art.setUsuarioCodigo(usr.getCodigo());
            int cat;
            cat = Integer.parseInt(request.getParameter("categoria"));
            art.setFechaPublicacion(null);
            art.setPrecio(0);
            art.setFechaFinPublicacion(null);
            art.setPrioridad(null);
            ArticuloEstado artEstado = new ArticuloEstado();
            artEstado.setCodigo(1);
            art.setEstado(artEstado);
            ArticuloFachada artFach = new ArticuloFachada();
            TipoArticulo tpArt = new TipoArticulo();
            tpArt.setCodigo(1);
            art.setTipoArticulo(tpArt);
            Categoria categ = new Categoria();
            categ.setCodigo(cat);
            art.setCategoria(categ);
            /*if(codArt==null){
                artFach.insertObject(art);
            }else{
                art.setCodigo(Integer.parseInt(codArt));
                artFach.updateObject(art);
            }*/
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
