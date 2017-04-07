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
import java.util.List;
import persistencia.conexion.ConexionBD;
import persistencia.entidades.DocumentoDoc;
import persistencia.entidades.TipoError;
import utilitarias.Utilitaria;

/**
 *
 * @author Jesus.Ramos
 */
public class DocumentoDocDAO implements GestionDAO {

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        DocumentoDoc doc = (DocumentoDoc) object;
        doc.setId(getMaxCodigo());
        Connection con = null;
        int ingreso = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql ="INSERT INTO documento_doc (id,estado_doc_ID,estilo_doc_ID,tipo_documento_doc_ID,usuario_CODIGO) VALUES (?,?,?,?,?)";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, doc.getId());
            pS.setInt(2, 1);
            pS.setInt(3, doc.getEstilo().getId());
            pS.setInt(4, doc.getTipo().getId());
            pS.setInt(5, doc.getUser().getCodigo());
            ingreso = pS.executeUpdate();

        } catch (ClassNotFoundException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("insertObject");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("insertObject");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("insertObject");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return 0;
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

    private int getMaxCodigo() {
        Connection con = null;
        int cont = 1;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "SELECT MAX(id)+1 codigo FROM documento_doc";
            PreparedStatement pS = con.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                int max = rS.getInt(1);
                cont = ((max > 0) ? max : cont);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getMaxCodigo");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getMaxCodigo");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getMaxCodigo");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return cont;
    }

}
