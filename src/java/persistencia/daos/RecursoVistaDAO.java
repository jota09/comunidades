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
import persistencia.entidades.Recurso;
import persistencia.entidades.RecursoVista;
import persistencia.entidades.Vista;
import persistencia.entidades.Error;
import persistencia.entidades.TipoError;
import utilitarias.CondicionPaginado;
import utilitarias.Utilitaria;

/**
 *
 * @author manuel.alcala
 */
public class RecursoVistaDAO implements GestionDAO {

    @Override
    public int getCount(Object object) {
        CondicionPaginado condicion = (CondicionPaginado) object;
        Connection con = null;
        int tamano = 0;
        try {
            con = ConexionBD.obtenerConexion();
            
            String sql = "select count(rv.codigo) from recurso_vista rv join recurso r on r.codigo=rv.recurso_codigo  join vista v on v.codigo=rv.vista_codigo "+condicion.getCondicion();
            PreparedStatement pS = con.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                tamano = rS.getInt(1);
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
        return tamano;
    }

    @Override
    public Object getObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListObject() {
        Connection con = null;
        List<RecursoVista> recursoVistas = new ArrayList();
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "select r.*,v.*,rv.codigo from recurso_vista rv join recurso r on r.codigo=rv.recurso_codigo "
                    + " join vista v on v.codigo=rv.vista_codigo order by v.nombre";
            PreparedStatement pS = con.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Recurso recurso = new Recurso();
                Vista vista = new Vista();
                RecursoVista recursoVista = new RecursoVista();
                recurso.setCodigo(rS.getInt(1));
                recurso.setNombre(rS.getString(2));
                recurso.setRuta(rS.getString(3));
                recurso.setActivo(rS.getShort(4));
                vista.setCodigo(rS.getInt(5));
                vista.setNombre(rS.getString(6));
                vista.setUrl(rS.getString(7));
                vista.setActivo(rS.getShort(8));
                recursoVista.setRecursoCodigo(recurso);
                recursoVista.setCodigo(rS.getInt(9));
                recursoVista.setVistaCodigo(vista);
                recursoVistas.add(recursoVista);
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
        return recursoVistas;
    }

    @Override
    public int updateObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertObject(Object object) {
        RecursoVista recursoVista = (RecursoVista) object;
        Connection con = null;
        int tamano = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "insert into recurso_vista(recurso_codigo,vista_codigo)values(?,?)";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, recursoVista.getRecursoCodigo().getCodigo());
            pS.setInt(2, recursoVista.getVistaCodigo().getCodigo());
            tamano = pS.executeUpdate();
            pS.close();
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
        return tamano;
    }

    @Override
    public void deleteObject(Object object) {
        RecursoVista recursoVista = (RecursoVista) object;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "delete from recurso_vista where codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, recursoVista.getCodigo());
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListByPagination(Object object) {
        Connection con = null;
        List<RecursoVista> recursoVistas = new ArrayList();
        CondicionPaginado condicion = (CondicionPaginado) object;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "select r.codigo,r.nombre,r.ruta,r.activo,v.codigo,v.nombre,v.url,v.activo,rv.codigo  from recurso_vista rv join recurso r on r.codigo=rv.recurso_codigo "
                    + " join vista v on v.codigo=rv.vista_codigo "+condicion.getCondicion();
            PreparedStatement pS = con.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Recurso recurso = new Recurso();
                Vista vista = new Vista();
                RecursoVista recursoVista = new RecursoVista();
                recurso.setCodigo(rS.getInt(1));
                recurso.setNombre(rS.getString(2));
                recurso.setRuta(rS.getString(3));
                recurso.setActivo(rS.getShort(4));
                vista.setCodigo(rS.getInt(5));
                vista.setNombre(rS.getString(6));
                vista.setUrl(rS.getString(7));
                vista.setActivo(rS.getShort(8));
                recursoVista.setRecursoCodigo(recurso);
                recursoVista.setCodigo(rS.getInt(9));
                recursoVista.setVistaCodigo(vista);
                recursoVistas.add(recursoVista);
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
        return recursoVistas;
    }

}
