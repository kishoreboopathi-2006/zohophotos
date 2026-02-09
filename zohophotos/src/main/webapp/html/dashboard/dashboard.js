
let favourite = [];
let favIcons = document.getElementsByClassName("fav-icon");
const grid = document.getElementById("galleryGrid");
let entry = true;
let images = [];
let urls = [];
let currentIndex = 0;
let aiResponsePhotoDetails = [];
let reminderDetails = [];
window.addEventListener("load", () => {
	if (entry) {
		fetch("/zohophotos/getFavouritePhotos").then(handleResponse).then(handleData).then(showError);
		function handleResponse(response) {
			return response.json()
		}
		function handleData(data) {
			console.log(data);
			favourite = data;
			entry = false;
		}
		function showError(error) {
			console.log(error);
		}
	}
});
window.addEventListener("load", () => {
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
				img.dataset.fileId = file.resourceId;
				const aiIcon = document.createElement("div");
				aiIcon.className = "ai-icon";
				aiIcon.innerHTML = "✨";
				aiIcon.onclick = () => describeFromIcon(aiIcon);
				const overlay = document.createElement("div");
				const favIcon = document.createElement("div");
				favIcon.className = "fav-icon";
				favIcon.innerHTML = "❤️";
				console.log(file.previewUrl);
				const fav = favourite.some(data => data.previewUrl === file.previewUrl);
				console.log("fav:" + fav);
				if (fav) {
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


window.addEventListener("load", function() {
	fetch("/zohophotos/getReminderDetails").then(handleResponse).then(handleData).catch(showError);
	function handleResponse(response) {
		return response.json();
	}
	function handleData(data) {
		reminderDetails = data;
		const birthday=reminderDetails.find(upcomingBirtday=>{
			return upcomingBirtday.category==="birthday";
		});
		const date=new Date(birthday.Date).toLocaleDateString("en-GB", {
		  day: "2-digit",
		  month: "short",
		  year: "numeric"
		});
		document.getElementById("photo").src=birthday.previewUrl;
		document.getElementById("date").textContent=date;
		document.getElementById("title").textContent=birthday.title;
		document.getElementById("message").textContent=birthday.message;
		console.log("birthday"+JSON.stringify(birthday));
	}
	function showError(error) {
		console.log(error);
	}
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
					console.log("favoutire:" + favourite);
					e.target.classList.toggle("is-favourite");
				}
			}
			function showError(err) {
				console.log(err);
			}
		}
	}
});

