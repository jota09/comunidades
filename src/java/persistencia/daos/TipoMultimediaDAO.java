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
import persistencia.entidades.TipoMultimedia;

/**
 *
 * @author ferney.medina
 */
public class TipoMultimediaDAO implements GestionDAO{

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
        TipoMultimedia tpMult = (TipoMultimedia) object;
        ArrayList<TipoMultimedia> listTpMult = new ArrayList<TipoMultimedia>();
        try {
            Connection con=null;
            con=ConexionBD.obtenerConexion();
            String query = "SELECT * FROM tipo_multimedia WHERE nombre=?";
            PreparedStatement pS=con.prepareStatement(query);
            pS.setString(1, tpMult.getNombre());
            ResultSet rS=pS.executeQuery();
            while(rS.next())
            {
                tpMult.setCodigo(rS.getInt("codigo"));
                tpMult.setExtension(rS.getString("extension"));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listTpMult;        
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
    
}
