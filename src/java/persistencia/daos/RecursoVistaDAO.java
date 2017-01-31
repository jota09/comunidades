/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.conexion.ConexionBD;
import persistencia.entidades.Recurso;
import persistencia.entidades.RecursoVista;
import persistencia.entidades.Vista;

/**
 *
 * @author manuel.alcala
 */
public class RecursoVistaDAO implements GestionDAO {

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
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(RecursoVistaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
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

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RecursoVistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RecursoVistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(RecursoVistaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            Logger.getLogger(RecursoVistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RecursoVistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(RecursoVistaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

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
