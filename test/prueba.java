
import fachada.GestionFachada;
import fachada.RegistroFachada;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import persistencia.daos.GestionDAO;
import persistencia.daos.PerfilDAO;
import persistencia.entidades.Comunidad;
import persistencia.entidades.Perfil;
import persistencia.entidades.Registro;
import utilitarias.Cifrar;
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
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        System.out.println("VALOR:"+Cifrar.getEncryp("8509243222512345", ""));
    }

}
