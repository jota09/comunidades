/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarias;

import com.sun.javafx.css.CalculatedValue;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.conexion.ConexionBD;
import persistencia.entidades.Proceso;

/**
 *
 * @author manuel.alcala
 */
public class EjecutaProcedimiento {

    public static int ejecutarProcedimientoFacturacion(Proceso proceso) {
        Connection con = null;
        int tam = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "CALL poblaFacturacion(?)";
            CallableStatement call = con.prepareCall(sql);
            call.setInt(1, proceso.getCodigo());
            tam = call.executeUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EjecutaProcedimiento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EjecutaProcedimiento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EjecutaProcedimiento.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return tam;
    }
}
