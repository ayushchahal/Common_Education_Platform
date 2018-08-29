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
public class DeleteTeacher extends HttpServlet {  

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html"); 
		PrintWriter out;
		out= response.getWriter();
		
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
				String action = request.getParameter("editOrDeleteDetails");
				String tid = request.getParameter("tid");
				if(action.equalsIgnoreCase("Delete"))
				{
					DBConnection dbc=new DBConnection();
					dbc.deleteTeacher(tid);
					response.sendRedirect("/ViewTeachersServlet");
				}
				else if(action.equalsIgnoreCase("Edit"))
				{
					out.print("Modal popup to be implemented here");
				}
			}
		}catch(Exception e)
		{
			out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();	
		}
	
}
}
