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
import dynamicResponses.CreateQuestions;

@SuppressWarnings("serial")
public class CreateTest extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
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
				String testName=request.getParameter("testName");
				String subjectName=request.getParameter("testSubject");
				String testType=request.getParameter("testType");
				String noq=request.getParameter("noq");
				String totalMarks=request.getParameter("totalMarks");
				String totalTime=request.getParameter("totalTime");
				if(testType.contains("Standard"))
					testType="1";
				else
					testType="2";
				
				TestCreators tc=new TestCreators();
				if(tc.insertTestDetails(testName, subjectName, testType, noq, totalMarks, totalTime))
				{
					//out.print("Test Created");
					CreateQuestions cq=new CreateQuestions();
					int numberOfQuestions=Integer.parseInt(noq);
					String html=cq.createQuestionsUI(numberOfQuestions, 1);
					out.println(html);
								
					//RequestDispatcher rd=request.getRequestDispatcher("/CreateQuestions");  
			        //rd.include(request,response); 
				}
			}
		}catch(Exception e)
		{
			out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();	
		}
	}
	
	

}
