/*FUNCIONES USADAS PARA EL MODULO DE MIS CLASIFICADOS*/

/*funcion cargada para el inicio del modulo*/
var tipoArt;/*variable global para declarar el tipo de articulo:1:clasificado;2:clasificados*/
var array = {};
var tamArray = 0;
var autor;
inicioClasificado();

/*funcion usada para activar los check box de cada registro*/
function seleccionTodos() {

    if ($("#todosCheck").is(':checked'))
    {
        $("input[name='seleccion']").prop('checked', true);
    } else {
        $("input[name='seleccion']").prop('checked', false);
    }

}
/*funcion creada para crear el contenedor de imagenes previsualizadas
 y ademÃ¡s asignar los files al objeto de tipo array*/
$("#cargaFile").on("change", function () {
    array = {};
    tamArray = 0;
    var file = $('#cargaFile')[0].files;
    $("#previsualizar").html("");
    $("#previsualizar").html("<div class='col-xs-12'>Archivos seleccionados<label>(Escoja la imagen destacada):</label>");
    $("#previsualizar").css("backgroundColor", "rgb(238, 238, 238)");
    for (var i = 0; i < file.length; i++)
    {
        tamArray++;
        muestraImg(file[i], i);
        array[file[i].name] = file[i];
    }
    $("#imagenesDescargadas").val("");
});

function dibujarImg(base64, cont, name, destacada) {
    $("#previsualizar").append("<div class='col-xs-6 col-sm-4 col-md-2 col-lg-2' id='img-" + cont + "'"
            + " style='display:block; width:200px; height:200px;'><input type='radio' name='imagenDestacada' class='pull-left' value='" + name + "' " + ((destacada === 1) ? "checked" : "") + ">" +
            "<span class='glyphicon glyphicon-remove pull-right' onclick=\"eliminaImagen(" + cont + ",'" + name + "')\">" +
            "</span><br><img class='img-responsive img-thumbnail' alt='" + name + "' src='" + base64 + "' style='display:block; width:160px; height:160px;'></div></div>");
}


/*funcion creada para visualizar las imagenes al ser cargadas desde
 el input file*/
function muestraImg(file, cont) {
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function () {
        $("#previsualizar").append("<div class='col-xs-6 col-sm-4 col-md-2 col-lg-2' id='img-" + cont + "'"
                + " style='display:block; width:200px; height:200px;'><input type='radio' name='imagenDestacada' class='pull-left' value='" + file.name + "' " + ((cont == 0) ? "checked" : "") + ">" +
                "<span class='glyphicon glyphicon-remove pull-right' onclick=\"eliminaImagen(" + cont + ",'" + file.name + "')\">" +
                "</span><br><img class='img-responsive img-thumbnail' alt='" + file.name + "' src='" + reader.result + "' style='display:block; width:160px; height:160px;'></div></div>");
    }
}

/*funcion usada para eliminar las imagenes previsualizadas*/
function eliminaImagen(padre, name) {
    var nameAtr = '#img-' + padre;
    $(nameAtr).remove();
    delete array[name];
    tamArray--;
}

