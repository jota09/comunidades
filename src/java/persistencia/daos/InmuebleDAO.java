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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.conexion.ConexionBD;
import persistencia.entidades.Inmueble;
import persistencia.entidades.TipoInmueble;

/**
 *
 * @author manuel.alcala
 */
public class InmuebleDAO implements GestionDAO {

    @Override
    public int getCount(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getObject(Object object) {
        Inmueble inmueble = (Inmueble) object;
        Connection con = null;
        try {
            String sql = "Select inm.codigo,inm.ubicacion,tin.nombre from inmueble inm join tipo_inmueble tin on inm.tipo_inmueble_codigo=tin.codigo where inm.comunidad_codigo=? and inm.usuario_codigo=?";
            con = ConexionBD.obtenerConexion();
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, inmueble.getComunidadCodigo());
            pS.setInt(2, inmueble.getUsuarioCodigo());
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                TipoInmueble tipo = new TipoInmueble();
                inmueble.setCodigo(rS.getInt(1));
                inmueble.setUbicacion(rS.getString(2));
                tipo.setNombre(rS.getString(3));
                inmueble.setTipoInmuebleCodigo(tipo);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InmuebleDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InmuebleDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InmuebleDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return inmueble;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
