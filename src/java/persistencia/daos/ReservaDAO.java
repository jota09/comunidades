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
import persistencia.entidades.Reserva;
import persistencia.entidades.ReservaEstado;
import persistencia.entidades.TipoError;
import persistencia.entidades.Usuario;
import persistencia.entidades.ZonaComun;
import utilitarias.CondicionPaginado;
import utilitarias.Utilitaria;

/**
 *
 * @author daniel.franco
 */
public class ReservaDAO implements GestionDAO {

    @Override
    public int getCount(Object object) {
        CondicionPaginado condicionPaginado = (CondicionPaginado) object;
        Connection con = null;
        int cont = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select count(res.codigo) from reserva res join zona_comun zc on res.zona_codigo=zc.codigo "
                    + " join usuario usr on res.usuario_codigo=usr.codigo join reserva_estado re on res.estado_codigo=re.codigo where "
                    + " " + ((condicionPaginado.getComunidad() != null) ? "res.comunidad_codigo=" + condicionPaginado.getComunidad().getCodigo() : "")
                    + " " + ((condicionPaginado.getUser() != null) ? "and res.usuario_codigo=" + condicionPaginado.getUser().getCodigo() : "") + " " + condicionPaginado.getCondicion();
            PreparedStatement pS = con.prepareStatement(sql);
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
        Connection con = null;
        CondicionPaginado condicionPaginado = (CondicionPaginado) object;
        List<Reserva> reservas = new ArrayList();
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select res.codigo,res.fecha_inicio,res.fecha_fin,res.costo,res.descripcion,res.creacion,res.actualizacion,zc.codigo,zc.nombre, "
                    + "usr.codigo,usr.nombres,usr.apellidos,usr.user_name,re.codigo,re.nombre from reserva res join zona_comun zc on res.zona_codigo=zc.codigo "
                    + " join usuario usr on res.usuario_codigo=usr.codigo join reserva_estado re on res.estado_codigo=re.codigo where "
                    + " " + ((condicionPaginado.getComunidad() != null) ? "res.comunidad_codigo=" + condicionPaginado.getComunidad().getCodigo() : "")
                    + " " + ((condicionPaginado.getUser() != null) ? "and res.usuario_codigo=" + condicionPaginado.getUser().getCodigo() : "") + " " + condicionPaginado.getCondicion();
            PreparedStatement pS = con.prepareStatement(sql);
            System.out.println("Query reserva:" + pS);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Reserva reserva = new Reserva();
                ZonaComun zonaComun = new ZonaComun();
                Usuario usuario = new Usuario();
                ReservaEstado reservaEstado = new ReservaEstado();
                reserva.setCodigo(rS.getInt(1));
                reserva.setFechaInicio(rS.getTimestamp(2));
                reserva.setFechaFin(rS.getTimestamp(3));
                reserva.setCosto(rS.getDouble(4));
                reserva.setDescripcion(rS.getString(5));
                reserva.setCreacion(rS.getTimestamp(6));
                reserva.setActualizacion(rS.getTimestamp(7));
                zonaComun.setCodigo(rS.getInt(8));
                zonaComun.setNombre(rS.getString(9));
                reserva.setZonaCodigo(zonaComun);
                usuario.setCodigo(rS.getInt(10));
                usuario.setNombres(rS.getString(11));
                usuario.setApellidos(rS.getString(12));
                usuario.setUserName(rS.getString(13));
                reserva.setUsuarioCodigo(usuario);
                reservaEstado.setCodigo(rS.getInt(14));
                reservaEstado.setNombre(rS.getString(15));
                reserva.setEstadoCodigo(reservaEstado);
                reservas.add(reserva);

            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListByPagination");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListByPagination");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            persistencia.entidades.Error error = new persistencia.entidades.Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListByPagination");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return reservas;

    }
}