function cargarVisualizacion(contenidoActualizado, url, title, cargarPagina) {
    var finPublicacion = $("#finPublicacion").val();
    var src = "";
    var descripcion = "";
    if (contenidoActualizado === "")
    {
        descripcion = tinyMCE.get('descripcion').getContent();
    }
    if (url === undefined)
    {
        var alt = $("input[name='imagenDestacada']:checked").val();
        src = $("img[alt='" + alt + "']").attr("src");
    } else {
        src = url;
    }
    var esquemaContenido = "";
    //if($("input[name='imagenDestacada']:checked").val()!=undefined)
    if (src != undefined && src != "")
    {
        esquemaContenido = "<div class='row' ><div class='col-xs-12 col-md-offset-2 col-md-8 col-lg-offset-2 col-lg-8' id='tituloVisualizador'>" +
                "<h2>" + ((title != undefined) ? title : $("#titulo").val()) + "</h2><hr></div></div>" +
                "<div	class='row'	><div class='col-xs-12 col-md-offset-2 col-md-4 col-lg-offset-2 col-lg-2 '><img id='imagenDestacadaVisualizar' class='center-block' src='" + src + "' style='max-width:100%'  ></div>" +
                "<div class='col-xs-12 col-md-6 col-lg-6'>" + ((contenidoActualizado != undefined && contenidoActualizado != "") ? contenidoActualizado : descripcion) + "</div></div><br><br>" +
                "<div	class='row'	title='Autor'><div class='col-xs-12 col-md-offset-2 col-md-4 col-lg-offset-2 col-lg-4'>" +
                "<span class='glyphicon glyphicon-user'>&nbsp;<strong>Autor:&nbsp;</strong></span>" + autor + "</div>" +
                "<div class='col-xs-12 col-md-6 col-lg-6 ' ><span class='glyphicon glyphicon-calendar'>&nbsp;<strong>Fin publicaciÃ³n:&nbsp;</strong></span>" + finPublicacion + "</div></div>" +
                "<br><div class='row'><div class='col-xs-12 col-md-offset-2 col-md-8 col-lg-offset-2 col-lg-8'>" +
                "<input type='button' class='btn btn-primary' value='Regresar' onclick=\"mostrarDiv('" + ((cargarPagina != undefined) ? cargarPagina : 'crearClasificado') + "','visualizarClasificado')\"></div></div>";
    } else {
        esquemaContenido = "<div class='row' ><div class='col-xs-12 col-md-offset-2 col-md-8 col-lg-offset-2 col-lg-8' id='tituloVisualizador'>" +
                "<h2>" + ((title != undefined) ? title : $("#titulo").val()) + "</h2><hr></div></div>" +
                "<div	class='row'	><div class='col-xs-12 col-md-offset-2 col-md-8 col-lg-offset-2 col-lg-8'>" + ((contenidoActualizado != undefined && contenidoActualizado != "") ? contenidoActualizado : descripcion) + "</div></div><br><br>" +
                "<div	class='row'	title='Autor'><div class='col-xs-12 col-md-offset-2 col-md-4 col-lg-offset-2 col-lg-4'>" +
                "<span class='glyphicon glyphicon-user'>&nbsp;<strong>Autor:&nbsp;</strong></span>" + autor + "</div>" +
                "<div class='col-xs-12 col-md-6 col-lg-6 ' ><span class='glyphicon glyphicon-calendar'>&nbsp;<strong>Fin publicaciÃ³n:&nbsp;</strong></span>" + finPublicacion + "</div></div>" +
                "<br><div class='row'><div class='col-xs-12 col-md-offset-2 col-md-8 col-lg-offset-2 col-lg-8'>" +
                "<input type='button' class='btn btn-primary' value='Regresar' onclick=\"mostrarDiv('" + ((cargarPagina != undefined) ? cargarPagina : 'crearClasificado') + "','visualizarClasificado')\"></div></div>";
    }
    $("#visualizarClasificado").html(esquemaContenido);
}

function mostrarDivVisualizado(idmuestra, idoculta) {
    var ident = "#" + idmuestra;
    var identOculta = "#" + idoculta;
    $(identOculta).hide();
    $(ident).show();
    $("#visualizarClasificado").html();
    cargarVisualizacion("", undefined, undefined, undefined);
}

function mostrarDivVisualizadoInicial(idmuestra, idoculta, codArticulo) {
    var ident = "#" + idmuestra;
    var identOculta = "#" + idoculta;
    $(identOculta).hide();
    $(ident).show();
    cargarArticulo('8', codArticulo);
}

