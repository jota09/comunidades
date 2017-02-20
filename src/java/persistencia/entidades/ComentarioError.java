/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.entidades;

/**
 *
 * @author manuel.alcala
 */
public class ComentarioError {
    private int codigo;
    private String autor;
    private String comentario;

    public ComentarioError() {
    }

    public ComentarioError(int codigo) {
        this.codigo = codigo;
    }

    public ComentarioError(int codigo, String autor, String comentario) {
        this.codigo = codigo;
        this.autor = autor;
        this.comentario = comentario;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
}
