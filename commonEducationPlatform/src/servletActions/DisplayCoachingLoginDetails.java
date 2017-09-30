package servletActions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class DisplayCoachingLoginDetails extends HttpServlet{

	//public static PrintWriter out;
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		/*
		String loginID=AddCoaching.COACHING_NAME+"@"+AddCoaching.COACHING_CITY;
		loginID=loginID.replaceAll("\\s","");
		String passwd=AddCoaching.COACHING_NAME+"@"+AddCoaching.COACHING_CITY;
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
		
		out.println("<form action=\"/SuccessfulLogin.html\"><input type=\"submit\" value=\"Go to Homepage\" /></form>");
		
		if(new Adders().insertLoginDetails(loginID, passwd, "2"))
		{
			out.println("Login ID of Coaching <b>"+AddCoaching.COACHING_NAME+"</b> is <b>"+loginID+"</b>");
			out.println("<br>Password is <b>"+passwd+"</b>");
		}
		else 
		{
			out.println("<script type=\"text/javascript\">");  
			out.println("alert('Error in inserting login details');");  
			out.println("</script>");
		}
		
		
		out.println("</body></html>");
		*/
	}

}
