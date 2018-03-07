package servletActions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbActions.DBConnection;
import dbActions.TestCreators;
import dbActions.TestUpdater;

@SuppressWarnings("serial")
public class SaveAndExit extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		TestCreators tc = new TestCreators();
		String qno = request.getParameter("qno");
		int qNumber = Integer.parseInt(qno);
		
		String testID = (String) request.getAttribute("testID");
		
		HttpSession session = request.getSession(false);
		String username=(String) session.getAttribute("user");
		int loginType=new DBConnection().getLoginType(username);
		
		
		/*
		 * If user is at the question for the first time and user clicks on save and exit then insert the record
		 * Else if user is at the question for the second time and user has updated the question then do update operation
		 * Else if user is at the question for the second time and user has not updated the question then do not do any db operation.
		 */
		
		
		/*
		 * Fetching question data
		 */
		String questionText = request.getParameter("question");
		String questionType = request.getParameter("qType");
		String pmarks = request.getParameter("pmarks");
		String nmarks = request.getParameter("nmarks");
		int numberOfOptions = 6;
		String options[] = new String[numberOfOptions];
		
		for(int i=0;i<numberOfOptions;i++)
		{
			if(questionType.equals("2"))
				options[i] = request.getParameter("option-"+(i+1));
			else if(questionType.equals("3"))
				options[i] = request.getParameter("MAQoption-"+(i+1));
		}
		
		String answer="";
		if(questionType.equals("1"))
		{
			answer = request.getParameter("options");
			if(answer.equals("true"))
				answer="1";
			else
				answer="2";
		}
		else if(questionType.equals("2"))
		{
			answer = request.getParameter("options");
		}
		else if(questionType.equals("3"))
		{
			String[] results = request.getParameterValues("options");
			for (int i = 0; i < results.length; i++) {
			    answer=answer+results[i]; 
			}
		}
		
		
		if(qNumber > tc.numberOfQuestionsInQuestionsTable(testID))
		{
			//Insert in db
			tc.insertQuestionDetails(questionText, questionType, pmarks, nmarks, options[0], options[1], options[2], options[3], options[4], options[5], testID,answer);
		}
		else
		{
			String qIDs[]=tc.getQuestionIDs(testID);
			int questionNumber = Integer.parseInt(qno);
			int questionID = Integer.parseInt(qIDs[questionNumber-1]);
			if(tc.isQuestionUpdated(""+questionID, questionText, questionType, pmarks, nmarks, options[0], options[1], options[2], options[3], options[4], options[5], answer))
			{
				System.out.println("Doing update operation");
				TestUpdater tu=new TestUpdater();
				tu.updateQuestion(""+questionID, questionText, questionType, pmarks, nmarks, options[0], options[1], options[2], options[3], options[4], options[5], answer);
			}
			else
			{
				//Do nothing
			}
		}
		
		
		
		if(tc.numberOfQuestionsInQuestionsTable(testID) == tc.numberOfQuestionsInTestTable(testID))
		{
			tc.updateIsCompletedToOne(testID);
		}
		if(loginType == 1)
		{
			response.sendRedirect("/Dashboard");
		}
		else if(loginType == 2)
		{
			response.sendRedirect("/CoachingDashboard");
		}
	}
}
