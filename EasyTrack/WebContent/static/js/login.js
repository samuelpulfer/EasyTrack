pwinput = document.getElementById("password");
pwinput.addEventListener("keyup", function(event) {
  // Cancel the default action, if needed
  event.preventDefault();
  // Number 13 is the "Enter" key on the keyboard
  if (event.keyCode === 13) {
    // Trigger the button element with a click
    document.getElementById("sendbtn").click();
  }
});

userinput = document.getElementById("username");
userinput.addEventListener("keyup", function(event) {
  // Cancel the default action, if needed
  event.preventDefault();
  // Number 13 is the "Enter" key on the keyboard
  if (event.keyCode === 13) {
    // Trigger the button element with a click
    document.getElementById("sendbtn").click();
  }
});

function send_form(obj) {
	// clear messages
	clearErrorNode();
	
	// get data from the form
	var username = document.getElementById("username").value;
	var password = document.getElementById("password").value;
	
	// user input sanity check
	if (username == "") {
		document.getElementById("message").appendChild(getErrorNode("Please enter a username"));
	}
	
	if (password == "") {
		document.getElementById("message").appendChild(getErrorNode("Please enter a password"));
	}
	
	if (document.getElementById("message").children.length != 0) {
		return false;
	}
	
	// create a json request object
	var params = {
		username: username,
		password: password
	}
	
	var r = new XMLHttpRequest();
	r.open("POST", "Login", true);
	r.setRequestHeader("Content-Type","application/json; charset=ISO-8859-1");
	r.onreadystatechange = function () {
		switch (r.readyState) {
			case 0:
				console.log('request not initialized. Status: ' + r.statusText)
				break;
			case 1:
				console.log('server connection established. Status: ' + r.statusText)
				break;
			case 2:
				console.log('request received. Status: ' + r.statusText)
				break;
			case 3:
				console.log('processing request. Status: ' + r.statusText)
				break;
			case 4:
				console.log('request finished and response is ready. Status: ' + r.statusText)
				break;
		}
		if (r.readyState==4 && r.status==200) {
			console.log("hallo");
			data = JSON.parse(r.responseText);
			if (data.meta.error == 1) {
				clearErrorNode();
				document.getElementById("password").value = "";
				document.getElementById("message").appendChild(getErrorNode(data.meta.description));
			} else {
				if (window.location.pathname.split("/")[window.location.pathname.split("/").length -1] == "Login") {
					window.location.replace(".");
				} else {
					window.location.replace(window.location.pathname);
				}
			}
			console.log(data);
			return
		};
	};
	r.send(JSON.stringify(params));
}

function test(obj) {
	alert("hallo");
}

function getErrorNode(msg) {
	var elem = document.createElement("div");
	elem.classList.add("alert","alert-danger");
	elem.setAttribute("role", "alert");
	elem.innerHTML = msg;
	return elem;
}
function clearErrorNode() {
	var message = document.getElementById("message");
	while (message.firstChild) {
    message.removeChild(message.firstChild);
	}
	return;
}