
import com.keepautomation.barcode.BarCode;
import com.keepautomation.barcode.IBarCode;
import com.lowagie.text.DocumentException;
import fachada.EstructuraFachada;
import fachada.GestionFachada;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.xml.bind.DatatypeConverter;
import net.sf.jasperreports.engine.JRException;
import net.sourceforge.jbarcodebean.model.Codabar;
import org.krysalis.barcode4j.BarcodeUtil;
import org.krysalis.barcode4j.impl.codabar.CodabarBean;
import org.krysalis.barcode4j.impl.code128.EAN128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import persistencia.entidades.Estructura;
import utilitarias.GeneraBarCode;
import utilitarias.ServicioDeEnvioMail;
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
    public static void main(String[] args) throws JRException, DocumentException, IOException, Exception {
        EAN128Bean bean = new EAN128Bean();
        final int dpi = 250;
        bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi));
        bean.setModuleWidth(0.1);
        bean.setTemplate("(415)n13(8020)n12(3900)n10(96)n8");
        bean.doQuietZone(false);
        File file = new File("c:/PDF/prueba.png");
        OutputStream os = new FileOutputStream(file);
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(os, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        bean.generateBarcode(canvas, "41577099981457408020002019400060390000006326509620170306");
        canvas.finish();
        os.close();
    }

}
