package persistencia.entidades;

import java.io.Serializable;
import java.sql.Timestamp;

public class Autorizacion implements Serializable {

    private int codigo;
    private int usuariocodigo;
    private Timestamp fechaingreso;
    private String personaingresa;
    private int documentopersonaingresa;
    private String estado;
    private String motivo;
    private Timestamp fechasalida;
    private String empresacontratista;
    private short activo;

    public Autorizacion() {
    }

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getUsuarioCodigo() {
        return this.usuariocodigo;
    }

    public void setUsuarioCodigo(int usuariocodigo) {
        this.usuariocodigo = usuariocodigo;
    }

    public Timestamp getFechaIngreso() {
        return this.fechaingreso;
    }

    public void setFechaIngreso(Timestamp fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public String getPersonaIngresa() {
        return this.personaingresa;
    }

    public void setPersonaIngresa(String personaingresa) {
        this.personaingresa = personaingresa;
    }

    public int getDocumentoPersonaIngresa() {
        return this.documentopersonaingresa;
    }

    public void setDocumentoPersonaIngresa(int documentopersonaingresa) {
        this.documentopersonaingresa = documentopersonaingresa;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMotivo() {
        return this.motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Timestamp getFechaSalida() {
        return this.fechasalida;
    }

    public void setFechaSalida(Timestamp fechasalida) {
        this.fechasalida = fechasalida;
    }

    public String getEmpresaContratista() {
        return this.empresacontratista;
    }

    public void setEmpresaContratista(String empresacontratista) {
        this.empresacontratista = empresacontratista;
    }

    public short getActivo() {
        return this.activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }
}
