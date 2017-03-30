/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachada;

import java.util.List;
import persistencia.daos.DocumentoBorradorDAO;
import persistencia.daos.GestionDAO;

/**
 *
 * @author Jesus.Ramos
 */
public class DocumentoBorradorFachada implements GestionFachada {

    @Override
    public int getCount(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getObject(Object object) {
        GestionDAO doc = new DocumentoBorradorDAO();
        return doc.getObject(object);
    }

    @Override
    public int insertObject(Object object) {
        GestionDAO doc = new DocumentoBorradorDAO();
        return doc.insertObject(object);
    }

    @Override
    public List getListObject(Object object) {
        GestionDAO doc = new DocumentoBorradorDAO();
        return doc.getListObject(object);
    }

    @Override
    public List getListObject() {
        GestionDAO doc = new DocumentoBorradorDAO();
        return doc.getListObject();
    }

    @Override
    public int updateObject(Object object) {
        GestionDAO doc = new DocumentoBorradorDAO();
        return doc.updateObject(object);
    }

    @Override
    public void deleteObject(Object object) {
        GestionDAO doc = new DocumentoBorradorDAO();
        doc.deleteObject(object);
    }

    @Override
    public List getListByCondition(Object object) {
        GestionDAO doc = new DocumentoBorradorDAO();
        return doc.getListByCondition(object);
    }

    @Override
    public List getListByPagination(Object object) {
        GestionDAO doc = new DocumentoBorradorDAO();
        return doc.getListByPagination(object);
    }

}
