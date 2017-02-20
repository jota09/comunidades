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

/**
 *
 * @author ferney.medina
 */
public class PerfilDAO implements GestionDAO {

    @Override
    public Object getObject(Object object) {
        Perfil perfil = (Perfil) object;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String query = "SELECT * FROM perfil WHERE codigo=?";
            PreparedStatement pS = con.prepareStatement(query);
            pS.setInt(1, perfil.getCodigo());
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                perfil.setCodigo(rS.getInt("codigo"));
                perfil.setNombre(rS.getString("nombre"));
            }
            rS.close();
            pS.close();
        } catch (Exception e) {
            e.printStackTrace();
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            Logger.getLogger(PerfilDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PerfilDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PerfilDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return perfiles;
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
