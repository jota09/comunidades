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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import persistencia.entidades.Ciudad;
import persistencia.entidades.Comunidad;
import persistencia.entidades.CondicionPaginacion;
import persistencia.entidades.Departamento;
import persistencia.entidades.Pais;
import persistencia.entidades.Usuario;
import utilitarias.CondicionPaginado;
import utilitarias.LecturaConfig;
import utilitarias.Utilitaria;
import utilitarias.Visibilidad;

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
                    this.getComunidades(request, response);
                    break;
                case 2:
                    this.getComunidad(request, response);
                    break;
                case 3:
                    this.borrarComunidad(request, response);
                    break;
                case 4:
                    this.guardarComunidad(request, response);
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
                case 13:
                    this.getComunidadSession(request,response);
                    break;
                 
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getComunidades(HttpServletRequest request, HttpServletResponse response)
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

    private void getComunidad(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String codigoComunidad = request.getParameter("comunidad");
        if (codigoComunidad != null && !codigoComunidad.trim().isEmpty()) {
            GestionFachada comunidadFachada = new ComunidadFachada();

            Comunidad comunidad = new Comunidad();
            comunidad.setCodigo(Integer.parseInt(codigoComunidad));
            comunidadFachada.getObject(comunidad);

            String rutaLogoDisco = LecturaConfig.getValue("pathResources") + File.separator + "logos" + File.separator + comunidad.getCodigo() + ".png";
            String urlImagen = "";

            File file = new File(rutaLogoDisco);
            if (!file.exists()) {
                urlImagen = LecturaConfig.getValue("rutaImg") + "logo/3.png";
            } else {
                urlImagen = LecturaConfig.getValue("rutaImg") + "logo/" + codigoComunidad + ".png";
            }

            GestionFachada ciudadFachada = new CiudadFachada();
            Ciudad ciudad = new Ciudad();
            ciudad.setCodigo(comunidad.getCiudadCodigo().getCodigo());
            ciudadFachada.getObject(ciudad);

            JSONObject obj = new JSONObject();
            obj.put("codigo", comunidad.getCodigo());
            obj.put("nit", comunidad.getNit());
            obj.put("nombre", comunidad.getNombre());
            obj.put("direccion", comunidad.getDireccion());
            obj.put("telefono", comunidad.getTelefono());
            obj.put("pais", ciudad.getDepartamento().getPais().getCodigo());
            obj.put("departamento", ciudad.getDepartamento().getCodigo());
            obj.put("ciudad", comunidad.getCiudadCodigo().getCodigo());
            obj.put("visibilidad", comunidad.getVisibilidad().getVisibilidad());
            obj.put("codigoBarras", comunidad.getIdBarCode());
            obj.put("logo", urlImagen);

            try (PrintWriter out = response.getWriter()) {
                out.print(obj);
            }
        }
    }
    
    private void borrarComunidad(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession sesion = request.getSession();

        String codigo = request.getParameter("comunidad");
        if (!codigo.isEmpty()) {
            GestionFachada gestionFachada = new ComunidadFachada();
            Comunidad comunidad = new Comunidad();
            comunidad.setCodigo(Integer.parseInt(codigo));
            comunidad.setActivo((short) 0);
            int cantidad = gestionFachada.updateObject(comunidad);
            if (cantidad > 0) {
                sesion.setAttribute("message", Utilitaria.createAlert("Éxito", "Se ha eliminado la comunidad", "success"));
            } else {
                sesion.setAttribute("message", Utilitaria.createAlert("Error", "Ha ocurrido un error al eliminar la comunidad", "danger"));
            }
        }
    }

    private void guardarComunidad(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession sesion = request.getSession();

        String codigo = request.getParameter("codigo");
        String nit = request.getParameter("nit");
        String nombre = request.getParameter("nombre");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");
        String codigoCiudad = request.getParameter("ciudad");
        String codigoVisibilidad = request.getParameter("visibilidad");
        String codigoBarras = request.getParameter("codigoBarras");
        String imagen64 = request.getParameter("imagen");
        
        int cantidad = 0;

        if (nit != null && !nit.isEmpty()
                && nombre != null && !nombre.isEmpty()
                && direccion != null && !direccion.isEmpty()
                && telefono != null && !telefono.isEmpty()
                && codigoCiudad != null && !codigoCiudad.isEmpty()
                && codigoVisibilidad != null && !codigoVisibilidad.isEmpty()) {

            GestionFachada comunidadFachada = new ComunidadFachada();
            Comunidad comunidad = new Comunidad();
            comunidad.setNit(nit);
            comunidad.setNombre(nombre);
            comunidad.setDireccion(direccion);
            comunidad.setTelefono(telefono);
            Ciudad ciudad = new Ciudad();
            ciudad.setCodigo(Integer.parseInt(codigoCiudad));
            comunidad.setCiudadCodigo(ciudad);
            Visibilidad visibilidad = new Visibilidad();
            visibilidad.setVisibilidad(Short.parseShort(codigoVisibilidad));
            comunidad.setVisibilidad(visibilidad);

            if (codigoBarras != null && !codigoBarras.isEmpty()) {
                comunidad.setIdBarCode(codigoBarras);
            }

            if (codigo != null && !codigo.isEmpty()) {
                comunidad.setCodigo(Integer.parseInt(codigo));
                comunidad.setActivo((short) -1);
                cantidad = comunidadFachada.updateObject(comunidad);
            } else {
                cantidad = comunidadFachada.insertObject(comunidad);
            }

            if (cantidad > 0) {
                if (imagen64 != null && !imagen64.isEmpty()) {
                    File file = new File(LecturaConfig.getValue("pathResources") + File.separator + "logos" + File.separator + comunidad.getCodigo() + ".png");
                    FileOutputStream out = new FileOutputStream(file);
                    out.write(DatatypeConverter.parseBase64Binary(imagen64.split(",")[1]));
                    out.close();
                }
                sesion.setAttribute("message", Utilitaria.createAlert("Éxito", "Se ha guardado la comunidad " + comunidad.getNombre(), "success"));
            } else {
                sesion.setAttribute("message", Utilitaria.createAlert("Error", "Ha ocurrido un error al crear la comunidad " + comunidad.getNombre(), "danger"));
            }
        } else {
            sesion.setAttribute("message", Utilitaria.createAlert("Error", "Faltan datos para crear la comunidad", "danger"));
        }
    }

    private void getPaises(HttpServletRequest request, HttpServletResponse response)
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

    private void getDepartamentos(HttpServletRequest request, HttpServletResponse response)
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

    private void getCiudades(HttpServletRequest request, HttpServletResponse response)
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

    private void getComunidadSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession sesion=request.getSession();
        Usuario user=(Usuario) sesion.getAttribute("user");
        if (user!=null) {
            GestionFachada comunidadFachada = new ComunidadFachada();

            Comunidad comunidad = new Comunidad();
            comunidad.setCodigo(user.getPerfilCodigo().getComunidad().getCodigo());
            comunidadFachada.getObject(comunidad);

            String rutaLogoDisco = LecturaConfig.getValue("pathResources") + File.separator + "logos" + File.separator + comunidad.getCodigo() + ".png";
            String urlImagen = "";

            File file = new File(rutaLogoDisco);
            if (!file.exists()) {
                urlImagen = LecturaConfig.getValue("rutaImg") + "logo/3.png";
            } else {
                urlImagen = LecturaConfig.getValue("rutaImg") + "logo/" + comunidad.getCodigo() + ".png";
            }
            GestionFachada ciudadFachada = new CiudadFachada();
            Ciudad ciudad = new Ciudad();
            ciudad.setCodigo(comunidad.getCiudadCodigo().getCodigo());
            ciudadFachada.getObject(ciudad);
            JSONObject obj = new JSONObject();
            obj.put("nit", comunidad.getNit());
            obj.put("nombre", comunidad.getNombre());
            obj.put("direccion", comunidad.getDireccion());
            obj.put("telefono", comunidad.getTelefono());
            obj.put("pais", ciudad.getDepartamento().getPais().getNombre());
            obj.put("departamento", ciudad.getDepartamento().getNombre());
            obj.put("ciudad", comunidad.getCiudadCodigo().getNombre());
            obj.put("visibilidad", comunidad.getVisibilidad().getVisibilidad());
            obj.put("logo", urlImagen);

            try (PrintWriter out = response.getWriter()) {
                out.print(obj);
            }
        }
    }
}