function favouritePage() {
	window.location.href = "/zohophotos/html/favourite/favourite.html";
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

document.getElementById("search").addEventListener("click", function() {
	fetch("/zohophotos/getAiResponse")
		.then(handleResponse)
		.then(handleData)
		.catch(showError);
	function handleResponse(response) {
		return response.json();
	}
	function handleData(data) {
		aiResponsePhotoDetails = data;
		console.log(data);
	}
	function showError(error) {
		console.log(error);
	}
});
document.getElementById("search-input").addEventListener("keydown", function(event) {

	if (event.key === "Enter") {
		const query = document.getElementById("search-input").value;
		const photoDetails = aiResponsePhotoDetails.filter(response => {
			return response.categories.some(categorie => categorie.includes(query));
		});
		console.log(JSON.stringify(photoDetails, null, 2));
		renderPhotos(photoDetails);
	}
});
function renderPhotos(photoDetails) {
	const searchContainer = document.getElementById("search-overlay");
	searchContainer.style.display = "flex";
	searchContainer.innerHTML = "";
	const exitButton = document.createElement("button");
	exitButton.className = "exit";
	exitButton.id = "exit-button";
	exitButton.textContent = "exit";
	searchContainer.appendChild(exitButton);
	document.getElementById("exit-button").addEventListener("click", function() {
		const searchContainer = document.getElementById("search-overlay");
		searchContainer.style.display = "none";
	});
	photoDetails.forEach(photo => {
		const photoUrl = images.find(image => {
			return photo.workdrive_file_id === image.resourceId;
		});
		if (photoUrl) {
			const card = document.createElement("div");
			card.className = "photo-card glass-panel";
			const img = document.createElement("img");
			img.src = photoUrl.previewUrl;
			card.appendChild(img);
			searchContainer.appendChild(card);
		}
	});
};
document.getElementById("close-btn").addEventListener("click", function() {
	console.log("click");
	document.getElementById("search-input").value = "";
});
function albumPage() {
	window.location.href = "/zohophotos/html/album/album.html";
}
document.getElementById("galleryGrid").addEventListener("click", function(e) {
	const img = e.target.closest("img");
	urls = images.map(img => {
		return img.previewUrl;
	});
	currentIndex = urls.findIndex(url => {
		return url === img.src;
	});
	console.log(urls);
	console.log(img.src);
	openFullView();
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
	img.className = "view-photo";
	const closeBtn = document.createElement("span");
	closeBtn.id = "exit";
	closeBtn.className = "close-view";
	closeBtn.innerHTML = "x";
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
const themeBtn = document.getElementById("themetoggle");
        const themeIcon = document.getElementById("theme-icon");

        function updateTheme(isDark) {
            if (isDark) {
                document.body.classList.add("dark");
                themeIcon.className = "ph ph-sun";
            } else {
                document.body.classList.remove("dark");
                themeIcon.className = "fa-regular fa-moon";
            }
        }


        themeBtn.addEventListener("click", () => {
            const isDark = !document.body.classList.contains("dark");
            localStorage.setItem("theme", isDark ? "dark" : "light");
            updateTheme(isDark);
        });

let storyMode = false;
let selectedStoryImages = [];

/* ================= AI STORY BUTTON ================= */

document.getElementById("aiStoryBtn").addEventListener("click", () => {

	const cards = document.querySelectorAll(".photo-card");
	if (!storyMode) {
		storyMode = true;
		selectedStoryImages = [];

		cards.forEach(card => {
			card.classList.add("story-mode");


			if (!card.querySelector(".select-check")) {
				const tick = document.createElement("div");
				tick.className = "select-check";
				tick.innerHTML = "✔";
				card.appendChild(tick);

				tick.onclick = (e) => {
					e.stopPropagation();
					const img = card.querySelector("img");
					toggleStorySelection(card, img.dataset.fileId);
				};
			}
		});

		document.getElementById("aiStoryBtn").innerHTML =
			"<i class='ph-fill ph-sparkle'></i> Generate Story";

		return;
	}


	if (selectedStoryImages.length === 0) {
		alert("Please select at least one photo");
		return;
	}

	openStoryModal("⏳ Generating story...");

	fetch("/zohophotos/aiStory", {
		method: "POST",
		headers: { "Content-Type": "application/json" },
		body: JSON.stringify({
			imageIds: selectedStoryImages,
			lang: "tanglish"
		})
	})
		.then(res => res.json())
		.then(data => {
			if (!data.story) {
				openStoryModal(" Unable to generate story");
				resetStoryMode();
				return;
			}
			showStoryWithTranslate(data.story);
			resetStoryMode();
		})
		.catch(() => {
			openStoryModal(" Error generating story");
			resetStoryMode();
		});
});

/* ================= STORY SELECTION ================= */

function toggleStorySelection(card, fileId) {

	const tick = card.querySelector(".select-check");

	if (card.classList.contains("story-selected")) {
		card.classList.remove("story-selected");
		tick.classList.remove("selected");
		selectedStoryImages =
			selectedStoryImages.filter(id => id !== fileId);
	} else {
		card.classList.add("story-selected");
		tick.classList.add("selected");
		selectedStoryImages.push(fileId);
	}
}

/* ================= STORY MODAL ================= */

function openStoryModal(text) {
	document.getElementById("ai-modal-title").innerHTML =
		"✨ AI Photo Story";
	document.getElementById("ai-modal-content").innerHTML =
		`<div style="line-height:1.8;font-size:16px">${text}</div>`;
	document.getElementById("ai-modal-overlay").classList.add("active");
}

/* ================= TRANSLATE UI ================= */

function showStoryWithTranslate(storyText) {
	document.getElementById("ai-modal-content").innerHTML = `
        <div id="storyText"
             style="line-height:1.8;font-size:16px;margin-bottom:15px">
            ${storyText.replace(/\n/g, "<br>")}
        </div>

        <div style="display:flex;gap:10px;justify-content:flex-end">
            <button class="btn-primary" onclick="translateStory('english')">
                English
            </button>
            <button class="btn-primary" onclick="translateStory('tanglish')">
                Tamil
            </button>
        </div>
    `;
}

/* ================= TRANSLATE API ================= */

function translateStory(lang) {

	const text = document.getElementById("storyText").innerText;
	document.getElementById("storyText").innerHTML = "⏳ Translating...";

	fetch("/zohophotos/translateStory", {
		method: "POST",
		headers: { "Content-Type": "application/json" },
		body: JSON.stringify({ text, lang })
	})
		.then(res => res.json())
		.then(data => {
			document.getElementById("storyText").innerHTML =
				data.story.replace(/\n/g, "<br>");
		})
		.catch(() => {
			document.getElementById("storyText").innerHTML =
				" Translation failed";
		});
}
/* ================= RESET ================= */

function resetStoryMode() {

	storyMode = false;
	selectedStoryImages = [];

	document.getElementById("aiStoryBtn").innerHTML =
		"<i class='ph-fill ph-sparkle'></i> AI Story";

	document.querySelectorAll(".photo-card").forEach(card => {
		card.classList.remove("story-mode", "story-selected");
		const tick = card.querySelector(".select-check");
		if (tick) tick.remove();
	});
}


function reminderPage() {
	console.log("reminer");
	window.location.href = "/zohophotos/html/reminder/reminder.html";
}
        
        if (localStorage.getItem("theme") === "dark") updateTheme(true);
function trashImage(){
	window.location.href=""
}
        
