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
import persistencia.entidades.Autorizacion;
import persistencia.entidades.EstadoAutorizacion;
import persistencia.entidades.Estructura;
import persistencia.entidades.Inmueble;
import persistencia.entidades.MotivoAutorizacion;
import persistencia.entidades.TipoError;
import persistencia.entidades.Usuario;
import utilitarias.CondicionPaginado;
import utilitarias.Utilitaria;

/**
 *
 * @author Jesus.Ramos
 */
public class AutorizacionDAO implements GestionDAO {

    @Override
    public Object getObject(Object object) {
        Autorizacion auto = (Autorizacion) object;
        Connection con = null;
        Autorizacion autorizacion = new Autorizacion();
        try {

            con = ConexionBD.obtenerConexion();
            String sql = "SELECT auto.codigo,auto.usuario_codigo,auto.fecha_autorizacion,"
                    + " auto.fecha_real_ingreso,auto.descripcion,"
                    + " auto.persona_ingresa,auto.documento_persona_ingresa,auto.fecha_real_salida,"
                    + " auto.empresa_contratista, usr.nombres,usr.apellidos,"
                    + " e.codigo,e.nombre,m.codigo,m.nombre  "
                    + " FROM autorizacion auto "
                    + " JOIN motivo_autorizacion m ON auto.motivo_autorizacion_codigo=m.codigo "
                    + " JOIN estado_autorizacion e ON auto.estado_autorizacion_codigo=e.codigo "
                    + " JOIN usuario usr ON auto.usuario_codigo=usr.codigo"
                    + " JOIN comunidad c ON auto.comunidad_codigo=c.codigo"
                    + "   WHERE auto.codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, auto.getCodigo());
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                autorizacion.setCodigo(rS.getInt(1));
                autorizacion.setFechaautorizacion(rS.getDate(3));
                autorizacion.setFechaRealIngreso(rS.getTimestamp(4));
                autorizacion.setDescripcion(rS.getString(5));
                autorizacion.setPersonaIngresa(rS.getString(6));
                autorizacion.setDocumentoPersonaIngresa(rS.getString(7));
                autorizacion.setFechaRealSalida(rS.getTimestamp(8));
                autorizacion.setEmpresaContratista(rS.getString(9));
                Usuario usr = new Usuario();
                usr.setCodigo(rS.getInt(2));
                usr.setNombres(rS.getString(10));
                usr.setApellidos(rS.getString(11));
                autorizacion.setUsuarioCodigo(usr);
                EstadoAutorizacion estado = new EstadoAutorizacion(rS.getInt(12));
                estado.setNombre(rS.getString(13));
                autorizacion.setEstado(estado);
                MotivoAutorizacion motivo = new MotivoAutorizacion(rS.getInt(14));
                motivo.setNombre(rS.getString(15));
                autorizacion.setMotivo(motivo);
            }
            pS.close();
        } catch (ClassNotFoundException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getObject");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getObject");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getObject");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return autorizacion;
    }

    @Override
    public List getListObject(Object obj) {
        Autorizacion auto = (Autorizacion) obj;
        ArrayList<Autorizacion> autorizaciones = new ArrayList();
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();

            String sql = "SELECT auto.codigo,auto.usuario_codigo,auto.fecha_autorizacion,"
                    + " auto.fecha_real_ingreso,"
                    + " auto.persona_ingresa,auto.documento_persona_ingresa,auto.fecha_real_salida,"
                    + " auto.empresa_contratista, usr.nombres,usr.apellidos,"
                    + " e.codigo,e.nombre,m.codigo,m.nombre  "
                    + " FROM autorizacion auto "
                    + " JOIN motivo_autorizacion m ON auto.motivo_autorizacion_codigo=m.codigo "
                    + " JOIN estado_autorizacion e ON auto.estado_autorizacion_codigo=e.codigo "
                    + " JOIN usuario usr ON auto.usuario_codigo=usr.codigo"
                    + " JOIN comunidad c ON auto.comunidad_codigo=c.codigo"
                    + "   WHERE " + ((auto.getComunidadcodigo() != null) ? "auto.comunidad_codigo=" + auto.getComunidadcodigo().getCodigo() + " and " : "") + " "
                    + ((auto.getEstado() != null) ? "e.nombre='" + auto.getEstado().getNombre() + "'" : "") + " "
                    + " Limit " + auto.getRango();
            PreparedStatement pS = con.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Autorizacion autorizacion = new Autorizacion();
                autorizacion.setCodigo(rS.getInt(1));
                autorizacion.setFechaautorizacion(rS.getDate(3));
                autorizacion.setFechaRealIngreso(rS.getTimestamp(4));
                autorizacion.setPersonaIngresa(rS.getString(5));
                autorizacion.setDocumentoPersonaIngresa(rS.getString(6));
                autorizacion.setFechaRealSalida(rS.getTimestamp(7));
                autorizacion.setEmpresaContratista(rS.getString(8));
                Usuario usr = new Usuario();
                usr.setCodigo(rS.getInt(2));
                usr.setNombres(rS.getString(9));
                usr.setApellidos(rS.getString(10));
                autorizacion.setUsuarioCodigo(usr);
                EstadoAutorizacion estado = new EstadoAutorizacion(rS.getInt(11));
                estado.setNombre(rS.getString(12));
                autorizacion.setEstado(estado);
                MotivoAutorizacion motivo = new MotivoAutorizacion(rS.getInt(13));
                motivo.setNombre(rS.getString(14));
                autorizacion.setMotivo(motivo);
                autorizaciones.add(autorizacion);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListObject");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListObject");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListObject");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return autorizaciones;
    }

    @Override
    public int updateObject(Object object) {
        Autorizacion auto = (Autorizacion) object;
        Connection con = null;
        EstructuraFachada estFach = new EstructuraFachada();
        Estructura est = (Estructura) estFach.getObject(new Estructura("autorizacionEstadoInicial"));
        Estructura est2 = (Estructura) estFach.getObject(new Estructura("autorizacionEstadoEspera"));
        Estructura est3 = (Estructura) estFach.getObject(new Estructura("autorizacionEstadoFinaliza"));
        Estructura est4 = (Estructura) estFach.getObject(new Estructura("autorizacionEstadoAprobado"));
        int numfilas = 0;
        try {
            con = ConexionBD.obtenerConexion();
            if (auto.getEstado().getCodigo() == Integer.parseInt(est.getValor())) {
                String sql = "UPDATE autorizacion SET persona_ingresa=?, "
                        + " " + (!auto.getDocumentoPersonaIngresa().equals('0') ? "documento_persona_ingresa='" + auto.getDocumentoPersonaIngresa() + "'," : "") + ""
                        + " " + (!auto.getEmpresaContratista().equals("") ? "empresa_contratista='" + auto.getEmpresaContratista() + "'," : "") + ""
                        + " " + (auto.getDescripcion() != null ? "descripcion='" + auto.getDescripcion()+ "'," : "descripcion=null,") + ""
                        + " estado_autorizacion_codigo=?,"
                        + " fecha_autorizacion=?,"
                        + " motivo_autorizacion_codigo=?"
                        + " WHERE codigo=?";
                PreparedStatement pS = con.prepareStatement(sql);
                pS.setString(1, auto.getPersonaIngresa());
                pS.setInt(2, auto.getEstado().getCodigo());
                pS.setString(3, auto.getFechaautorizacion().toString());
                pS.setInt(4, auto.getMotivo().getCodigo());
                pS.setInt(5, auto.getCodigo());
                numfilas = pS.executeUpdate();
                pS.close();
            }
            if (auto.getEstado().getCodigo() == Integer.parseInt(est2.getValor())) {
                String sql = "UPDATE autorizacion SET fecha_real_ingreso=now(),estado_autorizacion_codigo=?"
                        + " WHERE codigo=?";
                PreparedStatement pS = con.prepareStatement(sql);
                pS.setInt(1, auto.getEstado().getCodigo());
                pS.setInt(2, auto.getCodigo());
                numfilas = pS.executeUpdate();
                pS.close();
            }
            if (auto.getEstado().getCodigo() == Integer.parseInt(est3.getValor())) {
                String sql = "UPDATE autorizacion SET fecha_real_salida=now(),estado_autorizacion_codigo=?"
                        + " WHERE codigo=?";
                PreparedStatement pS = con.prepareStatement(sql);
                pS.setInt(1, auto.getEstado().getCodigo());
                pS.setInt(2, auto.getCodigo());
                numfilas = pS.executeUpdate();
                pS.close();
            }
            if (auto.getEstado().getCodigo() == Integer.parseInt(est4.getValor())) {
                String sql = "UPDATE autorizacion SET estado_autorizacion_codigo=?"
                        + " WHERE codigo=?";
                PreparedStatement pS = con.prepareStatement(sql);
                pS.setInt(1, auto.getEstado().getCodigo());
                pS.setInt(2, auto.getCodigo());
                numfilas = pS.executeUpdate();
                pS.close();
            }
        } catch (ClassNotFoundException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("updateObject");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("updateObject");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
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
        Autorizacion auto = (Autorizacion) object;
        Connection con = null;
        int result = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "INSERT INTO autorizacion( usuario_codigo, fecha_autorizacion,"
                    + " persona_ingresa, documento_persona_ingresa,empresa_contratista,descripcion,"
                    + " estado_autorizacion_codigo, motivo_autorizacion_codigo,comunidad_codigo)"
                    + " VALUES (?,?,?,?,?,?,?,?,?)";
            try (PreparedStatement pS = con.prepareStatement(sql)) {
                pS.setInt(1, auto.getUsuarioCodigo().getCodigo());
                pS.setDate(2, auto.getFechaautorizacion());
                pS.setString(3, auto.getPersonaIngresa());
                pS.setString(4, auto.getDocumentoPersonaIngresa());
                pS.setString(5, auto.getEmpresaContratista());
                pS.setString(6, auto.getDescripcion());
                pS.setInt(7, auto.getEstado().getCodigo());
                pS.setInt(8, auto.getMotivo().getCodigo());
                pS.setInt(9, auto.getComunidadcodigo().getCodigo());
                result = pS.executeUpdate();
                pS.close();
            }
        } catch (ClassNotFoundException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("insertObject");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("insertObject");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
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
            String sql = "SELECT COUNT('codigo') FROM autorizacion WHERE "+((condicionPaginado.getUser() != null) ? " usuario_codigo=" + condicionPaginado.getUser().getCodigo()+" and" : "")+" comunidad_codigo=? "
                    + "" + ((condicionPaginado.getEstado() != null) ? " and estados_codigo=" + condicionPaginado.getEstado() : "") + " ";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, condicionPaginado.getComunidad().getCodigo());
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                cont = rS.getInt(1);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getCount");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getCount");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
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
        Autorizacion auto = (Autorizacion) object;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "DELETE FROM autorizacion WHERE codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, auto.getCodigo());
            pS.execute();
            pS.close();
        } catch (ClassNotFoundException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("deleteObject");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("deleteObject");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
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
        Autorizacion auto = (Autorizacion) object;
        ArrayList<Autorizacion> autorizaciones = new ArrayList<Autorizacion>();
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String query = "SELECT auto.codigo,auto.usuario_codigo,auto.fecha_autorizacion,"
                    + " auto.fecha_real_ingreso,"
                    + " auto.persona_ingresa,auto.documento_persona_ingresa,auto.fecha_real_salida,"
                    + " auto.empresa_contratista, usr.nombres,usr.apellidos,"
                    + " e.codigo,e.nombre,m.codigo,m.nombre,i.codigo,i.ubicacion  "
                    + " FROM autorizacion auto "
                    + " JOIN motivo_autorizacion m ON auto.motivo_autorizacion_codigo=m.codigo "
                    + " JOIN estado_autorizacion e ON auto.estado_autorizacion_codigo=e.codigo "
                    + " JOIN usuario usr ON auto.usuario_codigo=usr.codigo"
                    + " JOIN comunidad c ON auto.comunidad_codigo=c.codigo"
                    + " JOIN inmueble i ON usr.codigo=i.usuario_codigo"
                    + " WHERE "+ ((auto.getUsuarioCodigo()!= null) ? "usr.codigo=" + auto.getUsuarioCodigo().getCodigo()+" AND " : "") +"(auto.fecha_autorizacion LIKE ? OR usr.nombres LIKE ?"
                    + " OR usr.apellidos LIKE ? OR auto.persona_ingresa LIKE ?"
                    + " OR auto.documento_persona_ingresa LIKE ?"
                    + " OR i.ubicacion LIKE ? OR e.nombre LIKE ? OR m.nombre LIKE ?)"
                    + " and auto.COMUNIDAD_CODIGO = ?"
                    + " ORDER BY auto.fecha_autorizacion ASC "+ ((auto.getRango() != null) ? "LIMIT " + auto.getRango() + " " : "")+ "";
            PreparedStatement pS = con.prepareStatement(query);
            pS.setString(1, "%" + auto.getBusqueda() + "%");
            pS.setString(2, "%" + auto.getBusqueda() + "%");
            pS.setString(3, "%" + auto.getBusqueda() + "%");
            pS.setString(4, "%" + auto.getBusqueda() + "%");
            pS.setString(5, "%" + auto.getBusqueda() + "%");
            pS.setString(6, "%" + auto.getBusqueda() + "%");
            pS.setString(7, "%" + auto.getBusqueda() + "%");
            pS.setString(8, "%" + auto.getBusqueda() + "%");
            pS.setInt(9, auto.getComunidadcodigo().getCodigo());
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Autorizacion autorizacion = new Autorizacion();
                autorizacion.setCodigo(rS.getInt(1));
                autorizacion.setFechaautorizacion(rS.getDate(3));
                autorizacion.setFechaRealIngreso(rS.getTimestamp(4));
                autorizacion.setPersonaIngresa(rS.getString(5));
                autorizacion.setDocumentoPersonaIngresa(rS.getString(6));
                autorizacion.setFechaRealSalida(rS.getTimestamp(7));
                autorizacion.setEmpresaContratista(rS.getString(8));
                Usuario usr = new Usuario();
                usr.setCodigo(rS.getInt(2));
                usr.setNombres(rS.getString(9));
                usr.setApellidos(rS.getString(10));
                usr.setInmueble(new Inmueble(rS.getInt(15), rS.getString(16)));
                autorizacion.setUsuarioCodigo(usr);
                EstadoAutorizacion estado = new EstadoAutorizacion(rS.getInt(11));
                estado.setNombre(rS.getString(12));
                autorizacion.setEstado(estado);
                MotivoAutorizacion motivo = new MotivoAutorizacion(rS.getInt(13));
                motivo.setNombre(rS.getString(14));
                autorizacion.setMotivo(motivo);
                autorizaciones.add(autorizacion);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListByCondition");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListByCondition");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListByCondition");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return autorizaciones;
    }

    @Override
    public List getListByPagination(Object object) {
        Connection con = null;
        Autorizacion auto = (Autorizacion) object;
        List<Autorizacion> autorizaciones = new ArrayList();
        try {
            con = ConexionBD.obtenerConexion();

            String sql = "SELECT auto.codigo,auto.usuario_codigo,auto.fecha_autorizacion,"
                    + " auto.fecha_real_ingreso,"
                    + " auto.persona_ingresa,auto.documento_persona_ingresa,auto.fecha_real_salida,"
                    + " auto.empresa_contratista, usr.nombres,usr.apellidos,i.codigo,i.ubicacion,"
                    + " e.codigo,e.nombre,m.codigo,m.nombre  "
                    + " FROM autorizacion auto "
                    + " JOIN motivo_autorizacion m ON auto.motivo_autorizacion_codigo=m.codigo "
                    + " JOIN estado_autorizacion e ON auto.estado_autorizacion_codigo=e.codigo "
                    + " JOIN usuario usr ON auto.usuario_codigo=usr.codigo"
                    + " JOIN comunidad c ON auto.comunidad_codigo=c.codigo"
                    + " JOIN inmueble i ON usr.codigo=i.usuario_codigo"
                    + " " + ((auto.getComunidadcodigo() != null || auto.getEstado() != null) ? " WHERE " : "") + "" + ((auto.getComunidadcodigo() != null) ? "auto.comunidad_codigo=" + auto.getComunidadcodigo().getCodigo() + " " : "") + " "
                    + ((auto.getEstado() != null) ? " and e.codigo=" + auto.getEstado().getCodigo() + "" : "") + " "
                    + ((auto.getUsuarioCodigo() != null) ? " and usr.codigo=" + auto.getUsuarioCodigo().getCodigo() + "" : "") + " "
                    + "  ORDER BY auto.fecha_autorizacion ASC "
                    + " Limit " + auto.getRango();
            PreparedStatement pS = con.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Autorizacion autorizacion = new Autorizacion();
                autorizacion.setCodigo(rS.getInt(1));
                autorizacion.setFechaautorizacion(rS.getDate(3));
                autorizacion.setFechaRealIngreso(rS.getTimestamp(4));
                autorizacion.setPersonaIngresa(rS.getString(5));
                autorizacion.setDocumentoPersonaIngresa(rS.getString(6));
                autorizacion.setFechaRealSalida(rS.getTimestamp(7));
                autorizacion.setEmpresaContratista(rS.getString(8));
                Usuario usr = new Usuario();
                usr.setCodigo(rS.getInt(2));
                usr.setNombres(rS.getString(9));
                usr.setApellidos(rS.getString(10));
                usr.setInmueble(new Inmueble(rS.getInt(11), rS.getString(12)));
                autorizacion.setUsuarioCodigo(usr);
                EstadoAutorizacion estado = new EstadoAutorizacion(rS.getInt(13));
                estado.setNombre(rS.getString(14));
                autorizacion.setEstado(estado);
                MotivoAutorizacion motivo = new MotivoAutorizacion(rS.getInt(15));
                motivo.setNombre(rS.getString(16));
                autorizacion.setMotivo(motivo);
                autorizaciones.add(autorizacion);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListByPagination");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListByPagination");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListByPagination");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return autorizaciones;
    }

    private synchronized int getMaxCodigo() {
        Connection con = null;
        int cont = 1;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "SELECT MAX(codigo)+1 codigo FROM autorizacion ";
            PreparedStatement pS = con.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                int max = rS.getInt(1);
                cont = ((max > 0) ? max : cont);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getMaxCodigo");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getMaxCodigo");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
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
