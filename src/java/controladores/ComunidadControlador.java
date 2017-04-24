/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.CiudadFachada;
import fachada.ComunidadFachada;
import fachada.CondicionPaginacionFachada;
import fachada.DepartamentoFachada;
import fachada.GestionFachada;
import fachada.PaisFachada;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import persistencia.entidades.Ciudad;
import persistencia.entidades.Comunidad;
import persistencia.entidades.CondicionPaginacion;
import persistencia.entidades.Departamento;
import persistencia.entidades.Pais;
import utilitarias.CondicionPaginado;

/**
 *
 * @author daniel.franco
 */
@WebServlet(name = "ComunidadControlador", urlPatterns = {"/ComunidadControlador"})
public class ComunidadControlador extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int op = Integer.parseInt(request.getParameter("op"));
        try {
            switch (op) {
                case 1:
                    this.getRecursoVistaConPaginacion(request, response);
                    break;
                case 10:
                    this.getPaises(request, response);
                    break;
                case 11:
                    this.getDepartamentos(request, response);
                    break;
                case 12:
                    this.getCiudades(request, response);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getRecursoVistaConPaginacion(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String rango = request.getParameter("rango");
        String condicionesPag = request.getParameter("condicionesPag");
        String busqueda = request.getParameter("busqueda");
        GestionFachada condicionFachada = new CondicionPaginacionFachada();
        CondicionPaginacion condicionPaginacion = new CondicionPaginacion(Integer.parseInt(condicionesPag));
        condicionFachada.getObject(condicionPaginacion);
        CondicionPaginado condicion = new CondicionPaginado();
        condicion.setCondicion(condicionPaginacion.getCondicion().replace("<?>", busqueda) + " limit " + rango);
        GestionFachada comunidadFachada = new ComunidadFachada();
        try (PrintWriter out = response.getWriter()) {
            JSONArray arrayComunidades = new JSONArray();
            List<Comunidad> comunidades = comunidadFachada.getListByPagination(condicion);
            for (Comunidad comunidad : comunidades) {
                JSONObject obj = new JSONObject();
                obj.put("codigo", comunidad.getCodigo());
                obj.put("nit", comunidad.getNit());
                obj.put("nombre", comunidad.getNombre());
                obj.put("direccion", comunidad.getDireccion());
                obj.put("telefono", comunidad.getTelefono());
                obj.put("codigoBarras", comunidad.getIdBarCode());
                obj.put("miembros", comunidad.getMiembros());
                arrayComunidades.add(obj);
            }
            out.print(arrayComunidades);
        }
    }

    public void getPaises(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try (PrintWriter out = response.getWriter()) {
            PaisFachada paisFachada = new PaisFachada();
            ArrayList<Pais> paises = (ArrayList<Pais>) paisFachada.getListObject();
            JSONArray arregloPaises = new JSONArray();
            for (Pais pais : paises) {
                JSONObject obj = new JSONObject();
                obj.put("codigo", pais.getCodigo());
                obj.put("nombre", pais.getNombre());
                arregloPaises.add(obj);
            }
            out.print(arregloPaises);
        }
    }

    public void getDepartamentos(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try (PrintWriter out = response.getWriter()) {
            String pais = request.getParameter("pais");
            if (pais != null) {
                Pais paisBusqueda = new Pais();
                paisBusqueda.setCodigo(Integer.parseInt(pais));
                DepartamentoFachada departamentoFachada = new DepartamentoFachada();
                ArrayList<Departamento> departamentos = (ArrayList<Departamento>) departamentoFachada.getListByCondition(paisBusqueda);
                JSONArray arregloDepartamentos = new JSONArray();
                for (Departamento departamento : departamentos) {
                    JSONObject obj = new JSONObject();
                    obj.put("codigo", departamento.getCodigo());
                    obj.put("nombre", departamento.getNombre());
                    arregloDepartamentos.add(obj);
                }
                out.print(arregloDepartamentos);
            } else {

            }
        }
    }

    public void getCiudades(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try (PrintWriter out = response.getWriter()) {
            String departamento = request.getParameter("departamento");
            if (departamento != null) {
                Departamento departamentoBusqueda = new Departamento();
                departamentoBusqueda.setCodigo(Integer.parseInt(departamento));
                CiudadFachada ciudadFachada = new CiudadFachada();
                ArrayList<Ciudad> ciudades = (ArrayList<Ciudad>) ciudadFachada.getListByCondition(departamentoBusqueda);
                JSONArray arregloCiudades = new JSONArray();
                for (Ciudad ciudad : ciudades) {
                    JSONObject obj = new JSONObject();
                    obj.put("codigo", ciudad.getCodigo());
                    obj.put("nombre", ciudad.getNombre());
                    arregloCiudades.add(obj);
                }
                out.print(arregloCiudades);
            } else {

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
}
