let favourite = [];
const grid = document.getElementById("galleryGrid");

window.addEventListener("load", () => {
	fetch("/zohophotos/getFavouritePhotos")
		.then(response => response.json())
		.then(data => {
			favourite = data;
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
