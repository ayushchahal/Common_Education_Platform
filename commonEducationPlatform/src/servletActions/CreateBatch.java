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
public class CreateBatch extends HttpServlet {  

	
	//public static PrintWriter Coaching_out;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");  
		PrintWriter out=response.getWriter();
		try {
			
			Cookie[] cookie = request.getCookies();
			String cookieValue=cookie[0].getValue();
			//System.out.println(cookieValue);
			
			HttpSession session = request.getSession(false);
			//System.out.println(session);
			String sessionID = session.getId();
			
			String username=(String) session.getAttribute("user");
			int loginType=new DBConnection().getLoginType(username);
			if(loginType == 2)
			{
				if(!sessionID.equals(cookieValue))
				{
					out.print("<html><body>User not correctly authenticated</body></html>");
				}
				else {
					String batchName = request.getParameter("batchName");
					String coachingID = request.getParameter("coachingID");
					
					TestCreators tc = new TestCreators();
					tc.insertBatchName(batchName, coachingID);
					
					RequestDispatcher rd=request.getRequestDispatcher("/ViewBatches");  
					rd.forward(request, response);  
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
