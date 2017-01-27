package persistencia.entidades;

import java.sql.Timestamp;

public class EventoReserva {

    private int codigo;
    private int usuariocodigo;
    private int usuariocodigoadm;
    private String descripcion;
    private Timestamp fechainicio;
    private Timestamp fechafin;
    private double costo;
    private short activo;
    private Timestamp creacion;
    private Timestamp actualizacion;
    private int calendariocodigo;
    private int inmueblecodigo;
    private int eventoestadocodigo;

    public EventoReserva() {
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

    public int getUsuarioCodigoAdm() {
        return this.usuariocodigoadm;
    }

    public void setUsuarioCodigoAdm(int usuariocodigoadm) {
        this.usuariocodigoadm = usuariocodigoadm;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Timestamp getFechaInicio() {
        return this.fechainicio;
    }

    public void setFechaInicio(Timestamp fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Timestamp getFechaFin() {
        return this.fechafin;
    }

    public void setFechaFin(Timestamp fechafin) {
        this.fechafin = fechafin;
    }

    public double getCosto() {
        return this.costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public short getActivo() {
        return this.activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }

    public Timestamp getCreacion() {
        return this.creacion;
    }

    public void setCreacion(Timestamp creacion) {
        this.creacion = creacion;
    }

    public Timestamp getActualizacion() {
        return this.actualizacion;
    }

    public void setActualizacion(Timestamp actualizacion) {
        this.actualizacion = actualizacion;
    }

    public int getCalendarioCodigo() {
        return this.calendariocodigo;
    }

    public void setCalendarioCodigo(int calendariocodigo) {
        this.calendariocodigo = calendariocodigo;
    }

    public int getInmuebleCodigo() {
        return this.inmueblecodigo;
    }

    public void setInmuebleCodigo(int inmueblecodigo) {
        this.inmueblecodigo = inmueblecodigo;
    }

    public int getEventoEstadoCodigo() {
        return this.eventoestadocodigo;
    }

    public void setEventoEstadoCodigo(int eventoestadocodigo) {
        this.eventoestadocodigo = eventoestadocodigo;
    }
}
