/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachada;

import java.util.List;
import persistencia.daos.FacturaDAO;
import persistencia.daos.GestionDAO;

/**
 *
 * @author manuel.alcala
 */
public class FacturaFachada implements GestionFachada {

    @Override
    public int getCount(Object object) {
        GestionDAO facturaDAO = new FacturaDAO();
        return facturaDAO.getCount(object);
    }

    @Override
    public Object getObject(Object object) {
        GestionDAO facturaDAO = new FacturaDAO();
        return facturaDAO.getObject(object);
    }

    @Override
    public int insertObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListObject(Object object) {
        GestionDAO facturaDAO = new FacturaDAO();
        return facturaDAO.getListObject(object);
    }

    @Override
    public List getListObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListByCondition(Object object) {
        GestionDAO facturaDAO = new FacturaDAO();
        return facturaDAO.getListByCondition(object);
    }

    @Override
    public List getListByPagination(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
