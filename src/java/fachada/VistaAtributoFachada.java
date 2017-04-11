/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachada;

import java.util.List;
import persistencia.daos.GestionDAO;
import persistencia.daos.VistaAtributoDAO;

/**
 *
 * @author manuel.alcala
 */
public class VistaAtributoFachada implements GestionFachada {

    @Override
    public int getCount(Object object) {
        GestionDAO vistaAtributoDAO = new VistaAtributoDAO();
        return vistaAtributoDAO.getCount(object);
    }

    @Override
    public Object getObject(Object object) {
        GestionDAO vistaAtributoDAO = new VistaAtributoDAO();
        return vistaAtributoDAO.getObject(object);
    }

    @Override
    public int insertObject(Object object) {
        GestionDAO vistaAtributoDAO = new VistaAtributoDAO();
        return vistaAtributoDAO.insertObject(object);
    }

    @Override
    public List getListObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListObject() {
        GestionDAO vistaAtributoDAO = new VistaAtributoDAO();
        return vistaAtributoDAO.getListObject();
    }

    @Override
    public int updateObject(Object object) {
        GestionDAO vistaAtributoDAO = new VistaAtributoDAO();
        return vistaAtributoDAO.updateObject(object);
    }

    @Override
    public void deleteObject(Object object) {
        GestionDAO vistaAtributoDAO = new VistaAtributoDAO();
        vistaAtributoDAO.deleteObject(object);
    }

    @Override
    public List getListByCondition(Object object) {
        GestionDAO vistaAtributoDAO = new VistaAtributoDAO();
        return vistaAtributoDAO.getListByCondition(object);
    }

    @Override
    public List getListByPagination(Object object) {
        GestionDAO vistaAtributoDAO = new VistaAtributoDAO();
        return vistaAtributoDAO.getListByPagination(object);
    }

}
