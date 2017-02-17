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

/**
 *
 * @author ferney.medina
 */
public class LecturaConfig {

    public static String getValue(String key) {
        Properties prop = new Properties();
        try {
            File archivoConf = new File("c:/configComunidades/configuracion.properties");
            //File archivoConf=new File("/configuracion.properties");
            InputStream iS = new FileInputStream(archivoConf);

            prop.load(iS);
        } catch (FileNotFoundException ex) {
            System.out.println("No se encuentra el archivo:" + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error de lectura:" + ex.getMessage());
        }
        return prop.getProperty(key);
    }
}
