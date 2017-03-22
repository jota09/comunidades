/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.GestionFachada;
import fachada.MultimediaAutorizacionFachada;
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
import persistencia.entidades.Autorizacion;
import persistencia.entidades.MultimediaAutorizacion;
import persistencia.entidades.TipoMultimedia;
import utilitarias.LecturaConfig;
import persistencia.entidades.Error;
import persistencia.entidades.TipoError;
import utilitarias.Utilitaria;

/**
 *
 * @author manuel.alcala
 */
@WebServlet(name = "SubeArchivoControlador2", urlPatterns = {"/SubeArchivoControlador2"})
public class SubeArchivoControlador2 extends HttpServlet {

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
            int opcion = Integer.parseInt(request.getParameter("op"));
            String codArticulo = request.getParameter("codArt");
            System.out.println("Id del articulo: "+codArticulo);
            switch (opcion) {
                case 1:
                    crearMultimedia(request, response, codArticulo);
                    break;
                case 2:
                    borrarMultimedia(request, response);
                    break;
            }
        } catch (IOException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("processRequest");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        }
    }

    private void crearMultimedia(HttpServletRequest request, HttpServletResponse response, String id) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            short destacada = Short.parseShort(request.getParameter("destacada"));
            String encoded[] = request.getParameter("file").split(",");
            String ext = (encoded[0].split(";")[0]);
            ext = ext.split("/")[1];
            ext = ((ext.equals("vnd.openxmlformats-officedocument.wordprocessingml.document")) ? "docx" : ext);
            ext = ((ext.equals("plain")) ? "txt" : ext);
            GestionFachada tipoMultimediaFachada = new TipoMultimediaFachada();
            GestionFachada multimediaFachada = new MultimediaAutorizacionFachada();
            TipoMultimedia tipoMultimedia = new TipoMultimedia();
            tipoMultimedia.setExtension(ext);
            tipoMultimediaFachada.getObject(tipoMultimedia);
            MultimediaAutorizacion multimedia = new MultimediaAutorizacion();
            multimedia.setTipoMultimediaCodigo(tipoMultimedia.getCodigo());
            multimedia.setAutorizacioncodigo(new Autorizacion(Integer.parseInt(id)));
            multimedia.setDestacada(destacada);
            multimediaFachada.insertObject(multimedia);
            String content = encoded[1];
            byte[] decoded = Base64.getDecoder().decode(content.getBytes(StandardCharsets.UTF_8));
            String path = LecturaConfig.getValue("rutaUploadAutorizacion") + id + File.separator;
            String file = multimedia.getCodigo() + "." + ext;
            generaArchivo(path, file, decoded);

        }
    }

    private void borrarMultimedia(HttpServletRequest request, HttpServletResponse response) {

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
            file.setReadable(true, false);
            file.setExecutable(true, false);
            file.setWritable(true, false);
         
        }
        String nuevaRuta = path + archivo;
        File file2 = new File(nuevaRuta);
        BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file2));
        writer.write(content);
        writer.flush();
        writer.close();
        file2.setReadable(true, false);
        file2.setExecutable(true, false);
        file2.setWritable(true, false);
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
