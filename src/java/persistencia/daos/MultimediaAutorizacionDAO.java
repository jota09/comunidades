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
import java.util.Calendar;
import java.util.List;
import persistencia.conexion.ConexionBD;
import persistencia.entidades.Articulo;
import persistencia.entidades.Autorizacion;
import persistencia.entidades.MultimediaAutorizacion;
import persistencia.entidades.TipoError;
import utilitarias.Utilitaria;
import persistencia.entidades.Error;

/**
 *
 * @author manuel.alcala
 */
public class MultimediaAutorizacionDAO implements GestionDAO {

    @Override
    public int getCount(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getObject(Object object) {
        Autorizacion auto = (Autorizacion) object;
        MultimediaAutorizacion mult = new MultimediaAutorizacion();
        Connection con = null;
        try {

            con = ConexionBD.obtenerConexion();
            String query = "SELECT * FROM multimedia_autorizacion AS m INNER JOIN tipo_multimedia AS tm ON m.tipo_multimedia_codigo = tm.codigo WHERE m.autorizacion_codigo=?";
            PreparedStatement pS = con.prepareStatement(query);
            pS.setInt(1, auto.getCodigo());
            ResultSet rS = pS.executeQuery();
            while(rS.next()){
                mult.setCodigo(rS.getLong("m.codigo"));
                mult.setExtension(rS.getString("tm.extension"));
            }
            pS.close();
            rS.close();
        } catch (ClassNotFoundException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getObject");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getObject");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getObject");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return mult;
    }

    @Override
    public List getListObject(Object object) {
        Articulo art = (Articulo) object;
        ArrayList<MultimediaAutorizacion> listMult = new ArrayList<MultimediaAutorizacion>();
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String query = "SELECT mult.*,tpMult.extension FROM multimedia_autorizacion mult "
                    + "INNER JOIN tipo_multimedia tpMult ON mult.tipo_multimedia_codigo=tpMult.codigo WHERE mult.autorizacion_codigo=?";
            PreparedStatement pS = con.prepareStatement(query);
            pS.setInt(1, art.getCodigo());
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                MultimediaAutorizacion mult = new MultimediaAutorizacion();
                mult.setCodigo(rS.getLong("codigo"));
                mult.setTipoMultimediaCodigo(rS.getInt("tipo_multimedia_codigo"));
                mult.setActivo(rS.getShort("activo"));
                mult.setDestacada(rS.getShort("destacada"));
                mult.setExtension(rS.getString("extension"));
                listMult.add(mult);
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
        return listMult;
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
    public synchronized int insertObject(Object object) {
        MultimediaAutorizacion multimedia = (MultimediaAutorizacion) object;
        Connection con = null;
        int tamano = 0;
        try {
            int numeroAleatorio = (int) (Math.random() * 9999 + 1000);
            con = ConexionBD.obtenerConexion();
            Calendar calendar = Calendar.getInstance();
            String sql = "INSERT INTO multimedia_autorizacion (CODIGO,AUTORIZACION_CODIGO, TIPO_MULTIMEDIA_CODIGO, DESTACADA) VALUES (?,?,?,?)";
            PreparedStatement pS = con.prepareStatement(sql);
            multimedia.setCodigo(calendar.getTimeInMillis() + numeroAleatorio);
            pS.setLong(1, multimedia.getCodigo());
            pS.setInt(2, multimedia.getAutorizacioncodigo().getCodigo());
            pS.setInt(3, multimedia.getTipoMultimediaCodigo());
            pS.setShort(4, multimedia.getDestacada());
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
    public void deleteObject(Object object) {
        MultimediaAutorizacion mult = (MultimediaAutorizacion) object;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "DELETE FROM multimedia_autorizacion WHERE autorizacion_codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);            
            pS.setInt(1, mult.getAutorizacioncodigo().getCodigo());
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
        }
        finally{
            ConexionBD.cerrarConexion(con);
        }
    }

    @Override
    public List getListByCondition(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListByPagination(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private  int getMaxCodigo() {
        Connection con = null;
        int cont = 1;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "SELECT MAX(codigo)+1 codigo FROM multimedia_autorizacion ";
            PreparedStatement pS = con.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                int max = rS.getInt(1);
                cont = ((max > 0) ? max : cont);
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
}
