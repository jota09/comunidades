/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachada;

import java.util.List;
import persistencia.daos.AtributoDAO;
import persistencia.daos.GestionDAO;

/**
 *
 * @author manuel.alcala
 */
public class AtributoFachada implements GestionFachada {

    @Override
    public List getListObject(Object object) {
        GestionDAO atributoDAO = new AtributoDAO();
        return atributoDAO.getListObject(object);
    }

    @Override
    public Object getObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertObject(Object object) {
        GestionDAO atributoDAO = new AtributoDAO();
        return atributoDAO.insertObject(object);
    }

    @Override
    public int updateObject(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getCount(Object obj) {
        GestionDAO atributoDAO = new AtributoDAO();
        return atributoDAO.getCount(obj);
    }

    @Override
    public void deleteObject(Object object) {
        GestionDAO atributoDAO = new AtributoDAO();
        atributoDAO.deleteObject(object);
    }

    @Override
    public List getListObject() {
        GestionDAO atributoDAO = new AtributoDAO();
        return atributoDAO.getListObject();
    }

    @Override
    public List getListByCondition(Object object) {
        GestionDAO atributoDAO = new AtributoDAO();
        return atributoDAO.getListByCondition(object);
    }

    @Override
    public List getListByPagination(Object object) {
        GestionDAO atributoDAO = new AtributoDAO();
        return atributoDAO.getListByPagination(object);
    }

}
