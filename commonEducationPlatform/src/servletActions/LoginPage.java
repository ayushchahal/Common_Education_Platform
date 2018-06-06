package servletActions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class LoginPage extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{  
	  
	    response.setContentType("text/html");  
	    PrintWriter out = response.getWriter(); 
	    
	    String displayStyle = "";
	    
	  
	    displayStyle = "<span id=\"errLoginid\" style=\"display: none; color:red;\">* Invalid username or password</span>";
	    
	    displayLoginPage(displayStyle, out);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{  
	  
	    response.setContentType("text/html");  
	    PrintWriter out = response.getWriter(); 
	    
	    String displayStyle = "";
	    
	    String error = (String) request.getAttribute("error");
	    
	    if(error == null)
	    	error = "none";
	    
	    if(error.equalsIgnoreCase("error"))
	    	displayStyle = "<span id=\"errLoginid\" style=\"display: block; color:red;\">* Invalid username or password</span>";
	    else
	    	displayStyle = "<span id=\"errLoginid\" style=\"display: none; color:red;\">* Invalid username or password</span>";
	    
	    displayLoginPage(displayStyle, out);
	}
	
	void displayLoginPage(String displayStyle,PrintWriter out)
	{
		String html = "<!doctype html>\r\n" + 
	    		"<html>\r\n" + 
	    		"<head>\r\n" + 
	    		"	<title> LOGIN </title>\r\n" + 
	    		"	<meta charset=\"utf-8\">\r\n" + 
	    		"	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
	    		"	<link rel=\"stylesheet\" href=\"../stylesheets/style.css\">\r\n" + 
	    		"	<script src=\"http://code.jquery.com/jquery-latest.min.js\"></script> \r\n" + 
	    		"\r\n" + 
	    		"	<!-- Compiled and minified CSS -->\r\n" + 
	    		"	<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css\">\r\n" + 
	    		"	<!--Icons-->\r\n" + 
	    		"	<link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\">\r\n" + 
	    		"	<!-- Compiled and minified JavaScript -->\r\n" + 
	    		"	<script src=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js\"></script>\r\n" + 
	    		"	<!--Fonts-->\r\n" + 
	    		"	<link href=\"https://fonts.googleapis.com/css?family=Pacifico\" rel=\"stylesheet\">\r\n" + 
	    		"	<link href=\"https://fonts.googleapis.com/css?family=Bree+Serif\" rel=\"stylesheet\">\r\n" + 
	    		"	<script src=\"../javascript/loginScript.js\"></script>\r\n" + 
	    		"</head>\r\n" + 
	    		"<body>\r\n" + 
	    		"	<nav class=\"nav-extended\">\r\n" + 
	    		"		<div class=\"nav-wrapper logo-nav-optimize\">\r\n" + 
	    		"			<img src=\"../images/logo.png\" class=\"responsive-img logo-img\" />\r\n" + 
	    		"			<div class=\"right header-text\">We Believe in Redefining Education</div>\r\n" + 
	    		"		</div>\r\n" + 
	    		"		<div class=\"nav-wrapper\">\r\n" + 
	    		"			<a href=\"#\" data-activates=\"mobile-demo\" class=\"button-collapse\"><i class=\"material-icons\">menu</i></a>\r\n" + 
	    		"			<ul class=\"tabs tabs-transparent right hide-on-med-and-down\">\r\n" + 
	    		"				<li class=\"tab\"><a href=\"#home\">Home</a></li>\r\n" + 
	    		"				<li class=\"tab\"><a class=\"active\" href=\"#aboutCoaching\">About Coaching</a></li>\r\n" + 
	    		"				<li class=\"tab\"><a href=\"#aboutPortal\">About Test Portal</a></li>\r\n" + 
	    		"				<li class=\"tab\"><a href=\"#contact\">Contact Us</a></li>\r\n" + 
	    		"			</ul>\r\n" + 
	    		"			<ul class=\"side-nav\" id=\"mobile-demo\">\r\n" + 
	    		"				<li class=\"tab\"><a href=\"#home\">Home</a></li>\r\n" + 
	    		"				<li class=\"tab\"><a class=\"active\" href=\"#aboutCoaching\">About Coaching</a></li>\r\n" + 
	    		"				<li class=\"tab\"><a href=\"#aboutPortal\">About Test Portal</a></li>\r\n" + 
	    		"				<li class=\"tab\"><a href=\"#contact\">Contact Us</a></li>\r\n" + 
	    		"			</ul>\r\n" + 
	    		"		</div>\r\n" + 
	    		"	</nav>\r\n" + 
	    		"	<div class=\"row\">\r\n" + 
	    		"\r\n" + 
	    		"		<div class=\"col s4\" style=\"text-align: justify;\">\r\n" + 
	    		"			<h4>Toppers</h4>\r\n" + 
	    		"			Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\r\n" + 
	    		"		</div>\r\n" + 
	    		"		<div class=\"col s4\" style=\"text-align: justify;\">\r\n" + 
	    		"			<h4>Upcoming tests</h4>\r\n" + 
	    		"			Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\r\n" + 
	    		"		</div>\r\n" + 
	    		"		<div class=\"col s4\">\r\n" + 
	    		"			<div>\r\n" + 
	    		"				<div class=\"row\">\r\n" + 
	    		"					<form id=\"loginForm\" action=\"/LoginServlet1\" class=\"col s12\" method=\"POST\" onsubmit=\"return validateForm()\">\r\n" + 
	    		"						<div class=\"card pink lighten-5\">\r\n" + 
	    		"							<div class=\"card-content white-text\">\r\n" + 
	    		"								<span class=\"card-title black-text center portal-text\">Login</span>\r\n" + 
	    		"								<div class=\"row\">\r\n" + 
	    		"									<div class=\"input-field col s12\">\r\n" + 
	    		"										<input id=\"loginid\" type=\"text\" class=\"validate black-text\" name=\"username\" onfocusout=\"validateLoginid();\">\r\n" + 
	    		"										<label for=\"loginid\">Login ID</label>\r\n" + 
	    		"										<span id=\"errLoginid\" style=\"display: none; color:red;\">* Required</span>\r\n" + 
	    		"									</div>\r\n" + 
	    		"								</div>\r\n" + 
	    		"								<div class=\"row\">\r\n" + 
	    		"									<div class=\"input-field col s12\">\r\n" + 
	    		"										<input id=\"password\" type=\"password\" class=\"validate black-text\" name=\"password\" onfocusout=\"validatePassword();\">\r\n" + 
	    		"										<label for=\"password\">Password</label>\r\n" + 
	    		"										<span id=\"errPassword\" style=\"display: none; color:red;\">* Required</span>\r\n" + 
	    		"									</div>\r\n" + 
	    		"								</div>\r\n" + displayStyle+
	    		
	    		"							</div>\r\n" + 
	    		"							<div class=\"card-action\">\r\n" + 
	    		"								<button id=\"btnSubmit\" class=\"waves-effect waves-light btn red center\" style=\"width:100%;\">Login</button>\r\n" + 
	    		"								<!-- <input type=\"submit\" id=\"btnSubmit\" class=\"waves-effect waves-light btn red center\" style=\"width: 100%;\" text=\"Login\" /> -->\r\n" + 
	    		"							</div>\r\n" + 
	    		"						</div>\r\n" + 
	    		"					</form>\r\n" + 
	    		"				</div>\r\n" + 
	    		"			</div>\r\n" + 
	    		"		</div>\r\n" + 
	    		"		<div id=\"test1\" class=\"col s12\">Test 1</div>\r\n" + 
	    		"		<div id=\"test2\" class=\"col s12\">Test 2</div>\r\n" + 
	    		"		<div id=\"test3\" class=\"col s12\">Test 3</div>\r\n" + 
	    		"		<div id=\"test4\" class=\"col s12\">Test 4</div>\r\n" + 
	    		"\r\n" + 
	    		"<!-- 		<script>\r\n" + 
	    		"		$( document ).ready(function(){\r\n" + 
	    		"			$('.button-collapse').sideNav({\r\n" + 
	    		"				closeOnClick: true\r\n" + 
	    		"			}\r\n" + 
	    		"			);\r\n" + 
	    		"		});\r\n" + 
	    		"	</script>\r\n" + 
	    		"	 -->	\r\n" + 
	    		"	</body>	";
		
		out.print(html);
	}
	

}
