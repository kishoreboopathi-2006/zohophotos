/**
 * 
 */
const albumCategories = ["pongal", "diwali", "holi", "christmas", "newYear", "weddings", "birthdays", "anniversaries", "religiousFestivals", "temple&CulturalEvents", "nature&Landscapes", "animals&pets", "travel&Adventure", "cityLife&Architecture", "food&Cooking", "sports&Activities",
	"artAndCreativity", "work&office", "everydaymoments"];
let categorizePhotoDetails = [];
let photoDetails = [];
const grid = document.getElementById("galleryGrid");
window.addEventListener("load", function() {
	fetch("/zohophotos/getCategorizePhotos")
		.then(handleResponse)
		.then(handleData)
		.catch(showError);
	function handleResponse(response) {
		return response.json();
	}
	function handleData(data) {
		categorizePhotoDetails = data;
		console.log("data" + JSON.stringify(data, null, 2));
	}
	function showError(error) {
		console.log(error);
	}
});
window.addEventListener("load", function() {
	fetch("/zohophotos/retrievePhotos")
		.then(handleResponse)
		.then(handleData)
		.catch(showError);
	function handleResponse(response) {
		return response.json();
	}
	function handleData(data) {
		photoDetails = data;
		setAlbums();
	}
	function showError(error) {
		console.log(error);
	}
});

function setAlbums() {
	grid.innerHTML = "";
	categorizePhotoDetails.forEach(category => {
		if (category.photosIds.length > 3) {
			console.log(category.photosIds[0]);
			console.log("dddd..." + JSON.stringify(photoDetails, null, 2));
			const photoUrl = photoDetails.find(url => {
				return url.resourceId === category.photosIds[0];
			});
			if (photoUrl) {
				const card = document.createElement("div");
				card.className = "photo-card glass-panel";
				card.id = category.category;
				const img = document.createElement("img");
				img.src = photoUrl.previewUrl;
				const overlay = document.createElement("div");
				overlay.className = "photo-overlay";
				overlay.innerHTML = `<div class="photo-title">${category.category}</div>`;
				card.appendChild(img);
				card.appendChild(overlay);
				grid.appendChild(card);
			}
		}
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
		card.appendChild(img);
		view.appendChild(card);

	});

}












