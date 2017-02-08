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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
