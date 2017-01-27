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
    public static String getValue(String key) throws FileNotFoundException, IOException
    {
        File archivoConf=new File(Utilitaria.getSystemProperty("CONFIGCOMUNIDADES"));
        //File archivoConf=new File("/configuracion.properties");
        InputStream iS=new FileInputStream(archivoConf);
        Properties prop=new Properties();
        prop.load(iS);
        return prop.getProperty(key);
    }
}
