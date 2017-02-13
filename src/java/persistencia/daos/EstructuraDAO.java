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
import persistencia.entidades.Estructura;

/**
 *
 * @author Jesus.Ramos
 */
public class EstructuraDAO implements GestionDAO {
// Este metodo trae uno especifico...

    @Override
    public Object getObject(Object object) {
        Estructura estruc = (Estructura) object;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String query = "SELECT codigo,referencia,valor,descripcion FROM estructura WHERE referencia=? or codigo=?";
            PreparedStatement pS = con.prepareStatement(query);
            pS.setString(1, estruc.getReferencia());
            pS.setInt(2, estruc.getCodigo());
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                estruc.setCodigo(rS.getInt(1));
                estruc.setReferencia(rS.getString(2));
                estruc.setValor(rS.getString(3));
                estruc.setDescripcion(rS.getString(4));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(EstructuraDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return estruc;
    }

    @Override
    public List getListObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListObject() {
        ArrayList<Estructura> listEstructura = new ArrayList<Estructura>();
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String query = "SELECT codigo,referencia,valor,descripcion FROM estructura";
            PreparedStatement pS = con.prepareStatement(query);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Estructura est = new Estructura(rS.getInt(1), rS.getString(2), rS.getString(3), rS.getString(4));
                listEstructura.add(est);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EstructuraDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EstructuraDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listEstructura;
    }

    @Override
    public int updateObject(Object object) {
        Estructura estructura = (Estructura) object;
        Connection con = null;
        int tamano = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "UPDATE estructura set valor=?,descripcion=? where codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setString(1, estructura.getValor());
            pS.setString(2, estructura.getDescripcion());
            pS.setInt(3, estructura.getCodigo());
            tamano = pS.executeUpdate();
            pS.close();
        } catch (ClassNotFoundException ex) {
            System.out.println("Error:" + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("Error:" + ex.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error:" + ex.getMessage());
            }
        }
        return tamano;
    }

    @Override
    public int insertObject(Object object) {
        Estructura estructura = (Estructura) object;
        Connection con = null;
        int tamano = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Insert into estructura(referencia,valor,descripcion) values(?,?,?)";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setString(1, estructura.getReferencia());
            pS.setString(2, estructura.getValor());
            pS.setString(3, estructura.getDescripcion());
            tamano = pS.executeUpdate();
            pS.close();
        } catch (ClassNotFoundException ex) {
            System.out.println("Error:" + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("Error:" + ex.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error:" + ex.getMessage());
            }
        }
        return tamano;
    }

    @Override
    public int getCount(Object obj) {
        Connection con = null;
        int tamano = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select count(codigo) from estructura";
            PreparedStatement pS = con.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                tamano = rS.getInt(1);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AtributoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AtributoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(AtributoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tamano;
    }

    @Override
    public void deleteObject(Object object) {
        Estructura estructura = (Estructura) object;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Delete from estructura where codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, estructura.getCodigo());
            pS.execute();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EstructuraDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EstructuraDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(EstructuraDAO.class.getName()).log(Level.SEVERE, null, ex);
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
