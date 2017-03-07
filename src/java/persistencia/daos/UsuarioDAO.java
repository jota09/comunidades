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
import persistencia.entidades.Usuario;
import persistencia.entidades.Error;
import persistencia.entidades.TipoError;
import utilitarias.Utilitaria;

/**
 *
 * @author ferney.medina
 */
public class UsuarioDAO implements GestionDAO {

    @Override
    public Object getObject(Object obj) {
        Usuario user = (Usuario) obj;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "SELECT  usr.nombres,usr.apellidos,usr.correo,usr.celular,"
                    + "usr.telefono,usr.codigo,usr.user_name"
                    + " FROM usuario usr "
                    + "JOIN seguridad_usuario sgUsr ON sgUsr.usuario_codigo=usr.codigo "
                    + "WHERE ((usr.user_name=? or usr.correo=? or usr.codigo_documento=?) and sgUsr.contrasena=? and usr.activo=1 and sgUsr.activo=1) or usr.codigo=?";

            PreparedStatement pS = con.prepareStatement(sql);
            pS.setString(1, user.getUserName());
            pS.setString(2, user.getCorreo());
            pS.setInt(3, user.getCodigoDocumento());
            pS.setString(4, ((user.getListaSeguridad() != null) ? user.getListaSeguridad().getContrasena() : null));
            pS.setInt(5, user.getCodigo());
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                user.setNombres(rS.getString(1));
                user.setApellidos(rS.getString(2));
                user.setCorreo(rS.getString(3));
                user.setCelular(rS.getString(4));
                user.setTelefono(rS.getString(5));
                user.setCodigo(rS.getInt(6));
                user.setUserName(rS.getString(7));
            }
            rS.close();
            pS.close();
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
        return user;
    }

    @Override
    public List getListObject(Object object) {
        Comunidad comunidad = (Comunidad) object;
        ArrayList<Usuario> listUsuario = new ArrayList();
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String query = "SELECT usr.codigo,usr.codigo_documento,usr.nombres,"
                    + " usr.apellidos,usr.correo,usr.celular,usr.telefono,usr.user_name FROM usuario usr"
                    + " join usuario_perfil usrpf on usrpf.usuario_codigo=usr.codigo "
                    + " join perfil pf on usrpf.perfil_codigo=pf.codigo WHERE usr.activo=1 and pf.comunidad_codigo=?";
            PreparedStatement pS = con.prepareStatement(query);
            pS.setInt(1, comunidad.getCodigo());
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Usuario user = new Usuario();
                user.setCodigo(rS.getInt(1));
                user.setCodigoDocumento(rS.getInt(2));
                user.setNombres(rS.getString(3));
                user.setApellidos(rS.getString(4));
                user.setCorreo(rS.getString(5));
                user.setCelular(rS.getString(6));
                user.setTelefono(rS.getString(7));
                user.setUserName(rS.getString(8));
                listUsuario.add(user);
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
        return listUsuario;
    }

    @Override
    public int updateObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertObject(Object object) {
        Usuario user = (Usuario) object;
        Connection con = null;
        int tam = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "insert into usuario(codigo_documento,tipo_documento_codigo,nombres,apellidos,"
                    + "correo,celular,telefono,user_name,codigo) values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement pS = con.prepareStatement(sql);
            user.setCodigo(getMaxCodigo());
            pS.setInt(1, user.getCodigoDocumento());
            pS.setInt(2, user.getTipoDocumentoCodigo());
            pS.setString(3, user.getNombres());
            pS.setString(4, user.getApellidos());
            pS.setString(5, user.getCorreo());
            pS.setString(6, user.getCelular());
            pS.setString(7, user.getTelefono());
            pS.setString(8, user.getUserName());
            pS.setInt(9, user.getCodigo());
            tam = pS.executeUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return tam;
    }

    @Override
    public List getListObject() {
        ArrayList<Usuario> listUsuario = new ArrayList();
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String query = "SELECT codigo,codigo_documento,nombres,apellidos,correo,celular,telefono,user_name FROM usuario WHERE activo=1";
            PreparedStatement pS = con.prepareStatement(query);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Usuario user = new Usuario();
                user.setCodigo(rS.getInt(1));
                user.setCodigoDocumento(rS.getInt(2));
                user.setNombres(rS.getString(3));
                user.setApellidos(rS.getString(4));
                user.setCorreo(rS.getString(5));
                user.setCelular(rS.getString(6));
                user.setTelefono(rS.getString(7));
                user.setUserName(rS.getString(8));
                listUsuario.add(user);
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
        return listUsuario;
    }

    @Override
    public int getCount(Object obj) {
        Usuario user = (Usuario) obj;
        Connection con = null;
        int cont = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql;
            PreparedStatement pS;
            if (user != null) {
                sql = "Select count(codigo) from usuario where correo=?";
                pS = con.prepareStatement(sql);
                pS.setString(1, user.getCorreo());
            } else {
                sql = "Select count(codigo) from usuario";
                pS = con.prepareStatement(sql);
            }
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                cont = rS.getInt(1);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return cont;
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
            String sql = "SELECT MAX(codigo)+1 codigo FROM usuario ";
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
