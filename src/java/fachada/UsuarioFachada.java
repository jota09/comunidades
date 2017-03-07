/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachada;

import java.util.List;
import persistencia.daos.GestionDAO;
import persistencia.daos.UsuarioDAO;

/**
 *
 * @author ferney.medina
 */
public class UsuarioFachada implements GestionFachada {
    
    public Object getObject(Object obj) {
        GestionDAO usr = new UsuarioDAO();
        return usr.getObject(obj);
    }
    
    @Override
    public int insertObject(Object object) {
        GestionDAO userDAO = new UsuarioDAO();
        return userDAO.insertObject(object);
    }
    
    @Override
    public List getListObject(Object object) {
        GestionDAO userDAO = new UsuarioDAO();
        return userDAO.getListObject(object);
    }
    
    @Override
    public int updateObject(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int getCount(Object obj) {
        GestionDAO userDAO = new UsuarioDAO();
        return userDAO.getCount(obj);
    }
    
    @Override
    public void deleteObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List getListObject() {
        GestionDAO userDAO = new UsuarioDAO();
        return userDAO.getListObject();
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
