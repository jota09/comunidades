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
import persistencia.entidades.Factura;
import persistencia.entidades.Proceso;
import persistencia.entidades.TipoError;
import persistencia.entidades.Usuario;
import utilitarias.CondicionPaginado;
import utilitarias.Utilitaria;

/**
 *
 * @author manuel.alcala
 */
public class FacturaDAO implements GestionDAO {

    @Override
    public int getCount(Object object) {
        CondicionPaginado condicion = (CondicionPaginado) object;
        Connection con = null;
        int cont = 0;
        try {
            con = ConexionBD.obtenerConexion();
              String sql = "Select count(fac.codigo) from factura fac "
                    + "join proceso pr on fac.proceso_codigo=pr.codigo "
                    + "where fac.usuario_codigo=? and pr.comunidad_codigo=? "+condicion.getCondicion();
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1,condicion.getUser().getCodigo());
            pS.setInt(2, condicion.getComunidad().getCodigo());
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
        Factura factura = (Factura) object;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select num_factura,proceso_codigo from factura  where codigo=?  ";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, factura.getCodigo());
            System.out.println("Factura SQL:" + pS);
            ResultSet rS = pS.executeQuery();

            if (rS.next()) {
                Proceso proceso = new Proceso();
                factura.setNumFactura(rS.getString(1));
                proceso.setCodigo(rS.getInt(2));
                proceso.setActivo(0);
                factura.setProceso(proceso);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FacturaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FacturaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return factura;
    }

    @Override
    public List getListObject(Object object) {
        Proceso proceso = (Proceso) object;
        Connection con = null;
        List<Factura> facturas = new ArrayList();
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "select codigo,usuario_codigo,num_factura from factura where proceso_codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, proceso.getCodigo());
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Factura factura = new Factura();
                factura.setCodigo(rS.getInt(1));
                Usuario user = new Usuario();
                user.setCodigo(rS.getInt(2));
                factura.setUsuario(user);
                factura.setNumFactura(rS.getString(3));
                facturas.add(factura);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FacturaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FacturaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }

        return facturas;
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
        CondicionPaginado condicion = (CondicionPaginado) object;
        Connection con = null;
        List<Factura> facturas = new ArrayList();
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select fac.codigo,fac.num_factura from factura fac "
                    + "join proceso pr on fac.proceso_codigo=pr.codigo "
                    + "where fac.usuario_codigo=? and pr.comunidad_codigo=? "+condicion.getCondicion();
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, condicion.getUser().getCodigo());
            pS.setInt(2, condicion.getComunidad().getCodigo());
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Factura factura = new Factura();
                factura.setCodigo(rS.getInt(1));
                factura.setNumFactura(rS.getString(2));
                facturas.add(factura);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FacturaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FacturaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return facturas;
    }

    @Override
    public List getListByPagination(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
