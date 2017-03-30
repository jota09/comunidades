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
import persistencia.entidades.LogXProceso;
import persistencia.entidades.Proceso;

/**
 *
 * @author manuel.alcala
 */
public class LogXProcesoDAO implements GestionDAO {

    @Override
    public int getCount(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListObject(Object object) {
        Proceso proceso = (Proceso) object;
        Connection con = null;
        List<LogXProceso> logs = new ArrayList();
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "select codigo,descripcion,fecha from log_x_proceso where proceso_codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, proceso.getCodigo());
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                LogXProceso log = new LogXProceso();
                log.setCodigo(rS.getInt(1));
                log.setDescripcion(rS.getString(2));
                log.setFecha(rS.getTimestamp(3));
                logs.add(log);
            }
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LogXProcesoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LogXProcesoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LogXProcesoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return logs;

    }

    @Override
    public List getListObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertObject(Object object) {
        Connection con = null;
        LogXProceso log = (LogXProceso) object;
        int tam = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "insert into log_x_proceso(descripcion,proceso_codigo,fecha) values(?,?,now())";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setString(1, log.getDescripcion());
            pS.setInt(2, log.getProceso().getCodigo());
            tam = pS.executeUpdate();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LogXProcesoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LogXProcesoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LogXProcesoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return tam;
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