function cargarArticulo(opc, codArt)
{
    $.get("ClasificadoControlador", {opc: opc, cod: codArt}, function (datos) {
        var recepcion = JSON.parse(datos);
        var titulo = recepcion.titulo;
        var descripcion = recepcion.descripcion;
        var categoria = recepcion.categoria_codigo;
        var fechaFinPublicacion = recepcion.fecha_fin_publicacion;
        delete arrayRecepcion;
        var arrayRecepcion = [];
        arrayRecepcion = recepcion.Imagenes;
        var url = "";
        if (arrayRecepcion.length > 0)
        {
            for (var i = 0; i < arrayRecepcion.length; i++)
            {
                if (arrayRecepcion[i].destacada === 1)
                {
                    url = recepcion.Directorio;
                    url += arrayRecepcion[i].name + "." + arrayRecepcion[i].ext;
                }
            }
        }
        $("#visualizarClasificado").html();
        cargarVisualizacion(descripcion, url, titulo, "mostrarClasificado");
    });
}

/*funcion que se ejecuta de primeras al cargar el body de la pÃ¡gina+
 cuando el articulo es de tipo 2, es una clasificado informativa*/
function inicioClasificado() {
    formData = "opc=2";
    $.ajax({
        url: 'ClasificadoControlador',
        type: "POST",
        dataType: "json",
        data: formData,
        beforeSend: function () {
            $("#prioridad").attr("disabled", true);
        }
    }).done(function (result) {
        $("#prioridad").removeAttr("disabled", false);
        var listaPrioridades = '';
        $.each(result, function (k, v) {
            listaPrioridades += '<option value="' + v.codigo + '"';
            listaPrioridades += '>' + v.nombre + '</option>';
        });
        $("#prioridad").append(listaPrioridades);
        $("#prioridadAnadir").append(listaPrioridades);
    }).fail(function (jqXHR, textStatus, errorThrown) {
        bootbox.alert({
            message: "Error al cargar las prioridades",
            className: 'bb-alternate-modal'
        });
    });
    $.get("ClasificadoControlador", {opc: 10}, function (data) {
        tipoArt = data;
        mostrarArticulo('0,'+$("#paginaClasificado").val(),'1',tipoArt,11);
        paginacion('Articulo', tipoArt);
    });
    $("#Buscar").on("input", buscarClasificado);
    tinymce.remove();
    cargarCategorias();

    $("#finPublicacion").datepicker({
        dateFormat: "yy-mm-dd",
        minDate: 0,
        maxDate: "+3M +10D"
    });
    tinymce.init({
        selector: '#descripcion',
        menubar: false,
        height: 400,
        language: "es_MX",
        plugins: [
            'advlist autolink lists link charmap print preview anchor',
            'searchreplace visualblocks code fullscreen',
            'insertdatetime table contextmenu paste code'
        ],
        toolbar: 'undo redo | insert | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image',
        statusbar: false,
        setup: function (ed) {
            ed.on('change', function (e) {
                $("#visualizarClasificado").html();
                cargarVisualizacion(undefined, ed.getContent(), undefined, undefined);
            });
        }
    });
    $(":file").filestyle({buttonName: "btn-primary"});
    $(":file").filestyle('placeholder', 'Cargar imagenes');
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1; //Enero es 0

    var yyyy = today.getFullYear();
    if (dd < 10) {
        dd = '0' + dd;
    }
    if (mm < 10) {
        mm = '0' + mm;
    }
    today = yyyy + '-' + mm + '-' + dd;
    $("#finPublicacion").val(today);
}

/*funcion usada para cargar las opciones del select de categorias*/
function cargarCategorias() {
    $.ajax({
        url: 'ClasificadoControlador',
        data: {opc: 1},
        type: 'GET',
        async: true})
            .done(function (data) {
                $(".categorias").html("<select class='form-control categ' name='categ'>" + data + "<option value>Todas las categorias</option></select>");
            })
            .fail(function (jqXHR, textStatus, errorThrown) {
                bootbox.alert({
                    message: "Error al cargar las categorias de clasificado, comuniquese con el administrador...",
                    className: 'bb-alternate-modal'
                });
            });
}
/*funcion usada para crear la clasificado y envia como respuesta
 un alert de boostrap indicado la ejecuciÃ³n correcta*/
