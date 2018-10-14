package dynamicResponses;

public class Misc {

	public String getRandomQuestionID(String[] questionIDs)
	{
		
		return null;
	}
	
	public String HtmlHeader()
	{
		String cardStart = "<div class=\"card border-0\">";
		String cardHeader = "<div class=\"card-header\" style=\"background-color:#eaeced;\"><h5>Exam Flux<form action=\"/Logout\" method=\"post\"><input name=\"Submit\" type=\"submit\" value=\"Logout\" id=\"Logout\" class=\"btn btn-secondary float-right\"></form></h5></div>";
		
		String header = cardStart+cardHeader;
		return header;
	}
	
	public void recordRemainingTime(String dateTime)
	{
		/*
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = df.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	//	long epoch = date.getTime();
		*/
		
	}
	

}
