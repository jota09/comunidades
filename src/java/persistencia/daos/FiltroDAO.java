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
import persistencia.entidades.CondicionesFiltro;
import persistencia.entidades.Filtro;
import persistencia.entidades.MetaData;
import persistencia.entidades.TipoError;
import utilitarias.Utilitaria;
import persistencia.entidades.Error;
import persistencia.entidades.Vista;

/**
 *
 * @author manuel.alcala
 */
public class FiltroDAO implements GestionDAO {

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
        MetaData tabla = (MetaData) object;
        Connection con = null;
        List<Filtro> filtros = new ArrayList();
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select f.codigo,f.nombre,f.tabla,f.campo,cf.condicion,cf.codigo from filtro f join condicion_filtro cf "
                    + "on f.condicion_filtro_codigo=cf.codigo where f.tabla=? and f.vista_codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setString(1, tabla.getTabla());
            pS.setInt(2, tabla.getCodVista());
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Filtro filtro = new Filtro();
                CondicionesFiltro condicionFiltro = new CondicionesFiltro();
                filtro.setCodigo(rS.getInt(1));
                filtro.setNombre(rS.getString(2));
                filtro.setTabla(rS.getString(3));
                filtro.setCampo(rS.getString(4));
                filtro.setCondicion(rS.getString(5));
                condicionFiltro.setCodigo(rS.getInt(6));
                filtro.setCondicionFiltro(condicionFiltro);
                filtros.add(filtro);
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
        return filtros;
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
        Filtro filtro = (Filtro) object;
        int tam = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Insert into filtro(codigo,nombre,tabla,campo,condicion_filtro_codigo,vista_codigo,tablapk) values(?,?,?,?,?,?,?)";
            PreparedStatement pS = con.prepareStatement(sql);
            filtro.setCodigo(getMaxCodigo());
            pS.setInt(1, filtro.getCodigo());
            pS.setString(2, filtro.getNombre());
            pS.setString(3, filtro.getTabla());
            pS.setString(4, filtro.getCampo());
            pS.setInt(5, filtro.getCondicionFiltro().getCodigo());
            pS.setInt(6, filtro.getVista().getCodigo());
            pS.setString(7,filtro.getTablaPK());
            tam = pS.executeUpdate();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("insertObject");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("insertObject");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("insertObject");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return tam;
    }

    private int getMaxCodigo() {
        Connection con = null;
        int cont = 1;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "SELECT MAX(codigo)+1 codigo FROM filtro ";
            PreparedStatement pS = con.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                cont = (rS.getInt(1)==0)?1:rS.getInt(1);
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
        return cont;
    }

    @Override
    public void deleteObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListByCondition(Object object) {
        Vista vista = (Vista) object;
        Connection con = null;
        List<Filtro> filtros = new ArrayList();
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select f.codigo,f.nombre,f.tabla,f.campo,cf.condicion,cf.codigo from filtro f join condicion_filtro cf "
                    + "on f.condicion_filtro_codigo=cf.codigo where  f.vista_codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, vista.getCodigo());
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Filtro filtro = new Filtro();
                CondicionesFiltro condicionFiltro = new CondicionesFiltro();
                filtro.setCodigo(rS.getInt(1));
                filtro.setNombre(rS.getString(2));
                filtro.setTabla(rS.getString(3));
                filtro.setCampo(rS.getString(4));
                filtro.setCondicion(rS.getString(5));
                condicionFiltro.setCodigo(rS.getInt(6));
                filtro.setCondicionFiltro(condicionFiltro);
                filtros.add(filtro);
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
        return filtros;
    }

    @Override
    public List getListByPagination(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
