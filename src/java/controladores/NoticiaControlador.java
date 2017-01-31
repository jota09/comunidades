/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.ArticuloFachada;
import fachada.CategoriaFachada;
import fachada.TipoMultimediaFachada;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.json.simple.JSONArray;
import persistencia.entidades.Articulo;
import persistencia.entidades.ArticuloEstado;
import persistencia.entidades.Categoria;
import persistencia.entidades.TipoArticulo;
import persistencia.entidades.Usuario;
import org.json.simple.JSONObject;
import persistencia.entidades.Multimedia;
import persistencia.entidades.Prioridad;
import persistencia.entidades.TipoMultimedia;
import utilitarias.LecturaConfig;
import utilitarias.Utilitaria;
//Prueba de que ya sincronizo el proyecto con alejandro
//Prueba de que ya sincronizo el proyecto con manuel
//Verificacion....
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
            throws ServletException, IOException, FileUploadException {
        response.setContentType("text/html;charset=UTF-8");
        if(request.getParameter("op")!=null)
        {
            int opcion = Integer.parseInt(request.getParameter("op"));       
            switch (opcion) {
                case 1:
                    tablaRegistros(request, response);
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

    private void tablaRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            ArticuloFachada artFachada = new ArticuloFachada();
            TipoArticulo tipArt = new TipoArticulo();
            tipArt.setCodigo(Integer.parseInt(request.getParameter("tipo")));                                    
            Articulo articulo=new Articulo();
            articulo.setTipoArticulo(tipArt);
            articulo.setRango(request.getParameter("rango"));
            articulo.setUsuario((Usuario) request.getSession().getAttribute("user"));
            if(request.getParameter("cat")!=null)
            {
                if(!request.getParameter("cat").equals(""))
                    articulo.setCategoria(new Categoria(Integer.parseInt(request.getParameter("cat"))));
            }
            if(request.getParameter("buscar")!=null)
            {
                articulo.setBusqueda(request.getParameter("buscar"));
            }
            List<Articulo> listArticulo = artFachada.getListObject(articulo);
            JSONArray jsonArray=new JSONArray();
            for (Articulo art : listArticulo) {
                JSONObject jsonObj=new JSONObject();
                jsonObj.put("codigo", art.getCodigo());
                jsonObj.put("titulo",art.getTitulo());
                jsonObj.put("nombreUsuario",art.getUsuario().getNombres());
                jsonObj.put("apellidoUsuario",art.getUsuario().getApellidos());
                jsonObj.put("nombreCategoria",art.getCategoria().getNombre());
                jsonObj.put("nombreEstado",art.getCategoria().getNombre());
                jsonObj.put("fechaPublicacion",art.getFechaPublicacion());
                jsonArray.add(jsonObj);
            }
            out.print(jsonArray);
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
            tpArt.setCodigo(2);//Tipo articulo noticia es 2
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

    private void editarRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileUploadException {
        try (PrintWriter out = response.getWriter()) {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (isMultipart) {
                System.out.println("entro a lectura de archivos");
                subirArchivo(request,response);
            }
            int cod = Integer.parseInt(request.getParameter("cod"));
            Articulo art = new Articulo();
            art.setCodigo(cod);
            ArticuloFachada artFachada = new ArticuloFachada();
            art = (Articulo) artFachada.getObject(art);
            JSONObject obj = new JSONObject();
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
            out.print(obj);
        }
    }

    private void borrarRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            int cod = Integer.parseInt(request.getParameter("cod").trim());
            Articulo art = new Articulo();
            art.setCodigo(cod);
            TipoArticulo tpArt=new TipoArticulo();
            tpArt.setCodigo(2);//Tipo articulo noticia es 2
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
        
    private void subirArchivo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileUploadException
    {        
        try (PrintWriter out = response.getWriter()) {                    
            DiskFileItemFactory factory = new DiskFileItemFactory();
            RequestContext requestContext = new ServletRequestContext(request);
            ServletContext servletContext = this.getServletConfig().getServletContext();
            File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
            factory.setRepository(repository);
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest(requestContext);
            Iterator iterator = items.iterator();
            int cod = Integer.parseInt(request.getParameter("cod"));
            
            while(iterator.hasNext()) {
                FileItem fil = (FileItem) iterator.next();
                String extension = fil.getName();
                int startExtension = extension.indexOf(".") + 1;
                extension = extension.substring(startExtension, extension.length());                               
                String ruta=LecturaConfig.getValue("upload");
                File dir=new File(ruta);
                if(!dir.exists())
                    dir.mkdirs();//la diferenecia con mkdir es que con esta instrucción crea toda la ruta
                String nuevaRuta=dir+File.separator+fil.getName(); 
                Multimedia multimedia=new Multimedia();
                multimedia.setArticuloCodigo(cod);
                multimedia.setNombre(fil.getName());
                TipoMultimediaFachada tpMultFach=new TipoMultimediaFachada();
                List<TipoMultimedia> listTipMult=tpMultFach.getListObject("imagen");
                for(TipoMultimedia tpList:listTipMult)
                {
                    if(tpList.getExtension().equals(extension))
                    {
                        
                    }
                    else
                    {
                        System.out.print("FORMATO NO VÁLIDO");
                    }
                }
                /*ArchivoFachada  achFachada=new ArchivoFachada();
                achFachada.insertObject(arch);
                File files=new File(nuevaRuta);
                fil.write(files);//FileItem es el cre crea el archivo en la nueva ruta */               
                }            
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
        try {
            processRequest(request, response);
        } catch (FileUploadException ex) {
            Logger.getLogger(NoticiaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (FileUploadException ex) {
            Logger.getLogger(NoticiaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
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
