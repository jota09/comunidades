/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachada;

import java.util.List;
import persistencia.daos.GestionDAO;
import persistencia.daos.MultimediaAutorizacionDAO;

/**
 *
 * @author manuel.alcala
 */
public class MultimediaAutorizacionFachada implements GestionFachada {

    @Override
    public int getCount(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getObject(Object object) {
        GestionDAO multimediaDAO = new MultimediaAutorizacionDAO();
        return multimediaDAO.getObject(object);
    }

    @Override
    public int insertObject(Object object) {
        GestionDAO multimediaDAO = new MultimediaAutorizacionDAO();
        return multimediaDAO.insertObject(object);
    }

    @Override
    public List getListObject(Object object) {
        GestionDAO multimediaDAO = new MultimediaAutorizacionDAO();
        return multimediaDAO.getListObject(object);
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
        GestionDAO multimediaDAO = new MultimediaAutorizacionDAO();
        multimediaDAO.deleteObject(object);
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
