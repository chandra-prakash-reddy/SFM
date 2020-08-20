
var api_services = {
	"download": HOSTNAME + '/download?path=',
	"get_file_paths": HOSTNAME + '/getFilePaths?path=',
	"save_file": HOSTNAME + '/saveFileContent?path=',
	"get_file_content": HOSTNAME + '/getFileContent?path=',
	"get_log_file_content": HOSTNAME + '/getLogFileContent?path='
}

function downloadFile() {
	const urlParams = new URLSearchParams(window.location.search);
	var path = urlParams.get('path');
	axios.get(api_services.download + encodeURIComponent(path)).then((response) => {
		var blob = new Blob([response]);
		headers = response['headers']
		var downloadUrl = URL.createObjectURL(blob);
		var a = document.createElement("a");
		a.download = headers['content-disposition'].replace('attachment; filename=', '')
		a.href = downloadUrl;
		document.body.appendChild(a);
		a.click();
	}, (error) => {
		alert("Unable to download!");
		console.log(error)
	});
};