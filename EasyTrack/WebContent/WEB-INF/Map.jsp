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
    <meta name="description" content="Map">
    <meta name="author" content="samuelpulfer@gmail.com">
    <link rel="icon" href="static/img/underconstruction.ico">
    
    <!-- Bootstrap core CSS -->
    <link href="static/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="static/css/starter-template.css" rel="stylesheet">
    
	<title>EasyTrack Map</title>
</head>
<body>
    <nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
      <a class="navbar-brand" href="#">EasyTrack Map</a>
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

	<div class="align-items-center" id="sketch-holder">
		
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
    <script src="static/js/map/p5.min.js"></script>
    <script src="static/js/map/Coord.js"></script>
    <script src="static/js/map/History.js"></script>
    <script src="static/js/map/sketch.js"></script>
</body>
</html>