var editorContainer = document.querySelector('.editorContainer')

function viewFilesList() {
	var file_path = document.getElementById("inputpath").value
	axios.get(api_services.get_file_paths + file_path).then((response) => {
		files = response.data;
		var ul = '';
		for (var fileIndex = 0; fileIndex < files.length; fileIndex++) {
			ul += '<a  class="file" href=/log/viewer.html?path=' + files[fileIndex].replace(/\\/g, '/') + '>' + files[fileIndex] + '</a>\n<br>';
		}
		ul += '\n';
		document.getElementById("filepathviewer").innerHTML = ul
	}, (error) => {
		alert("error in getting files!");
		console.log(error)
	});
};


function loadLogFile() {
	const urlParams = new URLSearchParams(window.location.search);
	var path = urlParams.get('path');
	document.getElementById("currentPath").innerHTML = path;
	document.getElementById("logLines").innerHTML = 100;
	axios.get(api_services.get_log_file_content + path + '&lines=100').then((response) => {
		text = response.data;
		CodeMirror(editorContainer, {
			lineNumbers: true,
			mode: 'javascript',
			value: text,
			theme: 'monokai'
		})
	}, (error) => {
		alert("error in getting log file content!");
		console.log(error)
	});
};


function refreshFile() {
	const urlParams = new URLSearchParams(window.location.search);
	var path = urlParams.get('path');
	axios.get(api_services.get_log_file_content + path + '&lines=' + document.getElementById("logLines").value).then((response) => {
		text = response.data;
		document.querySelector('.CodeMirror').CodeMirror.setValue(text)
	}, (error) => {
		alert("error in getting log file content!");
		console.log(error)
	});
};