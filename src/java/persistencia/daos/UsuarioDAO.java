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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.conexion.ConexionBD;
import persistencia.entidades.Usuario;

/**
 *
 * @author ferney.medina
 */
public class UsuarioDAO implements GestionDAO{

    @Override
    public Object getObject(Object obj) {
        Usuario user=(Usuario)obj;
        Connection con=null;
        try{
            con=ConexionBD.obtenerConexion();
            String sql="SELECT  usr.nombres,usr.apellidos,usr.correo,usr.celular,"
                    + "usr.telefono,usr.user_name,usr.perfil_codigo,usr.activo,"
                    + "sgUsr.contrasena,sgUsr.activo,usr.codigo_documento,usr.activo,sgUsr.activo,sgUsr.codigo FROM usuario usr "
                    + "JOIN seguridad_usuario sgUsr ON sgUsr.usuario_codigo=usr.codigo "
                    + "WHERE (usr.user_name=? or usr.correo=? or usr.codigo_documento=?) and sgUsr.contrasena=? and usr.activo=1 and sgUsr.activo=1";
            PreparedStatement pS=con.prepareStatement(sql);
            pS.setString(1,user.getUserName());
            pS.setString(2,user.getCorreo());
            pS.setInt(3,user.getCodigoDocumento());
            pS.setString(4,user.getListaSeguridad().getContrasena());
            ResultSet rS=pS.executeQuery();
            if(rS.next())
            {
                System.out.println("dentro if");
                user.setNombres(rS.getString("nombres"));
                System.out.println("Apellidos del usuario:"+rS.getString("apellidos"));
                user.setApellidos(rS.getString("apellidos"));
                user.setCorreo(rS.getString("correo"));
                user.setCelular(rS.getString("celular"));
                user.setTelefono(rS.getString("telefono"));
                user.setCodigo(rS.getInt("codigo"));
                user.setPerfilCodigo(rS.getInt("perfil_codigo"));
            }
            rS.close();
            pS.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        @Override
    public int getCount(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
