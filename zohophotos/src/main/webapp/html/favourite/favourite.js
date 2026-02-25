let favourite = [];
const grid = document.getElementById("galleryGrid");
let deletedPhotos=[];
window.addEventListener("load", async() => {
	showLoader();
	const deleteRes = await fetch("/zohophotos/getDeletedPhotos");
	deletedPhotos = await deleteRes.json();
	fetch("/zohophotos/getFavouritePhotos")
		.then(response => response.json())
		.then(data => {
			favourite = data;
			console.log(favourite)
			favourite=favourite.filter(img=>{
				return !deletedPhotos.some(del=>img.previewUrl===del.url);
					});
			console.log("remove"+favourite);
			grid.innerHTML = "";
			favourite.forEach(file => {
				const card = document.createElement("div");
				card.className = "photo-card glass-panel";
				const img = document.createElement("img");
				img.src = file.previewUrl;
				img.alt = file.photoName || "Photo";
				const overlay = document.createElement("div");
				overlay.className = "photo-overlay";
				overlay.innerHTML = `<div class="photo-title">${file.photoName || "Photo"}</div>`;
				card.appendChild(img);
				card.appendChild(overlay);
				grid.appendChild(card);
			});
		})
		.catch(err => console.error(err));
});
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
function showLoader() {
	const loader = document.getElementById('loader');
	loader.classList.remove('hidden');
	setTimeout(() => {
		hideLoader();
	}, 5000);
}
function hideLoader() {
	const loader = document.getElementById('loader');
	loader.classList.add('hidden');
}