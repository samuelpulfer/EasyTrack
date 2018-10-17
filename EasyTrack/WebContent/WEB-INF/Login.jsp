<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%
	String servletPath = (String) request.getAttribute("javax.servlet.forward.servlet_path");
	if(servletPath.startsWith("/"))
		servletPath = servletPath.substring(1);
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Login Page">
    <meta name="author" content="samuelpulfer@gmail.com">
    <link rel="icon" href="static/img/underconstruction.ico">
	<title>Login</title>
	<!-- Bootstrap core CSS -->
    <link href="static/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="static/css/login.css" rel="stylesheet">
</head>
<body class="text-center">
	<div class="invisible">
		<div id="path"><%= servletPath %></div>
	</div>
	
	<form class="form-signin">
		<%
			String customLoginMessage = (String) session.getAttribute("customLoginMessage");
			if(customLoginMessage != null) { %>
				<div id="messageTop" class="alert alert-danger" role="alert"><%= customLoginMessage %></div>

			<% }  
		%>
		
		<img class="mb-4" src="static/img/underconstruction.png" alt="" width="72" height="72">
		<h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
		
		<input type="text" id="username" class="form-control" placeholder="Username" required autofocus>
		
		<input type="password" id="password" class="form-control" placeholder="Password" required>
		<button type="button" id="sendbtn" class="btn btn-lg btn-primary btn-block" onClick="send_form(this)" >Sign in</button>
		<br>
		<div id="message"></div>
		<p class="mt-5 mb-3 text-muted">&copy; 2018 Samuel Pulfer</p>
	</form>
	<script src="static/js/login.js"></script>

</body>
</html>