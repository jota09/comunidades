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
public class UsuarioPerfil {
    private int codigo;
    private Perfil perfil;
    private Usuario usuario;

    public UsuarioPerfil() {
    }

    public UsuarioPerfil(int codigo) {
        this.codigo = codigo;
    }

    public UsuarioPerfil(int codigo, Perfil perfil, Usuario usuario) {
        this.codigo = codigo;
        this.perfil = perfil;
        this.usuario = usuario;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
