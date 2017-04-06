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
import persistencia.entidades.CondicionPaginacion;
import persistencia.entidades.Vista;

/**
 *
 * @author manuel.alcala
 */
public class CondicionPaginacionDAO implements GestionDAO {

    @Override
    public int getCount(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getObject(Object object) {
        Connection con = null;
        CondicionPaginacion condicion = (CondicionPaginacion) object;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "select codigo,condicion,activa_usuario,activa_comunidad from condicion_paginacion where codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, condicion.getCodigo());
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                condicion.setCodigo(rS.getInt(1));
                condicion.setCondicion(rS.getString(2));
                condicion.setActivaUsuario(rS.getShort(3));
                condicion.setActivaComunidad(rS.getShort(4));
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CondicionPaginacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CondicionPaginacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CondicionPaginacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return condicion;
    }

    @Override
    public List getListObject(Object object) {
        Vista vista = (Vista) object;
        Connection con = null;
        List<CondicionPaginacion> condiciones = new ArrayList<CondicionPaginacion>();
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "select codigo,condicion from condicion_paginacion where vista_codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, vista.getCodigo());
            System.out.println("QUERY CONDICIONES PAGINACION:" + pS);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                CondicionPaginacion condicion = new CondicionPaginacion();
                condicion.setCodigo(rS.getInt(1));
                condicion.setCondicion(rS.getString(2));
                condiciones.add(condicion);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CondicionPaginacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CondicionPaginacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CondicionPaginacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return condiciones;
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
