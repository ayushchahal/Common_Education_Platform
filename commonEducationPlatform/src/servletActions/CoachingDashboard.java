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
import dynamicResponses.Misc;

@SuppressWarnings("serial")
public class CoachingDashboard extends HttpServlet 
{  
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();
		
		try {
			HttpSession session = request.getSession(false);
			String sessionID = session.getId();
			Cookie[] cookie = request.getCookies();
			String cookieValue=cookie[0].getValue();
			String username=(String) session.getAttribute("user");
			int loginType=new DBConnection().getLoginType(username);
			if(loginType == 2)
			{
				if(!sessionID.equals(cookieValue))
				{
					out.print("<html><body>User not correctly authenticated</body></html>");
				}
				else
				{
					DBConnection dbc = new DBConnection();
					String coachingID = dbc.getCoachingIDFromLoginName(username);
					String coachingName = dbc.getCoachingNameFromCoachingID(coachingID);
					
					String html="<html>";
					String head="<head>";
					String bootstrapCDN = "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\">";
					String closeHead="</head>";
					String body="<body>";
					
					String header = new Misc().HtmlHeader();
					
					String cardBodyStart = "<div class=\"card-body\">";
					String cardBodyTitle = "<h6 class=\"card-title\">Welcome " + coachingName +"</h6>";
					String br = "<br>";
					
					String addStudent="<a href=\"/DisplayAddStudent\">Add Student</a>";
					String viewStudentDetails="<a href=\"/ViewStudentsServlet\">View Students</a>";
					String addTeacher="<a href=\"/DisplayAddTeacher\">Add Teacher</a>";
					String viewTeacher="<a href=\"/ViewTeachersServlet\">View Teachers</a>";
					String createTest="<a href=\"/DisplayCreateTestPage\">Create a test</a>";
					String viewTests="<a href=\"/DisplayCreatedTests\">View my tests</a>";
					String cardBodyEnd = "</div>";
					String cardEnd = "</div>";
					String closeBody="</body>";
					String closeHtml="</html>";
					
					out.print(html+head+bootstrapCDN+closeHead+body+header+cardBodyStart+cardBodyTitle+br+addStudent+br+viewStudentDetails+br+addTeacher+br+viewTeacher+br+createTest+br+viewTests+cardBodyEnd+cardEnd+closeBody+closeHtml);
				}
			}
			else
			{
				out.print("<html><body>You are not supposed to view that.</body></html>");
			}
			
		}catch(Exception e)
		{
			out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();
		}
	}
}
