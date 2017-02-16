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
import persistencia.entidades.Menu;
import persistencia.entidades.Perfil;
import persistencia.entidades.PerfilMenu;

/**
 *
 * @author manuel.alcala
 */
public class PerfilMenuDAO implements GestionDAO {

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
        Perfil perfil = (Perfil) object;
        List<Menu> menus = new ArrayList();
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select menu_codigo from perfil_menu where perfil_codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, perfil.getCodigo());
            ResultSet rS = pS.executeQuery();

            while (rS.next()) {
                Menu menu = new Menu();
                menu.setCodigo(rS.getInt(1));
                menus.add(menu);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PerfilMenuDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PerfilMenuDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PerfilMenuDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(PerfilMenuDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return menus;
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
        PerfilMenu perfilMenu = (PerfilMenu) object;
        Connection con = null;
        int insert = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "INSERT INTO perfil_menu(perfil_codigo,menu_codigo) values(?,?)";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, perfilMenu.getPerfilCodigo());
            pS.setInt(2, perfilMenu.getMenuCodigo());
            insert = pS.executeUpdate();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PerfilMenuDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PerfilMenuDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PerfilMenuDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(PerfilMenuDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return insert;
    }

    @Override
    public void deleteObject(Object object) {
        Perfil perfil = (Perfil) object;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "DELETE FROM perfil_menu where perfil_codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, perfil.getCodigo());
            pS.executeUpdate();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PerfilMenuDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PerfilMenuDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PerfilMenuDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(PerfilMenuDAO.class.getName()).log(Level.SEVERE, null, ex);
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
