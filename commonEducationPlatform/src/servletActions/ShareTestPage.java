package servletActions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbActions.TestCreators;

@SuppressWarnings("serial")
public class ShareTestPage extends HttpServlet{

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
				TestCreators tc = new TestCreators();
				Object testID = request.getAttribute("testID");
				tc.updateIsCompletedToOne(""+testID);
				String html="<html>";
				String head="<head></head>";
				String body="<body>";
				String heading="<h1>Share test with students</h1>";
				String form="<form action=\"ShareTest\" method=\"Post\">";
				String h2="<h2>Step-1: Specify date when you want to make test public to students</h2>";
				String datepicker="<input type=\"text\" name=\"date\" placeholder=\"Enter date in format yyyy/mm/dd\">";
				String h3="<h2>Step-2: Share test</h2>";
				String button="<input type=\"submit\" class=\"bouton-contact\" name=\"Share\" value=\"Share this Test\">";
				String hiddenTestID="<input type=\"hidden\" name=\"testID\" value="+testID+">";
				String closeform="</form>";
				String closebody="</body>";
				String closeHtml="</html>";
				
				out.print(html+head+body+heading+form+h2+datepicker+h3+button+hiddenTestID+closeform+closebody+closeHtml);
			}
		}catch(Exception e)
		{
			out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();
		}
	}
}
