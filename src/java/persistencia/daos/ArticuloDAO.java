/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.daos;

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
import persistencia.entidades.Estructura;
import persistencia.entidades.TipoArticulo;
import persistencia.entidades.Usuario;
import utilitarias.CondicionPaginado;
import utilitarias.Utilitaria;
import persistencia.entidades.Error;
import persistencia.entidades.TipoError;

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
            String query = "SELECT * FROM articulo WHERE codigo=? ";
            PreparedStatement pS = con.prepareStatement(query);
            pS.setInt(1, art.getCodigo());
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                Usuario usr = new Usuario();
                TipoArticulo tpArt = new TipoArticulo();
                tpArt.setCodigo(rS.getInt("tipo_articulo_codigo"));
                usr.setCodigo(rS.getInt("usuario_codigo"));
                Categoria cat = new Categoria();
                ArticuloEstado estado = new ArticuloEstado(rS.getInt("estados_codigo"));
                art.setUsuario(usr);
                cat.setCodigo(rS.getInt("categoria_codigo"));
                art.setCategoria(cat);
                art.setTipoArticulo(tpArt);
                art.setTitulo(rS.getString("titulo"));
                art.setDescripcion(rS.getString("descripcion"));
                art.setFechaPublicacion(rS.getDate("fecha_publicacion"));
                art.setFechaFinPublicacion(rS.getDate("fecha_fin_publicacion"));
                art.setEstado(estado);
                art.setPrecio(rS.getDouble("precio"));
                art.setVisibilidad(rS.getShort("visibilidad"));
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
        ArrayList<Articulo> listArt = new ArrayList<Articulo>();
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();

            String query = "SELECT CODIGO, TITULO "
                    + "FROM comunidades.articulo "
                    + "WHERE FECHA_PUBLICACION <= NOW() AND TIPO_ARTICULO_CODIGO = ? AND ESTADOS_CODIGO = ? AND COMUNIDAD_CODIGO = ? "
                    + "ORDER BY FECHA_PUBLICACION DESC "
                    + "LIMIT " + art.getRango() + " ";
            PreparedStatement pS = con.prepareStatement(query);
            pS.setInt(1, art.getTipoArticulo().getCodigo());
            pS.setInt(2, art.getEstado().getCodigo());
            pS.setInt(3, art.getUsuario().getPerfilCodigo().getComunidad().getCodigo());
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
            if(art.getTitulo()!=null){
            String sql = "UPDATE articulo SET usuario_codigo=?, titulo=?, descripcion=?,"
                    + "fecha_publicacion=?, precio=?,fecha_fin_publicacion=?, prioridad_codigo=?,"
                    + "estados_codigo=?,tipo_articulo_codigo=?,categoria_codigo=?,visibilidad=? "
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
            pS.setShort(11, art.getVisibilidad());
            pS.setInt(12, art.getCodigo());
            pS.setInt(13, art.getComunidad().getCodigo());
            numfilas = pS.executeUpdate();
            pS.close();
            }
            else{
                String sql = "UPDATE articulo SET fecha_publicacion=CURDATE(), estados_codigo=?"
                    + " WHERE codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, art.getEstado().getCodigo());
            pS.setInt(2, art.getCodigo());
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
                pS.setShort(12, art.getVisibilidad());
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
            String sql = "SELECT COUNT('codigo') FROM articulo WHERE tipo_articulo_codigo=? and usuario_codigo=? and comunidad_codigo=? ";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, condicionPaginado.getTipo());
            pS.setInt(2, condicionPaginado.getUser().getCodigo());
            pS.setInt(3, condicionPaginado.getComunidad().getCodigo());
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
                    + "    WHERE art.tipo_articulo_codigo=? AND art.usuario_codigo=?"
                    + "    " + ((articulo.getCategoria() != null) ? " AND art.categoria_codigo=" + articulo.getCategoria().getCodigo() + " AND" : "AND ")
                    + "    (art.titulo LIKE ?"
                    + "    OR art.descripcion LIKE ?) and art.COMUNIDAD_CODIGO = ?";
            System.out.println(query);
            PreparedStatement pS = con.prepareStatement(query);
            pS.setInt(1, articulo.getTipoArticulo().getCodigo());
            pS.setInt(2, articulo.getUsuario().getCodigo());
            pS.setString(3, articulo.getBusqueda() + "%");
            pS.setString(4, articulo.getBusqueda() + "%");
            pS.setInt(5, articulo.getUsuario().getPerfilCodigo().getComunidad().getCodigo());
            ResultSet rS = pS.executeQuery();
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
        Articulo articulo = (Articulo) object;
        ArrayList<Articulo> listArt = new ArrayList<Articulo>();
        String rango = "";
        String busqueda = "";
        String condicionArmada = "";
        EstructuraDAO estrucDAO = new EstructuraDAO();
        Estructura estruc = (Estructura) estrucDAO.getObject(new Estructura("comunidadAnonima"));
        String naturalezaSesion = (Integer.parseInt(estruc.getValor()) != articulo.getUsuario().getPerfilCodigo().getComunidad().getCodigo()) ? "AND art.COMUNIDAD_CODIGO = ?" : " AND art.VISIBILIDAD = 0 ";
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            if (!articulo.getRango().isEmpty()) {
                rango = "LIMIT " + articulo.getRango();
            } else {
                rango = "";
            }
            if (articulo.getBusqueda() != null && !articulo.getBusqueda().isEmpty()) {
                if (articulo.getBusqueda().indexOf("/") >= 0) {
                    String[] separarCondiciones = articulo.getBusqueda().split("/");
                    if (separarCondiciones.length > 1 && !separarCondiciones[0].equals("")) {
                        String[] campos = separarCondiciones[0].split(",");
                        for (int i = 0; i < campos.length; i++) {
                            condicionArmada += " and " + campos[i];
                        }
                        busqueda = "OR ( fecha_publicacion <= NOW() " + condicionArmada + " AND art.tipo_articulo_codigo=? )) "+ naturalezaSesion;

                        busqueda += separarCondiciones[1].replace("/", " ") + " ";
                    } else {
                        busqueda = " OR ( fecha_publicacion <= NOW() AND art.tipo_articulo_codigo=?  )) "+naturalezaSesion+" " + separarCondiciones[1].replace("/", " ") + " ";
                    }
                } else {
                    String[] campos = articulo.getBusqueda().split(",");
                    for (int i = 0; i < campos.length; i++) {
                        condicionArmada += " and " + campos[i];
                    }
                    busqueda = "OR ( fecha_publicacion <= NOW() " + condicionArmada + " AND art.tipo_articulo_codigo=?  )) "+naturalezaSesion;
                }
            } else {
                busqueda = "OR ( fecha_publicacion <= NOW() AND art.tipo_articulo_codigo=? )) "+naturalezaSesion+" ORDER BY FECHA_PUBLICACION DESC ";
            }
            String query = "SELECT art.*,usr.codigo,usr.nombres,usr.apellidos,cat.*,"
                    + "artEstado.codigo,artEstado.nombre nombreEstado"
                    + " FROM articulo art JOIN "
                    + "usuario usr ON  art.usuario_codigo=usr.codigo JOIN "
                    + "categoria cat ON art.categoria_codigo=cat.codigo JOIN "
                    + "articulo_estado artEstado ON art.estados_codigo=artEstado.codigo "
                    + "WHERE ((art.tipo_articulo_codigo=? AND art.usuario_codigo=?) "
                    + busqueda + " "
                    + rango;
            PreparedStatement pS = con.prepareStatement(query);
            pS.setInt(1, articulo.getTipoArticulo().getCodigo());
            pS.setInt(2, articulo.getUsuario().getCodigo());
            if (Integer.parseInt(estruc.getValor()) != articulo.getUsuario().getPerfilCodigo().getComunidad().getCodigo()) {
                pS.setInt(3, articulo.getTipoArticulo().getCodigo());
                pS.setInt(4, articulo.getUsuario().getPerfilCodigo().getComunidad().getCodigo());
            } else {
                pS.setInt(3, articulo.getTipoArticulo().getCodigo());
            }
            ResultSet rS = pS.executeQuery();
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
                art.setPrecio(rS.getInt("precio"));
                art.setEstado(estado);
                listArt.add(art);
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
        return listArt;
    }

    private synchronized int getMaxCodigo() {
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
