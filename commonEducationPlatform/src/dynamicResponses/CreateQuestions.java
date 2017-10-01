package dynamicResponses;

public class CreateQuestions {

	public String createQuestionsUI(int noq, int questionNumber)
	{
		String html1="<html>\r\n" + 
				"	<head>\r\n" + 
				"		<meta charset=\"ISO-8859-1\">\r\n" + 
				"		<title>Create a test</title>\r\n" + 
				"		<link rel=\"stylesheet\" href=\"questions.css\">\r\n" + 
				"<script src=\"addRemoveOptions.js\"></script>"+
				"<script src=\"changeQType.js\"></script>"+
				"	</head>\r\n" + 
				"<body>";
		
		String block="<div id=\"content\" class=\"block\">";
		String form="<form action=\"SaveTest\" method=\"Post\">";
		String textArea="<label style>Question "+questionNumber+" of "+noq+"</label><br>"+"<textarea id=\"question\" name=\"question\"></textarea><br><br>";
		String dropdown="<div class=\"lhs\"><label style>Type of Question:</label><br><select name=\"qType\" id=\"qType\" onChange=\"changeQType()\"><option value=\"1\">True/False</option><option value=\"2\" selected>Multiple choice</option><option value=\"3\">Multiple response</option><option value=\"4\">Fill in the blank</option></select></div>";
		String positiveMarks="<div class=\"rhs\"><label style>Positive Marks:</label><input id=\"pmarks\" name=\"pmarks\" size=\"3\" type=\"number\" minimun=\"0\" step=\"0.01\" value=\"1\"></div><br><br>";
		String negativeMarks="<div class=\"rhs\"><label style>Negative Marks:</label><input id=\"nmarks\" name=\"nmarks\" size=\"3\" type=\"number\" minimun=\"0\" step=\"0.01\" value=\"0\"></div><br><br><br>";
		
		String label="<label>Enter the options, and mark which answer is correct</label><br>";
		String TandFdiv = "<br><div id=\"TandF\" style=\"display:none;\">";
		String TandFoptions = "<div id=\"option-1\"><input type=\"radio\" name=\"options\" class=\"radio\" value=\"true\" style=\"vertical-align: top;\">True</div><br><div id=\"option-2\"><input type=\"radio\" name=\"options\" class=\"radio\" value=\"false\" style=\"vertical-align: top;\">False</div>";
		String closeTandFdiv="</div>";
		
		String optionDiv="<div id=\"multipleChoiceQuestions\"><div class=\"option\" name=\"mcqoptions\">";
		String options="<div id=\"option-1\"><br><input type=\"radio\" name=\"options\" class=\"radio\"><textarea onclick=\"clearParagraph()\" class=\"options\"></textarea><br></div><div id=\"option-2\"><br><input type=\"radio\" name=\"options\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\"></textarea><br></div><div id=\"option-3\"><br><input type=\"radio\" name=\"options\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\"></textarea><br></div><div id=\"option-4\"><br><input type=\"radio\" name=\"options\" class=\"radio\"><textarea  onclick=\"clearParagraph()\" class=\"options\"></textarea><br></div>";
		String closeDiv="</div></div>";
		
		String MAQDiv = "<div id=\"MAQ\" style=\"display:none;\"><div class=\"MAQoption\">";
		String MAQOptions = "<div id=\"MAQoption-1\"><br><input type=\"checkbox\" name=\"options\" class=\"checkbox\"><textarea onclick=\"clearParagraph()\" class=\"options\"></textarea><br></div><div id=\"MAQoption-2\"><br><input type=\"checkbox\" name=\"options\" class=\"checkbox\"><textarea  onclick=\"clearParagraph()\" class=\"options\"></textarea><br></div><div id=\"MAQoption-3\"><br><input type=\"checkbox\" name=\"options\" class=\"checkbox\"><textarea  onclick=\"clearParagraph()\" class=\"options\"></textarea><br></div><div id=\"MAQoption-4\"><br><input type=\"checkbox\" name=\"options\" class=\"checkbox\"><textarea  onclick=\"clearParagraph()\" class=\"options\"></textarea><br></div>";
		String closeMAQDiv="</div></div>";
		
		String p="<p id=\"optionsValidator\"></p>";
		String linkDiv="<div id=\"link\">";
		String addOption="<br><a onclick=\"addOption()\" href=\"#\">Add Option</a>";
		String slash="<label> / </label>";
		String removeOption="<a onclick=\"removeOption()\" href=\"#\">Remove Option</a>";
		String linkDivClose="</div>";
		String button1="<br><br><input class=\"lhs\" type=\"submit\" name=\"save\" value=\"Save and Exit\">";
		String reset="<input class=\"center\" type=\"reset\" name=\"reset\" value=\"Reset\">";
		String button2="<input class=\"rhs\" type=\"submit\" name=\"n	ext\" value=\"To Next Question\">";
		String html2="</form></div></body></html>";
		
		return html1+block+form+textArea+dropdown+positiveMarks+negativeMarks+label+TandFdiv+TandFoptions+closeTandFdiv+optionDiv+options+closeDiv+MAQDiv+MAQOptions+closeMAQDiv+p+linkDiv+addOption+slash+removeOption+linkDivClose+button1+reset+button2+html2;
		
	}
	
}
