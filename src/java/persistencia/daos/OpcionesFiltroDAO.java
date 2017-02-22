/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.conexion.ConexionBD;
import persistencia.entidades.OpcionesFiltro;

/**
 *
 * @author manuel.alcala
 */
public class OpcionesFiltroDAO implements GestionDAO {

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
        Connection con = null;
        OpcionesFiltro opcionFiltro = (OpcionesFiltro) object;
        int tam = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Insert into opciones_filtro(nombre,valor,filtro_codigo) values(?,?,?)";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setString(1, opcionFiltro.getNombre());
            pS.setString(2, opcionFiltro.getValor());
            pS.setInt(3, opcionFiltro.getFiltro().getCodigo());
            tam = pS.executeUpdate();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OpcionesFiltroDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(OpcionesFiltroDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OpcionesFiltroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tam;
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
