package servletActions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dynamicResponses.Misc;

@SuppressWarnings("serial")
public class ViewSolutions extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");  
		PrintWriter out= response.getWriter();
		
		try {
			HttpSession session = request.getSession(false);
			String sessionID = session.getId();
			
			Cookie[] cookie = request.getCookies();
			String cookieValue=cookie[0].getValue();

			if(!sessionID.equals(cookieValue))
			{
				out.print("<html><body>User not correctly authenticated</body></html>");
			}
			else 
			{
				out.print("<html>");
				Misc misc = new Misc();
				String companyHeader = misc.HtmlHeader();
				String navigationBar = "<div class=\"card-body\"><nav aria-label=\"breadcrumb\">" + 
						"  <ol class=\"breadcrumb\">" + 
						"    <li class=\"breadcrumb-item\"><a href=\"StudentDashboard#\">Home</a></li>" + 
						"    <li class=\"breadcrumb-item\"><a href=\"/MyTests\">My Tests</a></li>" + 
						"    <li class=\"breadcrumb-item\" active>Score Card</li>" +
						"    <li class=\"breadcrumb-item active\" aria-current=\"page\">Solutions</li>" +
						"  </ol>" + 
						"</nav>";
				
				out.println("<html><head><title>Test Report</title>");
				out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\">");
				
				out.println("</head>");
				
				out.println("<body>");
				out.println(companyHeader);
				out.println("</div>");
				out.println(navigationBar);
				out.print("<p>Solutions are not yet avaible. Please come back again later.</p>");
				out.println("</body>");
				out.println("</html>");
				
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
