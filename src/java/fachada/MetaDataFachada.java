/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachada;

import java.util.List;
import java.util.Map;
import persistencia.daos.MetaDataDAO;
import persistencia.entidades.MetaData;

/**
 *
 * @author manuel.alcala
 */
public class MetaDataFachada {

    public List<MetaData> getTables() {
        MetaDataDAO metaDAO = new MetaDataDAO();
        return metaDAO.getTables();
    }
     public Map<String,MetaData> getColumnas(MetaData metada) {
         MetaDataDAO metaDAO = new MetaDataDAO();
         return metaDAO.getColumnas(metada);
     }
}
