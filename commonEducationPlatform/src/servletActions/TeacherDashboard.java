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
public class TeacherDashboard extends HttpServlet {  

	//public static PrintWriter Coaching_out;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
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
			if(loginType == 3)
			{
				if(!sessionID.equals(cookieValue))
				{
					out.print("<html><body>User not correctly authenticated</body></html>");
				}
				else
				{
					String header="<link rel=\"stylesheet\" href=\"dashboard.css\"></head><body><a href=\"/TeacherDashboard\"><img id=\"Logo\" border=\"0\" src=\"CommonPlatforms.jpg\" width=\"175\" height=\"100\"></a><form action=\"/Logout\" method=\"post\">\r\n" + 
							"<input name=\"Submit\" type=\"submit\" value=\"Logout\" id=\"Logout\"><br><br><br><b>Login Successful</b><br><br><br><br>";
					
					
					String html="<html>";
					String head="<head>";
					String closeHead="</head>";
					String body="<body>";
					String viewStudentDetails="<a href=\"/ViewStudentsServlet\">View Students</a><br>";
					String createTest="<a href=\"/DisplayCreateTestPage\">Create a test</a><br>";
					String viewTests="<a href=\"/DisplayCreatedTests\">View my tests</a>";
					String closeBody="</body>";
					String closeHtml="</html>";
					
					out.print(html+head+closeHead+body+header+viewStudentDetails+createTest+viewTests+closeBody+closeHtml);
				}
			}
			else
			{
				out.print("<html><body>You are not supposed to view that.</body></html>");
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
