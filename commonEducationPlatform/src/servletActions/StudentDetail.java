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
public class StudentDetail extends HttpServlet {  

	//public static PrintWriter Coaching_out;
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
			if(loginType == 1 || loginType == 2 || loginType == 3)
			{
				if(!sessionID.equals(cookieValue))
				{
					out.print("<html><body>User not correctly authenticated</body></html>");
				}
				else
				{
					
					String studentID = request.getParameter("sid");
					out.print(studentID);
					//Student details and performance in tests will be displayed here
					
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
