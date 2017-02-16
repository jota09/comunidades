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
import persistencia.entidades.Menu;
import persistencia.entidades.Perfil;

/**
 *
 * @author ferney.medina
 */
public class MenuDAO implements GestionDAO {

    @Override
    public Object getObject(Object object) {
        Menu menu = (Menu) object;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select codigo_padre,nombre,url from menu where codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, menu.getCodigo());
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                menu.setCodigoPadre(rS.getInt(1));
                menu.setNombre(rS.getString(2));
                menu.setUrl(rS.getString(3));
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MenuDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MenuDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(MenuDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return menu;
    }

    @Override
    public List getListObject(Object object) {
        Perfil perfil = (Perfil) object;
        ArrayList<Menu> listaMenus = new ArrayList<Menu>();
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "SELECT men.* FROM perfil_menu pfM "
                    + "JOIN menu men ON men.codigo=pfM.menu_codigo where pfM.perfil_codigo=? and men.codigo_padre is null";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, perfil.getCodigo());
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Menu menu = new Menu();
                menu.setCodigo(rS.getInt("codigo"));
                menu.setCodigoPadre(rS.getInt("codigo_padre"));
                menu.setNombre(rS.getString("nombre"));
                menu.setUrl(rS.getString("url"));
                menu.setListaMenu(getHijos(menu.getCodigo(), perfil.getCodigo()));
                listaMenus.add(menu);
            }
            rS.close();
            pS.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaMenus;
    }

    private List<Menu> getHijos(int codMenu, int codPerfil) {
        ArrayList<Menu> listaMenus = new ArrayList<Menu>();
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "SELECT men.* FROM perfil_menu pfM "
                    + "JOIN menu men ON men.codigo=pfM.menu_codigo where pfM.perfil_codigo=? and men.codigo_padre=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, codPerfil);
            pS.setInt(2, codMenu);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Menu menu = new Menu();
                menu.setCodigo(rS.getInt("codigo"));
                menu.setCodigoPadre(rS.getInt("codigo_padre"));
                menu.setNombre(rS.getString("nombre"));
                menu.setUrl(rS.getString("url"));
                menu.setListaMenu(getHijos(menu.getCodigo(), codPerfil));
                listaMenus.add(menu);
            }
            rS.close();
            pS.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaMenus;
    }

    @Override
    public int updateObject(Object object) {
        Menu menu = (Menu) object;
        Connection con = null;
        int update = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "UPDATE menu set nombre=? where codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setString(1, menu.getNombre());
            pS.setInt(2, menu.getCodigo());
            update = pS.executeUpdate();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MenuDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MenuDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return update;
    }

    @Override
    public int insertObject(Object object) {
        Menu menu = (Menu) object;
        Connection con = null;
        int insert = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "INSERT INTO menu(codigo_padre,url,nombre) values(" + ((menu.getCodigoPadre() != 0) ? menu.getCodigoPadre() : null) + ",?,?)";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setString(1, menu.getUrl());
            pS.setString(2, menu.getNombre());
            insert = pS.executeUpdate();
            pS.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MenuDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MenuDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return insert;
    }

    @Override
    public List getListObject() {
        List<Menu> listaMenus = new ArrayList<Menu>();
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "SELECT * from menu where codigo_padre is null";

            PreparedStatement pS = con.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Menu menu = new Menu();
                menu.setCodigo(rS.getInt("codigo"));
                menu.setCodigoPadre(rS.getInt("codigo_padre"));
                menu.setNombre(rS.getString("nombre"));
                menu.setUrl(rS.getString("url"));
                menu.setListaMenu(getHijos(menu.getCodigo()));
                listaMenus.add(menu);
            }
            rS.close();
            pS.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaMenus;
    }

    private List<Menu> getHijos(int codMenu) {
        ArrayList<Menu> listaMenus = new ArrayList<Menu>();
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "SELECT * from menu where codigo_padre=?";

            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, codMenu);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Menu menu = new Menu();
                menu.setCodigo(rS.getInt("codigo"));
                menu.setCodigoPadre(rS.getInt("codigo_padre"));
                menu.setNombre(rS.getString("nombre"));
                menu.setUrl(rS.getString("url"));
                menu.setListaMenu(getHijos(menu.getCodigo()));
                listaMenus.add(menu);
            }
            rS.close();
            pS.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaMenus;
    }

    @Override
    public int getCount(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteObject(Object object) {
        Menu menu = (Menu) object;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "DELETE FROM menu where codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, menu.getCodigo());
            pS.executeUpdate();
            pS.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MenuDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MenuDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
