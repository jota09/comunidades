/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.conexion;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ferney.medina
 */
public class ConexionBD {
    private static String URL="jdbc:mysql://98.76.54.4:3306/comunidades";
    private static String USER="root";
    private static String PASS="hmt123";
    
    public  static Connection obtenerConexion() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        Connection con=DriverManager.getConnection(URL,USER,PASS);
        return con;
    }
    
    public static void cerrarConexion(Connection con)
    {
        try
        {
            con.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    
}
