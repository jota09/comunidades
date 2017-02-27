/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.conexion.ConexionBD;
import persistencia.entidades.Comunidad;
import persistencia.entidades.Estructura;
import persistencia.entidades.Registro;

/**
 *
 * @author manuel.alcala
 */
public class RegistroDAO implements GestionDAO {

    @Override
    public int getCount(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getObject(Object object) {
        Registro registro = (Registro) object;
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "Select codigo,comunidad_codigo from registro where codigo_generado=? ";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setString(1, registro.getCodigoGenerado());
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                registro.setCodigo(rS.getInt(1));
                Comunidad comunidad = new Comunidad();
                comunidad.setCodigo(rS.getInt(2));
                registro.setComunidad(comunidad);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegistroDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RegistroDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RegistroDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return registro;
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
        Registro registro = (Registro) object;
        GestionDAO estructuraDAO = new EstructuraDAO();
        Estructura estructura = new Estructura();
        estructura.setReferencia("tiempoVigenciaCodigoGenerado");
        estructuraDAO.getObject(estructura);
        Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_YEAR) + Integer.parseInt(estructura.getValor());
        calendar.set(Calendar.DAY_OF_YEAR, dia);
        Connection con = null;
        int tamano = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "insert into registro(codigo_generado,comunidad_codigo,fecha_vencimiento) values(?,?,?)";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setString(1, registro.getCodigoGenerado());
            pS.setInt(2, registro.getComunidad().getCodigo());
            pS.setDate(3, new Date(calendar.getTime().getTime()));
            tamano = pS.executeUpdate();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegistroDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RegistroDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RegistroDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return tamano;
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
