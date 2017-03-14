/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.daos;

import fachada.EstructuraFachada;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import persistencia.conexion.ConexionBD;
import persistencia.entidades.Articulo;
import persistencia.entidades.ArticuloEstado;
import persistencia.entidades.Categoria;
import persistencia.entidades.Ciudad;
import persistencia.entidades.Comunidad;
import persistencia.entidades.Departamento;
import persistencia.entidades.TipoArticulo;
import persistencia.entidades.Usuario;
import utilitarias.CondicionPaginado;
import utilitarias.Utilitaria;
import persistencia.entidades.Error;
import persistencia.entidades.Estructura;
import persistencia.entidades.Pais;
import persistencia.entidades.Prioridad;
import persistencia.entidades.TipoError;
import utilitarias.Visibilidad;

/**
 *
 * @author ferney.medina
 */
public class ArticuloDAO implements GestionDAO {

    @Override
    public Object getObject(Object object) {
        Articulo art = (Articulo) object;
        Connection con = null;
        try {

            con = ConexionBD.obtenerConexion();
            String query = "SELECT * FROM articulo AS art JOIN usuario AS u ON art.USUARIO_CODIGO = u.CODIGO JOIN prioridad AS p ON p.CODIGO = art.PRIORIDAD_CODIGO JOIN comunidad AS c ON art.COMUNIDAD_CODIGO = c.CODIGO JOIN ciudad AS ciu ON c.CIUDAD_CODIGO = ciu.CODIGO JOIN departamento AS d ON ciu.departamento_CODIGO = d.CODIGO JOIN pais ON d.pais_CODIGO = pais.CODIGO WHERE art.codigo=?";
            PreparedStatement pS = con.prepareStatement(query);
            pS.setInt(1, art.getCodigo());
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                Usuario usr = new Usuario();
                usr.setCodigo(rS.getInt("art.usuario_codigo"));
                usr.setNombres(rS.getString("u.nombres"));
                usr.setApellidos(rS.getString("u.apellidos"));
                usr.setCorreo(rS.getString("u.correo"));
                art.setUsuario(usr);
                art.setCategoria(new Categoria(rS.getInt("art.categoria_codigo")));
                art.setTipoArticulo(new TipoArticulo(rS.getInt("art.tipo_articulo_codigo")));
                art.setTitulo(rS.getString("art.titulo"));
                art.setDescripcion(rS.getString("art.descripcion"));
                art.setFechaPublicacion(rS.getDate("art.fecha_publicacion"));
                art.setFechaFinPublicacion(rS.getDate("art.fecha_fin_publicacion"));
                art.setObservacionesAdmon(rS.getString("art.observaciones_admon"));
                art.setEstado(new ArticuloEstado(rS.getInt("art.estados_codigo")));
                art.setPrecio(rS.getDouble("art.precio"));
                art.setPrioridad(new Prioridad(rS.getInt("p.codigo"), rS.getString("p.nombre"), rS.getInt("p.valor")));
                art.setVisibilidad(new Visibilidad(rS.getShort("visibilidad")));
                Comunidad comunidad = new Comunidad(rS.getInt("c.CODIGO"),rS.getString("c.nombre"));
                comunidad.setCiudadCodigo(new Ciudad(rS.getInt("ciu.codigo"),rS.getString("ciu.nombre")));
                comunidad.setPaisCodigo(new Pais(rS.getInt("pais.codigo"),rS.getString("pais.nombre")));
                comunidad.setDepartamentoCodigo(new Departamento(rS.getInt("d.codigo"),rS.getString("d.nombre")));
                art.setComunidad(comunidad);
            }
            pS.close();
        } catch (ClassNotFoundException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getObject");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getObject");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getObject");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return art;
    }

    @Override
    public List getListObject(Object obj) {
        Articulo art = (Articulo) obj;
        EstructuraFachada estrucFachada = new EstructuraFachada();
        int anonimo = Integer.parseInt(((Estructura) estrucFachada.getObject(new Estructura("comunidadAnonima"))).getValor());
        ArrayList<Articulo> listArt = new ArrayList();
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String query = "SELECT art.CODIGO, art.TITULO, art.FECHA_PUBLICACION "
                    + "FROM articulo AS art INNER JOIN comunidad AS c ON c.CODIGO = art.COMUNIDAD_CODIGO "
                    + "WHERE art.TIPO_ARTICULO_CODIGO = ? " + ((art.getInicio() == true) ? " AND art.FECHA_FIN_PUBLICACION >= CURDATE() " : "")
                    + " " + ((art.getEstado() != null) ? " AND art.ESTADOS_CODIGO = " + art.getEstado().getCodigo() : "")
                    + " " + ((art.getVisibilidad() != null) ? " AND art.VISIBILIDAD=" + art.getVisibilidad().getVisibilidad() : "")
                    + " " + ((art.getComunidad() != null && art.getComunidad().getCodigo() != anonimo) ? "AND art.COMUNIDAD_CODIGO=" + art.getComunidad().getCodigo() : "")
                    + " " + ((art.getComunidad().getVisibilidad() != null) ? "AND c.VISIBILIDAD=" + art.getComunidad().getVisibilidad().getVisibilidad(): "")
                    + " ORDER BY art.FECHA_PUBLICACION DESC "
                    + "LIMIT " + art.getRango() + " ";
            PreparedStatement pS = con.prepareStatement(query);
            pS.setInt(1, art.getTipoArticulo().getCodigo());
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Articulo art2 = new Articulo();
                art2.setCodigo(rS.getInt("CODIGO"));
                art2.setTitulo(rS.getString("TITULO"));
                art2.setFechaPublicacion(rS.getDate("FECHA_PUBLICACION"));
                listArt.add(art2);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListObject");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListObject");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListObject");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return listArt;
    }

    @Override
    public int updateObject(Object object) {
        Articulo art = (Articulo) object;
        Connection con = null;
        int numfilas = 0;
        try {
            con = ConexionBD.obtenerConexion();
            if (art.getTitulo() != null) {
                String sql = "UPDATE articulo SET usuario_codigo=?, titulo=?, descripcion=?,"
                        + "fecha_publicacion=?, precio=?,fecha_fin_publicacion=?, prioridad_codigo=?,"
                        + "estados_codigo=?,tipo_articulo_codigo=?,categoria_codigo=?,visibilidad=?, actualizacion = now() "
                        + " WHERE codigo=? and comunidad_codigo=? ";
                PreparedStatement pS = con.prepareStatement(sql);
                pS.setInt(1, art.getUsuario().getCodigo());
                pS.setString(2, art.getTitulo());
                pS.setString(3, art.getDescripcion());
                pS.setDate(4, art.getFechaPublicacion());
                pS.setDouble(5, art.getPrecio());
                pS.setDate(6, art.getFechaFinPublicacion());
                pS.setInt(7, art.getPrioridad().getCodigo());
                pS.setInt(8, art.getEstado().getCodigo());
                pS.setInt(9, art.getTipoArticulo().getCodigo());
                pS.setInt(10, art.getCategoria().getCodigo());
                pS.setShort(11, (short) art.getVisibilidad().getVisibilidad());
                pS.setInt(12, art.getCodigo());
                pS.setInt(13, art.getComunidad().getCodigo());
                numfilas = pS.executeUpdate();
                pS.close();
            } else {
                String sql = "UPDATE articulo SET fecha_publicacion=NOW(), estados_codigo=?,observaciones_admon = ?,actualizacion = now()"
                        + " WHERE codigo=?";
                PreparedStatement pS = con.prepareStatement(sql);
                pS.setInt(1, art.getEstado().getCodigo());
                pS.setString(2, art.getObservacionesAdmon());
                pS.setInt(3, art.getCodigo());

                numfilas = pS.executeUpdate();
                pS.close();
            }
        } catch (ClassNotFoundException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("updateObject");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("updateObject");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("updateObject");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return numfilas;
    }

    @Override
    public synchronized int insertObject(Object object) {
        Articulo art = (Articulo) object;
        Connection con = null;
        int result = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "INSERT INTO articulo( codigo,usuario_codigo, titulo, descripcion,"
                    + " precio, fecha_fin_publicacion, prioridad_codigo,"
                    + "estados_codigo,tipo_articulo_codigo,categoria_codigo,comunidad_codigo,visibilidad) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            try (PreparedStatement pS = con.prepareStatement(sql)) {
                art.setCodigo(getMaxCodigo());
                pS.setInt(1, art.getCodigo());
                pS.setInt(2, art.getUsuario().getCodigo());
                pS.setString(3, art.getTitulo());
                pS.setString(4, art.getDescripcion());
                pS.setDouble(5, art.getPrecio());
                pS.setDate(6, art.getFechaFinPublicacion());
                pS.setInt(7, art.getPrioridad().getCodigo());
                pS.setInt(8, art.getEstado().getCodigo());
                pS.setInt(9, art.getTipoArticulo().getCodigo());
                pS.setInt(10, art.getCategoria().getCodigo());
                pS.setInt(11, art.getComunidad().getCodigo());
                pS.setShort(12, (short) art.getVisibilidad().getVisibilidad());
                result = pS.executeUpdate();
                pS.close();
            }
        } catch (ClassNotFoundException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("insertObject");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("insertObject");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("insertObject");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return result;
    }

    @Override
    public List getListObject() {
        return null;
    }

    @Override
    public int getCount(Object obj) {
        CondicionPaginado condicionPaginado = (CondicionPaginado) obj;
        Connection con = null;
        int cont = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "SELECT COUNT('codigo') FROM articulo WHERE tipo_articulo_codigo=? and "+((condicionPaginado.getUser() != null) ? " usuario_codigo=" + condicionPaginado.getUser().getCodigo()+" and" : " and ")+"  comunidad_codigo=? "
                    + "" + ((condicionPaginado.getEstado() != null) ? " and estados_codigo=" + condicionPaginado.getEstado() : "") + " ";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, condicionPaginado.getTipo());
            pS.setInt(2, condicionPaginado.getComunidad().getCodigo());
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                cont = rS.getInt(1);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getCount");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getCount");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getCount");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return cont;
    }

    @Override
    public void deleteObject(Object object) {
        Articulo art = (Articulo) object;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "DELETE FROM articulo WHERE codigo=? AND tipo_articulo_codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, art.getCodigo());
            pS.setInt(2, art.getTipoArticulo().getCodigo());
            pS.execute();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("deleteObject");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("deleteObject");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("deleteObject");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
    }

    @Override
    public List getListByCondition(Object object) {
        Articulo articulo = (Articulo) object;
        ArrayList<Articulo> listArt = new ArrayList<Articulo>();
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String query = "SELECT art.*,usr.codigo,usr.nombres,usr.apellidos,cat.*,"
                    + "    artEstado.codigo,artEstado.nombre nombreEstado"
                    + "    FROM articulo art JOIN "
                    + "    usuario usr ON  art.usuario_codigo=usr.codigo JOIN "
                    + "    categoria cat ON art.categoria_codigo=cat.codigo JOIN "
                    + "    articulo_estado artEstado ON art.estados_codigo=artEstado.codigo "
                    + "    WHERE art.tipo_articulo_codigo=? " + ( (articulo.getUsuario() != null) ? "AND art.usuario_codigo="+articulo.getUsuario().getCodigo() : "" )
                    + "    " + ((articulo.getCategoria() != null) ? " AND art.categoria_codigo=" + articulo.getCategoria().getCodigo() + " AND" : "AND ")
                    + "    (art.titulo LIKE ?"
                    + "    OR usr.nombres LIKE ? OR usr.apellidos LIKE ? OR artEstado.nombre LIKE ? OR art.fecha_fin_publicacion LIKE ? OR cat.nombre LIKE ?) and art.COMUNIDAD_CODIGO = ? "+ ((articulo.getRango() != null) ? "LIMIT " + articulo.getRango() + " " : "")+ "";
            PreparedStatement pS = con.prepareStatement(query);
            System.out.println(query);
            pS.setInt(1, articulo.getTipoArticulo().getCodigo());
            pS.setString(2, "%" + articulo.getBusqueda() + "%");
            pS.setString(3, "%" + articulo.getBusqueda() + "%");
            pS.setString(4, "%" + articulo.getBusqueda() + "%");
            pS.setString(5, "%" + articulo.getBusqueda() + "%");
            pS.setString(6, "%" + articulo.getBusqueda() + "%");
            pS.setString(7, "%" + articulo.getBusqueda() + "%");
            pS.setInt(8, articulo.getComunidad().getCodigo());
            ResultSet rS = pS.executeQuery();
            System.out.println(pS);
            while (rS.next()) {
                Articulo art = new Articulo();
                Categoria cat = new Categoria();
                ArticuloEstado estado = new ArticuloEstado(rS.getInt("estados_codigo"));
                estado.setNombre(rS.getString("nombreEstado"));
                Usuario usr = new Usuario();
                art.setCodigo(rS.getInt("codigo"));
                usr.setCodigo(rS.getInt("usuario_codigo"));
                usr.setNombres(rS.getString("nombres"));
                usr.setApellidos(rS.getString("apellidos"));
                art.setUsuario(usr);
                cat.setCodigo(rS.getInt("codigo"));
                cat.setNombre(rS.getString("nombre"));
                art.setCategoria(cat);
                art.setTitulo(rS.getString("titulo"));
                art.setDescripcion(rS.getString("descripcion"));
                art.setFechaPublicacion(rS.getDate("fecha_publicacion"));
                art.setFechaFinPublicacion(rS.getDate("fecha_fin_publicacion"));
                art.setEstado(estado);
                listArt.add(art);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListByCondition");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListByCondition");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListByCondition");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return listArt;
    }

    @Override
    public List getListByPagination(Object object) {
        Connection con = null;
        Articulo art = (Articulo) object;
        EstructuraFachada estrucFachada = new EstructuraFachada();
        int anonimo = Integer.parseInt(((Estructura) estrucFachada.getObject(new Estructura("comunidadAnonima"))).getValor());
        List<Articulo> articulos = new ArrayList();
        try {
            con = ConexionBD.obtenerConexion();

            String sql = "SELECT art.codigo,art.titulo,art.descripcion,art.fecha_publicacion,"
                    + "         art.fecha_fin_publicacion,cat.codigo,cat.nombre,artEstado.codigo,"
                    + "         artEstado.nombre,art.precio,usr.codigo,usr.nombres,usr.apellidos  "
                    + "   FROM articulo art "
                    + "         JOIN categoria cat ON art.categoria_codigo=cat.codigo "
                    + "         JOIN articulo_estado artEstado ON art.estados_codigo=artEstado.codigo "
                    + "         JOIN usuario usr ON art.usuario_codigo=usr.codigo"
                    + "         JOIN comunidad c ON art.comunidad_codigo=c.codigo"
                    + "   WHERE " + ((art.getEstado() != null) ? "art.estados_codigo=" + art.getEstado().getCodigo() + " and " : "") + " "
                    + "         art.tipo_articulo_codigo=? " + ((art.getComunidad() != null && art.getComunidad().getCodigo() != anonimo) ? "and art.comunidad_codigo=" + art.getComunidad().getCodigo() : "")
                    + ((art.getVisibilidad() != null) ? " and art.visibilidad=" + art.getVisibilidad().getVisibilidad() + " " : "") + ""
                    + " " + ((art.getBusqueda() != null && !art.getBusqueda().isEmpty()) ? " and "
                    + art.getBusqueda() : "") + ((art.getUsuario() != null) ? " and art.usuario_codigo=" + art.getUsuario().getCodigo() : "")
                    + " " + ((art.getComunidad().getVisibilidad() != null) ? "AND c.visibilidad=" + art.getComunidad().getVisibilidad().getVisibilidad(): "")
                    + ((art.getInicio() == true) ? " and art.fecha_fin_publicacion >= CURDATE() " : "") + ""
                    + " Limit " + art.getRango();
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, art.getTipoArticulo().getCodigo());
            System.out.println(pS);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Articulo articulo = new Articulo();
                Categoria cat = new Categoria();
                Usuario usr = new Usuario();
                ArticuloEstado estado = new ArticuloEstado();
                articulo.setCodigo(rS.getInt(1));
                articulo.setTitulo(rS.getString(2));
                articulo.setDescripcion(rS.getString(3));
                articulo.setFechaPublicacion(rS.getDate(4));
                articulo.setFechaFinPublicacion(rS.getDate(5));
                cat.setCodigo(rS.getInt(6));
                cat.setNombre(rS.getString(7));
                estado.setCodigo(rS.getInt(8));
                estado.setNombre(rS.getString(9));
                articulo.setPrecio(rS.getDouble(10));
                usr.setCodigo(rS.getInt(11));
                usr.setNombres(rS.getString(12));
                usr.setApellidos(rS.getString(13));
                articulo.setCategoria(cat);
                articulo.setEstado(estado);
                articulo.setUsuario(usr);
                articulos.add(articulo);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListByPagination");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListByPagination");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListByPagination");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return articulos;
    }

    private  int getMaxCodigo() {
        Connection con = null;
        int cont = 1;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "SELECT MAX(codigo)+1 codigo FROM articulo ";
            PreparedStatement pS = con.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                int max = rS.getInt(1);
                cont = ((max > 0) ? max : cont);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getMaxCodigo");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getMaxCodigo");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getMaxCodigo");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return cont;
    }

}
