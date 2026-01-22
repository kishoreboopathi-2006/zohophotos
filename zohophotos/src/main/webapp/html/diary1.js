document.getElementById("diaryForm").addEventListener("submit", submitDiary);
let diaryDetails = [];
const photos = [];
let entry = false;

window.onload = async function() {
	// Set initial date display but do NOT use mock data
	let today = new Date().toISOString().split('T')[0];
	document.getElementById("dateInput").textContent = today;
	document.getElementById("date").value = today;

	// Original fetch logic integration (will error in preview but works on your server)
	diaryDetails = await fetchDiaryDetails();
	if (diaryDetails) {
		filldiaryDetails(today);
	}
};

function submitDiary(e) {
	// --- YOUR ORIGINAL BACKEND LOGIC ---
	e.preventDefault();
	var form = document.getElementById("diaryForm");
	let formData = new FormData(form);
	formData.append("entry", entry);
	console.log(form);
	console.log(formData);
	fetch("../getDiaryData", {
		method: "post",
		body: formData
	}).then(handleResponse).then(showMessage).catch(showError);
}

function handleResponse(response) {
	let a = response.text();
	return a;
}

function showMessage(data) {
	document.getElementById("message").innerHTML = data;
	document.getElementById("file-upload").value = "";
	setTimeout(() => { document.getElementById("message").innerHTML = ""; }, 2000);
}

function showError() {
	document.getElementById("message").innerHTML = "Error saving diary";
}

let overlay = null;

function previewimage(e) {
	const files = e.target.files;
	const preview = document.getElementById("preview");

	Array.from(files).forEach(file => {
		const img = document.createElement("img");
		img.className = "images";
		img.src = URL.createObjectURL(file);
		img.style.width = "100px";
		img.style.height = "100px";
		img.style.margin = "3px";
		img.style.cursor = "pointer";
		// Added styling for polaroid look
		img.style.boxShadow = "2px 2px 5px rgba(0,0,0,0.3)";
		img.style.border = "3px solid white";

		img.addEventListener("click", function() {
			toggleBigImage(img.src);
		});

		preview.appendChild(img);
	});
}

function toggleBigImage(src) {
	if (overlay) {
		overlay.remove();
		overlay = null;
		return;
	}

	overlay = document.createElement("div");
	overlay.style.position = "fixed";
	overlay.style.top = "0";
	overlay.style.left = "0";
	overlay.style.width = "100%";
	overlay.style.height = "100%";
	overlay.style.background = "rgba(0,0,0,0.8)";
	overlay.style.display = "flex";
	overlay.style.justifyContent = "center";
	overlay.style.alignItems = "center";
	overlay.style.zIndex = "1000";

	const bigImg = document.createElement("img");
	bigImg.src = src;
	bigImg.style.maxWidth = "80%";
	bigImg.style.maxHeight = "80%";
	bigImg.style.borderRadius = "8px";
	bigImg.style.boxShadow = "0 0 20px black";

	overlay.appendChild(bigImg);
	overlay.addEventListener("click", function() {
		overlay.remove();
		overlay = null;
	});

	document.body.appendChild(overlay);
}

async function clearImage() {
	// --- YOUR ORIGINAL BACKEND LOGIC ---
	let message = await deleteImages();
	const imgs = document.querySelectorAll("#preview .images");
	imgs.forEach(el => {
		URL.revokeObjectURL(el.src);
		el.remove();
	});
	console.log(message);
	// Fixed: value assignment for file input
	document.getElementById("file-upload").value = "";
}

async function deleteImages() {
	// --- YOUR ORIGINAL BACKEND LOGIC ---
	console.log(photos);
	const response = await fetch("../deletePhotos", {
		method: "post",
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({ photoId: photos })
	});
	if (!response.ok) {
		throw new Error("HTTP error:" + response.status)
	}
	const data = await response.text();
	return data;
}


// --- CALENDAR LOGIC (Improved Visuals, Original Data Logic) ---
const dateInput = document.getElementById("dateInput");
const calendar = document.getElementById("calendar");
const calendarGrid = document.getElementById("calendarGrid");
const monthYear = document.getElementById("monthYear");
let currentDate = new Date();

async function showCalender() {
	calendar.style.display = calendar.style.display === "block" ? "none" : "block";
	diaryDetails = await (fetchDiaryDetails());
	renderCalendar();
}

async function fetchDiaryDetails() {
	// --- YOUR ORIGINAL BACKEND LOGIC ---
	try {
		const response = await fetch("../diaryDetails");
		if (!response.ok) {
			throw new Error("HTTP error " + response.status);
		}
		console.log("ak");
		const data = await response.json();
		console.log("data => ", JSON.stringify(data));
		return data;
	} catch (error) {
		console.log("error:" + error);
		return []; // Return empty array on error so UI doesn't crash
	}
}

