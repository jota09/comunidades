cargarTabla(1,'tbodyData');
function cargarTabla(op,name){
	$.get("RecursoControlador",{op:op},function(data){
		var arrayTable=JSON.parse(data);
		var html="";
		for(var i=0;i<arrayTable.length;i++){
			html+="<tr><td>"+arrayTable[i].codigo+"</td><td>"+arrayTable[i].nombre+"</td><td style='font-size:10px'>"+arrayTable[i].ruta+"</td><td><div class='btn-group'>";
			html+="<button class=\"btn btn-info btn-sm\" onclick=\"consultarRecurso("+ arrayTable[i].codigo+")\"><span class=\"glyphicon glyphicon-pencil\"></span></button>";
			html+="<button class=\"btn btn-danger btn-sm\"><span class=\"glyphicon glyphicon-remove\" onclick=\"borrarRecurso("+ arrayTable[i].codigo+")\" ></span></button>";
			html+="</div></td></tr>";
		}
		$("#"+name).html(html);

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
		$.post("RecursoControlador",{op:4,cod:cod});
		redirigir("recurso.html");
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
