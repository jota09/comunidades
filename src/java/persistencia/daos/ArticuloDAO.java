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
import persistencia.entidades.Articulo;
import persistencia.entidades.ArticuloEstado;
import persistencia.entidades.Categoria;
import persistencia.entidades.TipoArticulo;
import persistencia.entidades.Usuario;

/**
 *
 * @author ferney.medina
 */
public class ArticuloDAO implements GestionDAO {

    @Override
    public Object getObject(Object object) {
        Articulo art = (Articulo) object;
        try {
            Connection con=null;
            con=ConexionBD.obtenerConexion();
            String query = "SELECT * FROM articulo where codigo=?";
            PreparedStatement pS=con.prepareStatement(query);
            pS.setInt(1, art.getCodigo());
            ResultSet rS=pS.executeQuery();
            if(rS.next())
            {
                Usuario usr=new Usuario();
                TipoArticulo tpArt=new TipoArticulo();
                tpArt.setCodigo(rS.getInt("tipo_articulo_codigo"));
                usr.setCodigo(rS.getInt("usuario_codigo"));                
                Categoria cat = new Categoria();
                ArticuloEstado estado=new ArticuloEstado(rS.getInt("estados_codigo"));                
                art.setUsuario(usr);
                cat.setCodigo(rS.getInt("categoria_codigo"));                
                art.setCategoria(cat);
                art.setTipoArticulo(tpArt);
                art.setTitulo(rS.getString("titulo"));
                art.setDescripcion(rS.getString("descripcion"));
                art.setFechaPublicacion(rS.getTimestamp("fecha_publicacion"));
                art.setFechaFinPublicacion(rS.getTimestamp("fecha_fin_publicacion"));
                art.setActivo(rS.getShort("activo"));
                art.setEstado(estado);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return art;
    }

    @Override
    public List getListObject(Object obj) {
        TipoArticulo tpArt = (TipoArticulo) obj;
        ArrayList<Articulo> listArt = new ArrayList<Articulo>();
        String rango = "";
        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            if (!tpArt.getRango().isEmpty()) {
                rango = "limit " + tpArt.getRango();
            } else {
                rango = "";
            }
            String query = "SELECT art.*,usr.codigo,usr.nombres,usr.apellidos,cat.* FROM articulo art JOIN "
                    + "usuario usr ON  art.usuario_codigo=usr.codigo JOIN categoria cat ON art.categoria_codigo=cat.codigo "
                    + "where tipo_articulo_codigo=? "
                    + rango;
            PreparedStatement pS = con.prepareStatement(query);
            pS.setInt(1, tpArt.getCodigo());
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                Articulo art = new Articulo();
                Categoria cat = new Categoria();
                ArticuloEstado estado=new ArticuloEstado(rS.getInt("estados_codigo"));
                Usuario usr = new Usuario();
                art.setCodigo(rS.getInt("codigo"));
                usr.setCodigo(rS.getInt("usuario_codigo"));
                usr.setNombres(rS.getString("nombres"));
                usr.setApellidos(rS.getString("apellidos"));
                art.setUsuario(usr);
                cat.setCodigo(rS.getInt("codigo"));
                cat.setNombre(rS.getString("nombre"));
                art.setCategoria(cat);
                art.setTitulo(rS.getString("titulo"));
                art.setDescripcion(rS.getString("descripcion"));
                art.setFechaPublicacion(rS.getTimestamp("fecha_publicacion"));
                art.setFechaFinPublicacion(rS.getTimestamp("fecha_fin_publicacion"));
                art.setActivo(rS.getShort("activo"));
                art.setEstado(estado);
                listArt.add(art);
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
        return listArt;
    }

    @Override
    public int updateObject(Object object) {
        Articulo art = (Articulo) object;
        Connection con = null;
        int numfilas=0;
        try {
            con=ConexionBD.obtenerConexion();
            String sql="UPDATE articulo SET USUARIO_CODIGO=?, TITULO=?, DESCRIPCION=?,"
                    + "FECHA_PUBLICACION=?, PRECIO=?,FECHA_FIN_PUBLICACION=?, PRIORIDAD=?,"
                    + "ESTADOS_CODIGO=?,TIPO_ARTICULO_CODIGO=?,CATEGORIA_CODIGO=? "
                    + " WHERE CODIGO=?";
            PreparedStatement pS=con.prepareStatement(sql);
            pS.setInt(1, art.getUsuario().getCodigo());
            pS.setString(2, art.getTitulo());
            pS.setString(3, art.getDescripcion());
            pS.setTimestamp(4, art.getFechaPublicacion());
            pS.setDouble(5, art.getPrecio());
            pS.setTimestamp(6, art.getFechaFinPublicacion());
            pS.setString(7, art.getPrioridad());
            pS.setInt(8, art.getEstado().getCodigo());
            pS.setInt(9, art.getTipoArticulo().getCodigo());
            pS.setInt(10, art.getCategoria().getCodigo());
            pS.setInt(11, art.getCodigo());
            numfilas=pS.executeUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return numfilas;        
    }

    @Override
    public int insertObject(Object object) {
        Articulo art = (Articulo) object;
        Connection con = null;
        int result = 0;
        try {
            con = ConexionBD.obtenerConexion();
            String sql = "INSERT INTO articulo( USUARIO_CODIGO, TITULO, DESCRIPCION,"
                    + "FECHA_PUBLICACION, PRECIO,FECHA_FIN_PUBLICACION, PRIORIDAD,"
                    + "ESTADOS_CODIGO,TIPO_ARTICULO_CODIGO,CATEGORIA_CODIGO) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?)";
            try (PreparedStatement pS = con.prepareStatement(sql)) {
                pS.setInt(1, art.getUsuario().getCodigo());
                pS.setString(2, art.getTitulo());
                pS.setString(3, art.getDescripcion());
                pS.setTimestamp(4, art.getFechaPublicacion());
                pS.setDouble(5, art.getPrecio());
                pS.setTimestamp(6, art.getFechaFinPublicacion());
                pS.setString(7, art.getPrioridad());
                pS.setInt(8, art.getEstado().getCodigo());
                pS.setInt(9, art.getTipoArticulo().getCodigo());
                pS.setInt(10, art.getCategoria().getCodigo());
                result = pS.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    @Override
    public List getListObject() {
        return null;
    }

    @Override
    public int getCount(Object obj) {
        Connection con=null;
        int cont=0;
        try{
            con=ConexionBD.obtenerConexion();
            String sql="SELECT COUNT('CODIGO') FROM articulo ";
            PreparedStatement pS=con.prepareStatement(sql);
            ResultSet rS=pS.executeQuery();            
            if(rS.next()){
                cont=rS.getInt(1);
                cont++;
            }else
            {
                cont++;
            }                
            rS.close();
            pS.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cont;
    }

    @Override
    public void deleteObject(Object object) {
        Articulo art = (Articulo) object;
        Connection con = null;
        try {
            con=ConexionBD.obtenerConexion();
            String sql="DELETE FROM articulo WHERE CODIGO=? AND TIPO_ARTICULO_CODIGO=?";
            System.out.println("sql:"+sql);
            PreparedStatement pS=con.prepareStatement(sql);
            pS.setInt(1, art.getCodigo());
            pS.setInt(2, art.getTipoArticulo().getCodigo());
            pS.execute();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
