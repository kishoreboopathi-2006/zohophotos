/**
 * 
 */

const albumCategories = [
	"Diwali",
	"Pongal",
	"People",
	"Office",
	"Pets",
	"Sports",
	"Travel",
	"Functions",
	"Shopping",
	"Friends"
];
let categorizePhotoDetails = [];
let photoDetails = [];
let urls=[];
let resourceIds=[];
let currentIndex = 0;

const grid = document.getElementById("galleryGrid");
window.addEventListener("load", async function () {
	try {

		const [catRes, photoRes] = await Promise.all([
			fetch("/zohophotos/getCategorizePhotos"),
			fetch("/zohophotos/retrievePhotos")
		]);

		categorizePhotoDetails = await catRes.json();
		photoDetails = await photoRes.json();

		console.log("categories:", categorizePhotoDetails);
		console.log("photos:", photoDetails);

		setAlbums();

	} catch (err) {
		console.log(err);
	}
});

function setAlbums() {
	grid.innerHTML = "";

	categorizePhotoDetails.forEach(category => {

		if (!category.photosIds || category.photosIds.length === 0) return;

		const card = document.createElement("div");
		card.className = "photo-card glass-panel album-card";
		card.id = category.category;

		const total = category.photosIds.length;

		/* ---------- CASE 1 → ONLY 1 IMAGE ---------- */
		if (total === 1) {

			const photo = photoDetails.find(p =>
				p.resourceId === category.photosIds[0]
			);

			if(photo){
				const img = document.createElement("img");
				img.src = photo.previewUrl;
				card.appendChild(img);
			}
		}

		/* ---------- CASE 2 → MULTIPLE IMAGES ---------- */
		else {

			const stack = document.createElement("div");
			stack.className = "album-stack";

			category.photosIds.slice(0,3).forEach((pid, index) => {

				const photo = photoDetails.find(p => p.resourceId === pid);

				if(photo){
					const img = document.createElement("img");
					img.src = photo.previewUrl;
					img.className = "stack-img stack-" + index;
					stack.appendChild(img);
				}
			});

			card.appendChild(stack);
		}

		/* ---------- TITLE OVERLAY ---------- */
		const overlay = document.createElement("div");
		overlay.className = "photo-overlay";
		overlay.innerHTML = `<div class="photo-title">${category.category}</div>`;

		card.appendChild(overlay);
		grid.appendChild(card);
	});
}
grid.addEventListener("click", function(e) {
	const card = e.target.closest(".photo-card");
	const id = card.id;
	const category = albumCategories.find(category => {
		return category.toLowerCase() === id.toLowerCase();
	});
	console.log(JSON.stringify(categorizePhotoDetails, null, 2));
	const photos = categorizePhotoDetails.find(photo => {
		return photo.category.toLowerCase() === category.toLowerCase();
	});
	console.log("cate" + category);

	renderPhotos(photos);

});
function renderPhotos(photos) {
	const photosContainer = document.getElementById("photos-overlay");
	photosContainer.style.display = "flex";
	photosContainer.innerHTML = "";
	const exitButton = document.createElement("button");
	exitButton.className = "exit back-btn";
	exitButton.id = "exit-button";
	const view = document.createElement("div");
	view.className = "view";
	view.id = "view";
	exitButton.textContent = "back";
	photosContainer.appendChild(exitButton);
	photosContainer.appendChild(view);
	document.getElementById("exit-button").addEventListener("click", function() {
		photosContainer.style.display = "none";
	});
	console.log(photos.photosIds)
	const photoIds = photos.photosIds;
	console.log(photoIds);
	photoIds.forEach(photo => {
		const image = photoDetails.find(img => {
			return photo === img.resourceId;
		});
		
		const card = document.createElement("div");
		card.className = "photo-card glass-panel size";
		const img = document.createElement("img");
		img.src = image.previewUrl;
		urls.push(image.previewUrl);
		resourceIds.push(image.resourceId);
		console.log(urls);
		card.appendChild(img);
		view.appendChild(card);

	});

}

document.getElementById("photos-overlay").addEventListener("click", function(e) {
	const img = e.target.closest("img");
	console.log(img.src);
	if (img) {
		currentIndex = urls.findIndex(url => {
			return url === img.src;
		});
		console.log("resource" + resourceIds);
		console.log(img.src);
		openFullView();
	}
});
function openFullView() {
	const viewContainer = document.getElementById("viewBox");
	viewContainer.style.display = "flex";
	console.log("ddd");
	const right = document.createElement("span");
	right.className = "button";
	right.id = "right";
	right.innerHTML = "&#10095;";
	const left = document.createElement("span");
	left.className = "button";
	left.id = "left";
	left.innerHTML = "&#10094;";
	const viewBox = document.createElement("div");
	viewBox.className = "viewBox";
	const img = document.createElement("img");
	img.src = urls[currentIndex];
	img.dataset.resourceId = resourceIds[currentIndex];
	img.className = "view-photo";
	const closeBtn = document.createElement("span");
	closeBtn.id = "exit";
	closeBtn.className = "close-view";
	closeBtn.innerHTML = `<i class="ph ph-x"></i>`;
	viewBox.appendChild(img);
	viewContainer.appendChild(viewBox);
	viewContainer.appendChild(right);
	viewContainer.appendChild(left);
	viewContainer.appendChild(closeBtn);
	document.getElementById("right").addEventListener("click", function() {
		currentIndex = (currentIndex + 1) % urls.length;
		img.src = urls[currentIndex];
	});
	document.getElementById("left").addEventListener("click", function() {
		currentIndex = (currentIndex - 1 + urls.length) % urls.length;
		img.src = urls[currentIndex];
	});
	document.getElementById("exit").addEventListener("click", function() {
		viewContainer.style.display = "none";
		viewContainer.innerHTML = "";
	});

};

function dashboardPage() {
	window.location.href = "/zohophotos/html/dashboard/dashboard.html";
}
function diaryPage() {
	window.location.href = "/zohophotos/html/diary/diary.html";
}

function favouritePage() {
	window.location.href = "/zohophotos/html/favourite/favourite.html";
}
function reminderPage() {
	window.location.href = "/zohophotos/html/reminder/reminder.html";
}

function albumPage() {
	window.location.reload();
	window.location.href = "/zohophotos/html/album/album.html";
}