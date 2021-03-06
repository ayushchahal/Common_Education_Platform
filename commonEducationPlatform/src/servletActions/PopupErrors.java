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
public class PopupErrors extends HttpServlet{


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
				String testID = request.getParameter("testID");
				System.out.println("45435434234ssdfsd  + "+ testID);
				String studentID = request.getParameter("studentID");
				String questionID = request.getParameter("questionID");
				
				String timeLimitTypeID = tc.getTimeLimitType(testID);
				
				response.setContentType("text/plain");  
				response.setCharacterEncoding("UTF-8");
				
				if(timeLimitTypeID.equals("1"))
				{
					if(tc.isQuestionAlreadyAnswered(studentID, questionID))
					{
						System.out.println("FRAUD ATTEMPT");
						out.write("Fraud");
					}
				}
			}
		}catch(Exception e)
	    {
	    	out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();
	    }
	}
}
