/**
 * 
 */
let favourite = [];
let favIcons = document.getElementsByClassName("fav-icon");
const grid = document.getElementById("galleryGrid");
let entry = true;
window.addEventListener("load", () => {
	if (entry) {
		fetch("/zohophotos/getFavouritePhotos").then(handleResponse).then(handleData).then(showError);
		function handleResponse(response) {
			return response.json()
		}
		function handleData(data) {
			console.log(data);
			favourite = data;
			entry=false;
		}
		function showError(error) {
			console.log(error);
		}
	}
});
window.addEventListener("load", () => {
	let images = [];
	const modalOverlay = document.getElementById("ai-modal-overlay");
	const sliderTrack = document.getElementById("sliderTrack");
	window.closeAiModal = function() {
		modalOverlay.classList.remove("active");
	};

	if (!grid || !sliderTrack) return;

	fetch("/zohophotos/retrievePhotos")
		.then(res => res.json())
		.then(result => {
			console.log(result);
			images = result;
			[...images].forEach(file => {
				const img = document.createElement("img");
				img.src = file.previewUrl;
				img.loading = "lazy";
				img.alt = file.imageName || "Photo";
				sliderTrack.appendChild(img);
			});
			if (images.length > 3) {
				sliderTrack.style.animation = "slide 30s linear infinite";
			} else {
				sliderTrack.style.animation = "none";
			}
			grid.innerHTML = "";
			console.log("Images:" + images);
			images.forEach(file => {
				const card = document.createElement("div");
				card.className = "photo-card glass-panel";
				const img = document.createElement("img");
				img.src = file.previewUrl;
				img.alt = file.imageName || "Photo";
				img.dataset.fileId=file.resourceId;
				const aiIcon = document.createElement("div");
				aiIcon.className = "ai-icon";
				aiIcon.innerHTML = "✨";
				aiIcon.onclick = () => describeFromIcon(aiIcon);
				const overlay = document.createElement("div");
				overlay.className = "photo-overlay";
				overlay.innerHTML = `<div class="photo-title">${file.imageName || "Photo"}</div>`;
				const favIcon = document.createElement("div");
				favIcon.className = "fav-icon";
				favIcon.innerHTML = "❤️";
				console.log(file.previewUrl);
				const fav=favourite.some(data=>data.previewUrl===file.previewUrl);
				console.log("fav:"+fav);
				if(fav){
				favIcon.classList.toggle("is-favourite");
				}
				card.appendChild(img);
				card.appendChild(aiIcon);
				card.appendChild(favIcon);
				card.appendChild(overlay);
				grid.appendChild(card);
			});

		})
		.catch(err => {
			console.error(err);
			grid.innerHTML = "<p style='color:red'>Unable to load photos</p>";
		});
});

function diaryPage() {
	window.location.href = "/zohophotos/html/diary/diary.html";
}
grid.addEventListener("click", function(e) {
	if (e.target.classList.contains("fav-icon")) {
		const img = e.target.closest(".photo-card").querySelector("img");
		if (e.target.classList.contains("is-favourite")) {
			let formData = new FormData();
			formData.append("add", "false")
			formData.append("previewUrl", img.src);
			formData.append("photoName", img.alt);
			fetch("/zohophotos/selectFavouritePhoto", {
				method: "POST",
				body: formData
			})
				.then(handleResponse)
				.then(showData)
				.catch(showError);
			function handleResponse(response) {
				return response.text();
			}
			function showData(data) {
				if (data != "fail") {
					e.target.classList.toggle("is-favourite");
				}
			}
			function showError(err) {
				console.log(err);
			}
		}
		else {
			let formData = new FormData();
			formData.append("add", "true")
			formData.append("previewUrl", img.src);
			formData.append("photoName", img.alt);
			fetch("/zohophotos/selectFavouritePhoto", {
				method: "POST",
				body: formData
			})
				.then(handleResponse)
				.then(showData)
				.catch(showError);
			function handleResponse(response) {
				return response.text();
			}
			function showData(data) {
				if (data != "fail") {
					favourite.push(data);
					console.log("favoutire:"+favourite);
					e.target.classList.toggle("is-favourite");
				}
			}
			function showError(err) {
				console.log(err);
			}
		}
	}
});

