package servletActions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbActions.DBConnection;

@SuppressWarnings("serial")
public class ViewStudentsServlet extends HttpServlet {  

	public static PrintWriter Coaching_out;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");  
		Coaching_out= response.getWriter();
		new DBConnection().getAllStudentDetails();
		
	}

}
