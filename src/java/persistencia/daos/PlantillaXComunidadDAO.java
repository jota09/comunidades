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
import persistencia.entidades.Comunidad;
import persistencia.entidades.PlantillaPDF;
import persistencia.entidades.PlantillaXComunidad;
import persistencia.entidades.TipoError;
import utilitarias.CondicionPaginado;
import utilitarias.Utilitaria;

/**
 *
 * @author manuel.alcala
 */
public class PlantillaXComunidadDAO implements GestionDAO {

    @Override
    public int getCount(Object object) {
        CondicionPaginado condicionPaginado = (CondicionPaginado) object;
        Connection con = null;
        int cont = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select count(pxc.codigo) from plantilla_x_comunidad pxc "
                    + " join plantilla_pdf pd on pxc.plantilla_pdf_codigo=pd.codigo where pxc.comunidad_codigo=? " + condicionPaginado.getCondicion();
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, condicionPaginado.getComunidad().getCodigo());
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                cont = rS.getInt(1);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getCount");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getCount");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getCount");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return cont;
    }

    @Override
    public Object getObject(Object object) {
        PlantillaXComunidad pxComunidad = (PlantillaXComunidad) object;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "select codigo,plantilla_pdf_codigo from plantilla_x_comunidad where activo=1 and comunidad_codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, pxComunidad.getComunidad().getCodigo());
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                PlantillaPDF plantilla = new PlantillaPDF();
                pxComunidad.setCodigo(rS.getInt(1));
                plantilla.setCodigo(rS.getInt(2));
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PlantillaXComunidadDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PlantillaXComunidadDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PlantillaXComunidadDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return pxComunidad;
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
        PlantillaXComunidad pxComunidad = (PlantillaXComunidad) object;
        int tam = 0;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql1 = "Update plantilla_x_comunidad set activo=0 where comunidad_codigo=?";
            String sql2 = "Update plantilla_x_comunidad set activo=1 where codigo=?";
            PreparedStatement pS = con.prepareStatement(sql1);
            pS.setInt(1, pxComunidad.getComunidad().getCodigo());
            pS.execute();
            pS = con.prepareStatement(sql2);
            pS.setInt(1, pxComunidad.getCodigo());
            tam = pS.executeUpdate();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PlantillaXComunidadDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PlantillaXComunidadDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PlantillaXComunidadDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return tam;
    }

    @Override
    public int insertObject(Object object) {
        PlantillaXComunidad plantilla = (PlantillaXComunidad) object;
        Connection con = null;
        int tam = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "insert into plantilla_x_comunidad(codigo,plantilla_pdf_codigo,comunidad_codigo,activo) values(?,?,?,?)";
            PreparedStatement pS = con.prepareStatement(sql);
            plantilla.setCodigo(getMaxCodigo());
            pS.setInt(1, plantilla.getCodigo());
            pS.setInt(2, plantilla.getPlantilla().getCodigo());
            pS.setInt(3, plantilla.getComunidad().getCodigo());
            pS.setInt(4, 0);
            tam = pS.executeUpdate();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PlantillaPDFDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PlantillaPDFDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PlantillaPDFDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return tam;
    }

    @Override
    public void deleteObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListByCondition(Object object) {
        CondicionPaginado condicionPaginado = (CondicionPaginado) object;
        Connection con = null;
        List<PlantillaXComunidad> plantillas = new ArrayList();
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select pxc.codigo,pd.codigo,pd.nombre,pxc.activo from plantilla_x_comunidad pxc "
                    + " join plantilla_pdf pd on pxc.plantilla_pdf_codigo=pd.codigo where pxc.comunidad_codigo=? " + condicionPaginado.getCondicion();
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, condicionPaginado.getComunidad().getCodigo());
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                PlantillaXComunidad pxc = new PlantillaXComunidad();
                PlantillaPDF ppdf = new PlantillaPDF();
                pxc.setCodigo(rS.getInt(1));
                ppdf.setCodigo(rS.getInt(2));
                ppdf.setNombre(rS.getString(3));
                pxc.setActivo(rS.getInt(4));
                pxc.setPlantilla(ppdf);
                plantillas.add(pxc);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PlantillaXComunidadDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PlantillaXComunidadDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PlantillaXComunidadDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return plantillas;
    }

    private int getMaxCodigo() {
        Connection con = null;
        int cont = 1;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "SELECT MAX(codigo)+1 codigo FROM plantilla_x_comunidad ";
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

    @Override
    public List getListByPagination(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
