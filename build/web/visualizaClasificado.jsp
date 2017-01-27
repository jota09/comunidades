<%-- 
    Document   : noticias
    Created on : 26/12/2016, 09:46:53 AM
    Author     : ferney.medina
--%>

<%@page import="utilitarias.Utilitaria"%>
<%@page import="persistencia.entidades.Estado"%>
<%@page import="java.util.List"%>
<%@page import="utilitarias.LecturaConfig"%>
<%@page import="fachada.CategoriaFachada"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%  CategoriaFachada catFachada=new CategoriaFachada(); %>    
<div>    
    <h2>
        <span class="glyphicon glyphicon-globe"></span>
        <%= LecturaConfig.getValue("titulo", "/configComunidades/"
                        + "visualizaClasificado.properties")%>
    </h2>
    <hr>  
    <div class="row">
        <div class='col-lg-2 pull-left' id='precios' style="overflow-y: scroll;">
            <h4>PRECIOS</h4>
            <hr>
            <ul class="ulPersonalizada">
                <li>Todos</li>
                <li>Entre 700.000-800.000</li>
                <li>Entre 600.000-700.000</li>
                <li>Entre 500.000-600.000</li>            
            </ul>
            <br/>
                <h4>CATEGORIAS</h4>
                <hr>
                <ul class="ulPersonalizada">
                    <% String vacio="";%>
                    <%=Utilitaria.construirCategorias(catFachada.getListObject(vacio)) %>
                </ul>                       
            
        </div> 
        
        <div class="col-lg-10">
            <div class="row">
               <div class="col-lg-4 col-lg-offset-1">
                    <h3>VENDO CELULAR IPHONE 6</h3>
                    <img src="resources/css/images/iphone.jpg" class="img-responsive imgArticulos" alt=""/>
                    <p class="precioArticulo">$850.000</p>
                    <a class="verMas" onclick="redirigir('verMas.jsp')">Ver más</a>
                </div>   
                <div class="col-lg-4 col-lg-offset-1">
                    <h3>VENDO CELULAR HUAWEI</h3>
                    <img src="resources/css/images/huawei-G7.jpg" class="img-responsive imgArticulos" alt=""/>
                    <p class="precioArticulo">$850.000</p>
                    <a class="verMas" onclick="redirigir('verMas.jsp')">Ver más</a>
                </div>  
            </div>
            <div class="row">
                <div class="col-lg-4 col-lg-offset-1">
                    <h3>VENDO CELULAR IPHONE 6</h3>
                    <img src="resources/css/images/iphone.jpg" class="img-responsive imgArticulos" alt=""/>
                    <p class="precioArticulo">$850.000</p>
                    <a class="verMas" onclick="redirigir('verMas.jsp')">Ver más</a>
                </div>   
                <div class="col-lg-4 col-lg-offset-1">
                    <h3>VENDO CELULAR HUAWEI</h3>
                    <img src="resources/css/images/huawei-G7.jpg" class="img-responsive imgArticulos" alt=""/>
                    <p class="precioArticulo">$850.000</p>
                    <a class="verMas" onclick="redirigir('verMas.jsp')">Ver más</a>
                </div>
            </div>             
        </div>
    </div>
    
        
       
    
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
