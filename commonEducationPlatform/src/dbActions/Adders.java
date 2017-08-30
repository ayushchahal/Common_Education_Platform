package dbActions;

import java.sql.SQLException;
import java.sql.Statement;

public class Adders {

	public boolean insertCoachingDetails(String coachingName, String A1, String A2, String C, String State, String C1, String C2, String e, String IsActive )
	{
		Statement ps;
		boolean status=false;
		try {
			ps = DBConnection.con.createStatement();
			//String sql = "insert into coachingDetails (CoachingName,Address1,Address2,City,State,ContactNumber1,ContactNumber2,Email,IsActive) values (\""+coachingName+"\",\""+A1+"\",\""+A2+"\",\""+C+"\",\""+State+"\",\""+C1+"\",\""+C2+"\",\""+e+"\","+IsActive+")";
			//System.out.println(sql);
			ps.executeUpdate("insert into coachingDetails (CoachingName,Address1,Address2,City,State,ContactNumber1,ContactNumber2,Email,IsActive) values (\""+coachingName+"\",\""+A1+"\",\""+A2+"\",\""+C+"\",\""+State+"\",\""+C1+"\",\""+C2+"\",\""+e+"\","+IsActive+")");
			status=true;
		} catch (SQLException ex) 
		{
			ex.printStackTrace();
		}
		return status;
	}
}
