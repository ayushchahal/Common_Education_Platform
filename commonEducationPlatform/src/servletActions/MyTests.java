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
import dbActions.TestAndCoachingAssoc;

@SuppressWarnings("serial")
public class MyTests extends HttpServlet{

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
			//int loginID=new DBConnection().getLoginID(username);
			if(!sessionID.equals(cookieValue))
			{
				out.print("<html><body>User not correctly authenticated</body></html>");
			}
			else 
			{
				DBConnection dbc = new DBConnection();
				String studentID = dbc.getStudentIDFromLoginDetails(username);
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
				String dashboard = "StudentDashboard";
				out.println("<a href=\"/"+dashboard+"\"><img id=\"Logo\" border=\"0\" src=\"CommonPlatforms.jpg\" width=\"175\" height=\"100\"></a><form action=\"/Logout\" method=\"post\"><input name=\"Submit\" type=\"submit\" value=\"Logout\" id=\"Logout\"></form><br><br><br><br><br><br>");
				out.println("<table>");
				
				
				String tableheaders = "<tr><th>S No.</th><th>Test Name</th><th>Subject Name</th><th>Number of Questions</th><th>Total Marks</th><th>Total Time</th><th>Action</th></tr>";
				TestAndCoachingAssoc taca = new TestAndCoachingAssoc();
				String[] htmlTable = taca.getMyTestDetails(studentID);
				out.println(tableheaders);
				for(int j=0;j<htmlTable.length;j++)
				{
					out.println(htmlTable[j]);
				}
				
				out.println("</table>");
				out.println("</body></html>");
				
			}
		}catch(Exception e)
		{
			out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();	
		}
	}
	
	

}
