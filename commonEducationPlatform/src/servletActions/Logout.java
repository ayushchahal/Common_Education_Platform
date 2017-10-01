package servletActions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class Logout extends HttpServlet {  

	//public String username;
	//public String password;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{  
	  
	    response.setContentType("text/html");  
	    HttpSession session = request.getSession(false);
	    session.invalidate();
	    response.sendRedirect("/LoginPage.html");
	}
}
