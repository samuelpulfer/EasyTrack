<%@ page import="ch.bfh.btx8101.examples.Employee" %>
<%@ page import="ch.bfh.btx8101.examples.RequestOverview" %>
<%@ page import="ch.bfh.btx8101.examples.RequestOverview.KeyValue" %>
<%@ page import="java.util.ArrayList" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%
	String user;
	Employee emp = (Employee) request.getSession().getAttribute("User");
	user = emp.getUsername();
	
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="This is a Bootstrap starter template">
    <meta name="author" content="samuelpulfer@gmail.com">
    <link rel="icon" href="static/img/underconstruction.ico">
    
    <!-- Bootstrap core CSS -->
    <link href="static/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="static/css/starter-template.css" rel="stylesheet">
    <link href="static/css/order.css" rel="stylesheet">
    <!-- AutoComplete -->
    <link href="static/css/autoComplete/auto-complete.css" rel="stylesheet">
    
	<title>EasyTrack</title>
</head>
<body>
    <nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
      <a class="navbar-brand" href="#">EasyTrack OrderEntry</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item active">
            <a class="nav-link" href=".">Home <span class="sr-only">(current)</span></a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="Order">Order</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="Map">Map</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="app/app-release.apk">Download App</a>
          </li>
        </ul>
        <form class="form-inline my-2 my-lg-0">
          <button class="btn btn-outline-success my-2 my-sm-0" type="button" onClick="location.href = 'Login?logout=true';"><%= user %> abmelden</button>
        </form>
      </div>
    </nav>

    <main role="main" class="container">

		<div id="antrag">
	<div class="container">
	
	
		<div class="row border border-dark">
			
			
			<table class="table borderless">
				<tr>
					<td>
						<h2>Patient</h2>
					</td>
				</tr>
				<tr>
					<td class="col-70p">
						<label for="fallnummer"><b>Fallnummer:</b></label>
						<input type="text" class="form-control" id="fallnummer">
					</td>
				</tr>
			</table>
		</div>
		<br>
		<div class="row border border-dark">
			<table class="table borderless">
				<tr>
					<td>
						<h2>Transport</h2>
					</td>
				</tr>
				<tr>
					<td>
						<label for="von"><b>Von:</b></label>
						<input type="text" class="form-control" id="von" name="von" placeholder="Station..." autocomplete="off">
					</td>
				</tr>
				<tr>
					<td>
						<label for="nach"><b>Nach:</b></label>
						<input type="text" class="form-control" id="nach" name="nach" placeholder="Station..." autocomplete="off">
					</td>
				</tr>
			</table>
			<table class="table borderless">
				<tr>
					<td><b>Zeitpunkt:</b></td>

					<td>
						<label class="radio-inline"><input type="radio" name="zeitpunkt" id="radioNow" onclick="zeitpunktChange();" checked>So Schnell wie m&ouml;glich</label>
					</td>

					
					<td>
						<label class="radio-inline"><input type="radio" name="zeitpunkt" onclick="zeitpunktChange();" id="radioStart">Abholzeitpunkt</label>
					</td>
					<td>
						<label class="radio-inline"><input type="radio" name="zeitpunkt" onclick="zeitpunktChange();" id="radioEnd">Ankunftszeitpunkt</label>
					</td>
				</tr>
			</table>
			<table class="table borderless">
				<tr>
					<td>
						<label for="datum"><b>Datum:</b></label>
						<input type="date" class="form-control" id="datum" name="datum" autocomplete="off" disabled>
					</td>
					<td>
						<label for="zeit"><b>Zeit:</b></label>
						<input type="time" class="form-control" id="zeit" name="zeit" autocomplete="off" disabled>
					</td>
				</tr>
			</table>
			<table class="table borderless">
				<tr>
					<td>
						<b>Transportart: </b>
					</td>
					<td>
						<label class="radio-inline"><input type="radio" name="transportart" id="radioBett" checked>Bett</label>
					</td>
					<td>
						<label class="radio-inline"><input type="radio" name="transportart" id="radioStuhl">Rollstuhl</label>
					</td>
					<td>
						<label class="radio-inline"><input type="radio" name="transportart" id="radioFuss">zu Fuss</label>
					</td>
				</tr>
			</table>
			<table class="table borderless">
				<tr>
					<td>
						<div class="checkbox">
							<label><input type="checkbox" id="isolation" value="">Isolation</label>
						</div>
					</td>
					<td>
						<div class="checkbox">
							<label><input type="checkbox" id="notfall" value="">Notfall</label>
						</div>
					</td>
				</tr>
			</table>
	
			<table class="table borderless">
				<tr>
					<td>
						<label for="nachricht"><b>Nachricht an Transporteur:</b></label>
						<textarea class="form-control" id="nachricht" rows="5"></textarea>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<br>
	<div id="errordiv"></div>
	<button type="button" class="btn btn-success" onclick="sendOrder();">Absenden</button>
	</div>

    </main><!-- /.container -->

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    
    <script src="static/js/jquery-3.3.1.min.js"></script>
    <!--
    <script>window.jQuery || document.write('<script src="/static/js/vendor/jquery-slim.min.js"><\/script>')</script>
    <script src="/static/js/vendor/popper.min.js"></script>
    -->
    <script src="static/js/bootstrap.min.js"></script>
    <script src="static/js/autoComplete/auto-complete.min.js"></script>
    <script src="static/js/order.js"></script>
</body>
</html>