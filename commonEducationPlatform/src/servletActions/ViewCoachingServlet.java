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
public class ViewCoachingServlet extends HttpServlet {  

	
	//public static PrintWriter Coaching_out;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");  
		PrintWriter out=response.getWriter();
		try {
			
			Cookie[] cookie = request.getCookies();
			String cookieValue=cookie[0].getValue();
			//System.out.println(cookieValue);
			
			HttpSession session = request.getSession(false);
			//System.out.println(session);
			String sessionID = session.getId();
			
			String username=(String) session.getAttribute("user");
			int loginType=new DBConnection().getLoginType(username);
			if(loginType == 1)
			{
				if(!sessionID.equals(cookieValue))
				{
					out.print("<html><body>User not correctly authenticated</body></html>");
				}
				else {
					DBConnection dbc=new DBConnection();
					dbc.connectToDB();
					String[] htmlTable=dbc.getAllCoachingDetails();
					
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
					out.println("<a href=\"/Dashboard\"><img id=\"Logo\" border=\"0\" src=\"CommonPlatforms.jpg\" width=\"175\" height=\"100\"></a><form action=\"/Logout\" method=\"post\"><input name=\"Submit\" type=\"submit\" value=\"Logout\" id=\"Logout\"><br><br><br><br><br><br>");
					out.println("<center>");
					out.println("<table>");
					String tableheaders = "<tr><th>Coaching ID</th><th>Name</th><th>Address1</th><th>Address2</th><th>City</th><th>Phone Number</th><th>Email</th><th>IsActive</th></tr>";
					out.println(tableheaders);
					for(int j=0;j<htmlTable.length;j++)
					{
						out.println(htmlTable[j]);
					}
					
					out.println("</table>");
					out.println("</center>");
					out.println("</body></html>");
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
