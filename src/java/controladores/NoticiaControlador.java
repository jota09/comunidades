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
import persistencia.entidades.Articulo;
import persistencia.entidades.ArticuloEstado;
import persistencia.entidades.Categoria;
import persistencia.entidades.TipoArticulo;
import persistencia.entidades.Usuario;
import org.json.simple.JSONObject;
import persistencia.entidades.Prioridad;
import utilitarias.Utilitaria;

/**
 *
 * @author ferney.medina
 */
@WebServlet(name = "Noticia", urlPatterns = {"/Noticia"})
public class NoticiaControlador extends HttpServlet {

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
        if(request.getParameter("op")!=null)
        {
            int opcion = Integer.parseInt(request.getParameter("op"));       
            switch (opcion) {
                case 1:
                    pintarRegistros(request, response);
                    break;
                case 2:
                    crearRegistros(request, response);
                    break;
                case 3:                    
                    editarRegistros(request, response);
                    break;
                case 4:
                    borrarRegistros(request, response);
                    break;
                case 5:
                    aprobarRegistros(request, response);
                    break;
                case 6:
                    cargarCategorias(request, response);
                    break;
            }
        }
        else
        {
            System.out.println("La opcion es nula por favor verificar");
        }
    }

    private void pintarRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            ArticuloFachada artFachada = new ArticuloFachada();
            TipoArticulo tipArt = new TipoArticulo();
            tipArt.setCodigo(Integer.parseInt(request.getParameter("tipo")));            
            Articulo articulo=new Articulo();
            articulo.setTipoArticulo(tipArt);
            articulo.setRango(request.getParameter("rango"));
            articulo.setUsuario((Usuario) request.getSession().getAttribute("user"));
            List<Articulo> listArticulo = artFachada.getListObject(articulo);
            for (Articulo art : listArticulo) {
                out.print("<tr>");
                out.print("<td><input type=\"checkbox\" name=\"seleccion\" value=\"" + art.getCodigo() + " \" /></td>");
                out.print("<td id='tituloArt'>" + art.getTitulo() + "</td>");
                out.print("<td>" + art.getUsuario().getNombres() + " " + art.getUsuario().getApellidos() + "</td>");
                out.print("<td>" + art.getCategoria().getNombre() + "</td>");
                out.print("<td>" + art.getEstado().getNombre()+ "</td>");                
                out.print("<td>" + (art.getFechaPublicacion() == null ? "" : art.getFechaPublicacion()) + "</td>");
                out.print("<td>");
                out.print("<button class=\"btn btn-success\" onclick=\"aprobarArt('Noticia','5','" + art.getCodigo() + "') \"><span class=\"glyphicon glyphicon-ok\" title=\"aprobar noticia\"></span></button>");
                out.print("<button class=\"btn btn-danger\" onclick=\"rechazarArt('Noticia','6','" + art.getCodigo() + "') \"><span class=\"glyphicon glyphicon-minus-sign\" title=\"rechazar noticia\"></span></button>");
                out.print("<button class=\"btn btn-info\" onclick=\"editarArt('Noticia','3','" + art.getCodigo() + "') \"><span class=\"glyphicon glyphicon-pencil\" title=\"editar noticia\"></span></button>");
                out.print("<button class=\"btn btn-danger\"  onclick=\"borrarNoticia("+art.getCodigo()+") \"><span class=\"glyphicon glyphicon-remove\" title=\"borrar noticia\"></span></button>");
                out.print("<button class=\"btn btn-primary\" ><span class=\"glyphicon glyphicon-eye-open\" title=\"visualizar noticia\"></span></button>");
                out.print("</td>");
                out.print("</tr>");
            }
        }
    }

    private void crearRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            String codArt = request.getParameter("codArt");
            Articulo art = new Articulo();
            art.setTitulo(request.getParameter("titulo"));
            art.setDescripcion(request.getParameter("descripcion"));
            Usuario usr = (Usuario) request.getSession().getAttribute("user");
            art.setUsuario(usr);
            int cat;
            cat = Integer.parseInt(request.getParameter("categoria"));                                               
            ArticuloEstado artEstado = new ArticuloEstado();
            artEstado.setCodigo(1);
            art.setEstado(artEstado);
            art.setPrioridad(new Prioridad(1));//La noticia no tiene prioridad pero sin embargo se le envia el 1(baja)
            ArticuloFachada artFach = new ArticuloFachada();
            TipoArticulo tpArt = new TipoArticulo();
            tpArt.setCodigo(1);
            art.setTipoArticulo(tpArt);
            Categoria categ = new Categoria();
            categ.setCodigo(cat);
            art.setCategoria(categ);
            //System.out.println("este es codArt: "+codArt);
            if(codArt.equals("")){
                artFach.insertObject(art);
            }else{
                art.setCodigo(Integer.parseInt(codArt));
                artFach.updateObject(art);
            }
        }
    }

    private void editarRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            int cod = Integer.parseInt(request.getParameter("cod"));
            Articulo art = new Articulo();
            art.setCodigo(cod);
            ArticuloFachada artFachada = new ArticuloFachada();
            art = (Articulo) artFachada.getObject(art);
            //System.out.println("codigo:"+cod);
            /*Categoria cat=new Categoria();
            cat.setCodigo(25);
            cat.setNombre("Prueba JSON");*/
            JSONObject obj = new JSONObject();
            //obj.put("nombres", cat.getNombre());
            obj.put("codigo", art.getCodigo());
            obj.put("usuario_codigo", art.getUsuario().getCodigo());
            obj.put("titulo", art.getTitulo());
            obj.put("descripcion", art.getDescripcion());
            obj.put("fecha_publicacion", art.getFechaPublicacion());
            obj.put("fecha_fin_publicacion", art.getFechaFinPublicacion());
            obj.put("actualizacion", art.getActualizacion());
            obj.put("estados_codigo", art.getEstado().getCodigo());
            obj.put("tipo_articulo_codigo", art.getTipoArticulo().getCodigo());
            obj.put("categoria_codigo", art.getCategoria().getCodigo());
            obj.put("activo", art.getActivo());
            System.out.println("JSON: " + obj);
            out.print(obj);
        }
    }

    private void borrarRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            int cod = Integer.parseInt(request.getParameter("cod").trim());
            Articulo art = new Articulo();
            art.setCodigo(cod);
            TipoArticulo tpArt=new TipoArticulo();
            tpArt.setCodigo(1);
            art.setTipoArticulo(tpArt);
            ArticuloFachada artFachada = new ArticuloFachada();
            artFachada.deleteObject(art);            
        }
    }
    
    private void aprobarRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
                      
        }
    }
    
    private void cargarCategorias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            CategoriaFachada catFachada=new CategoriaFachada();
            out.print(Utilitaria.construirCategorias(catFachada.getListObject()));          
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
