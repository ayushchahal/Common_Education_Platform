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
public class CreateQuestions extends HttpServlet{

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
				Object questionID = request.getAttribute("questionID");
				String alreadyPresentLabels = "";
				int flag = 0;
				String questionText = "";
				String typeOfQuestion = "";
				String pMarks = "";
				String nMarks = "";
				String answer = "";
				String o1 = "";
				String o2 = "";
				String o3 = "";
				String o4 = "";
				String o5 = "";
				String o6 = "";
				int noOfOptionsInAQuestion = 0;
				String timeLimitType = "";
				String timeLimitValue = ""; 
				TestCreators tc = new TestCreators();
				timeLimitType = tc.getTimeLimitType(""+testID);
				String br = "<br><br>";
				String inputTopic = 
					"<div class=\"input-group input-group-default\" style=\"width:100%\">"
				+ 	"	<div class=\"input-group-prepend\" style=\"width:100%\">"
				+ 	"		<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">"
				+ 	"			 Select topic:"
				+ 	"		</span>"
				+ 	"		<input id=\"topic\" type=\"text\" name=\"topic\" style=\"width:100%\">"
				+ 	"	</div>"
				+ 	"</div>";
				
				String topicValidation = "<div id=\"validationTopics\" style=\"display:none\">\r\n" + 
						"<p style=\"color:red\">Please specify the topic name(s) for better reporting.</p>\r\n" + 
						"</div>";
				
			
				if(questionID == null)
				{
					flag = 0;
				}
				else
				{
					questionText = tc.getQuestionText(""+questionID);
					typeOfQuestion = tc.getTypeOfQuestion(""+questionID);
					pMarks = tc.getPositiveMarks(""+questionID);
					nMarks = tc.getNegativeMarks(""+questionID);
					timeLimitValue = tc.getTimeLimitValue(""+questionID);
					answer = tc.getAnswer(""+questionID);
					o1 = tc.getOption(""+questionID, "1");
					o2 = tc.getOption(""+questionID, "2");
					o3 = tc.getOption(""+questionID, "3");
					o4 = tc.getOption(""+questionID, "4");
					o5 = tc.getOption(""+questionID, "5");
					o6 = tc.getOption(""+questionID, "6");
					noOfOptionsInAQuestion = tc.getNumberOfOptionsInAQuestion(""+questionID);
					alreadyPresentLabels = tc.getLabelsInAQuestion(""+questionID);
					flag = 1;
				}
				
				String header = new Misc().HtmlHeader();
				
				String breadCrumb = "<nav aria-label=\"breadcrumb\">  <ol class=\"breadcrumb\">    <li class=\"breadcrumb-item\"><a href=\"/CoachingDashboard\">Home</a></li>    <li class=\"breadcrumb-item\" aria-current=\"page\">\r\n" + 
						"    \r\n" + 
						"    <a href=\"/DisplayCreateTestPage\">Create Test</a></li>\r\n" + 
						"<li class=\"breadcrumb-item active\" aria-current=\"page\">Create Questions</li>  </ol></nav><br>";
				
				
				String cardBorderDiv = "<div class=\"card border-secondary\" style=\"margin-left:5%;width:70%;\">";
				
				String sss = "<script src=\"http://js.nicedit.com/nicEdit-latest.js\" type=\"text/javascript\"></script>"
						+ "<script type=\"text/javascript\">\r\n" + 
						"//<![CDATA[\r\n" + 
						"bkLib.onDomLoaded(function() {\r\n" + 
						"    nicEditors.editors.push(\r\n" + 
						"        new nicEditor().panelInstance(\r\n" + 
						"            document.getElementById('question')\r\n" + 
						"        )\r\n" + 
						"    );\r\n" + 
						"});\r\n" + 
						"//]]>\r\n" + 
						"</script>";
				
				sss = 	"<script src=\"https://code.jquery.com/jquery-3.3.1.js\" integrity=\"sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60=\" crossorigin=\"anonymous\"></script>" + sss;
				
				sss = sss + 
					"<link rel=\"stylesheet\" type=\"text/css\" href=\"http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css\"/>\r\n" + 
					"\r\n" + 
					" \r\n" + 
					"<link rel=\"stylesheet\" type=\"text/css\" href=\"/style1.css\"/>\r\n" + 
					"<script src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js\"></script> \r\n" + 
					"<script src=\"http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js\"></script>\r\n" + 
					"<script src=\"/jquery.autocomplete.multiselect.js\"></script>\r\n"+
					"<script src=\"/javascript/insertNames.js\"></script>"+
					"<script src=\"/javascript/loadTopics.js\"></script>"; 
					
				
				sss = sss + "<script src=\"/javascript/clientSideValidationForCreatingTest.js\"></script>"; 
				
					
				
				String[] labels = tc.getAllTopics();
				String labels2 = "";
				for(int i = 0;i<labels.length;i++)
				{
					if(i==(labels.length-1))
					{
						labels2 = labels2 + "\""+labels[i]+"\"";
					}
					else 
					{
						labels2 = labels2 + "\""+labels[i]+"\""+",";	
					}
				}
				
				
				sss = sss + "<script type=\"text/javascript\">\r\n" + 
						"	$(function(){\r\n" + 
						"		var availableTags = [" + labels2 + 
						"		];" + 
						"		$('#topic').autocomplete({\r\n" + 
						"			source: availableTags,\r\n" + 
						"			multiselect: true\r\n" + 
						"		});\r\n" + 
						"	});\r\n" + 
						"</script>";
				
				
				String bootstrap = 
						"<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">\r\n" + 
						"<link rel=\"stylesheet\" href=\"//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css\">" +
						"<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">" +
						"<script src=\"/javascript/changeSize.js\"></script>";
				
				String html1=
						"<html>" + 
						"	<head>" + 
						"		<meta charset=\"ISO-8859-1\">" + 
						"		<title>Create a test</title>" + 
						"		<link rel=\"stylesheet\" href=\"questions.css\">" + 
						"		<script src=\"addRemoveOptions.js\"></script>"+
						"		<script src=\"changeQType.js\"></script>" + bootstrap + sss +
						"	</head>" + 
						"<body onload=\"changeSize();loadTopics()\" >";
				
				String cardHeader = 
						"<div class=\"card-header bg-dark text-white\">"
				+		"	<h5>Question "+questionNumber+" of "+ noq +"</h5>" 
				+		"</div>";
				
				String textArea = 
						"<textarea id=\"question\" name=\"question\" style=\"width:100%\"></textarea>";
				
				String validationQText = "<div id=\"validationQText\" style=\"display:none\">\r\n" + 
						"    <p style=\"color:red\">Please specify the question text.</p>\r\n" + 
						"</div><br>";
				
