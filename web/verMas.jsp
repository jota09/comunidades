<%-- 
    Document   : verMas
    Created on : 2/01/2017, 10:20:45 PM
    Author     : ferney.medina
--%>

<%@page import="utilitarias.LecturaConfig"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="contenedor">
    <div class="col-lg-10" class="contenidoClasificado">
        <h4>CELULAR HUAWEI P8</h4>
        <hr> 
        <p>Publicado,22 diciembre de 2016</p>
        <p>Bogotá DC, comunidad hermanitas calle</p>
        <h2 class="pull-right">$850.000</h2>
        <img src="resources/css/images/huawei-G7.jpg" style="width: 70%;height: 70%;" class="img-responsive imgArticulos" alt=""/>
        <h4>Especificaciones Técnicas</h4>
        <p>MEMORIA RAM: 8GB</p>
        <p>ALMACENAMIENTO INTERNO: 16GB</p>
        <p>COLOR: Dorado</p>
        <p>GARANTÍA: 3años</p>        
    </div>
    <div class="col-lg-2 pull-right" class="contenidoContacto">
        <h4>CONTACTO</h4>
        <hr>
        <p><img src="<%=LecturaConfig.getValue("pathLogoSesion", "/configComunidades/login.properties") %>" alt="..." class="img-thumbnail"></p>
        <p><span class="glyphicon glyphicon-earphone"></span>&nbsp;&nbsp;3152563598</p>
        <textarea rows="10"></textarea>
        <button class="btn btn-primary">Enviar Mensaje</button>
    </div>
</div>
    
