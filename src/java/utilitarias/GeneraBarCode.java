/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarias;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import net.sourceforge.jbarcodebean.JBarcodeBean;
import net.sourceforge.jbarcodebean.model.Code128;

/**
 *
 * @author manuel.alcala
 */
public class GeneraBarCode {

    public static String generarBase64(String codigo, int ancho, int alto, String extension) throws IOException {
        JBarcodeBean barcode = new JBarcodeBean();
        barcode.setCodeType(new Code128());
        barcode.setCode(codigo);
        barcode.setCheckDigit(true);
        BufferedImage bufferedImage = barcode.draw(new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB));
        ByteArrayOutputStream ouput = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, extension, ouput);
//        return "data:image/"+extension+";base64,"+DatatypeConverter.printBase64Binary(ouput.toByteArray());
    return DatatypeConverter.printBase64Binary(ouput.toByteArray());
    }
}
