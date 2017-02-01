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
public class EstructuraDAO implements GestionDAO{

    @Override
    public Object getObject(Object object) {
        Estructura estruc = (Estructura) object;
        try {
            Connection con=null;
            con=ConexionBD.obtenerConexion();
            String query = "SELECT * FROM estructura WHERE referencia=?";
            PreparedStatement pS=con.prepareStatement(query);
            pS.setString(1, estruc.getReferencia());
            ResultSet rS=pS.executeQuery();
            if(rS.next())
            {
                estruc.setCodigo(rS.getInt("codigo"));
                estruc.setDireccion(rS.getString("direccion"));
                estruc.setDescripcion(rS.getString("descripcion"));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estruc;
    }

    @Override
    public List getListObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List getListObject() {
        ArrayList<Estructura> listEstructura=new ArrayList<Estructura>();                
        Connection con=null;
        try
        {
            con=ConexionBD.obtenerConexion();
            String query="SELECT * FROM prioridad";
            PreparedStatement pS=con.prepareStatement(query);
            ResultSet rS=pS.executeQuery();            
            while(rS.next()){
                Estructura prio=new Estructura(rS.getInt("codigo"), rS.getString("referencia"), rS.getString("direccion"), rS.getString("descripcion"));
                listEstructura.add(prio);
            }
            rS.close();
            pS.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertObject(Object object) {
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

    @Override
    public List getListByCondition(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListByPagination(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
