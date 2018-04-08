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

@SuppressWarnings("serial")
public class TestCompletionReport extends HttpServlet{

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
				String studentID = (String) request.getAttribute("studentID");
				String testID = (String) request.getAttribute("testID");
				
				//Get QuestionID and Answer using TestID and store them in a data structure A.
				//Using studentID and QuestionID (retrieve questionID from A) from AnswerSheet get Answers and compare them with answers in A.
				TestCreators tc = new TestCreators();
				int n = tc.getNumberOfSavedQuestionsInATest(testID);
				
				String[][] A = new String[n][4];
				A = tc.getCoachingAnswers(testID);
				
				int score = 0;
				
				int total = tc.getTotalMarks(testID);
				
				for(int i=0;i<n;i++)
				{
					String answer = tc.getStudentAnswer(A[i][0],studentID);
					if(answer.equals(A[i][1]))
						score = score+Integer.parseInt(A[i][2]);
					else
						score = score-Integer.parseInt(A[i][3]);
				}
				
				
				
				out.println("<html><head><title>Test successful</title></head>");
				out.println("<body>");
				out.println("<h3>Test completed<h3><br><br>");
				out.println("<h2>Your score is: "+score+" out of "+total);
				
				
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
	