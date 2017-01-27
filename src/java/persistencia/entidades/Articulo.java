package persistencia.entidades;

import java.sql.Timestamp;
import java.util.List;


public class Articulo {

    private int codigo;
    private Usuario usuario;
    private int usuariocodigoadm;
    private String titulo;
    private String descripcion;
    private Timestamp fechapublicacion;
    private double precio;
    private Timestamp fechafinpublicacion;
    private double costo;
    private String prioridad;
    private short activo;
    private Timestamp creacion;
    private Timestamp actualizacion;
    private short eliminado;
    private String observacionesadmon;
    private ArticuloEstado estado;
    private TipoArticulo tipoarticulo;
    private Categoria categoria;
    private List<ClasificadoInteres> listaInteres;


    public Articulo() {
    }

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getUsuarioCodigoAdm() {
        return this.usuariocodigoadm;
    }

    public void setUsuarioCodigoAdm(int usuariocodigoadm) {
        this.usuariocodigoadm = usuariocodigoadm;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Timestamp getFechaPublicacion() {
        return this.fechapublicacion;
    }

    public void setFechaPublicacion(Timestamp fechapublicacion) {
        this.fechapublicacion = fechapublicacion;
    }

    public double getPrecio() {
        return this.precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Timestamp getFechaFinPublicacion() {
        return this.fechafinpublicacion;
    }

    public void setFechaFinPublicacion(Timestamp fechafinpublicacion) {
        this.fechafinpublicacion = fechafinpublicacion;
    }

    public double getCosto() {
        return this.costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getPrioridad() {
        return this.prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
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

    public short getEliminado() {
        return this.eliminado;
    }

    public void setEliminado(short eliminado) {
        this.eliminado = eliminado;
    }

    public String getObservacionesAdmon() {
        return this.observacionesadmon;
    }

    public void setObservacionesAdmon(String observacionesadmon) {
        this.observacionesadmon = observacionesadmon;
    }

    public ArticuloEstado getEstado() {
        return this.estado;
    }

    public void setEstado(ArticuloEstado estado) {
        this.estado = estado;
    }

    public TipoArticulo getTipoArticulo() {
        return this.tipoarticulo;
    }

    public void setTipoArticulo(TipoArticulo tipoarticulo) {
        this.tipoarticulo = tipoarticulo;
    }
    
    public Categoria getCategoria(){
        return categoria;
    }
    
    public void setCategoria(Categoria categoria){
        this.categoria = categoria;
    }
    
    public List<ClasificadoInteres> getListaInteres(){
        return listaInteres;
    }
    
    public void setListaInteres(List<ClasificadoInteres> listaInteres){
        this.listaInteres = listaInteres;
    }
}
