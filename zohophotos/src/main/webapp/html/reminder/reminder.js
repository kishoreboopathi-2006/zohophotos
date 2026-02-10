let state = {
	reminders: [],          
	activeTab: 'birthday',
	viewDate: new Date(),
	isDark: false
};
let birthday = [];
let wedding = [];
let work = [];
let other = [];

// --- INITIAL LOAD ---
window.addEventListener("load", () => {
	fetch("/zohophotos/getReminderDetails")
		.then(res => res.json())
		.then(data => {
			state.reminders = data.map(r => ({
				id: r.folderId,
				title: r.title,
				date: r.Date,
				type: r.category,
				message: r.message,
				photo: r.previewUrl,
				time: r.time
			}));
			console.log(state);
			getPhotos();
			renderUI();
		})
		.catch(err => console.error(err));
});
function getPhotos() {
	fetch("/zohophotos/")
}

// --- CALENDAR ENGINE ---
function renderCalendar() {
	const grid = document.getElementById('calendar-grid');
	const label = document.getElementById('cal-month-label');

	grid.innerHTML = '';

	const y = state.viewDate.getFullYear();
	const m = state.viewDate.getMonth();

	label.innerText = state.viewDate.toLocaleString('default', {
		month: 'long',
		year: 'numeric'
	});

	const firstDay = new Date(y, m, 1).getDay();
	const lastDate = new Date(y, m + 1, 0).getDate();
	const prevMonthLastDate = new Date(y, m, 0).getDate();

	// Previous month ghost days
	for (let i = firstDay; i > 0; i--) {
		const d = document.createElement('div');
		d.className = 'cal-cell empty';
		d.innerText = prevMonthLastDate - i + 1;
		grid.appendChild(d);
	}

	// Current month days
	for (let i = 1; i <= lastDate; i++) {
		const d = document.createElement('div');
		d.className = 'cal-cell';

		const dStr = `${y}-${String(m + 1).padStart(2, '0')}-${String(i).padStart(2, '0')}`;

		if (state.reminders.some(r => r.date === dStr && r.type === state.activeTab)) {
			d.classList.add('has-event');
		}

		d.innerText = i;
		grid.appendChild(d);
	}
}

// --- CORE RENDER ---
function renderUI() {
	renderCalendar();
	renderHeader();
	renderCount();
	renderReminderList();
	console.log(state);
}

