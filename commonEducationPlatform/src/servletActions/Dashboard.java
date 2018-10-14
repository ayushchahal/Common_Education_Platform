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


@SuppressWarnings("serial")
public class Dashboard extends HttpServlet 
{  
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();
		
		try {
			HttpSession session = request.getSession(false);
			String sessionID = session.getId();
			//System.out.println(sessionID);
			Cookie[] cookie = request.getCookies();
			String cookieValue=cookie[0].getValue();
			//System.out.println(cookieValue);
			String username=(String) session.getAttribute("user");
			int loginType=new DBConnection().getLoginType(username);
			if(loginType == 1)
			{
				if(!sessionID.equals(cookieValue))
				{
					out.print("<html><body>User not correctly authenticated</body></html>");
				}
				else {
					String html1="<html><head><link rel=\"stylesheet\" href=\"dashboard.css\"></head><body><a href=\"/Dashboard\"><img id=\"Logo\" border=\"0\" src=\"CommonPlatforms.jpg\" width=\"175\" height=\"100\"></a><form action=\"/Logout\" method=\"post\">\r\n" + 
							"<input name=\"Submit\" type=\"submit\" value=\"Logout\" id=\"Logout\"><br><br><br><b>Login Successful</b><br><br><br><br>";
					
					String html2="<br><br><br><a href=\"/ViewCoachingServlet\">View Coaching</a><br><br><a href=\"/ViewTeachersServlet\">View Teachers</a><br><br><a href=\"/ViewStudentsServlet\">View Students</a><br><br>";
					String html3="<a href=\"/DisplayAddCoaching\">Add Coaching</a><br><br><a href=\"/DisplayAddTeacher\">Add Teacher</a><br><br><a href=\"/DisplayAddStudent\">Add Student</a>";
					String html4="<br><br><a href=\"/DisplayCreateTestPage\">Create a test</a>";
					String html5="<br><br><a href=\"/DisplayCreatedTests\">View tests</a>";
					String html6="<br><br><a href=\"/MyWorkspace\">MyWorkspace</a>";
					//String userID="<input type=\"hidden\" id=\"userID\" name=\"userID\" style=\"display:none;\" value=\""+username+"\">";
					String closeHtml="</body></html>";
					out.print(html1+html2+html3+html4+html5+html6+closeHtml);
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
