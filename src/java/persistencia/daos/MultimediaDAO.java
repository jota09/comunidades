/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.conexion.ConexionBD;
import persistencia.entidades.Articulo;
import persistencia.entidades.Multimedia;

/**
 *
 * @author manuel.alcala
 */
public class MultimediaDAO implements GestionDAO {

    @Override
    public int getCount(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListObject(Object object) {
        Articulo art = (Articulo) object;
        ArrayList<Multimedia> listMult = new ArrayList<Multimedia>();
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String query = "SELECT * FROM multimedia WHERE articulo_codigo=?";
            PreparedStatement pS = con.prepareStatement(query);
            pS.setInt(1, art.getTipoArticulo().getCodigo());
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Multimedia mult=new Multimedia();
                mult.setCodigo(rS.getInt("codigo"));
                mult.setTipoMultimediaCodigo(rS.getInt("tipo_multimedia_codigo"));
                mult.setActivo(rS.getShort("activo"));
                mult.setDestacada(rS.getShort("destacada"));
                listMult.add(mult);
            }
            rS.close();
            pS.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listMult;
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
    public synchronized int insertObject(Object object) {
        Multimedia multimedia = (Multimedia) object;
        Connection con = null;
        int tamano = 0;
        try {
            int numeroAleatorio = (int) (Math.random()*9999+1000);
            con = ConexionBD.obtenerConexion();
            Calendar calendar=Calendar.getInstance();
            String sql = "INSERT INTO multimedia (CODIGO,ARTICULO_CODIGO, TIPO_MULTIMEDIA_CODIGO, DESTACADA) VALUES (?,?,?,?)";
            PreparedStatement pS = con.prepareStatement(sql);
            multimedia.setCodigo(calendar.getTimeInMillis()+numeroAleatorio);
            pS.setLong(1, multimedia.getCodigo());
            pS.setInt(2, multimedia.getArticulocodigo().getCodigo());
            pS.setInt(3, multimedia.getTipoMultimediaCodigo());
            pS.setShort(4,multimedia.getDestacada());
            tamano = pS.executeUpdate();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MultimediaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MultimediaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MultimediaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(MultimediaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tamano;
    }

    @Override
    public void deleteObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListByCondition(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListByPagination(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private synchronized int getMaxCodigo() {
        Connection con = null;
        int cont = 1;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "SELECT MAX(codigo)+1 codigo FROM multimedia ";
            PreparedStatement pS = con.prepareStatement(sql);
            ResultSet rS = pS.executeQuery();
            if (rS.next()) {
                int max=rS.getInt(1);
                cont = ((max>0)?max:cont);
            }
            rS.close();
            pS.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cont;
    }
}
