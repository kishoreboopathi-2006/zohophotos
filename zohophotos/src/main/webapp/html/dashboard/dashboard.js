let favourite = [];
let favIcons = document.getElementsByClassName("fav-icon");
const grid = document.getElementById("galleryGrid");
let entry = true;
let images = [];
let urls = [];
let currentIndex = 0;
let aiResponsePhotoDetails = [];
let reminderDetails = [];
let resourceIds = [];
let deletedPhotos = [];
let userDetails = [];

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
window.addEventListener("load", async () => {
	/* deleted photos*/

	const deleteRes = await fetch("/zohophotos/getDeletedPhotos");
	deletedPhotos = await deleteRes.json();
	console.log(JSON.stringify(deletedPhotos, null, 2));
	const userRes = await fetch("/zohophotos/getUserDetails");
	userDetails = await userRes.json();
	console.log(JSON.stringify(userDetails));
	insertUserDetails();
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
			images = images.filter(img => {
				return !deletedPhotos.some(del => del.resourceId === img.resourceId)
			});
			/*[...images].forEach(file => {
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
			}*/
			[...images].forEach(file => {
				const img = document.createElement("img");
				img.src = file.previewUrl;
				img.loading = "lazy";
				img.alt = file.imageName || "Photo";
				sliderTrack.appendChild(img);
			});

			/* SLIDER ANIMATION ENABLE/DISABLE */
			if (images.length > 3) {
				sliderTrack.style.animation = "slide 30s linear infinite";
			} else {
				sliderTrack.style.animation = "none";
			}

			/* CENTER IMAGE HIGHLIGHT */
			function highlightCenter() {
				const imgs = sliderTrack.querySelectorAll("img");
				const center = window.innerWidth / 2;

				imgs.forEach(img => {
					const rect = img.getBoundingClientRect();
					const imgCenter = rect.left + rect.width / 2;

					if (Math.abs(center - imgCenter) < rect.width / 2) {
						img.classList.add("active");
					} else {
						img.classList.remove("active");
					}
				});
			}
			/* RUN CONTINUOUSLY */
			setInterval(highlightCenter, 100);
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
				console.log(file.previewUrl);
				const fav = favourite.some(data => data.previewUrl === file.previewUrl);
				console.log("fav:" + fav)
				card.appendChild(img);
				card.appendChild(aiIcon);
				card.appendChild(overlay);
				grid.appendChild(card);
			});

		})
		.catch(err => {
			console.error(err);
			grid.innerHTML = "<p style='color:red'>Unable to load photos</p>";
		});
});
function formetDate(date) {
	const option = { year: "numeric", month: "long", day: "numeric" }
	return new Date(date).toLocaleDateString("en-Us", option);

}


