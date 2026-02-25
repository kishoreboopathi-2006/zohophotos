document.getElementById("diaryForm").addEventListener("submit", submitDiary);
let diaryDetails = [];
const photos = [];
let entry = false;

/* ---------------- DATE FORMATTER (DISPLAY ONLY) ---------------- */
function formatDisplayDate(dateStr) {
	const d = new Date(dateStr);
	return String(d.getDate()).padStart(2, '0') + "-" +
		String(d.getMonth() + 1).padStart(2, '0') + "-" +
		d.getFullYear();
}

/* ---------------- PAGE LOAD ---------------- */
window.onload = async function() {
	let today = new Date().toISOString().split('T')[0];

	// display format
	document.getElementById("dateInput").textContent = formatDisplayDate(today);

	// keep logic format
	document.getElementById("date").value = date;
	document.getElementById("rightDateDisplay").textContent = formatDisplayDate(today);
	diaryDetails = await fetchDiaryDetails();
	if (diaryDetails) {
		filldiaryDetails(today);
	}
};

/* ---------------- FORM SUBMIT ---------------- */
function submitDiary(e) {
	e.preventDefault();
	let form = document.getElementById("diaryForm");
	let formData = new FormData(form);
	let date=document.getElementById("date").value;
	console.log(date);
	formData.append("entry", entry);
	formData.append("date",date)

	fetch("/zohophotos/getDiaryData", {
		method: "post",
		body: formData
	})
		.then(handleResponse)
		.then(showMessage)
		.catch(showError);
}

function handleResponse(response) {
	return response.text();
}

function showMessage(data) {
	document.getElementById("message").innerHTML = data;
	setTimeout(() => {
		document.getElementById("message").innerHTML = "";
	}, 3000);
}

function showError() {
	document.getElementById("message").innerHTML = "Error saving diary";
}

/* ---------------- IMAGE PREVIEW ---------------- */
let overlay = null;


function previewimage(e) {
	const files = e.target.files;
	const preview = document.getElementById("preview");

	Array.from(files).forEach(file => {
		const img = document.createElement("img");
		img.className = "images";
		img.src = URL.createObjectURL(file);
		img.style.width="100%";
		img.style.margin = "3px";
		img.style.cursor = "pointer";
		img.style.boxShadow = "2px 2px 5px rgba(0,0,0,0.3)";
		img.style.border = "3px solid white";
		img.addEventListener("click", function() {
			toggleBigImage(img.src);
		});

		preview.appendChild(img);
	});
	updateLayout();
	
}
function updateLayout() {
	const preview = document.getElementById("preview");
	const count = preview.children.length;

	
	preview.classList.remove(
		"layout-1",
		"layout-2",
		"layout-3",
		"layout-4",
		"layout-5"
	);

	if (count === 1) preview.classList.add("layout-1");
	else if (count === 2) preview.classList.add("layout-2");
	else if (count === 3) preview.classList.add("layout-3");
	else if (count === 4) preview.classList.add("layout-4");
	else preview.classList.add("layout-5");
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

	overlay.appendChild(bigImg);
	overlay.addEventListener("click", () => {
		overlay.remove();
		overlay = null;
	});

	document.body.appendChild(overlay);
}

/* ---------------- DELETE IMAGES ---------------- */
async function clearImage() {
	await deleteImages();

	document.querySelectorAll("#preview .images").forEach(el => {
		URL.revokeObjectURL(el.src);
		el.remove();
	});

	document.getElementById("file-upload").value = "";
}

async function deleteImages() {
	const response = await fetch("/zohophotos/deletePhotos", {
		method: "post",
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify({ photoId: photos })
	});

	if (!response.ok) throw new Error("HTTP error:" + response.status);
	return await response.text();
}

/* ---------------- CALENDAR ---------------- */
const dateInput = document.getElementById("dateInput");
const calendar = document.getElementById("calendar");
const calendarGrid = document.getElementById("calendarGrid");
const monthYear = document.getElementById("monthYear");

let currentDate = new Date();

async function showCalender() {
	calendar.style.display = calendar.style.display === "block" ? "none" : "block";
	diaryDetails = await fetchDiaryDetails();
	renderCalendar();
}

async function fetchDiaryDetails() {
	try {
		const response = await fetch("/zohophotos/diaryDetails");
		if (!response.ok) throw new Error("HTTP error " + response.status);
		return await response.json();
	} catch (error) {
		console.log(error);
		return [];
	}
}

