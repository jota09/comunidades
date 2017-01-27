function iniciarFileInput(){
	$("#portadaClasificado").fileinput({
    uploadUrl: "/file-upload-batch/2",
    maxFileCount: 4,
	minFileCount: 1,
    validateInitialCount: true,
    overwriteInitial: false,
    initialPreviewAsData: true,
    allowedFileExtensions: ["jpg", "png", "gif"]
});
}
iniciarFileInput();