function renderCount() {
	birthday = state.reminders.filter(reminder => {
		return reminder.type === "birthday";
	});
	wedding = state.reminders.filter(reminder => {
		return reminder.type === "wedding";
	});
	work = state.reminders.filter(reminder => {
		return reminder.type === "work";
	});
	other = state.reminders.filter(reminder => {
		return reminder.type === "other";
	});
	document.getElementById("count-birthday").textContent = birthday.length;
	document.getElementById("count-wedding").textContent = wedding.length;
	document.getElementById("count-work").textContent = work.length;
	document.getElementById("count-other").textContent = other.length;

}
function renderReminderList() {
	const grid = document.getElementById("events-grid");
	if (state.activeTab === "birthday") {
		grid.innerHTML = "";
		birthday.forEach(details => {
			const card = document.createElement("div");
			card.className = "card";
			const leftCard = document.createElement("div");
			leftCard.className = "card-left";
			const date = document.createElement("span");
			date.textContent = details.date;
			const img = document.createElement("img");
			img.src = details.photo;
			img.className = "photo-placeholder";
			leftCard.appendChild(date);
			leftCard.appendChild(img);
			const cardRight = document.createElement("div");
			const title = document.createElement("h3");
			title.textContent = details.title;
			title.className = "title";
			const message = document.createElement("p");
			message.className = "msg";
			message.textContent = details.message;
			const trash = document.createElement("div");
			trash.innerHTML = `<i class="ph ph-trash"></i>`;
			trash.className = "trash-btn";
			trash.id = "trash";
			trash.dataset.message = details.message;
			trash.onclick = function() {
				const message = this.dataset.message;
				deleteDetails(message);
			}
			cardRight.appendChild(title);
			cardRight.appendChild(message);
			card.appendChild(leftCard);
			card.appendChild(cardRight);
			card.appendChild(trash);
			grid.appendChild(card);
		});
	}
	else if (state.activeTab === "work") {
		grid.innerHTML = "";
		work.forEach(details => {
			const card = document.createElement("div");
			card.className = "card";
			const leftCard = document.createElement("div");
			leftCard.className = "card-left";
			const date = document.createElement("span");
			date.textContent = details.date;
			const img = document.createElement("img");
			img.src = details.photo;
			img.className = "photo-placeholder";
			leftCard.appendChild(date);
			leftCard.appendChild(img);
			const cardRight = document.createElement("div");
			const title = document.createElement("h3");
			title.textContent = details.title;
			title.className = "title";
			const message = document.createElement("p");
			message.className = "msg";
			message.textContent = details.message;
			const trash = document.createElement("button");
			trash.innerHTML = `<i class="ph ph-trash"></i>`;
			trash.className = "trash-btn";
			trash.dataset.message = details.message;
			trash.id = "trash";
			trash.onclick = function() {
				const message = this.dataset.message;
				deleteDetails(message);
			}
			cardRight.appendChild(title);
			cardRight.appendChild(message);
			card.appendChild(leftCard);
			card.appendChild(cardRight);
			card.appendChild(trash);
			grid.appendChild(card);
		});
	}
	else if (state.activeTab === "wedding") {
		grid.innerHTML = "";
		wedding.forEach(details => {
			const card = document.createElement("div");
			card.className = "card";
			const leftCard = document.createElement("div");
			leftCard.className = "card-left";
			const date = document.createElement("span");
			date.textContent = details.date;
			const img = document.createElement("img");
			img.src = details.photo;
			img.className = "photo-placeholder";
			leftCard.appendChild(date);
			leftCard.appendChild(img);
			const cardRight = document.createElement("div");
			const title = document.createElement("h3");
			title.textContent = details.title;
			title.className = "title";
			const message = document.createElement("p");
			message.className = "msg";
			message.textContent = details.message;
			const trash = document.createElement("button");
			trash.innerHTML = `<i class="ph ph-trash"></i>`;
			trash.className = "trash-btn";
			trash.id = "trash";
			trash.dataset.message = details.message;
			trash.onclick = function() {
				const message = this.dataset.message;
				deleteDetails(message);
			}
			cardRight.appendChild(title);
			cardRight.appendChild(message);
			card.appendChild(leftCard);
			card.appendChild(cardRight);
			card.appendChild(trash);
			grid.appendChild(card);
		});
	}
	else {
		grid.innerHTML = "";
		other.forEach(details => {
			const card = document.createElement("div");
			card.className = "card";
			const leftCard = document.createElement("div");
			leftCard.className = "card-left";
			const date = document.createElement("span");
			date.textContent = details.date;
			const img = document.createElement("img");
			img.src = details.photo;
			img.class = "photo-placeholder";
			leftCard.appendChild(date);
			leftCard.appendChild(img);
			const cardRight = document.createElement("div");
			const title = document.createElement("h3");
			title.textContent = details.title;
			title.className = "title";
			const message = document.createElement("p");
			message.className = "msg";
			message.textContent = details.message;
			const trash = document.createElement("button");
			trash.innerHTML = `<i class="ph ph-trash"></i>`;
			trash.className = "trash-btn";
			trash.id = "trash";
			trash.dataset.message = details.message;
			trash.onclick = function() {
				const message = this.dataset.message;
				deleteDetails(message);
			}
			cardRight.appendChild(title);
			cardRight.appendChild(message);
			card.appendChild(leftCard);
			card.appendChild(cardRight);
			card.appendChild(trash);
			grid.appendChild(card);
		});
	}
}


