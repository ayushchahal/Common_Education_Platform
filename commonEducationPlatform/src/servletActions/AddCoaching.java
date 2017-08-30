package servletActions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbActions.Adders;

@SuppressWarnings("serial")
public class AddCoaching extends HttpServlet {  

	public static PrintWriter Coaching_out;
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");  
		Coaching_out= response.getWriter();
		
		String cname=request.getParameter("coahcingName");
		String a1=request.getParameter("address1");
		String a2=request.getParameter("address2");
		String city=request.getParameter("city");
		String state=request.getParameter("state");
		String c1=request.getParameter("cno1");
		String c2=request.getParameter("cno2");
		String e=request.getParameter("email");
		String isActive=request.getParameter("IsActive");
		if(isActive.equals("Yes"))
			isActive="1";
		else 
			isActive="0";
		
		if(new Adders().insertCoachingDetails(cname,a1,a2,city,state,c1,c2,e,isActive))
		{
			response.sendRedirect("/SuccessfulLogin.html");
			/*
			Coaching_out.println("<html>");
			Coaching_out.println("<head>");
			Coaching_out.println("<script type=\"text/javascript\">");  
			Coaching_out.println("alert('Coaching Added Successfully');");  
			Coaching_out.println("</script>");
			Coaching_out.println("</head>");
			Coaching_out.println("</html>");
			*/
		}else
		{
			Coaching_out.println("<script type=\"text/javascript\">");  
			Coaching_out.println("alert('Error in adding coaching');");  
			Coaching_out.println("</script>");
		}
		
		
		//response.sendRedirect("/SuccessfulLogin.html");
		
		//new Adders().insertCoachingDetails("PowerPoint","lkj","das","delhi","UP","2334343","245543543","ayush.singh@easyjet.com","1");
		
	}

}
