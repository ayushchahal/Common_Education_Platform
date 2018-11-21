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

import dbActions.DBConnection;
import dbActions.TestCreators;

@SuppressWarnings("serial")
public class PreviousQuestions extends HttpServlet {  

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
			else
			{
				if(loginType == 1 || loginType == 2)
				{
					int questionNumber = Integer.parseInt(request.getParameter("qno"));
					String testID = request.getParameter("tid");
					String numberOfQuestions = request.getParameter("noq");
					TestCreators tc = new TestCreators();
					//String questionText = tc.getQuestionText(questionID);
					String qID[]=tc.getQuestionIDs(testID);
					int questionID = Integer.parseInt(qID[questionNumber-2]);
					
			        request.setAttribute("numberOfQuestions", numberOfQuestions);
					request.setAttribute("questionNumber", questionNumber-1);
					request.setAttribute("testID", testID);
					request.setAttribute("questionID", questionID);
					String cQuestionText = request.getParameter("question");
					String cQuestionType = request.getParameter("qType");
					String cPMarks = request.getParameter("pmarks");
					String cNMarks = request.getParameter("nmarks");
					int numberOfOptions = 6;
					String options[] = new String[numberOfOptions];
					String timeLimit = request.getParameter("timeLimit");
					for(int i=0;i<numberOfOptions;i++)
					{
						if(cQuestionType.equals("2"))
							options[i] = request.getParameter("option-"+(i+1));
						else if(cQuestionType.equals("3"))
							options[i] = request.getParameter("MAQoption-"+(i+1));
					}
					String answer="";
					if(cQuestionType.equals("1"))
					{
						answer = request.getParameter("options");
						if(answer.equals("true"))
							answer="1";
						else
							answer="2";
					}
					else if(cQuestionType.equals("2"))
					{
						answer = request.getParameter("options");
					}
					else if(cQuestionType.equals("3"))
					{
						String[] results = request.getParameterValues("options");
						for (int i = 0; i < results.length; i++) {
						    answer=answer+results[i]; 
						}
					}
					
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
					
					if(tc.isQuestionUpdated(""+questionID, cQuestionText, cQuestionType, cPMarks, cNMarks, options[0], options[1], options[2], options[3], options[4], options[05], answer, timeLimit,topics))
						tc.insertQuestionDetails(cQuestionText, cQuestionType, cPMarks, cNMarks, options[0], options[1], options[2], options[3], options[4], options[5], testID,answer,timeLimit,topics);
					
					RequestDispatcher rd=request.getRequestDispatcher("/ShowPreviousQuestion");  
			        rd.forward(request, response);  
			        
				}
			}
		}catch(Exception e)
		{
			out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();	
		}
	}
}
