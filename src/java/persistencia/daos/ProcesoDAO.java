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
import persistencia.entidades.EventoProceso;
import persistencia.entidades.PlantillaPDF;
import persistencia.entidades.PlantillaXComunidad;
import persistencia.entidades.Proceso;
import persistencia.entidades.TipoError;
import persistencia.entidades.Usuario;
import utilitarias.Utilitaria;

/**
 *
 * @author manuel.alcala
 */
public class ProcesoDAO implements GestionDAO {

    @Override
    public int getCount(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getObject(Object object) {
        Connection con = null;
        Proceso proceso = (Proceso) object;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select pro.fecha_inicio,pro.fecha_fin,pro.comunidad_codigo,pro.evento_proceso_codigo,"
                    + "pro.usuario_responsable,pro.plantilla_x_comunidad_codigo,pxc.plantilla_pdf_codigo from proceso pro join plantilla_x_comunidad pxc"
                    + " on pxc.codigo=pro.plantilla_x_comunidad_codigo where pro.codigo=? and pxc.activo=1";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, proceso.getCodigo());
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                PlantillaPDF plantilla = new PlantillaPDF();
                PlantillaXComunidad plantillaXComunidad = new PlantillaXComunidad();
                Comunidad comunidad = new Comunidad();
                Usuario usuario = new Usuario();
                EventoProceso evento = new EventoProceso();
                proceso.setFechaInicio(rS.getTimestamp(1));
                proceso.setFechaFin(rS.getTimestamp(2));
                comunidad.setCodigo(rS.getInt(3));
                proceso.setComunidad(comunidad);
                evento.setCodigo(rS.getInt(4));
                proceso.setEventoProceso(evento);
                usuario.setCodigo(rS.getInt(5));
                proceso.setUsuarioResponsable(usuario);
                plantillaXComunidad.setCodigo(rS.getInt(6));
                plantilla.setCodigo(rS.getInt(7));
                plantillaXComunidad.setPlantilla(plantilla);
                proceso.setPlantillaXComunidad(plantillaXComunidad);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProcesoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProcesoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProcesoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return proceso;
    }

    @Override
    public List getListObject(Object object) {
        Comunidad com = (Comunidad) object;
        List<Proceso> procesos = new ArrayList();
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "select p.codigo,p.fecha_inicio,p.fecha_fin,p.evento_proceso_codigo,ep.nombre,p.usuario_responsable,usr.user_name from proceso p "
                    + "join evento_proceso ep on p.evento_proceso_codigo=ep.codigo join usuario usr "
                    + "on p.usuario_responsable=usr.codigo where comunidad_codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, com.getCodigo());
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Proceso pro = new Proceso();
                EventoProceso evento = new EventoProceso();
                Usuario usuario = new Usuario();
                pro.setCodigo(rS.getInt(1));
                pro.setFechaInicio(rS.getTimestamp(2));
                pro.setFechaFin(rS.getTimestamp(3));
                evento.setCodigo(rS.getInt(4));
                evento.setNombre(rS.getString(5));
                pro.setEventoProceso(evento);
                usuario.setCodigo(rS.getInt(6));
                usuario.setUserName(rS.getString(7));
                pro.setUsuarioResponsable(usuario);
                procesos.add(pro);
            }
            rS.close();
            pS.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProcesoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProcesoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProcesoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return procesos;
    }

    @Override
    public List getListObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateObject(Object object) {
        Connection con = null;
        Proceso proceso = (Proceso) object;
        int tam = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "update proceso set evento_proceso_codigo=? where codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, proceso.getEventoProceso().getCodigo());
            pS.setInt(2, proceso.getCodigo());
            tam = pS.executeUpdate();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProcesoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProcesoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProcesoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return tam;
    }

    @Override
    public int insertObject(Object object) {
        Proceso proceso = (Proceso) object;
        proceso.setCodigo(getMaxCodigo());
        Connection con = null;
        int tam = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "insert into proceso(codigo,comunidad_codigo,usuario_responsable,plantilla_x_comunidad_codigo) values(?,?,?,?)";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, proceso.getCodigo());
            pS.setInt(2, proceso.getComunidad().getCodigo());
            pS.setInt(3, proceso.getUsuarioResponsable().getCodigo());
            pS.setInt(4, proceso.getPlantillaXComunidad().getCodigo());
            tam = pS.executeUpdate();

            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProcesoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProcesoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProcesoDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            String sql = "SELECT MAX(codigo)+1 codigo FROM proceso ";
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
