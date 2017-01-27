<%@page import="utilitarias.Utilitaria"%>
<%@page import="persistencia.entidades.Estado"%>
<%@page import="java.util.List"%>
<%@page import="utilitarias.LecturaConfig"%>
<%@page import="fachada.EstadoFachada"%>
<%@page import="fachada.CategoriaFachada"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--<script src='https://cdn.tinymce.com/4/tinymce.min.js'></script>-->
<script src="resources/js/utilitaria.js" type="text/javascript"></script>
<div>
    <div class="row">
        <div class="col-xs-7 col-sm-6 col-md-3 col-lg-3">
            <h2>
                <span class="glyphicon glyphicon-picture"></span>
                <%= LecturaConfig.getValue("titulo", "/configComunidades/crearNoticias.properties")%>
            </h2>
        </div>        
    </div>
    <hr>
    <form method="POST" id="crearNoticia" >
    <!--<form method="POST" id="crearNoticia" onsubmit="return false">-->
        <div class='col-lg-10'>
            <input type="text" name="titulo" id="titulo" class="form-control" placeholder="Introduce el título aquí" />
            <br>
            <textarea id="descripcion" name="descripcion"></textarea>
        </div>    
        <%  CategoriaFachada catFachada=new CategoriaFachada(); %>        
        <div class='col-lg-2' id='categorias' style="overflow-y: scroll;">
            <h4>CATEGORIAS</h4>
            <hr>
            <ul class="ulPersonalizada">
                <% String vacio="";%>
                <%=Utilitaria.construirCategorias(catFachada.getListObject(vacio)) %>
            </ul>                       
        </div>
        <br/>
        <button class="btn btn-primary"><span class="glyphicon glyphicon-eye-open"></span>&nbsp;&nbsp;Vista previa</button>
        <button class="btn btn-primary" onclick="enviarNoticia()"><span class="glyphicon glyphicon-floppy-disk"></span>&nbsp;&nbsp;Guardar</button>         
    </form>
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
