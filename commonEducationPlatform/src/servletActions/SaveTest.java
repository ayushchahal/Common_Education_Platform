package servletActions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbActions.TestCreators;

@SuppressWarnings("serial")
public class SaveTest extends HttpServlet {  

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");  
		PrintWriter out= response.getWriter();
		
		String question = request.getParameter("question");
		String qType = request.getParameter("qType");
		String pmarks = request.getParameter("pmarks");
		String nmarks = request.getParameter("nmarks");
		int numberOfOptions = 6;
		String options[] = new String[numberOfOptions];
		
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
	
		String testID = request.getParameter("tid");
		TestCreators tc=new TestCreators();
		tc.insertQuestionDetails(question, qType, pmarks, nmarks, options[0], options[1], options[2], options[3], options[4], options[5], testID,answer);
		
		String qno = request.getParameter("qno");
		int qNumber = Integer.parseInt(qno);
		
		String noq = request.getParameter("noq");
		int numberOfQuestions = Integer.parseInt(noq);
		
		request.setAttribute("testID", testID);
		request.setAttribute("questionNumber",qNumber+1);
		request.setAttribute("numberOfQuestions",numberOfQuestions);
		
		String buttonAction = request.getParameter("save");
		System.out.println(buttonAction);
		if(buttonAction.equalsIgnoreCase("To Next Question"))
		{
			RequestDispatcher rd=request.getRequestDispatcher("/CreateQuestions");
			rd.forward(request,response);
		}
		else if(buttonAction.equalsIgnoreCase("Save and Exit"))
		{
			//RequestDispatcher rd=request.getRequestDispatcher("/LoginServlet1");
			//rd.forward(request,response);
			response.sendRedirect("/Dashboard");
		}
		else if(buttonAction.equalsIgnoreCase("Complete Questions Submission"))
		{
			out.print("Test created");
		}
		
	}

}
