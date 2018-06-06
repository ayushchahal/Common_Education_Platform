package dynamicResponses;

public class Misc {

	public String getRandomQuestionID(String[] questionIDs)
	{
		
		return null;
	}
	
	public String HtmlHeader()
	{
		String cardStart = "<div class=\"card border-0\">";
		String cardHeader = "<div class=\"card-header\" style=\"background-color:#eaeced;\"><h5>Test Plant<form action=\"/Logout\" method=\"post\"><input name=\"Submit\" type=\"submit\" value=\"Logout\" id=\"Logout\" class=\"btn btn-secondary float-right\"></form></h5></div>";
		
		String header = cardStart+cardHeader;
		return header;
	}
	

}
