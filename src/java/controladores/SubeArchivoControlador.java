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
import persistencia.entidades.Multimedia;
import persistencia.entidades.TipoMultimedia;

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
            String id = request.getParameter("id");
            short destacada = Short.parseShort(request.getParameter("destacada"));
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
            multimedia.setArticuloCodigo(Integer.parseInt(id));
            multimedia.setDestacada(destacada);
            multimediaFachada.insertObject(multimedia);
            String content = encoded[1];
            byte[] decoded = Base64.getDecoder().decode(content.getBytes(StandardCharsets.UTF_8));
            String path = "c:/files/" + multimedia.getCodigo() + "." + ext;
            generaArchivo(path, decoded);
            System.out.println("Decodificacion:"+request.getParameter("file"));
        }
    }

    
    private synchronized void generaArchivo(String path, byte[] content) throws FileNotFoundException, IOException {
        File file=new File(path);
        BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
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
