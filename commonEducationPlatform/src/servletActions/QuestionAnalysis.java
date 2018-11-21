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
public class QuestionAnalysis extends HttpServlet{


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
				TestCreators tc = new TestCreators();
				
				//String testID = request.getParameter("testID");
				String studentID = request.getParameter("studentID");
				String questionID = request.getParameter("questionID");
				String questionNumber = request.getParameter("questionNumber");
				
				
				int totalAttempts = 0;
				int successfulAttempts = 0;
				
				double averageTime = tc.getAverageTimeForEachQuestion(studentID, questionID);
				
				totalAttempts = tc.getTotalAttemptsOfAQuestion(questionID);
				successfulAttempts = tc.getSuccessfulAttemptsOfQuestion(questionID);
				
				int successfulPercentage = (successfulAttempts*100)/totalAttempts;
				/*
				String attemptstudentText = "students";
				String totalstudentText = "students";
				if(totalAttempts == 1 )
					totalstudentText="student";
				
				if(successfulAttempts == 0 || successfulAttempts == 1)
					attemptstudentText="student";
				*/	
				//out.write("<p><b>"+successfulAttempts+" "+attemptstudentText+"</b> out of total <b>"+totalAttempts+" "+totalstudentText+"</b> have attempted this question correctly.</p>");
				
				
				out.write("<div class=\"alert alert-primary\" role=\"alert\">");
				out.write("<b>Question "+ questionNumber +" Analysis:</b>");
				out.write("<ul>");
				out.write("<p><li><b>"+successfulPercentage+" %</b> students answered this question correctly.</li>");
				out.write("<li>Average time to attempt this question is <b>"+averageTime+" seconds</b>.</li></p>");
				out.write("</ul>");
				out.write("</div>");
				
				
				
				
			}
		}catch(Exception e)
	    {
	    	out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();
	    }
	}
}
