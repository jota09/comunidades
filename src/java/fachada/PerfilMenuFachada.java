/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachada;

import java.util.List;
import persistencia.daos.GestionDAO;
import persistencia.daos.PerfilMenuDAO;

/**
 *
 * @author manuel.alcala
 */
public class PerfilMenuFachada implements GestionFachada {
    
    @Override
    public int getCount(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Object getObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int insertObject(Object object) {
         GestionDAO perfilMenuDAO = new PerfilMenuDAO();
         return perfilMenuDAO.insertObject(object);
    }
    
    @Override
    public List getListObject(Object object) {
        GestionDAO perfilMenuDAO = new PerfilMenuDAO();
        return perfilMenuDAO.getListObject(object);
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
    public void deleteObject(Object object) {
        GestionDAO perfilMenuDAO = new PerfilMenuDAO();
        perfilMenuDAO.deleteObject(object);
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
