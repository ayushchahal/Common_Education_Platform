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
import dynamicResponses.Misc;

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
				
				out.println("<html>"+"<head>");
				String bootstrapCDN = "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\"><script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\" integrity=\"sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN\" crossorigin=\"anonymous\"></script>\r\n" + 
						"<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js\" integrity=\"sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q\" crossorigin=\"anonymous\"></script>\r\n" + 
						"<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\" integrity=\"sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl\" crossorigin=\"anonymous\"></script>";
				out.print(bootstrapCDN);
				
				out.println("</head>");
				
				out.println("<body>");
				
				String header = new Misc().HtmlHeader();
				out.print(header);
				
				String cardBodyStart = "<div class=\"card-body\">";
				out.print(cardBodyStart);
				
				String dashboard = "StudentDashboard";
				String breadcrumbs= "<nav aria-label=\"breadcrumb\">\r\n" + 
						"  <ol class=\"breadcrumb\">\r\n" + 
						"    <li class=\"breadcrumb-item\"><a href=\""+dashboard+"#\">Home</a></li>\r\n" + 
						"    <li class=\"breadcrumb-item active\" aria-current=\"page\">My Tests</li>\r\n" + 
						"  </ol>\r\n" + 
						"</nav>";
				out.print(breadcrumbs);
				
				out.println("<table class=\"table table-striped table-bordered\">");
				
				String tableheaders = "<tr><th>S No.</th><th>Test Name</th><th>Subject Name</th><th>Number of Questions</th><th>Total Marks</th><th>Total Time</th><th>Action</th></tr>";
				TestAndCoachingAssoc taca = new TestAndCoachingAssoc();
				String[] htmlTable = taca.getMyTestDetails(studentID);
				
				int n = taca.numberOfTestsActiveForStudent(studentID);
				
				out.println(tableheaders);
				
				
				for(int j=0;j<n;j++)
				{
					out.println(htmlTable[j]);
				}
				
				out.println("</table>");
				out.println("</div></div></body></html>");
				
			}
		}catch(Exception e)
		{
			out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();	
		}
	}
	
	

}