				String tid="<input type=\"hidden\" id=\"tid\" name=\"tid\" style=\"display:none;\" value=\""+testID+"\">";
				String qno="<input type=\"hidden\" id=\"qno\" name=\"qno\" style=\"display:none;\" value=\""+questionNumber+"\">";
				String noq1="<input type=\"hidden\" id=\"noq\" name=\"noq\" style=\"display:none;\" value=\""+noq+"\">"+
						"<input type=\"hidden\" id=\"alreadyPresentLabels\" name=\"labels\" style=\"display:none;\" value=\""+0+"\">";
				
				//String block="<div id=\"content\" class=\"block\">";
				
				String form=
						"<div class=\"card-body\">"
					+	"<form action=\"SaveTest\" method=\"Post\" id=\"questionsForm\" onsubmit=\"return clientSideValidations();\">";
				
				
				String typeOfQuestionDropdown = 
						"<div class=\"input-group input-group-default\">" 
				+		"	<div class=\"input-group-prepend\">"
				+		"		<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">"
				+ 		"			Type of Question:"
				+ 		"		</span>"
				+ 		"		<select name=\"qType\" id=\"qType\" onChange=\"changeQType()\">"
				+ 		"			<option value=\"1\">True/False</option>"
				+ 		"			<option value=\"2\" selected>Multiple choice</option>"
				+ 		"			<option value=\"3\">Multiple response</option>"
				+ 		"		</select>"
				+ 		"	</div>"
				+ 		"</div>"
				+ 		"<br>";
				
				String positiveMarks=
						"<div class=\"input-group input-group-default\">"
				+		"	<div class=\"input-group-prepend\">" 
				+		"		<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">"
				+ 		"			Positive Marks:"
				+ 		"		</span>"
				+ 		"		<input id=\"pmarks\" name=\"pmarks\" size=\"3\" type=\"number\" minimun=\"0\" step=\"0.5\" value=\"1\" style=\"text-align: right; width:20%\">"
				+ 		"	</div>"
				+ 		"</div>";
				
				
				String positiveMarksValidator = "<div id=\"validationPMarks\" style=\"display:none\">\r\n" 
				+ 	"<p style=\"color:red\">Please specify the Positive Marks.</p>\r\n" 
				+ 	"</div>"
				+	"<br>";
				
				String negativeMarks=
						"<div class=\"input-group input-group-default\">"
				+ 		"	<div class=\"input-group-prepend\">" 
				+		"		<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">"
				+ 		"			Negative Marks:"
				+ 		"		</span>"
				+ 		"		<input id=\"nmarks\" name=\"nmarks\" size=\"3\" type=\"number\" minimun=\"0\" step=\"0.5\" value=\"0\" style=\"text-align: right; width:20%\">"
				+ 		"	</div>"
				+ 		"</div>";
				
				String negativeMarksValidator = "<div id=\"validationNMarks\" style=\"display:none\">\r\n" 
						+ 	"<p style=\"color:red\">Please specify the Negative Marks. Please enter 0 if you do not want any negative marking.</p>\r\n" 
						+ 	"</div>"
						+	"<br>";
				
				String timeLimit = "";
				if(timeLimitType.equals("1"))
				{
					timeLimit=
							"<div class=\"input-group input-group-default\">" 
					+		"	<div class=\"input-group-prepend\">"  
					+		"		<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">"
					+		"			Time Limit (in seconds):"
					+ 		"		</span>"
					+ 		"		<input id=\"timeLimit\" name=\"timeLimit\" size=\"3\" type=\"number\" minimun=\"0\" value=\"0\" style=\"text-align: right; width:20%\">"
					+ 		"	</div>"
					+ 		"</div>";
				}
				else
				{
				 timeLimit = "<br>";
				}
				
				String timeLimitValidator = "<div id=\"validationTimeLimit\" style=\"display:none\">\r\n" + 
						"<p id='limitValidation' style=\"color:red\">Please specify the time limit in seconds.</p>\r\n" + 
						"<p id='impracticalTimeLimit' style=\"color:red;display:none\">Time Limit of less than 5 seconds is very impractical.</p>\r\n" + 
						"</div><br>";
				
				
				String label=
						"<label style=\"width: 90%\">"
					+ 	"	<h6 class=\"card-header border\">"
					+	"		Enter the options, and mark the correct answer(s)"
					+ 	"	</h6>" + 
						"</label>";
				
				String TandFdiv = 
						"<br>"
					+	"<div id=\"TandF\" style=\"display:none;\">";
				
				String TandFoptions = 
								"<div id=\"option-1\" name=\"option-1\">"+
								"<label style=\"width: 90%\">" + 
								"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
								"		<div class=\"input-group-prepend\">\r\n" + 
								"			<div class=\"input-group-text\">\r\n" + 
								"				<input type=\"radio\" name=\"options\" value=\"true\" class=\"radio\">" + 
								"			</div>" + 
								"		</div>" + 
								"		<h4 class=\"form-control\">True</h4>"+
								"	</div>"+
								"</label>" + 
								"</div>"
						+
								"<div id=\"option-2\" name=\"option-2\">"+
								"<label style=\"width: 90%\">" + 
								"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
								"		<div class=\"input-group-prepend\">" + 
								"			<div class=\"input-group-text\">" + 
								"				<input type=\"radio\" name=\"options\" value=\"true\" class=\"radio\">" + 
								"			</div>" + 
								"		</div>" + 
								"		<h4 class=\"form-control\">False</h4>"+
								"	</div>"+
								"</label>"+
								"</div>";
				
				String closeTandFdiv="</div>";
				
				String optionDiv=
						"<div id=\"multipleChoiceQuestions\">"
					+	"	<div class=\"option\" name=\"mcqoptions\">";
				
				String options = "<div id=\"option-1\">"+
						"<label style=\"width: 90%\">" + 
						"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
						"		<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
						"			<div class=\"input-group-text\">\r\n" + 
						"				<input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\">" + 
						"			</div>" + 
						"		<textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\"></textarea>"+
						"		</div>" + 
						"	</div>"+
						"</label>" + 
						"</div>"
				+
						"<div id=\"option-2\">"+
						"	<label style=\"width: 90%\">" + 
						"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
						"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
						"				<div class=\"input-group-text\">\r\n" + 
						"					<input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\">" + 
						"				</div>" + 
						"			<textarea onclick=\"clearParagraph()\" name=\"option-2\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\"></textarea>"+
						"			</div>" + 
						"		</div>"+
						"	</label>" + 
						"</div>"
				+
						"<div id=\"option-3\">"+
						"	<label style=\"width: 90%\">" + 
						"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
						"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
						"				<div class=\"input-group-text\">\r\n" + 
						"					<input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\">" + 
						"				</div>" + 
						"			<textarea onclick=\"clearParagraph()\" name=\"option-3\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\"></textarea>"+
						"			</div>" + 
						"		</div>"+
						"	</label>" + 
						"</div>"
				+
						"<div id=\"option-4\">"+
						"	<label style=\"width: 90%\">" + 
						"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
						"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
						"				<div class=\"input-group-text\">\r\n" + 
						"					<input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\">" + 
						"				</div>" + 
						"			<textarea onclick=\"clearParagraph()\" name=\"option-4\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\"></textarea>"+
						"			</div>" + 
						"		</div>"+
						"	</label>" + 
						"</div>";
				
