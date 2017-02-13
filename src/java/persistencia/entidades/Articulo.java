package persistencia.entidades;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;


public class Articulo {

    private int codigo;
    private Usuario usuario;
    private int usuariocodigoadm;
    private String titulo;
    private String descripcion;
    private Date fechapublicacion;
    private double precio;
    private Date fechafinpublicacion;
    private Prioridad prioridad;
    private String observacionesadmon;
    private ArticuloEstado estado;
    private TipoArticulo tipoarticulo;
    private Categoria categoria;
    private Comunidad comunidad;
    private List<ClasificadoInteres> listaInteres;
    private String rango;
    private String busqueda;

    public Articulo() {
    }

    public Articulo(int codigo) {
        this.codigo = codigo;
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

    public Date getFechaPublicacion() {
        return this.fechapublicacion;
    }

    public void setFechaPublicacion(Date fechapublicacion) {
        this.fechapublicacion = fechapublicacion;
    }

    public double getPrecio() {
        return this.precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Date getFechaFinPublicacion() {
        return this.fechafinpublicacion;
    }

    public void setFechaFinPublicacion(Date fechafinpublicacion) {
        this.fechafinpublicacion = fechafinpublicacion;
    }

    public Prioridad getPrioridad() {
        return this.prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
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
    
    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }
    
    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }    

    @Override
    public String toString() {
        return "Articulo{" + "codigo=" + codigo + ", usuario=" + usuario + ", usuariocodigoadm=" + usuariocodigoadm + ", titulo=" + titulo + ", descripcion=" + descripcion + ", fechapublicacion=" + fechapublicacion + ", precio=" + precio + ", fechafinpublicacion=" + fechafinpublicacion + ", prioridad=" + prioridad + ", observacionesadmon=" + observacionesadmon + ", estado=" + estado + ", tipoarticulo=" + tipoarticulo + ", categoria=" + categoria + ", comunidad=" + comunidad + ", listaInteres=" + listaInteres + ", rango=" + rango + ", busqueda=" + busqueda + '}';
    }

    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }

    public Comunidad getComunidad() {
        return comunidad;
    }
    
}
