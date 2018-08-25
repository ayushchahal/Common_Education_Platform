package servletActions;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbActions.TestCreators;
import dynamicResponses.Misc;

@SuppressWarnings("serial")
public class SubmitQuestion extends HttpServlet{

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
				
				String buttonAction = request.getParameter("save");
				String questionID = (String) request.getParameter("questionID");
				String questionNumber = (String) request.getParameter("questionNumber");
				String testID = (String) request.getParameter("testID");
				request.setAttribute("testID", testID);
				String questionType = tc.getTypeOfQuestion(questionID);
				int qno = Integer.parseInt(questionNumber);
				//String questionIDs[] = tc.getQuestionIDs(testID);
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
				String studentID = request.getParameter("studentID");
				request.setAttribute("studentID", studentID);
				
				String testTimeLimit = tc.getTestTimeLimitType(testID);
				if(testTimeLimit.contains("test"))
				{
					String currentDateTime = "";
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
				    Date date = new Date();  
				    currentDateTime = formatter.format(date); 
				    request.setAttribute("dateTimeInvested", currentDateTime);
					//new Misc().recordRemainingTime(dateTime);
				}
				
				if(buttonAction.equalsIgnoreCase("To Next Question"))
				{
					/* LOGIC:
					 * Check if the question is being answered for the first time.
					 * If YES, then insert the answer and display next question
					 * If NO, then check if the answer is updated or not updated.
					 * 		If YES, then do UPDATE operation and then display next question
					 * 		If no, then display next question
					 * 
					 */
					request.setAttribute("questionNumber", qno+1);
					if(tc.isQuestionAlreadyAnswered(studentID, questionID))
					{
						//String answer2 = tc.getAnswer(studentID, questionID);
						//request.setAttribute("answer", answer2);
						if(tc.isAnswerUpdated(studentID, questionID, answer))
						{
							//Do update operation
							tc.updateAnswer(studentID, questionID, answer);
						}
					}
					else
					{
						//Do insert operation
						tc.recordAnswer(studentID, questionID, answer, testID);
						//String next_question_ID = questionIDs[qno];
						//String next_question_answer = tc.getAnswer(studentID, next_question_ID);
						//request.setAttribute("answer", next_question_answer);
					}
					
					
					RequestDispatcher rd=request.getRequestDispatcher("/DisplayQuestions");
					rd.forward(request,response);
				}
				else if(buttonAction.equalsIgnoreCase("To Previous Question"))
				{
					//Logic is to check if the question is already answered
					//If yes then check if answer is updated. If yes, do UPDATE operation else don't do anything
					//If no then do INSERT operation
					request.setAttribute("questionNumber", qno-1);
					if(tc.isQuestionAlreadyAnswered(studentID, questionID))
					{
						String answer2 = tc.getAnswer(studentID, questionID);
						request.setAttribute("answer", answer2);
						if(tc.isAnswerUpdated(studentID, questionID, answer))
						{
							//Do update operation
							tc.updateAnswer(studentID, questionID, answer);
						}
					}
					else
					{
						//Do insert operation
						tc.recordAnswer(studentID, questionID, answer, testID);
						/*String prev_question_ID = questionIDs[qno-2];
						String prev_question_answer = tc.getAnswer(studentID, prev_question_ID);
						request.setAttribute("answer", prev_question_answer);
						*/
						
					}
					RequestDispatcher rd=request.getRequestDispatcher("/DisplayQuestions");
					rd.forward(request,response);
				}
				else if(buttonAction.equalsIgnoreCase("Submit test"))
				{
					/*
					 * Check if the current question is already answered.
					 * If yes then do update operation
					 * If no then do insert operation
					 */
					if(tc.isQuestionAlreadyAnswered(studentID, questionID))
					{
						tc.updateAnswer(studentID, questionID, answer);
					}
					else
					{
						tc.recordAnswer(studentID, questionID, answer, testID);
					}
					/*
					 * Check if all questions are answered or not. 
					 * If no, insert null values in answersheet.
					 * If yes, do nothing.
					 */
					int numberOfQuestions = tc.getNumberOfQuestionsInATest(testID);
					if(!(tc.areAllQuestionsAnswered(testID, studentID, numberOfQuestions)))
					{
						System.out.println("All questions are not answered.");
						String uaq[] = tc.returnUnansweredQuestionIDs(testID, studentID);
						for(int i = 0; i< uaq.length; i++)
						{
							System.out.println("Unanswered questions ids are: "+uaq[i]);
						}
						//Insert null values for unanswered questions in answersheet table.
						tc.insertNullValuesForUnansweredQuestions(uaq,studentID,testID);
					}
					tc.updateStudentTestAssociation(studentID, testID, 1);
					RequestDispatcher rd=request.getRequestDispatcher("/TestCompletionReport");
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
