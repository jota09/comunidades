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
import persistencia.entidades.Atributo;
import persistencia.entidades.TipoError;
import persistencia.entidades.Vista;
import utilitarias.Utilitaria;
import persistencia.entidades.Error;
import utilitarias.CondicionPaginado;

/**
 *
 * @author manuel.alcala
 */
public class AtributoDAO implements GestionDAO {

    @Override
    public Object getObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListObject(Object object) {
        Connection con = null;
        Vista vista = (Vista) object;
        List<Atributo> atributos = new ArrayList();
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select  atr.referencia,vat.valor from atributo atr "
                    + "join vista_atributo vat on vat.atributo_codigo=atr.codigo "
                    + "join vista vi on vat.vista_codigo=vi.codigo where vi.codigo=? order by atr.referencia";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, vista.getCodigo());
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Atributo atributo = new Atributo();
                atributo.setReferencia(rS.getString(1));
                atributo.setValor(rS.getString(2));
                atributos.add(atributo);
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
        return atributos;
    }

    @Override
    public List getListObject() {
        Connection con = null;
        List<Atributo> atributos = new ArrayList();
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select * from atributo where activo=1 order by referencia";
            PreparedStatement pS = con.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Atributo atributo = new Atributo();
                atributo.setCodigo(rS.getInt(1));
                atributo.setReferencia(rS.getString(2));
                atributos.add(atributo);
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
        return atributos;
    }

    @Override
    public int updateObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertObject(Object object) {
        Atributo atributo = (Atributo) object;
        Connection con = null;
        int tamano = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Insert into atributo(referencia) values(?)";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setString(1, atributo.getReferencia());
            tamano = pS.executeUpdate();
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
        return tamano;
    }

    @Override
    public int getCount(Object object) {
        CondicionPaginado condicion = (CondicionPaginado) object;
        Connection con = null;
        int tamano = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select count(codigo) from atributo "+condicion.getCondicion();
            PreparedStatement pS = con.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                tamano = rS.getInt(1);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getCount");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getCount");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getCount");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return tamano;
    }

    @Override
    public void deleteObject(Object object) {
        Atributo atributo = (Atributo) object;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "delete from atributo where codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, atributo.getCodigo());
            pS.execute();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("deleteObject");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("deleteObject");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("deleteObject");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }

    }

    @Override
    public List getListByCondition(Object object) {
        Connection con = null;
        String condicion = String.valueOf(object);
        List<Atributo> atributos = new ArrayList();
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "select * from atributo where referencia like ?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setString(1, condicion + "%");
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Atributo atributo = new Atributo();
                atributo.setCodigo(rS.getInt(1));
                atributo.setReferencia(rS.getString(2));
                atributos.add(atributo);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListByCondition");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListByCondition");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListByCondition");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return atributos;
    }

    @Override
    public List getListByPagination(Object object) {
        CondicionPaginado condicion = (CondicionPaginado) object;
        Connection con = null;
        List<Atributo> atributos = new ArrayList();
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "select * from atributo "+condicion.getCondicion();
            PreparedStatement pS = con.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Atributo atributo = new Atributo();
                atributo.setCodigo(rS.getInt(1));
                atributo.setReferencia(rS.getString(2));
                atributos.add(atributo);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListByCondition");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListByCondition");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListByCondition");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return atributos;
    }

}
