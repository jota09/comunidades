/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.ComunidadFachada;
import fachada.EstructuraFachada;
import fachada.GestionFachada;
import fachada.PlantillaPDFFachada;
import fachada.PlantillaXComunidadFachada;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import persistencia.entidades.Comunidad;
import persistencia.entidades.Estructura;
import persistencia.entidades.PlantillaPDF;
import persistencia.entidades.PlantillaXComunidad;
import persistencia.entidades.Usuario;
import utilitarias.DatosJasper;
import utilitarias.LecturaConfig;
import utilitarias.Utilitaria;

/**
 *
 * @author manuel.alcala
 */
@WebServlet(name = "AdminPlantillasPDFControlador", urlPatterns = {"/AdminPlantillasPDFControlador"})
public class AdminPlantillasPDFControlador extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        response.setContentType("text/html;charset=UTF-8");
        int op = Integer.parseInt(request.getParameter("op"));
        try {
            switch (op) {

                case 1: {
                    cargarPlantillaXComunidad(request, response);
                    break;
                }
                case 2: {
                    cargarComunidades(request, response);
                    break;
                }
                case 3: {
                    descargarPlantilla(request, response);
                    break;
                }
                case 4: {
                    visualizarPlantilla(request, response);
                    break;
                }
                case 5: {
                    activaPlantilla(request, response);
                    break;
                }
                case 6: {
                    subirPlantilla(request, response);
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(AdminPlantillasPDFControlador.class.getName()).log(Level.SEVERE, null, ex);
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

    private void cargarPlantillaXComunidad(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Comunidad comunidad = new Comunidad(Integer.parseInt(request.getParameter("codigoComunidad")));
        GestionFachada pxcFachada = new PlantillaXComunidadFachada();
        List<PlantillaXComunidad> plantillas = pxcFachada.getListByCondition(comunidad);
        JSONArray array = new JSONArray();
        for (PlantillaXComunidad pxc : plantillas) {
            JSONObject obj = new JSONObject();
            obj.put("codigo", pxc.getCodigo());
            obj.put("nombre", pxc.getPlantilla().getNombre());
            obj.put("activo", pxc.getActivo());
            obj.put("codigoPlantilla", pxc.getPlantilla().getCodigo());
            array.add(obj);
        }
        PrintWriter out = response.getWriter();
        out.print(array);
    }

    private void cargarComunidades(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GestionFachada comunidadFachada = new ComunidadFachada();
        List<Comunidad> comunidades = comunidadFachada.getListObject();
        JSONArray array = new JSONArray();
        for (Comunidad comunidad : comunidades) {
            JSONObject obj = new JSONObject();
            obj.put("codigo", comunidad.getCodigo());
            obj.put("nombre", comunidad.getNombre());
            array.add(obj);
        }
        PrintWriter out = response.getWriter();
        out.print(array);
    }

    private void descargarPlantilla(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String codigo = request.getParameter("codigoPlantilla");
        File jasper = new File(LecturaConfig.getValue("rutaPlantillaPDF") + codigo + ".jasper");
        FileInputStream in = new FileInputStream(jasper);
        response.setContentType("application/x-download");
        Runtime run = Runtime.getRuntime();
        response.addHeader("Content-disposition", "attachment; filename=plantilla" + codigo + ".jasper");
        try (ServletOutputStream stream = response.getOutputStream()) {
            IOUtils.copy(in, stream);
            stream.flush();
        }
        in.close();
    }

    private void visualizarPlantilla(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession sesion = request.getSession();
        Usuario user = (Usuario) sesion.getAttribute("user");
         
        String codigoPlantilla = request.getParameter("codigoPlantilla");
        Comunidad comunidad = new Comunidad(Integer.parseInt(request.getParameter("comunidadCodigo")));
        GestionFachada comunidadFachada = new ComunidadFachada();
        GestionFachada estructuraFachada = new EstructuraFachada();
        Estructura estructura = new Estructura();
        comunidadFachada.getObject(comunidad);
        Map<String, Object> parametros = new HashMap();
        parametros = new HashMap<>();
        List datos_Factura = new ArrayList();
        List datos_Cliente = new ArrayList();
        double total = 0;
        Date fechaVencimiento = new Date();

        for (int i = 0; i < 5; i++) {
            DatosJasper datos = new DatosJasper("Ejemplo " + i, "$" + 100 * i);
            total += 100 * i;
            datos_Factura.add(datos);
        }
        datos_Factura.add(new DatosJasper("TOTAL", "$" + total));
        datos_Cliente.add(new DatosJasper("Nombres del cliente", user.getNombres() + " " + user.getApellidos()));
        datos_Cliente.add(new DatosJasper("Documento", String.valueOf(user.getCodigoDocumento())));
        datos_Cliente.add(new DatosJasper("Correo", user.getCorreo()));
        parametros.put("imagen_comunidad", LecturaConfig.getValue("rutaImg") + "logo/" + comunidad.getCodigo() + ".png");
        parametros.put("nombre_comunidad", comunidad.getNombre());
        parametros.put("nit_comunidad", comunidad.getNit());
        parametros.put("direccion_comunidad", comunidad.getDireccion());
        parametros.put("telefono_comunidad", comunidad.getTelefono());
        estructura.setReferencia("tituloFactura");
        estructuraFachada.getObject(estructura);
        parametros.put("nombre_factura", estructura.getValor());
        parametros.put("numero_factura", "0000001");
        parametros.put("datos_cliente", datos_Cliente);
        parametros.put("datos_factura", datos_Factura);
        estructura = new Estructura();
        estructura.setReferencia("resolucionDian");
        estructuraFachada.getObject(estructura);
        parametros.put("datos_dian", estructura.getValor());
        estructura = new Estructura();
        estructura.setReferencia("datosLegales");
        estructuraFachada.getObject(estructura);
        parametros.put("datos_legal", estructura.getValor());
        estructura = new Estructura();
        estructura.setReferencia("imgPSE");
        estructuraFachada.getObject(estructura);
        parametros.put("imagen_pse", estructura.getValor());
        estructura = new Estructura();
        estructura.setReferencia("datosPSE");
        estructuraFachada.getObject(estructura);
        parametros.put("datos_pse", estructura.getValor());
        estructura = new Estructura();
        estructura.setReferencia("enlacePSE");
        estructuraFachada.getObject(estructura);
//        System.out.println("Ruta plantilla:" + rutaPlantilla);
        parametros.put("enlace_pse", estructura.getValor());
        String valor415 = agregarCero(comunidad.getIdBarCode(), 13);
        String valor8020 = String.valueOf(user.getCodigoDocumento());
        valor8020 = agregarCero(valor8020, 12);
        String valor3900 = String.valueOf((int) total);
        valor3900 = agregarCero(valor3900, 10);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        parametros.put("codigo_barras", "415" + valor415 + "8020" + valor8020 + "3900" + valor3900 + "96" + format.format(fechaVencimiento));
        PrintWriter out = response.getWriter();
        out.print(Utilitaria.generaPDFB64(codigoPlantilla + ".jasper", parametros));
        
    }

    private String agregarCero(String valor, int tamano) {
        for (int i = valor.length(); i < tamano; i++) {
            valor = "0" + valor;
        }
        return valor;
    }

    private void activaPlantilla(HttpServletRequest request, HttpServletResponse response) {
        PlantillaXComunidad pxc = new PlantillaXComunidad(Integer.parseInt(request.getParameter("codigo")));
        Comunidad comunidad = new Comunidad(Integer.parseInt(request.getParameter("comunidadCodigo")));
        GestionFachada pxcFachada = new PlantillaXComunidadFachada();
        HttpSession session = request.getSession();
        pxc.setComunidad(comunidad);
        pxcFachada.updateObject(pxc);
        session.setAttribute("message", Utilitaria.createAlert("Exito", "Se activo la plantilla", "success"));

    }

    private void subirPlantilla(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String plantillaB64 = request.getParameter("plantillaB64").replace("data:;base64,", "");
        String rutaPlantilla = LecturaConfig.getValue("rutaPlantillaPDF");
        HttpSession session = request.getSession();
        Comunidad comunidad = new Comunidad(Integer.parseInt(request.getParameter("comunidadCodigo")));
        PlantillaXComunidad pxc = new PlantillaXComunidad();
        GestionFachada pxcFachada = new PlantillaXComunidadFachada();
        GestionFachada plantillaPDFFachada = new PlantillaPDFFachada();
        PlantillaPDF plantilla = new PlantillaPDF();
        plantilla.setNombre(request.getParameter("nombre"));
        plantillaPDFFachada.insertObject(plantilla);
        pxc.setPlantilla(plantilla);
        pxc.setComunidad(comunidad);
        pxcFachada.insertObject(pxc);
        pxcFachada.updateObject(pxc);
        byte plantillaByte[] = DatatypeConverter.parseBase64Binary(plantillaB64);
        File file = new File(rutaPlantilla + plantilla.getCodigo() + ".jasper");
        System.out.println("Ruta plantilla:" + rutaPlantilla);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(plantillaByte);
        fos.flush();
        fos.close();
        session.setAttribute("message", Utilitaria.createAlert("Exito", "Se Agrego y activo la nueva plantilla", "success"));
    }

}
