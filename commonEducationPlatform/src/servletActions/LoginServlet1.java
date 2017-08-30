package servletActions;

import java.io.IOException;  
import java.io.PrintWriter;  
import javax.servlet.RequestDispatcher;  
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import dbActions.DBConnection;

  
@SuppressWarnings("serial")
public class LoginServlet1 extends HttpServlet {  

public static String username;
public static String password;

public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
{  
  
    response.setContentType("text/html");  
    PrintWriter out = response.getWriter();  
          
    String n=request.getParameter("username");  
    String p=request.getParameter("password"); 
	username=n;
	password=p;
	
	dbActions.DBConnection.connectToDB();
	
	DBConnection dbc=new DBConnection();
	 
	if(dbc.validateUserNameandPassowrd(n,p))
	{
		//String html1 = "<html><body><b>Login Successful</b><br /><br /><p>Welcome "+username+"</p></body></html>";
		//out.println(html1);
		RequestDispatcher rd=request.getRequestDispatcher("/SuccessfulLogin.html");  
        rd.include(request,response);  
		
	}
    else
    {   
        out.print("Sorry username or password error");  
        RequestDispatcher rd=request.getRequestDispatcher("index.html");  
        rd.include(request,response);  
    }  
          
    out.close();  
    }  
}  