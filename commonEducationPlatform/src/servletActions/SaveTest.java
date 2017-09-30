package servletActions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class SaveTest extends HttpServlet {  

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		String question = request.getParameter("question");
		String qType = request.getParameter("qType");
		String pmarks = request.getParameter("pmarks");
		String nmarks = request.getParameter("nmarks");
		int numberOfOptions = 4;
		String options[] = new String[numberOfOptions];
		
		//for(int i=0;i<numberOfOptions-1;i++)
		//{
			options[0] = request.getParameter("option-1");
		//}
		
		System.out.println(question+qType+pmarks+nmarks+options[0]);
		
	}

}
