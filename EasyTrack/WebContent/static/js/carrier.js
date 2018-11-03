
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
