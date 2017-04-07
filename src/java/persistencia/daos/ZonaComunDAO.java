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
import persistencia.entidades.TipoError;
import persistencia.entidades.ZonaComun;
import utilitarias.Utilitaria;

/**
 *
 * @author daniel.franco
 */
public class ZonaComunDAO implements GestionDAO {

    @Override
    public int getCount(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //Método para consultar una zona común en específico
    @Override
    public Object getObject(Object object) {
        ZonaComun zona = (ZonaComun) object;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "SELECT NOMBRE,COMUNIDAD_CODIGO,ALQUILER,DESCRIPCION "
                    + "FROM zona_comun WHERE CODIGO = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, zona.getCodigo());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                zona.setNombre(rs.getString("NOMBRE"));
            }
            rs.close();
            ps.close();
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
        return zona;
    }

    //Método para consulta todas las zonas comunes asociadas a una comunidad
    @Override
    public List getListObject(Object object) {
        int comunidad = (int) object;
        ArrayList<ZonaComun> zonas = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "SELECT * FROM zona_comun WHERE COMUNIDAD_CODIGO = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, comunidad);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ZonaComun zona = new ZonaComun();
                zona.setCodigo(rs.getInt("CODIGO"));
                zona.setNombre(rs.getString("NOMBRE"));
                zona.setComunidad(rs.getInt("COMUNIDAD_CODIGO"));
                zona.setAlquiler(rs.getDouble("ALQUILER"));
                zona.setDescripcion(rs.getString("DESCRIPCION"));
                zonas.add(zona);
            }
            rs.close();
            ps.close();
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
        return zonas;
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