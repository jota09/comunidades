/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.conexion.ConexionBD;
import persistencia.entidades.MetaData;
import persistencia.entidades.TipoError;
import utilitarias.Utilitaria;
import persistencia.entidades.Error;

/**
 *
 * @author manuel.alcala
 */
public class MetaDataDAO {

    public List<MetaData> getTables() {
        Connection con = null;
        List<MetaData> tablas = new ArrayList();
        try {
            con = ConexionBD.obtenerConexion();
            DatabaseMetaData databaseMetaData = con.getMetaData();
            ResultSet rS = databaseMetaData.getTables(null, "comunidades", null, null);
            while (rS.next()) {
                MetaData tabla = new MetaData();
                tabla.setEsquema(rS.getString(1));
                tabla.setTabla(rS.getString(3));
                tablas.add(tabla);
            }

            rS.close();
        } catch (ClassNotFoundException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getTables");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getTables");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getTables");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return tablas;
    }

    public List<String> getColumnas(MetaData metada) {
        Connection con = null;
        List<String> columnas = new ArrayList();
        try {
            con = ConexionBD.obtenerConexion();
            DatabaseMetaData databaseMetaData = con.getMetaData();
            ResultSet rS = databaseMetaData.getColumns(null, "comunidades", metada.getTabla(), null);
            while (rS.next()) {
                columnas.add(rS.getString(4));
            }

            rS.close();
        } catch (ClassNotFoundException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getColumnas");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getColumnas");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getColumnas");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return columnas;
    }

}
