
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.daos.GestionDAO;
import persistencia.daos.MenuDAO;
import persistencia.daos.MetaDataDAO;
import persistencia.daos.UsuarioDAO;
import persistencia.entidades.MetaData;
import persistencia.entidades.Usuario;
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
        MetaDataDAO metaDAO=new MetaDataDAO();
        List<MetaData> tablas=metaDAO.getTables();
        for(MetaData mt:tablas){
        System.out.println("Tabla:"+mt.getTabla());
            for(String s:mt.getColumna()){
                System.out.println("Columna:"+s);
            }
        }
        
    }
    
}
