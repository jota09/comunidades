
import java.util.Map;
import java.util.Set;
import persistencia.daos.GestionDAO;
import persistencia.daos.MenuDAO;
import persistencia.daos.UsuarioDAO;
import persistencia.entidades.Usuario;

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
        /*Map<String, String> env = System.getenv();
        System.out.println("ruta:"+env.get("CONFIGCOMUNIDADES"));*/
        int doc=1010194766;
        System.out.println(doc);
        Usuario usr=new Usuario(1);
        UsuarioDAO usrDAO=new UsuarioDAO();
        usrDAO.getObject(usr);
        System.out.println(usr);
        
        
    }

}
