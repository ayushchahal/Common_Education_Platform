package servletActions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbActions.TestCreators;
import dynamicResponses.Misc;

@SuppressWarnings("serial")
public class TakeTest extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");  
		PrintWriter out= response.getWriter();
		
		try {
			HttpSession session = request.getSession(false);
			String sessionID = session.getId();
			
			Cookie[] cookie = request.getCookies();
			String cookieValue=cookie[0].getValue();

			//String username=(String) session.getAttribute("user");
			//int loginID=new DBConnection().getLoginID(username);
			if(!sessionID.equals(cookieValue))
			{
				out.print("<html><body>User not correctly authenticated</body></html>");
			}
			else 
			{
				String testID = request.getParameter("testID");
				String studentID = request.getParameter("studentID");
				request.setAttribute("studentID", studentID);
				request.setAttribute("testID", testID);
				TestCreators tc = new TestCreators();
				if(tc.doesStudentTestAssociationExist(studentID, testID))
				{
					
				}
				else
				{
					tc.createStudentTestAssociation(studentID, testID, 0);
				}
				
				String timeLimitText = tc.getTestTimeLimitType(testID);
				int timeLimitType = 0;
				if(timeLimitText.contains("question"))
					timeLimitType = 1;
				else if(timeLimitText.contains("test"))
					timeLimitType = 2;
				
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Insrtuction Page</title>");
				out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\">");
				out.println("</head>");
				
				out.println("<body>");
				
				Misc misc = new Misc();
				String companyHeader = misc.HtmlHeader();
				
				out.println(companyHeader);
				out.println("</div>");
				
				if(timeLimitType == 1)
				{
					out.println("<h5 align=\"center\">INSTRUCTIONS</h5>");
					out.println("<ol>\r\n" + 
							"    <li>Time limit is on each question</li>\r\n" + 
							"    <li>You have to solve each question in a specified amount of time.</li>\r\n" + 
							"    <li>There may be negative marking on wrong answers. Please check the negative marks section of a question to check this.</li>\r\n" + 
							"    <li>No marks will be deducted if you haven't attempted a question.</li>\r\n" + 
							"    <li>You cannot navigate back once you have attempted a particular question.</li>\r\n" + 
							"    <li>Timer will turn red when the last 30 seconds .</li>\r\n" + 
							"</ol>");
					
				}	
				else if(timeLimitType == 2)
				{
					out.println("<h5 align=\"center\">INSTRUCTIONS</h5>");
					out.println("<ol>\r\n" + 
							"    <li>First Instruction</li>\r\n" + 
							"    <li>Second Instruction</li>\r\n" + 
							"    <li>Third Instruction</li>\r\n" + 
							"    <li>Fourth Instruction</li>\r\n" + 
							"</ol>");
					
				}	
					
					out.println("<form action=\"/DisplayQuestions\" method=\"Post\">");
					out.println("<input type=\"hidden\" name=\"testID\" value=\""+testID+"\"/>");
					out.println("<input type=\"hidden\" name=\"studentID\" value=\""+studentID+"\"/>");
					out.println("<input class=\"btn btn-info\" type=\"submit\" value=\"Start Test\" style=\"margin-left:45%;\">");
					out.println("</form>");	 
							
				
				
				out.println("</body>");
				out.println("</html>");
				
				//RequestDispatcher rd=request.getRequestDispatcher("/DisplayQuestions");
				//rd.forward(request,response);
			
			}
		}catch(Exception e)
		{
			out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();	
		}
	}
}
