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
public class ViewTeachersServlet extends HttpServlet {  

	//public static PrintWriter Coaching_out;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");  
		PrintWriter out= response.getWriter();
		try {
			Cookie[] cookie = request.getCookies();
			String cookieValue=cookie[0].getValue();
			//System.out.println(cookieValue);
			
			HttpSession session = request.getSession(false);
			//System.out.println(session);
			String sessionID = session.getId();
			
			String username=(String) session.getAttribute("user");
			int loginType=new DBConnection().getLoginType(username);
			if(loginType == 1 || loginType == 2)
			{
				if(!sessionID.equals(cookieValue))
				{
					out.print("<html><body>User not correctly authenticated</body></html>");
				}
				else {
					DBConnection dbc=new DBConnection();
					String[] htmlTable=null;
					if(loginType == 1)
					{
						htmlTable=dbc.getAllTeachersDetails();
					}	
					else if (loginType == 2)
					{
						String[] c = username.split("@");
						String coachingName=c[0];
						htmlTable=dbc.getSelectedCoachingTeacherDetails(coachingName);
					}
					
					out.println("<html>"+"<head>");
					String bootstrapCDN = "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\"><script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\" integrity=\"sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN\" crossorigin=\"anonymous\"></script>\r\n" + 
							"<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js\" integrity=\"sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q\" crossorigin=\"anonymous\"></script>\r\n" + 
							"<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\" integrity=\"sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl\" crossorigin=\"anonymous\"></script>";
					out.print(bootstrapCDN);
					
					out.println("</head>");
					
					out.println("<body>");
					
					String dashboard = "Dashboard";
					if(loginType == 2)
						dashboard = "CoachingDashboard";
					else if(loginType == 3)
						dashboard = "TeacherDashboard";
					
					String breadcrumbs= "<nav aria-label=\"breadcrumb\">\r\n" + 
							"  <ol class=\"breadcrumb\">\r\n" + 
							"    <li class=\"breadcrumb-item\"><a href=\""+dashboard+"#\">Home</a></li>\r\n" + 
							"    <li class=\"breadcrumb-item active\" aria-current=\"page\">View Teachers</li>\r\n" + 
							"  </ol>\r\n" + 
							"</nav>";
					
					String header = new Misc().HtmlHeader();
					
					out.print(header);
					
					String cardBodyStart = "<div class=\"card-body\">";
					out.print(cardBodyStart);
					out.print(breadcrumbs);
					out.println("<table class=\"table table-striped table-bordered\">");
					String tableheaders = "";
					if(loginType == 1)
					{
						tableheaders = "<tr><th>Teacher ID</th><th>Name</th><th>Contact No.</th><th>Email</th><th>IsActive</th><th>CoachingName</th></tr>";
					}
					else if (loginType == 2)
					{
						tableheaders = "<tr><th>Teacher ID</th><th>Name</th><th>Contact No.</th><th>Email</th><th>Actions</th></tr>";
					}
					
					out.println(tableheaders);
					for(int j=0;j<htmlTable.length;j++)
					{
						out.println(htmlTable[j]);
					}
					
					out.println("</table>");
					out.println("</div></div></body></html>");
					
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