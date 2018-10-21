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

import dbActions.Adders;

@SuppressWarnings("serial")
public class AddStudent extends HttpServlet{


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
			else {
				String sname=request.getParameter("sname");
				String c1=request.getParameter("sco");
				String e=request.getParameter("sEmail");
				String isActive=request.getParameter("sStatus");
				String associatedCoaching = request.getParameter("cname");
				
				
				if(new Adders().insertStudentDetails(sname,c1,e,isActive,associatedCoaching))
				{
					//response.sendRedirect("/SuccessfulLogin.html");
					RequestDispatcher rd=request.getRequestDispatcher("/DisplayStudentLoginDetails");  
			        rd.forward(request, response);  
					
				}else
				{
					out.println("<script type=\"text/javascript\">");  
					out.println("alert('Error in adding coaching');");  
					out.println("</script>");
				}
			}
		}catch(Exception e)
	    {
	    	out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();
	    }
		
		
		
	}

}
