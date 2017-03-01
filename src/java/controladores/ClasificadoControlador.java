/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.ArticuloFachada;
import fachada.CategoriaFachada;
import fachada.EstructuraFachada;
import fachada.GestionFachada;
import fachada.MultimediaFachada;
import fachada.PrioridadFachada;
import fachada.UsuarioFachada;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import persistencia.entidades.Articulo;
import persistencia.entidades.ArticuloEstado;
import persistencia.entidades.Categoria;
import persistencia.entidades.Comunidad;
import persistencia.entidades.Estructura;
import persistencia.entidades.Multimedia;
import persistencia.entidades.Prioridad;
import persistencia.entidades.TipoArticulo;
import persistencia.entidades.Usuario;
import utilitarias.LecturaConfig;
import utilitarias.Utilitaria;
import persistencia.entidades.Error;
import persistencia.entidades.TipoError;
import utilitarias.Visibilidad;

/**
 *
 * @author Jesus.Ramos
 */
@WebServlet(name = "ClasificadoControlador", urlPatterns = {"/ClasificadoControlador"})
public class ClasificadoControlador extends HttpServlet {

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
        try {
            response.setContentType("text/html;charset=UTF-8");
            if (request.getParameter("opc") != null) {
                int opcion = Integer.parseInt(request.getParameter("opc"));
                switch (opcion) {
                    case 1:
                        recuperarCategorias(request, response);
                        break;
                    case 2:
                        recuperarPrioridad(request, response);
                        break;
                    case 3:
                        crearRegistros(request, response);
                        break;
                    case 4:
                        recuperarMostrar(request, response);
                        break;
                    case 5:
                        recuperarRangoPrecio(request, response);
                        break;
                    case 6:
                        recuperarOrdenarPor(request, response);
                        break;
                    case 7:
                        recuperarUltimosClasificados(request, response);
                        break;
                    case 8:
                        editarRegistros(request, response);
                        break;
                    case 9:
                        recuperarInicioClasificado(request, response);
                        break;
                    case 10:
                        tipoArticulo(request, response);
                        break;
                    case 11:
                        tablaRegistros(request, response);
                        break;
                    case 12:
                        borrarRegistros(request, response);
                        break;
                    case 13:
                        filtrarCategorias(request, response);
                        break;
                    case 14:
                        recuperarClasificado(request, response);
                        break;
                    case 15:
                        tablaRegistrosAdmin(request, response);
                        break;
                    case 16:
                        aprobarArticulo(request, response);
                        break;
                    case 17:
                        devolverArticulo(request, response);
                        break;
                    case 18:
                        buscarRegistros(request, response);
                        break;
                    case 19:
                        filtrarCategoriasAdmin(request, response);
                        break;
                    case 20:
                        buscarRegistrosAdmin(request, response);
                        break;
                    case 21:
                        usuarioLogueado(request, response);
                        break;
                    case 22:
                        borrarRegistrosAdmin(request, response);
                        break;
                    case 23:
                        recuperarInicioClasificadoPublico(request, response);
                        break;
                    case 24:
                        recuperarUltimosClasificadosPublico(request, response);
                        break;
                    case 25:
                        editarRegistrosInicio(request, response);
                        break;
                }
            }
        } catch (IOException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("processRequest");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (ParseException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("processRequest");
            error.setTipoError(new TipoError(4));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (org.json.simple.parser.ParseException ex) {
            Logger.getLogger(ClasificadoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void recuperarCategorias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            CategoriaFachada catFachada = new CategoriaFachada();
            out.print(Utilitaria.construirCategorias(catFachada.getListObject()));
        }
    }

    private void recuperarPrioridad(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            PrioridadFachada prioFachada = new PrioridadFachada();
            List<Prioridad> listPrioridad = prioFachada.getListObject();
            JSONArray array = new JSONArray();
            for (Prioridad prio : listPrioridad) {
                JSONObject obj = new JSONObject();
                obj.put("codigo", prio.getCodigo());
                obj.put("nombre", prio.getNombre());
                obj.put("valor", prio.getValor());
                obj.put("activo", prio.getActivo());
                array.add(obj);
            }
            out.print(array);
        }
    }

    private void recuperarMostrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            EstructuraFachada estrucFachada = new EstructuraFachada();
            String ref = "rangoPaginas";
            Estructura estruc = new Estructura(ref);
            estruc = (Estructura) estrucFachada.getObject(estruc);
            String[] mostrar = estruc.getValor().split(";");
            JSONArray array = new JSONArray();
            for (int i = 0; i < mostrar.length; i++) {
                JSONObject obj = new JSONObject();
                obj.put("cantidad", mostrar[i]);
                array.add(obj);
            }
            out.print(array);
        }
    }

    private void recuperarRangoPrecio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            EstructuraFachada estrucFachada = new EstructuraFachada();
            String ref = "rangoPrecio";
            Estructura estruc = new Estructura(ref);
            estruc = (Estructura) estrucFachada.getObject(estruc);
            String[] rango = estruc.getValor().split(";");
            JSONArray array = new JSONArray();
            for (int i = 1; i < rango.length; i++) {
                JSONObject obj = new JSONObject();
                obj.put("rango", "$" + rango[i - 1] + " - $" + rango[i] + " ");
                array.add(obj);
            }
            out.print(array);
        }
    }

    private void recuperarOrdenarPor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            EstructuraFachada estrucFachada = new EstructuraFachada();
            String ref = "ordenarPor";
            Estructura estruc = new Estructura(ref);
            estruc = (Estructura) estrucFachada.getObject(estruc);
            String[] rango = estruc.getValor().split(";");
            String ref2 = "clasificadoOrdenar";
            Estructura estruc2 = new Estructura(ref2);
            estruc2 = (Estructura) estrucFachada.getObject(estruc2);
            String[] rango2 = estruc2.getValor().split(";");
            JSONArray array = new JSONArray();
            for (int i = 0; i < rango2.length; i++) {
                String[] ordenar = rango2[i].split(",");
                for (int j = 0; j < rango.length; j++) {
                    JSONObject obj = new JSONObject();
                    if (rango[j].equals("ASC")) {
                        obj.put("campo", ordenar[0]);
                        obj.put("nombre", ordenar[1] + " ascendete");
                        obj.put("orden", rango[j]);
                    }
                    if (rango[j].equals("DESC")) {
                        obj.put("campo", ordenar[0]);
                        obj.put("nombre", ordenar[1] + " descendente");
                        obj.put("orden", rango[j]);
                    }
                    array.add(obj);
                }
            }
            out.print(array);
        }
    }

    private void crearRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        try (PrintWriter out = response.getWriter()) {
            EstructuraFachada estrucFachada = new EstructuraFachada();
            response.setContentType("text/html;charset=UTF-8");
            String codArt = request.getParameter("codArt");
            Articulo art = new Articulo();
            art.setTitulo(request.getParameter("titulo"));
            art.setDescripcion(request.getParameter("descripcion"));
            Usuario usr = (Usuario) request.getSession().getAttribute("user");
            art.setUsuario(usr);
            int cat;
            cat = Integer.parseInt(request.getParameter("categoria"));
            ArticuloEstado artEstado = new ArticuloEstado();
            String ref2 = "articuloEstadoInicial";
            Estructura estruc3 = new Estructura(ref2);
            estruc3 = (Estructura) estrucFachada.getObject(estruc3);
            art.setEstado(new ArticuloEstado(Integer.parseInt(estruc3.getValor())));
            art.setPrioridad(new Prioridad(Integer.parseInt(request.getParameter("prioridad"))));
            art.setPrecio(Double.parseDouble(request.getParameter("precio")));
            ArticuloFachada artFach = new ArticuloFachada();
            String ref = "tipoClasificado";
            Estructura estruc2 = new Estructura(ref);
            estruc2 = (Estructura) estrucFachada.getObject(estruc2);
            art.setTipoArticulo(new TipoArticulo(Integer.parseInt(estruc2.getValor())));
            Categoria categ = new Categoria();
            categ.setCodigo(cat);
            art.setCategoria(categ);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date parsed = format.parse(request.getParameter("finPublicacion"));
            java.sql.Date date = new java.sql.Date(parsed.getTime());
            art.setFechaFinPublicacion(date);
            art.setComunidad(usr.getPerfilCodigo().getComunidad());
            short visibilidad = Short.parseShort(request.getParameter("visibilidad"));
            if (request.getParameter("visibilidad") != null) {
                Visibilidad visibilidadArticulo = new Visibilidad(visibilidad);
                art.setVisibilidad(visibilidadArticulo);
            }

            if (codArt.equals("")) {
                artFach.insertObject(art);
            } else {
                art.setCodigo(Integer.parseInt(codArt));
                artFach.updateObject(art);
            }
            out.print(art.getCodigo());
        }
        request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se creo el clasificado", "success"));
    }

    private void aprobarArticulo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        try (PrintWriter out = response.getWriter()) {
            EstructuraFachada estrucFachada = new EstructuraFachada();
            response.setContentType("text/html;charset=UTF-8");
            Articulo art = new Articulo();
            art.setCodigo(Integer.parseInt(request.getParameter("cod")));
            String ref2 = "articuloEstadoAprobado";
            Estructura estruc3 = new Estructura(ref2);
            estruc3 = (Estructura) estrucFachada.getObject(estruc3);
            art.setEstado(new ArticuloEstado(Integer.parseInt(estruc3.getValor())));
            ArticuloFachada artFach = new ArticuloFachada();
            artFach.updateObject(art);
            art = (Articulo) artFach.getObject(new Articulo(Integer.parseInt(request.getParameter("cod"))));
            Utilitaria.enviarMailArticuloAprobado(art, art.getTitulo());
            out.print(1);

        }
        request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se aprobo el clasificado", "success"));
    }

    private void devolverArticulo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        PrintWriter out = response.getWriter();
        GestionFachada estructuraFachada = new EstructuraFachada();
        response.setContentType("text/html;charset=UTF-8");
        Articulo art = new Articulo();
        art.setCodigo(Integer.parseInt(request.getParameter("cod")));
        art.setObservacionesAdmon(request.getParameter("obs"));
        art.setEstado(new ArticuloEstado((Integer.parseInt(((Estructura) estructuraFachada.getObject(new Estructura("articuloDevuelto"))).getValor()))));
        ArticuloFachada artFach = new ArticuloFachada();
        artFach.updateObject(art);
        art = (Articulo) artFach.getObject(new Articulo(Integer.parseInt(request.getParameter("cod"))));
        Utilitaria.enviarMailArticuloDevuelto(art, request.getParameter("obs"), request.getParameter("tit"));
        out.print(1);
        request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se envio a correción el clasificado", "success"));
    }

    private void recuperarUltimosClasificados(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            EstructuraFachada estrucFachada = new EstructuraFachada();
            Articulo art = new Articulo();
            art.setRango("0," + ((Estructura) estrucFachada.getObject(new Estructura("cantidadUltimosClasificados"))).getValor());
            art.setTipoArticulo(new TipoArticulo(Integer.parseInt(((Estructura) estrucFachada.getObject(new Estructura("tipoClasificado"))).getValor())));
            Usuario user = (Usuario) request.getSession().getAttribute("user");
            art.setEstado(new ArticuloEstado(Integer.parseInt(((Estructura) estrucFachada.getObject(new Estructura("articuloEstadoAprobado"))).getValor())));
            art.setComunidad(user.getPerfilCodigo().getComunidad());
            art.setInicio(true);
            ArticuloFachada artFachada = new ArticuloFachada();
            List<Articulo> listArticulo = artFachada.getListObject(art);
            JSONArray array = new JSONArray();
            for (Articulo art2 : listArticulo) {
                JSONObject obj = new JSONObject();
                obj.put("codigo", art2.getCodigo());
                obj.put("nombre", art2.getTitulo());
                array.add(obj);

            }
            out.print(array);
        }
    }

    private void recuperarInicioClasificado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, org.json.simple.parser.ParseException {
        try (PrintWriter out = response.getWriter()) {
            Usuario user = (Usuario) request.getSession().getAttribute("user");
            EstructuraFachada estrucFachada = new EstructuraFachada();
            Articulo art = new Articulo();
            art.setTipoArticulo(new TipoArticulo(Integer.parseInt(((Estructura) estrucFachada.getObject(new Estructura("tipoClasificado"))).getValor())));
            art.setComunidad(user.getPerfilCodigo().getComunidad());
            art.setRango(request.getParameter("limIni") + "," + ((Estructura) estrucFachada.getObject(new Estructura("clasificadoMostrarInicio"))).getValor());
            String condicion = Utilitaria.construyeCondicion(request.getParameter("opciones"));
            ArticuloFachada artFachada = new ArticuloFachada();
            art.setBusqueda(condicion);
            ArticuloEstado estado = new ArticuloEstado(Integer.parseInt(((Estructura) estrucFachada.getObject(new Estructura("articuloEstadoAprobado"))).getValor()));
            art.setEstado(estado);
            MultimediaFachada multFachada = new MultimediaFachada();
            art.setInicio(true);
            List<Articulo> listArticulo = artFachada.getListByPagination(art);
            JSONArray array = new JSONArray();
            for (Articulo art2 : listArticulo) {
                if (art2.getFechaPublicacion() != null) {
                    JSONObject obj = new JSONObject();
                    obj.put("codigo", art2.getCodigo());
                    Multimedia mult = new Multimedia(new Articulo(art2.getCodigo()));
                    mult.setDestacada(Short.parseShort("1"));
                    mult = (Multimedia) multFachada.getObject(mult);
                    if (mult.getExtension() != null && !mult.getExtension().isEmpty()) {
                        String path = LecturaConfig.getValue("rutaVisualiza") + "\\" + art2.getCodigo() + "\\" + mult.getCodigo() + "." + mult.getExtension();
                        obj.put("imgDestacada", path);
                    } else {
                        String path = LecturaConfig.getValue("rutaImg") + "\\" + ((Estructura) estrucFachada.getObject(new Estructura("sinImagenArticulo"))).getValor();
                        obj.put("imgDestacada", path);
                    }
                    obj.put("titulo", art2.getTitulo());
                    obj.put("precio", Utilitaria.conversionNatural(art2.getPrecio()));
                    array.add(obj);
                }
            }
            out.print(array);
        }
    }

    private void editarRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            int cod = Integer.parseInt(request.getParameter("cod"));
            Articulo art = new Articulo();
            art.setCodigo(cod);
            ArticuloFachada artFachada = new ArticuloFachada();
            TipoArticulo tpArt = new TipoArticulo();
            GestionFachada estFach = new EstructuraFachada();
            Estructura estructura = new Estructura();
            estructura.setReferencia("tipoClasificado");
            Estructura est = (Estructura) estFach.getObject(estructura);
            tpArt.setCodigo(Integer.parseInt(est.getValor()));
            art.setTipoArticulo(tpArt);
            Usuario usr = (Usuario) request.getSession().getAttribute("user");
            art.setUsuario(usr);
            art.setComunidad(usr.getPerfilCodigo().getComunidad());
            art = (Articulo) artFachada.getObject(art);
            MultimediaFachada multFachada = new MultimediaFachada();
            List<Multimedia> listMult = multFachada.getListObject(art);
            JSONArray jsArray = new JSONArray();
            for (Multimedia mult : listMult) {
                JSONObject obj1 = new JSONObject();
                String name = String.valueOf(mult.getCodigo());
                obj1.put("name", name);
                obj1.put("ext", mult.getExtension());
                obj1.put("destacada", mult.getDestacada());
                jsArray.add(obj1);
            }
            JSONObject obj = new JSONObject();
            obj.put("codigo", art.getCodigo());
            obj.put("usuario_codigo", art.getUsuario().getCodigo());
            obj.put("usuario_nombres", art.getUsuario().getNombres());
            obj.put("usuario_apellidos", art.getUsuario().getApellidos());
            obj.put("titulo", art.getTitulo());
            obj.put("descripcion", art.getDescripcion());
            obj.put("fecha_publicacion", ((art.getFechaPublicacion() == null ? "" : art.getFechaPublicacion().toString())));
            obj.put("fecha_fin_publicacion", ((art.getFechaFinPublicacion() == null ? "" : art.getFechaFinPublicacion().toString())));
            obj.put("estados_codigo", art.getEstado().getCodigo());
            obj.put("tipo_articulo_codigo", art.getTipoArticulo().getCodigo());
            obj.put("precio", art.getPrecio());
            obj.put("prioridad", art.getPrioridad().getValor());
            obj.put("categoria_codigo", art.getCategoria().getCodigo());
            obj.put("visibilidad", art.getVisibilidad().getVisibilidad());
            if (art.getObservacionesAdmon() != null && !art.getObservacionesAdmon().isEmpty()) {
                obj.put("observacionAdmin", art.getObservacionesAdmon());
            } else {
                obj.put("observacionAdmin", "Sin observacion");
            }
            obj.put("Imagenes", jsArray);
            obj.put("Directorio", LecturaConfig.getValue("rutaVisualiza") + art.getCodigo() + "\\");
            out.print(obj);
        }
    }

    private void tipoArticulo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            GestionFachada estFach = new EstructuraFachada();
            out.print(((Estructura) estFach.getObject(new Estructura("tipoClasificado"))).getValor());
        }
    }

    private void tablaRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            ArticuloFachada artFachada = new ArticuloFachada();
            GestionFachada estFach = new EstructuraFachada();
            Articulo articulo = new Articulo();
            articulo.setTipoArticulo(new TipoArticulo(Integer.parseInt(request.getParameter("tipo"))));
            articulo.setRango(request.getParameter("rango"));
            Usuario user = (Usuario) request.getSession().getAttribute("user");
            articulo.setUsuario(user);
            articulo.setComunidad(user.getPerfilCodigo().getComunidad());
            if (request.getParameter("cat") != null) {
                if (!request.getParameter("cat").equals("")) {
                    articulo.setCategoria(new Categoria(Integer.parseInt(request.getParameter("cat"))));
                }
            }
            if (request.getParameter("buscar") != null) {
                articulo.setBusqueda(request.getParameter("buscar"));
            }
            List<Articulo> listArticulo = artFachada.getListByPagination(articulo);
            String ref = "articuloEstadoAprobado";
            Estructura estruc = new Estructura(ref);
            estruc = (Estructura) estFach.getObject(estruc);
            String ref2 = "articuloEstadoInicial";
            Estructura estruc2 = new Estructura(ref2);
            estruc2 = (Estructura) estFach.getObject(estruc2);
            JSONArray jsonArray = new JSONArray();
            for (Articulo art : listArticulo) {
                JSONObject jsonObj = new JSONObject();
                if (user.getCodigo() == art.getUsuario().getCodigo()) {
                    jsonObj.put("codigo", art.getCodigo());
                    jsonObj.put("titulo", art.getTitulo());
                    jsonObj.put("nombreUsuario", art.getUsuario().getNombres());
                    jsonObj.put("apellidoUsuario", art.getUsuario().getApellidos());
                    jsonObj.put("nombreCategoria", art.getCategoria().getNombre());
                    jsonObj.put("nombreEstado", art.getEstado().getNombre());
                    jsonObj.put("codigoEstado", art.getEstado().getCodigo());
                    jsonObj.put("fechafinPublicacion", art.getFechaFinPublicacion().toString());
                    if (art.getEstado().getCodigo() == Integer.parseInt(estruc.getValor()) || art.getEstado().getCodigo() == Integer.parseInt(estruc2.getValor())) {
                        jsonObj.put("fechaPublicacion", 0);
                    } else {
                        jsonObj.put("fechaPublicacion", art.getFechaPublicacion().toString());
                    }
                    jsonArray.add(jsonObj);
                }
            }
            out.print(jsonArray);
        }
    }

    private void tablaRegistrosAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            ArticuloFachada artFachada = new ArticuloFachada();
            GestionFachada estFach = new EstructuraFachada();
            TipoArticulo tipArt = new TipoArticulo();
            tipArt.setCodigo(Integer.parseInt(request.getParameter("tipo")));
            Articulo articulo = new Articulo();
            articulo.setTipoArticulo(tipArt);
            articulo.setRango(request.getParameter("rango"));
            articulo.setComunidad(((Usuario) request.getSession().getAttribute("user")).getPerfilCodigo().getComunidad());
            if (request.getParameter("cat") != null) {
                if (!request.getParameter("cat").equals("")) {
                    articulo.setCategoria(new Categoria(Integer.parseInt(request.getParameter("cat"))));
                }
            }
            if (request.getParameter("buscar") != null) {
                articulo.setBusqueda(request.getParameter("buscar"));
            }
            List<Articulo> listArticulo = artFachada.getListByPagination(articulo);
            JSONArray jsonArray = new JSONArray();
            String estadoI = ((Estructura) estFach.getObject(new Estructura("articuloEstadoInicial"))).getValor();
            for (Articulo art : listArticulo) {
                if (art.getFechaPublicacion() == null && art.getEstado().getCodigo() == Integer.parseInt(estadoI)) {
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("codigo", art.getCodigo());
                    jsonObj.put("titulo", art.getTitulo());
                    jsonObj.put("nombreUsuario", art.getUsuario().getNombres());
                    jsonObj.put("apellidoUsuario", art.getUsuario().getApellidos());
                    jsonObj.put("nombreCategoria", art.getCategoria().getNombre());
                    jsonObj.put("nombreEstado", art.getEstado().getNombre());
                    jsonObj.put("fechafinPublicacion", art.getFechaFinPublicacion().toString());
                    jsonArray.add(jsonObj);
                }
            }
            out.print(jsonArray);
        }
    }

    private void borrarRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            EstructuraFachada estrucFachada = new EstructuraFachada();
            int cod = Integer.parseInt(request.getParameter("cod").trim());
            Articulo art = new Articulo();
            art.setCodigo(cod);
            String ref = "tipoClasificado";
            Estructura estruc2 = new Estructura(ref);
            estruc2 = (Estructura) estrucFachada.getObject(estruc2);
            art.setTipoArticulo(new TipoArticulo(Integer.parseInt(estruc2.getValor())));
            ArticuloFachada artFachada = new ArticuloFachada();
            artFachada.deleteObject(art);
            out.print(1);
            Utilitaria.borrarArchivos(LecturaConfig.getValue("rutaUpload") + art.getCodigo(), true);
        }
        request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se ha eliminado el clasificado correctamente", "success"));
    }

    private void borrarRegistrosAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            EstructuraFachada estrucFachada = new EstructuraFachada();
            int cod = Integer.parseInt(request.getParameter("cod").trim());
            Articulo art = new Articulo();
            art.setCodigo(cod);
            String ref = "tipoClasificado";
            Estructura estruc2 = new Estructura(ref);
            estruc2 = (Estructura) estrucFachada.getObject(estruc2);
            art.setTipoArticulo(new TipoArticulo(Integer.parseInt(estruc2.getValor())));
            ArticuloFachada artFachada = new ArticuloFachada();
            Articulo art2 = (Articulo) artFachada.getObject(new Articulo(Integer.parseInt(request.getParameter("cod").trim())));
            artFachada.deleteObject(art);
            Utilitaria.enviarMailArticuloEliminado(art2, request.getParameter("descrip"), request.getParameter("tit"));
            out.print(1);
        }
        request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se ha eliminado el clasificado correctamente", "success"));
    }

    private void filtrarCategorias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            ArticuloFachada artFachada = new ArticuloFachada();
            EstructuraFachada estFach = new EstructuraFachada();
            TipoArticulo tipArt = new TipoArticulo();
            tipArt.setCodigo(Integer.parseInt(request.getParameter("tipo")));
            Articulo articulo = new Articulo();
            articulo.setTipoArticulo(tipArt);
            articulo.setUsuario((Usuario) request.getSession().getAttribute("user"));
            if (request.getParameter("cat") != null) {
                if (!request.getParameter("cat").equals("")) {
                    articulo.setCategoria(new Categoria(Integer.parseInt(request.getParameter("cat"))));
                }
            }
            articulo.setBusqueda("%");
            List<Articulo> listArticulo = artFachada.getListByCondition(articulo);
            JSONArray jsonArray = new JSONArray();
            String estadoI = ((Estructura) estFach.getObject(new Estructura("articuloEstadoInicial"))).getValor();
            String estadoA = ((Estructura) estFach.getObject(new Estructura("articuloEstadoAprobado"))).getValor();
            for (Articulo art : listArticulo) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("codigo", art.getCodigo());
                jsonObj.put("titulo", art.getTitulo());
                jsonObj.put("nombreUsuario", art.getUsuario().getNombres());
                jsonObj.put("apellidoUsuario", art.getUsuario().getApellidos());
                jsonObj.put("nombreCategoria", art.getCategoria().getNombre());
                jsonObj.put("nombreEstado", art.getEstado().getNombre());
                jsonObj.put("codigoEstado", art.getEstado().getCodigo());
                jsonObj.put("fechafinPublicacion", art.getFechaFinPublicacion().toString());
                if (art.getEstado().getCodigo() == Integer.parseInt(estadoI) || art.getEstado().getCodigo() == Integer.parseInt(estadoA)) {
                    jsonObj.put("fechaPublicacion", 0);
                } else {
                    jsonObj.put("fechaPublicacion", art.getFechaPublicacion().toString());
                }
                jsonArray.add(jsonObj);
            }
            out.print(jsonArray);
        }
    }

    private void filtrarCategoriasAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            ArticuloFachada artFachada = new ArticuloFachada();
            GestionFachada estFach = new EstructuraFachada();
            TipoArticulo tipArt = new TipoArticulo();
            tipArt.setCodigo(Integer.parseInt(request.getParameter("tipo")));
            Articulo articulo = new Articulo();
            articulo.setTipoArticulo(tipArt);
            articulo.setUsuario((Usuario) request.getSession().getAttribute("user"));
            if (request.getParameter("cat") != null) {
                if (!request.getParameter("cat").equals("")) {
                    articulo.setCategoria(new Categoria(Integer.parseInt(request.getParameter("cat"))));
                }
            }
            articulo.setBusqueda("%");
            List<Articulo> listArticulo = artFachada.getListByCondition(articulo);
            JSONArray jsonArray = new JSONArray();
            String estadoI = ((Estructura) estFach.getObject(new Estructura("articuloEstadoInicial"))).getValor();
            for (Articulo art : listArticulo) {
                if (art.getFechaPublicacion() == null && art.getEstado().getCodigo() == Integer.parseInt(estadoI)) {
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("codigo", art.getCodigo());
                    jsonObj.put("titulo", art.getTitulo());
                    jsonObj.put("nombreUsuario", art.getUsuario().getNombres());
                    jsonObj.put("apellidoUsuario", art.getUsuario().getApellidos());
                    jsonObj.put("nombreCategoria", art.getCategoria().getNombre());
                    jsonObj.put("nombreEstado", art.getCategoria().getNombre());
                    jsonObj.put("fechafinPublicacion", art.getFechaFinPublicacion().toString());
                    if (art.getFechaPublicacion() == null) {
                        jsonObj.put("fechaPublicacion", 0);
                    } else {
                        jsonObj.put("fechaPublicacion", art.getFechaPublicacion().toString());
                    }
                    jsonArray.add(jsonObj);
                }
            }
            out.print(jsonArray);
        }
    }

    private void recuperarClasificado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            ArticuloFachada artFachada = new ArticuloFachada();
            Articulo art = (Articulo) artFachada.getObject(new Articulo(Integer.parseInt(request.getParameter("id"))));
            UsuarioFachada userFachada = new UsuarioFachada();
            art.setUsuario((Usuario) userFachada.getObject(new Usuario(art.getUsuario().getCodigo())));
            MultimediaFachada multFachada = new MultimediaFachada();
            List<Multimedia> listMult = multFachada.getListObject(art);
            JSONArray jsArray = new JSONArray();
            String pathOrigen = LecturaConfig.getValue("rutaVisualiza") + "\\" + art.getCodigo();
            for (Multimedia mult : listMult) {
                JSONObject obj1 = new JSONObject();
                obj1.put("ruta", pathOrigen + "\\" + mult.getCodigo() + "." + mult.getExtension());
                obj1.put("destacada", mult.getDestacada());
                jsArray.add(obj1);
            }
            JSONObject obj = new JSONObject();
            obj.put("codigo", art.getCodigo());
            obj.put("nombreUsuario", art.getUsuario().getUserName());
            if (art.getUsuario().getCelular() != null) {
                obj.put("telefono", art.getUsuario().getCelular());
            } else {
                obj.put("telefono", art.getUsuario().getTelefono());
            }
            obj.put("titulo", art.getTitulo());
            obj.put("descripcion", art.getDescripcion());
            obj.put("fechaPublicacion", Utilitaria.convertirFecha(art.getFechaPublicacion()));
            obj.put("descripcion", art.getDescripcion());
            obj.put("precio", Utilitaria.conversionNatural(art.getPrecio()));
            obj.put("ubicacion",art.getComunidad().getDepartamentoCodigo().getNombre()+","+art.getComunidad().getCiudadCodigo().getNombre()+","+art.getComunidad().getNombre());
            obj.put("imagenes", jsArray);
            out.print(obj);
        }
    }

    private void buscarRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            ArticuloFachada artFachada = new ArticuloFachada();
            TipoArticulo tipArt = new TipoArticulo();
            EstructuraFachada estFach = new EstructuraFachada();
            tipArt.setCodigo(Integer.parseInt(request.getParameter("tipo")));
            Articulo articulo = new Articulo();
            articulo.setTipoArticulo(tipArt);
            articulo.setUsuario((Usuario) request.getSession().getAttribute("user"));
            if (request.getParameter("buscar") != null) {
                articulo.setBusqueda(request.getParameter("buscar"));
            }
            List<Articulo> listArticulo = artFachada.getListByCondition(articulo);
            JSONArray jsonArray = new JSONArray();
            String estadoI = ((Estructura) estFach.getObject(new Estructura("articuloEstadoInicial"))).getValor();
            String estadoA = ((Estructura) estFach.getObject(new Estructura("articuloEstadoAprobado"))).getValor();
            for (Articulo art : listArticulo) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("codigo", art.getCodigo());
                jsonObj.put("titulo", art.getTitulo());
                jsonObj.put("nombreUsuario", art.getUsuario().getNombres());
                jsonObj.put("apellidoUsuario", art.getUsuario().getApellidos());
                jsonObj.put("nombreCategoria", art.getCategoria().getNombre());
                jsonObj.put("nombreEstado", art.getEstado().getNombre());
                jsonObj.put("codigoEstado", art.getEstado().getCodigo());
                jsonObj.put("fechafinPublicacion", art.getFechaFinPublicacion().toString());
                if (art.getEstado().getCodigo() == Integer.parseInt(estadoI) || art.getEstado().getCodigo() == Integer.parseInt(estadoA)) {
                    jsonObj.put("fechaPublicacion", 0);
                } else {
                    jsonObj.put("fechaPublicacion", art.getFechaPublicacion().toString());
                }
                jsonArray.add(jsonObj);
            }
            out.print(jsonArray);
        }
    }

    private void buscarRegistrosAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            ArticuloFachada artFachada = new ArticuloFachada();
            GestionFachada estFach = new EstructuraFachada();
            TipoArticulo tipArt = new TipoArticulo();
            tipArt.setCodigo(Integer.parseInt(request.getParameter("tipo")));
            Articulo articulo = new Articulo();
            articulo.setTipoArticulo(tipArt);
            articulo.setUsuario((Usuario) request.getSession().getAttribute("user"));
            if (request.getParameter("buscar") != null) {
                articulo.setBusqueda(request.getParameter("buscar"));
            }
            List<Articulo> listArticulo = artFachada.getListByCondition(articulo);
            JSONArray jsonArray = new JSONArray();
            String estadoI = ((Estructura) estFach.getObject(new Estructura("articuloEstadoInicial"))).getValor();
            for (Articulo art : listArticulo) {
                if (art.getFechaPublicacion() == null && art.getEstado().getCodigo() == Integer.parseInt(estadoI)) {
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("codigo", art.getCodigo());
                    jsonObj.put("titulo", art.getTitulo());
                    jsonObj.put("nombreUsuario", art.getUsuario().getNombres());
                    jsonObj.put("apellidoUsuario", art.getUsuario().getApellidos());
                    jsonObj.put("nombreCategoria", art.getCategoria().getNombre());
                    jsonObj.put("nombreEstado", art.getCategoria().getNombre());
                    jsonObj.put("fechafinPublicacion", art.getFechaFinPublicacion().toString());
                    if (art.getFechaPublicacion() == null) {
                        jsonObj.put("fechaPublicacion", 0);
                    } else {
                        jsonObj.put("fechaPublicacion", art.getFechaPublicacion().toString());
                    }
                    jsonArray.add(jsonObj);
                }
            }
            out.print(jsonArray);
        }
    }

    private void usuarioLogueado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Usuario user = (Usuario) request.getSession().getAttribute("user");
        JSONObject obj = new JSONObject();
        obj.put("codigo", user.getCodigo());
        obj.put("nombres", user.getNombres());
        obj.put("apellidos", user.getApellidos());
        out.print(obj);
    }

    private void recuperarInicioClasificadoPublico(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, org.json.simple.parser.ParseException {
        try (PrintWriter out = response.getWriter()) {
            EstructuraFachada estrucFachada = new EstructuraFachada();
            Articulo art = new Articulo();
            art.setTipoArticulo(new TipoArticulo(Integer.parseInt(((Estructura) estrucFachada.getObject(new Estructura("tipoClasificado"))).getValor())));
            art.setRango(request.getParameter("limIni") + "," + ((Estructura) estrucFachada.getObject(new Estructura("clasificadoMostrarInicio"))).getValor());
            String condicion = Utilitaria.construyeCondicion(request.getParameter("opciones"));
            ArticuloFachada artFachada = new ArticuloFachada();
            art.setBusqueda(condicion);
            ArticuloEstado estado = new ArticuloEstado(Integer.parseInt(((Estructura) estrucFachada.getObject(new Estructura("articuloEstadoAprobado"))).getValor()));
            art.setEstado(estado);
            MultimediaFachada multFachada = new MultimediaFachada();
            art.setInicio(true);
            art.setVisibilidad(new Visibilidad(Short.parseShort("1")));
            art.setComunidad(new Comunidad(Integer.parseInt(((Estructura) estrucFachada.getObject(new Estructura("comunidadAnonima"))).getValor())));
            art.getComunidad().setVisibilidad(new Visibilidad(Short.parseShort("1")));
            List<Articulo> listArticulo = artFachada.getListByPagination(art);
            JSONArray array = new JSONArray();
            for (Articulo art2 : listArticulo) {
                if (art2.getFechaPublicacion() != null) {
                    JSONObject obj = new JSONObject();
                    obj.put("codigo", art2.getCodigo());
                    Multimedia mult = new Multimedia(new Articulo(art2.getCodigo()));
                    mult.setDestacada(Short.parseShort("1"));
                    mult = (Multimedia) multFachada.getObject(mult);
                    if (mult.getExtension() != null && !mult.getExtension().isEmpty()) {
                        String path = LecturaConfig.getValue("rutaVisualiza") + "\\" + art2.getCodigo() + "\\" + mult.getCodigo() + "." + mult.getExtension();
                        obj.put("imgDestacada", path);
                    } else {
                        String path = LecturaConfig.getValue("rutaImg") + "\\" + ((Estructura) estrucFachada.getObject(new Estructura("sinImagenArticulo"))).getValor();
                        obj.put("imgDestacada", path);
                    }
                    obj.put("titulo", art2.getTitulo());
                    obj.put("precio", Utilitaria.conversionNatural(art2.getPrecio()));
                    array.add(obj);
                }
            }
            out.print(array);
        }
    }

    private void recuperarUltimosClasificadosPublico(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            EstructuraFachada estrucFachada = new EstructuraFachada();
            Articulo art = new Articulo();
            art.setRango("0," + ((Estructura) estrucFachada.getObject(new Estructura("cantidadUltimosClasificados"))).getValor());
            art.setTipoArticulo(new TipoArticulo(Integer.parseInt(((Estructura) estrucFachada.getObject(new Estructura("tipoClasificado"))).getValor())));
            art.setEstado(new ArticuloEstado(Integer.parseInt(((Estructura) estrucFachada.getObject(new Estructura("articuloEstadoAprobado"))).getValor())));
            art.setInicio(true);
            art.setVisibilidad(new Visibilidad(Short.parseShort("1")));
            art.setComunidad(new Comunidad(Integer.parseInt(((Estructura) estrucFachada.getObject(new Estructura("comunidadAnonima"))).getValor())));
            art.getComunidad().setVisibilidad(new Visibilidad(Short.parseShort("1")));
            ArticuloFachada artFachada = new ArticuloFachada();
            List<Articulo> listArticulo = artFachada.getListObject(art);
            JSONArray array = new JSONArray();
            for (Articulo art2 : listArticulo) {
                JSONObject obj = new JSONObject();
                obj.put("codigo", art2.getCodigo());
                obj.put("nombre", art2.getTitulo());
                array.add(obj);

            }
            out.print(array);
        }
    }
    
    private void editarRegistrosInicio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            int cod = Integer.parseInt(request.getParameter("id"));
            Articulo art = new Articulo();
            art.setCodigo(cod);
            ArticuloFachada artFachada = new ArticuloFachada();
            TipoArticulo tpArt = new TipoArticulo();
            GestionFachada estFach = new EstructuraFachada();
            Estructura estructura = new Estructura();
            estructura.setReferencia("tipoClasificado");
            Estructura est = (Estructura) estFach.getObject(estructura);
            tpArt.setCodigo(Integer.parseInt(est.getValor()));
            art.setTipoArticulo(tpArt);
            art = (Articulo) artFachada.getObject(art);
            UsuarioFachada userFachada = new UsuarioFachada();
            art.setUsuario((Usuario) userFachada.getObject(new Usuario(art.getUsuario().getCodigo())));
            MultimediaFachada multFachada = new MultimediaFachada();
            List<Multimedia> listMult = multFachada.getListObject(art);
            JSONArray jsArray = new JSONArray();
            for (Multimedia mult : listMult) {
                JSONObject obj1 = new JSONObject();
                String name = String.valueOf(mult.getCodigo());
                obj1.put("name", name);
                obj1.put("ext", mult.getExtension());
                obj1.put("destacada", mult.getDestacada());
                jsArray.add(obj1);
            }
            JSONObject obj = new JSONObject();
            obj.put("codigo", art.getCodigo());
            obj.put("usuario_codigo", art.getUsuario().getCodigo());
            obj.put("autor",art.getUsuario().getNombres()+" "+art.getUsuario().getApellidos());
            obj.put("nombreUsuario", art.getUsuario().getUserName());
            if (art.getUsuario().getCelular() != null) {
                obj.put("telefono", art.getUsuario().getCelular());
            } else {
                obj.put("telefono", art.getUsuario().getTelefono());
            }
            obj.put("precio", Utilitaria.conversionNatural(art.getPrecio()));
            obj.put("ubicacion",art.getComunidad().getDepartamentoCodigo().getNombre()+","+art.getComunidad().getCiudadCodigo().getNombre()+","+art.getComunidad().getNombre());
            obj.put("titulo", art.getTitulo());
            obj.put("descripcion", art.getDescripcion());
            obj.put("fecha_publicacion", ((art.getFechaPublicacion() == null ? "" : art.getFechaPublicacion().toString())));
            obj.put("fecha_fin_publicacion", ((art.getFechaFinPublicacion() == null ? "" : art.getFechaFinPublicacion().toString())));
            obj.put("estados_codigo", art.getEstado().getCodigo());
            obj.put("tipo_articulo_codigo", art.getTipoArticulo().getCodigo());
            obj.put("categoria_codigo", art.getCategoria().getCodigo());
            obj.put("visibilidad", art.getVisibilidad().getVisibilidad());
            obj.put("Imagenes", jsArray);
            obj.put("Directorio", LecturaConfig.getValue("rutaVisualiza") + art.getCodigo() + "/");
            System.out.println(obj);
            out.print(obj);
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

}
