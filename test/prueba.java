
import com.lowagie.text.DocumentException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import net.sf.jasperreports.engine.JRException;
import org.krysalis.barcode4j.impl.code128.EAN128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import utilitarias.ServicioDeEnvioMail;

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
    public static void main(String[] args) throws JRException, DocumentException, IOException, Exception {
        ServicioDeEnvioMail servicio=new ServicioDeEnvioMail("98.76.54.4",25, "alejoab12@comunidades.com", "alejoab12", "m22102013", "false", "false", "false");
        servicio.sendEmail("hola mundo", "prueba envio", "malejoab1990@gmail.com", null, null, null);
    }

}

