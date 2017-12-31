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

@SuppressWarnings("serial")
public class SaveTest extends HttpServlet {  

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
			//int loginType=new DBConnection().getLoginType(username);
			
			if(!sessionID.equals(cookieValue))
			{
				out.print("<html><body>User not correctly authenticated</body></html>");
			}
			else
			{
				/*
				String question = request.getParameter("question");
				String qType = request.getParameter("qType");
				String pmarks = request.getParameter("pmarks");
				String nmarks = request.getParameter("nmarks");
				int numberOfOptions = 6;
				String options[] = new String[numberOfOptions];
				String qno = request.getParameter("qno");
				int qNumber = Integer.parseInt(qno);
				
				String noq = request.getParameter("noq");
				int numberOfQuestions = Integer.parseInt(noq);
				
				
				for(int i=0;i<numberOfOptions;i++)
				{
					if(qType.equals("2"))
						options[i] = request.getParameter("option-"+(i+1));
					else if(qType.equals("3"))
						options[i] = request.getParameter("MAQoption-"+(i+1));
				}
				
				String answer="";
				if(qType.equals("1"))
				{
					answer = request.getParameter("options");
					if(answer.equals("true"))
						answer="1";
					else
						answer="2";
				}
				else if(qType.equals("2"))
				{
					answer = request.getParameter("options");
				}
				else if(qType.equals("3"))
				{
					String[] results = request.getParameterValues("options");
					for (int i = 0; i < results.length; i++) {
					    answer=answer+results[i]; 
					}
				}
				String buttonAction = request.getParameter("save");
				System.out.println(buttonAction);
				TestCreators tc=new TestCreators();
				
				if(buttonAction.equalsIgnoreCase("To Previous Question"))
				{
					
					RequestDispatcher rd=request.getRequestDispatcher("/PreviousQuestions");
					rd.forward(request,response);
				}
				
				
				String testID = request.getParameter("tid");
				
				if(!(buttonAction.equalsIgnoreCase("To Previous Question")))
				{
					tc.insertQuestionDetails(question, qType, pmarks, nmarks, options[0], options[1], options[2], options[3], options[4], options[5], testID,answer);					
				}
				if(buttonAction.equalsIgnoreCase("Complete Questions Submission"))
				{
					tc.updateIsCompletedToOne(testID);
				}
				
				
				request.setAttribute("testID", testID);
				request.setAttribute("questionNumber",qNumber+1);
				request.setAttribute("numberOfQuestions",numberOfQuestions);
				
				
				if(buttonAction.equalsIgnoreCase("To Next Question"))
				{
					RequestDispatcher rd=request.getRequestDispatcher("/CreateQuestions");
					rd.forward(request,response);
				}
				else if(buttonAction.equalsIgnoreCase("Save and Exit"))
				{
					//RequestDispatcher rd=request.getRequestDispatcher("/LoginServlet1");
					//rd.forward(request,response);
					if(qNumber == numberOfQuestions)
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
				else if(buttonAction.equalsIgnoreCase("Complete Questions Submission"))
				{
					RequestDispatcher rd=request.getRequestDispatcher("/ShareTestPage");
					rd.forward(request,response);
				}
				*/
				String buttonAction = request.getParameter("save");
				String testID = request.getParameter("tid");
				request.setAttribute("testID", testID);
				
				if(buttonAction.equalsIgnoreCase("To Next Question"))
				{
					RequestDispatcher rd=request.getRequestDispatcher("/ToNextQuestion");
					rd.forward(request,response);
				}
				else if(buttonAction.equalsIgnoreCase("To Previous Question"))
				{
					RequestDispatcher rd=request.getRequestDispatcher("/ToPreviousQuestion");
					rd.forward(request,response);
				}
				else if(buttonAction.equalsIgnoreCase("Complete Questions Submission"))
				{
					RequestDispatcher rd=request.getRequestDispatcher("/ShareTestPage");
					rd.forward(request,response);
				}
				else if(buttonAction.equalsIgnoreCase("Save and Exit"))
				{
					RequestDispatcher rd=request.getRequestDispatcher("/SaveAndExit");
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
