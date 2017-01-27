/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachada;

import java.util.List;

/**
 *
 * @author ferney.medina
 */
public interface GestionFachada {

    public int getCount(Object object);

    public Object getObject(Object object);

    public int insertObject(Object object);

    public List getListObject(Object object);

    public List getListObject();

    public int updateObject(Object object);

    public void deleteObject(Object object);
}
