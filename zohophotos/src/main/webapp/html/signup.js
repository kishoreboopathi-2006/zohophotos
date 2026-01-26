localStorage.setItem("forcequit", Date.now());
const regexForEmail = /^[a-z0-9._%+-]+@[a-z]+\.[a-z]{2,}$/;
const regexForPassword = /^[A-Za-z0-9_%+@]{8,}$/;
let messageForEmail = document.getElementById("msg1");
let messageForPassword = document.getElementById("msg2");
let emailInput = document.getElementById("email");
let password = document.getElementById("password");
let emailcheck = false;
let passwordCheck = false;
emailInput.addEventListener("input", function() {
	if (regexForEmail.test(emailInput.value)) {
		emailcheck = true;
		messageForEmail.textContent = "Valid email";
		messageForEmail.style.color = "green";
	} else {
		emailcheck = false;
		messageForEmail.textContent = "wrong";
		messageForEmail.style.color = "red";
	}
});
password.addEventListener("input", function() {
	if (regexForPassword.test(password.value)) {
		passwordCheck = true;
		messageForPassword.textContent = "Valid password";
		messageForPassword.style.color = "green";
	} else {
		passwordCheck = false;
		messageForPassword.textContent = "Enter valid password";
		messageForPassword.style.color = "red";
	}
})

document.getElementById("signupForm").addEventListener("submit", function(e) {
	e.preventDefault();
	if (passwordCheck && emailcheck) {
		let form = document.getElementById("signupForm");
		let formData = new FormData(form);
		fetch("../signUp", {
			method: "post",
			body: formData
		}).then(handleResponse).then(data).catch(error);
		function handleResponse(response) {
			return response.text();
		}
		function data(data) {
			data = data.trim();
			if (data .equals( "success")) {
				window.location.href = 'dashboard.html';
				console.log("if" + data);
			}
			else {
				document.getElementById("information").textContent = "Email already exists";
				console.log("else" + data);
			}
		}
		function error(err) {
			console.log(err);
		}
	}
	else {
		if (passwordCheck) {
			document.getElementById("information").textContent = "Enter valid email";
		}
		else if (emailcheck) {
			document.getElementById("information").textContent = "Enter valid password";
		}
		else {
			document.getElementById("information").textContent = "Enter valid email and password";
		}

	}
})


