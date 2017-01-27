<%-- 
    Document   : insertarDocumento
    Created on : 2/01/2017, 10:27:52 AM
    Author     : ferney.medina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insertar documento</title>
<h3><span class="glyphicon glyphicon-upload"></span>&nbsp;Subida de Archivos</h3> 
<script src="resources/js/archivos.js" type="text/javascript"></script>
<hr>

<form id="subirArchivos" action="SubeArchivo" method="POST" enctype="multipart/form-data" > 
    <h4>Seleccione archivos:</h4>
    <input id="subida" type="file" name="subeArchivo" class="form-control" multiple><br/>
    <input class="btn btn-primary" type="button" value="Enviar" onclick="llamado()" >
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