function renderCalendar() {
	calendarGrid.innerHTML = "";
	const year = currentDate.getFullYear();
	const month = currentDate.getMonth();
	console.log(year + ":" + month);
	monthYear.textContent = currentDate.toLocaleString("default", {
		month: "long",
		year: "numeric"
	});

	const days = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
	days.forEach(d => {
		const div = document.createElement("div");
		div.textContent = d;
		div.className = "day-name";
		calendarGrid.appendChild(div);
	});

	const firstDay = new Date(year, month, 1).getDay();
	const daysInMonth = new Date(year, month + 1, 0).getDate();
	console.log(firstDay + "d:" + daysInMonth);

	// Empty slots before 1st day
	for (let i = 0; i < firstDay; i++) {
		calendarGrid.appendChild(document.createElement("div"));
	}

	// Dates
	for (let day = 1; day <= daysInMonth; day++) {
		let dateObj = new Date(year, month, day);
		// Ensure date format matches 'en-CA' (YYYY-MM-DD) which is standard for date inputs
		let dateStr = dateObj.getFullYear() + '-' +
			String(dateObj.getMonth() + 1).padStart(2, '0') + '-' +
			String(dateObj.getDate()).padStart(2, '0');

		// Safety check for diaryDetails
		const exists = diaryDetails && diaryDetails.some(diary => diary.date === dateStr);

		const div = document.createElement("div");
		div.textContent = day;
		if (exists) {
			console.log("green");
			// Improved styling from my version
			div.style.color = "red";
			div.style.backgroundColor = "#e0f2f1";
			div.style.fontWeight = "bold";
		}
		div.className = "day";
		div.onclick = () => {
			let currentSelected = document.getElementById("date").value;
			// Determine direction for flip
			let direction = (dateStr > currentSelected) ? 1 : -1;

			dateInput.textContent = dateStr;
			calendar.style.display = "none";
			document.getElementById("date").value = dateStr;

			// Trigger Flip
			flipPage(direction, dateStr);
		};
		calendarGrid.appendChild(div);
	}
}

function filldiaryDetails(date) {
	console.log(date);
	// Safety check
	if (!diaryDetails) return;

	let diaryData = diaryDetails.find(diary => diary.date === date);
	const preview = document.getElementById("preview");

	document.getElementById("date").value = date;
	dateInput.textContent = date;

	if (diaryData) {
		entry = true;
		document.getElementById("title").value = diaryData.title;
		document.getElementById("content").value = diaryData.content;
		preview.innerHTML = "";

		diaryData.photos.forEach(photo => {
			const img = document.createElement("img");
			img.src = photo.url;
			img.className = "images";
			img.style.width = "100px";
			img.style.height = "100px";
			preview.appendChild(img);
			photos.push(photo.photoId);
			img.addEventListener("click", function() {
				toggleBigImage(img.src);
			});
		});
		console.log(photos);
	}
	else {
		entry = false;
		document.getElementById("title").value = "";
		document.getElementById("content").value = "";
		preview.innerHTML = "";
	}
}

async function getDate() {
	let date = document.getElementById("date").value;
	console.log("success:" + date);
	diaryDetails = await (fetchDiaryDetails());
	filldiaryDetails(date);
	console.log("sele");
}

function changeMonth(step) {
	currentDate.setMonth(currentDate.getMonth() + step);
	renderCalendar();
}

// --- NEW FUNCTION: REALISTIC PAGE FLIP ANIMATION ---
// This is visual logic only - it calls your existing filldiaryDetails()
function goToDate(direction) {
	let currentVal = document.getElementById("date").value;
	let currDate = new Date(currentVal);

	// Add or subtract a day
	currDate.setDate(currDate.getDate() + direction);

	// Format new date YYYY-MM-DD
	let newDateStr = String(currDate.getDate()).padStart(2, '0') + '-' +
		String(currDate.getMonth() + 1).padStart(2, '0') + '-' + currDate.getFullYear();
		document.getElementById("date-formater").textContent=newDateStr;
		console.log(newDateStr);
	flipPage(direction, newDateStr);
}

function flipPage(direction, targetDate) {
	const turningPage = document.getElementById("turning-page");
	const frontFace = turningPage.querySelector('.front');
	const backFace = turningPage.querySelector('.back');

	// 1. Reset Animation
	turningPage.className = "";
	void turningPage.offsetWidth; // Trigger reflow

	// 2. Decide Direction & Textures
	if (direction === 1) {
		// Next: Right page turns to Left
		frontFace.className = "page-face front face-lined";
		backFace.className = "page-face back face-plain";
		turningPage.classList.add("animate-next");
	} else {
		// Prev: Left page turns to Right
		frontFace.className = "page-face front face-plain";
		backFace.className = "page-face back face-lined";
		turningPage.classList.add("animate-prev");
	}

	// 3. MID-POINT Logic 
	// Wait until page blocks view (700ms), then load data
	setTimeout(() => {
		filldiaryDetails(targetDate);
	}, 700);

	// 4. Cleanup after animation ends
	setTimeout(() => {
		turningPage.className = "";
	}, 1400);
}