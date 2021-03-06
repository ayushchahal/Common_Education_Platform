package servletActions;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class EditQuestions2 extends HttpServlet{

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
				int noq = (int) request.getAttribute("numberOfQuestions");
				Object questionNumber = request.getAttribute("questionNumber");
				Object testID = request.getAttribute("testID");
				//TestCreators tc = new TestCreators();
				//String[] questionIDs = tc.getQuestionIDs(testID.toString());				
				
				String html1="<html>\r\n" + 
						"	<head>\r\n" + 
						"		<meta charset=\"ISO-8859-1\">\r\n" + 
						"		<title>Create a test</title>\r\n" + 
						"		<link rel=\"stylesheet\" href=\"questions.css\">\r\n" + 
						"<script src=\"addRemoveOptions.js\"></script>"+
						"<script src=\"changeQType.js\"></script>"+
						"	</head>\r\n" + 
						"<body>";
				
				String tid="<input type=\"hidden\" id=\"tid\" name=\"tid\" style=\"display:none;\" value=\""+testID+"\">";
				String qno="<input type=\"hidden\" id=\"qno\" name=\"qno\" style=\"display:none;\" value=\""+questionNumber+"\">";
				String noq1="<input type=\"hidden\" id=\"noq\" name=\"noq\" style=\"display:none;\" value=\""+noq+"\">";
				String block="<div id=\"content\" class=\"block\">";
				String form="<form action=\"SaveTest\" method=\"Post\" id=\"questionsForm\">";
				String textArea="<label style>Question "+questionNumber+" of "+noq+"</label><br>"+"<textarea id=\"question\" name=\"question\">"+"</textarea><br><br>";
				String dropdown="<div class=\"lhs\"><label style>Type of Question:</label><br><select name=\"qType\" id=\"qType\" onChange=\"changeQType()\"><option value=\"1\">True/False</option><option value=\"2\" selected>Multiple choice</option><option value=\"3\">Multiple response</option></select></div>";
				//String fillInTheBlanks="<option value=\"4\">Fill in the blank</option></select></div>";
				String positiveMarks="<div class=\"rhs\"><label style>Positive Marks:</label><input id=\"pmarks\" name=\"pmarks\" size=\"3\" type=\"number\" minimun=\"0\" step=\"0.01\" value=\"1\"></div><br><br>";
				String negativeMarks="<div class=\"rhs\"><label style>Negative Marks:</label><input id=\"nmarks\" name=\"nmarks\" size=\"3\" type=\"number\" minimun=\"0\" step=\"0.01\" value=\"0\"></div><br><br><br>";
				
				String label="<label>Enter the options, and mark which answer is correct</label><br>";
				String TandFdiv = "<br><div id=\"TandF\" style=\"display:none;\">";
				String TandFoptions = "<div id=\"option-1\" name=\"option-1\"><input type=\"radio\" name=\"options\" class=\"radio\" value=\"true\" style=\"vertical-align: top;\">True</div><br><div id=\"option-2\" name=\"option-2\"><input type=\"radio\" name=\"options\" class=\"radio\" value=\"false\" style=\"vertical-align: top;\">False</div>";
				String closeTandFdiv="</div>";
				
				String optionDiv="<div id=\"multipleChoiceQuestions\"><div class=\"option\" name=\"mcqoptions\">";
				String options="<div id=\"option-1\"><br><input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"options\"></textarea><br></div><div id=\"option-2\"><br><input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-2\"></textarea><br></div><div id=\"option-3\"><br><input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-3\"></textarea><br></div><div id=\"option-4\"><br><input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-4\"></textarea><br></div>";
				String closeDiv="</div></div>";
				
				String MAQDiv = "<div id=\"MAQ\" style=\"display:none;\"><div class=\"MAQoption\">";
				String MAQOptions = "<div id=\"MAQoption-1\"><br><input type=\"checkbox\" name=\"options\" value=\"1\" class=\"checkbox\"><textarea onclick=\"clearParagraph()\" class=\"options\" name=\"MAQoption-1\"></textarea><br></div><div id=\"MAQoption-2\"><br><input type=\"checkbox\" name=\"options\" value=\"2\" class=\"checkbox\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"MAQoption-2\"></textarea><br></div><div id=\"MAQoption-3\"><br><input type=\"checkbox\" name=\"options\" value=\"3\" class=\"checkbox\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"MAQoption-3\"></textarea><br></div><div id=\"MAQoption-4\"><br><input type=\"checkbox\" name=\"options\" value=\"4\" class=\"checkbox\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"MAQoption-4\"></textarea><br></div>";
				String closeMAQDiv="</div></div>";
				
				String p="<p id=\"optionsValidator\"></p>";
				String linkDiv="<div id=\"link\">";
				String addOption="<br><a onclick=\"addOption()\" href=\"#\">Add Option</a>";
				String slash="<label> / </label>";
				String removeOption="<a onclick=\"removeOption()\" href=\"#\">Remove Option</a>";
				String linkDivClose="</div>";
				String previousButton = "<br><br><input class=\"lhs\" type=\"submit\" name=\"save\" value=\"To Previous Question\">";
				
				String button1="<input class=\"lhs\" type=\"submit\" name=\"save\" value=\"Save and Exit\">";
				//String reset="<input class=\"center\" type=\"reset\" name=\"reset\" value=\"Reset\">";
				String button2="<input class=\"rhs\" type=\"submit\" name=\"save\" value=\"To Next Question\">";
				String button3="<input class=\"rhs\" type=\"submit\" name=\"save\" value=\"Complete Questions Submission\">";
				String html2="</form></div></body></html>";
				
				if((int)questionNumber > noq)
					out.println("Test created");
				else if((int)questionNumber == noq)
					out.println(html1+block+form+tid+qno+noq1+textArea+dropdown+positiveMarks+negativeMarks+label+TandFdiv+TandFoptions+closeTandFdiv+optionDiv+options+closeDiv+MAQDiv+MAQOptions+closeMAQDiv+p+linkDiv+addOption+slash+removeOption+linkDivClose+previousButton+button1+button3+html2);
				else
					out.println(html1+block+form+tid+qno+noq1+textArea+dropdown+positiveMarks+negativeMarks+label+TandFdiv+TandFoptions+closeTandFdiv+optionDiv+options+closeDiv+MAQDiv+MAQOptions+closeMAQDiv+p+linkDiv+addOption+slash+removeOption+linkDivClose+previousButton+button1+button2+html2);
				
			}
		}catch(Exception e)
		{
			out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();
		}
	}

}
