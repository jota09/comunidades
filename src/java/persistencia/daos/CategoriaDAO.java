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
import persistencia.entidades.Categoria;

/**
 *
 * @author ferney.medina
 */
public class CategoriaDAO implements GestionDAO{

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
        ArrayList<Categoria> listCategoria=new ArrayList<Categoria>();                
        Connection con=null;
        try
        {
            con=ConexionBD.obtenerConexion();
            String query="SELECT * FROM categoria WHERE activo=1 and codigo_padre is null";
            PreparedStatement pS=con.prepareStatement(query);
            ResultSet rS=pS.executeQuery();            
            while(rS.next()){
                Categoria cat=new Categoria();
                cat.setCodigo(rS.getInt("codigo"));
                cat.setCodigoPadre(rS.getInt("codigo_padre"));
                cat.setNombre(rS.getString("nombre"));
                cat.setListaCategorias(getHijos(cat.getCodigo()));
                listCategoria.add(cat);
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
        return listCategoria;
    }   

    private List<Categoria> getHijos(int codMenu){                
        ArrayList<Categoria> listaCategorias=new ArrayList<Categoria>();
        Connection con=null;
        try{
            con=ConexionBD.obtenerConexion();
            String sql="SELECT * FROM categoria where codigo_padre=? and codigo_padre is not null";
            PreparedStatement pS=con.prepareStatement(sql);
            pS.setInt(1, codMenu);
            ResultSet rS=pS.executeQuery();
            while(rS.next())
            {                
                Categoria cat=new Categoria();
                cat.setCodigo(rS.getInt("codigo"));
                cat.setCodigoPadre(rS.getInt("codigo_padre"));
                cat.setNombre(rS.getString("nombre"));
                cat.setListaCategorias(getHijos(cat.getCodigo()));
                listaCategorias.add(cat);
            }
            rS.close();
            pS.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaCategorias;
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
}
