package dynamicResponses;

public class CreateCoachingLogin {

	public String createUserID(String COACHING_NAME, String COACHING_CITY)
	{
		String loginID=COACHING_NAME+"@"+COACHING_CITY;
		loginID=loginID.replaceAll("\\s","");
		return loginID;
		
	}
	
	public String createPassword(String COACHING_NAME, String COACHING_CITY)
	{
		String passwd=COACHING_NAME+"@"+COACHING_CITY;
		passwd=passwd.replaceAll("\\s","");
		return passwd;
	}
}
