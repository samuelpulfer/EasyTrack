var stationen = [ 'Station A' ];

new autoComplete({
	selector : 'input[name="von"]',
	minChars : 1,
	source : function(term, suggest) {
		term = term.toLowerCase();
		var choices = stationen;
		var matches = [];
		for (i = 0; i < choices.length; i++)
			if (~choices[i].toLowerCase().indexOf(term))
				matches.push(choices[i]);
		suggest(matches);
	}
});
new autoComplete({
	selector : 'input[name="nach"]',
	minChars : 1,
	source : function(term, suggest) {
		term = term.toLowerCase();
		var choices = stationen;
		var matches = [];
		for (i = 0; i < choices.length; i++)
			if (~choices[i].toLowerCase().indexOf(term))
				matches.push(choices[i]);
		suggest(matches);
	}
});
getStations();
setDate();
zeitpunktChange();

function zeitpunktChange(){
	if (document.getElementById("radioNow").checked) {
		document.getElementById("datum").disabled = true;
		document.getElementById("zeit").disabled = true;
	} else {
		document.getElementById("datum").disabled = false;
		document.getElementById("zeit").disabled = false;
	}
}

function setDate(){
	var now = new Date();
	now.setMinutes(now.getMinutes() + 30);
	date = "" + now.getFullYear();
	date += "-" + zeroPadding(now.getMonth() + 1);
	date += "-" + zeroPadding(now.getDate());
	document.getElementById("datum").value = date;
	time = zeroPadding(now.getHours()) + ":" + zeroPadding(now.getMinutes());
	document.getElementById("zeit").value = time;
}

function zeroPadding(number){
	if (number <= 10){
		number = ("0"+number).slice(-2);
	}
	return number;
}

function getStations() {
	var r = new XMLHttpRequest();
	r.open("GET", "Station", true);
	r.setRequestHeader("Content-Type", "application/json; charset=ISO-8859-1");
	r.onreadystatechange = function(stations) {
		if (r.readyState == 4 && r.status == 200) {
			data = JSON.parse(r.responseText);
			if (data.meta.error == 1) {
				console.log(data.meta.description);
				return;
			} else {
				stationen = data.data.stations
			}
		}
		;
	};
	r.send();
}

function sendOrder() {
	
	var fallnummer = document.getElementById("fallnummer").value;
	var von = document.getElementById("von").value;
	var nach = document.getElementById("nach").value;
	var startzeit = "";
	var endzeit = "";
	
	var zeit = document.getElementById("zeit").value;
	if(zeit.length == 5){
		zeit += ":00";
	}
	if (document.getElementById("radioNow").checked){
		startzeit = "asap";
	} else if(document.getElementById("radioStart").checked){
		startzeit = document.getElementById("datum").value + " " + zeit;
	} else {
		endzeit = document.getElementById("datum").value + " " + zeit;
	}
	var transportart = "";
	if (document.getElementById("radioBett").checked){
		transportart = "bett";
	} else if(document.getElementById("radioStuhl").checked){
		transportart = "rollstuhl";
	} else {
		transportart = "zufuss";
	}
	var isolation = false;
	var notfall = false;
	if(document.getElementById("isolation").checked){
		isolation = true;
	}
	if(document.getElementById("notfall").checked){
		notfall = true;
	}
	var message = document.getElementById("nachricht").value;
	
	// create a json request object
	var params = {
		fallnummer: fallnummer,
		von: von,
		nach: nach,
		startzeit: startzeit,
		endzeit: endzeit,
		transportart: transportart,
		isolation: isolation,
		notfall: notfall,
		message: message
	}
	
	var r = new XMLHttpRequest();
	r.open("POST", "Order", true);
	r.setRequestHeader("Content-Type","application/json; charset=ISO-8859-1");
	r.onreadystatechange = function () {
		if (r.readyState==4 && r.status==200) {
			data = JSON.parse(r.responseText);
			if (data.meta.error == 1) {
				console.log(data.meta.description)
				
				document.getElementById("errordiv").appendChild(getErrorNode(data.meta.description));
			} else {
				alert("Auftrag wurde im System registriert");
				window.location.replace(window.location.pathname);
			}
			console.log(data);
			return
		};
	};
	r.send(JSON.stringify(params));
	
	
}
function getErrorNode(msg) {
	var elem = document.createElement("div");
	elem.classList.add("alert","alert-danger");
	elem.setAttribute("role", "alert");
	elem.innerHTML = msg;
	return elem;
}