function enviarClasificado()
{
    var tit = $("#titulo").val();
    var descr = tinyMCE.get('descripcion').getContent();
    var cat = $("#crearClasificado .categorias .categ").val();
    var visible = $("input[name='visibilidad']:checked").val();
    var prioridad = $("#prioridad").val();
    var precio = $("#precio").val();

    if (cat == undefined)
        cat = "1";

    var parametros = {
        titulo: tit,
        descripcion: descr,
        categoria: cat,
        opc: 3,
        codArt: $("#codigoArticulo").val(),
        finPublicacion: $("#finPublicacion").val(),
        visibilidad: visible,
        precio: precio,
        prioridad: prioridad
    }
    bootbox.confirm({
        title: "Crear Clasificado",
        message: "Esta seguro que desea crear la clasificado con la informaciÃ³n diligenciada?",
        buttons: {
            cancel: {
                label: '<span class="glyphicon glyphicon-remove-sign"></span> Cancelar',
                className: 'btn-danger'
            },
            confirm: {
                label: '<span class="glyphicon glyphicon-ok-sign"></span> Confirmar',
                className: 'btn-success'
            }
        },
        callback: function (result) {
            if (result) {
                $.ajax({
                    url: 'ClasificadoControlador',
                    data: parametros,
                    type: 'POST',
                    async: true,
                })
                        .done(function (data) {
                            if (tamArray != 0)
                            {
                                enviarArchivo(data, 'anadirClasificados.html');
                            } else
                            {
                                $.post("SubeArchivoControlador", {opc: 2, codArt: data}, function (datos) {
                                    redirigir('anadirClasificados.html');
                                });
                            }
                        })
                        .fail(function (jqXHR, textStatus, errorThrown) {
                            bootbox.alert({
                                message: "Error al crear la clasificado, comuniquese con el administrador...",
                                className: 'bb-alternate-modal'
                            });
                        });
            }
        }
    });
}

/*funcion usada para llamar al servlet encargado de la paginacion*/
function paginacion(texto, tipo) {
    var rango = $("#paginaClasificado").val();
    $.get("Pagina", {obj: texto, tipo: tipo, rango: rango}, function (data) {
        $("#paginacion").html(data);
    });
}

function recargaPaginacion() {
    var rango = $("#paginaClasificado").val();
    paginacion('Articulo', tipoArt);
    mostrarArticulo('0,'+rango,'1',tipoArt,11);
}

/*funcion que llama el div donde esta contenido el formulario de crear una clasificado, pero 
 carga los valores traidos por JSON para editarlos 
 */
function editarArt(opc, codArt) {
    $("#mostrarClasificado").hide();
    $("#crearClasificado").show();
    $("#codigoArticulo").val(codArt);
    delete arrayRecepcion;
    var arrayRecepcion = [];
    var incremento = 0;
    $.ajax({
        url: 'ClasificadoControlador',
        data: {opc: opc, cod: codArt},
        type: 'GET',
        async: true})
            .done(function (datos) {
                var recepcion = JSON.parse(datos);
                $("#titulo").val(recepcion.titulo);
                tinyMCE.get('descripcion').setContent(recepcion.descripcion);
                $(".categ option[value=" + recepcion.categoria_codigo + "]").attr("selected", true);
                $("#finPublicacion").val(recepcion.fecha_fin_publicacion);
                $("#prioridad").val(recepcion.prioridad);
                $("#precio").val(recepcion.precio);
                if (recepcion.visibilidad == 1) {
                    $("#visibilidad1").prop("checked", true);
                } else {
                    $("#visibilidad2").prop("checked", true);
                }
                arrayRecepcion = recepcion.Imagenes;
                $("#previsualizar").html("");
                $("#previsualizar").css("backgroundColor", "");

                if (arrayRecepcion.length > 0)
                {
                    array = {};
                    tamArray = 0;
                    $("#previsualizar").html("<div class='col-xs-12' >Archivos seleccionados<label>(Escoja la imagen destacada):</label>");
                    $("#previsualizar").css("backgroundColor", "rgb(238, 238, 238)");
                    for (var i = 0; i < arrayRecepcion.length; i++)
                    {
                        tamArray++;
                        var url = recepcion.Directorio;
                        url += arrayRecepcion[i].name + "." + arrayRecepcion[i].ext;
                        dibujarImg(url, i, arrayRecepcion[i].name, arrayRecepcion[i].destacada);
                        var promise = getBase64Image(url);
                        promise.then(function (dataURL) {	/*este bloque de codigo no es asyncrono y permite garantizar que el codigo se ejecute secuencialmente*/
                            array[arrayRecepcion[incremento].name] = dataURL;
                            incremento++;
                        });
                    }
                    $("#imagenesDescargadas").val("SI");
                } else {
                    $("#previsualizar").append("<p>La clasificado no posee imagenes cargadas...</p>");
                }
            })
            .fail(function (jqXHR, textStatus, errorThrown) {
                bootbox.alert({
                    message: "Error al editar la clasificado, comuniquese con el administrador...",
                    className: 'bb-alternate-modal'
                });
            });
}

