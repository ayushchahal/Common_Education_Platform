package servletActions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbActions.TestCreators;

@SuppressWarnings("serial")
public class SaveTest extends HttpServlet {  

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		String question = request.getParameter("question");
		String qType = request.getParameter("qType");
		String pmarks = request.getParameter("pmarks");
		String nmarks = request.getParameter("nmarks");
		int numberOfOptions = 6;
		String options[] = new String[numberOfOptions];
		
		for(int i=0;i<numberOfOptions;i++)
		{
			options[i] = request.getParameter("option-"+(i+1));
		}
		
		TestCreators tc=new TestCreators();
		tc.insertQuestionDetails(question, qType, pmarks, nmarks, options[0], options[1], options[2], options[3], options[4], options[5],null);
		
		
		
	}

}
