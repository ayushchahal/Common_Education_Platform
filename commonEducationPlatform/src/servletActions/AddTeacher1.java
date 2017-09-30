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

@SuppressWarnings("serial")
public class AddTeacher1 extends HttpServlet{

	//public static PrintWriter out;
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");  
		PrintWriter out= response.getWriter();
		//RequestDispatcher rd=request.getRequestDispatcher("/ViewCoachingsInDropdown");  
        //rd.include(request,response);
		
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
				RequestDispatcher rd=request.getRequestDispatcher("/ViewCoachingsInDropdown");  
		        rd.include(request,response); 
			}
		}catch(Exception e)
		{
			out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();	
		}
		
		 
		//response.sendRedirect("/ViewCoachingsInDropdown");
	}
}
