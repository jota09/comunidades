<%-- 
    Document   : menuprincipal
    Created on : 22/12/2016, 11:05:54 AM
    Author     : ferney.medina
--%>

<%@page import="utilitarias.Utilitaria"%>
<%@page import="utilitarias.LecturaConfig"%>
<%@page import="persistencia.entidades.Menu"%>
<%@page import="java.util.List"%>
<%@page import="persistencia.entidades.Perfil"%>
<%@page import="fachada.MenuFachada"%>
<%@page import="persistencia.entidades.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%  Usuario usr = (Usuario) request.getSession().getAttribute("user");    
    if (usr != null) {
        Perfil pf = new Perfil();
        pf.setCodigo(usr.getPerfilCodigo());
        MenuFachada menFac = new MenuFachada();
        List<Menu> menus = menFac.getListObject(pf);

        /*for(Menu m:menus){
        out.println(m.getNombre()+" "+m.getCodigoPadre()+"<br>");
        for(Menu m2:m.getListaMenu()){
            out.println(m2.getNombre()+" "+m.getCodigoPadre()+"<br>");
        }
    }*/        
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" type="image/gif" href="resources/img/group.png">
        <link href="resources/css/estiloMenu.css" rel="stylesheet" type="text/css"/> 
        <link href="resources/css/bootstrap-theme.css" rel="stylesheet" type="text/css"/>
        <link href="resources/css/bootstrap.css" rel="stylesheet" type="text/css"/> 
        <link href="resources/css/estiloMenuPrincipal.css" rel="stylesheet" type="text/css"/>
        <link href="resources/css/jquery-ui.css" rel="stylesheet" type="text/css"/>
        <script src="resources/js/jquery-3.1.1.js" type="text/javascript"></script>
        <script src="resources/js/bootstrap.js" type="text/javascript"></script>
        <script src="resources/js/jquery-ui.min.js" type="text/javascript"></script>        
        <!--<script src="resources/js/tinymce/tinymce.min.js" type="text/javascript"></script>-->
        <script src='https://cdn.tinymce.com/4/tinymce.min.js'></script>         
        <script src="resources/js/utilitaria.js" type="text/javascript"></script>
        <!--<script src="resources/js/crearnoticia.js" type="text/javascript"></script>-->
        <title><%=LecturaConfig.getValue("titulo")%></title>
    </head>
    <body onload="redirigir('noticias.jsp')">
        <nav class="navbar navbar-inverse">            
            <div class="container-fluid">                
                <a class="navbar-brand" href="#"><img alt="Brand" src="resources/img/group.png"></a>
                <div class="navbar-header">                                     
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#colapsa" aria-expanded="false">
                        <span class="sr-only"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <div class="navbar-collapse collapse" id="colapsa">
                    <ul class="nav navbar-nav multi-level">
                        <%=Utilitaria.construirMenu(menus)%>                        
                    </ul>
                    <ul class="nav navbar-nav pull-right" style="cursor: pointer">
                        <li>
                            <a onclick="$('#cierre').submit()">
                                <form id="cierre" name="cierre" action="CerrarSesion" method="POST">
                                    <span class="glyphicon glyphicon-log-out"></span>&nbsp;Salir
                                </form>
                            </a>
                        </li>
                    </ul>
                </div>                 
            </div>            
        </nav>
        <div id="cuerpo"></div>
    </body>
</html>
<% } else {
        response.sendRedirect("login.jsp");
    }
    
%>