package servletActions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbActions.TestCreators;
import dbActions.TestUpdater;

@SuppressWarnings("serial")
public class ToNextQuestion extends HttpServlet{

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
				String questionText = request.getParameter("question");
				String questionType = request.getParameter("qType");
				String pmarks = request.getParameter("pmarks");
				String nmarks = request.getParameter("nmarks");
				int numberOfOptions = 6;
				String options[] = new String[numberOfOptions];
				String qno = request.getParameter("qno");
				int questionNumber = Integer.parseInt(qno);
				
				String noq = request.getParameter("noq");
				int numberOfQuestions = Integer.parseInt(noq);
				String testID = request.getParameter("tid");
				String timeLimit = request.getParameter("timeLimit");
				
				
				/*
				 * Topics
				 */
				String numberOfTopics = request.getParameter("numberOfTopics");
				System.out.println("Number Of Topics: "+ numberOfTopics);
				int noT = Integer.parseInt(numberOfTopics);
				String topics = "";
				
				for(int i = 0; i < noT; i++)
				{
					if(i == (noT-1))
						topics = topics + request.getParameter("topics"+(i+1));
					else
						topics = topics + request.getParameter("topics"+(i+1)) + ",";
				}
				
				System.out.println("TOPIC NAME IS: " + topics);
				
				/*
				for(int i = 0;i<topics.length;i++)
				{
					System.out.println("TOPIC NAME IS: " + topics[i]);
				}
				*/
				
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
				
				request.setAttribute("testID", testID);
				request.setAttribute("questionNumber",questionNumber+1);
				request.setAttribute("numberOfQuestions",numberOfQuestions);
				
				
				TestCreators tc=new TestCreators();
				/*
				 * first check if question already exists in db (i.e. check if user is creating the question for the first time or is viewing an already created question)
				 * if yes then check if user has updated a question or not. If question is updated then update db otherwise do not do any db operation.
				 * if no then insert question in the db
				 */
				
				int numberOfQuestionsSaved = tc.getNumberOfSavedQuestionsInATest(testID);
				System.out.println("numberOfQuestionsSaved: "+numberOfQuestionsSaved);
				System.out.println("questionNumber: "+questionNumber);
				if(numberOfQuestionsSaved >= questionNumber)
				{
					String qIDs[]=tc.getQuestionIDs(testID);
					int questionID = Integer.parseInt(qIDs[questionNumber-1]);
					String nextQuestionID = null;
					if(qIDs.length <= (questionNumber))
					{
						nextQuestionID = null;
					}
					else
					{
						nextQuestionID = qIDs[questionNumber];
					}
					
					if(tc.isQuestionUpdated(""+questionID, questionText, questionType, pmarks, nmarks, options[0], options[1], options[2], options[3], options[4], options[5], answer, timeLimit, topics))
					{
						System.out.println("Doing update operation");
						TestUpdater tu=new TestUpdater();
						tu.updateQuestion(""+questionID, questionText, questionType, pmarks, nmarks, options[0], options[1], options[2], options[3], options[4], options[5], answer, timeLimit, topics);
						request.setAttribute("questionID", nextQuestionID);
						RequestDispatcher rd=request.getRequestDispatcher("/CreateQuestions");
						rd.forward(request,response);
					}
					else
					{
						System.out.println("Display next question without doing any update operation.");
						request.setAttribute("questionID", nextQuestionID);
						RequestDispatcher rd=request.getRequestDispatcher("/CreateQuestions");
						rd.forward(request,response);
					}
				}
				else
				{
					System.out.println("New question - Inserting into db");
					tc.insertQuestionDetails(questionText, questionType, pmarks, nmarks, options[0], options[1], options[2], options[3], options[4], options[5], testID, answer, timeLimit, topics);					
					RequestDispatcher rd=request.getRequestDispatcher("/CreateQuestions");
					rd.forward(request,response);
				}
					
				
				
				
			}
		}catch(Exception e)
		{
			out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();	
		}
	}
}
