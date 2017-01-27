$(document).ready(function ($) {   
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
                $('#mensajeFinal').append("<p>Noticia creada con éxito...</p><p>Pendiente la publicación de parte del administrador</p>");
            }
            
        },
        error: function (xhr, status) {
            $('#mensaje').modal('toggle');	                
            $('#mensajeFinal').append("Existio un problema");
        },
        /*complete: function (xhr, status) {
            alert('Petición realizada');
        }*/
    });    
}

