/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.GestionFachada;
import fachada.MultimediaFachada;
import fachada.TipoMultimediaFachada;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistencia.daos.MultimediaDAO;
import persistencia.entidades.Articulo;
import persistencia.entidades.Multimedia;
import persistencia.entidades.TipoMultimedia;
import utilitarias.LecturaConfig;

/**
 *
 * @author manuel.alcala
 */
@WebServlet(name = "SubeArchivoControlador", urlPatterns = {"/SubeArchivoControlador"})
public class SubeArchivoControlador extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            int opcion = Integer.parseInt(request.getParameter("op"));
            String codArticulo = request.getParameter("codArt");
            switch (opcion) {
                case 1:
                    crearMultimedia(request, response, codArticulo);
                    break;
                case 2:
                    borrarMultimedia(request, response, codArticulo);
                    break;
            }
        }
    }

    private void crearMultimedia(HttpServletRequest request, HttpServletResponse response, String id) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {            
            short destacada = Short.parseShort(request.getParameter("destacada"));
            System.out.println("imprimiendo subeArchivoFile:" + request.getParameter("file"));
            String encoded[] = request.getParameter("file").split(",");
            String ext = (encoded[0].split(";")[0]);
            ext = ext.split("/")[1];
            ext = ((ext.equals("vnd.openxmlformats-officedocument.wordprocessingml.document")) ? "docx" : ext);
            ext = ((ext.equals("plain")) ? "txt" : ext);
            GestionFachada tipoMultimediaFachada = new TipoMultimediaFachada();
            GestionFachada multimediaFachada = new MultimediaFachada();
            TipoMultimedia tipoMultimedia = new TipoMultimedia();
            tipoMultimedia.setExtension(ext);
            tipoMultimediaFachada.getObject(tipoMultimedia);
            Multimedia multimedia = new Multimedia();
            multimedia.setTipoMultimediaCodigo(tipoMultimedia.getCodigo());
            multimedia.setArticulocodigo(new Articulo(Integer.parseInt(id)));
            multimedia.setDestacada(destacada);
            multimediaFachada.insertObject(multimedia);
            String content = encoded[1];
            byte[] decoded = Base64.getDecoder().decode(content.getBytes(StandardCharsets.UTF_8));
            //String path = "c:/files/" + multimedia.getCodigo() + ".txt" ;
            String path = LecturaConfig.getValue("rutaUpload") + id + "\\";
            String file = multimedia.getCodigo() + "." + ext;
            generaArchivo(path, file, decoded);
            //generarArchivoBase64(path,request.getParameter("file"));
            //System.out.println("Decodificacion:"+request.getParameter("file"));
        }

    }

    private void borrarMultimedia(HttpServletRequest request, HttpServletResponse response, String codArticulo) {
        GestionFachada tipoMultimediaFachada = new TipoMultimediaFachada();
        GestionFachada multimediaFachada = new MultimediaFachada();
        Multimedia multimedia = new Multimedia();
        multimedia.setArticulocodigo(new Articulo(Integer.parseInt(codArticulo)));
        multimediaFachada.deleteObject(multimedia);
        String path = LecturaConfig.getValue("rutaUpload") + codArticulo + "\\";
        File file = new File(path);
        File[] files = file.listFiles();
        if(files.length>0)
        {
            for (File f : files) {
                f.delete();
            }
        }
    }

    private void generarArchivoBase64(String ruta, String base64) throws IOException {
        FileWriter fW = new FileWriter(ruta, true);
        BufferedWriter bW = new BufferedWriter(fW);
        bW.write(base64);
        bW.flush();
        fW.flush();
        bW.close();
        fW.close();
    }

    private synchronized void generaArchivo(String path, String archivo, byte[] content) throws FileNotFoundException, IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String nuevaRuta = path + archivo;
        File file2 = new File(nuevaRuta);
        BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file2));
        writer.write(content);
        writer.flush();
        writer.close();
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
