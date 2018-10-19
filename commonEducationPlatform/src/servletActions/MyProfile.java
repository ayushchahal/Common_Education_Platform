package servletActions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbActions.DBConnection;
import dynamicResponses.Misc;

@SuppressWarnings("serial")
public class MyProfile extends HttpServlet{


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");  
		PrintWriter out= response.getWriter();
		
		try {
			HttpSession session = request.getSession(false);
			String sessionID = session.getId();
			
			Cookie[] cookie = request.getCookies();
			String cookieValue=cookie[0].getValue();

			
			if(!sessionID.equals(cookieValue))
			{
				out.print("<html><body>User not correctly authenticated</body></html>");
			}
			else 
			{
				out.println("<html><head><title>My Profile</title>");
				out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\">");
				out.println("<script src=\"\\javascript\\"+"changeInputType.js"+"\"></script>");
				out.println("</head>");
				
				Misc misc = new Misc();
				out.print(misc.HtmlHeader()+"</div>");
				
				String navigationBar = "<div class=\"card-body\"><nav aria-label=\"breadcrumb\">\r\n" + 
						"  <ol class=\"breadcrumb\">\r\n" + 
						"    <li class=\"breadcrumb-item\"><a href=\"StudentDashboard#\">Home</a></li>\r\n" + 
						"    <li class=\"breadcrumb-item active\" aria-current=\"page\">My Profile</li>\r\n" + 
						"  </ol>\r\n" + 
						"</nav>";
				
				out.println(navigationBar);
					
				String personalDetailsCardHeader = "<div class=\"card border-secondary\" style=\"margin-left:5%;width:70%;\">     <div class=\"card-header\">        <h5>Personal Details</h5>    </div>  ";
				
				out.println(personalDetailsCardHeader);
				
				String personalDetailsCardBody = "<div class=\"card-body\"> ";
				out.println(personalDetailsCardBody);
				
				String studentNameLabel = "<div class=\"input-group input-group-default\"><div class=\"input-group-prepend\">    		<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Student Name\r\n" + 
						"        </span>  \r\n" + 
						"    </div>";
				
				out.println(studentNameLabel);
				
				String username=(String) session.getAttribute("user");
				DBConnection dbc = new DBConnection();
				String studentID = dbc.getStudentIDFromLoginName(username);
				String studentName = dbc.getStudentName(studentID);
				
				String studentNameP = "<p class=\"form-control\">"+studentName+"</p>";
				out.println(studentNameP);
				out.println("</div><br>");
				
				String contactNumberLabel = "<div class=\"input-group input-group-default\"><div class=\"input-group-prepend\">    		<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Contact Number\r\n" + 
						"        </span>  \r\n" + 
						"    </div>";
				out.println(contactNumberLabel);
				
				String contactNumber = dbc.getStudentContactNumber(studentID); 
				String contactNumberP = "<p class=\"form-control\">"+contactNumber+"</p>";
				out.println(contactNumberP);
				out.println("</div><br>");
				
				
				String emailAddressLabel = "<div class=\"input-group input-group-default\"><div class=\"input-group-prepend\">    		<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Email Address\r\n" + 
						"        </span>  \r\n" + 
						"    </div>";
				out.println(emailAddressLabel);
				
				String emailAddress = dbc.getStudentEmailAddress(studentID); 
				String emailAddressP = "<p class=\"form-control\">"+emailAddress+"</p>";
				out.println(emailAddressP);
				out.println("</div><br></div></div><br>");
				
				
				personalDetailsCardHeader = "<div class=\"card border-secondary\" style=\"margin-left:5%;width:70%;\">     <div class=\"card-header\">        <h5>Account Details</h5>    </div>  ";
				
				out.println(personalDetailsCardHeader);
				
				personalDetailsCardBody = "<div class=\"card-body\"> ";
				out.println(personalDetailsCardBody);
				
				String userNameLabel = "<div class=\"input-group input-group-default\"><div class=\"input-group-prepend\">    		<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Username\r\n" + 
						"        </span>  \r\n" + 
						"    </div>";
				out.println(userNameLabel);
				
				String usernameP = "<p class=\"form-control\">"+username+"</p>";
				out.println(usernameP);
				out.println("</div><br>");
				
				String passwordLabel = "<div class=\"input-group input-group-default\"><div class=\"input-group-prepend\">    		<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Password\r\n" + 
						"        </span>  \r\n" + 
						"    </div>";
				out.println(passwordLabel);
				
				String password = dbc.getStudentPassword(username);
				String passwordP = "<input type=\"password\" class=\"form-control\" id=\"inputType\" value=\""+password+"\">";
				String showPassword = "<input type=\"button\" class=\"form-control\" id=\"pwdButton\" value=\"Show Password\" onclick=\"changeInputType()\">";
				out.println(passwordP+showPassword);
				out.println("</div><br>");
				
				String accountDeactLabel = "<div class=\"input-group input-group-default\"><div class=\"input-group-prepend\">    		<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Account creation date\r\n" + 
						"        </span>  \r\n" + 
						"    </div>";
				out.println(accountDeactLabel);
				
				String createDate = dbc.getStudentAccountCreateDate(studentID);
				String createDateP = "<p class=\"form-control\">"+createDate+"</p>";
				out.println(createDateP);
				out.println("</div><br>");
				
				String accountStatusLabel = "<div class=\"input-group input-group-default\"><div class=\"input-group-prepend\">    		<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Account status\r\n" + 
						"        </span>  \r\n" + 
						"    </div>";
				out.println(accountStatusLabel);
				
				String accountStatus = dbc.getStudentAccountStatus(studentID);
				String accountStatusP = "<p class=\"form-control\">"+accountStatus+"</p>";
				out.println(accountStatusP);
				out.println("</div><br>");
				
				out.println("</div></div><br>");
				
				
				
				
			}
		}catch(Exception e)
	    {
	    	out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();
	    }
	}
}