function favouritePage(){
	window.location.href="/zohophotos/html/favourite/favourite.html";
}

function describeFromIcon(iconEl) {
    const card = iconEl.closest(".photo-card");
    const img = card.querySelector("img");
    const fileId = img.dataset.fileId;
    if (!fileId) {
        alert("File ID missing");
        return;
    }
    iconEl.innerHTML = "⏳";
    document.getElementById("ai-modal-title").innerHTML = "✨ AI Photo Description";
    document.getElementById("ai-modal-content").innerHTML =
        `<div class="loading-spinner"></div>`;
    document.getElementById("ai-modal-overlay").classList.add("active");
    fetch("/zohophotos/describePhoto", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ file_id: fileId })
    })
    .then(res => res.json())
    .then(data => {
        if (!data.content) {
        	
            document.getElementById("ai-modal-content").innerHTML =
                "<p style='color:red'>No AI response received</p>";
            iconEl.innerHTML = "✨";
            return;
        }
        console.log(data);
        document.getElementById("ai-modal-content").innerHTML = `
            <div style="line-height:1.8; font-size:16px; color:#334155;">
                ${data.content.replace(/\n/g, "<br>")}</div>`;
        iconEl.innerHTML = "✨";
    })
    .catch(err => {
        console.error(err);
        document.getElementById("ai-modal-content").innerHTML =
            "<p style='color:red'>Unable to describe photo</p>";
        iconEl.innerHTML = "✨";
    });
}
/*
for (let icon of favIcons) {
	icon.addEventListener("click", function() {
		console.log(this.src);
		let formData = new FormData();
		formData.append("previewUrl", this.src);

		fetch("../insertFavouriteImage", {
			method: "POST",
			body: formData
		})
		.then(handleResponse)
		.then(handleData)
		.catch(showError);

		function handleResponse(response) {
			return response.text();
		}

		function handleData(data) {
			console.log(data);
		}

		function showError(err) {
			console.log(err);
		}
	});
}

function describeFromIcon(iconEl) {

	const card = iconEl.closest(".photo-card");
	const img = card.querySelector("img");
	const fileId = img.dataset.fileId;

	if (!fileId) {
		alert("File ID missing");
		return;
	}

	iconEl.innerHTML = "⏳";

	fetch("../describePhoto", {
		method: "POST",
		headers: { "Content-Type": "application/json" },
		body: JSON.stringify({ file_id: fileId })
	})
	.then(res => res.json())
	.then(data => {

		if (data.error) {
			alert(data.error);
			iconEl.innerHTML = "✨";
			return;
		}
	    
		function openAiModal(title, content) {
			document.getElementById("ai-modal-title").innerHTML = title;
			document.getElementById("ai-modal-content").innerHTML = content;
			document.getElementById("ai-modal-overlay").classList.add("active");
		}
	   

		iconEl.innerHTML = "✨";
	})
	.catch(err => {
		console.error("Describe error:", err);
		alert("Unable to describe photo");
		iconEl.innerHTML = "✨";
	});
}

function loadFavorites() {

	Promise.all([
		fetch("../retrievePhotos").then(r => r.json()),
		fetch("../favorites").then(r => r.json())
	])
	.then(([photosRes, favRes]) => {

		const images = photosRes.data || [];
		const favSet = new Set(favRes.favorites || []);

		const grid = document.getElementById("galleryGrid");
		grid.innerHTML = "";

		images.forEach(file => {
			if (!favSet.has(file.id)) return; 

			const card = document.createElement("div");
			card.className = "photo-card glass-panel";

			const img = document.createElement("img");
			img.src = `../preview/${file.id}`;
			img.dataset.fileId = file.id;

			const aiIcon = document.createElement("div");
			aiIcon.className = "ai-icon";
			aiIcon.innerHTML = "✨";
			aiIcon.onclick = () => describeFromIcon(aiIcon);

			const favIcon = document.createElement("div");
			favIcon.className = "fav-icon active"; 
			favIcon.innerHTML = "❤️";
			favIcon.onclick = (e) => {
				e.stopPropagation();
				toggleFavorite(favIcon);
			};

			card.appendChild(img);
			card.appendChild(aiIcon);
			card.appendChild(favIcon);
			grid.appendChild(card);
		});
	});
}

*/