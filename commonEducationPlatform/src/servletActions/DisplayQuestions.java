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

@SuppressWarnings("serial")
public class DisplayQuestions extends HttpServlet{

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
			//int loginID=new DBConnection().getLoginID(username);
			if(!sessionID.equals(cookieValue))
			{
				out.print("<html><body>User not correctly authenticated</body></html>");
			}
			else 
			{
				//String testID = request.getParameter("testID");
				String testID = (String) request.getAttribute("testID");
				TestCreators tc = new TestCreators();
				int numberOfQuestions = tc.numberOfQuestionsInQuestionsTable(testID);
				String[] questionIDs = tc.getQuestionIDs(testID);
				
				String studentID = (String) request.getAttribute("studentID");
				/*
				Misc misc = new Misc();
				String questionID = misc.getRandomQuestionID(questionIDs);
				*/
				Object qNo = request.getAttribute("questionNumber");
				int questionNumber = 0;
				if(qNo == null)
				{
					questionNumber = 1;
				}
				else 
				{
					questionNumber = Integer.parseInt(qNo+"");
				}
				System.out.println("Question number: "+questionNumber);
				
				
				String questionID = questionIDs[questionNumber-1];
				System.out.println("QuestionID is: "+questionID);
				String hiddenQuestionID = "<input type=\"hidden\" name=\"questionID\" value=\""+questionID+"\">";
				String hiddenStudentID = "<input type=\"hidden\" name=\"studentID\" value=\""+studentID+"\">";			
				String hiddenTestID = "<input type=\"hidden\" name=\"testID\" value=\""+testID+"\">";
				String hiddenquestionNumber = "<input type=\"hidden\" name=\"questionNumber\" value=\""+questionNumber+"\">";
				
				int n = tc.getNumberOfOptionsInAQuestion(questionID);
				String[] options = new String[n];
				
				for(int i=0;i<n;i++)
				{
					options[i] = tc.getOption(questionID, (i+1)+"");
				}
				
				String questionText = tc.getQuestionText(questionID);
				String questionType = tc.getTypeOfQuestion(questionID);
				String positiveMarks = tc.getPositiveMarks(questionID);
				String negativeMarks = tc.getNegativeMarks(questionID);
				
				
				String html = "<html><head><script src=\"javascript\timer.js\"></script></head>";
				String body = "<body>";
				String timer = "<div>Time left = <span id=\"Time left\"></span></div>";
				String form = "<form action=\"SubmitQuestion\" method=\"Post\" id=\"questionPaper\">";
				String questionNumberlabel = "<b><label>Question "+questionNumber+" of "+numberOfQuestions+"</label></b>";
				String questionTextLabel = "<label>"+questionText+"</label>";
				String positiveMarksLabel = "<label> Correct answer: +"+positiveMarks+"</label>";
				String negativeMarksLabel = "<label> Incorrect answer: -"+negativeMarks+"</label>";
				String previousButton = "<input class=\"lhs\" type=\"submit\" name=\"save\" value=\"To Previous Question\">";
				String nextButton="<input class=\"rhs\" type=\"submit\" name=\"save\" value=\"To Next Question\">";
				String submit="<input class=\"rhs\" type=\"submit\" name=\"save\" value=\"Submit test\">";
				
				String br = "<br>";
				String closeForm = "</form>";
				String closeBody = "</body>";
				String closehtml = "</html>";
				String rb1 = "";
				
				/*
				Object answer2 = request.getAttribute("answer");
				System.out.println(answer2);
				String a = "";
				*/
				String answer2 = tc.getAnswer(studentID, questionID);
				String a = "";
				if(answer2 == null)
				{
					a = "A";
				}
				else
				{
					a = (String) answer2;
				}
					
				System.out.println("Answer: "+a);
				
				if(questionType.equals("1"))
				{
					rb1 = "<input type=\"radio\" name=\"options\" class=\"radio\" value=\"true\" style=\"vertical-align: top;\">True<br><input type=\"radio\" name=\"options\" class=\"radio\" value=\"false\" style=\"vertical-align: top;\">False</div>";
				}
				else if(questionType.equals("2"))
				{
					for (int i=0;i<n;i++)
					{
						if(a.equals((i+1)+""))
							rb1 = rb1 + "<input type=\"radio\" name=\"options\" checked=\"checked\" value=\""+(i+1)+"\">"+options[i]+"<br>";
						else
							rb1 = rb1 + "<input type=\"radio\" name=\"options\" value=\""+(i+1)+"\">"+options[i]+"<br>";
					}
				}
				else if(questionType.equals("3"))
				{
					for (int i=0;i<n;i++)
					{
						rb1 = rb1 + "<input type=\"checkbox\" name=\"options\" value=\""+(i+1)+"\">"+options[i]+"<br>";
					}
				}
				
				if(questionNumber == 1)
					out.print(html+body+timer+form+hiddenQuestionID+hiddenStudentID+hiddenTestID+hiddenquestionNumber+questionNumberlabel+br+questionTextLabel+br+positiveMarksLabel+br+negativeMarksLabel+br+rb1+br+nextButton+br+submit+closeForm+closeBody+closehtml);
				else if(questionNumber == numberOfQuestions)
					out.print(html+body+timer+form+hiddenQuestionID+hiddenStudentID+hiddenTestID+hiddenquestionNumber+questionNumberlabel+br+questionTextLabel+br+positiveMarksLabel+br+negativeMarksLabel+br+rb1+br+previousButton+br+br+submit+closeForm+closeBody+closehtml);
				else
					out.print(html+body+timer+form+hiddenQuestionID+hiddenStudentID+hiddenTestID+hiddenquestionNumber+questionNumberlabel+br+questionTextLabel+br+positiveMarksLabel+br+negativeMarksLabel+br+rb1+br+previousButton+br+nextButton+br+submit+closeForm+closeBody+closehtml);
				
		
			}
		}catch(Exception e)
		{
			out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();	
		}
	}
}