				String closeDiv="</div></div>";
				
				String MAQDiv = 
						"<div id=\"MAQ\" style=\"display:none;\">"
					+ 	"	<div class=\"MAQoption\">";
				
				String MAQOptions = 
						"<div id=\"MAQoption-1\">"+
						"	<label style=\"width: 90%\">" + 
						"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
						"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\" >\r\n" + 
						"				<div class=\"input-group-text\">\r\n" + 
						"					<input type=\"checkbox\" name=\"options\" value=\"1\" class=\"checkbox\">" + 
						"				</div>" + 
						"			<textarea style=\"width:100%;height:100%\" onclick=\"clearParagraph()\" name=\"MAQoption-1\" class=\"options\"></textarea>"+
						
						"			</div>" + 
						"		</div>"+
						"	</label>" + 
						"</div>"
				+
						"<div id=\"MAQoption-2\">"+
						"	<label style=\"width: 90%\">" + 
						"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
						"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
						"				<div class=\"input-group-text\">\r\n" + 
						"					<input type=\"checkbox\" name=\"options\" value=\"2\" class=\"checkbox\">" + 
						"				</div>" + 
						"			<textarea style=\"width:100%;height:100%\" onclick=\"clearParagraph()\" name=\"MAQoption-2\" class=\"options\"></textarea>"+
						
						"			</div>" + 
						"		</div>"+
						"	</label>" + 
						"</div>"
				+
						"<div id=\"MAQoption-3\">"+
						"	<label style=\"width: 90%\">" + 
						"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
						"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
						"				<div class=\"input-group-text\">\r\n" + 
						"					<input type=\"checkbox\" name=\"options\" value=\"3\" class=\"checkbox\">" + 
						"				</div>" + 
						"			<textarea style=\"width:100%;height:100%\" onclick=\"clearParagraph()\" name=\"MAQoption-3\" class=\"options\"></textarea>"+
						
						"			</div>" + 
						"		</div>"+
						"	</label>" + 
						"</div>"
				+
						"<div id=\"MAQoption-4\">"+
						"	<label style=\"width: 90%\">" + 
						"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
						"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
						"				<div class=\"input-group-text\">\r\n" + 
						"					<input type=\"checkbox\" name=\"options\" value=\"4\" class=\"checkbox\">" + 
						"				</div>" + 
						"			<textarea style=\"width:100%;height:100%\" onclick=\"clearParagraph()\" name=\"MAQoption-4\" class=\"options\"></textarea>"+
						
						"			</div>" + 
						"		</div>"+
						"	</label>" + 
						"</div>";
				
				String closeMAQDiv="</div></div>";
				
				String p = 
						"<p id=\"checkOptions\" style=\"display:none;color:red\">" 
					+	"Please enter data in all the options"
					+ 	"</p>"
					+	"<p id=\"checkMRQOptions\" style=\"display:none;color:red\">" 
					+	"Please mark an answer."
					+ 	"</p>"
					+	"<p id=\"optionsValidator\"></p>";
				
				String linkDiv="<div id=\"link\">";
				String addOption="<br><a onclick=\"addOption(); return false;\" href=\"#\">Add Option</a>";
				String slash="<label> / </label>";
				String removeOption="<a onclick=\"removeOption(); return false;\" href=\"#\">Remove Option</a>";
				String linkDivClose="</div>";
				
				if(typeOfQuestion.equals("1"))
				{
					linkDiv = "";
					addOption = "";
					slash = "";
					removeOption = "";
					linkDivClose = "";
				}
				
				String previousButton = "<br><br><input class=\"btn btn-primary float-left\" type=\"submit\" name=\"save\" id=\"prev\" value=\"To Previous Question\" style=\"margin-left:2%\" onclick=\"insertNames()\">";
				String nextbutton="<input class=\"btn btn-primary float-right\" type=\"submit\" name=\"save\" value=\"To Next Question\" style=\"margin-left:2%\" onclick=\"insertNames()\">";
						
				String saveAndExit="<input class=\"btn btn-success float-left\" type=\"submit\" name=\"save\" value=\"Save & Exit\" style=\"margin-left:2%\" onclick=\"insertNames()\">";
				
				String completeQuestionSubmission="<input class=\"btn btn-success float-right\" type=\"submit\" name=\"save\" value=\"Complete Questions Submission\" style=\"margin-left:2%\" onclick=\"insertNames()\">";
				
				String html2="</form></div></body></html>";
				
