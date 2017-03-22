/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.ComunidadFachada;
import fachada.EstructuraFachada;
import fachada.FacturaFachada;
import fachada.GestionFachada;
import fachada.MovimientoFachada;
import fachada.PlantillaXComunidadFachada;
import fachada.ProcesoFachada;
import fachada.TempCargueFacturacionFachada;
import fachada.UsuarioFachada;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import persistencia.entidades.Comunidad;
import persistencia.entidades.Estructura;
import persistencia.entidades.EventoProceso;
import persistencia.entidades.Factura;
import persistencia.entidades.Movimiento;
import persistencia.entidades.PlantillaXComunidad;
import persistencia.entidades.Proceso;
import persistencia.entidades.TempCargueFacturacion;
import persistencia.entidades.Usuario;
import utilitarias.DatosJasper;
import utilitarias.EjecutaProcedimiento;
import utilitarias.LecturaConfig;
import utilitarias.Utilitaria;

/**
 *
 * @author manuel.alcala
 */
@WebServlet(name = "GestionFacturaControlador", urlPatterns = {"/GestionFacturaControlador"})
public class GestionFacturaControlador extends HttpServlet {

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
        try {
            int op = Integer.parseInt(request.getParameter("op"));
            switch (op) {
                case 1: {
                    getProcesos(request, response);
                    break;
                }
                case 2: {
                    generaMuestra(request, response);
                    break;
                }
                case 3: {
                    valoraMuestra(request, response);
                    break;
                }
                case 4: {
                    subirArchivo(request, response);
                    break;
                }
                case 5: {
                    iniciarProceso(request, response);
                    break;
                }
                case 6: {
                    poblarTablas(request, response);
                    break;
                }
                case 7: {
                    valorMuestraEnvio(request, response);
                }

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(GestionFacturaControlador.class.getName()).log(Level.SEVERE, null, ex);
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

    private void getProcesos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GestionFachada procesoFachada = new ProcesoFachada();
        HttpSession sesion = request.getSession();
        Usuario user = (Usuario) sesion.getAttribute("user");
        Comunidad comunidad = user.getPerfilCodigo().getComunidad();
        List<Proceso> procesos = procesoFachada.getListObject(comunidad);
        JSONArray array = new JSONArray();
        for (Proceso p : procesos) {
            JSONObject obj = new JSONObject();
            obj.put("codigo", p.getCodigo());
            obj.put("codigoEvento", p.getEventoProceso().getCodigo());
            obj.put("evento", p.getEventoProceso().getNombre());
            obj.put("usuario", p.getUsuarioResponsable().getUserName());
            obj.put("fechainicio", (p.getFechaInicio() != null) ? p.getFechaInicio().toString() : "");
            obj.put("fechafin", (p.getFechaFin() != null) ? p.getFechaFin().toString() : "");
            array.add(obj);
        }
        PrintWriter out = response.getWriter();
        out.print(array);
    }

    private void generaMuestra(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Proceso proceso = new Proceso(Integer.parseInt(request.getParameter("codigoProceso")));
        GestionFachada procesoFachada = new ProcesoFachada();
        GestionFachada facturaFachada = new FacturaFachada();
        GestionFachada movimientoFachada = new MovimientoFachada();
        GestionFachada comunidadFachada = new ComunidadFachada();
        GestionFachada estructuraFachada = new EstructuraFachada();
        Estructura estructura = new Estructura();
        GestionFachada usuarioFachada = new UsuarioFachada();
        procesoFachada.getObject(proceso);
        comunidadFachada.getObject(proceso.getComunidad());
        List<Factura> facturas = facturaFachada.getListObject(proceso);
        int tamano = facturas.size();
        int numAleatorio = (int) Math.floor(Math.random() * tamano);
        Factura factura = facturas.get(numAleatorio);
        usuarioFachada.getObject(factura.getUsuario());
        List<Movimiento> movimientos = movimientoFachada.getListObject(factura);
        Comunidad comunidad = proceso.getComunidad();
        Map<String, Object> parametros = new HashMap();
        parametros = new HashMap<>();
        List datos_Factura = new ArrayList();
        List datos_Cliente = new ArrayList();
        double total = 0;
        Date fechaVencimiento = null;
        for (Movimiento m : movimientos) {
            DatosJasper datos = new DatosJasper(m.getDetalle(), "$" + m.getValor());
            total += m.getValor();
            datos_Factura.add(datos);
            if (fechaVencimiento==null || fechaVencimiento.after(m.getFechaVencimiento())) {
                fechaVencimiento = m.getFechaVencimiento();
            }
        }
        datos_Factura.add(new DatosJasper("TOTAL", "$" + total));
        datos_Cliente.add(new DatosJasper("Nombres del cliente", factura.getUsuario().getNombres() + " " + factura.getUsuario().getApellidos()));
        datos_Cliente.add(new DatosJasper("Documento", String.valueOf(factura.getUsuario().getCodigoDocumento())));
        datos_Cliente.add(new DatosJasper("Correo", factura.getUsuario().getCorreo()));
        parametros.put("imagen_comunidad", LecturaConfig.getValue("rutaImg") + "logo/" + comunidad.getCodigo() + ".png");
        parametros.put("nombre_comunidad", comunidad.getNombre());
        parametros.put("nit_comunidad", comunidad.getNit());
        parametros.put("direccion_comunidad", comunidad.getDireccion());
        parametros.put("telefono_comunidad", comunidad.getTelefono());
        estructura.setReferencia("tituloFactura");
        estructuraFachada.getObject(estructura);
        parametros.put("nombre_factura", estructura.getValor());
        parametros.put("numero_factura", factura.getNumFactura());
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
        parametros.put("enlace_pse", estructura.getValor());
        String valor415 = agregarCero(comunidad.getIdBarCode(), 13);
        String valor8020 = String.valueOf(factura.getUsuario().getCodigoDocumento());
        valor8020 = agregarCero(valor8020, 12);
        String valor3900 = String.valueOf((int) total);
        valor3900 = agregarCero(valor3900, 10);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        parametros.put("codigo_barras", "415" + valor415+ "8020" + valor8020 + "3900" + valor3900 + "96" + format.format(fechaVencimiento));
        PrintWriter out = response.getWriter();
        out.print(Utilitaria.generaPDFB64(proceso.getPlantillaXComunidad().getPlantilla().getCodigo() + ".jasper", parametros));
    }

    private void valoraMuestra(HttpServletRequest request, HttpServletResponse response) {
        boolean valoracion = Boolean.parseBoolean(request.getParameter("valoracion"));
        int codProceso = Integer.parseInt(request.getParameter("codProceso"));
        HttpSession sesion = request.getSession();
        GestionFachada procesoFachada = new ProcesoFachada();
        Proceso proceso = new Proceso(codProceso);
        EventoProceso evento = new EventoProceso();
        proceso.setEventoProceso(evento);
        if (valoracion) {
            evento.setCodigo(3);
            sesion.setAttribute("message", Utilitaria.createAlert("Exito", "Se Aprobo la muestra del pdf", "success"));
        } else {
            evento.setCodigo(4);
            sesion.setAttribute("message", Utilitaria.createAlert("Exito", "Se rechazo la muestra del pdf", "warning"));
        }
        procesoFachada.updateObject(proceso);

    }

    private void subirArchivo(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        Proceso proceso = new Proceso(Integer.parseInt(request.getParameter("proceso")));
        GestionFachada tempFachada = new TempCargueFacturacionFachada();
        String carga = request.getParameter("carga");
        if (!carga.isEmpty()) {
            String columnas[] = carga.split("\t");
            TempCargueFacturacion temp = new TempCargueFacturacion();
            temp.setDocumento(Integer.parseInt(columnas[0]));
            temp.setDetalle(columnas[1]);
            temp.setProceso(proceso);
            temp.setValor(Double.parseDouble(columnas[2]));
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            temp.setFecha_vencimiento(format.parse(columnas[3]));
            temp.setNum_Factura(columnas[4].replace("\r", ""));
            tempFachada.insertObject(temp);

        }
    }

    private void iniciarProceso(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GestionFachada pxComuninidadFachada = new PlantillaXComunidadFachada();
        GestionFachada procesoFachada = new ProcesoFachada();
        Proceso proceso = new Proceso();
        PlantillaXComunidad pxComunidad = new PlantillaXComunidad();
        HttpSession sesion = request.getSession();
        Usuario user = (Usuario) sesion.getAttribute("user");
        pxComunidad.setComunidad(user.getPerfilCodigo().getComunidad());
        pxComuninidadFachada.getObject(pxComunidad);
        proceso.setUsuarioResponsable(user);
        proceso.setPlantillaXComunidad(pxComunidad);
        proceso.setComunidad(user.getPerfilCodigo().getComunidad());
        procesoFachada.insertObject(proceso);
        PrintWriter out = response.getWriter();
        out.print(proceso.getCodigo());
    }

    private void poblarTablas(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Proceso proceso = new Proceso(Integer.parseInt(request.getParameter("codProceso")));
        int tam = EjecutaProcedimiento.ejecutarProcedimientoFacturacion(proceso);
        HttpSession sesion = request.getSession();
        if (tam > 0) {
            sesion.setAttribute("message", Utilitaria.createAlert("Exito", "Se proceso el Archivo Correctamente", "success"));
        } else {
            sesion.setAttribute("message", Utilitaria.createAlert("Error", "Hubieron Fallos al Procesar el Archivo Correctamente", "danger"));
        }

    }

    private void valorMuestraEnvio(HttpServletRequest request, HttpServletResponse response) {

    }

    private String agregarCero(String valor, int tamano) {
        for (int i = valor.length(); i < tamano; i++) {
            valor = "0" + valor;
        }
        return valor;
    }

}
