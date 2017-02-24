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
import persistencia.entidades.CondicionesFiltro;
import persistencia.entidades.Filtro;
import persistencia.entidades.OpcionesFiltro;

/**
 *
 * @author manuel.alcala
 */
public class OpcionesFiltroDAO implements GestionDAO {

    @Override
    public int getCount(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getObject(Object object) {
        OpcionesFiltro opcion = (OpcionesFiltro) object;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select of.valor,cf.condicion,f.campo from opciones_filtro of "
                    + "join filtro f on f.codigo=of.filtro_codigo "
                    + "join condicion_filtro cf on f.condicion_filtro_codigo=cf.codigo"
                    + " where of.codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            System.out.println("SQL_FILTROS:"+sql.replace("?", opcion.getCodigo()+""));
            pS.setInt(1, opcion.getCodigo());
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                CondicionesFiltro condicion = new CondicionesFiltro();
                Filtro filtro = new Filtro();
                opcion.setValor(rS.getString(1));
                condicion.setCondicion(rS.getString(2));
                filtro.setCampo(rS.getString(3));
                filtro.setCondicionFiltro(condicion);
                opcion.setFiltro(filtro);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OpcionesFiltroDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(OpcionesFiltroDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OpcionesFiltroDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return opcion;
    }

    @Override
    public List getListObject(Object object) {
        Connection con = null;
        Filtro filtro = (Filtro) object;
        List<OpcionesFiltro> opcionesFiltro = new ArrayList();
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "SELECT codigo,nombre,valor from opciones_filtro where filtro_codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, filtro.getCodigo());
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                OpcionesFiltro opcion = new OpcionesFiltro(rS.getInt(1), rS.getString(2), rS.getString(3), filtro);
                opcionesFiltro.add(opcion);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OpcionesFiltroDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(OpcionesFiltroDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OpcionesFiltroDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return opcionesFiltro;

    }

    @Override
    public List getListObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateObject(Object object) {
        return 0;
    }

    @Override
    public int insertObject(Object object) {
        Connection con = null;
        OpcionesFiltro opcionFiltro = (OpcionesFiltro) object;
        int tam = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Insert into opciones_filtro(nombre,valor,filtro_codigo) values(?,?,?)";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setString(1, opcionFiltro.getNombre());
            pS.setString(2, opcionFiltro.getValor());
            System.out.println("CODIGO FILTRO EN OPCION:" + opcionFiltro.getFiltro().getCodigo());
            pS.setInt(3, opcionFiltro.getFiltro().getCodigo());
            tam = pS.executeUpdate();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OpcionesFiltroDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(OpcionesFiltroDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OpcionesFiltroDAO.class.getName()).log(Level.SEVERE, null, ex);
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

}
