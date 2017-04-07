/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import persistencia.conexion.ConexionBD;
import persistencia.entidades.ComponenteDoc;
import persistencia.entidades.TipoCompDoc;
import persistencia.entidades.TipoDocumentoDoc;
import persistencia.entidades.TipoError;
import utilitarias.Utilitaria;

/**
 *
 * @author Jesus.Ramos
 */
public class ComponenteDocDAO implements GestionDAO{

    @Override
    public int getCount(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListObject(Object object) {
        ComponenteDoc comp = (ComponenteDoc) object;
        ArrayList<ComponenteDoc> listComp = new ArrayList();
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "SELECT id, nombre, referencia, componente_ID, tipo_documento_doc_ID, tipo_comp_doc_ID"
                    + " FROM componente_doc"
                    + ""+((comp.getTipoD() != null ) ? " WHERE tipo_documento_doc_ID = "+comp.getTipoD().getId():" ")+"";
            PreparedStatement pS = con.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();
            while(rS.next()){
                ComponenteDoc comp2 = new ComponenteDoc();
                comp2.setId(rS.getInt(1));
                comp2.setNombre(rS.getString(2));
                comp2.setReferencia(rS.getString(3));
                comp2.setComponente(new ComponenteDoc(rS.getInt(4)));
                comp2.setTipoD(new TipoDocumentoDoc(rS.getInt(5)));
                comp2.setTipo(new TipoCompDoc(rS.getInt(6)));
                listComp.add(comp2);
            }
        } catch (ClassNotFoundException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListObject");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListObject");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListObject");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return listComp;
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
    public int insertObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
