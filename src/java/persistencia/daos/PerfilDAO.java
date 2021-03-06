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
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.conexion.ConexionBD;
import persistencia.entidades.Comunidad;
import persistencia.entidades.Perfil;
import persistencia.entidades.TipoError;
import utilitarias.Utilitaria;
import persistencia.entidades.Error;
import utilitarias.CondicionPaginado;

/**
 *
 * @author manuel.alcala
 */
public class PerfilDAO implements GestionDAO {

    @Override
    public Object getObject(Object object) {
        Perfil perfil = (Perfil) object;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String query = "SELECT pr.codigo,pr.nombre,com.codigo,com.nombre FROM perfil pr join comunidad com on pr.comunidad_codigo=com.codigo WHERE pr.codigo=? or (pr.comunidad_codigo=? and pr.nombre=?)";
            PreparedStatement pS = con.prepareStatement(query);
            pS.setInt(1, perfil.getCodigo());
            pS.setInt(2, ((perfil.getComunidad() != null) ? perfil.getComunidad().getCodigo() : 0));
            pS.setString(3, perfil.getNombre());
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                perfil.setCodigo(rS.getInt(1));
                perfil.setNombre(rS.getString(2));
                Comunidad comunidad = new Comunidad(rS.getInt(3));
                comunidad.setNombre(rS.getString(4));
                perfil.setComunidad(comunidad);

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
        return perfil;
    }

    @Override
    public List getListObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateObject(Object object) {
        Perfil perfil = (Perfil) object;
        int cont = 0;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "update perfil set nombre=? where codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setString(1, perfil.getNombre());
            pS.setInt(2, perfil.getCodigo());
            cont = pS.executeUpdate();
            pS.close();
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
        return cont;
    }

    @Override
    public int insertObject(Object object) {
        Perfil perfil = (Perfil) object;
        int cont = 0;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "insert into perfil(nombre,comunidad_codigo) values(?,?)";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setString(1, perfil.getNombre());
            pS.setInt(2, perfil.getComunidad().getCodigo());
            cont = pS.executeUpdate();
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
        return cont;

    }

    @Override
    public List getListObject() {
        List<Perfil> perfiles = new ArrayList();
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select p.codigo,p.nombre,c.codigo,c.nombre from perfil p left join comunidad c on p.comunidad_codigo=c.codigo  where p.activo=1 order by p.nombre";
            PreparedStatement pS = con.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Perfil perfil = new Perfil();
                Comunidad comunidad = new Comunidad();
                perfil.setCodigo(rS.getInt(1));
                perfil.setNombre(rS.getString(2));
                comunidad.setCodigo(rS.getInt(3));
                comunidad.setNombre(rS.getString(4));
                perfil.setComunidad(comunidad);
                perfiles.add(perfil);
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
        return perfiles;
    }

    @Override
    public int getCount(Object object) {
        CondicionPaginado condicion = (CondicionPaginado) object;
        Connection con = null;
        int cont = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select count(pf.codigo) from perfil pf join comunidad com on pf.comunidad_codigo=com.codigo where " + condicion.getCondicion();
            PreparedStatement pS = con.prepareStatement(sql);
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
        Perfil perfil = (Perfil) object;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "delete from perfil where codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, perfil.getCodigo());
            pS.executeUpdate();
            pS.close();
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

    }

    @Override
    public List getListByCondition(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListByPagination(Object object) {
        CondicionPaginado condicion = (CondicionPaginado) object;
        Connection con = null;
        List<Perfil> perfiles = new ArrayList();
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select pf.codigo,pf.nombre,com.codigo,com.nombre from perfil pf join comunidad com on pf.comunidad_codigo=com.codigo where " + condicion.getCondicion();
            PreparedStatement pS = con.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Perfil perfil = new Perfil();
                Comunidad comunidad = new Comunidad();
                perfil.setCodigo(rS.getInt(1));
                perfil.setNombre(rS.getString(2));
                comunidad.setCodigo(rS.getInt(3));
                comunidad.setNombre(rS.getString(4));
                perfil.setComunidad(comunidad);
                perfiles.add(perfil);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ErrorDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ErrorDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ErrorDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return perfiles;
    }
}
