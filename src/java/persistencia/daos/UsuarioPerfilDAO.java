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
import persistencia.entidades.Perfil;
import persistencia.entidades.Usuario;
import persistencia.entidades.UsuarioPerfil;

/**
 *
 * @author manuel.alcala
 */
public class UsuarioPerfilDAO implements GestionDAO {

    @Override
    public int getCount(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListObject(Object object) {
        Usuario usuario = (Usuario) object;
        List<Perfil> perfiles=new ArrayList();
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select  p.codigo,p.nombre,c.codigo,c.nombre,c.direccion,c.telefono from usuario_perfil up join perfil p on up.perfil_codigo=p.codigo "
                    + "join comunidad c on p.comunidad_codigo=c.codigo where up.usuario_codigo=?";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1,usuario.getCodigo());
            ResultSet rS=pS.executeQuery();
            while(rS.next()){
                Perfil perfil=new Perfil();
                Comunidad comunidad=new Comunidad();
                perfil.setCodigo(rS.getInt(1));
                perfil.setNombre(rS.getString(2));
                comunidad.setCodigo(rS.getInt(3));
                comunidad.setNombre(rS.getString(4));
                comunidad.setDireccion(rS.getString(5));
                comunidad.setTelefono(rS.getString(6));
                perfil.setComunidad(comunidad);
                perfiles.add(perfil);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioPerfilDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioPerfilDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UsuarioPerfilDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            ConexionBD.cerrarConexion(con);
        }
        return perfiles;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