function getBase64Image(url) {
    var promise = new Promise(function (resolve, reject) {
        var img = new Image();
        img.crossOrigin = "Anonymous";
        img.onload = function () {
            var canvas = document.createElement("canvas");
            canvas.width = img.width;
            canvas.height = img.height;
            var ctx = canvas.getContext("2d");
            ctx.drawImage(img, 0, 0);
            var dataURL = canvas.toDataURL("image/png");
            //resolve(dataURL.replace(/^data:image\/(png|jpg|jpeg|pdf);base64,/, ""));
            resolve(dataURL);
        };
        img.src = url;
    });
    return promise;
}
;

/*funcion usada para borrar un solo registro de la tabla*/
function borrarClasificado(codArt, titulo) {
    bootbox.confirm({
        title: "Eliminar Clasificado",
        message: "Â¿Esta seguro que desea borrar la clasificado con tÃ­tulo <strong>" + titulo + "</strong> ?",
        buttons: {
            cancel: {
                label: '<span class="glyphicon glyphicon-remove-sign"></span> Cancelar',
                className: 'btn-danger'
            },
            confirm: {
                label: '<span class="glyphicon glyphicon-ok-sign"></span> Confirmar',
                className: 'btn-success'
            }
        },
        callback: function (result) {
            if (result) {
                $.ajax({
                    url: 'ClasificadoControlador',
                    data: {opc: 12, cod: codArt},
                    type: 'GET',
                    async: true})
                        .done(function (data) {
                            redirigir('anadirClasificados.html');
                        })
                        .fail(function (jqXHR, textStatus, errorThrown) {
                            bootbox.alert({
                                message: "Error al eliminar la clasificado, comuniquese con el administrador...",
                                className: 'bb-alternate-modal'
                            });
                        });
            }
        }
    });
}
/*funcion usada para borrar todos los registros que se encuentren
 activos por check box*/
function borrarClasificados() {
    var select = $("#accionesLote").val();
    if (select == "Eliminar")
    {
        bootbox.confirm({
            title: "Eliminar Clasificado",
            message: "Â¿Esta seguro que desea borrar <strong>todas las clasificados</strong>?",
            buttons: {
                cancel: {
                    label: '<span class="glyphicon glyphicon-remove-sign"></span> Cancelar',
                    className: 'btn-danger'
                },
                confirm: {
                    label: '<span class="glyphicon glyphicon-ok-sign"></span> Confirmar',
                    className: 'btn-success'
                }
            },
            callback: function (result) {
                if (result) {
                    $("[name='seleccion']:checked").each(function () {
                        var codArticulo = this.value;
                        $.post("SubeArchivoControlador", {opc: 2, codArt: codArticulo}, function (datos) {
                            $.ajax({
                                url: 'ClasificadoControlador',
                                data: {opc: 12, cod: codArticulo},
                                type: 'GET',
                                async: true,
                                success: function (dat) {
                                },
                                error: function (xhr, status) {
                                    $('#mensaje').modal('toggle');
                                    $('#mensajeFinal').html("Existio un problema al eliminar registros");
                                }
                            });
                        });


                    });
                    redirigir('anadirClasificados.html');
                }
            }
        });
    }
}

