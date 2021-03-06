/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import fachada.ArticuloFachada;
import fachada.CategoriaFachada;
import fachada.CondicionPaginacionFachada;
import fachada.EstructuraFachada;
import fachada.GestionFachada;
import fachada.MultimediaFachada;
import fachada.UsuarioFachada;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import java.util.logging.Logger;
import java.util.logging.Level;
import persistencia.entidades.Articulo;
import persistencia.entidades.ArticuloEstado;
import persistencia.entidades.Categoria;
import persistencia.entidades.TipoArticulo;
import persistencia.entidades.Usuario;
import org.json.simple.JSONObject;
import persistencia.entidades.Comunidad;
import persistencia.entidades.CondicionPaginacion;
import persistencia.entidades.Estructura;
import persistencia.entidades.Multimedia;
import persistencia.entidades.Prioridad;
import utilitarias.LecturaConfig;
import utilitarias.Utilitaria;
import persistencia.entidades.Error;
import persistencia.entidades.TipoError;
import utilitarias.CondicionPaginado;
import utilitarias.Visibilidad;

/**
 *
 * @author ferney.medina
 */
@WebServlet(name = "Noticia", urlPatterns = {"/NoticiaControlador"})
public class NoticiaControlador extends HttpServlet {

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
            if (request.getParameter("op") != null) {
                int opcion = Integer.parseInt(request.getParameter("op"));
                switch (opcion) {
                    case 1:
                        tablaRegistros(request, response);
                        break;
                    case 2:
                        crearRegistros(request, response);
                        break;
                    case 3:
                        editarRegistros(request, response);
                        break;
                    case 4:
                        borrarRegistros(request, response);
                        break;
                    case 5:
                        aprobarArticulo(request, response);
                        break;
                    case 6:
                        cargarCategorias(request, response);
                        break;
                    case 7:
                        filtrarCategorias(request, response);
                        break;
                    case 8:
                        buscarRegistros(request, response);
                        break;
                    case 9:
                        tipoArticulo(request, response);
                        break;
                    case 10:
                        devolverArticulo(request, response);
                        break;
                    case 11:
                        recuperarNoticia(request, response);
                        break;
                    case 12:
                        recuperarMostrar(request, response);
                        break;
                    case 13:
                        recuperarOrdenarPor(request, response);
                        break;
                    case 14:
                        recuperarCategorias(request, response);
                        break;
                    case 15:
                        recuperarUltimos(request, response);
                        break;
                    case 16:
                        recuperarInicioNoticia(request, response);
                        break;
                    case 17:
                        tablaRegistrosAdmin(request, response);
                        break;
                    case 18:
                        filtrarCategoriasAdmin(request, response);
                        break;
                    case 19:
                        buscarRegistrosAdmin(request, response);
                        break;
                    case 20:
                        usuarioLogueado(request, response);
                        break;
                    case 21:
                        borrarRegistrosAdmin(request, response);
                        break;
                }
            } else {
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
            Logger.getLogger(NoticiaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void tipoArticulo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            GestionFachada estFach = new EstructuraFachada();
            Estructura estructura = new Estructura();
            estructura.setReferencia("tipoNoticia");
            Estructura est = (Estructura) estFach.getObject(estructura);
            out.print(est.getValor());
        }
    }

