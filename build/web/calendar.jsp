<%-- 
    Document   : calendar
    Created on : 14/12/2016, 02:54:31 PM
    Author     : ferney.medina
--%>
<%@page import="utilitarias.Calendario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="resources/css/estiloCalendar.css" rel="stylesheet" type="text/css"/>
        <link href="resources/css/bootstrap-theme.css" rel="stylesheet" type="text/css"/>
        <link href="resources/css/bootstrap.css" rel="stylesheet" type="text/css"/>                
        <script src="resources/js/jquery-3.1.1.js" type="text/javascript"></script>
        <script src="resources/js/bootstrap.js" type="text/javascript"></script>
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Calendar</h1>
        <%  /*out.print(Calendario.obtenerMesActualCadena());
            out.print(Calendario.obtenerMesActualNumero());            
            int cantidadDiaMes=Calendario.obtenerDiaMes(2016, 9, 1);
            out.print(cantidadDiaMes+"<br>");
            int diaInicioMes=Calendario.obtenerDiaSemana(2016, 9, 1); 
            out.print(diaInicioMes);*/
            int cantidadDiaMes=Calendario.obtenerDiaMes(Calendario.obtenerAnioActualNumero(),Calendario.obtenerMesActualNumero(),1);
            int diaInicioMes=Calendario.obtenerDiaSemana(Calendario.obtenerAnioActualNumero(),Calendario.obtenerMesActualNumero(),1);
        %>
        
        <span id="flechaIzq" class="glyphicon glyphicon-chevron-left"></span>
        <div id='mes'>
            <% out.print(Calendario.obtenerMesActualCadena().toUpperCase()); %>
        </div>
        <span id="flechaDer" class="glyphicon glyphicon-chevron-right"></span>
        <table>
            <tr>
                <th>Domingo</th>
                <th>Lunes</th>
                <th>Martes</th>
                <th>Miercoles</th>
                <th>Jueves</th>
                <th>Viernes</th>
                <th>Sábado</th>
            </tr>
        <%            
            int j=1;       
            for(int i=1;j<=cantidadDiaMes;i++)
            {
                if(i%8==0 || i==1){     
                    if(i!=1){
                        out.print("</tr>");
                    }
                    out.println("<tr class='info'>");
                    if(i==1){
                        out.print("<td></td>");                    
                    }
                }else{
                    if(i>=diaInicioMes){                        
                        out.print("<td>"+j+"</td>");
                        if(j==cantidadDiaMes){
                          out.println("</tr>");
                        }
                        j++;
                    }else{
                        out.print("<td>&nbsp;</td>");
                    } 
                }
            }            
        %>
        </table>
    </body>
</html>
