/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import persistencia.conexion.ConexionBD;
import persistencia.entidades.Atributo;
import persistencia.entidades.Recurso;
import persistencia.entidades.Vista;

/**
 *
 * @author manuel.alcala
 */
public class RecursoDAO implements GestionDAO {

    @Override
    public Object getObject(Object object) {
        Recurso recurso = (Recurso) object;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "select nombre,ruta from recurso where codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, recurso.getCodigo());
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                recurso.setNombre(rS.getString(1));
                recurso.setRuta(rS.getString(2));
            }
            rS.close();
            pS.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return recurso;
    }

    @Override
    public List getListObject(Object object) {
        Connection con = null;
        Vista vista = (Vista) object;
        List<Recurso> recursos = new ArrayList();
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "select rs.* from recurso rs join recurso_vista rv on "
                    + "rv.recurso_codigo=rs.codigo "
                    + "join vista vi on vi.codigo=rv.vista_codigo where vi.codigo=? and rs.activo=1";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, vista.getCodigo());
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Recurso recurso = new Recurso();
                recurso.setCodigo(rS.getInt(1));
                recurso.setNombre(rS.getString(2));
                recurso.setRuta(rS.getString(3));
                recursos.add(recurso);
            }
            rS.close();
            pS.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return recursos;
    }

    @Override
    public List getListObject() {
        Connection con = null;
        List<Recurso> recursos = new ArrayList();
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "select * from recurso where activo=1";
            PreparedStatement pS = con.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Recurso recurso = new Recurso();
                recurso.setCodigo(rS.getInt(1));
                recurso.setNombre(rS.getString(2));
                recurso.setRuta(rS.getString(3));
                recursos.add(recurso);
            }
            rS.close();
            pS.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return recursos;
    }

    @Override
    public int updateObject(Object object) {
        Connection con = null;
        Recurso recurso = (Recurso) object;
        int cantidad = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "update recurso set nombre=?,ruta=? where codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setString(1, recurso.getNombre());
            pS.setString(2, recurso.getRuta());
            pS.setInt(3, recurso.getCodigo());
            cantidad = pS.executeUpdate();
            pS.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return cantidad;
    }

    @Override
    public int insertObject(Object object) {
        Connection con = null;
        Recurso recurso = (Recurso) object;
        int cantidad = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "insert into recurso(nombre,ruta) values(?,?)";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setString(1, recurso.getNombre());
            pS.setString(2, recurso.getRuta());
            cantidad = pS.executeUpdate();
            pS.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return cantidad;
    }

    @Override
    public int getCount(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteObject(Object object) {
        Connection con = null;
        Recurso recurso = (Recurso) object;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "delete from recurso where codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, recurso.getCodigo());
            pS.execute();
            pS.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            ConexionBD.cerrarConexion(con);
        }
    }
}
