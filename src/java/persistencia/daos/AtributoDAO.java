/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.conexion.ConexionBD;
import persistencia.entidades.Atributo;
import persistencia.entidades.Vista;

/**
 *
 * @author manuel.alcala
 */
public class AtributoDAO implements GestionDAO {

    @Override
    public Object getObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListObject(Object object) {
        Connection con=null;
        Vista vista=(Vista)object;
        List<Atributo> atributos=new ArrayList();
        try{
            con=ConexionBD.obtenerConexion();
            String sql="Select  atr.referencia,vat.valor from atributo atr "
                    + "join vista_atributo vat on vat.atributo_codigo=atr.codigo "
                    + "join vista vi on vat.vista_codigo=vi.codigo where vi.codigo=?";
            PreparedStatement pS=con.prepareStatement(sql);
            pS.setInt(1, vista.getCodigo());
            ResultSet rS=pS.executeQuery();
            while(rS.next()){
                Atributo atributo=new Atributo();
                atributo.setReferencia(rS.getString(1));
                atributo.setValor(rS.getString(2));
                atributos.add(atributo);
            }
            rS.close();
            pS.close();
        }catch(SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }finally{
            ConexionBD.cerrarConexion(con);
        }
        return atributos;
    }

    @Override
    public List getListObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getCount(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
