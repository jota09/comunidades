/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.conexion.ConexionBD;
import persistencia.entidades.SeguridadUsuario;
import persistencia.entidades.Error;
import persistencia.entidades.TipoError;
import utilitarias.Utilitaria;

/**
 *
 * @author ferney.medina
 */
public class SeguridadDAO implements GestionDAO {

    @Override
    public Object getObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateObject(Object object) {
        SeguridadUsuario sgdadUsr = (SeguridadUsuario) object;
        String sql = "UPDATE seguridad_usuario SET ip_ultima_sesion=?, fecha_ultima_sesion=now() WHERE codigo=? ";
        int resultado = 0;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setString(1, sgdadUsr.getIpUltimaSesion());
            pS.setInt(2, sgdadUsr.getCodigo());
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
        return resultado;
    }

    @Override
    public int insertObject(Object object) {
        Connection con = null;
        SeguridadUsuario seguridad = (SeguridadUsuario) object;
        int tam = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "insert into seguridad_usuario(contrasena,usuario_codigo,fecha_ultima_sesion,ip_ultima_sesion) values(?,?,now(),?)";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setString(1, seguridad.getContrasena());
            pS.setInt(2, seguridad.getUsuarioCodigo());
            pS.setString(3, seguridad.getIpUltimaSesion());
            tam = pS.executeUpdate();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SeguridadDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SeguridadDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SeguridadDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return tam;
    }

    @Override
    public List getListObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getCount(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListByCondition(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListByPagination(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
