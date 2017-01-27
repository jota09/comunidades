/*FUNCIONES USADAS PARA EL MODULO DE NOTICAS*/

/*funcion cargada para el inicio del modulo*/
$(document).ready(function ($) {    
	$("#crearNoticia").hide();
	mostrarNoticia('0,10','1',1)
	paginacion('Articulo',1)
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

/*funcion usada para crear la noticia y envia como respuesta
un alert de boostrap indicado la ejecución correcta*/
function enviarNoticia()
{       
    //console.log("descripcion"+descr);	
    console.log("categoria"+cat);
    var tit=$("#titulo").val();
    var descr=tinyMCE.get('descripcion').getContent();
    var cat=$("#categoria").val();
	
	if(cat==undefined)
		cat="0";
    var parametros={
        titulo: tit,
        descripcion: descr,
        categoria: cat,
		op:2,
		codArt:$("#codigoArticulo").val()		
    }
    $.ajax({        
        url: 'Noticia',
        data: parametros,        
        type: 'POST',
        async: true,
        success: function (datos) {            
            if(datos)
            {
                /*('#mensaje').modal('toggle');	*/
				console.log("estos son los datos:"+datos);
                /*$('#mensajeFinal').html("<p>Noticia creada con éxito...</p><p>Pendiente la publicación de parte del administrador</p>");*/
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

/*funcion usada para llamar al servlet encargado de la paginacion*/
function paginacion(texto,tipo){
	$.get("Pagina",{obj:texto,tipo:tipo},function(data){
        $("#piePagina").html(data);
    });
}

/*funcion creada para mostrar el div que contiene el formulario 
de la creacion de una noticia*/
function mostrarDiv(idmuestra,idoculta){
	var ident="#"+idmuestra;
	var identOculta="#"+idoculta;
	$(identOculta).hide();
	$(ident).show();
}

/*funcion usada para cargar las noticias en la tabla
y le asigna estilo a la pagina activa en la paginación*/
function mostrarNoticia(paginas,padre,tipo)
{    
    $("li").removeClass("active");
    var parent="#"+padre;    
    console.log("paginas:"+paginas+"padre:"+padre);
    $(parent).addClass("active");
	//alert(tipo);
    
    var parametros={rango:paginas,tipo:tipo,op:1};

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
            $('#mensajeFinal').html("Existio un problema al cargar las noticias");
        }
    });  
}

/*funcion que llama el div donde esta contenido el formulario de crear una noticia, pero 
carga los valores traidos por JSON para editarlos 
 */
function editarArt(ruta,opc,codArt){
	$("#mostrarNoticia").hide();
	$("#crearNoticia").show();
	$("#codigoArticulo").val(codArt);
	$.get(ruta,{op:opc,cod:codArt},function(data){
        var recepcion=JSON.parse(data);
		$("#titulo").val(recepcion.titulo);
		tinyMCE.get('descripcion').setContent(recepcion.descripcion);		
		//$("#descripcion").text(recepcion.descripcion);
		
    });
}
