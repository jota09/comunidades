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
import persistencia.entidades.Ciudad;
import persistencia.entidades.Comunidad;
import persistencia.entidades.TipoError;
import utilitarias.CondicionPaginado;
import utilitarias.Utilitaria;
import persistencia.entidades.Error;
import utilitarias.Visibilidad;

/**
 *
 * @author manuel.alcala
 */
public class ComunidadDAO implements GestionDAO {

    @Override
    public int getCount(Object object) {
        CondicionPaginado condicion = (CondicionPaginado) object;
        Connection con = null;
        int tamano = 0;
        try {
            con = ConexionBD.obtenerConexion();            
            String sql = "SELECT count(com.codigo) "
                    + "FROM comunidad com, ciudad C, departamento D, pais P "
                    + "WHERE com.CIUDAD_CODIGO = C.CODIGO AND C.departamento_CODIGO = D.CODIGO AND D.pais_CODIGO = P.CODIGO AND com.ACTIVO = 1 "
                    + condicion.getCondicion();
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
    public Object getObject(Object object) {
        Comunidad comunidad = (Comunidad) object;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select nombre,direccion,telefono,ciudad_codigo,nit,visibilidad,id_barcode from comunidad where codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, comunidad.getCodigo());
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                Ciudad ciudad = new Ciudad();
                ciudad.setCodigo(rS.getInt(4));
                comunidad.setNombre(rS.getString(1));
                comunidad.setDireccion(rS.getString(2));
                comunidad.setTelefono(rS.getString(3));
                comunidad.setCiudadCodigo(ciudad);
                comunidad.setNit(rS.getString(5));
                comunidad.setVisibilidad(new Visibilidad(rS.getShort(6)));
                comunidad.setIdBarCode(rS.getString(7));
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ComunidadDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ComunidadDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ComunidadDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return comunidad;
    }

    @Override
    public List getListObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListObject() {
        Connection con = null;
        List<Comunidad> comunidades = new ArrayList();
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select nombre,direccion,telefono,nit,id_barcode,codigo from comunidad ";
            PreparedStatement pS = con.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Comunidad comunidad = new Comunidad();
                comunidad.setCodigo(rS.getInt(6));
                comunidad.setNombre(rS.getString(1));
                comunidad.setDireccion(rS.getString(2));
                comunidad.setTelefono(rS.getString(3));
                comunidad.setNit(rS.getString(4));
                comunidad.setIdBarCode(rS.getString(5));
                comunidades.add(comunidad);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ComunidadDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ComunidadDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ComunidadDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return comunidades;
    }

    @Override
    public int updateObject(Object object) {
        Comunidad comunidad = (Comunidad) object;
        Connection con = null;
        int cantidad = 0;
        try{
            con = ConexionBD.obtenerConexion();
            String sql = "UPDATE comunidad SET";
            sql += (comunidad.getNit() != null && !comunidad.getNit().isEmpty() ? " NIT = '" + comunidad.getNit() + "',": "");
            sql += (comunidad.getNombre() != null && !comunidad.getNombre().isEmpty() ? " NOMBRE = '" + comunidad.getNombre() + "',": "");
            sql += (comunidad.getDireccion()!= null && !comunidad.getDireccion().isEmpty() ? " DIRECCION = '" + comunidad.getDireccion() + "',": "");
            sql += (comunidad.getTelefono()!= null && !comunidad.getTelefono().isEmpty() ? " TELEFONO = '" + comunidad.getTelefono() + "',": "");
            sql += (comunidad.getCiudadCodigo() != null ? " CIUDAD_CODIGO = " + comunidad.getCiudadCodigo().getCodigo() + ",": "");
            sql += (comunidad.getVisibilidad() != null ? "VISIBILIDAD = " + comunidad.getVisibilidad().getVisibilidad() + ",": "");
            sql += (comunidad.getIdBarCode() != null && !comunidad.getIdBarCode().isEmpty() ? "ID_BARCODE = '" + comunidad.getIdBarCode() + "',": "");
            sql += (comunidad.getActivo() != -1 ? " ACTIVO = " + comunidad.getActivo() + ",": "");
            sql = sql.substring(0,sql.length()-1);
            sql += " WHERE CODIGO = " + comunidad.getCodigo();
            PreparedStatement pS = con.prepareStatement(sql);
            System.out.println(pS);
            cantidad = pS.executeUpdate(sql);
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
        return cantidad;
    }

    @Override
    public int insertObject(Object object) {
        Comunidad comunidad = (Comunidad) object;
        Connection con = null;
        int resultado = 0;
        try{
            con = ConexionBD.obtenerConexion();
            String sql = "INSERT INTO comunidad "
                    + "(CODIGO,NIT,NOMBRE,DIRECCION,TELEFONO,CIUDAD_CODIGO,VISIBILIDAD"+((comunidad.getIdBarCode()!=null)?",ID_BARCODE":"")+")"
                    + "VALUES (?,?,?,?,?,?,?"+((comunidad.getIdBarCode()!=null)?",?":"")+")";
            PreparedStatement pS = con.prepareStatement(sql);
            comunidad.setCodigo(this.getMaxCodigo());
            pS.setInt(1, comunidad.getCodigo());
            pS.setString(2, comunidad.getNit());
            pS.setString(3, comunidad.getNombre());
            pS.setString(4, comunidad.getDireccion());
            pS.setString(5, comunidad.getTelefono());
            pS.setInt(6, comunidad.getCiudadCodigo().getCodigo());
            pS.setInt(7, comunidad.getVisibilidad().getVisibilidad());
            if(comunidad.getIdBarCode()!=null){
                pS.setString(8,comunidad.getIdBarCode());
            }
            resultado = pS.executeUpdate();
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
        return resultado;
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
        CondicionPaginado condicion = (CondicionPaginado) object;
        List<Comunidad> comunidades = new ArrayList<Comunidad>();
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "SELECT "
                    + "com.CODIGO, com.NIT, com.NOMBRE, com.DIRECCION, com.TELEFONO, "
                    + "C.NOMBRE as CIUDAD, D.NOMBRE as DEPARTAMENTO, P.NOMBRE as PAIS, "
                    + "com.ID_BARCODE, com.CREACION, com.VISIBILIDAD, "
                    + "(SELECT count(usr.codigo) from usuario usr "
                    + "join usuario_perfil usrp "
                    + "on usr.codigo=usrp.usuario_codigo "
                    + "join perfil p "
                    + "on usrp.perfil_CODIGO=p.codigo "
                    + "where p.comunidad_CODIGO=com.codigo) as MIEMBROS "
                    + "FROM comunidad com, ciudad C, departamento D, pais P "
                    + "WHERE com.CIUDAD_CODIGO = C.CODIGO AND C.departamento_CODIGO = D.CODIGO AND D.pais_CODIGO = P.CODIGO AND com.ACTIVO = 1 "           
                    + condicion.getCondicion();
            PreparedStatement pS = con.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Comunidad comunidad = new Comunidad();
                comunidad.setCodigo(rS.getInt("CODIGO"));
                comunidad.setNit(rS.getString("NIT"));
                comunidad.setNombre(rS.getString("NOMBRE"));
                comunidad.setDireccion(rS.getString("DIRECCION"));
                comunidad.setTelefono(rS.getString("TELEFONO"));
                comunidad.setCreacion(rS.getTimestamp("CREACION"));                
                comunidad.setIdBarCode(rS.getString("ID_BARCODE"));
                comunidad.setMiembros(rS.getInt("MIEMBROS"));
                comunidades.add(comunidad);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ComunidadDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ComunidadDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ComunidadDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return comunidades;
    }

    private int getMaxCodigo() {
        Connection con = null;
        int cont = 1;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "SELECT MAX(codigo)+1 codigo FROM comunidad";
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
