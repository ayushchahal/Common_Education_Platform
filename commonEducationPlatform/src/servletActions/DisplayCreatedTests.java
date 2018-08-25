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

@SuppressWarnings("serial")
public class DisplayCreatedTests  extends HttpServlet 
{  
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
			
			if(!sessionID.equals(cookieValue))
			{
				out.print("<html><body>User not correctly authenticated</body></html>");
			}
			else
			{
				TestCreators tc=new TestCreators();
				String[] htmlTable = null;
				int loginID = new DBConnection().getLoginID(username);
				if(loginType == 1)
				{
					htmlTable=tc.getAllTests();
				}
				else if (loginType == 2)
				{
					htmlTable=tc.getAllCoachingTests(""+loginID);
				}
				else if (loginType == 3)
				{
					htmlTable=tc.getAllTeacherTests(""+loginID);
				}
				out.println("<html>"+"<head>"+"<style>");
				out.println("table {\r\n" + 
						"    font-family: arial, sans-serif;\r\n" + 
						"    border-collapse: collapse;\r\n" + 
						"    width: 100%;\r\n" + 
						"}\r\n" + 
						"\r\n" + 
						"td, th {\r\n" + 
						"    border: 1px solid #dddddd;\r\n" + 
						"    text-align: left;\r\n" + 
						"    padding: 8px;\r\n" + 
						"}\r\n" + 
						"\r\n" + 
						"tr:nth-child(even) {\r\n" + 
						"    background-color: #dddddd;\r\n" + 
						"}");
				
				out.println("</style>");
				out.println("<link rel=\"stylesheet\" href=\"dashboard.css\">");
				out.println("</head>");
				out.println("<body>");
				String dashboard = "";
				if (loginType == 1)
					dashboard = "Dashboard";
				else if(loginType == 2)
					dashboard = "CoachingDashboard";
				else if(loginType == 3)
					dashboard = "TeacherDashboard";
				
				out.println("<a href=\"/"+dashboard+"\"><img id=\"Logo\" border=\"0\" src=\"CommonPlatforms.jpg\" width=\"175\" height=\"100\"></a><form action=\"/Logout\" method=\"post\"><input name=\"Submit\" type=\"submit\" value=\"Logout\" id=\"Logout\"></form><br><br><br><br><br><br>");
				out.println("<center>");
				out.println("<table>");
				String tableheaders = "<tr><th>Test ID</th><th>Test Name</th><th>Subject Name</th><th>Test Type</th><th>Number of Questions</th><th>Total Marks</th><th>Total Time</th><th>Test Owner</th><th>Is Completed</th><th>Is Shared</th><th>Public DateTime</th></tr>";
				
				if(loginType == 2 || loginType == 3)
				{
					tableheaders = "<tr><th>Test ID</th><th>Test Name</th><th>Subject Name</th><th>Test Type</th><th>Number of Questions</th><th>Total Marks</th><th>Total Time</th><th>All Questions Completed?</th><th>Is Test Shared?</th><th>Public DateTime</th><th>Action</th></tr>";
				}
				
				out.println(tableheaders);
				for(int j=0;j<htmlTable.length;j++)
				{
					out.println(htmlTable[j]);
				}
				
				out.println("</table>");
				out.println("</center>");
				out.println("</body></html>");
			}
		}catch(Exception e)
		{
			out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();	
		}
	}

}
