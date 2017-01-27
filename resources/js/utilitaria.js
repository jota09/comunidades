/*FUNCIONES USADAS PARA LA VISTA INICIAL*/
$(document).ready(function ($) {
    /*$("#fecha").datepicker({dateFormat: 'yy-mm-dd'});
    $("#fechaFin").datepicker({dateFormat: 'yy-mm-dd', minDate: 0});
    $("#btnFecha").click(function () {
        //console.log("clic boton");
        //$("#fecha").datepicker({dateFormat:'yy-mm-dd'});
        //$("#fecha").datepicker({ minDate: 0, maxDate: "+1M +10D" });
        $("#fecha").datepicker({minDate: 0});
    });*/  
});

function redirigir(ruta) {
    
    $.get("/Comunidades",{view:ruta},function(data){
        $("#cuerpo").html(data);
    });
}