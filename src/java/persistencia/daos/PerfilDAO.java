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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.conexion.ConexionBD;
import persistencia.entidades.Perfil;

/**
 *
 * @author ferney.medina
 */
public class PerfilDAO implements GestionDAO{

    @Override
    public Object getObject(Object object) {
        Perfil perfil=(Perfil)object;
        Connection con=null;
        try{
            con=ConexionBD.obtenerConexion();
            String query="SELECT * FROM perfil WHERE codigo=?";
            PreparedStatement pS=con.prepareStatement(query);
            pS.setInt(1,perfil.getCodigo());
            ResultSet rS=pS.executeQuery();
            if(rS.next()){
                perfil.setCodigo(rS.getInt("codigo"));
                perfil.setNombre(rS.getString("nombre"));
            }
            rS.close();
            pS.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
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
}
