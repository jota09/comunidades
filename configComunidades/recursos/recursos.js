cargarTabla();
cargarTablaAso();
cargarSelects();
function cargarTabla(){
	$.get("RecursoControlador",{op:1},function(data){
		var arrayTable=JSON.parse(data);
		var html="";
		for(var i in arrayTable){
			html+="<tr><td>"+arrayTable[i].codigo+"</td><td>"+arrayTable[i].nombre+"</td><td style='font-size:10px'>"+arrayTable[i].ruta+"</td><td><div class='btn-group'>";
			html+="<button class=\"btn btn-info btn-sm\" onclick=\"consultarRecurso("+ arrayTable[i].codigo+")\"><span class=\"glyphicon glyphicon-pencil\"></span></button>";
			html+="<button onclick=\"borrarRecurso("+ arrayTable[i].codigo+")\" class=\"btn btn-danger btn-sm\"><span class=\"glyphicon glyphicon-remove\"  ></span></button>";
			html+="</div></td></tr>";
		}
		$("#tbodyData").html(html);

	});
}
function cargarSelects(){
	$.get("RecursoControlador",{op:1},function(data){
		var arrayTable=JSON.parse(data);
		var html="";
		for(var i in arrayTable){
			html+="<option value='"+arrayTable[i].codigo+"'>"+arrayTable[i].nombre+"</option>";
		}
		$("#selectRecurso").html(html);
	});
	$.get("RecursoControlador",{op:6},function(data){
		var arrayTable=JSON.parse(data);
		var html="";
		for(var i in arrayTable){
			html+="<option value='"+arrayTable[i].codigo+"'>"+arrayTable[i].nombre+"</option>";
		}
		$("#selectVista").html(html);
	});
	
}
function cargarTablaAso(){
	$.get("RecursoControlador",{op:5},function(data){
		var arrayTable=JSON.parse(data);
		var html="";
		for(var i in arrayTable){
			html+="<tr><td>"+arrayTable[i].codRecurso+"</td><td>"+arrayTable[i].nombreRecurso+"</td><td>"+arrayTable[i].codVista+"</td><td>"+arrayTable[i].nombreVista+"</td><td></td>";
			html+="<td><button class=\"btn btn-danger btn-sm\" onclick=\"borrarAso("+ arrayTable[i].codRV+")\" ><span class=\"glyphicon glyphicon-remove\"  ></span></button></td></tr>";
		}
		$("#tbodyData2").html(html);

	});
}
function registrarRecurso(){
	var codRecurso=$("#codRecurso").val();
	var nombreRecurso=$("#nombreRecurso").val();
	var rutaRecurso=$("#rutaRecurso").val();

	$.post("RecursoControlador",{op:2,cod:codRecurso,nombreRecurso:nombreRecurso,rutaRecurso:rutaRecurso});
	redirigir("recurso.html");
}

function borrarRecurso(cod){
	if(confirm("¿Desea Eliminar el Recurso?")){
		$.post("RecursoControlador",{op:4,cod:cod});
		redirigir("recurso.html");
	}
}
function consultarRecurso(cod){
	$.post("RecursoControlador",{op:3,cod:cod},function(data){
		var recurso=JSON.parse(data);
		$("#codRecurso").val(recurso.codigo);
		$("#nombreRecurso").val(recurso.nombre);
		$("#rutaRecurso").val(recurso.ruta);
		$("#nombreRecurso").focus();
		
	});
}
function borrarAso(cod){
	if(confirm("¿Desea Eliminar La Asociacion?")){
	$.post("RecursoControlador",{op:7,cod:cod});
	redirigir("recurso.html");
}
}
selectRecurso,selectVista
function asoRecAVista(){
	if(confirm("¿Desea Asociar los Recursos?")){
	var codRecurso=$("#selectRecurso").val();
	var codVista=$("#selectVista").val();
	$.post("RecursoControlador",{op:8,codRecurso:codRecurso,codVista:codVista});
	redirigir("recurso.html");
}
}