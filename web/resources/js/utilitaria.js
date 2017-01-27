/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function ($) {
    $("#fecha").datepicker({dateFormat: 'yy-mm-dd'});
    $("#fechaFin").datepicker({dateFormat: 'yy-mm-dd', minDate: 0});
    $("#btnFecha").click(function () {
        //console.log("clic boton");
        //$("#fecha").datepicker({dateFormat:'yy-mm-dd'});
        //$("#fecha").datepicker({ minDate: 0, maxDate: "+1M +10D" });
        $("#fecha").datepicker({minDate: 0});
    });
    tinymce.init({
        selector: '#descripcion',
        menubar: true,
        height: 400,
        plugins: [
            'advlist autolink lists link charmap print preview anchor',
            'searchreplace visualblocks code fullscreen',
            'insertdatetime media table contextmenu paste code'
        ],
        toolbar: 'undo redo | insert | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image'
    });   
});

function enviarNoticia()
{       
    console.log("descripcion"+descr);
    console.log("categoria"+cat);
    var tit=$("#titulo").val();
    var descr=tinyMCE.get('descripcion').getContent();
    var cat=$("#categoria").val();
    var parametros={
        titulo: tit,
        descripcion: descr,
        categoria: cat
    }
    $.ajax({        
        url: 'crearNoticia',
        data: parametros,        
        type: 'POST',
        async: true,
        success: function (datos) {            
            if(datos)
            {
                $('#mensaje').modal('toggle');	                
                $('#mensajeFinal').html("<p>Noticia creada con éxito...</p><p>Pendiente la publicación de parte del administrador</p>");
            }           
        },
        error: function (xhr, status) {
            $('#mensaje').modal('toggle');	                
            $('#mensajeFinal').append("Existio un problema");
        },
        complete: function (xhr, status) {
            $('#mensaje').modal('toggle');	                
            $('#mensajeFinal').html("<p>Noticia creada con éxito...</p><p>Pendiente la publicación de parte del administrador</p>");             
        }
    });    
}

function redirigir(url) {
    console.log(url);
    $('#cuerpo').load(url);
}

function mostrarNoticia(paginas,padre)
{    
    $("li").removeClass("active");
    var parent="#"+padre;    
    console.log("paginas:"+paginas+"padre:"+padre);
    $(parent).addClass("active");
    
    var parametros={rango:paginas};

    $.ajax({        
        url: 'Noticia',
        data: parametros,        
        type: 'POST',
        async: true,
        success: function (datos) {            
            if(datos)
            {
                $('#contenido').html(datos);
            }           
        },
        error: function (xhr, status) {
            $('#mensaje').modal('toggle');	                
            $('#mensajeFinal').append("Existio un problema al cargar las noticias");
        }
    });  
}
