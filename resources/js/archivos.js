function llamado()
{
    //e.preventDefault();//detiene la acción por defecto de un elemento, es decir de un formulario evita que se envie y un href a que no se envie
    //var f = $(this);
    var formData = new FormData(document.getElementById("subirArchivos"));
    //formData.append("dato", "valor");//para agregar valores adicionales al formulario
    //formData.append(f.attr("name"), $(this)[0].files[0]);
    $.ajax({
        url: "SubeArchivo",
        type: "post",
        dataType: "html",
        data: formData,
        cache: false,
        contentType: false,
        processData: false
    })
    .done(function (res) {
        //alert(res);
        $('#mensaje').modal('toggle');	                
        $('#mensajeFinal').html("<p>Archivo subido con éxito</p>");
    });
}

