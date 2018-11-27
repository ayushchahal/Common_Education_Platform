package servletActions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbActions.DBConnection;
import dbActions.TestCreators;
import dynamicResponses.Misc;

@SuppressWarnings("serial")
public class ShareTestPage extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");  
		PrintWriter out= response.getWriter();
		
		try {
			HttpSession session = request.getSession(false);
			String sessionID = session.getId();
			
			Cookie[] cookie = request.getCookies();
			String cookieValue=cookie[0].getValue();
			String username=(String) session.getAttribute("user");
			int loginType=new DBConnection().getLoginType(username);
			
			if(!sessionID.equals(cookieValue))
			{
				out.print("<html><body>User not correctly authenticated</body></html>");
			}
			else
			{
				TestCreators tc = new TestCreators();
				Object testID = request.getAttribute("testID");
				tc.updateIsCompletedToOne(""+testID);
				
				String html="<html>";
				String head="<head>";
				String bootstrapCDN = 
						"<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\">"
						
						+
						
						"<link rel=\"stylesheet\" href=\"https://harvesthq.github.io/chosen/chosen.css\">"
						
						+
						
						"<link rel=\"stylesheet\" href=\"\\stylesheets\\ShareTestPage.css\">";
			
						
						
				
				String javascripts = "<script src=\"/javascript/checkScoreCardSettings.js\"></script>"
						
						+
						
						"<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>"
						
						+
						
						"<script src=\"https://harvesthq.github.io/chosen/chosen.jquery.js\"></script>"
						
						+
						
						"<script>" + 
						"	$(document).ready(function() {\r\n" + 
						"	$(\".chosen-select\").chosen();\r\n" + 
						"	});\r\n" + 
						"</script>";
						
						
				
				String closeHead="</head>";
				String body="<body>";
				
				String header = new Misc().HtmlHeader();
				String dashboard = "/Dashboard";
				if(loginType == 2)
					dashboard = "/CoachingDashboard";
				
				String breadCrumb = "<nav aria-label=\"breadcrumb\">\r\n" + 
						"  <ol class=\"breadcrumb\">\r\n" + 
						"    <li class=\"breadcrumb-item\"><a href=\""+dashboard+"\">Home</a></li>\r\n" + 
						"    <li class=\"breadcrumb-item\"><a href=\"/DisplayCreatedTests\">View Tests</a></li>\r\n" + 
						"    <li class=\"breadcrumb-item active\" aria-current=\"page\">Test configurations</li>\r\n" + 
						"  </ol>\r\n" + 
						"</nav>";
				
				
				String mainHeadingCard = "<br>\r\n" + 
						"<div class=\"card border-secondary\" style=\"margin-left:5%;width:70%;\"> \r\n" + 
						"    <div class=\"card-header bg-dark text-white\">\r\n" + 
						"        <h5>Test configurations</h5>\r\n" + 
						"    </div>\r\n" + 
						"    \r\n" + 
						"    <div class=\"card-body\">";
				
				String form="<form action=\"ShareTest\" method=\"Post\">";
				
				/*
				String shareDate = "<div class=\"input-group input-group-default\">\r\n" + 
						"  				<div class=\"input-group-prepend\">\r\n" + 
						"    				<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Specify date when you want to make this test available for students</span>\r\n" + 
						" 				</div>\r\n" + 
						"  				<input type=\"date\" class=\"form-control\" aria-label=\"Large\" name=\"date\" aria-describedby=\"inputGroup-sizing-sm\" required>\r\n" + 
						"			</div>";
				
				String shareTime = "<div class=\"input-group input-group-default\">\r\n" + 
						"  				<div class=\"input-group-prepend\">\r\n" + 
						"    				<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Specify time when you want to make this test available for students</span>\r\n" + 
						" 				</div>\r\n" + 
						"  				<input type=\"time\" class=\"form-control\" aria-label=\"Large\" name=\"time\" aria-describedby=\"inputGroup-sizing-sm\" required>\r\n" + 
						"			</div>";
				*/
				
				String shareDateAndTimeCard = "<div class=\"card border-primary\">\r\n" + 
						"    <div class=\"card-header\">\r\n" + 
						"        <h5>Test opening date and time</h5>\r\n" + 
						"    </div>\r\n" + 
						"    <div class=\"card-body\">\r\n" + 
						"		<div class=\"input-group input-group-default\">\r\n" + 
						"			<div class=\"input-group-prepend\">\r\n" + 
						"				<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Date:</span>\r\n" + 
						" 			</div>\r\n" + 
						"  			<input type=\"date\" class=\"form-control\" aria-label=\"Large\" name=\"odate\" aria-describedby=\"inputGroup-sizing-sm\" required=\"\">\r\n" + 
						"		</div>\r\n" + 
						"	<br>\r\n" + 
						"		<div class=\"input-group input-group-default\">\r\n" + 
						"			<div class=\"input-group-prepend\">\r\n" + 
						"				<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Time:</span>\r\n" + 
						"			</div>\r\n" + 
						"			<input type=\"time\" class=\"form-control\" aria-label=\"Large\" name=\"otime\" aria-describedby=\"inputGroup-sizing-sm\" required=\"\">\r\n" + 
						"		</div>\r\n" + 
						"	</div>\r\n" + 
						"</div>";
				
				String specifyBatch = "<div class=\"input-group input-group-default\">\r\n" + 
						"				<div class=\"input-group-prepend\">\r\n" + 
						"			    	<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Assign this test to batch(s)</span>\r\n" + 
						"			  	</div>\r\n" + 
						"				<select type=\"text\" class=\"form-control\" aria-label=\"Large\" name=\"batch\" aria-describedby=\"inputGroup-sizing-sm\" required>\r\n" + 
						"					<option value=\"1\" disabled selected>Please select a batch</option>	" +
						"					<option value=\"1\">Dummy value 1</option>	" +
						"					<option value=\"2\">Dummy value 2</option>	" +
						"					<option value=\"3\">Dummy value 3</option>	" +
						"					<option value=\"4\">Dummy value 4</option>	" +
						"				</select>" +					
						"			</div>";
				
				specifyBatch = 
						"<div class=\"input-group input-group-default\">\r\n" + 
								"				<div class=\"input-group-prepend\">\r\n" + 
								"			    	<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Assign this test to batch(s)</span>\r\n" + 
								"			  	</div>\r\n" + 
						"<select class=\"chosen-select form-control\" tabindex=\"8\" multiple=\"\" style=\"width:350px;\" data-placeholder=\"Please select batch(s)\" aria-label=\"Large\" name=\"batch\" aria-describedby=\"inputGroup-sizing-sm\" required>\r\n" + 
						"                <option value=\"\"></option>\r\n" + 
						"                <option>American Black Bear</option>\r\n" + 
						"                <option>Asiatic Black Bear</option>\r\n" + 
						"                <option>Brown Bear</option>\r\n" + 
						"                <option>Giant Panda</option>\r\n" + 
						"                <option>Sloth Bear</option>\r\n" + 
						"                <option>Sun Bear</option>\r\n" + 
						"                <option>Polar Bear</option>\r\n" + 
						"                <option>Spectacled Bear</option>\r\n" + 
						"            </select>"+					
						"			</div>";
				
				
				
				
				String scoreCardSettings = "<div class=\"input-group input-group-default\">\r\n" + 
						"				<div class=\"input-group-prepend\">\r\n" + 
						"			    	<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Score settings:</span>\r\n" + 
						"			  	</div>\r\n" + 
						"				<select type=\"text\" class=\"form-control\" aria-label=\"Large\" name=\"scoreCard\" aria-describedby=\"inputGroup-sizing-sm\" onChange=\"checkScoreCardSettings()\" required>\r\n" + 
						"					<option value=\"1\" disabled selected>Please select a setting</option>	" +
						"					<option value=\"1\">Show score as soon as a student finishes the test</option>	" +
						"					<option value=\"2\">Show score after a specified date</option>	" +
						"				</select>" +					
						"			</div>"
						+ "<br>";
				/*
				String scoreCardDate = "<div id=\"scoreDate\" class=\"input-group input-group-default\" style=\"display:none\">" + 
						"  				<div class=\"input-group-prepend\">\r\n" + 
						"    				<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Specify date when you want to show score to the students</span>\r\n" + 
						" 				</div>\r\n" + 
						"  				<input type=\"date\" class=\"form-control\" aria-label=\"Large\" name=\"scoreDate\" aria-describedby=\"inputGroup-sizing-sm\" required>\r\n" + 
						"			</div>"+
						"<br id=\"br1\" style=\"display:none\">";
				
				String scoreCardTime = "<div id=\"scoreTime\" class=\"input-group input-group-default\" style=\"display:none\">\r\n" + 
						"  				<div class=\"input-group-prepend\">\r\n" + 
						"    				<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Specify time when you want to show score to the students</span>\r\n" + 
						" 				</div>\r\n" + 
						"  				<input type=\"time\" class=\"form-control\" aria-label=\"Large\" name=\"scoreTime\" aria-describedby=\"inputGroup-sizing-sm\" required>\r\n" + 
						"			</div>"+
						"<br id=\"br2\" style=\"display:none\">";
				*/
				String scoreCardDateAndTimeCard = "<div class=\"card border-primary\" id=\"scoreDate\" style=\"display:none\">\r\n" + 
						"    <div class=\"card-header\">\r\n" + 
						"        <h5>Enter date and time for displaying score card:</h5>\r\n" + 
						"    </div>\r\n" + 
						"    <div class=\"card-body\">\r\n" + 
						"		<div class=\"input-group input-group-default\">\r\n" + 
						"			<div class=\"input-group-prepend\">\r\n" + 
						"				<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Date:</span>\r\n" + 
						" 			</div>\r\n" + 
						"  			<input type=\"date\" class=\"form-control\" aria-label=\"Large\" name=\"scoreDate\" aria-describedby=\"inputGroup-sizing-sm\" >\r\n" + 
						"		</div>\r\n" + 
						"	<br>\r\n" + 
						"		<div class=\"input-group input-group-default\">\r\n" + 
						"			<div class=\"input-group-prepend\">\r\n" + 
						"				<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Time:</span>\r\n" + 
						"			</div>\r\n" + 
						"			<input type=\"time\" class=\"form-control\" aria-label=\"Large\" name=\"scoreTime\" aria-describedby=\"inputGroup-sizing-sm\" >\r\n" + 
						"		</div>\r\n" + 
						"	</div>\r\n" + 
						"</div>"+
						"<br id=\"br1\" style=\"display:none\">";
						
						
				String rankSettings = "<div class=\"input-group input-group-default\">\r\n" + 
				"				<div class=\"input-group-prepend\">\r\n" + 
				"			    	<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Rank settings:</span>\r\n" + 
				"			  	</div>\r\n" + 
				"				<select type=\"text\" class=\"form-control\" aria-label=\"Large\" name=\"rankCard\" aria-describedby=\"inputGroup-sizing-sm\" onChange=\"checkRankCardSettings()\" required>\r\n" + 
				"					<option value=\"1\" disabled selected>Please select a setting</option>	" +
				"					<option value=\"1\">Show rank as soon as a student finishes the test</option>	" +
				"					<option value=\"2\">Show rank after a specified date</option>	" +
				"				</select>" +					
				"			</div>"
				+ "<br>";
				
				/*
				String rankCardDate = "<div id=\"rankDate\" class=\"input-group input-group-default\" style=\"display:none\">" + 
						"  				<div class=\"input-group-prepend\">\r\n" + 
						"    				<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Specify date when you want to show rank to the students</span>\r\n" + 
						" 				</div>\r\n" + 
						"  				<input type=\"date\" class=\"form-control\" aria-label=\"Large\" name=\"scoreDate\" aria-describedby=\"inputGroup-sizing-sm\" required>\r\n" + 
						"			</div>"+
						"<br id=\"br3\" style=\"display:none\">";
						*/
				/*
				String rankCardTime = "<div id=\"rankTime\" class=\"input-group input-group-default\" style=\"display:none\">\r\n" + 
						"  				<div class=\"input-group-prepend\">\r\n" + 
						"    				<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Specify time when you want to show rank to the students</span>\r\n" + 
						" 				</div>\r\n" + 
						"  				<input type=\"time\" class=\"form-control\" aria-label=\"Large\" name=\"scoreTime\" aria-describedby=\"inputGroup-sizing-sm\" required>\r\n" + 
						"			</div>"+
						"<br id=\"br4\" style=\"display:none\">";
				*/
				String rankCardDateAndTimeCard = "<div class=\"card border-primary\" id=\"rankDate\" style=\"display:none\">\r\n" + 
						"    <div class=\"card-header\">\r\n" + 
						"        <h5>Enter date and time for displaying rank:</h5>\r\n" + 
						"    </div>\r\n" + 
						"    <div class=\"card-body\">\r\n" + 
						"		<div class=\"input-group input-group-default\">\r\n" + 
						"			<div class=\"input-group-prepend\">\r\n" + 
						"				<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Date:</span>\r\n" + 
						" 			</div>\r\n" + 
						"  			<input type=\"date\" class=\"form-control\" aria-label=\"Large\" name=\"rankDate\" aria-describedby=\"inputGroup-sizing-sm\" >\r\n" + 
						"		</div>\r\n" + 
						"	<br>\r\n" + 
						"		<div class=\"input-group input-group-default\">\r\n" + 
						"			<div class=\"input-group-prepend\">\r\n" + 
						"				<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Time:</span>\r\n" + 
						"			</div>\r\n" + 
						"			<input type=\"time\" class=\"form-control\" aria-label=\"Large\" name=\"rankTime\" aria-describedby=\"inputGroup-sizing-sm\" >\r\n" + 
						"		</div>\r\n" + 
						"	</div>\r\n" + 
						"</div>"+
						"<br id=\"br3\" style=\"display:none\">";
				
				String closeDateAndTimeCard = "<div class=\"card border-primary\">\r\n" + 
						"    <div class=\"card-header\">\r\n" + 
						"        <h5>Test closing date and time</h5>\r\n" + 
						"    </div>\r\n" + 
						"    <div class=\"card-body\">\r\n" + 
						"		<div class=\"input-group input-group-default\">\r\n" + 
						"			<div class=\"input-group-prepend\">\r\n" + 
						"				<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Date:</span>\r\n" + 
						" 			</div>\r\n" + 
						"  			<input type=\"date\" class=\"form-control\" aria-label=\"Large\" name=\"cdate\" aria-describedby=\"inputGroup-sizing-sm\" required=\"\">\r\n" + 
						"		</div>\r\n" + 
						"	<br>\r\n" + 
						"		<div class=\"input-group input-group-default\">\r\n" + 
						"			<div class=\"input-group-prepend\">\r\n" + 
						"				<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Time:</span>\r\n" + 
						"			</div>\r\n" + 
						"			<input type=\"time\" class=\"form-control\" aria-label=\"Large\" name=\"ctime\" aria-describedby=\"inputGroup-sizing-sm\" required=\"\">\r\n" + 
						"		</div>\r\n" + 
						"	</div>\r\n" + 
						"</div>";
				
				
				
				
				String shareButton = "<div class=\"input-group input-group-default\">\r\n" + 
						"  <input type=\"submit\" name=\"Share\" class=\"btn btn-dark\" value=\"Save Details\">\r\n" + 
						"</div>";
				
				
				String hiddenTestID="<input type=\"hidden\" name=\"testID\" value="+testID+">";
				String closeform="</form>";
				String closeMainHeading = "</div>";
				String closebody="</body>";
				String closeHtml="</html>";
				
				out.print(html+head+bootstrapCDN+javascripts+closeHead+body+header+breadCrumb+mainHeadingCard+form+shareDateAndTimeCard+"<br>"+specifyBatch+"<br>"+scoreCardSettings+scoreCardDateAndTimeCard+rankSettings+rankCardDateAndTimeCard+closeDateAndTimeCard+"<br>"+shareButton+hiddenTestID+closeform+closeMainHeading+closebody+closeHtml);
			}
		}catch(Exception e)
		{
			out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();
		}
	}
}
