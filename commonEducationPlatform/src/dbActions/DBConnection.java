package dbActions;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;

public class DBConnection { 
	
	//public Connection con=null;

	public Connection connectToDB()
	{
		Connection con = null;
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
	
	public int getLoginType(String username)
	{
		int loginType=0;
		Connection con=connectToDB();
		try{ 
			//Connection con=connectToDB();
			PreparedStatement ps=con.prepareStatement("select LoginTypeID from LoginDetails where username=?");
			ps.setString(1, username);
			ResultSet rs=ps.executeQuery(); 
			rs.next();
			loginType=rs.getInt(1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}  
		destroyConnection(con);
		return loginType;
		
	}
	
	public boolean validateUserNameandPassowrd(String username, String password)
	{
		boolean status=false;
		Connection con=connectToDB();
		try{ 
			//Connection con=connectToDB();
			PreparedStatement ps=con.prepareStatement("select username,password from LoginDetails where username=? and password=?");
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs=ps.executeQuery(); 
			rs.next();
			String dbusername=rs.getString(1);
			String dbpassword=rs.getString(2);
			
			if(dbusername.equalsIgnoreCase(username) && dbpassword.equals(password))
				status=true;
			else
				status=false;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}  
		destroyConnection(con);
		return status;
	}
	
	public int getNumberOfCoachings()
	{
		PreparedStatement ps;
		int count=0;
		Connection con=connectToDB();
		try {			
			ps = con.prepareStatement("select Count(ID) from coachingDetails");
			ResultSet rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		return count;
	}
	
	public String[] getAllCoachingDetails()
	{
		int n= getNumberOfCoachings();
		//String[] data = new String[n];
		String htmlTable[]= new String[n];
		Connection con=connectToDB();
		try {
			
			PreparedStatement ps=con.prepareStatement("select ID,CoachingName,Address1,Address2,City,ContactNumber1,Email,IsActive from coachingDetails");
			ResultSet rs = ps.executeQuery();
			
			int i=0;
			
			while(rs.next())
			{
				String ID = rs.getString(1);
				String CoachingName = rs.getString(2);
				String Address1 = rs.getString(3);
				String Address2 = rs.getString(4);
				String City = rs.getString(5);
				String PNo = rs.getString(6);
				String Email = rs.getString(7);
				String IsActive = rs.getString(8);
				htmlTable[i]=putCoachingDataIntoHTMLTable(ID, CoachingName, Address1,Address2,City,PNo,Email,IsActive);
				i=i+1;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		destroyConnection(con);
		return htmlTable;
		
	}
	
	public String putCoachingDataIntoHTMLTable(String ID, String Cname, String a1, String a2,String city, String pno, String e, String S)
	{
		String table = null;
		table= "<tr><td>"+ID+"</td><td>"+Cname+"</td><td>"+a1+"</td><td>"+a2+"</td><td>"+city+"</td><td>"+pno+"</td><td>"+e+"</td><td>"+S+"</td></tr>";
		return table;
	}
	
	public int getNumberOfStudents()
	{
		PreparedStatement ps;
		int count=0;
		Connection con=connectToDB();
		try {			
			ps = con.prepareStatement("select Count(ID) from studentDetails");
			ResultSet rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		return count;
	}
	
	
	public String[] getAllStudentDetails()
	{
		int n= getNumberOfStudents();
		String htmlTable[]= new String[n];
		Connection con=connectToDB();
		try {
			PreparedStatement ps=con.prepareStatement("select s.ID,s.StudentName,s.ContactNumber,s.Email,s.IsActive,c.CoachingName from studentDetails s inner join coachingdetails c on c.ID=s.CoachingID");
			ResultSet rs = ps.executeQuery();
			
			int i=0;
			while(rs.next())
			{
				String ID = rs.getString(1);
				String StudentName = rs.getString(2);
				String CNo = rs.getString(3);
				String Email = rs.getString(4);
				String IsActive = rs.getString(5);
				String CoachingName = rs.getString(6);
				htmlTable[i]=putStudentDataIntoHTMLTable(ID, StudentName, CNo,Email,IsActive,CoachingName);
				i=i+1;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		return htmlTable;
	}
	
	public String putStudentDataIntoHTMLTable(String ID, String Sname, String C, String e,String S, String Cname)
	{
		String table = null;
		table= "<tr><td>"+ID+"</td><td>"+Sname+"</td><td>"+C+"</td><td>"+e+"</td><td>"+S+"</td><td>"+Cname+"</td></tr>";
		return table;
	}
	
	public int getNumberOfTeachers()
	{
		PreparedStatement ps;
		int count=0;
		Connection con=connectToDB();
		try {		
			ps = con.prepareStatement("select Count(ID) from teacherDetails");
			ResultSet rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		return count;
	}
	
	
	public String[] getAllTeachersDetails()
	{
		int n= getNumberOfTeachers();
		String htmlTable[]= new String[n];
		Connection con=connectToDB();
		try {		
			PreparedStatement ps=con.prepareStatement("select T.ID,T.TeacherName,T.ContactNumber,T.Email,T.IsActive,c.CoachingName from TeacherDetails T inner join coachingdetails c on c.ID=T.CoachingID");
			ResultSet rs = ps.executeQuery();
			
			int i=0;
			while(rs.next())
			{
				String ID = rs.getString(1);
				String TeacherName = rs.getString(2);
				String CNo = rs.getString(3);
				String Email = rs.getString(4);
				String IsActive = rs.getString(5);
				String CoachingName = rs.getString(6);
				htmlTable[i]=putTeachersDataIntoHTMLTable(ID, TeacherName, CNo,Email,IsActive,CoachingName);
				i=i+1;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		return htmlTable;

	}
	
	public String putTeachersDataIntoHTMLTable(String ID, String Tname, String C, String e,String S, String Cname)
	{
		String table = null;
		table= "<tr><td>"+ID+"</td><td>"+Tname+"</td><td>"+C+"</td><td>"+e+"</td><td>"+S+"</td><td>"+Cname+"</td></tr>";
		return table;
	}
	
	
	public String[] getAllCoachingName()
	{
		int n= getNumberOfCoachings();
		String data[]= new String[n];
		PreparedStatement ps;
		int i=0;
		Connection con=connectToDB();
		try {	
			ps = con.prepareStatement("select CoachingName from coachingDetails");
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				data[i]=rs.getString(1);
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		destroyConnection(con);
		return data;
		
	}
	
	public String getCoachingID(String cName)
	{
		PreparedStatement ps;
		String ID = "1";
		Connection con=connectToDB();
		try {
			String query = "select ID from coachingDetails where CoachingName ='"+cName+"'";
			//System.out.println(query);
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			rs.next();
			ID = rs.getString("ID");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		destroyConnection(con);
		return ID;

	}
	
}