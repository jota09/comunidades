/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.conexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import utilitarias.LecturaConfig;

/**
 *
 * @author ferney.medina
 */
public class ConexionBD {
    public static Connection obtenerConexion() throws ClassNotFoundException, SQLException, IOException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(LecturaConfig.getValue("url"), LecturaConfig.getValue("user"), LecturaConfig.getValue("pass"));
        return con;
    }
    public static void cerrarConexion(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