function renderHeader() {
	const events = {
		birthday: "Upcoming Birthday",
		wedding: "Upcoming Wedding",
		work: "Upcoming Works",
		other: "Upcoming Events"
	}
	console.log(events.birthday);
	const active = state.activeTab;
	document.getElementById("list-title").textContent = events[active];
}
// --- SAVE FORM ---
const saveForm = document.getElementById('save-form');

saveForm.addEventListener('submit', e => {
	e.preventDefault();

	const fd = new FormData(saveForm);

	fetch("/zohophotos/uploadReminderDetails", {
		method: "POST",
		body: fd
	})
		.then(res => res.text())
		.then(() => {
			closeModal();
			refreshFromBackend();
		})
		.catch(() => console.log("error"));
});

// --- REFRESH AFTER SAVE / DELETE ---
function refreshFromBackend() {
	fetch("/zohophotos/getReminderDetails")
		.then(res => res.json())
		.then(data => {
			state.reminders = data.map(r => ({
				id: r.folderId,
				title: r.title,
				date: r.Date,
				type: r.category,
				message: r.message,
				photo: r.previewUrl,
				time: r.time
			}));
			console.log(state);
			renderUI();
		});
}


// --- DELETE (BACKEND DRIVEN) ---
function deleteItem(id) {
	fetch(`/zohophotos/deleteReminder/${id}`, {
		method: "DELETE"
	})
		.then(() => refreshFromBackend());
}

// --- NAVIGATION ---
document.getElementById('prev-month').onclick = () => {
	state.viewDate.setMonth(state.viewDate.getMonth() - 1);
	renderUI();
};

document.getElementById('next-month').onclick = () => {
	state.viewDate.setMonth(state.viewDate.getMonth() + 1);
	renderUI();
};

// --- MODAL ---
document.getElementById('open-modal').onclick = () => {
	document.getElementById('add-modal').style.display = 'flex';
};

document.getElementById('close-modal').onclick = closeModal;

function closeModal() {
	document.getElementById('add-modal').style.display = 'none';
	saveForm.reset();
	document.getElementById('u-preview').style.display = 'none';
	document.getElementById('u-placeholder').style.display = 'block';
}

// --- CATEGORY SWITCH ---
document.querySelectorAll('.cat-box').forEach(box => {
	box.onclick = () => {
		document.querySelectorAll('.cat-box').forEach(b => b.classList.remove('active'));
		box.classList.add('active');
		state.activeTab = box.dataset.type;
		renderUI();
	};
});

// --- TYPE SELECT ---
document.querySelectorAll('.type-item').forEach(ti => {
	ti.onclick = () => {
		document.querySelectorAll('.type-item').forEach(i => i.classList.remove('active'));
		ti.classList.add('active');
		document.getElementById('f-type').value = ti.dataset.val;
	};
});

// --- IMAGE UPLOAD PREVIEW ---
document.getElementById('drop-zone').onclick = () =>
	document.getElementById('f-input').click();

document.getElementById('f-input').onchange = e => {
	const file = e.target.files[0];
	if (!file) return;

	const reader = new FileReader();
	reader.onload = ev => {
		const preview = document.getElementById('u-preview');
		preview.src = ev.target.result;
		preview.style.display = 'block';
		document.getElementById('u-placeholder').style.display = 'none';
	};
	reader.readAsDataURL(file);
};
function deleteDetails(id) {
	const form = new FormData();
	form.append("message", id)
	console.log(id);
	console.log(form);
	fetch("/zohophotos/deleteReminderDetails", {
		method: "post",
		body: form
	}).then(handleResponse).then(handleData).catch(showError);
	function handleResponse(response) {
		return response;
	}
	function handleData(data) {
		console.log(data);
		refreshFromBackend();
	}
	function showError(err) {
		console.log(err);
	}

}