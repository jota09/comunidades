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
import persistencia.entidades.Comunidad;
import persistencia.entidades.Perfil;
import persistencia.entidades.TipoError;
import utilitarias.Utilitaria;
import persistencia.entidades.Error;

/**
 *
 * @author manuel.alcala
 */
public class PerfilDAO implements GestionDAO {

    @Override
    public Object getObject(Object object) {
        Perfil perfil = (Perfil) object;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String query = "SELECT codigo,nombre,comunidad_codigo FROM perfil WHERE codigo=? or (comunidad_codigo=? and nombre=?)";
            PreparedStatement pS = con.prepareStatement(query);
            pS.setInt(1, perfil.getCodigo());
            pS.setInt(2, ((perfil.getComunidad() != null) ? perfil.getComunidad().getCodigo() : 0));
            pS.setString(3, perfil.getNombre());
            ResultSet rS = pS.executeQuery();
            System.out.println("QUERY:" + pS);
            if (rS.next()) {
                perfil.setCodigo(rS.getInt(1));
                perfil.setNombre(rS.getString(2));
                Comunidad comunidad = new Comunidad(rS.getInt(3));
                perfil.setComunidad(comunidad);

            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getMaxCodigo");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getMaxCodigo");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getMaxCodigo");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return perfil;
    }

    @Override
    public List getListObject(Object object) {
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
    public List getListObject() {
        List<Perfil> perfiles = new ArrayList();
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select p.codigo,p.nombre,c.codigo,c.nombre from perfil p left join comunidad c on p.comunidad_codigo=c.codigo  where p.activo=1 order by p.nombre";
            System.out.println("Imprimiendo PERFIL:" + sql);
            PreparedStatement pS = con.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Perfil perfil = new Perfil();
                Comunidad comunidad = new Comunidad();
                perfil.setCodigo(rS.getInt(1));
                perfil.setNombre(rS.getString(2));
                comunidad.setCodigo(rS.getInt(3));
                comunidad.setNombre(rS.getString(4));
                perfil.setComunidad(comunidad);
                perfiles.add(perfil);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListObject");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListObject");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListObject");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return perfiles;
    }

    @Override
    public int getCount(Object obj) {
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
