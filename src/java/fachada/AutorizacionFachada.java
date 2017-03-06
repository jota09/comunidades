/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachada;

import java.util.List;
import persistencia.daos.ArticuloDAO;
import persistencia.daos.AutorizacionDAO;
import persistencia.daos.GestionDAO;

/**
 *
 * @author Jesus.Ramos
 */
public class AutorizacionFachada implements GestionFachada {

    @Override
    public int getCount(Object object) {
        GestionDAO autoDAO = new AutorizacionDAO();
        return autoDAO.getCount(object);
    }

    @Override
    public Object getObject(Object object) {
        GestionDAO autoDAO = new AutorizacionDAO();
        return autoDAO.getObject(object);
    }

    @Override
    public int insertObject(Object object) {
        GestionDAO autoDAO = new AutorizacionDAO();
        return autoDAO.insertObject(object);
    }

    @Override
    public List getListObject(Object object) {
        GestionDAO autoDAO = new AutorizacionDAO();
        return autoDAO.getListObject(object);
    }

    @Override
    public List getListObject() {
        GestionDAO autoDAO = new AutorizacionDAO();
        return autoDAO.getListObject();
    }

    @Override
    public int updateObject(Object object) {
        GestionDAO autoDAO = new AutorizacionDAO();
        return autoDAO.updateObject(object);
    }

    @Override
    public void deleteObject(Object object) {
        GestionDAO autoDAO = new AutorizacionDAO();
        autoDAO.deleteObject(object);
    }

    @Override
    public List getListByCondition(Object object) {
        GestionDAO autoDAO = new AutorizacionDAO();
        return autoDAO.getListByCondition(object);
    }

    @Override
    public List getListByPagination(Object object) {
        GestionDAO autoDAO = new AutorizacionDAO();
        return autoDAO.getListByPagination(object);
    }

}
