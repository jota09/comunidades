<%-- 
    Document   : login
    Created on : 9/12/2016, 03:36:30 PM
    Author     : ferney.medina
--%>
<%@page import="utilitarias.LecturaConfig"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="resources/css/estiloLogin.css" rel="stylesheet" type="text/css"/>
        <link href="resources/css/bootstrap-theme.css" rel="stylesheet" type="text/css"/>
        <link href="resources/css/bootstrap.css" rel="stylesheet" type="text/css"/> 
        <script src="resources/js/jquery-3.1.1.js" type="text/javascript"></script>
        <script src="resources/js/bootstrap.js" type="text/javascript"></script>
        <title><%=LecturaConfig.getValue("titulo", "/configComunidades/login.properties") %></title>
    </head>
    <body>
        <div id="contenedor" class="col-xs-8 col-xs-offset-2 col-sm-8 col-sm-offset-2 col-md-offset-4 col-md-4 col-lg-3 col-lg-offset-4" > 
            <div class="panel panel-info">
                <form class="form-signin" action="validacion" method="POST">
                    <h2><strong><%=LecturaConfig.getValue("tituloFormulario", "/configComunidades/login.properties") %></strong></h2>
                    <img id="logoSesion" src="<%=LecturaConfig.getValue("pathLogoSesion", "/configComunidades/login.properties") %>" alt="logo" class="img-circle" />
                    <br/><br/>
                    <input class="form-control" name="username" type="text" placeholder="USUARIO" >
                    <input class="form-control" name="password" type="password" placeholder="CONTRASEÑA">
                    <%if (request.getAttribute("message") != null) {%>
                    <div class="alert alert-danger" role="alert"><%=request.getAttribute("message")%></div>
                    <%}%>
                    <button class="btn btn-lg btn-primary btn-block" type="submit"><span class="glyphicon glyphicon-log-in"></span>&nbsp;&nbsp;<%= LecturaConfig.getValue("nombreBoton", "/configComunidades/login.properties") %></button>
                </form>
            </div>        
        </div>
    </body>
</html>
