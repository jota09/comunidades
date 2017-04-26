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
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.conexion.ConexionBD;
import persistencia.entidades.Ciudad;
import persistencia.entidades.Departamento;
import persistencia.entidades.Pais;

/**
 *
 * @author daniel.franco
 */
public class CiudadDAO implements GestionDAO {

    @Override
    public int getCount(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getObject(Object object) {
        Ciudad ciudad = (Ciudad) object;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "SELECT p.CODIGO as PAIS, d.CODIGO AS DEPARTAMENTO "
                    + "FROM pais p, departamento d, ciudad c "
                    + "WHERE c.CODIGO = ? AND departamento_CODIGO = d.CODIGO AND pais_CODIGO = p.CODIGO";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, ciudad.getCodigo());
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                Pais pais = new Pais();
                pais.setCodigo(rS.getInt("PAIS"));
                Departamento departamento = new Departamento();
                departamento.setCodigo(rS.getInt("DEPARTAMENTO"));
                departamento.setPais(pais);
                ciudad.setDepartamento(departamento);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PaisDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PaisDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PaisDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return ciudad;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListByCondition(Object object) {
        Departamento departamento = (Departamento) object;
        Connection con = null;
        List<Ciudad> ciudades = new ArrayList<>();
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "SELECT CODIGO,NOMBRE FROM ciudad WHERE departamento_CODIGO = ? AND ACTIVO = 1";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, departamento.getCodigo());
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Ciudad ciudad = new Ciudad();
                ciudad.setCodigo(rS.getInt("CODIGO"));
                ciudad.setNombre(rS.getString("NOMBRE"));
                ciudades.add(ciudad);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PaisDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PaisDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PaisDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return ciudades;
    }

    @Override
    public List getListByPagination(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
