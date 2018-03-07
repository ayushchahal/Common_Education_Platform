package servletActions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbActions.TestCreators;

@SuppressWarnings("serial")
public class TakeTest extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");  
		PrintWriter out= response.getWriter();
		
		try {
			HttpSession session = request.getSession(false);
			String sessionID = session.getId();
			
			Cookie[] cookie = request.getCookies();
			String cookieValue=cookie[0].getValue();

			//String username=(String) session.getAttribute("user");
			//int loginID=new DBConnection().getLoginID(username);
			if(!sessionID.equals(cookieValue))
			{
				out.print("<html><body>User not correctly authenticated</body></html>");
			}
			else 
			{
				String testID = request.getParameter("testID");
				String studentID = request.getParameter("studentID");
				request.setAttribute("studentID", studentID);
				request.setAttribute("testID", testID);
				TestCreators tc = new TestCreators();
				tc.createStudentTestAssociation(studentID, testID, 0);
				RequestDispatcher rd=request.getRequestDispatcher("/DisplayQuestions");
				rd.forward(request,response);
			
			}
		}catch(Exception e)
		{
			out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();	
		}
	}
}
