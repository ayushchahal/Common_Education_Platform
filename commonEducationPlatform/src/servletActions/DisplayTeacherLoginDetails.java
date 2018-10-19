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
import dynamicResponses.Misc;

@SuppressWarnings("serial")
public class DisplayTeacherLoginDetails extends HttpServlet{

	//public static PrintWriter out;
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
				String TEACHER_COACHING_ASSOC="";request.getParameter("cname");
				String TEACHER_NAME="";request.getParameter("tname");
				
				if(loginType ==1)
				{
					TEACHER_NAME=request.getParameter("tname");
					TEACHER_COACHING_ASSOC = request.getParameter("cname");
				}
				else
				{
					TEACHER_NAME=request.getParameter("teacherName");
					String[] c = username.split("@");
					TEACHER_COACHING_ASSOC=c[0];
				}
				
				String loginID=TEACHER_NAME+"@"+TEACHER_COACHING_ASSOC;
				loginID=loginID.replaceAll("\\s","");
				String passwd=TEACHER_NAME+"@"+TEACHER_COACHING_ASSOC;
				passwd=passwd.replaceAll("\\s","");
				out=response.getWriter();
				out.println("<html>\r\n" + 
						"	<head>\r\n" + 
						"		<title>User successfully created</title>\r\n" +
						"<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">"
						);
				
				String header = new Misc().HtmlHeader();
				out.print(header);
				String dashboard = "/Dashboard";
				if(loginType == 2)
					dashboard = "/CoachingDashboard";
				
				String breadCrumb = "<nav aria-label=\"breadcrumb\">\r\n" + 
						"  <ol class=\"breadcrumb\">\r\n" + 
						"    <li class=\"breadcrumb-item\"><a href=\""+dashboard+"\">Home</a></li>\r\n" + 
						"    <li class=\"breadcrumb-item\"><a href=\"/DisplayAddTeacher\">Add Teacher</a></li>\r\n" + 
						"    <li class=\"breadcrumb-item active\" aria-current=\"page\">Teacher Login Details</li>\r\n" + 
						"  </ol>\r\n" + 
						"</nav>";
				
				out.print(breadCrumb);
				
				String teacherID = new DBConnection().getTeacherIDFromTeacherDetails(TEACHER_NAME,request.getParameter("cno1"), request.getParameter("email"));
				out.print("<div class=\"card-body\">");
				if(new Adders().insertLoginDetails(loginID, passwd, "3", teacherID))
				{
					out.print("<div class=\"alert alert-success\" role=\"alert\" style=\"width:60%\">");
					out.println("Login ID of Teacher <b>"+TEACHER_NAME+"</b> is <b>"+loginID+"</b>");
					out.println("<br>Password is <b>"+passwd+"</b>");
					out.print("<br><br><b>Note:</b> Kindly note down and handover these details to the teacher.");
					out.print("</div>");
				}
				else 
				{
					out.println("<script type=\"text/javascript\">");  
					out.println("alert('Error in inserting login details');");  
					out.println("</script>");
				}
				
				out.println("<div></div></body></html>");
				
			}
		}catch(Exception e)
	    {
	    	out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();
	    }
	}
}
