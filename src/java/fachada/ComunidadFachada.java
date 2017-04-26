/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachada;

import java.util.List;
import persistencia.daos.ComunidadDAO;
import persistencia.daos.GestionDAO;

/**
 *
 * @author manuel.alcala
 */
public class ComunidadFachada implements GestionFachada {

    @Override
    public int getCount(Object object) {
        GestionDAO comunidadDAO = new ComunidadDAO();
        return comunidadDAO.getCount(object);
    }

    @Override
    public Object getObject(Object object) {
        GestionDAO comunidadDAO = new ComunidadDAO();
        return comunidadDAO.getObject(object);
    }

    @Override
    public int insertObject(Object object) {
        GestionDAO comunidadDAO = new ComunidadDAO();
        return comunidadDAO.insertObject(object);
    }

    @Override
    public List getListObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListObject() {
        GestionDAO comunidadDAO = new ComunidadDAO();
        return comunidadDAO.getListObject();
    }

    @Override
    public int updateObject(Object object) {
        GestionDAO comunidadDAO = new ComunidadDAO();
        return comunidadDAO.updateObject(object);
    }

    @Override
    public void deleteObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListByCondition(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListByPagination(Object object) {
        ComunidadDAO comunidadDAO = new ComunidadDAO();
        return comunidadDAO.getListByPagination(object);
    }
}
