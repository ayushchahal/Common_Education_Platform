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
public class ViewStudentsServlet extends HttpServlet {  

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
			if(loginType == 1 || loginType == 2 || loginType == 3)
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
						htmlTable=dbc.getAllStudentDetails();
					}	
					else if (loginType == 2)
					{
						String[] c = username.split("@");
						String coachingName=c[0];
						htmlTable=dbc.getSelectedCoachingStudentDetails(coachingName);
					}
					else if (loginType == 3)
					{
						String[] teacher = username.split("@");
						//String teacherName = teacher[0];
						String coachingName = teacher[1];
						//String coachingID = dbc.getCoachingIDFromTeacherName(teacherName);
						//htmlTable = dbc.getSelectedTeacherStudentDetails(coachingID);
						htmlTable=dbc.getSelectedCoachingStudentDetails(coachingName);
					}

					out.println("<html>"+"<head>");
					String bootstrapCDN = "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\">";
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
							"    <li class=\"breadcrumb-item active\" aria-current=\"page\">View Students</li>\r\n" + 
							"  </ol>\r\n" + 
							"</nav>";
					
					String cardStart = "<div class=\"card border-0\">";
					String cardHeader = "<div class=\"card-header\"><h5>Test Plant<form action=\"/Logout\" method=\"post\"><input name=\"Submit\" type=\"submit\" value=\"Logout\" id=\"Logout\" class=\"btn btn-secondary float-right\"></form></h5></div>";
					
					out.print(cardStart+cardHeader);
					
					String cardBodyStart = "<div class=\"card-body\">";
					out.print(cardBodyStart);
					out.print(breadcrumbs);
					out.println("<table class=\"table table-striped table-bordered\">");
					String tableheaders = "<tr><th>Student ID</th><th>Name</th><th>Contact No.</th><th>Email</th><th>Status</th><th>CoachingName</th><th>Details</th></tr>";
					out.println(tableheaders);
					for(int j=0;j<htmlTable.length;j++)
					{
						out.println(htmlTable[j]);
					}
					
					out.println("</table>");
					//out.println("</form>");
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