    private void tablaRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            ArticuloFachada artFachada = new ArticuloFachada();
            String rango = request.getParameter("rango");
            String condicionesPag = request.getParameter("condicionesPag");
            String busqueda = request.getParameter("busqueda");
            GestionFachada condicionFachada = new CondicionPaginacionFachada();
            CondicionPaginacion condicionPaginacion = new CondicionPaginacion(Integer.parseInt(condicionesPag));
            condicionFachada.getObject(condicionPaginacion);
            CondicionPaginado condicion = new CondicionPaginado();
            condicion.setCondicion(condicionPaginacion.getCondicion().replace("<?>", busqueda) + " limit " + rango);
            condicion.setUser(((Usuario) request.getSession().getAttribute("user")));
            condicion.setComunidad(((Usuario) request.getSession().getAttribute("user")).getPerfilCodigo().getComunidad());
            List<Articulo> listArticulo = artFachada.getListByPagination(condicion);
            JSONArray jsonArray = new JSONArray();
            for (Articulo art : listArticulo) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("codigo", art.getCodigo());
                jsonObj.put("titulo", art.getTitulo());
                jsonObj.put("nombreCategoria", art.getCategoria().getNombre());
                jsonObj.put("nombreEstado", art.getEstado().getNombre());
                jsonObj.put("codigoEstado", art.getEstado().getCodigo());
                jsonObj.put("fechafinPublicacion", art.getFechaFinPublicacion().toString());
                if (art.getFechaPublicacion() == null || art.getFechaPublicacion().toString().equals("")) {
                    jsonObj.put("fechaPublicacion", 0);
                } else {
                    jsonObj.put("fechaPublicacion", art.getFechaPublicacion().toString());
                }
                jsonArray.add(jsonObj);
            }
            out.print(jsonArray);
        }
    }

    private void crearRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        try (PrintWriter out = response.getWriter()) {
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
            artEstado.setCodigo(1);
            art.setEstado(artEstado);
            art.setPrioridad(new Prioridad(1));//La noticia no tiene prioridad pero sin embargo se le envia el 1(baja)
            ArticuloFachada artFach = new ArticuloFachada();
            TipoArticulo tpArt = new TipoArticulo();
            tpArt.setCodigo(2);//Tipo articulo noticia es 2
            art.setTipoArticulo(tpArt);
            Categoria categ = new Categoria();
            categ.setCodigo(cat);
            art.setCategoria(categ);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date parsed = format.parse(request.getParameter("finPublicacion"));
            java.sql.Date date = new java.sql.Date(parsed.getTime());
            art.setFechaFinPublicacion(date);
            art.setComunidad(usr.getPerfilCodigo().getComunidad());
            art.setVisibilidad(new Visibilidad(Short.parseShort(request.getParameter("visibilidad"))));
            if (codArt.equals("")) {
                artFach.insertObject(art);
            } else {
                art.setCodigo(Integer.parseInt(codArt));
                artFach.updateObject(art);
            }
            out.print(art.getCodigo());
        }
        request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se creo la noticia", "success"));
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
            estructura.setReferencia("tipoNoticia");
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
            obj.put("autor", art.getUsuario().getNombres() + " " + art.getUsuario().getApellidos());
            obj.put("titulo", art.getTitulo());
            obj.put("descripcion", art.getDescripcion());
            obj.put("fecha_publicacion", ((art.getFechaPublicacion() == null ? "" : art.getFechaPublicacion().toString())));
            obj.put("fecha_fin_publicacion", ((art.getFechaFinPublicacion() == null ? "" : art.getFechaFinPublicacion().toString())));
            obj.put("estados_codigo", art.getEstado().getCodigo());
            obj.put("tipo_articulo_codigo", art.getTipoArticulo().getCodigo());
            obj.put("categoria_codigo", art.getCategoria().getCodigo());
            if (art.getObservacionesAdmon() != null && !art.getObservacionesAdmon().isEmpty()) {
                obj.put("observacionAdmin", art.getObservacionesAdmon());
            } else {
                obj.put("observacionAdmin", "Sin observacion");
            }
            obj.put("visibilidad", art.getVisibilidad().getVisibilidad());
            obj.put("Imagenes", jsArray);
            obj.put("Directorio", LecturaConfig.getValue("rutaVisualizaArticulo") + art.getCodigo() + "/");
            out.print(obj);
        }
    }

    private void borrarRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            int cod = Integer.parseInt(request.getParameter("cod").trim());
            Articulo art = new Articulo();
            art.setCodigo(cod);
            TipoArticulo tpArt = new TipoArticulo();
            GestionFachada estFach = new EstructuraFachada();
            Estructura estructura = new Estructura();
            estructura.setReferencia("tipoNoticia");
            Estructura est = (Estructura) estFach.getObject(estructura);
            tpArt.setCodigo(Integer.parseInt(est.getValor()));//Tipo articulo noticia es 2
            art.setTipoArticulo(tpArt);
            ArticuloFachada artFachada = new ArticuloFachada();
            artFachada.deleteObject(art);
            Utilitaria.borrarArchivos(LecturaConfig.getValue("rutaUploadArticulo") + art.getCodigo(), true);
        }
    }

    private void cargarCategorias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            CategoriaFachada catFachada = new CategoriaFachada();
            out.print(Utilitaria.construirCategorias(catFachada.getListObject()));
        }
    }

    private void filtrarCategorias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            ArticuloFachada artFachada = new ArticuloFachada();
            TipoArticulo tipArt = new TipoArticulo();
            EstructuraFachada estFach = new EstructuraFachada();
            tipArt.setCodigo(Integer.parseInt(request.getParameter("tipo")));
            Articulo articulo = new Articulo();
            articulo.setTipoArticulo(tipArt);
            articulo.setUsuario((Usuario) request.getSession().getAttribute("user"));
            //articulo.setCategoria(new Categoria(Integer.parseInt(request.getParameter("cat"))));
            if (request.getParameter("cat") != null) {
                if (!request.getParameter("cat").equals("")) {
                    articulo.setCategoria(new Categoria(Integer.parseInt(request.getParameter("cat"))));
                }
            }
            articulo.setBusqueda("%");
            List<Articulo> listArticulo = artFachada.getListByCondition(articulo);
            String estadoI = ((Estructura) estFach.getObject(new Estructura("articuloEstadoInicial"))).getValor();
            String estadoA = ((Estructura) estFach.getObject(new Estructura("articuloEstadoAprobado"))).getValor();
            JSONArray jsonArray = new JSONArray();
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

    private void buscarRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            ArticuloFachada artFachada = new ArticuloFachada();
            TipoArticulo tipArt = new TipoArticulo();
            EstructuraFachada estFach = new EstructuraFachada();
            tipArt.setCodigo(Integer.parseInt(request.getParameter("tipo")));
            Articulo articulo = new Articulo();
            articulo.setTipoArticulo(tipArt);
            articulo.setUsuario((Usuario) request.getSession().getAttribute("user"));
            articulo.setComunidad(new Comunidad(((Usuario) request.getSession().getAttribute("user")).getPerfilCodigo().getComunidad().getCodigo()));
            if (request.getParameter("buscar") != null && !request.getParameter("buscar").isEmpty() && !request.getParameter("buscar").equals("null")) {
                articulo.setBusqueda(request.getParameter("buscar"));
            } else {
                articulo.setBusqueda("");
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

    private void devolverArticulo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        PrintWriter out = response.getWriter();
        GestionFachada estructuraFachada = new EstructuraFachada();
        response.setContentType("text/html;charset=UTF-8");
        Articulo art = new Articulo(Integer.parseInt(request.getParameter("cod")));
        art.setObservacionesAdmon(request.getParameter("obs"));
        art.setEstado(new ArticuloEstado((Integer.parseInt(((Estructura) estructuraFachada.getObject(new Estructura("articuloDevuelto"))).getValor()))));
        ArticuloFachada artFach = new ArticuloFachada();
        artFach.updateObject(art);
        art = (Articulo) artFach.getObject(new Articulo(Integer.parseInt(request.getParameter("cod"))));
        Utilitaria.enviarMailArticuloDevuelto(art, request.getParameter("obs"), request.getParameter("tit"));
        out.print(1);
        request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se envio a correción la noticia", "success"));
    }

    private void aprobarArticulo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        try (PrintWriter out = response.getWriter()) {
            EstructuraFachada estrucFachada = new EstructuraFachada();
            response.setContentType("text/html;charset=UTF-8");
            Articulo art = new Articulo(Integer.parseInt(request.getParameter("cod")));
            art.setEstado(new ArticuloEstado(Integer.parseInt(((Estructura) estrucFachada.getObject(new Estructura("articuloEstadoAprobado"))).getValor())));
            ArticuloFachada artFach = new ArticuloFachada();
            artFach.updateObject(art);
            art = (Articulo) artFach.getObject(new Articulo(Integer.parseInt(request.getParameter("cod"))));
            Utilitaria.enviarMailArticuloAprobado(art, art.getTitulo());
            out.print(1);
        }
        request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se aprobo la noticia", "success"));
    }

    private void recuperarNoticia(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            Articulo art = new Articulo(Integer.parseInt(request.getParameter("id")));
            ArticuloFachada artFachada = new ArticuloFachada();
            art = (Articulo) artFachada.getObject(art);
            Usuario user = new Usuario(art.getUsuario().getCodigo());
            UsuarioFachada userFachada = new UsuarioFachada();
            user = (Usuario) userFachada.getObject(user);
            art.setUsuario(user);
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
            out.print(obj);
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

    private void recuperarUltimos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            EstructuraFachada estrucFachada = new EstructuraFachada();
            Articulo art = new Articulo();
            art.setRango("0," + ((Estructura) estrucFachada.getObject(new Estructura("cantidadUltimasNoticias"))).getValor());
            art.setTipoArticulo(new TipoArticulo(Integer.parseInt(((Estructura) estrucFachada.getObject(new Estructura("tipoNoticia"))).getValor())));
            Usuario user = (Usuario) request.getSession().getAttribute("user");
            art.setUsuario(user);
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
                if (art2.getImagenes().getTipo().getExtension() != null) {
                    obj.put("imagenDestacada", LecturaConfig.getValue("rutaVisualizaArticulo") + art2.getCodigo() + "/" + art2.getImagenes().getCodigo() + "." + art2.getImagenes().getTipo().getExtension());
                } else {
                    obj.put("imagenDestacada", LecturaConfig.getValue("rutaVisualizaArticulo") + "nodisponible.png");
                }
                array.add(obj);
            }
            out.print(array);
        }
    }

    private void recuperarInicioNoticia(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, org.json.simple.parser.ParseException {
        try (PrintWriter out = response.getWriter()) {
            String path;
            int iteracion = Integer.parseInt(request.getParameter("rango"));
            EstructuraFachada estrucFachada = new EstructuraFachada();
            String opcionesFiltro = Utilitaria.construyeCondicion(request.getParameter("opciones"));
            int cantidad = Integer.parseInt(((Estructura) estrucFachada.getObject(new Estructura("noticiaMostrarInicio"))).getValor());
            String condicionesPag = request.getParameter("condicionesPag");
            String busqueda = request.getParameter("busqueda");
            GestionFachada condicionFachada = new CondicionPaginacionFachada();
            CondicionPaginacion condicionPaginacion = new CondicionPaginacion(Integer.parseInt(condicionesPag));
            condicionFachada.getObject(condicionPaginacion);
            CondicionPaginado condicion = new CondicionPaginado();
            condicion.setCondicion(condicionPaginacion.getCondicion().replace("<?>", busqueda)
                    .replace("<#>", ((opcionesFiltro.isEmpty()) ? "" : " AND " + opcionesFiltro))
                    + " limit " + (iteracion * cantidad) + "," + cantidad);
            condicion.setComunidad(((Usuario) request.getSession().getAttribute("user")).getPerfilCodigo().getComunidad());
            ArticuloFachada artFachada = new ArticuloFachada();
            MultimediaFachada multFachada = new MultimediaFachada();
            List<Articulo> listArticulo = artFachada.getListByPagination(condicion);
            JSONArray array = new JSONArray();
            for (Articulo art2 : listArticulo) {
                JSONObject obj = new JSONObject();
                obj.put("codigo", art2.getCodigo());
                Multimedia mult = new Multimedia(new Articulo(art2.getCodigo()));
                mult.setDestacada(Short.parseShort("1"));
                mult = (Multimedia) multFachada.getObject(mult);
                if (mult.getExtension() != null && !mult.getExtension().isEmpty()) {
                    path = LecturaConfig.getValue("rutaVisualizaArticulo") + "\\" + art2.getCodigo() + "\\" + mult.getCodigo() + "." + mult.getExtension();
                } else {
                    path = LecturaConfig.getValue("rutaImg") + "\\" + ((Estructura) estrucFachada.getObject(new Estructura("sinImagenArticulo"))).getValor();
                }
                obj.put("imgDestacada", path);
                obj.put("titulo", art2.getTitulo());
                obj.put("descripcion", art2.getDescripcion());
                obj.put("finPublicacion", art2.getFechaFinPublicacion().toString());
                array.add(obj);
            }
            out.print(array);
        }
    }

    private void recuperarCategorias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            CategoriaFachada catFachada = new CategoriaFachada();
            out.print(Utilitaria.construirCategorias(catFachada.getListObject()));
        }
    }

    private void tablaRegistrosAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            ArticuloFachada artFachada = new ArticuloFachada();
            String rango = request.getParameter("rango");
            String condicionesPag = request.getParameter("condicionesPag");
            String busqueda = request.getParameter("busqueda");
            GestionFachada condicionFachada = new CondicionPaginacionFachada();
            CondicionPaginacion condicionPaginacion = new CondicionPaginacion(Integer.parseInt(condicionesPag));
            condicionFachada.getObject(condicionPaginacion);
            CondicionPaginado condicion = new CondicionPaginado();
            condicion.setCondicion(condicionPaginacion.getCondicion().replace("<?>", busqueda) + " limit " + rango);
            condicion.setComunidad(((Usuario) request.getSession().getAttribute("user")).getPerfilCodigo().getComunidad());
            List<Articulo> listArticulo = artFachada.getListByPagination(condicion);
            JSONArray jsonArray = new JSONArray();
            for (Articulo art : listArticulo) {
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

    private void buscarRegistrosAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            ArticuloFachada artFachada = new ArticuloFachada();
            GestionFachada estFach = new EstructuraFachada();
            TipoArticulo tipArt = new TipoArticulo();
            tipArt.setCodigo(Integer.parseInt(request.getParameter("tipo")));
            Articulo articulo = new Articulo();
            articulo.setTipoArticulo(tipArt);
            articulo.setComunidad(new Comunidad(((Usuario) request.getSession().getAttribute("user")).getPerfilCodigo().getComunidad().getCodigo()));
            if (request.getParameter("buscar") != null && !request.getParameter("buscar").isEmpty() && !request.getParameter("buscar").equals("null")) {
                articulo.setBusqueda(request.getParameter("buscar"));
            } else {
                articulo.setBusqueda("");
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
                    jsonObj.put("nombreEstado", art.getEstado().getNombre());
                    jsonObj.put("codigoEstado", art.getEstado().getCodigo());
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

    private void borrarRegistrosAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            EstructuraFachada estrucFachada = new EstructuraFachada();
            Articulo art = new Articulo(Integer.parseInt(request.getParameter("cod")));
            art.setTipoArticulo(new TipoArticulo(Integer.parseInt(((Estructura) estrucFachada.getObject(new Estructura("tipoNoticia"))).getValor())));
            ArticuloFachada artFachada = new ArticuloFachada();
            Articulo art2 = (Articulo) artFachada.getObject(new Articulo(Integer.parseInt(request.getParameter("cod"))));
            artFachada.deleteObject(art);
            Utilitaria.enviarMailArticuloEliminado(art2, request.getParameter("descrip"), request.getParameter("tit"));
            out.print(1);
        }
        request.getSession().setAttribute("message", Utilitaria.createAlert("Exito", "Se ha eliminado la noticia correctamente", "success"));
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
