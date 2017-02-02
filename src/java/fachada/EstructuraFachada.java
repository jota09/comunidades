/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachada;

import java.util.List;
import persistencia.daos.EstructuraDAO;
import persistencia.daos.GestionDAO;

/**
 *
 * @author Jesus.Ramos
 */
public class EstructuraFachada implements GestionFachada {

    @Override
    public int insertObject(Object object) {
        GestionDAO artDAO = new EstructuraDAO();
        return artDAO.insertObject(object);
    }

    @Override
    public List getListObject(Object obj) {
        GestionDAO artDAO = new EstructuraDAO();
        return artDAO.getListObject(obj);
    }

    @Override
    public Object getObject(Object object) {
        GestionDAO estrucDAO = new EstructuraDAO();
        return estrucDAO.getObject(object);
    }

    @Override
    public int updateObject(Object obj) {
        GestionDAO artDAO = new EstructuraDAO();
        return artDAO.updateObject(obj);
    }

    @Override
    public int getCount(Object obj) {
        GestionDAO artDAO = new EstructuraDAO();
        return artDAO.getCount(obj);
    }

    @Override
    public void deleteObject(Object obj) {
        GestionDAO artDAO = new EstructuraDAO();
        artDAO.deleteObject(obj);
    }

    @Override
    public List getListObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListByCondition(Object object) {
        GestionDAO artDAO = new EstructuraDAO();
        return artDAO.getListByCondition(object);
    }

    @Override
    public List getListByPagination(Object object) {
        GestionDAO artDAO = new EstructuraDAO();
        return artDAO.getListByPagination(object);
    }

}
