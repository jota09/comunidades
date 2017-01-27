<%-- 
    Document   : noticias
    Created on : 26/12/2016, 09:46:53 AM
    Author     : ferney.medina
--%>
<%@page import="persistencia.entidades.Articulo"%>
<%@page import="java.util.List"%>
<%@page import="fachada.ArticuloFachada"%>
<%@page import="utilitarias.LecturaConfig"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%  ArticuloFachada artFachada=new ArticuloFachada();
    String r="";
    List<Articulo> listArticulo=artFachada.getListObject(r);
    int cont=1;
    String pag="pag";
    int numRegistrosTabla=listArticulo.size();
    List<String> paginas=utilitarias.Utilitaria.getPaginacion(numRegistrosTabla, 5);
%>
<script src="resources/js/utilitaria.js" type="text/javascript"></script>
<script> mostrarNoticia('<%= paginas.get(0) %>') </script>
<div>
    <div class="row">
        <div class="col-xs-7 col-sm-6 col-md-3 col-lg-2">
            <h2>
                <span class="glyphicon glyphicon-picture"></span>
                <%= LecturaConfig.getValue("titulo", "/configComunidades/clasificado.properties")%>
            </h2>
        </div>
        <div class="col-xs-3 col-sm-3 col-md-2 col-lg-1">
            <button class="btn btn-primary" id="btn0" onclick="redirigir('crearClasificado.jsp')">
                <%= LecturaConfig.getValue("nombreBoton1", "/configComunidades/noticias.properties")%>
            </button>
        </div>
    </div>
    <hr>
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#todos" aria-controls="todos" role="tab" data-toggle="tab">Todos</a></li>
        <li role="presentation"><a href="#publicados" aria-controls="publicados" role="tab" data-toggle="tab">Publicados</a></li>
    </ul>

    <!-- Tab panes -->
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="todos">
            <br/>
            <div class="row">
                <button class="btn btn-primary" id="btn1">
                    <%= LecturaConfig.getValue("nombreBoton2", "/configComunidades/clasificado.properties")%>
                </button>
                <button class="btn btn-primary" id="btn2">
                    <%= LecturaConfig.getValue("nombreBoton3", "/configComunidades/clasificado.properties")%>
                </button>
                <button class="btn btn-primary" id="btn3">
                    <%= LecturaConfig.getValue("nombreBoton4", "/configComunidades/clasificado.properties")%>
                </button>
                <button class="btn btn-primary " id="btn4">
                    <%= LecturaConfig.getValue("nombreBoton5", "/configComunidades/clasificado.properties")%>
                </button>
            </div>
            <br/>           
            
            <div class="row table-responsive">
                <table class="table">                    
                    <thead>
                        <tr class="active">
                            <th><input type="checkbox"  name="seleccion" value="0" /></th>
                            <th>TITULO</th>
                            <th>AUTOR</th>
                            <th>CATEGORIA</th>
                            <th>PUBLICADO</th>
                            <th>FECHA</th>
                            <th>ACCIONES</th>
                        </tr>  
                    </thead>
                    <tbody id="contenido">                                                                    
                    </tbody>
                </table>
            </div>
            <nav aria-label="Page navigation">
                <ul class="pagination">
                  <li>
                    <a href="#" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                    </a>
                  </li>
                  <%                     
                      for(String p:paginas)
                      {
                  %>
                        <li><a id="<%= pag+"-"+cont %>" onclick="mostrarNoticia('<%=p %>')" ><%= cont %></a></li>
                  <%   
                        cont++;
                      }                  
                  %>                  
                  <li>
                    <a href="#" aria-label="Next">
                      <span aria-hidden="true">&raquo;</span>
                    </a>
                  </li>
                </ul>
            </nav>
            <button class="btn btn-primary" id="btn5">
                <%= LecturaConfig.getValue("nombreBoton6", "/configComunidades/clasificado.properties")%>
            </button>
            <button class="btn btn-primary" id="btn6">
                <%= LecturaConfig.getValue("nombreBoton7", "/configComunidades/clasificado.properties")%>
            </button>
        </div>        
        <div role="tabpanel" class="tab-pane" id="publicados"></div>
        <div role="tabpanel" class="tab-pane" id="aprobados"></div>
    </div>
    <div class="modal fade" id="mensaje">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;&nbsp;Información</h4>
                </div>
                <div class="modal-body" id='mensajeFinal'>                        
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>        
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</div>
