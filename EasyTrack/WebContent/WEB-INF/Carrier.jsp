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
    <meta name="description" content="Carrier">
    <meta name="author" content="samuelpulfer@gmail.com">
    <link rel="icon" href="static/img/underconstruction.ico">
    
    <!-- Bootstrap core CSS -->
    <link href="static/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="static/css/starter-template.css" rel="stylesheet">
    <link href="static/css/carrier.css" rel="stylesheet">
    
	<title>EasyTrack</title>
</head>
<body>
    <nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
      <a class="navbar-brand" href="#">EasyTrack Carrier</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item active">
            <a class="nav-link" href=".">Home <span class="sr-only">(current)</span></a>
          </li>
          
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="http://example.com" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Dropdown</a>
            <div class="dropdown-menu" aria-labelledby="dropdown01">
              <a class="dropdown-item" href="#">Action</a>
              <a class="dropdown-item" href="#">Another action</a>
              <a class="dropdown-item" href="#">Something else here</a>
            </div>
          </li>
        </ul>
        <form class="form-inline my-2 my-lg-0">
          <button class="btn btn-outline-success my-2 my-sm-0" type="button" onClick="location.href = 'Login?logout=true';"><%= user %> abmelden</button>
        </form>
      </div>
    </nav>

    <main role="main" class="container">


	<div class="container">
	
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
    <script src="static/js/carrier.js"></script>
</body>
</html>