/*
window.addEventListener("load", function() {
	fetch("/zohophotos/getReminderDetails").then(handleResponse).then(handleData).catch(showError);
	function handleResponse(response) {
		return response.json();
	}
	function handleData(data) {
		reminderDetails = data;
		const birthday = reminderDetails.find(upcomingBirtday => {
			return upcomingBirtday.category === "birthday";
		});
		const date = new Date(birthday.Date).toLocaleDateString("en-GB", {
			day: "2-digit",
			month: "short",
			year: "numeric"
		});
		document.getElementById("photo").src = birthday.previewUrl;
		document.getElementById("date").textContent = date;
		document.getElementById("title").textContent = birthday.title;
		document.getElementById("message").textContent = birthday.message;
		console.log("birthday" + JSON.stringify(birthday));
	}
	function showError(error) {
		console.log(error);
	}
});*/
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
					console.log(favourite);
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
	console.log(fileId);
	if (!fileId) {
		alert("File ID missing");
		return;
	}
	iconEl.innerHTML = "⏳";
	document.getElementById("ai-modal-title").innerHTML = "✨ AI Photo Description";
	document.getElementById("ai-modal-content").innerHTML =
		`<div class="loading-spinner"></div>`;
	document.getElementById("ai-modal-overlay").classList.add("active");
	fetch("/zohophotos/getDescriptionForPhoto", {
		method: "POST",
		headers: { "Content-Type": "application/json" },
		body: JSON.stringify({ file_id: fileId })
	})
		.then(res => res.text())
		.then(data => {
			console.log(data);
			if (!data) {
				document.getElementById("ai-modal-content").innerHTML =
					"<p style='color:red'>No AI response received</p>";
				iconEl.innerHTML = "✨";
				return;
			}
			console.log(data);
			setTimeout(() => {
				document.getElementById("ai-modal-content").innerHTML = `
						<div class="fade-in" style="line-height:1.8; font-size:16px; color:#334155;">
							${data.replace(/\n/g, "<br>")}
						</div>`;
				iconEl.innerHTML = "✨";
			}, 1000);
		})
		.catch(err => {
			console.error(err);
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

function albumPage() {
	window.location.href = "/zohophotos/html/album/album.html";
}
document.getElementById("galleryGrid").addEventListener("click", function(e) {
	const img = e.target.closest("img");
	if (img) {
		urls = images.map(img => {
			return img.previewUrl;
		});
		resourceIds = images.map(img => {
			return img.resourceId;
		})

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
	/*=====================menu creation=====================*/	const menu = document.createElement("div");
	menu.className = "menu";

	const deleteIcon = document.createElement("div");
	deleteIcon.className = "delete";
	deleteIcon.setAttribute("data-tooltip", "Delete");
	deleteIcon.innerHTML = `<i class="ph-bold ph-trash"></i>`;

	const aiIcon = document.createElement("div");
	aiIcon.className = "ai";
	aiIcon.setAttribute("data-tooltip", "AI Enhance");
	aiIcon.innerHTML = `<i class="ph-bold ph-sparkle"></i>`;
	const zoomWrapper = document.createElement("div");
	zoomWrapper.className = "trigger-wrapper";

	const zoomTrigger = document.createElement("div");
	zoomTrigger.className = "trigger-btn zoom-btn";
	zoomTrigger.setAttribute("data-tooltip", "Zoom");
	zoomTrigger.innerHTML = `<i class="ph-bold ph-magnifying-glass"></i>`;

	const zoomGroup = document.createElement("div");
	zoomGroup.className = "floating-group";

	const zoomIn = document.createElement("div");
	zoomIn.className = "zoom-action";
	zoomIn.setAttribute("data-tooltip", "Zoom In");
	zoomIn.innerHTML = `<i class="ph-bold ph-magnifying-glass-plus"></i>`;
	zoomIn.onclick = (e) => {
		e.stopPropagation();
		currentZoom = Math.min(currentZoom + 0.2, 3);
		viewBox.style.transform = `scale(${currentZoom})`;
	};

	const zoomOut = document.createElement("div");
	zoomOut.className = "zoom-action";
	zoomOut.setAttribute("data-tooltip", "Zoom Out");
	zoomOut.innerHTML = `<i class="ph-bold ph-magnifying-glass-minus"></i>`;
	zoomOut.onclick = (e) => {
		e.stopPropagation();
		currentZoom = Math.max(currentZoom - 0.2, 0.5);
		viewBox.style.transform = `scale(${currentZoom})`;
	};

	zoomGroup.appendChild(zoomOut);
	zoomGroup.appendChild(zoomIn);
	zoomWrapper.appendChild(zoomGroup);
	zoomWrapper.appendChild(zoomTrigger);
	const favIcon = document.createElement("div");
	favIcon.className = "fav";
	favIcon.setAttribute("data-tooltip", "Favorite");
	const fav = favourite.some(data => data.previewUrl === img.src);
	favIcon.innerHTML = fav ? `<i class="fa-solid fa-heart"></i>` : `<i class="fa-regular fa-heart"></i>`;

	// TRIGGER WRAPPER (To anchor floating group directly above share)
	const triggerWrapper = document.createElement("div");
	triggerWrapper.className = "trigger-share-wrapper";

	const triggerIcon = document.createElement("div");
	triggerIcon.className = "trigger-share";
	triggerIcon.setAttribute("data-tooltip", "Share");
	triggerIcon.innerHTML = `<i class="ph-bold ph-share-network"></i>`;

	const floatingGroup = document.createElement("div");
	floatingGroup.className = "floating-group";

	const shareIcon = document.createElement("div"); // WhatsApp
	shareIcon.className = "share";
	shareIcon.setAttribute("data-tooltip", "WhatsApp");
	shareIcon.innerHTML = `<i class="fa-brands fa-whatsapp"></i>`;

	const copyIcon = document.createElement("div"); // Copy Link
	copyIcon.className = "copy";
	copyIcon.setAttribute("data-tooltip", "Copy Link");
	copyIcon.innerHTML = `<i class="ph-bold ph-link"></i>`;

	floatingGroup.appendChild(shareIcon);
	floatingGroup.appendChild(copyIcon);
	triggerWrapper.appendChild(floatingGroup);
	triggerWrapper.appendChild(triggerIcon);

	// Click Handlers
	favIcon.onclick = () => clickFavourite(fav, favIcon, img.src, "photo");
	deleteIcon.onclick = () => deletePhoto(img.src, img.dataset.resourceId);
	copyIcon.onclick = () => copyPhoto(img.src);
	shareIcon.onclick = () => shareOnWhatsApp(img.src);

	// Toggle Logic
	triggerIcon.onclick = (e) => {
		e.stopPropagation();
		floatingGroup.classList.toggle('show');
		triggerIcon.classList.toggle('active');
	};

	// Assembly Order
	
	menu.appendChild(aiIcon);
	menu.appendChild(favIcon);
	menu.appendChild(triggerWrapper);
	menu.appendChild(deleteIcon);

	viewContainer.appendChild(menu);
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
/*
const themeBtn = document.getElementById("themetoggle");
const themeIcon = document.getElementById("theme-icon");
*/
function updateTheme(isDark) {
	if (isDark) {
		document.body.classList.add("dark");
		themeIcon.className = "ph ph-sun";
	} else {
		document.body.classList.remove("dark");
		themeIcon.className = "fa-regular fa-moon";
	}
}

/*
themeBtn.addEventListener("click", () => {
	const isDark = !document.body.classList.contains("dark");
	localStorage.setItem("theme", isDark ? "dark" : "light");
	updateTheme(isDark);
});
*/
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
function trashImage() {
	window.location.href = ""
}
function dashboardPage() {
	window.location.href = "/zohophotos/html/dashboard/dashboard.html";
	window.location.reload();
}

/* ================= favourite ================= */
function clickFavourite(fav, favIcon, img, alt) {
	if (fav) {
		let formData = new FormData();
		formData.append("add", "false")
		formData.append("previewUrl", img);
		formData.append("photoName", alt);
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
			favourite = favourite.filter(data =>
				data.previewUrl != img.src
			);
			if (data != "fail") {
				favIcon.innerHTML = `<i class="fa-regular fa-heart"></i>`;
			}
		}
		function showError(err) {
			console.log(err);
		}
	}
	else {
		let formData = new FormData();
		formData.append("add", "true")
		formData.append("previewUrl", img);
		formData.append("photoName", alt);
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
				console.log(data);
				favourite.push(JSON.parse(data));
				console.log("favoutire:" + favourite);
				favIcon.innerHTML = `<i class="fa-solid fa-heart"></i>`;
			}
		}
		function showError(err) {
			console.log(err);
		}
	}
}
/* ================= delete ================= */

function deletePhoto(img, resourceId) {
	const form = new FormData();
	form.append("url", img);
	form.append("resourceId", resourceId);
	console.log(img);
	console.log(resourceId);
	fetch("/zohophotos/deletePhoto", {
		method: "post",
		body: form
	}).then(handleResponse).then(showData).catch(showError);
	function handleResponse(response) {
		return response.text();
	}
	function showData(data) {
		window.location.reload();
		console.log(data);
	}
	function showError(err) {
		console.log(err);
	}
}


/*===============redirect===================*/
function trashPage() {
	window.location.href = "/zohophotos/html/trash/trash.html";
}


/*==================== copy===================== */
function copyPhoto(img) {
	const input = document.createElement("input");
	input.value = img;
	document.body.appendChild(input);
	input.select();
	document.execCommand("copy");
	document.body.removeChild(input);
	alert("Link copied!");

}
function shareOnWhatsApp(imageUrl) {
	const url =
		"https://wa.me/?text=" + encodeURIComponent(imageUrl);
	window.open(url, "_blank");
}
function insertUserDetails() {
	console.log(userDetails.previewUrl);
	const profile = document.getElementById("avatar");
	const img = document.getElementById("profile-img");
	img.className = "profile-img";
	if (insertUserDetails.previewUrl !== null) {
		img.src = userDetails.previewUrl;
		profile.appendChild(img);
	}
}


/*=====================for userprofile==========================*/
let enter = false;
document.getElementById("avatar").addEventListener("click", function() {
	const root = document.getElementById("profile-root");
	root.innerHTML = "";
	console.log(enter);
	if (enter) {
		root.innerHTML = "";
		enter = false;
		return;
	}
	enter = true;
	const container = document.createElement("div");
	container.className = "profile-card";

	// Header decoration
	const header = document.createElement("div");
	header.className = "glass-header";

	// Avatar Section with Hover Effect
	const avatarWrapper = document.createElement("div");
	avatarWrapper.className = "avatar-wrapper";

	const avatarContainer = document.createElement("div");
	avatarContainer.className = "avatar-container";

	// Hidden File Input

	const fileInput = document.createElement("input");
	fileInput.type = "file";
	fileInput.className = "hidden";
	fileInput.accept = "image/*";
	fileInput.style.display = "none";
	// Clicking the container triggers the input

	const img = document.createElement("img");
	img.src = userDetails.previewUrl;
	img.className = "avatar-preview";
	const overlay = document.createElement("div");
	overlay.className = "avatar-overlay";
	overlay.innerHTML = `
	          <span class="text-xl mb-1">📷</span>
	          <span>Change Photo</span>
	      `;
	avatarContainer.onclick = () => {
		console.log("click");
		fileInput.click();
	}
	fileInput.onchange = (e) => {
		const file = e.target.files[0];
		const formData = new FormData();
		formData.append("photo", file);
		formData.append("entry", "profile");
		fetch("/zohophotos/upload", {
			method: "POST",
			body: formData
		})
			.then(res => res.text())
			.then(data => {
				userDetails.previewUrl = data,
					img.src = data,
					insertUserDetails()
			}
			);
	};


	avatarContainer.append(img, overlay);
	avatarWrapper.append(avatarContainer, fileInput);

	// Text Content
	const content = document.createElement("div");
	content.className = "px-8 pt-4 pb-8 text-center";

	const name = document.createElement("h3");
	name.className = "userName";
	name.textContent = userDetails.userName;

	const email = document.createElement("p");
	email.className = "email";
	email.textContent = userDetails.userEmail;

	// Menu Items
	const menu = document.createElement("div");
	menu.className = "text-left space-y-1";

	const createItem = (icon, text, onClick, isDanger = false) => {
		const item = document.createElement("div");
		item.className = `profile-item ${isDanger ? 'danger' : ''}`;
		item.innerHTML = `<span class="text-lg opacity-70">${icon}</span> ${text}`;
		item.onclick = onClick;
		return item;
	};

	const settingsBtn = createItem("⚙️", "Account Settings", () => showMessage("Opening account preferences..."));
	const signoutBtn = createItem("🔌", "Sign Out", () => showMessage("Securely logging out..."), true);

	menu.append(settingsBtn, signoutBtn);
	content.append(name, email, menu);
	container.append(header, avatarWrapper, content);
	root.appendChild(container);

});
/*============================================floating date================================== */
window.addEventListener("DOMContentLoaded", () => {

	const scrollContainer = document.querySelector(".content-scroll");
	const floatingDate = document.getElementById("floating-date");
	const gallery = document.querySelector("#galleryGrid");

	function initObserver() {

		const images = document.querySelectorAll(".photo-card img");

		if (images.length === 0) return;

		const observer = new IntersectionObserver((entries) => {
			entries.forEach(entry => {
				if (entry.isIntersecting) {
					floatingDate.textContent =
						entry.target.dataset.date;
				}
			});
		}, {
			root: scrollContainer,
			threshold: 0
		});

		images.forEach(img => observer.observe(img));

		// Set initial date
		floatingDate.textContent = images[0].dataset.date;
	}

	// Watch for images being added
	const mutationObserver = new MutationObserver(() => {
		const images = document.querySelectorAll(".photo-card img");
		if (images.length > 0) {
			initObserver();
			mutationObserver.disconnect(); // stop watching
		}
	});

	mutationObserver.observe(gallery, {
		childList: true,
		subtree: true
	});

});
/*----------Dark Mode---------------*/
const toggle = document.querySelector(".theme-switch__checkbox");


if (localStorage.getItem("theme") === "dark") {
	document.body.classList.add("dark");
	toggle.checked = true;
}

toggle.addEventListener("change", () => {
	if (toggle.checked) {
		document.body.classList.add("dark");
		localStorage.setItem("theme", "dark");
	} else {
		document.body.classList.remove("dark");
		localStorage.setItem("theme", "light");
	}
});
function toggleSearch(){
    document.getElementById("searchBox").classList.toggle("show");
}







