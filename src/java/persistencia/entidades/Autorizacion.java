package persistencia.entidades;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class Autorizacion implements Serializable {

    private int codigo;
    private int usuariocodigo;
    private Date fechaautorizacion;
    private Timestamp fecharealingreso;
    private String personaingresa;
    private int documentopersonaingresa;
    private EstadoAutorizacion estado;
    private MotivoAutorizacion motivo;
    private Timestamp fecharealsalida;
    private String empresacontratista;
    private short activo;

    public Autorizacion() {
    }

    public Autorizacion(int codigo) {
        this.codigo = codigo;
    }

    public Date getFechaautorizacion() {
        return fechaautorizacion;
    }

    public void setFechaautorizacion(Date fechaautorizacion) {
        this.fechaautorizacion = fechaautorizacion;
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

    public Timestamp getFechaRealIngreso() {
        return this.fecharealingreso;
    }

    public void setFechaRealIngreso(Timestamp fecharealingreso) {
        this.fecharealingreso = fecharealingreso;
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

    public EstadoAutorizacion getEstado() {
        return this.estado;
    }

    public void setEstado(EstadoAutorizacion estado) {
        this.estado = estado;
    }

    public MotivoAutorizacion getMotivo() {
        return this.motivo;
    }

    public void setMotivo(MotivoAutorizacion motivo) {
        this.motivo = motivo;
    }

    public Timestamp getFechaRealSalida() {
        return this.fecharealsalida;
    }

    public void setFechaRealSalida(Timestamp fecharealsalida) {
        this.fecharealsalida = fecharealsalida;
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
