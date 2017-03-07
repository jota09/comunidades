
import persistencia.daos.GestionDAO;
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
        Usuario user = new Usuario();
        user.setCorreo("alejoab12@hotmailcom");
        System.out.println("EMAIL:" + usuarioDAO.getCount(user));
    }
    
}
