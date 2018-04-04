package servletActions;

import java.io.IOException;  
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbActions.DBConnection;

  
@SuppressWarnings("serial")
public class LoginServlet1 extends HttpServlet {  

public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
{  
  
    response.setContentType("text/html");  
    PrintWriter out = response.getWriter();  
          
    String n=request.getParameter("username");  
    String p=request.getParameter("password"); 
	
    DBConnection dbc=new DBConnection();
    
    /*
     * Validate user and password from database.
     */
	if(dbc.validateUserNameandPassowrd(n,p))
	{
		HttpSession session = request.getSession();
		session.setAttribute("user", n);
		request.setAttribute("error", "none");
		//Setting Max Idle timeout for session to 60 seconds
		session.setMaxInactiveInterval(60);
		
		int loginType=dbc.getLoginType(n);
		
		if(loginType == 1)
			response.sendRedirect("/Dashboard");
		else if(loginType == 2)
			response.sendRedirect("/CoachingDashboard");
		else if(loginType == 3)
			response.sendRedirect("/TeacherDashboard");
		else if(loginType == 4)
			response.sendRedirect("/StudentDashboard");
	}
    else
    {   
    	request.setAttribute("error", "error");
    	
    	RequestDispatcher rd=request.getRequestDispatcher("/LoginPage");  
        rd.forward(request,response);
       
        
    }  
    out.close();  
    }

}  