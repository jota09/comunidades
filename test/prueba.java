
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
class metodo {
    static String metodoRaro(){
        int[] lista = {8,9,3};
        int n = lista.length;
        String[] simbolo = new String[n];
        int i,j,aux,izq,der,m;
        
        for(i=1;i<n;i++){
            aux = lista[i]; izq = 0; der = i-1;
            while(izq <= der){
                m = ((izq+der)/2);
                if(aux < lista[m]){
                    der = m-1;
                    simbolo[i-1] = "-";
                }else{
                    izq = m+1;
                    simbolo[i-1]="+";
                }
            }
            j=i-1;
            while(j>= izq){
                lista[j+1] = lista[j];
                j = j-1;
            }
            lista[izq] = aux;
        }
        simbolo[i-1] = "$";
        String salida = "";
        for(i=0;i<n;i++){
            salida += lista[i]+ simbolo[i];
        }
        return salida;
    }
}
