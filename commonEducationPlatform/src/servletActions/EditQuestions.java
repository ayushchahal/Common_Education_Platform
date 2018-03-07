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

import dbActions.DBConnection;
import dbActions.TestCreators;

@SuppressWarnings("serial")
public class EditQuestions extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
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
				if(loginType == 1 || loginType == 2)
				{
					String testID = (String) request.getAttribute("testID");
					TestCreators tc = new TestCreators();
					int numberOfQuestions = tc.getNumberOfQuestionsInATest(testID);
					request.setAttribute("numberOfQuestions", numberOfQuestions);
					int n=tc.getNumberOfSavedQuestionsInATest(testID);
					int questionNumber = n+1;
					request.setAttribute("questionNumber", questionNumber);
					request.setAttribute("testID", testID);
					
					RequestDispatcher rd=request.getRequestDispatcher("/CreateQuestions");  
			        rd.forward(request, response);  
				}	
			}
		}catch(Exception e)
		{
			out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();
		}
	}
}

