/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarias;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ferney.medina
 */
public class LecturaConfig {

    public static String getValue(String key) {
        Properties prop = new Properties();
        InputStream iS = null;
        try {
             File archivoConf = new File("/configComunidades/configuracion.properties"); // Para el local
           // File archivoConf = new File("/comunidades/configComunidades/configuracion.properties"); // Para el servidor
            //File archivoConf=new File("/configuracion.properties");
            iS = new FileInputStream(archivoConf);
            prop.load(iS);
        } catch (FileNotFoundException ex) {
            System.out.println("No se encuentra el archivo:" + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error de lectura:" + ex.getMessage());
        } finally {
            try {
                iS.close();
            } catch (IOException ex) {
                Logger.getLogger(LecturaConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return prop.getProperty(key);
    }
}
