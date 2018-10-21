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

//import dbActions.DBConnection;

@SuppressWarnings("serial")
public class ViewEditOrShareTest extends HttpServlet 
{  
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
			//int loginType=new DBConnection().getLoginType(username);
			
			if(!sessionID.equals(cookieValue))
			{
				out.print("<html><body>User not correctly authenticated</body></html>");
			}
			else
			{
				String buttonAction = request.getParameter("CompleteOrShare");
				String testID = request.getParameter("testID");
				request.setAttribute("testID", testID);
				if(buttonAction.equalsIgnoreCase("View Test"))
				{
					RequestDispatcher rd=request.getRequestDispatcher("/ViewTestDetails");  
			        rd.forward(request, response);  
				}
				else if(buttonAction.equalsIgnoreCase("Share Test"))
				{
					RequestDispatcher rd=request.getRequestDispatcher("/ShareTestPage");  
			        rd.forward(request, response);  
				}
				else if(buttonAction.equalsIgnoreCase("Complete Test"))
				{
					RequestDispatcher rd=request.getRequestDispatcher("/EditQuestions");  
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
