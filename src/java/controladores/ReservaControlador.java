/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.CondicionPaginacionFachada;
import fachada.GestionFachada;
import fachada.ReservaFachada;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import persistencia.entidades.CondicionPaginacion;
import persistencia.entidades.Reserva;
import persistencia.entidades.Usuario;
import utilitarias.CondicionPaginado;

/**
 *
 * @author daniel.franco
 */
@WebServlet(name = "ReservaControlador", urlPatterns = {"/ReservaControlador"})
public class ReservaControlador extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int op = Integer.parseInt(request.getParameter("op"));
        switch (op) {
            case 1: {
                recuperarReservas(request, response);
                break;
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void recuperarReservas(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rango = request.getParameter("rango");
        HttpSession session = request.getSession();
        String condicionesPag = request.getParameter("condicionesPag");
        String busqueda = request.getParameter("busqueda");
        GestionFachada condicionFachada = new CondicionPaginacionFachada();
        CondicionPaginacion condicionPaginacion = new CondicionPaginacion(Integer.parseInt(condicionesPag));
        CondicionPaginado condicion = new CondicionPaginado();
        condicionFachada.getObject(condicionPaginacion);
        if (condicionPaginacion.getActivaUsuario() == 1) {
            condicion.setUser((Usuario) session.getAttribute("user"));
        }
        if (condicionPaginacion.getActivaComunidad() == 1) {
            condicion.setComunidad(((Usuario) session.getAttribute("user")).getPerfilCodigo().getComunidad());
        }
        condicion.setCondicion(condicionPaginacion.getCondicion().replace("<?>", busqueda) + " limit " + rango);
        GestionFachada reservaFachada = new ReservaFachada();
        List<Reserva> reservas = reservaFachada.getListByPagination(condicion);
        JSONArray arrayReservas = new JSONArray();
        for (Reserva reserva : reservas) {
            JSONObject object = new JSONObject();
            object.put("codigoReserva", reserva.getCodigo());
            object.put("fecha_Inicio", reserva.getFechaInicio().toString());
            object.put("fecha_Fin", reserva.getFechaFin().toString());
            object.put("costo", reserva.getCosto());
            object.put("descripcion", reserva.getDescripcion());
            object.put("fecha_Creacion", reserva.getCreacion().toString());
            object.put("fecha_Actual", reserva.getActualizacion().toString());
            object.put("zonaCodigo", reserva.getZonaCodigo().getCodigo());
            object.put("zonaNombre", reserva.getZonaCodigo().getNombre());
            object.put("userCodigo", reserva.getUsuarioCodigo().getCodigo());
            object.put("userNombres", reserva.getUsuarioCodigo().getNombres() + " " + reserva.getUsuarioCodigo().getApellidos());
            object.put("userName", reserva.getUsuarioCodigo().getUserName());
            object.put("codigoEstado", reserva.getEstadoCodigo().getCodigo());
            object.put("estadoNombre", reserva.getEstadoCodigo().getNombre());
            arrayReservas.add(object);
        }
        PrintWriter out = response.getWriter();
        
        out.print(arrayReservas);
    }
}
