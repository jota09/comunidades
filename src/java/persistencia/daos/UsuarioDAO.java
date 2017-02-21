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
                    + "usr.telefono,usr.user_name,usr.activo,"
                    + "sgUsr.contrasena,sgUsr.activo,usr.codigo_documento,usr.activo,sgUsr.activo,sgUsr.codigo FROM usuario usr "
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
                user.setNombres(rS.getString("nombres"));
                user.setApellidos(rS.getString("apellidos"));
                user.setCorreo(rS.getString("correo"));
                user.setCelular(rS.getString("celular"));
                user.setTelefono(rS.getString("telefono"));
                user.setCodigo(rS.getInt("codigo"));
                user.setUserName(rS.getString("user_name"));
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
    public List getListObject() {
        ArrayList<Usuario> listUsuario = new ArrayList<Usuario>();
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String query = "SELECT * FROM usuario WHERE activo=1";
            PreparedStatement pS = con.prepareStatement(query);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Usuario user = new Usuario();
                user.setCodigo(rS.getInt("codigo"));
                user.setUserName(rS.getString("user_name"));
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
