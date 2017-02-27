
import fachada.GestionFachada;
import fachada.RegistroFachada;
import persistencia.entidades.Comunidad;
import persistencia.entidades.Registro;
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
    public static void main(String[] args) {
        GestionFachada registroFachada = new RegistroFachada();
        Registro registro = new Registro();
        Comunidad comunidad = new Comunidad();
        comunidad.setCodigo(1);
        registro.setComunidad(comunidad);
        registro.setCodigoGenerado(Utilitaria.genCodigoRegComunidad(1));
        registroFachada.insertObject(registro);
    }

}
