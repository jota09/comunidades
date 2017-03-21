/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.ComunidadFachada;
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
import persistencia.entidades.EventoProceso;
import persistencia.entidades.Factura;
import persistencia.entidades.Movimiento;
import persistencia.entidades.PlantillaXComunidad;
import persistencia.entidades.Proceso;
import persistencia.entidades.TempCargueFacturacion;
import persistencia.entidades.Usuario;
import utilitarias.DatosJasper;
import utilitarias.EjecutaProcedimiento;
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
        Map<String, Object> propiedades = new HashMap();
        propiedades.put("nombre_comunidad", comunidad.getNombre());
        propiedades.put("nit_comunidad", "11");
        propiedades.put("telefono_comunidad", comunidad.getTelefono());
        propiedades.put("direccion_comunidad", comunidad.getDireccion());
        propiedades.put("nombre_factura", "Factura de Venta");
        propiedades.put("numero_factura", String.valueOf(factura.getCodigo()));
        propiedades.put("imagen_comunidad", "C:\\configComunidades\\2.png");
        List datos_Factura = new ArrayList();
        List datos_Cliente = new ArrayList();
        double total = 0;
        for (Movimiento m : movimientos) {
            DatosJasper datos = new DatosJasper(m.getDetalle(), "$" + m.getValor());
            total += m.getValor();
            datos_Factura.add(datos);
        }
        datos_Factura.add(new DatosJasper("TOTAL", "$" + total));
        datos_Cliente.add(new DatosJasper("Nombres del cliente", factura.getUsuario().getNombres() + " " + factura.getUsuario().getApellidos()));
        datos_Cliente.add(new DatosJasper("Documento", String.valueOf(factura.getUsuario().getCodigoDocumento())));
        datos_Cliente.add(new DatosJasper("Correo", factura.getUsuario().getCorreo()));

        propiedades.put("datos_factura", datos_Factura);
        propiedades.put("datos_cliente", datos_Cliente);
        PrintWriter out = response.getWriter();
        out.print(Utilitaria.generaPDFB64(proceso.getPlantillaXComunidad().getPlantilla().getCodigo() + ".jasper", propiedades));
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
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
            temp.setFecha_vencimiento(format.parse(columnas[3]));
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

}