/*funcion usada para cargar las clasificados en la tabla
 y le asigna estilo a la pagina activa en la paginaciÃ³n*/
function mostrarArticulo(paginas, opcion, tipoOpcion,opcionClasificado)
{
    var arrayPagination=$(".pagination");
	var categ=$(".categ").val();
	var search=$("#Buscar").val();
	$(arrayPagination[0] ).children().removeClass("active");
	$(arrayPagination[0]).children("#"+opcion).addClass("active");
  var parametros={rango:((paginas!=undefined)?paginas:""),tipo:tipoOpcion,opc:((opcionClasificado!=undefined)?opcionClasificado:11),cat:categ,buscar:search};
    $.ajax({
        url: 'ClasificadoControlador',
        data: parametros,
        type: 'GET',
        async: true})
            .done(function (data) {
            
                var array = JSON.parse(data);
                $("#tbodyData").empty();
                for (var i in array)
                {   
                    autor = array[i].nombreUsuario + " " + array[i].apellidoUsuario;
                    var fila="<tr " + (array[i].codigoEstado == 2 ? "class=\"success\"" : (array[i].codigoEstado == 4 ? "class=\"danger\"" : "")) + ">";
                    fila += "<td><input type=\"checkbox\" name=\"seleccion\" value=\"" + array[i].codigo + " \" /></td>";
                    fila += "<td id='tituloArt'>" + array[i].titulo + "</td>";
                    fila += "<td>" + array[i].nombreUsuario + " " + array[i].apellidoUsuario + "</td>";
                    fila += "<td>" + array[i].nombreCategoria + "</td>";
                    fila += "<td>" + array[i].nombreEstado + "</td>";
                    fila += "<td>" + (array[i].fechafinPublicacion == null ? "No tiene" : array[i].fechafinPublicacion) + "</td>";
                    fila += "<td>";
                    if (array[i].fechaPublicacion == 0) {
                        fila += "<button class=\"btn btn-info\" disabled=\"disabled\"><span class=\"glyphicon glyphicon-send\" title=\"editar clasificado\" ></span></button>";
                    } else {
                        fila += "<button class=\"btn btn-info\" onclick=\"editarArt('8','" + array[i].codigo + "') \"><span class=\"glyphicon glyphicon-pencil\" title=\"editar clasificado\" ></span></button>";
                    }
                    fila += "<button class=\"btn btn-primary\" onclick=\"mostrarDivVisualizadoInicial('visualizarClasificado','mostrarClasificado','" + array[i].codigo + "')\"><span class=\"glyphicon glyphicon-eye-open\" title=\"visualizar clasificado\"></span></button>";
                    fila += "<button class=\"btn btn-danger\"  onclick=\"borrarClasificado(" + array[i].codigo + ",'" + array[i].titulo + "') \"><span class=\"glyphicon glyphicon-remove\" title=\"borrar clasificado\"></span></button>";
                    fila += "</tr>";
                    $("#tbodyData").append(fila);
                }
            })
            .fail(function (jqXHR, textStatus, errorThrown) {
                bootbox.alert({
                    message: "Error al efectuar al realizar los filtros, por favor comuniquese con el administrador...",
                    className: 'bb-alternate-modal'
                });
            });
}

/*funcion usada para el cuadro de texto de busqueda de registros
 de la tabla, los filtros son por titulo y por descripcion, es la
 misma MostrarArticulo, pero se usa porque la variable global de tipoArt
 aun no ha sido cargada*/
function buscarClasificado()
{
    mostrarArticulo('0,'+$("#paginaNoticia").val(),undefined,tipoArt,18);
}
