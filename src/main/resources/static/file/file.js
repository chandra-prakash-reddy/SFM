var editorContainer = document.querySelector('.editorContainer')

function viewFilesList() {
	var file_path = document.getElementById("inputpath").value
	axios.get(api_services.get_file_paths + file_path).then((response) => {
		files = response.data;
		console.log(files)
		var ul = '';
		for (var fileIndex = 0; fileIndex < files.length; fileIndex++) {
			ul += '<a  class="file" href=/file/viewer.html?path=' + files[fileIndex].replace(/\\/g, '/') + '>' + files[fileIndex] + '</a>\n<br>';
		}
		ul += '\n';
		document.getElementById("filepathviewer").innerHTML = ul
	}, (error) => {
		alert("error in getting files!");
		console.log(error)
	});
};


function loadFile() {
	const urlParams = new URLSearchParams(window.location.search);
	document.getElementById("currentPath").innerHTML = urlParams.get('path');
	var path = urlParams.get('path');
	axios.get(api_services.get_file_content + path).then((response) => {
		text = window.atob(response.data);
		CodeMirror(editorContainer, {
			lineNumbers: true,
			mode: 'javascript',
			value: text,
			theme: 'monokai'
		})
	}, (error) => {
		alert("error in getting files!");
		console.log(error)
	});
};


function saveFile() {
	var encodedFileData = window.btoa(document.querySelector('.CodeMirror').CodeMirror.getValue())
	const urlParams = new URLSearchParams(window.location.search);
	var path = urlParams.get('path');
	var config = {
		headers: {
			"content-type": "text/plain"
		}
	}
	axios.post(api_services.save_file + path, encodedFileData, config).then((response) => {
		text = window.atob(response.data);
		document.querySelector('.CodeMirror').CodeMirror.setValue(text)
		alert("file saved!");
	}, (error) => {
		alert("error in saving file!");
	});
};

function downloadFile() {
	document.getElementById("currentPath").innerHTML = urlParams.get('path');
	var path = urlParams.get('path');
	axios.get("http://localhost:9999/download?path=" + encodeURIComponent(path)).then((response) => {
		return response
	}, (error) => {
		alert("unable download file");
		console.log(error)
	});
}