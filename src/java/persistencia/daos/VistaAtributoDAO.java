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
import persistencia.entidades.Atributo;
import persistencia.entidades.Vista;
import persistencia.entidades.VistaAtributo;

/**
 *
 * @author manuel.alcala
 */
public class VistaAtributoDAO implements GestionDAO {

    @Override
    public int getCount(Object object) {
        Connection con = null;
        int tamano = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select count(codigo) from vista_atributo";
            PreparedStatement pS = con.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                tamano = rS.getInt(1);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VistaAtributoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(VistaAtributoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VistaAtributoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(VistaAtributoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tamano;
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
        Connection con = null;
        List<VistaAtributo> vistaAtributos = new ArrayList();
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select v.codigo,v.nombre,a.codigo,a.referencia,va.codigo,va.valor  from vista_atributo va join vista v on va.vista_codigo=v.codigo join"
                    + " atributo a on va.atributo_codigo=a.codigo where va.activo=1";
            PreparedStatement pS = con.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                VistaAtributo vistaAtributo = new VistaAtributo();
                Vista vista = new Vista();
                Atributo atributo = new Atributo();
                vista.setCodigo(rS.getInt(1));
                vista.setNombre(rS.getString(2));
                atributo.setCodigo(rS.getInt(3));
                atributo.setReferencia(rS.getString(4));
                vistaAtributo.setCodigo(rS.getInt(5));
                vistaAtributo.setValor(rS.getString(6));
                vistaAtributo.setVistaCodigo(vista);
                vistaAtributo.setAtributoCodigo(atributo);
                vistaAtributos.add(vistaAtributo);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(VistaAtributoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(VistaAtributoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return vistaAtributos;
    }

    @Override
    public int updateObject(Object object) {
        return 0;

    }

    @Override
    public int insertObject(Object object) {
        Connection con = null;
        VistaAtributo vistaAtributo = (VistaAtributo) object;
        int tamano = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Insert into vista_atributo(vista_codigo,atributo_codigo,valor) values(?,?,?)";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, vistaAtributo.getVistaCodigo().getCodigo());
            pS.setInt(2, vistaAtributo.getAtributoCodigo().getCodigo());
            pS.setString(3, vistaAtributo.getValor());
            tamano = pS.executeUpdate();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VistaAtributoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(VistaAtributoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VistaAtributoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(VistaAtributoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tamano;
    }

    @Override
    public void deleteObject(Object object) {
        Connection con = null;
        VistaAtributo vistaAtributo = (VistaAtributo) object;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "delete from vista_atributo where codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, vistaAtributo.getCodigo());
            pS.execute();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VistaAtributoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(VistaAtributoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VistaAtributoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(VistaAtributoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public List getListByCondition(Object object) {
        Connection con = null;
        String condicion = String.valueOf(object) + "%";
        List<VistaAtributo> vistaAtributos = new ArrayList();
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select v.codigo,v.nombre,a.codigo,a.referencia,"
                    + "va.codigo,va.valor  from vista_atributo va join vista v "
                    + "on va.vista_codigo=v.codigo join"
                    + " atributo a on va.atributo_codigo=a.codigo where "
                    + "(v.nombre like ? or a.referencia like ? or va.valor like ?) and va.activo=1 ";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setString(1, condicion);
            pS.setString(2, condicion);
            pS.setString(3, condicion);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                VistaAtributo vistaAtributo = new VistaAtributo();
                Vista vista = new Vista();
                Atributo atributo = new Atributo();
                vista.setCodigo(rS.getInt(1));
                vista.setNombre(rS.getString(2));
                atributo.setCodigo(rS.getInt(3));
                atributo.setReferencia(rS.getString(4));
                vistaAtributo.setCodigo(rS.getInt(5));
                vistaAtributo.setValor(rS.getString(6));
                vistaAtributo.setVistaCodigo(vista);
                vistaAtributo.setAtributoCodigo(atributo);
                vistaAtributos.add(vistaAtributo);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VistaAtributoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(VistaAtributoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VistaAtributoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(VistaAtributoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return vistaAtributos;
    }

    @Override
    public List getListByPagination(Object object) {
        Connection con = null;
        String rango = String.valueOf(object).replace("'", "");
        List<VistaAtributo> vistaAtributos = new ArrayList();
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select v.codigo,v.nombre,a.codigo,a.referencia,"
                    + "va.codigo,va.valor  from vista_atributo va join vista v "
                    + "on va.vista_codigo=v.codigo join"
                    + " atributo a on va.atributo_codigo=a.codigo where "
                    + " va.activo=1 limit " + rango;
            PreparedStatement pS = con.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                VistaAtributo vistaAtributo = new VistaAtributo();
                Vista vista = new Vista();
                Atributo atributo = new Atributo();
                vista.setCodigo(rS.getInt(1));
                vista.setNombre(rS.getString(2));
                atributo.setCodigo(rS.getInt(3));
                atributo.setReferencia(rS.getString(4));
                vistaAtributo.setCodigo(rS.getInt(5));
                vistaAtributo.setValor(rS.getString(6));
                vistaAtributo.setVistaCodigo(vista);
                vistaAtributo.setAtributoCodigo(atributo);
                vistaAtributos.add(vistaAtributo);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VistaAtributoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(VistaAtributoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VistaAtributoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(VistaAtributoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return vistaAtributos;
    }

}
