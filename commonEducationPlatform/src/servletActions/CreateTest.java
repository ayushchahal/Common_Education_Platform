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
public class CreateTest extends HttpServlet{

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
			int loginID=new DBConnection().getLoginID(username);
			if(!sessionID.equals(cookieValue))
			{
				out.print("<html><body>User not correctly authenticated</body></html>");
			}
			else 
			{
				String testName=request.getParameter("testName");
				String subjectName=request.getParameter("testSubject");
				String testType=request.getParameter("testType");
				String timeLimitType=request.getParameter("timeLimitType");
				String totalTime=request.getParameter("totalTime");
				
				String splitTest = request.getParameter("splitSections");
				
				String nos = request.getParameter("numberOfSections");
				int noOfSections = Integer.parseInt(nos);
				String[] sections = new String[noOfSections];
				String[] QBsections = new String[noOfSections];
				if(splitTest.equals("1"))
				{
					for(int i=0;i<noOfSections;i++)
					{
						sections[i] = request.getParameter("section-"+(i+1));
						QBsections[i] = request.getParameter("QBsection-"+(i+1));
					}
				}
				
				String noqQB=request.getParameter("noqQB");
				String noq=request.getParameter("noq");
				String totalMarks=request.getParameter("totalMarks");
				
				System.out.println("test name: "+testName);
				System.out.println("subject name: "+subjectName);
				System.out.println("test type: "+testType);
				System.out.println("time limit type: "+timeLimitType);
				System.out.println("total time: "+totalTime);
				System.out.println("Split test into sections: "+splitTest);
				System.out.println("number of sections: "+noOfSections);
				if(splitTest.equals("1"))
				{
					System.out.println("noq in section-1 QB: "+QBsections[0]);
					System.out.println("noq in section-1: "+sections[0]);
					System.out.println("noq in section-2 QB: "+QBsections[0]);
					System.out.println("noq in section-2: "+sections[1]);
				}
				System.out.println("Number of questions in QB: "+noqQB);
				System.out.println("Number of questions in test: "+noq);
				System.out.println("total marks: "+totalMarks);
				
				
				
				/*
				TestCreators tc=new TestCreators();
				DBConnection dbc = new DBConnection();
				int loginType =  dbc.getLoginType(username);
				String testOwner = "";
				String createdBy = "";
				if(loginType == 2)
				{
					testOwner = dbc.getCoachingIDFromLoginDetails(username);
					createdBy = testOwner;
				}
				if(loginType == 3)
				{
					testOwner = dbc.getCoachingIDFromTeacherLoginDetails(""+loginID);
					createdBy = dbc.getTeacherIDFromTeacherLoginDetails(""+loginID);
				}
				if(tc.insertTestDetails(testName, subjectName, testType, noq, totalMarks, totalTime, testOwner,"0","0",createdBy))
				{
					//out.print("Test Created");
					//CreateQuestions cq=new CreateQuestions();
					int numberOfQuestions=Integer.parseInt(noq);
					request.setAttribute("numberOfQuestions", numberOfQuestions);
					request.setAttribute("questionNumber",1);
					//String html=cq.createQuestionsUI(numberOfQuestions, 1);
					//out.println(html);
					
					int testID=tc.getTestID(testName, subjectName, testType, noq, totalMarks, totalTime);
					request.setAttribute("testID",testID);
					//tc.insertTestID(testID);
					//System.out.println("Out of control");
					RequestDispatcher rd=request.getRequestDispatcher("/CreateQuestions");  
			        rd.include(request,response);
				}
				*/
			}
		}catch(Exception e)
		{
			out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();	
		}
	}
	
	

}
