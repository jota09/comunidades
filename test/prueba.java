import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import persistencia.entidades.Error;
import persistencia.entidades.TipoError;
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
        int i=0;
        try{
         i=10/0;
        }catch(ArithmeticException ex){
            Error error=new Error();
            error.setClase("Prueba");
            error.setMetodo("Main");
            error.setDescripcion("error de prueba");
            error.setTipoError(new TipoError(1));
            Utilitaria.escribeError(error);
        }
        
    }
    
}
