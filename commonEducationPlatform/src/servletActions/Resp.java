package servletActions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class Resp extends HttpServlet {  

	LoginServlet1 ls1;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//String u=ls1.username;
		response.setContentType("text/html");  
		//PrintWriter out = response.getWriter();
		//out.print("\n"+u);
	}
	
}  