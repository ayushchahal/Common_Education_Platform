package dbActions;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import servletActions.LoginServlet1;
import servletActions.ViewCoachingServlet;
import servletActions.ViewStudentsServlet;
import servletActions.ViewTeachersServlet;

import java.sql.Connection;

public class DBConnection { 
	
	public static Connection con=null;
	
	
	public static void connectToDB()
	{
		try{   
			Class.forName("com.mysql.jdbc.Driver");  
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/coachingmanagement","root","ayush");   				  
		}catch(Exception e)
		{
			e.printStackTrace();
		}   
	}
	
	public boolean validateUserNameandPassowrd(String username, String password)
	{
		boolean status=false;
		try{ 
			PreparedStatement ps=DBConnection.con.prepareStatement("select username,password from LoginDetails where username=? and password=?");
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs=ps.executeQuery(); 
			status=rs.next();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}  
		
		return status;
	}
	
	public int getNumberOfCoachings()
	{
		PreparedStatement ps;
		int count=0;
		try {
			ps = DBConnection.con.prepareStatement("select Count(ID) from coachingDetails");
			ResultSet rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return count;
	}
	
	
	public String[] getAllCoachingDetails()
	{
		int n= getNumberOfCoachings();
		String[] data = new String[n];
		String htmlTable[]= new String[n];

		try {
			PreparedStatement ps=DBConnection.con.prepareStatement("select ID,CoachingName,Address1,Address2,City,ContactNumber1,Email,IsActive from coachingDetails");
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
		
		ViewCoachingServlet.Coaching_out.println("<html>"+"<style>");
		ViewCoachingServlet.Coaching_out.println("table {\r\n" + 
				"    font-family: arial, sans-serif;\r\n" + 
				"    border-collapse: collapse;\r\n" + 
				"    width: 100%;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"td, th {\r\n" + 
				"    border: 1px solid #dddddd;\r\n" + 
				"    text-align: left;\r\n" + 
				"    padding: 8px;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"tr:nth-child(even) {\r\n" + 
				"    background-color: #dddddd;\r\n" + 
				"}");
		
		ViewCoachingServlet.Coaching_out.println("</style>");
		ViewCoachingServlet.Coaching_out.println("<body>");
		ViewCoachingServlet.Coaching_out.println("<center>");
		ViewCoachingServlet.Coaching_out.println("<table>");
		String tableheaders = "<tr><th>Coaching ID</th><th>Name</th><th>Address1</th><th>Address2</th><th>City</th><th>Phone Number</th><th>Email</th><th>IsActive</th></tr>";
		ViewCoachingServlet.Coaching_out.println(tableheaders);
		for(int j=0;j<n;j++)
		{
			ViewCoachingServlet.Coaching_out.println(htmlTable[j]);
		}
		
		ViewCoachingServlet.Coaching_out.println("</table>");
		ViewCoachingServlet.Coaching_out.println("</center>");
		ViewCoachingServlet.Coaching_out.println("</body></html>");
		
		return data;
		
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
		try {
			ps = DBConnection.con.prepareStatement("select Count(ID) from studentDetails");
			ResultSet rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return count;
	}
	
	
	public void getAllStudentDetails()
	{
		int n= getNumberOfStudents();
		String htmlTable[]= new String[n];
		try {
			PreparedStatement ps=DBConnection.con.prepareStatement("select s.ID,s.StudentName,s.ContactNumber,s.Email,s.IsActive,c.CoachingName from studentDetails s inner join coachingdetails c on c.ID=s.CoachingID");
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
		
		ViewStudentsServlet.Coaching_out.println("<html>"+"<style>");
		ViewStudentsServlet.Coaching_out.println("table {\r\n" + 
				"    font-family: arial, sans-serif;\r\n" + 
				"    border-collapse: collapse;\r\n" + 
				"    width: 100%;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"td, th {\r\n" + 
				"    border: 1px solid #dddddd;\r\n" + 
				"    text-align: left;\r\n" + 
				"    padding: 8px;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"tr:nth-child(even) {\r\n" + 
				"    background-color: #dddddd;\r\n" + 
				"}");
		
		ViewStudentsServlet.Coaching_out.println("</style>");
		ViewStudentsServlet.Coaching_out.println("<body>");
		//ViewStudentsServlet.Coaching_out.println("<center>");
		ViewStudentsServlet.Coaching_out.println("<table>");
		String tableheaders = "<tr><th>Student ID</th><th>Name</th><th>Contact No.</th><th>Email</th><th>IsActive</th><th>CoachingName</th></tr>";
		ViewStudentsServlet.Coaching_out.println(tableheaders);
		for(int j=0;j<n;j++)
		{
			ViewStudentsServlet.Coaching_out.println(htmlTable[j]);
		}
		
		ViewStudentsServlet.Coaching_out.println("</table>");
		//ViewStudentsServlet.Coaching_out.println("</center>");
		ViewStudentsServlet.Coaching_out.println("</body></html>");
		

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
		try {
			ps = DBConnection.con.prepareStatement("select Count(ID) from teacherDetails");
			ResultSet rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return count;
	}
	
	
	public void getAllTeachersDetails()
	{
		int n= getNumberOfTeachers();
		String htmlTable[]= new String[n];
		try {
			PreparedStatement ps=DBConnection.con.prepareStatement("select T.ID,T.TeacherName,T.ContactNumber,T.Email,T.IsActive,c.CoachingName from TeacherDetails T inner join coachingdetails c on c.ID=T.CoachingID");
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
		
		ViewTeachersServlet.Coaching_out.println("<html>"+"<style>");
		ViewTeachersServlet.Coaching_out.println("table {\r\n" + 
				"    font-family: arial, sans-serif;\r\n" + 
				"    border-collapse: collapse;\r\n" + 
				"    width: 100%;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"td, th {\r\n" + 
				"    border: 1px solid #dddddd;\r\n" + 
				"    text-align: left;\r\n" + 
				"    padding: 8px;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"tr:nth-child(even) {\r\n" + 
				"    background-color: #dddddd;\r\n" + 
				"}");
		
		ViewTeachersServlet.Coaching_out.println("</style>");
		ViewTeachersServlet.Coaching_out.println("<body>");
		ViewTeachersServlet.Coaching_out.println("<table>");
		String tableheaders = "<tr><th>Teacher ID</th><th>Name</th><th>Contact No.</th><th>Email</th><th>IsActive</th><th>CoachingName</th></tr>";
		ViewTeachersServlet.Coaching_out.println(tableheaders);
		for(int j=0;j<n;j++)
		{
			ViewTeachersServlet.Coaching_out.println(htmlTable[j]);
		}
		
		ViewTeachersServlet.Coaching_out.println("</table>");
		ViewTeachersServlet.Coaching_out.println("</body></html>");
		

	}
	
	public String putTeachersDataIntoHTMLTable(String ID, String Tname, String C, String e,String S, String Cname)
	{
		String table = null;
		table= "<tr><td>"+ID+"</td><td>"+Tname+"</td><td>"+C+"</td><td>"+e+"</td><td>"+S+"</td><td>"+Cname+"</td></tr>";
		return table;
	}
	
}
