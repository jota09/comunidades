
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
        GestionDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = new Usuario(1);
        System.out.println(usuarioDAO.getObject(usuario));
        
    }
    
}
