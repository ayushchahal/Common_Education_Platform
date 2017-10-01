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
import dynamicResponses.CreateCoachingLogin;

@SuppressWarnings("serial")
public class AddCoaching extends HttpServlet {  

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html"); 
		PrintWriter out;
		out= response.getWriter();
		
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
				
				Adders add=new Adders();
				//add.connectToDB();
				
				if(add.insertCoachingDetails(cname,a1,a2,city,state,c1,c2,e,isActive))
				{
					//response.sendRedirect("/SuccessfulLogin.html");
					//RequestDispatcher rd=request.getRequestDispatcher("/DisplayCoachingLoginDetails");  
			       // rd.include(request, response);
					//displayCoachingLoginDetails(cname,city);
					CreateCoachingLogin createC=new CreateCoachingLogin();
					String loginID=createC.createUserID(cname, city);
					String passwd=createC.createPassword(cname, city);
					System.out.println(loginID);
					System.out.println(passwd);
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
					
					out.println("<form action=\"/Dashboard\" method=\"get\"><input type=\"submit\" value=\"Go to Homepage\" /></form>");
					
					if(add.insertLoginDetails(loginID, passwd, "2"))
					{
						out.println("Login ID of Coaching <b>"+cname+"</b> is <b>"+loginID+"</b>");
						out.println("<br>Password is <b>"+passwd+"</b>");
					}
					else 
					{
						out.println("<script type=\"text/javascript\">");  
						out.println("alert('Error in inserting login details');");  
						out.println("</script>");
					}
					
					
					out.println("</body></html>");
					
				}else
				{
					out.println("<script type=\"text/javascript\">");  
					out.println("alert('Error in adding coaching');");  
					out.println("</script>");
				}
			}
		}catch(Exception e)
			{
				out.print("<html><body>Err...Something went wrong.</body></html>");
				e.printStackTrace();	
			}
		
		
		//response.sendRedirect("/SuccessfulLogin.html");
		
		//new Adders().insertCoachingDetails("PowerPoint","lkj","das","delhi","UP","2334343","245543543","ayush.singh@easyjet.com","1");
		
	}
}
