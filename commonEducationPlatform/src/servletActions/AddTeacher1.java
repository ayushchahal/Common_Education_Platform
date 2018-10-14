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
import dbActions.DBConnection;

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

			String username=(String) session.getAttribute("user");
			int loginType=new DBConnection().getLoginType(username);
			
			
			if(!sessionID.equals(cookieValue))
			{
				out.print("<html><body>User not correctly authenticated</body></html>");
			}
			else 
			{
				if(loginType == 1)
				{
					RequestDispatcher rd=request.getRequestDispatcher("/ViewCoachingsInDropdown"); 
					rd.include(request,response);
				}
				else if (loginType == 2)
				{
					String tname=request.getParameter("teacherName");
					String cno1=request.getParameter("cno1");
					String tEmail=request.getParameter("email");
					String tStatus="1";
					String[] c = username.split("@");
					String coachingName=c[0];
					if(new Adders().insertTeacherDetails(tname,cno1,tEmail,tStatus,coachingName))
					{
						//response.sendRedirect("/SuccessfulLogin.html");
						RequestDispatcher rd=request.getRequestDispatcher("/DisplayTeacherLoginDetails");  
				        rd.forward(request, response);  
						
					}else
					{
						out.println("<script type=\"text/javascript\">");  
						out.println("alert('Error in adding coaching');");  
						out.println("</script>");
					}
				}
		         
			}
		}catch(Exception e)
		{
			out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();	
		}
		
		 
		//response.sendRedirect("/ViewCoachingsInDropdown");
	}
}