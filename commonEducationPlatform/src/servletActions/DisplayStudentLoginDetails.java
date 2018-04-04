package servletActions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbActions.Adders;
import dbActions.DBConnection;

@SuppressWarnings("serial")
public class DisplayStudentLoginDetails extends HttpServlet{

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");  
		PrintWriter out= response.getWriter();
		
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
			else {
				String STUDENT_NAME = "";
				String STUDENT_COACHING_ASSOC="";
				
				if(loginType ==1)
				{
					STUDENT_NAME=request.getParameter("sname");
					STUDENT_COACHING_ASSOC = request.getParameter("cname");
				}
				else
				{
					STUDENT_NAME=request.getParameter("studentName");
					String[] c = username.split("@");
					STUDENT_COACHING_ASSOC=c[0];
				}
					
				
				String loginID=STUDENT_NAME+"@"+STUDENT_COACHING_ASSOC;
				loginID=loginID.replaceAll("\\s","");
				String passwd=STUDENT_NAME+"@"+STUDENT_COACHING_ASSOC;
				passwd=passwd.replaceAll("\\s","");
				out=response.getWriter();
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
		
				String formAction1="<form action=\"/Dashboard\"><input type=\"submit\" value=\"Go to Homepage\" /></form>";
				String formAction2="<form action=\"/CoachingDashboard\"><input type=\"submit\" value=\"Go to Homepage\" /></form>";
				
				if(loginType == 1)
					out.print(formAction1);
				else if (loginType == 2)
					out.print(formAction2);
				String studentID = new DBConnection().getStudentIDFromStudentDetails(STUDENT_NAME, request.getParameter("cno1"), request.getParameter("email"));
				if(new Adders().insertLoginDetails(loginID, passwd, "4", studentID))
				{
					out.println("Login ID of student <b>"+STUDENT_NAME+"</b> is <b>"+loginID+"</b>");
					out.println("<br>Password is <b>"+passwd+"</b>");
					if(loginType == 2)
					{
						out.println("<br><br>Student added but is not activated. To activate student, please contact TESTPORTAL admin.");
					}
				}
				else 
				{
					out.println("<script type=\"text/javascript\">");  
					out.println("alert('Error in inserting login details');");  
					out.println("</script>");
				}
		
				out.println("</body></html>");
			}
		}catch(Exception e)
	    {
	    	out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();
	    }
		
		
	}
}