				if(flag == 0)
				{
					if((int)questionNumber > noq)
						out.println("Test created");
					else if((int)questionNumber == noq)
					{
						if((int)questionNumber == 1)
							out.println(html1+header+breadCrumb+cardBorderDiv+cardHeader+form+tid+qno+noq1+textArea+validationQText+inputTopic+topicValidation+"<br>"+typeOfQuestionDropdown+positiveMarks+positiveMarksValidator+negativeMarks+negativeMarksValidator+timeLimit+timeLimitValidator+label+TandFdiv+TandFoptions+closeTandFdiv+optionDiv+options+closeDiv+MAQDiv+MAQOptions+closeMAQDiv+p+linkDiv+addOption+slash+removeOption+linkDivClose+saveAndExit+completeQuestionSubmission+html2);
						else
							out.println(html1+header+breadCrumb+cardBorderDiv+cardHeader+form+tid+qno+noq1+textArea+validationQText+inputTopic+topicValidation+"<br>"+typeOfQuestionDropdown+positiveMarks+positiveMarksValidator+negativeMarks+negativeMarksValidator+timeLimit+timeLimitValidator+label+TandFdiv+TandFoptions+closeTandFdiv+optionDiv+options+closeDiv+MAQDiv+MAQOptions+closeMAQDiv+p+linkDiv+addOption+slash+removeOption+linkDivClose+previousButton+br+saveAndExit+completeQuestionSubmission+html2);
					}	
					else
					{
						if((int)questionNumber == 1)
							out.println(html1+header+breadCrumb+cardBorderDiv+cardHeader+form+tid+qno+noq1+textArea+validationQText+inputTopic+topicValidation+"<br>"+typeOfQuestionDropdown+positiveMarks+positiveMarksValidator+negativeMarks+negativeMarksValidator+timeLimit+timeLimitValidator+label+TandFdiv+TandFoptions+closeTandFdiv+optionDiv+options+closeDiv+MAQDiv+MAQOptions+closeMAQDiv+p+linkDiv+addOption+slash+removeOption+linkDivClose+nextbutton+br+saveAndExit+html2);
						else
							out.println(html1+header+breadCrumb+cardBorderDiv+cardHeader+form+tid+qno+noq1+textArea+validationQText+inputTopic+topicValidation+"<br>"+typeOfQuestionDropdown+positiveMarks+positiveMarksValidator+negativeMarks+negativeMarksValidator+timeLimit+timeLimitValidator+label+TandFdiv+TandFoptions+closeTandFdiv+optionDiv+options+closeDiv+MAQDiv+MAQOptions+closeMAQDiv+p+linkDiv+addOption+slash+removeOption+linkDivClose+previousButton+nextbutton+br+saveAndExit+html2);
					}
				}
				else
				{
					textArea=
							"<textarea id=\"question\" name=\"question\" style=\"width:100%\">"
					+			questionText
					+		"</textarea><br>";
					
					if(typeOfQuestion.equals("1"))
					{
						typeOfQuestionDropdown = 
								"<div class=\"input-group input-group-default\">" 
						+		"	<div class=\"input-group-prepend\">"
						+		"		<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">"
						+ 		"			Type of Question:"
						+ 		"		</span>"
						+ 		"		<select name=\"qType\" id=\"qType\" onChange=\"changeQType()\">"
						+ 		"			<option value=\"1\" selected>True/False</option>"
						+ 		"			<option value=\"2\">Multiple choice</option>"
						+ 		"			<option value=\"3\">Multiple response</option>"
						+ 		"		</select>"
						+ 		"	</div>"
						+ 		"</div>"
						+ 		"<br>";
					}
					else if(typeOfQuestion.equals("2"))
					{
						typeOfQuestionDropdown = 
								"<div class=\"input-group input-group-default\">" 
						+		"	<div class=\"input-group-prepend\">"
						+		"		<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">"
						+ 		"			Type of Question:"
						+ 		"		</span>"
						+ 		"		<select name=\"qType\" id=\"qType\" onChange=\"changeQType()\">"
						+ 		"			<option value=\"1\">True/False</option>"
						+ 		"			<option value=\"2\" selected>Multiple choice</option>"
						+ 		"			<option value=\"3\">Multiple response</option>"
						+ 		"		</select>"
						+ 		"	</div>"
						+ 		"</div>"
						+ 		"<br>";
					}
					else if(typeOfQuestion.equals("3"))
					{
						typeOfQuestionDropdown = 
								"<div class=\"input-group input-group-default\">" 
						+		"	<div class=\"input-group-prepend\">"
						+		"		<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">"
						+ 		"			Type of Question:"
						+ 		"		</span>"
						+ 		"		<select name=\"qType\" id=\"qType\" onChange=\"changeQType()\">"
						+ 		"			<option value=\"1\">True/False</option>"
						+ 		"			<option value=\"2\">Multiple choice</option>"
						+ 		"			<option value=\"3\" selected>Multiple response</option>"
						+ 		"		</select>"
						+ 		"	</div>"
						+ 		"</div>"
						+ 		"<br>";
					}
					
					positiveMarks=
							"<div class=\"input-group input-group-default\">"
					+		"	<div class=\"input-group-prepend\">" 
					+		"		<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">"
					+ 		"			Positive Marks:"
					+ 		"		</span>"
					+ 		"		<input id=\"pmarks\" name=\"pmarks\" size=\"3\" type=\"number\" minimun=\"0\" step=\"0.5\" value=\""+pMarks+"\" style=\"text-align: right; width:20%\">"
					+ 		"	</div>"
					+ 		"</div>";
					
					negativeMarks=
							"<div class=\"input-group input-group-default\">"
					+ 		"	<div class=\"input-group-prepend\">" 
					+		"		<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">"
					+ 		"			Negative Marks:"
					+ 		"		</span>"
					+ 		"		<input id=\"nmarks\" name=\"nmarks\" size=\"3\" type=\"number\" minimun=\"0\" step=\"0.5\" value=\""+nMarks+"\" style=\"text-align: right; width:20%\">"
					+ 		"	</div>"
					+ 		"</div>";
					
					
					timeLimit = "";
					
					
					if(timeLimitType.equals("1"))
					{
						timeLimit=
								"<div class=\"input-group input-group-default\">" 
						+		"	<div class=\"input-group-prepend\">"  
						+		"		<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">"
						+		"			Time Limit (in seconds):"
						+ 		"		</span>"
						+ 		"		<input id=\"timeLimit\" name=\"timeLimit\" size=\"3\" type=\"number\" minimun=\"0\" value=\""+timeLimitValue+"\" style=\"text-align: right; width:20%\">"
						+ 		"	</div>"
						+ 		"</div>";
					}
					else
					{
						timeLimit = "<br>";
					}
					
					
					String style1 = "style=\"display:none;\"";
					String style2 = "style=\"display:none;\"";
					String style3 = "style=\"display:none;\"";
					
					if(typeOfQuestion.equals("1"))
					{
						style1="style=\"display:block;\"";
						if(answer.equals("1"))
						{
							TandFoptions = 
									"<div id=\"option-1\" name=\"option-1\">"+
									"<label style=\"width: 90%\">" + 
									"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
									"		<div class=\"input-group-prepend\">\r\n" + 
									"			<div class=\"input-group-text\">\r\n" + 
									"				<input type=\"radio\" name=\"options\" value=\"true\" class=\"radio\" checked=\"checked\">" + 
									"			</div>" + 
									"		</div>" + 
									"		<h4 class=\"form-control\">True</h4>"+
									"	</div>"+
									"</label>" + 
									"</div>"
							+
									"<div id=\"option-2\" name=\"option-2\">"+
									"<label style=\"width: 90%\">" + 
									"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
									"		<div class=\"input-group-prepend\">" + 
									"			<div class=\"input-group-text\">" + 
									"				<input type=\"radio\" name=\"options\" value=\"true\" class=\"radio\">" + 
									"			</div>" + 
									"		</div>" + 
									"		<h4 class=\"form-control\">False</h4>"+
									"	</div>"+
									"</label>"+
									"</div>";
						}
						else
						{
							TandFoptions = 
									"<div id=\"option-1\" name=\"option-1\">"+
									"<label style=\"width: 90%\">" + 
									"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
									"		<div class=\"input-group-prepend\">\r\n" + 
									"			<div class=\"input-group-text\">\r\n" + 
									"				<input type=\"radio\" name=\"options\" value=\"true\" class=\"radio\">" + 
									"			</div>" + 
									"		</div>" + 
									"		<h4 class=\"form-control\">True</h4>"+
									"	</div>"+
									"</label>" + 
									"</div>"
							+
									"<div id=\"option-2\" name=\"option-2\">"+
									"<label style=\"width: 90%\">" + 
									"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
									"		<div class=\"input-group-prepend\">" + 
									"			<div class=\"input-group-text\">" + 
									"				<input type=\"radio\" name=\"options\" value=\"true\" class=\"radio\" checked=\"checked\">" + 
									"			</div>" + 
									"		</div>" + 
									"		<h4 class=\"form-control\">False</h4>"+
									"	</div>"+
									"</label>"+
									"</div>";
						}
					}
					else if(typeOfQuestion.equals("2"))
					{
						style2="style=\"display:block;\"";
						if(noOfOptionsInAQuestion == 2)
						{
							if(answer.equals("1"))
							{
								options = 
								"<div id=\"option-1\">"+
								"<label style=\"width: 90%\">" + 
								"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
								"		<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
								"			<div class=\"input-group-text\">\r\n" + 
								"				<input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\" checked=\"checked\">" + 
								"			</div>" + 
								"		<textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+
										o1 + 
								"		</textarea>"+
								"		</div>" + 
								"	</div>"+
								"</label>" + 
								"</div>"
						+
								"<div id=\"option-2\">"+
								"	<label style=\"width: 90%\">" + 
								"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
								"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
								"				<div class=\"input-group-text\">\r\n" + 
								"					<input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\">" + 
								"				</div>" + 
								"			<textarea onclick=\"clearParagraph()\" name=\"option-2\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+
											o2 +
								"			</textarea>"+
								"			</div>" + 
								"		</div>"+
								"	</label>" + 
								"</div>";
							}
							else if(answer.equals("2"))
							{
								options = 
										"<div id=\"option-1\">"+
										"<label style=\"width: 90%\">" + 
										"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"		<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"			<div class=\"input-group-text\">\r\n" + 
										"				<input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\" >" + 
										"			</div>" + 
										"		<textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+
												o1 + 
										"		</textarea>"+
										"		</div>" + 
										"	</div>"+
										"</label>" + 
										"</div>"
								+
										"<div id=\"option-2\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\" checked=\"checked\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-2\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+
													o2 +
										"			</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>";							
							}
						}
						else if(noOfOptionsInAQuestion == 3)
						{
							if(answer.equals("1"))
							{
								options =
								"<div id=\"option-1\">"+
								"<label style=\"width: 90%\">" + 
								"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
								"		<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
								"			<div class=\"input-group-text\">\r\n" + 
								"				<input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\" checked=\"checked\">" + 
								"			</div>" + 
								"		<textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+
										o1 + 
								"		</textarea>"+
								"		</div>" + 
								"	</div>"+
								"</label>" + 
								"</div>"
						+
								"<div id=\"option-2\">"+
								"	<label style=\"width: 90%\">" + 
								"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
								"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
								"				<div class=\"input-group-text\">\r\n" + 
								"					<input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\">" + 
								"				</div>" + 
								"			<textarea onclick=\"clearParagraph()\" name=\"option-2\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+
											o2 +
								"			</textarea>"+
								"			</div>" + 
								"		</div>"+
								"	</label>" + 
								"</div>"
						+
								"<div id=\"option-3\">"+
								"	<label style=\"width: 90%\">" + 
								"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
								"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
								"				<div class=\"input-group-text\">\r\n" + 
								"					<input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\">" + 
								"				</div>" + 
								"			<textarea onclick=\"clearParagraph()\" name=\"option-3\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+
											o3 + 
								"</textarea>"+
								"			</div>" + 
								"		</div>"+
								"	</label>" + 
								"</div>";
							}
							else if(answer.equals("2"))
							{
								options =
										"<div id=\"option-1\">"+
										"<label style=\"width: 90%\">" + 
										"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"		<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"			<div class=\"input-group-text\">\r\n" + 
										"				<input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\">" + 
										"			</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+
												o1 + 
										"		</textarea>"+
										"		</div>" + 
										"	</div>"+
										"</label>" + 
										"</div>"
								+
										"<div id=\"option-2\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\" checked=\"checked\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-2\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+
													o2 +
										"			</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-3\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-3\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+
													o3 + 
										"			</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>";
							}
							else if(answer.equals("3"))
							{
								options =
										"<div id=\"option-1\">"+
										"<label style=\"width: 90%\">" + 
										"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"		<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"			<div class=\"input-group-text\">\r\n" + 
										"				<input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\">" + 
										"			</div>" + 
										"		<textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+
												o1 + 
										"		</textarea>"+
										"		</div>" + 
										"	</div>"+
										"</label>" + 
										"</div>"
								+
										"<div id=\"option-2\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\" >" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-2\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+
													o2 +
										"			</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-3\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\" checked=\"checked\">" + 
										"				</div>" + 
										"				<textarea onclick=\"clearParagraph()\" name=\"option-3\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+
													o3 + 
										"			</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>";
							}
														
						}
						else if(noOfOptionsInAQuestion == 4)
						{
							if(answer.equals("1"))
							{ 
								options = 
								"<div id=\"option-1\">"+
								"<label style=\"width: 90%\">" + 
								"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
								"		<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
								"			<div class=\"input-group-text\">\r\n" + 
								"				<input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\" checked = \"checked\">" + 
								"			</div>" + 
								"		<textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o1+"</textarea>"+
								"		</div>" + 
								"	</div>"+
								"</label>" + 
								"</div>"
						+
								"<div id=\"option-2\">"+
								"	<label style=\"width: 90%\">" + 
								"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
								"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
								"				<div class=\"input-group-text\">\r\n" + 
								"					<input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\">" + 
								"				</div>" + 
								"			<textarea onclick=\"clearParagraph()\" name=\"option-2\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o2+"</textarea>"+
								"			</div>" + 
								"		</div>"+
								"	</label>" + 
								"</div>"
						+
								"<div id=\"option-3\">"+
								"	<label style=\"width: 90%\">" + 
								"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
								"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
								"				<div class=\"input-group-text\">\r\n" + 
								"					<input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\">" + 
								"				</div>" + 
								"			<textarea onclick=\"clearParagraph()\" name=\"option-3\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o3+"</textarea>"+
								"			</div>" + 
								"		</div>"+
								"	</label>" + 
								"</div>"
						+
								"<div id=\"option-4\">"+
								"	<label style=\"width: 90%\">" + 
								"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
								"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
								"				<div class=\"input-group-text\">\r\n" + 
								"					<input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\">" + 
								"				</div>" + 
								"			<textarea onclick=\"clearParagraph()\" name=\"option-4\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o4+"</textarea>"+
								"			</div>" + 
								"		</div>"+
								"	</label>" + 
								"</div>";
							}
							else if(answer.equals("2"))
							{
								options = 
										"<div id=\"option-1\">"+
										"<label style=\"width: 90%\">" + 
										"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"		<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"			<div class=\"input-group-text\">\r\n" + 
										"				<input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\">" + 
										"			</div>" + 
										"		<textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o1+"</textarea>"+
										"		</div>" + 
										"	</div>"+
										"</label>" + 
										"</div>"
								+
										"<div id=\"option-2\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\" checked=\"checked\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-2\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o2+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-3\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-3\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o3+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-4\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-4\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o4+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>";
							}
							else if(answer.equals("3"))
							{
								options = 
										"<div id=\"option-1\">"+
										"<label style=\"width: 90%\">" + 
										"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"		<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"			<div class=\"input-group-text\">\r\n" + 
										"				<input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\">" + 
										"			</div>" + 
										"		<textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o1+"</textarea>"+
										"		</div>" + 
										"	</div>"+
										"</label>" + 
										"</div>"
								+
										"<div id=\"option-2\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-2\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o2+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-3\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\" checked=\"checked\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-3\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o3+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-4\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-4\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o4+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>";
							
							}
							else if(answer.equals("4"))
							{
								options="<div id=\"option-1\"><br><input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"options\">"+o1+"</textarea><br></div><div id=\"option-2\"><br><input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-2\">"+o2+"</textarea><br></div><div id=\"option-3\"><br><input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-3\">"+o3+"</textarea><br></div><div id=\"option-4\"><br><input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\" checked=\"checked\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-4\">"+o4+"</textarea><br></div>";
								options = 
										"<div id=\"option-1\">"+
										"<label style=\"width: 90%\">" + 
										"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"		<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"			<div class=\"input-group-text\">\r\n" + 
										"				<input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\">" + 
										"			</div>" + 
										"		<textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o1+"</textarea>"+
										"		</div>" + 
										"	</div>"+
										"</label>" + 
										"</div>"
								+
										"<div id=\"option-2\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-2\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o2+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-3\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-3\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o3+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-4\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\" checked=\"checked\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-4\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o4+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>";
							}
						}
						else if(noOfOptionsInAQuestion == 5)
						{
							if(answer.equals("1"))
							{
								options = 
										"<div id=\"option-1\">"+
										"<label style=\"width: 90%\">" + 
										"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"		<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"			<div class=\"input-group-text\">\r\n" + 
										"				<input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\" checked=\"checked\">" + 
										"			</div>" + 
										"		<textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o1+"</textarea>"+
										"		</div>" + 
										"	</div>"+
										"</label>" + 
										"</div>"
								+
										"<div id=\"option-2\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-2\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o2+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-3\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-3\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o3+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-4\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-4\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o4+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-5\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"5\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-5\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o5+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>";
							}
							else if(answer.equals("2"))
							{
								options = 
										"<div id=\"option-1\">"+
										"<label style=\"width: 90%\">" + 
										"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"		<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"			<div class=\"input-group-text\">\r\n" + 
										"				<input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\" >" + 
										"			</div>" + 
										"		<textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o1+"</textarea>"+
										"		</div>" + 
										"	</div>"+
										"</label>" + 
										"</div>"
								+
										"<div id=\"option-2\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\" checked=\"checked\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-2\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o2+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-3\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-3\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o3+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-4\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-4\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o4+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-5\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"5\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-5\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o5+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>";
							}
							else if(answer.equals("3"))
							{
								options = 
										"<div id=\"option-1\">"+
										"<label style=\"width: 90%\">" + 
										"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"		<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"			<div class=\"input-group-text\">\r\n" + 
										"				<input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\">" + 
										"			</div>" + 
										"		<textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o1+"</textarea>"+
										"		</div>" + 
										"	</div>"+
										"</label>" + 
										"</div>"
								+
										"<div id=\"option-2\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\" >" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-2\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o2+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-3\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\" checked=\"checked\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-3\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o3+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-4\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\" >" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-4\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o4+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-5\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"5\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-5\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o5+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>";
							}
							else if(answer.equals("4"))
							{
								options = 
										"<div id=\"option-1\">"+
										"<label style=\"width: 90%\">" + 
										"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"		<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"			<div class=\"input-group-text\">\r\n" + 
										"				<input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\">" + 
										"			</div>" + 
										"		<textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o1+"</textarea>"+
										"		</div>" + 
										"	</div>"+
										"</label>" + 
										"</div>"
								+
										"<div id=\"option-2\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-2\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o2+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-3\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-3\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o3+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-4\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\" checked=\"checked\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-4\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o4+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-5\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"5\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-5\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o5+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>";
							}
							else if(answer.equals("5"))
							{
								options = 
										"<div id=\"option-1\">"+
										"<label style=\"width: 90%\">" + 
										"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"		<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"			<div class=\"input-group-text\">\r\n" + 
										"				<input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\">" + 
										"			</div>" + 
										"		<textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o1+"</textarea>"+
										"		</div>" + 
										"	</div>"+
										"</label>" + 
										"</div>"
								+
										"<div id=\"option-2\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-2\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o2+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-3\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-3\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o3+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-4\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-4\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o4+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-5\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"5\" class=\"radio\" checked=\"checked\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-5\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o5+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>";
							}
						}
						else if(noOfOptionsInAQuestion == 6)
						{
							if(answer.equals("1"))
							{
								options = 
										"<div id=\"option-1\">"+
										"<label style=\"width: 90%\">" + 
										"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"		<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"			<div class=\"input-group-text\">\r\n" + 
										"				<input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\" checked=\"checked\">" + 
										"			</div>" + 
										"		<textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o1+"</textarea>"+
										"		</div>" + 
										"	</div>"+
										"</label>" + 
										"</div>"
								+
										"<div id=\"option-2\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-2\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o2+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-3\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-3\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o3+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-4\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-4\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o4+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-5\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"5\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-5\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o5+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-6\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"6\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-6\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o6+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>";
							}
							else if(answer.equals("2"))
							{
								options = 
										"<div id=\"option-1\">"+
										"<label style=\"width: 90%\">" + 
										"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"		<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"			<div class=\"input-group-text\">\r\n" + 
										"				<input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\" >" + 
										"			</div>" + 
										"		<textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o1+"</textarea>"+
										"		</div>" + 
										"	</div>"+
										"</label>" + 
										"</div>"
								+
										"<div id=\"option-2\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\" checked=\"checked\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-2\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o2+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-3\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-3\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o3+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-4\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-4\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o4+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-5\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"5\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-5\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o5+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-6\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"6\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-6\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o6+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>";
							}
							else if(answer.equals("3"))
							{
								options = 
										"<div id=\"option-1\">"+
										"<label style=\"width: 90%\">" + 
										"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"		<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"			<div class=\"input-group-text\">\r\n" + 
										"				<input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\">" + 
										"			</div>" + 
										"		<textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o1+"</textarea>"+
										"		</div>" + 
										"	</div>"+
										"</label>" + 
										"</div>"
								+
										"<div id=\"option-2\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-2\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o2+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-3\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\" checked=\"checked\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-3\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o3+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-4\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-4\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o4+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-5\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"5\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-5\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o5+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-6\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"6\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-6\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o6+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>";
							}
							else if(answer.equals("4"))
							{
								options = 
										"<div id=\"option-1\">"+
										"<label style=\"width: 90%\">" + 
										"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"		<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"			<div class=\"input-group-text\">\r\n" + 
										"				<input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\" >" + 
										"			</div>" + 
										"		<textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o1+"</textarea>"+
										"		</div>" + 
										"	</div>"+
										"</label>" + 
										"</div>"
								+
										"<div id=\"option-2\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-2\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o2+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-3\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-3\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o3+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-4\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\" checked=\"checked\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-4\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o4+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-5\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"5\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-5\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o5+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-6\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"6\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-6\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o6+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>";
							}
							else if(answer.equals("5"))
							{
								options = 
										"<div id=\"option-1\">"+
										"<label style=\"width: 90%\">" + 
										"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"		<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"			<div class=\"input-group-text\">\r\n" + 
										"				<input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\" >" + 
										"			</div>" + 
										"		<textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o1+"</textarea>"+
										"		</div>" + 
										"	</div>"+
										"</label>" + 
										"</div>"
								+
										"<div id=\"option-2\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-2\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o2+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-3\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-3\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o3+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-4\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-4\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o4+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-5\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"5\" class=\"radio\" checked=\"checked\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-5\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o5+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-6\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"6\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-6\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o6+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>";
							}
							else if(answer.equals("6"))
							{
								options = 
										"<div id=\"option-1\">"+
										"<label style=\"width: 90%\">" + 
										"	<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"		<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"			<div class=\"input-group-text\">\r\n" + 
										"				<input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\" >" + 
										"			</div>" + 
										"		<textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o1+"</textarea>"+
										"		</div>" + 
										"	</div>"+
										"</label>" + 
										"</div>"
								+
										"<div id=\"option-2\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-2\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o2+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-3\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-3\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o3+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-4\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-4\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o4+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-5\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"5\" class=\"radio\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-5\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o5+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>"
								+
										"<div id=\"option-6\">"+
										"	<label style=\"width: 90%\">" + 
										"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
										"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
										"				<div class=\"input-group-text\">\r\n" + 
										"					<input type=\"radio\" name=\"options\" value=\"6\" class=\"radio\" checked=\"checked\">" + 
										"				</div>" + 
										"			<textarea onclick=\"clearParagraph()\" name=\"option-6\" class=\"SingleAnswerOptions\" style=\"width:100%;height:100%\">"+o6+"</textarea>"+
										"			</div>" + 
										"		</div>"+
										"	</label>" + 
										"</div>";
							}
						}	
					}
					else if(typeOfQuestion.equals("3"))
					{
						String MAQOptions1  = 
								"<div id=\"MAQoption-1\">"+
								"	<label style=\"width: 90%\">" + 
								"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
								"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\" >\r\n" + 
								"				<div class=\"input-group-text\">\r\n" + 
								"					<input type=\"checkbox\" name=\"options\" value=\"1\" class=\"checkbox\">" + 
								"				</div>" + 
								"			<textarea style=\"width:100%;height:100%\" onclick=\"clearParagraph()\" name=\"MAQoption-1\" class=\"options\">"+
											o1 + 
								"			</textarea>"+
								"			</div>" + 
								"		</div>"+
								"	</label>" + 
								"</div>";
						
						String MAQOptions2 = 
								"<div id=\"MAQoption-2\">"+
								"	<label style=\"width: 90%\">" + 
								"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
								"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
								"				<div class=\"input-group-text\">\r\n" + 
								"					<input type=\"checkbox\" name=\"options\" value=\"2\" class=\"checkbox\">" + 
								"				</div>" + 
								"			<textarea style=\"width:100%;height:100%\" onclick=\"clearParagraph()\" name=\"MAQoption-2\" class=\"options\">"+
											o2 +
								"			</textarea>"+
								"			</div>" + 
								"		</div>"+
								"	</label>" + 
								"</div>";
						
						String MAQOptions3 = 
								"<div id=\"MAQoption-3\">"+
								"	<label style=\"width: 90%\">" + 
								"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
								"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
								"				<div class=\"input-group-text\">\r\n" + 
								"					<input type=\"checkbox\" name=\"options\" value=\"3\" class=\"checkbox\">" + 
								"				</div>" + 
								"			<textarea style=\"width:100%;height:100%\" onclick=\"clearParagraph()\" name=\"MAQoption-3\" class=\"options\">"+
											o3 +
								"			</textarea>"+
								"			</div>" + 
								"		</div>"+
								"	</label>" + 
								"</div>";
						
						String MAQOptions4 = 
								"<div id=\"MAQoption-4\">"+
								"	<label style=\"width: 90%\">" + 
								"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
								"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
								"				<div class=\"input-group-text\">\r\n" + 
								"					<input type=\"checkbox\" name=\"options\" value=\"4\" class=\"checkbox\">" + 
								"				</div>" + 
								"			<textarea style=\"width:100%;height:100%\" onclick=\"clearParagraph()\" name=\"MAQoption-4\" class=\"options\">"+
											o4 +
								"			</textarea>"+
								"			</div>" + 
								"		</div>"+
								"	</label>" + 
								"</div>";
						
						String MAQOptions5 = 
								"<div id=\"MAQoption-5\">"+
								"	<label style=\"width: 90%\">" + 
								"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
								"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
								"				<div class=\"input-group-text\">\r\n" + 
								"					<input type=\"checkbox\" name=\"options\" value=\"5\" class=\"checkbox\">" + 
								"				</div>" + 
								"			<textarea style=\"width:100%;height:100%\" onclick=\"clearParagraph()\" name=\"MAQoption-5\" class=\"options\">"+
											o5 +
								"			</textarea>"+
								"			</div>" + 
								"		</div>"+
								"	</label>" + 
								"</div>";
						
						String MAQOptions6 = 
								"<div id=\"MAQoption-6\">"+
								"	<label style=\"width: 90%\">" + 
								"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
								"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
								"				<div class=\"input-group-text\">\r\n" + 
								"					<input type=\"checkbox\" name=\"options\" value=\"6\" class=\"checkbox\">" + 
								"				</div>" + 
								"			<textarea style=\"width:100%;height:100%\" onclick=\"clearParagraph()\" name=\"MAQoption-6\" class=\"options\">"+
											o6 +
								"			</textarea>"+
								"			</div>" + 
								"		</div>"+
								"	</label>" + 
								"</div>";
						
						style3="style=\"display:block;\"";
						if(answer.contains("1"))
						{
							MAQOptions1 = 
							"<div id=\"MAQoption-1\">"+
							"	<label style=\"width: 90%\">" + 
							"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
							"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\" >\r\n" + 
							"				<div class=\"input-group-text\">\r\n" + 
							"					<input type=\"checkbox\" name=\"options\" value=\"1\" checked=\"checked\" class=\"checkbox\">" + 
							"				</div>" + 
							"			<textarea style=\"width:100%;height:100%\" onclick=\"clearParagraph()\" name=\"MAQoption-1\" class=\"options\">"+
										o1 + 
							"			</textarea>"+
							"			</div>" + 
							"		</div>"+
							"	</label>" + 
							"</div>";
						}
						if(answer.contains("2"))
						{
							MAQOptions2 = 
							"<div id=\"MAQoption-2\">"+
							"	<label style=\"width: 90%\">" + 
							"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
							"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
							"				<div class=\"input-group-text\">\r\n" + 
							"					<input type=\"checkbox\" name=\"options\" value=\"2\" checked=\"checked\" class=\"checkbox\">" + 
							"				</div>" + 
							"			<textarea style=\"width:100%;height:100%\" onclick=\"clearParagraph()\" name=\"MAQoption-2\" class=\"options\">"+
										o2 +
							"			</textarea>"+
							"			</div>" + 
							"		</div>"+
							"	</label>" + 
							"</div>";
						}
						if(answer.contains("3"))
						{
							MAQOptions3 = 
							"<div id=\"MAQoption-3\">"+
							"	<label style=\"width: 90%\">" + 
							"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
							"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
							"				<div class=\"input-group-text\">\r\n" + 
							"					<input type=\"checkbox\" name=\"options\" value=\"3\" class=\"checkbox\" checked=\"checked\">" + 
							"				</div>" + 
							"			<textarea style=\"width:100%;height:100%\" onclick=\"clearParagraph()\" name=\"MAQoption-3\" class=\"options\">"+
										o3 +
							"			</textarea>"+
							"			</div>" + 
							"		</div>"+
							"	</label>" + 
							"</div>";
						}
						if(answer.contains("4"))
						{
							MAQOptions4 = 
							"<div id=\"MAQoption-4\">"+
							"	<label style=\"width: 90%\">" + 
							"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
							"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
							"				<div class=\"input-group-text\">\r\n" + 
							"					<input type=\"checkbox\" name=\"options\" value=\"4\" checked=\"checked\" class=\"checkbox\">" + 
							"				</div>" + 
							"			<textarea style=\"width:100%;height:100%\" onclick=\"clearParagraph()\" name=\"MAQoption-4\" class=\"options\">"+
										o4 +
							"			</textarea>"+
							"			</div>" + 
							"		</div>"+
							"	</label>" + 
							"</div>";
						}
						if(answer.contains("5"))
						{
							MAQOptions5 = 
							"<div id=\"MAQoption-5\">"+
							"	<label style=\"width: 90%\">" + 
							"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
							"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
							"				<div class=\"input-group-text\">\r\n" + 
							"					<input type=\"checkbox\" name=\"options\" value=\"5\" class=\"checkbox\" checked=\"checked\">" + 
							"				</div>" + 
							"			<textarea style=\"width:100%;height:100%\" onclick=\"clearParagraph()\" name=\"MAQoption-5\" class=\"options\">"+
										o5 +
							"			</textarea>"+
							"			</div>" + 
							"		</div>"+
							"	</label>" + 
							"</div>";
						}
						if(answer.contains("6"))
						{
							MAQOptions6 = 
							"<div id=\"MAQoption-6\">"+
							"	<label style=\"width: 90%\">" + 
							"		<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">" + 
							"			<div class=\"input-group-prepend\" style=\"width:100%;height:9%\">\r\n" + 
							"				<div class=\"input-group-text\">\r\n" + 
							"					<input type=\"checkbox\" name=\"options\" value=\"6\" class=\"checkbox\" checked=\"checked\">" + 
							"				</div>" + 
							"			<textarea style=\"width:100%;height:100%\" onclick=\"clearParagraph()\" name=\"MAQoption-6\" class=\"options\">"+
										o6 +
							"			</textarea>"+
							"			</div>" + 
							"		</div>"+
							"	</label>" + 
							"</div>";
						}
					
						MAQOptions = MAQOptions1 + MAQOptions2 + MAQOptions3 + MAQOptions4;
						
						
						
						if(noOfOptionsInAQuestion == 2)
							MAQOptions = MAQOptions1 + MAQOptions2;
						else if(noOfOptionsInAQuestion == 3)
							MAQOptions = MAQOptions1 + MAQOptions2 + MAQOptions3;
						else if(noOfOptionsInAQuestion == 4)
							MAQOptions = MAQOptions1 + MAQOptions2 + MAQOptions3 + MAQOptions4;
						else if(noOfOptionsInAQuestion == 5)
							MAQOptions = MAQOptions1 + MAQOptions2 + MAQOptions3 + MAQOptions4 + MAQOptions5;
						else if(noOfOptionsInAQuestion == 6)
							MAQOptions = MAQOptions1 + MAQOptions2 + MAQOptions3 + MAQOptions4 + MAQOptions5 + MAQOptions6;							
					}
					
					
					TandFdiv = "<br><div id=\"TandF\""+style1+">";
					 
					optionDiv="<div id=\"multipleChoiceQuestions\""+style2+"><div class=\"option\" name=\"mcqoptions\">";
					 
					MAQDiv = "<div id=\"MAQ\""+ style3+"><div class=\"MAQoption\">";
					 
					
					html2="</form></div></body></html>";
					
					 
					noq1=	"<input type=\"hidden\" id=\"noq\" name=\"noq\" style=\"display:none;\" value=\""+noq+"\">"+
							"<input type=\"hidden\" id=\"alreadyPresentLabels\" name=\"labels\" style=\"display:none;\" value=\""+alreadyPresentLabels+"\">";
					
				
					
					if((int)questionNumber > noq)
						out.println("Test created");
					else if((int)questionNumber == noq)
						out.println(html1+header+breadCrumb+cardBorderDiv+cardHeader+form+tid+qno+noq1+textArea+validationQText+inputTopic+topicValidation+"<br>"+typeOfQuestionDropdown+positiveMarks+positiveMarksValidator+negativeMarks+negativeMarksValidator+timeLimit+timeLimitValidator+label+TandFdiv+TandFoptions+closeTandFdiv+optionDiv+options+closeDiv+MAQDiv+MAQOptions+closeMAQDiv+p+linkDiv+addOption+slash+removeOption+linkDivClose+previousButton+br+saveAndExit+completeQuestionSubmission+html2);
					else if((int)questionNumber == 1)
						out.println(html1+header+breadCrumb+cardBorderDiv+cardHeader+form+tid+qno+noq1+textArea+validationQText+inputTopic+topicValidation+"<br>"+typeOfQuestionDropdown+positiveMarks+positiveMarksValidator+negativeMarks+negativeMarksValidator+timeLimit+timeLimitValidator+label+TandFdiv+TandFoptions+closeTandFdiv+optionDiv+options+closeDiv+MAQDiv+MAQOptions+closeMAQDiv+p+linkDiv+addOption+slash+removeOption+linkDivClose+nextbutton+br+saveAndExit+html2);
					else
						out.println(html1+header+breadCrumb+cardBorderDiv+cardHeader+form+tid+qno+noq1+textArea+validationQText+inputTopic+topicValidation+"<br>"+typeOfQuestionDropdown+positiveMarks+positiveMarksValidator+negativeMarks+negativeMarksValidator+timeLimit+timeLimitValidator+label+TandFdiv+TandFoptions+closeTandFdiv+optionDiv+options+closeDiv+MAQDiv+MAQOptions+closeMAQDiv+p+linkDiv+addOption+slash+removeOption+linkDivClose+previousButton+nextbutton+br+saveAndExit+html2);
					
				}
	
			}
		}catch(Exception e)
		{
			out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();
		}
	}

}
