/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachada;

import java.util.List;
import persistencia.daos.GestionDAO;
import persistencia.daos.PerfilDAO;

/**
 *
 * @author ferney.medina
 */
public class PerfilFachada implements GestionFachada {
    
    @Override
    public Object getObject(Object object) {
        GestionDAO perfil = new PerfilDAO();
        return perfil.getObject(object);
    }
    
    @Override
    public int insertObject(Object object) {
        GestionDAO perfil = new PerfilDAO();
        return perfil.insertObject(object);
    }
    
    @Override
    public int updateObject(Object obj) {
        GestionDAO perfil = new PerfilDAO();
        return perfil.updateObject(obj);
    }
    
    @Override
    public List getListObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int getCount(Object obj) {
        GestionDAO perfil = new PerfilDAO();
        return perfil.getCount(obj);
    }
    
    @Override
    public void deleteObject(Object object) {
        GestionDAO perfil = new PerfilDAO();
        perfil.deleteObject(object);
    }
    
    @Override
    public List getListObject() {
        GestionDAO perfil = new PerfilDAO();
        return perfil.getListObject();
    }
    
    @Override
    public List getListByCondition(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List getListByPagination(Object object) {
        GestionDAO perfil = new PerfilDAO();
        return perfil.getListByPagination(object);
    }
    
}
