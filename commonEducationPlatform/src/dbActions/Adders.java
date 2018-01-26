package dbActions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Adders {
	
	
	public Connection connectToDB()
	{
		Connection con=null;
		try{   
			Class.forName("com.mysql.jdbc.Driver");  
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/coachingmanagement","root","ayush");   				  
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return con;
	}
	
	public void destroyConnection(Connection con)
	{
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean insertCoachingDetails(String coachingName, String A1, String A2, String C, String State, String C1, String C2, String e, String IsActive )
	{
		Statement ps;
		boolean status=false;
		Connection con=connectToDB();
		try {
			ps = con.createStatement();
			//String sql = "insert into coachingDetails (CoachingName,Address1,Address2,City,State,ContactNumber1,ContactNumber2,Email,IsActive) values (\""+coachingName+"\",\""+A1+"\",\""+A2+"\",\""+C+"\",\""+State+"\",\""+C1+"\",\""+C2+"\",\""+e+"\","+IsActive+")";
			//System.out.println(sql);
			ps.executeUpdate("insert into coachingDetails (CoachingName,Address1,Address2,City,State,ContactNumber1,ContactNumber2,Email,IsActive) values (\""+coachingName+"\",\""+A1+"\",\""+A2+"\",\""+C+"\",\""+State+"\",\""+C1+"\",\""+C2+"\",\""+e+"\","+IsActive+")");
			status=true;
		} catch (SQLException ex) 
		{
			ex.printStackTrace();
		}
		destroyConnection(con);
		return status;
	}
	
	
	public boolean insertTeacherDetails(String tname, String c1, String e, String s, String cname)
	{
		Statement ps;
		boolean status=false;
		Connection con=connectToDB();
		String CoachingID = new DBConnection().getCoachingID(cname);
		
		try {
			ps = con.createStatement();
			String sql = "insert into teacherdetails (TeacherName,ContactNumber,Email,IsActive,CoachingID)values(\""+tname+"\",\""+c1+"\",\""+e+"\",\""+s+"\",\""+CoachingID+"\")";
			//System.out.println(sql);
			ps.executeUpdate(sql);
			status=true;
		} catch (SQLException ex) 
		{
			ex.printStackTrace();
		}
		destroyConnection(con);
		return status;
	}
	
	
	public boolean insertStudentDetails(String sname, String c1, String e, String s, String cname)
	{
		Statement ps;
		boolean status=false;
		Connection con=connectToDB();
		String CoachingID = new DBConnection().getCoachingID(cname);
		try {
			ps = con.createStatement();
			String sql = "insert into studentdetails (StudentName,ContactNumber,Email,IsActive,CoachingID)values(\""+sname+"\",\""+c1+"\",\""+e+"\",\""+s+"\",\""+CoachingID+"\")";
			//System.out.println(sql);
			ps.executeUpdate(sql);
			status=true;
		} catch (SQLException ex) 
		{
			ex.printStackTrace();
		}
		destroyConnection(con);
		return status;
	}
	
	
	
	public boolean insertLoginDetails(String username, String passwd,String LoginTypeID, String coachingID)
	{
		Statement ps;
		boolean status=false;
		Connection con=connectToDB();
		String FailedLoginAttempts="0";
		try {
			ps = con.createStatement();
			//String sql = "insert into logindetails (UserName,Password,FailedLoginAttempts,LoginTypeID) values (\""+username+"\",\""+passwd+"\",\""+FailedLoginAttempts+"\","+LoginTypeID+")";
			//System.out.println(sql);
			ps.executeUpdate("insert into logindetails (UserName,Password,FailedLoginAttempts,LoginTypeID, LoginUserID) values (\""+username+"\",\""+passwd+"\",\""+FailedLoginAttempts+"\", \""+LoginTypeID+"\","+coachingID+")");
			status=true;
		} catch (SQLException ex) 
		{
			ex.printStackTrace();
		}
		destroyConnection(con);
		return status;
	}
	
}
