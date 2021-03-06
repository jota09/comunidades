/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachada;

import java.util.List;
import persistencia.daos.ArticuloDAO;
import persistencia.daos.GestionDAO;

/**
 *
 * @author ferney.medina
 */
public class ArticuloFachada implements GestionFachada {

    @Override
    public int insertObject(Object object) {
        GestionDAO artDAO = new ArticuloDAO();
        return artDAO.insertObject(object);
    }

    @Override
    public List getListObject(Object obj) {
        GestionDAO artDAO = new ArticuloDAO();
        return artDAO.getListObject(obj);
    }

    @Override
    public Object getObject(Object object) {
        GestionDAO artDAO = new ArticuloDAO();
        return artDAO.getObject(object);
    }

    @Override
    public int updateObject(Object obj) {
        GestionDAO artDAO = new ArticuloDAO();
        return artDAO.updateObject(obj);
    }

    @Override
    public int getCount(Object obj) {
        GestionDAO artDAO = new ArticuloDAO();
        return artDAO.getCount(obj);
    }

    @Override
    public void deleteObject(Object obj) {
        GestionDAO artDAO = new ArticuloDAO();
        artDAO.deleteObject(obj);
    }

    @Override
    public List getListObject() {
        GestionDAO artDAO = new ArticuloDAO();
        return artDAO.getListObject();
    }

    @Override
    public List getListByCondition(Object object) {
        GestionDAO artDAO = new ArticuloDAO();
        return artDAO.getListByCondition(object);
    }

    @Override
    public List getListByPagination(Object object) {
        GestionDAO artDAO = new ArticuloDAO();
        return artDAO.getListByPagination(object);
    }

}
