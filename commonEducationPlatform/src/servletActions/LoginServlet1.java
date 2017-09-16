package servletActions;

import java.io.IOException;  
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbActions.DBConnection;

  
@SuppressWarnings("serial")
public class LoginServlet1 extends HttpServlet {  

//public String username;
//public String password;

public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
{  
  
    response.setContentType("text/html");  
    PrintWriter out = response.getWriter();  
          
    String n=request.getParameter("username");  
    String p=request.getParameter("password"); 
	//username=n;
	//password=p;
	
    DBConnection dbc=new DBConnection();
    //dbc.connectToDB();
	
	if(dbc.validateUserNameandPassowrd(n,p))
	{
		HttpSession session = request.getSession();
		session.setAttribute("user", n);
		//session.setMaxInactiveInterval(5);
		
		//String html1 = "<html><body><b>Login Successful</b><br /><br /><p>Welcome "+username+"</p></body></html>";
		//out.println(html1);
		//RequestDispatcher rd=request.getRequestDispatcher("/SuccessfulLogin.html");  
        //rd.include(request,response);
		
		//RequestDispatcher rd=request.getRequestDispatcher("/Dashboard");  
        //rd.include(request,response);
		int loginType=dbc.getLoginType(n);
		//System.out.println(loginType);
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
        out.print("Sorry username or password error");  
        
    }  
    //System.out.println("Dekho");  
    out.close();  
    }

}  