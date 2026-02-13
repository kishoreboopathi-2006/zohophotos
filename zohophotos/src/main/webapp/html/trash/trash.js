let deleted = [];
const grid = document.getElementById("galleryGrid");
/* kishore for delete */
window.addEventListener("load", () => {
	fetch("/zohophotos/getDeletedPhotos")
		.then(response => response.json())
		.then(data => {
			deleted = data;
			grid.innerHTML = "";
			deleted.forEach(file => {
				const card = document.createElement("div");
				card.className = "photo-card glass-panel";
				const img = document.createElement("img");
				img.src = file.url;
				img.alt = file.photoName || "Photo";
				const icon = document.createElement("div");
				icon.innerHTML = `<i class="fa-solid fa-trash-can-arrow-up"></i>`
				icon.className = "trash";
				icon.onclick = () => restorePhoto(file.url);
				card.appendChild(img);
				card.appendChild(icon);
				grid.appendChild(card);
			});
		})
		.catch(err => console.error(err));
});
/*===================================for redirect================= */
function dashboardPage() {
	window.location.href = "/zohophotos/html/dashboard/dashboard.html";
}
function diaryPage() {
	window.location.href = "/zohophotos/html/diary/diary.html";
}

function favouritePage() {
	window.location.href = "/zohophotos/html/favourite/favourite.html";
}
function albumPage() {
	window.location.href = "/zohophotos/html/album/album.html";
}
function reminderPage() {
	console.log("reminer");
	window.location.href = "/zohophotos/html/reminder/reminder.html";
}
function restorePhoto(url) {
	const form = new FormData();
	form.append("url", url);
	const img = document.querySelector(`img[src="${CSS.escape(url)}"]`);
	const card=img.closest(".photo-card");
	card.remove();
	console.log("card"+card);
	fetch("/zohophotos/restorePhoto", {
		method: "post",
		body: form
	}).then(handleResponse).then(handleData).catch(error);
	function handleResponse(response) {
		return response.text();
	}
	function handleData(data) {
		console.log(data);
	}
	function error(err) {
		console.log(err);
	}



}

