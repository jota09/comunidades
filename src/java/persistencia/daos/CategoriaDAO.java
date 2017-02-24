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
import java.util.List;
import persistencia.conexion.ConexionBD;
import persistencia.entidades.Categoria;
import persistencia.entidades.TipoError;
import utilitarias.Utilitaria;
import persistencia.entidades.Error;

/**
 *
 * @author ferney.medina
 */
public class CategoriaDAO implements GestionDAO {

    @Override
    public Object getObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListObject() {
        ArrayList<Categoria> listCategoria = new ArrayList<Categoria>();
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String query = "SELECT * FROM categoria WHERE activo=1 ";
            PreparedStatement pS = con.prepareStatement(query);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Categoria cat = new Categoria();
                cat.setCodigo(rS.getInt("codigo"));
                cat.setCodigoPadre(rS.getInt("codigo_padre"));
                cat.setNombre(rS.getString("nombre"));
                cat.setListaCategorias(getHijos(cat.getCodigo()));
                listCategoria.add(cat);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListObject");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListObject");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getListObject");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return listCategoria;
    }

    private List<Categoria> getHijos(int codMenu) {
        ArrayList<Categoria> listaCategorias = new ArrayList<Categoria>();
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "SELECT * FROM categoria where codigo_padre=? and codigo_padre is not null";
            PreparedStatement pS = con.prepareStatement(sql);
            pS.setInt(1, codMenu);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Categoria cat = new Categoria();
                cat.setCodigo(rS.getInt("codigo"));
                cat.setCodigoPadre(rS.getInt("codigo_padre"));
                cat.setNombre(rS.getString("nombre"));
                cat.setListaCategorias(getHijos(cat.getCodigo()));
                listaCategorias.add(cat);
            }
            rS.close();
            pS.close();
        } catch (ClassNotFoundException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getHijos");
            error.setTipoError(new TipoError(1));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (SQLException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getHijos");
            error.setTipoError(new TipoError(2));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } catch (IOException ex) {
            Error error = new Error();
            error.setClase(getClass().getName());
            error.setMetodo("getHijos");
            error.setTipoError(new TipoError(3));
            error.setDescripcion(ex.getMessage());
            Utilitaria.escribeError(error);
        } finally {
            ConexionBD.cerrarConexion(con);
        }
        return listaCategorias;
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

    @Override
    public List getListByCondition(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListByPagination(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
