package servletActions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbActions.Adders;

@SuppressWarnings("serial")
public class DisplayTeacherLoginDetails extends HttpServlet{

	//public static PrintWriter out;
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String TEACHER_COACHING_ASSOC=request.getParameter("cname");
		String TEACHER_NAME=request.getParameter("tname");
		String loginID=TEACHER_NAME+"@"+TEACHER_COACHING_ASSOC;
		loginID=loginID.replaceAll("\\s","");
		String passwd=TEACHER_NAME+"@"+TEACHER_COACHING_ASSOC;
		passwd=passwd.replaceAll("\\s","");
		PrintWriter out=response.getWriter();
		out.println("<html>\r\n" + 
				"	<head>\r\n" + 
				"		<title>User successfully created</title>\r\n" +
				"<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">\r\n" + 
				"	<link rel=\"stylesheet\" href=\"teacherLoginDetails.css\">\r\n" + 
				"	<link rel=\"stylesheet\" href=\"//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css\">"+
				"	</head>\r\n" + 
				"	\r\n" + 
				"	<body>\r\n" + 
				"	\r\n" + 
				"		");
		
		out.println("<form action=\"/Dashboard\"><input type=\"submit\" value=\"Go to Homepage\" /></form>");
		if(new Adders().insertLoginDetails(loginID, passwd, "3"))
		{
			out.println("Login ID of Teacher <b>"+TEACHER_NAME+"</b> is <b>"+loginID+"</b>");
			out.println("<br>Password is <b>"+passwd+"</b>");
		}
		else 
		{
			out.println("<script type=\"text/javascript\">");  
			out.println("alert('Error in inserting login details');");  
			out.println("</script>");
		}
		
		out.println("</body></html>");
		
		
	}
}
