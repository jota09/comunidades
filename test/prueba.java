
import com.lowagie.text.DocumentException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import sun.misc.BASE64Decoder;
import utilitarias.GeneraBarCode;
import utilitarias.HTML_A_PDF;
import utilitarias.LecturaConfig;
import utilitarias.Utilitaria;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ferney.medina
 */
public class prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws JRException, IOException, DocumentException {
       
        System.out.println("Base 64:"+Utilitaria.generaPDFB64("Factura Administración.jasper", null));
    }

}
