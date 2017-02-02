/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachada;

import java.util.List;
import persistencia.daos.GestionDAO;
import persistencia.daos.RecursoDAO;

/**
 *
 * @author manuel.alcala
 */
public class RecursoFachada implements GestionFachada {

    public List getListObject(Object object) {
        GestionDAO recursoDAO = new RecursoDAO();
        return recursoDAO.getListObject(object);
    }

    @Override
    public Object getObject(Object object) {
        GestionDAO recursoDAO = new RecursoDAO();
        return recursoDAO.getObject(object);
    }

    @Override
    public int insertObject(Object object) {
        GestionDAO recursoDAO = new RecursoDAO();
        return recursoDAO.insertObject(object);
    }

    @Override
    public int updateObject(Object obj) {
        GestionDAO recursoDAO = new RecursoDAO();
        return recursoDAO.updateObject(obj);
    }

    @Override
    public int getCount(Object obj) {
        GestionDAO recursoDAO = new RecursoDAO();
        return recursoDAO.getCount(obj);
    }

    @Override
    public void deleteObject(Object object) {
        GestionDAO recursoDAO = new RecursoDAO();
        recursoDAO.deleteObject(object);
    }

    @Override
    public List getListObject() {
        GestionDAO recursoDAO = new RecursoDAO();
        return recursoDAO.getListObject();
    }

    @Override
    public List getListByCondition(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListByPagination(Object object) {
        GestionDAO recursoDAO = new RecursoDAO();
        return recursoDAO.getListByPagination(object);
    }

}