function renderCalendar() {
	calendarGrid.innerHTML = "";

	const year = currentDate.getFullYear();
	const month = currentDate.getMonth();

	monthYear.textContent = currentDate.toLocaleString("default", {
		month: "long",
		year: "numeric"
	});

	const firstDay = new Date(year, month, 1).getDay();
	const daysInMonth = new Date(year, month + 1, 0).getDate();

	for (let i = 0; i < firstDay; i++) {
		calendarGrid.appendChild(document.createElement("div"));
	}

	for (let day = 1; day <= daysInMonth; day++) {
		let dateObj = new Date(year, month, day);

		let dateStr =
			dateObj.getFullYear() + '-' +
			String(dateObj.getMonth() + 1).padStart(2, '0') + '-' +
			String(dateObj.getDate()).padStart(2, '0');

		const exists = diaryDetails && diaryDetails.some(d => d.date === dateStr);

		const div = document.createElement("div");
		div.textContent = day;
		div.className = "day";

		if (exists) {
			div.style.color = "red";
			div.style.fontWeight = "bold";
		}

		div.onclick = () => {
			let currentSelected = document.getElementById("date").value;
			let direction = (dateStr > currentSelected) ? 1 : -1;

			// DISPLAY FORMAT ONLY
			dateInput.textContent = formatDisplayDate(dateStr);

			document.getElementById("date").value = dateStr;
			document.getElementById("rightDateDisplay").textContent = formatDisplayDate(dateStr);

			calendar.style.display = "none";

			flipPage(direction, dateStr);
		};

		calendarGrid.appendChild(div);
	}
}

/* ---------------- FILL DIARY ---------------- */
function filldiaryDetails(date) {
	if (!diaryDetails) return;

	let diaryData = diaryDetails.find(d => d.date === date);
	const preview = document.getElementById("preview");

	// keep logic format
	document.getElementById("date").value = date;


	// DISPLAY FORMAT
	dateInput.textContent = formatDisplayDate(date);
	document.getElementById("rightDateDisplay").textContent = formatDisplayDate(date);
	if (diaryData) {
		entry = true;

		document.getElementById("title").value = diaryData.title;
		document.getElementById("content").value = diaryData.content;

		preview.innerHTML = "";

		diaryData.photos.forEach(photo => {
			const img = document.createElement("img");
			img.src = photo.url;
			img.className = "images";
			img.style.width = "100%";
			img.style.height = "100%";
			

			img.addEventListener("click", () => toggleBigImage(img.src));
			preview.appendChild(img);
			updateLayout();
			photos.push(photo.photoId);
		});
	}
	else {
		entry = false;
		document.getElementById("title").value = "";
		document.getElementById("content").value = "";
		preview.innerHTML = "";
	}
}

/* ---------------- NAVIGATION ---------------- */
function goToDate(direction) {
	let currentVal = document.getElementById("date").value;
	let currDate = new Date(currentVal);

	currDate.setDate(currDate.getDate() + direction);

	let newDateStr =
		currDate.getFullYear() + '-' +
		String(currDate.getMonth() + 1).padStart(2, '0') + '-' +
		String(currDate.getDate()).padStart(2, '0');

	flipPage(direction, newDateStr);
}

/* ---------------- PAGE FLIP ---------------- */
function flipPage(direction, targetDate) {
	const turningPage = document.getElementById("turning-page");
	const frontFace = turningPage.querySelector('.front');
	const backFace = turningPage.querySelector('.back');

	turningPage.className = "";
	void turningPage.offsetWidth;

	if (direction === 1) {
		frontFace.className = "page-face front face-lined";
		backFace.className = "page-face back face-plain";
		turningPage.classList.add("animate-next");
	} else {
		frontFace.className = "page-face front face-plain";
		backFace.className = "page-face back face-lined";
		turningPage.classList.add("animate-prev");
	}

	setTimeout(() => filldiaryDetails(targetDate), 700);
	setTimeout(() => turningPage.className = "", 1400);
}
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
	console.log("reminer");
	window.location.href = "/zohophotos/html/reminder/reminder.html";
}

function albumPage() {
	window.location.href = "/zohophotos/html/album/album.html";
}


function changeMonth(step) {
	currentDate.setMonth(currentDate.getMonth() + step);
	renderCalendar();
}


