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
import dynamicResponses.Misc;

@SuppressWarnings("serial")
public class TestCompletionReport extends HttpServlet{

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
				String studentID = (String) request.getAttribute("studentID");
				String testID = (String) request.getAttribute("testID");
				
				//Get QuestionID and Answer using TestID and store them in a data structure A.
				//Using studentID and QuestionID (retrieve questionID from A) from AnswerSheet get Answers and compare them with answers in A.
				TestCreators tc = new TestCreators();
				int n = tc.getNumberOfSavedQuestionsInATest(testID);
				
				String[][] A = new String[n][4];
				A = tc.getCoachingAnswers(testID);
				
				int score = 0;
				
				int total = tc.getTotalMarks(testID);
				
				for(int i=0;i<n;i++)
				{
					String answer = tc.getStudentAnswer(A[i][0],studentID);
					System.out.println("Teacher's answer is: "+A[i][1]);
					System.out.println("Student's answer is: "+answer);
					if(answer != null)
					{
						if(A[i][1].equals(answer))
						{
							score = score+Integer.parseInt(A[i][2]);
						}
						else
						{
							score = score-Integer.parseInt(A[i][3]);
						}
					}
					
						
					/*
					if(answer.equals(A[i][1]))
						score = score+Integer.parseInt(A[i][2]);
					else
						score = score-Integer.parseInt(A[i][3]);
					*/
					
				}
				
				String htmlHeaders = new Misc().HtmlHeader();
				String navigationBar = "<div class=\"card-body\"><nav aria-label=\"breadcrumb\">\r\n" + 
						"  <ol class=\"breadcrumb\">\r\n" + 
						"    <li class=\"breadcrumb-item\"><a href=\"StudentDashboard#\">Home</a></li>\r\n" + 
						"    <li class=\"breadcrumb-item\"><a href=\"/MyTests\">My Tests</a></li>\r\n" + 
						"    <li class=\"breadcrumb-item active\" aria-current=\"page\">Score Card</li>\r\n" +
						"  </ol>\r\n" + 
						"</nav>";
				
				out.println("<html><head><title>Test successful</title>");
				out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\">");
				out.println("<head>");
				out.println(htmlHeaders);
				out.println("<body>");
				out.println(navigationBar);
				
				
				
				String scoreArea = "<div class=\"card border-secondary\" style=\"margin:0 auto;float: none;width:30%;\">";  
				String scoreHeader = "<div class=\"card-header bg-info text-white\"><h5>Score card</h5></div>";
				String scoreBody = "<div class=\"card-body\">";
				String scoreCard = "<p><b>Score:</b> "+ score +"</p>";
				String totalMarks = "<p><b>Maximum Marks:</b> "+ total +"</p>";
				String totalQuestions = "<p><b>Total questions:</b> "+ n + "</p>";
				String correctAnswers = "<p><b>Correct answers:</b> </p>";
				String incorrectAnswers = "<p><b>Incorrect answers:</b> </p>";
				String notAnswered = "<p><b>Not answered:</b> </p>";
				String br ="<br>";
				
				String rank = "<p><b>Rank:</b> </p>\r\n";
				String averageScore = "<p><b>Average score: </b></p>\r\n";
				String highestScore = "<p><b>Highest score: </b></p>\r\n";
				String lowesetScore = "</p><b>Lowest score: </b></p>\r\n"; 
				String closeScoreBodyAndArea = "</div></div>";
				
				out.println(scoreArea+scoreHeader+scoreBody+scoreCard+totalMarks+totalQuestions+correctAnswers+incorrectAnswers+notAnswered+br+rank+averageScore+highestScore+lowesetScore+closeScoreBodyAndArea);
				
				out.println("</body></div><html>");
				
				
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
	