document.getElementById("diaryForm").addEventListener("submit",submitDiary);
function submitDiary(e){
	e.preventDefault();
	var form = document.getElementById("diaryForm");
	let formData = new FormData(form);
	console.log(form);
	console.log(formData);
	fetch("../getDiaryData",{
		method:"post",
		body:formData
	}).then(handleResponse).then(showMessage).catch(showError);

}
function handleResponse(response){
	let a=response.text();
	return a;
}
function showMessage(data){
	document.getElementById("message").innerHTML=data;
}
function showError() {
    document.getElementById("message").innerHTML = "Error saving diary";
}
