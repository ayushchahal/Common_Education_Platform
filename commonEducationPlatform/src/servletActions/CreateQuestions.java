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
				TestCreators tc = new TestCreators();
				timeLimitType = tc.getTimeLimitType(""+testID);
				
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
					answer = tc.getAnswer(""+questionID);
					o1 = tc.getOption(""+questionID, "1");
					o2 = tc.getOption(""+questionID, "2");
					o3 = tc.getOption(""+questionID, "3");
					o4 = tc.getOption(""+questionID, "4");
					o5 = tc.getOption(""+questionID, "5");
					o6 = tc.getOption(""+questionID, "6");
					noOfOptionsInAQuestion = tc.getNumberOfOptionsInAQuestion(""+questionID);
					flag = 1;
				}
				
				String header = "<div class=\"card border-0\">	\r\n" + 
						"    		<div class=\"card-header\" style=\"background-color:#eaeced;\">		\r\n" + 
						"    			<h5>Test Plant\r\n" + 
						"    				<form action=\"/Logout\" method=\"post\">\r\n" + 
						"    					<input name=\"Submit\" type=\"submit\" value=\"Logout\" id=\"Logout\" class=\"btn btn-secondary float-right\">		\r\n" + 
						"    				</form>\r\n" + 
						"    			</h5>\r\n" + 
						"    		</div>\r\n" + 
						"    	</div>";
				
				String breadCrumb = "<nav aria-label=\"breadcrumb\">  <ol class=\"breadcrumb\">    <li class=\"breadcrumb-item\"><a href=\"/CoachingDashboard\">Home</a></li>    <li class=\"breadcrumb-item\" aria-current=\"page\">\r\n" + 
						"    \r\n" + 
						"    <a href=\"/DisplayCreateTestPage\">Create Test</a></li>\r\n" + 
						"<li class=\"breadcrumb-item active\" aria-current=\"page\">Create Questions</li>  </ol></nav><br>";
				
				
				String cardBorderDiv = "<div class=\"card border-secondary\" style=\"margin-left:5%;width:70%;\">";
				
				
				
				if(flag == 0)
				{
					String sss = "<script src=\"http://js.nicedit.com/nicEdit-latest.js\" type=\"text/javascript\"></script><script type=\"text/javascript\">\r\n" + 
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
					//sss = "";
					
					String bootstrap = "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">\r\n" + 
							"	<link rel=\"stylesheet\" href=\"//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css\">"
							+ "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">";
							
					
					String html1="<html>\r\n" + 
							"	<head>\r\n" + 
							"		<meta charset=\"ISO-8859-1\">\r\n" + 
							"		<title>Create a test</title>\r\n" + 
							"		<link rel=\"stylesheet\" href=\"questions.css\">\r\n" + 
							"<script src=\"addRemoveOptions.js\"></script>"+
							"<script src=\"changeQType.js\"></script>"+ sss + bootstrap +
							"	</head>\r\n" + 
							"<body>";
					
					String cardHeader = "<div class=\"card-header bg-dark text-white\">\r\n" + 
							"        <h5>Question "+questionNumber+" of "+ noq +"</h5>\r\n" + 
							"    </div>";
					
					String textArea="<textarea id=\"question\" name=\"question\"></textarea><br>";
					/*String textArea = "<div unselectable=\"on\" style=\"width: 100%;\"><div class=\" nicEdit-panelContain\" unselectable=\"on\" style=\"overflow: hidden; width: 100%; border: 1px solid rgb(204, 204, 204); background-color: rgb(239, 239, 239);\"><div class=\" nicEdit-panel\" unselectable=\"on\" style=\"margin: 0px 2px 2px; zoom: 1; overflow: hidden;\"><div style=\"float: left; margin-top: 2px; display: none;\"><div class=\" nicEdit-buttonContain\" style=\"width: 20px; height: 20px; opacity: 0.6;\"><div class=\" nicEdit-button-undefined\" style=\"background-color: rgb(239, 239, 239); border: 1px solid rgb(239, 239, 239);\"><div class=\" nicEdit-button\" unselectable=\"on\" style=\"width: 18px; height: 18px; overflow: hidden; zoom: 1; cursor: pointer; background-image: url(&quot;http://js.nicedit.com/nicEditIcons-latest.gif&quot;); background-position: -432px 0px;\"></div></div></div></div><div unselectable=\"on\" style=\"float: left; margin-top: 2px;\"><div class=\" nicEdit-buttonContain\" unselectable=\"on\" style=\"width: 20px; height: 20px; opacity: 0.6;\"><div class=\" nicEdit-button-undefined\" unselectable=\"on\" style=\"background-color: rgb(239, 239, 239); border: 1px solid rgb(239, 239, 239);\"><div class=\" nicEdit-button\" unselectable=\"on\" style=\"width: 18px; height: 18px; overflow: hidden; zoom: 1; cursor: pointer; background-image: url(&quot;http://js.nicedit.com/nicEditIcons-latest.gif&quot;); background-position: -54px 0px;\"></div></div></div></div><div unselectable=\"on\" style=\"float: left; margin-top: 2px;\"><div class=\" nicEdit-buttonContain\" unselectable=\"on\" style=\"width: 20px; height: 20px; opacity: 0.6;\"><div class=\" nicEdit-button-undefined\" unselectable=\"on\" style=\"background-color: rgb(239, 239, 239); border: 1px solid rgb(239, 239, 239);\"><div class=\" nicEdit-button\" unselectable=\"on\" style=\"width: 18px; height: 18px; overflow: hidden; zoom: 1; cursor: pointer; background-image: url(&quot;http://js.nicedit.com/nicEditIcons-latest.gif&quot;); background-position: -126px 0px;\"></div></div></div></div><div unselectable=\"on\" style=\"float: left; margin-top: 2px;\"><div class=\" nicEdit-buttonContain\" unselectable=\"on\" style=\"width: 20px; height: 20px; opacity: 0.6;\"><div class=\" nicEdit-button-undefined\" unselectable=\"on\" style=\"background-color: rgb(239, 239, 239); border: 1px solid rgb(239, 239, 239);\"><div class=\" nicEdit-button\" unselectable=\"on\" style=\"width: 18px; height: 18px; overflow: hidden; zoom: 1; cursor: pointer; background-image: url(&quot;http://js.nicedit.com/nicEditIcons-latest.gif&quot;); background-position: -342px 0px;\"></div></div></div></div><div unselectable=\"on\" style=\"float: left; margin-top: 2px;\"><div class=\" nicEdit-buttonContain\" unselectable=\"on\" style=\"width: 20px; height: 20px; opacity: 0.6;\"><div class=\" nicEdit-button-undefined\" unselectable=\"on\" style=\"background-color: rgb(239, 239, 239); border: 1px solid rgb(239, 239, 239);\"><div class=\" nicEdit-button\" unselectable=\"on\" style=\"width: 18px; height: 18px; overflow: hidden; zoom: 1; cursor: pointer; background-image: url(&quot;http://js.nicedit.com/nicEditIcons-latest.gif&quot;); background-position: -162px 0px;\"></div></div></div></div><div unselectable=\"on\" style=\"float: left; margin-top: 2px;\"><div class=\" nicEdit-buttonContain\" unselectable=\"on\" style=\"width: 20px; height: 20px; opacity: 0.6;\"><div class=\" nicEdit-button-undefined\" unselectable=\"on\" style=\"background-color: rgb(239, 239, 239); border: 1px solid rgb(239, 239, 239);\"><div class=\" nicEdit-button\" unselectable=\"on\" style=\"width: 18px; height: 18px; overflow: hidden; zoom: 1; cursor: pointer; background-image: url(&quot;http://js.nicedit.com/nicEditIcons-latest.gif&quot;); background-position: -72px 0px;\"></div></div></div></div><div unselectable=\"on\" style=\"float: left; margin-top: 2px;\"><div class=\" nicEdit-buttonContain\" unselectable=\"on\" style=\"width: 20px; height: 20px; opacity: 0.6;\"><div class=\" nicEdit-button-undefined\" unselectable=\"on\" style=\"background-color: rgb(239, 239, 239); border: 1px solid rgb(239, 239, 239);\"><div class=\" nicEdit-button\" unselectable=\"on\" style=\"width: 18px; height: 18px; overflow: hidden; zoom: 1; cursor: pointer; background-image: url(&quot;http://js.nicedit.com/nicEditIcons-latest.gif&quot;); background-position: -234px 0px;\"></div></div></div></div><div unselectable=\"on\" style=\"float: left; margin-top: 2px;\"><div class=\" nicEdit-buttonContain\" unselectable=\"on\" style=\"width: 20px; height: 20px; opacity: 0.6;\"><div class=\" nicEdit-button-undefined\" unselectable=\"on\" style=\"background-color: rgb(239, 239, 239); border: 1px solid rgb(239, 239, 239);\"><div class=\" nicEdit-button\" unselectable=\"on\" style=\"width: 18px; height: 18px; overflow: hidden; zoom: 1; cursor: pointer; background-image: url(&quot;http://js.nicedit.com/nicEditIcons-latest.gif&quot;); background-position: -144px 0px;\"></div></div></div></div><div unselectable=\"on\" style=\"float: left; margin-top: 2px;\"><div class=\" nicEdit-buttonContain\" unselectable=\"on\" style=\"width: 20px; height: 20px; opacity: 0.6;\"><div class=\" nicEdit-button-undefined\" unselectable=\"on\" style=\"background-color: rgb(239, 239, 239); border: 1px solid rgb(239, 239, 239);\"><div class=\" nicEdit-button\" unselectable=\"on\" style=\"width: 18px; height: 18px; overflow: hidden; zoom: 1; cursor: pointer; background-image: url(&quot;http://js.nicedit.com/nicEditIcons-latest.gif&quot;); background-position: -180px 0px;\"></div></div></div></div><div unselectable=\"on\" style=\"float: left; margin-top: 2px;\"><div class=\" nicEdit-buttonContain\" unselectable=\"on\" style=\"width: 20px; height: 20px; opacity: 0.6;\"><div class=\" nicEdit-button-undefined\" unselectable=\"on\" style=\"background-color: rgb(239, 239, 239); border: 1px solid rgb(239, 239, 239);\"><div class=\" nicEdit-button\" unselectable=\"on\" style=\"width: 18px; height: 18px; overflow: hidden; zoom: 1; cursor: pointer; background-image: url(&quot;http://js.nicedit.com/nicEditIcons-latest.gif&quot;); background-position: -324px 0px;\"></div></div></div></div><div unselectable=\"on\" style=\"float: left; margin: 2px 1px 0px;\"><div class=\" nicEdit-selectContain\" unselectable=\"on\" style=\"width: 90px; height: 20px; cursor: pointer; overflow: hidden; opacity: 0.6;\"><div unselectable=\"on\" style=\"overflow: hidden; zoom: 1; border: 1px solid rgb(204, 204, 204); padding-left: 3px; background-color: rgb(255, 255, 255);\"><div class=\" nicEdit-selectControl\" unselectable=\"on\" style=\"overflow: hidden; float: right; height: 18px; width: 16px; background-image: url(&quot;http://js.nicedit.com/nicEditIcons-latest.gif&quot;); background-position: -450px 0px;\"></div><div class=\" nicEdit-selectTxt\" unselectable=\"on\" style=\"overflow: hidden; float: left; width: 66px; height: 14px; margin-top: 1px; font-family: sans-serif; text-align: center; font-size: 12px;\">Font&nbsp;Size...</div></div></div></div><div unselectable=\"on\" style=\"float: left; margin: 2px 1px 0px;\"><div class=\" nicEdit-selectContain\" unselectable=\"on\" style=\"width: 90px; height: 20px; cursor: pointer; overflow: hidden; opacity: 0.6;\"><div unselectable=\"on\" style=\"overflow: hidden; zoom: 1; border: 1px solid rgb(204, 204, 204); padding-left: 3px; background-color: rgb(255, 255, 255);\"><div class=\" nicEdit-selectControl\" unselectable=\"on\" style=\"overflow: hidden; float: right; height: 18px; width: 16px; background-image: url(&quot;http://js.nicedit.com/nicEditIcons-latest.gif&quot;); background-position: -450px 0px;\"></div><div class=\" nicEdit-selectTxt\" unselectable=\"on\" style=\"overflow: hidden; float: left; width: 66px; height: 14px; margin-top: 1px; font-family: sans-serif; text-align: center; font-size: 12px;\">Font&nbsp;Family...</div></div></div></div><div unselectable=\"on\" style=\"float: left; margin: 2px 1px 0px;\"><div class=\" nicEdit-selectContain\" unselectable=\"on\" style=\"width: 90px; height: 20px; cursor: pointer; overflow: hidden; opacity: 0.6;\"><div unselectable=\"on\" style=\"overflow: hidden; zoom: 1; border: 1px solid rgb(204, 204, 204); padding-left: 3px; background-color: rgb(255, 255, 255);\"><div class=\" nicEdit-selectControl\" unselectable=\"on\" style=\"overflow: hidden; float: right; height: 18px; width: 16px; background-image: url(&quot;http://js.nicedit.com/nicEditIcons-latest.gif&quot;); background-position: -450px 0px;\"></div><div class=\" nicEdit-selectTxt\" unselectable=\"on\" style=\"overflow: hidden; float: left; width: 66px; height: 14px; margin-top: 1px; font-family: sans-serif; text-align: center; font-size: 12px;\">Font&nbsp;Format...</div></div></div></div><div unselectable=\"on\" style=\"float: left; margin-top: 2px;\"><div class=\" nicEdit-buttonContain\" unselectable=\"on\" style=\"width: 20px; height: 20px; opacity: 0.6;\"><div class=\" nicEdit-button-undefined\" unselectable=\"on\" style=\"background-color: rgb(239, 239, 239); border: 1px solid rgb(239, 239, 239);\"><div class=\" nicEdit-button\" unselectable=\"on\" style=\"width: 18px; height: 18px; overflow: hidden; zoom: 1; cursor: pointer; background-image: url(&quot;http://js.nicedit.com/nicEditIcons-latest.gif&quot;); background-position: -108px 0px;\"></div></div></div></div><div unselectable=\"on\" style=\"float: left; margin-top: 2px;\"><div class=\" nicEdit-buttonContain\" unselectable=\"on\" style=\"width: 20px; height: 20px; opacity: 0.6;\"><div class=\" nicEdit-button-undefined\" unselectable=\"on\" style=\"background-color: rgb(239, 239, 239); border: 1px solid rgb(239, 239, 239);\"><div class=\" nicEdit-button\" unselectable=\"on\" style=\"width: 18px; height: 18px; overflow: hidden; zoom: 1; cursor: pointer; background-image: url(&quot;http://js.nicedit.com/nicEditIcons-latest.gif&quot;); background-position: -198px 0px;\"></div></div></div></div><div style=\"float: left; margin-top: 2px;\"><div class=\" nicEdit-buttonContain\" style=\"width: 20px; height: 20px; opacity: 0.6;\"><div class=\" nicEdit-button-undefined\" style=\"background-color: rgb(239, 239, 239); border: 1px solid rgb(239, 239, 239);\"><div class=\" nicEdit-button\" unselectable=\"on\" style=\"width: 18px; height: 18px; overflow: hidden; zoom: 1; cursor: pointer; background-image: url(&quot;http://js.nicedit.com/nicEditIcons-latest.gif&quot;); background-position: -360px 0px;\"></div></div></div></div><div style=\"float: left; margin-top: 2px;\"><div class=\" nicEdit-buttonContain\" style=\"width: 20px; height: 20px; opacity: 0.6;\"><div class=\" nicEdit-button-undefined\" style=\"background-color: rgb(239, 239, 239); border: 1px solid rgb(239, 239, 239);\"><div class=\" nicEdit-button\" unselectable=\"on\" style=\"width: 18px; height: 18px; overflow: hidden; zoom: 1; cursor: pointer; background-image: url(&quot;http://js.nicedit.com/nicEditIcons-latest.gif&quot;); background-position: -468px 0px;\"></div></div></div></div><div style=\"float: left; margin-top: 2px;\"><div class=\" nicEdit-buttonContain\" style=\"width: 20px; height: 20px; opacity: 0.6;\"><div class=\" nicEdit-button-undefined\" style=\"background-color: rgb(239, 239, 239); border: 1px solid rgb(239, 239, 239);\"><div class=\" nicEdit-button\" unselectable=\"on\" style=\"width: 18px; height: 18px; overflow: hidden; zoom: 1; cursor: pointer; background-image: url(&quot;http://js.nicedit.com/nicEditIcons-latest.gif&quot;); background-position: -378px 0px;\"></div></div></div></div><div style=\"float: left; margin-top: 2px;\"><div class=\" nicEdit-buttonContain\" style=\"width: 20px; height: 20px; opacity: 0.6;\"><div class=\" nicEdit-button-undefined\" style=\"background-color: rgb(239, 239, 239); border: 1px solid rgb(239, 239, 239);\"><div class=\" nicEdit-button\" unselectable=\"on\" style=\"width: 18px; height: 18px; overflow: hidden; zoom: 1; cursor: pointer; background-image: url(&quot;http://js.nicedit.com/nicEditIcons-latest.gif&quot;); background-position: -396px 0px;\"></div></div></div></div><div style=\"float: left; margin-top: 2px;\"><div class=\" nicEdit-buttonContain\" style=\"width: 20px; height: 20px; opacity: 0.6;\"><div class=\" nicEdit-button-undefined\" style=\"background-color: rgb(239, 239, 239); border: 1px solid rgb(239, 239, 239);\"><div class=\" nicEdit-button\" unselectable=\"on\" style=\"width: 18px; height: 18px; overflow: hidden; zoom: 1; cursor: pointer; background-image: url(&quot;http://js.nicedit.com/nicEditIcons-latest.gif&quot;); background-position: -36px 0px;\"></div></div></div></div><div style=\"float: left; margin-top: 2px;\"><div class=\" nicEdit-buttonContain\" style=\"width: 20px; height: 20px; opacity: 0.6;\"><div class=\" nicEdit-button-undefined\" style=\"background-color: rgb(239, 239, 239); border: 1px solid rgb(239, 239, 239);\"><div class=\" nicEdit-button\" unselectable=\"on\" style=\"width: 18px; height: 18px; overflow: hidden; zoom: 1; cursor: pointer; background-image: url(&quot;http://js.nicedit.com/nicEditIcons-latest.gif&quot;); background-position: -18px 0px;\"></div></div></div></div></div></div>\r\n" + 
							"<div style=\"width: 100%; border-width: 0px 1px 1px; border-top-style: initial; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: initial; border-right-color: rgb(204, 204, 204); border-bottom-color: rgb(204, 204, 204); border-left-color: rgb(204, 204, 204); border-image: initial; overflow: hidden auto;\"><div class=\" nicEdit-main\" contenteditable=\"true\" style=\"width: 98%; margin: 4px; min-height: 130px; overflow: hidden;\" spellcheck=\"true\"><br></div></div>\r\n" + 
							"</div>";
					*/
					
					//String labels = "<label style>Label: </label>"+"<input type=\"text\" id=\"labels\" name=\"labels\"></input><br><br>";
					String tid="<input type=\"hidden\" id=\"tid\" name=\"tid\" style=\"display:none;\" value=\""+testID+"\">";
					String qno="<input type=\"hidden\" id=\"qno\" name=\"qno\" style=\"display:none;\" value=\""+questionNumber+"\">";
					String noq1="<input type=\"hidden\" id=\"noq\" name=\"noq\" style=\"display:none;\" value=\""+noq+"\">";
					String block="<div id=\"content\" class=\"block\">";
					String form="<div class=\"card-body\"><form action=\"SaveTest\" method=\"Post\" id=\"questionsForm\">";
					String dropdown="<div class=\"lhs\"><label style>Type of Question:</label><br><select name=\"qType\" id=\"qType\" onChange=\"changeQType()\"><option value=\"1\">True/False</option><option value=\"2\" selected>Multiple choice</option><option value=\"3\">Multiple response</option></select></div>";
					String typeOfQuestionDropdown = "<div class=\"input-group input-group-default\">\r\n" + 
							"  <div class=\"input-group-prepend\">\r\n" + 
							"    <span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Type of Question:</span>"
							+ "<select name=\"qType\" id=\"qType\" onChange=\"changeQType()\"><option value=\"1\">True/False</option><option value=\"2\" selected>Multiple choice</option><option value=\"3\">Multiple response</option></select></div></div><br>";
					
					//String fillInTheBlanks="<option value=\"4\">Fill in the blank</option></select></div>";
					String positiveMarks="<div class=\"input-group input-group-default\">\r\n" + 
							"  <div class=\"input-group-prepend\">\r\n" + 
							"    <span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Positive Marks:</span><input id=\"pmarks\" name=\"pmarks\" size=\"3\" type=\"number\" minimun=\"0\" step=\"0.1\" value=\"1\" style=\"text-align: right; width:20%\"></div></div><br>";
					
					String negativeMarks="<div class=\"input-group input-group-default\">\r\n" + 
							"  <div class=\"input-group-prepend\">\r\n" + 
							"    <span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Negative Marks:</span><input id=\"nmarks\" name=\"nmarks\" size=\"3\" type=\"number\" minimun=\"0\" step=\"0.1\" value=\"0\" style=\"text-align: right; width:20%\"></div></div><br>";
					String timeLimit = "";
					if(timeLimitType.equals("1"))
					{
						timeLimit="<div class=\"input-group input-group-default\">\r\n" + 
								"  <div class=\"input-group-prepend\">\r\n" + 
								"    <span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">Time Limit (in seconds):</span><input id=\"timeLimit\" name=\"timeLimit\" size=\"3\" type=\"number\" minimun=\"0\" value=\"0\" style=\"text-align: right; width:20%\"></div></div><br>";
					}
					else
					{
					 timeLimit = "<br>";
					}
					
					
					String label="<label style=\"width: 90%\"><h6 class=\"card-header border\">Enter the options, and mark the correct answer(s)</h6>\r\n" + 
							"    </label>";
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
					
					String button1="<input class=\"center\" type=\"submit\" name=\"save\" value=\"Save and Exit\">";
					//String reset="<input class=\"center\" type=\"reset\" name=\"reset\" value=\"Reset\">";
					String button2="<input class=\"rhs\" type=\"submit\" name=\"save\" value=\"To Next Question\">";
					String button3="<input class=\"rhs\" type=\"submit\" name=\"save\" value=\"Complete Questions Submission\">";
					String html2="</form></div></body></html>";
					
					
					
					if((int)questionNumber > noq)
						out.println("Test created");
					else if((int)questionNumber == noq)
					{
						if((int)questionNumber == 1)
							out.println(html1+header+breadCrumb+cardBorderDiv+cardHeader+form+tid+qno+noq1+textArea+typeOfQuestionDropdown+positiveMarks+negativeMarks+timeLimit+label+TandFdiv+TandFoptions+closeTandFdiv+optionDiv+options+closeDiv+MAQDiv+MAQOptions+closeMAQDiv+p+linkDiv+addOption+slash+removeOption+linkDivClose+button1+button3+html2);
						else
							out.println(html1+header+breadCrumb+cardBorderDiv+cardHeader+form+tid+qno+noq1+textArea+typeOfQuestionDropdown+positiveMarks+negativeMarks+timeLimit+label+TandFdiv+TandFoptions+closeTandFdiv+optionDiv+options+closeDiv+MAQDiv+MAQOptions+closeMAQDiv+p+linkDiv+addOption+slash+removeOption+linkDivClose+previousButton+button1+button3+html2);
					}	
					else
					{
						if((int)questionNumber == 1)
							out.println(html1+header+breadCrumb+cardBorderDiv+cardHeader+form+tid+qno+noq1+textArea+typeOfQuestionDropdown+positiveMarks+negativeMarks+timeLimit+label+TandFdiv+TandFoptions+closeTandFdiv+optionDiv+options+closeDiv+MAQDiv+MAQOptions+closeMAQDiv+p+linkDiv+addOption+slash+removeOption+linkDivClose+button1+button2+html2);
						else
							out.println(html1+header+breadCrumb+cardBorderDiv+cardHeader+form+tid+qno+noq1+textArea+typeOfQuestionDropdown+positiveMarks+negativeMarks+timeLimit+label+TandFdiv+TandFoptions+closeTandFdiv+optionDiv+options+closeDiv+MAQDiv+MAQOptions+closeMAQDiv+p+linkDiv+addOption+slash+removeOption+linkDivClose+previousButton+button1+button2+html2);
					}
				}
				else
				{
					String sss = "<script src=\"http://js.nicedit.com/nicEdit-latest.js\" type=\"text/javascript\"></script><script type=\"text/javascript\">\r\n" + 
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
					
					String bootstrap = "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">\r\n" + 
							"	<link rel=\"stylesheet\" href=\"//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css\">"
							+ "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">";
					
					
					String html1="<html>\r\n" + 
							"	<head>\r\n" + 
							"		<meta charset=\"ISO-8859-1\">\r\n" + 
							"		<title>Create a test</title>\r\n" + 
							"		<link rel=\"stylesheet\" href=\"questions.css\">\r\n" + 
							"<script src=\"addRemoveOptions.js\"></script>"+
							"<script src=\"changeQType.js\"></script>"+sss+bootstrap+
							"	</head>\r\n" + 
							"<body>";
					String tid="<input type=\"hidden\" id=\"tid\" name=\"tid\" style=\"display:none;\" value=\""+testID+"\">";
					String qno="<input type=\"hidden\" id=\"qno\" name=\"qno\" style=\"display:none;\" value=\""+questionNumber+"\">";
					String noq1="<input type=\"hidden\" id=\"noq\" name=\"noq\" style=\"display:none;\" value=\""+noq+"\">";
					String block="<div id=\"content\" class=\"block\">";
					String form="<form action=\"SaveTest\" method=\"Post\" id=\"questionsForm\">";
					String textArea="<label style>Question "+questionNumber+" of "+noq+"</label><br>"+"<textarea id=\"question\" name=\"question\">"+questionText+"</textarea><br><br>";
					String dropdown=null;
					
					if(typeOfQuestion.equals("1"))
					{
						dropdown="<div class=\"lhs\"><label style>Type of Question:</label><br><select name=\"qType\" id=\"qType\" onChange=\"changeQType()\" ><option value=\"1\" selected>True/False</option><option value=\"2\">Multiple choice</option><option value=\"3\">Multiple response</option></select></div>";
					}
					else if(typeOfQuestion.equals("2"))
					{
						dropdown="<div class=\"lhs\"><label style>Type of Question:</label><br><select name=\"qType\" id=\"qType\" onChange=\"changeQType()\" selected="+typeOfQuestion+"><option value=\"1\">True/False</option><option value=\"2\" selected>Multiple choice</option><option value=\"3\">Multiple response</option></select></div>";
					}
					else if(typeOfQuestion.equals("3"))
					{
						dropdown="<div class=\"lhs\"><label style>Type of Question:</label><br><select name=\"qType\" id=\"qType\" onChange=\"changeQType()\" selected="+typeOfQuestion+"><option value=\"1\">True/False</option><option value=\"2\">Multiple choice</option><option value=\"3\" selected>Multiple response</option></select></div>";
					}
					//String fillInTheBlanks="<option value=\"4\">Fill in the blank</option></select></div>";
					String positiveMarks="<div class=\"rhs\"><label style>Positive Marks:</label><input id=\"pmarks\" name=\"pmarks\" size=\"3\" type=\"number\" minimun=\"0\" step=\"0.01\" value=\""+pMarks+"\"></div><br><br>";
					String negativeMarks="<div class=\"rhs\"><label style>Negative Marks:</label><input id=\"nmarks\" name=\"nmarks\" size=\"3\" type=\"number\" minimun=\"0\" step=\"0.01\" value=\""+nMarks+"\"></div><br><br>";
					
					String timeLimit = "";
					if(timeLimitType.equals("1"))
					{
						timeLimit="<div class=\"rhs\"><label style>Time Limit (in seconds):</label><input id=\"timeLimit\" name=\"timeLimit\" size=\"3\" type=\"number\" minimun=\"0\" value=\"0\"></div><br><br><br>";
					}
					else
					{
						timeLimit = "<br>";
					}
					
					String label="<label>Enter the options, and mark which answer is correct</label><br>";
					//String TandFdiv = "<br><div id=\"TandF\" style=\"display:none;\">";
					
					String style1 = "style=\"display:none;\"";
					String style2 = "style=\"display:none;\"";
					String style3 = "style=\"display:none;\"";
					String TandFoptions = "<div id=\"option-1\" name=\"option-1\"><input type=\"radio\" name=\"options\" class=\"radio\" value=\"true\" style=\"vertical-align: top;\">True</div><br><div id=\"option-2\" name=\"option-2\"><input type=\"radio\" name=\"options\" class=\"radio\" value=\"false\" style=\"vertical-align: top;\">False</div>";
					String options="<div id=\"option-1\"><br><input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"options\"></textarea><br></div><div id=\"option-2\"><br><input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-2\"></textarea><br></div><div id=\"option-3\"><br><input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-3\"></textarea><br></div><div id=\"option-4\"><br><input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-4\"></textarea><br></div>";
					String MAQOptions = "<div id=\"MAQoption-1\"><br><input type=\"checkbox\" name=\"options\" value=\"1\" class=\"checkbox\"><textarea onclick=\"clearParagraph()\" class=\"options\" name=\"MAQoption-1\"></textarea><br></div><div id=\"MAQoption-2\"><br><input type=\"checkbox\" name=\"options\" value=\"2\" class=\"checkbox\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"MAQoption-2\"></textarea><br></div><div id=\"MAQoption-3\"><br><input type=\"checkbox\" name=\"options\" value=\"3\" class=\"checkbox\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"MAQoption-3\"></textarea><br></div><div id=\"MAQoption-4\"><br><input type=\"checkbox\" name=\"options\" value=\"4\" class=\"checkbox\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"MAQoption-4\"></textarea><br></div>";
					
					if(typeOfQuestion.equals("1"))
					{
						style1="style=\"display:block;\"";
						if(answer.equals("1"))
							TandFoptions = "<div id=\"option-1\" name=\"option-1\"><input type=\"radio\" name=\"options\" class=\"radio\" value=\"true\" style=\"vertical-align: top;\" checked=\"checked\">True</div><br><div id=\"option-2\" name=\"option-2\"><input type=\"radio\" name=\"options\" class=\"radio\" value=\"false\" style=\"vertical-align: top;\">False</div>";
						else
							TandFoptions = "<div id=\"option-1\" name=\"option-1\"><input type=\"radio\" name=\"options\" class=\"radio\" value=\"true\" style=\"vertical-align: top;\">True</div><br><div id=\"option-2\" name=\"option-2\"><input type=\"radio\" name=\"options\" class=\"radio\" value=\"false\" style=\"vertical-align: top;\" checked=\"checked\">False</div>";
					}
					else if(typeOfQuestion.equals("2"))
					{
						style2="style=\"display:block;\"";
						if(noOfOptionsInAQuestion == 2)
						{
							if(answer.equals("1"))
								options="<div id=\"option-1\"><br><input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\" checked=\"checked\"><textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"options\">"+o1+"</textarea><br></div><div id=\"option-2\"><br><input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-2\">"+o2+"</textarea><br></div>";
							else if(answer.equals("2"))
								options="<div id=\"option-1\"><br><input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"options\">"+o1+"</textarea><br></div><div id=\"option-2\"><br><input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\" checked=\"checked\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-2\">"+o2+"</textarea><br></div>";							
						}
						else if(noOfOptionsInAQuestion == 3)
						{
							if(answer.equals("1"))
								options="<div id=\"option-1\"><br><input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\" checked=\"checked\"><textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"options\">"+o1+"</textarea><br></div><div id=\"option-2\"><br><input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-2\">"+o2+"</textarea><br></div><div id=\"option-3\"><br><input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-3\">"+o3+"</textarea><br></div>";
							else if(answer.equals("2"))
								options="<div id=\"option-1\"><br><input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"options\">"+o1+"</textarea><br></div><div id=\"option-2\"><br><input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\" checked=\"checked\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-2\">"+o2+"</textarea><br></div><div id=\"option-3\"><br><input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-3\">"+o3+"</textarea><br></div>";
							else if(answer.equals("3"))
								options="<div id=\"option-1\"><br><input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"options\">"+o1+"</textarea><br></div><div id=\"option-2\"><br><input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-2\">"+o2+"</textarea><br></div><div id=\"option-3\"><br><input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\" checked=\"checked\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-3\">"+o3+"</textarea><br></div>";
														
						}
						else if(noOfOptionsInAQuestion == 4)
						{
							if(answer.equals("1"))
								options="<div id=\"option-1\"><br><input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\" checked=\"checked\"><textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"options\">"+o1+"</textarea><br></div><div id=\"option-2\"><br><input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-2\">"+o2+"</textarea><br></div><div id=\"option-3\"><br><input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-3\">"+o3+"</textarea><br></div><div id=\"option-4\"><br><input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-4\">"+o4+"</textarea><br></div>";
							else if(answer.equals("2"))
								options="<div id=\"option-1\"><br><input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"options\">"+o1+"</textarea><br></div><div id=\"option-2\"><br><input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\" checked=\"checked\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-2\">"+o2+"</textarea><br></div><div id=\"option-3\"><br><input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-3\">"+o3+"</textarea><br></div><div id=\"option-4\"><br><input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-4\">"+o4+"</textarea><br></div>";
							else if(answer.equals("3"))
								options="<div id=\"option-1\"><br><input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"options\">"+o1+"</textarea><br></div><div id=\"option-2\"><br><input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-2\">"+o2+"</textarea><br></div><div id=\"option-3\"><br><input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\" checked=\"checked\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-3\">"+o3+"</textarea><br></div><div id=\"option-4\"><br><input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-4\">"+o4+"</textarea><br></div>";
							else if(answer.equals("4"))
								options="<div id=\"option-1\"><br><input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"options\">"+o1+"</textarea><br></div><div id=\"option-2\"><br><input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-2\">"+o2+"</textarea><br></div><div id=\"option-3\"><br><input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-3\">"+o3+"</textarea><br></div><div id=\"option-4\"><br><input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\" checked=\"checked\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-4\">"+o4+"</textarea><br></div>";							
						}
						else if(noOfOptionsInAQuestion == 5)
						{
							if(answer.equals("1"))
								options="<div id=\"option-1\"><br><input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\" checked=\"checked\"><textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"options\">"+o1+"</textarea><br></div><div id=\"option-2\"><br><input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-2\">"+o2+"</textarea><br></div><div id=\"option-3\"><br><input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-3\">"+o3+"</textarea><br></div><div id=\"option-4\"><br><input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-4\">"+o4+"</textarea><br></div>"+"<div id=\"option-5\"><br><input type=\"radio\" name=\"options\" value=\"5\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-5\" class=\"options\">"+o5+"</textarea><br></div>";
							else if(answer.equals("2"))
								options="<div id=\"option-1\"><br><input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"options\">"+o1+"</textarea><br></div><div id=\"option-2\"><br><input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\" checked=\"checked\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-2\">"+o2+"</textarea><br></div><div id=\"option-3\"><br><input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-3\">"+o3+"</textarea><br></div><div id=\"option-4\"><br><input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-4\">"+o4+"</textarea><br></div>"+"<div id=\"option-5\"><br><input type=\"radio\" name=\"options\" value=\"5\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-5\" class=\"options\">"+o5+"</textarea><br></div>";
							else if(answer.equals("3"))
								options="<div id=\"option-1\"><br><input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"options\">"+o1+"</textarea><br></div><div id=\"option-2\"><br><input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-2\">"+o2+"</textarea><br></div><div id=\"option-3\"><br><input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\" checked=\"checked\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-3\">"+o3+"</textarea><br></div><div id=\"option-4\"><br><input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-4\">"+o4+"</textarea><br></div>"+"<div id=\"option-5\"><br><input type=\"radio\" name=\"options\" value=\"5\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-5\" class=\"options\">"+o5+"</textarea><br></div>";
							else if(answer.equals("4"))
								options="<div id=\"option-1\"><br><input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"options\">"+o1+"</textarea><br></div><div id=\"option-2\"><br><input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-2\">"+o2+"</textarea><br></div><div id=\"option-3\"><br><input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-3\">"+o3+"</textarea><br></div><div id=\"option-4\"><br><input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\" checked=\"checked\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-4\">"+o4+"</textarea><br></div>"+"<div id=\"option-5\"><br><input type=\"radio\" name=\"options\" value=\"5\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-5\" class=\"options\">"+o5+"</textarea><br></div>";
							else if(answer.equals("5"))
								options="<div id=\"option-1\"><br><input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"options\">"+o1+"</textarea><br></div><div id=\"option-2\"><br><input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-2\">"+o2+"</textarea><br></div><div id=\"option-3\"><br><input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-3\">"+o3+"</textarea><br></div><div id=\"option-4\"><br><input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-4\">"+o4+"</textarea><br></div>"+"<div id=\"option-5\"><br><input type=\"radio\" name=\"options\" value=\"5\" class=\"radio\" checked=\"checked\"><textarea onclick=\"clearParagraph()\" name=\"option-5\" class=\"options\">"+o5+"</textarea><br></div>";
						}
						else if(noOfOptionsInAQuestion == 6)
						{
							if(answer.equals("1"))
								options="<div id=\"option-1\"><br><input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\" checked=\"checked\"><textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"options\">"+o1+"</textarea><br></div><div id=\"option-2\"><br><input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-2\">"+o2+"</textarea><br></div><div id=\"option-3\"><br><input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-3\">"+o3+"</textarea><br></div><div id=\"option-4\"><br><input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-4\">"+o4+"</textarea><br></div>"+"<div id=\"option-5\"><br><input type=\"radio\" name=\"options\" value=\"5\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-5\" class=\"options\">"+o5+"</textarea><br></div>"+"<div id=\"option-6\"><br><input type=\"radio\" name=\"options\" value=\"6\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-6\" class=\"options\">"+o6+"</textarea><br></div>";
							else if(answer.equals("2"))
								options="<div id=\"option-1\"><br><input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"options\">"+o1+"</textarea><br></div><div id=\"option-2\"><br><input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\" checked=\"checked\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-2\">"+o2+"</textarea><br></div><div id=\"option-3\"><br><input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-3\">"+o3+"</textarea><br></div><div id=\"option-4\"><br><input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-4\">"+o4+"</textarea><br></div>"+"<div id=\"option-5\"><br><input type=\"radio\" name=\"options\" value=\"5\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-5\" class=\"options\">"+o5+"</textarea><br></div>"+"<div id=\"option-6\"><br><input type=\"radio\" name=\"options\" value=\"6\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-6\" class=\"options\">"+o6+"</textarea><br></div>";
							else if(answer.equals("3"))
								options="<div id=\"option-1\"><br><input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"options\">"+o1+"</textarea><br></div><div id=\"option-2\"><br><input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-2\">"+o2+"</textarea><br></div><div id=\"option-3\"><br><input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\" checked=\"checked\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-3\">"+o3+"</textarea><br></div><div id=\"option-4\"><br><input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-4\">"+o4+"</textarea><br></div>"+"<div id=\"option-5\"><br><input type=\"radio\" name=\"options\" value=\"5\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-5\" class=\"options\">"+o5+"</textarea><br></div>"+"<div id=\"option-6\"><br><input type=\"radio\" name=\"options\" value=\"6\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-6\" class=\"options\">"+o6+"</textarea><br></div>";
							else if(answer.equals("4"))
								options="<div id=\"option-1\"><br><input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"options\">"+o1+"</textarea><br></div><div id=\"option-2\"><br><input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-2\">"+o2+"</textarea><br></div><div id=\"option-3\"><br><input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-3\">"+o3+"</textarea><br></div><div id=\"option-4\"><br><input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\" checked=\"checked\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-4\">"+o4+"</textarea><br></div>"+"<div id=\"option-5\"><br><input type=\"radio\" name=\"options\" value=\"5\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-5\" class=\"options\">"+o5+"</textarea><br></div>"+"<div id=\"option-6\"><br><input type=\"radio\" name=\"options\" value=\"6\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-6\" class=\"options\">"+o6+"</textarea><br></div>";
							else if(answer.equals("5"))
								options="<div id=\"option-1\"><br><input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"options\">"+o1+"</textarea><br></div><div id=\"option-2\"><br><input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-2\">"+o2+"</textarea><br></div><div id=\"option-3\"><br><input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-3\">"+o3+"</textarea><br></div><div id=\"option-4\"><br><input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-4\">"+o4+"</textarea><br></div>"+"<div id=\"option-5\"><br><input type=\"radio\" name=\"options\" value=\"5\" class=\"radio\" checked=\"checked\"><textarea onclick=\"clearParagraph()\" name=\"option-5\" class=\"options\">"+o5+"</textarea><br></div>"+"<div id=\"option-6\"><br><input type=\"radio\" name=\"options\" value=\"6\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-6\" class=\"options\">"+o6+"</textarea><br></div>";
							else if(answer.equals("6"))
								options="<div id=\"option-1\"><br><input type=\"radio\" name=\"options\" value=\"1\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-1\" class=\"options\">"+o1+"</textarea><br></div><div id=\"option-2\"><br><input type=\"radio\" name=\"options\" value=\"2\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-2\">"+o2+"</textarea><br></div><div id=\"option-3\"><br><input type=\"radio\" name=\"options\" value=\"3\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-3\">"+o3+"</textarea><br></div><div id=\"option-4\"><br><input type=\"radio\" name=\"options\" value=\"4\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"option-4\">"+o4+"</textarea><br></div>"+"<div id=\"option-5\"><br><input type=\"radio\" name=\"options\" value=\"5\" class=\"radio\"><textarea onclick=\"clearParagraph()\" name=\"option-5\" class=\"options\">"+o5+"</textarea><br></div>"+"<div id=\"option-6\"><br><input type=\"radio\" name=\"options\" value=\"6\" class=\"radio\" checked=\"checked\"><textarea onclick=\"clearParagraph()\" name=\"option-6\" class=\"options\">"+o6+"</textarea><br></div>";
						}	
					}
					else if(typeOfQuestion.equals("3"))
					{
						String MAQOptions1 = "<div id=\"MAQoption-1\"><br><input type=\"checkbox\" name=\"options\" value=\"1\" class=\"checkbox\"><textarea onclick=\"clearParagraph()\" class=\"options\" name=\"MAQoption-1\">"+o1+"</textarea><br></div>";
						String MAQOptions2 = "<div id=\"MAQoption-2\"><br><input type=\"checkbox\" name=\"options\" value=\"2\" class=\"checkbox\"><textarea onclick=\"clearParagraph()\" class=\"options\" name=\"MAQoption-2\">"+o2+"</textarea><br></div>";
						String MAQOptions3 = "<div id=\"MAQoption-3\"><br><input type=\"checkbox\" name=\"options\" value=\"3\" class=\"checkbox\"><textarea onclick=\"clearParagraph()\" class=\"options\" name=\"MAQoption-3\">"+o3+"</textarea><br></div>";
						String MAQOptions4 = "<div id=\"MAQoption-4\"><br><input type=\"checkbox\" name=\"options\" value=\"4\" class=\"checkbox\"><textarea onclick=\"clearParagraph()\" class=\"options\" name=\"MAQoption-4\">"+o4+"</textarea><br></div>";
						String MAQOptions5 = "<div id=\"MAQoption-5\"><br><input type=\"checkbox\" name=\"options\" value=\"5\" class=\"checkbox\"><textarea onclick=\"clearParagraph()\" class=\"options\" name=\"MAQoption-5\">"+o5+"</textarea><br></div>";
						String MAQOptions6 = "<div id=\"MAQoption-6\"><br><input type=\"checkbox\" name=\"options\" value=\"6\" class=\"checkbox\"><textarea onclick=\"clearParagraph()\" class=\"options\" name=\"MAQoption-6\">"+o6+"</textarea><br></div>";
						
						style3="style=\"display:block;\"";
						if(answer.contains("1"))
							MAQOptions1 = "<div id=\"MAQoption-1\"><br><input type=\"checkbox\" name=\"options\" value=\"1\" class=\"checkbox\" checked=\"checked\"><textarea onclick=\"clearParagraph()\" class=\"options\" name=\"MAQoption-1\">"+o1+"</textarea><br></div>";
						if(answer.contains("2"))
							MAQOptions2 = "<div id=\"MAQoption-2\"><br><input type=\"checkbox\" name=\"options\" value=\"2\" class=\"checkbox\" checked=\"checked\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"MAQoption-2\">"+o2+"</textarea><br></div>";
						if(answer.contains("3"))
							MAQOptions3 = "<div id=\"MAQoption-3\"><br><input type=\"checkbox\" name=\"options\" value=\"3\" class=\"checkbox\" checked=\"checked\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"MAQoption-3\">"+o3+"</textarea><br></div>";
						if(answer.contains("4"))
							MAQOptions4 = "<div id=\"MAQoption-4\"><br><input type=\"checkbox\" name=\"options\" value=\"4\" class=\"checkbox\" checked=\"checked\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"MAQoption-4\">"+o4+"</textarea><br></div>";
						if(answer.contains("5"))
							MAQOptions5 = "<div id=\"MAQoption-5\"><br><input type=\"checkbox\" name=\"options\" value=\"5\" class=\"checkbox\" checked=\"checked\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"MAQoption-5\">"+o5+"</textarea><br></div>";
						if(answer.contains("6"))
							MAQOptions6 = "<div id=\"MAQoption-6\"><br><input type=\"checkbox\" name=\"options\" value=\"6\" class=\"checkbox\" checked=\"checked\"><textarea  onclick=\"clearParagraph()\" class=\"options\" name=\"MAQoption-6\">"+o6+"</textarea><br></div>";
					
						MAQOptions = MAQOptions1 + MAQOptions2 + MAQOptions3 + MAQOptions4;
						System.out.println(MAQOptions);
						
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
					
					
					String TandFdiv = "<br><div id=\"TandF\""+style1+">";
					String closeTandFdiv="</div>";
					
					String optionDiv="<div id=\"multipleChoiceQuestions\""+style2+"><div class=\"option\" name=\"mcqoptions\">";
					String closeDiv="</div></div>";
					
					String MAQDiv = "<div id=\"MAQ\""+ style3+"><div class=\"MAQoption\">";
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
						out.println(html1+header+breadCrumb+cardBorderDiv+block+form+tid+qno+noq1+textArea+dropdown+positiveMarks+negativeMarks+timeLimit+label+TandFdiv+TandFoptions+closeTandFdiv+optionDiv+options+closeDiv+MAQDiv+MAQOptions+closeMAQDiv+p+linkDiv+addOption+slash+removeOption+linkDivClose+previousButton+button1+button3+html2);
					else
						out.println(html1+header+breadCrumb+cardBorderDiv+block+form+tid+qno+noq1+textArea+dropdown+positiveMarks+negativeMarks+timeLimit+label+TandFdiv+TandFoptions+closeTandFdiv+optionDiv+options+closeDiv+MAQDiv+MAQOptions+closeMAQDiv+p+linkDiv+addOption+slash+removeOption+linkDivClose+previousButton+button1+button2+html2);
					
				}
	
			}
		}catch(Exception e)
		{
			out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();
		}
	}

}
