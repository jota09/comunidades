/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachada;

import java.util.List;
import persistencia.daos.GestionDAO;
import persistencia.daos.MenuDAO;

/**
 *
 * @author ferney.medina
 */
public class MenuFachada implements GestionFachada {

    @Override
    public List getListObject(Object object) {
        GestionDAO menDAO = new MenuDAO();
        return menDAO.getListObject(object);
    }

    @Override
    public Object getObject(Object object) {
        GestionDAO menDAO = new MenuDAO();
        return menDAO.getObject(object);
    }

    @Override
    public int insertObject(Object object) {
        GestionDAO menDAO = new MenuDAO();
        return menDAO.insertObject(object);
    }

    @Override
    public int updateObject(Object obj) {
        GestionDAO menDAO = new MenuDAO();
        return menDAO.updateObject(obj);
    }

    @Override
    public int getCount(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteObject(Object object) {
        GestionDAO menDAO = new MenuDAO();
        menDAO.deleteObject(object);
    }

    @Override
    public List getListObject() {
        GestionDAO menDAO = new MenuDAO();
        return menDAO.getListObject();
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
