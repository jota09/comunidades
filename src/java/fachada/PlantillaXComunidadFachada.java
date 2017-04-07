/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachada;

import java.util.List;
import persistencia.daos.GestionDAO;
import persistencia.daos.PlantillaXComunidadDAO;

/**
 *
 * @author manuel.alcala
 */
public class PlantillaXComunidadFachada implements GestionFachada {

    @Override
    public int getCount(Object object) {
        GestionDAO pxComunidadDAO = new PlantillaXComunidadDAO();
        return pxComunidadDAO.getCount(object);
    }

    @Override
    public Object getObject(Object object) {
        GestionDAO pxComunidadDAO = new PlantillaXComunidadDAO();
        return pxComunidadDAO.getObject(object);
    }

    @Override
    public int insertObject(Object object) {
        GestionDAO pxComunidadDAO = new PlantillaXComunidadDAO();
        return pxComunidadDAO.insertObject(object);
    }

    @Override
    public List getListObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateObject(Object object) {
        GestionDAO pxComunidadDAO = new PlantillaXComunidadDAO();
        return pxComunidadDAO.updateObject(object);
    }

    @Override
    public void deleteObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListByCondition(Object object) {
        GestionDAO pxComunidadDAO = new PlantillaXComunidadDAO();
        return pxComunidadDAO.getListByCondition(object);
    }

    @Override
    public List getListByPagination(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
