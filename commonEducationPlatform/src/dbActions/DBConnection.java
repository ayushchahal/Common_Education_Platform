package dbActions;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	
	public int getLoginID(String username)
	{
		int loginType=0;
		Connection con=connectToDB();
		try{ 
			//Connection con=connectToDB();
			PreparedStatement ps=con.prepareStatement("select ID from LoginDetails where username=?");
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
			System.out.println("User name or password entered by the user does not exist in the database");
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
		if(S.equals("0"))
			S="In Active";
		else
			S="Active";
		//table= "<tr name=\"ID\"><td>"+ID+"</td><td>"+Sname+"</td><td>"+C+"</td><td>"+e+"</td><td>"+S+"</td><td>"+Cname+"</td><td>"+"<a href=\"/StudentDetail\">Details</a>"+"</td></tr>";
		table= "<tr><td>"+ID+"</td><td>"+Sname+"</td><td>"+C+"</td><td>"+e+"</td><td>"+S+"</td><td>"+Cname+"</td><td>"+"<form action=\"/StudentDetail\" method=\"Post\">"+"<input type=\"hidden\" name=\"sid\" value=\""+ID+"\">"+"<input type=\"submit\" name=\"details\" class=\"btn btn-info\" value=\"Details\"></form></td></tr>";
		return table;
	}
	
	public String putTeachersDataIntoHTMLTable(String ID, String Tname, String C, String e,String S, String Cname, int loginType)
	{
		String table = null;
		table= "<tr><td>"+ID+"</td><td>"+Tname+"</td><td>"+C+"</td><td>"+e+"</td><td>"+S+"</td><td>"+Cname+"</td></tr>";
		if(loginType==2)
		{
			table= "<tr><td>"+ID+"</td><td>"+Tname+"</td><td>"+C+"</td><td>"+e+"</td><td>"+"<form action=\"/DeleteTeacher\" method=\"Post\">"+"<input type=\"hidden\" name=\"tid\" value=\""+ID+"\">"+"<input type=\"submit\" name=\"editOrDeleteDetails\" value=\"Delete\" class=\"btn btn-danger\"> <input type=\"submit\" name=\"editOrDeleteDetails\" class=\"btn btn-info\" value=\"Edit\"></form></td></tr>";;
		}
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
				htmlTable[i]=putTeachersDataIntoHTMLTable(ID, TeacherName, CNo,Email,IsActive,CoachingName,1);
				i=i+1;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		return htmlTable;

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
	
	public String getCoachingID(String coachingName)
	{
		PreparedStatement ps;
		String ID = "1";
		Connection con=connectToDB();
		try {
			String query = "select ID from coachingDetails where CoachingName =\""+coachingName+"\"";
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
	
	public String getCoachingIDFromTeacherName(String teacherName)
	{
		PreparedStatement ps;
		String ID = "1";
		Connection con=connectToDB();
		try {
			String query = "select CoachingID from teacherDetails where TeacherName =\""+teacherName+"\"";
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			rs.next();
			ID = rs.getString("CoachingID");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		destroyConnection(con);
		return ID;
	}
	
	public String getTeacherIDFromTeacherDetails(String teacherName, String contactNumber, String email)
	{
		PreparedStatement ps;
		String ID = "1";
		Connection con=connectToDB();
		try {
			String query = "select ID from teacherDetails where TeacherName =\""+teacherName+"\" and ContactNumber = \""+contactNumber+"\" and Email = \""+email+"\"";
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
	
	public String getStudentIDFromStudentDetails(String studentName, String contactNumber, String email)
	{
		PreparedStatement ps;
		String ID = "1";
		Connection con=connectToDB();
		try {
			String query = "select ID from studentDetails where StudentName =\""+studentName+"\" and ContactNumber = \""+contactNumber+"\" and Email = \""+email+"\"";
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
	
	public String getLoggedInCoachingCity(String userName)
	{
		String[] c = userName.split("@");
		String cname=c[0];
		String city = null;
		PreparedStatement ps;
		Connection con=connectToDB();
		try {
			String query = "select City from coachingDetails where CoachingName ='"+cname+"'";
			//System.out.println(query);
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			rs.next();
			city = rs.getString("City");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		destroyConnection(con);
		return city;
	}
	
	public String[] getSelectedCoachingStudentDetails(String coachingName)
	{
		String coachingID = getCoachingID(coachingName);
		int n= getNumberOfStudentsInCoaching(coachingID);
		String htmlTable[]= new String[n];
		Connection con=connectToDB();
		try {
			PreparedStatement ps=con.prepareStatement("select s.ID,s.StudentName,s.ContactNumber,s.Email,s.IsActive,c.CoachingName from studentDetails s inner join coachingdetails c on c.ID=s.CoachingID where s.CoachingID=?");
			ps.setString(1, coachingID);
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
	
	
	public String[] getSelectedCoachingTeacherDetails(String coachingName)
	{
		String coachingID = getCoachingID(coachingName);
		int n= getNumberOfTeacherInCoaching(coachingID);
		String htmlTable[]= new String[n];
		Connection con=connectToDB();
		try {
			PreparedStatement ps=con.prepareStatement("select s.ID,s.TeacherName,s.ContactNumber,s.Email from teacherDetails s inner join coachingdetails c on c.ID=s.CoachingID where s.IsActive=\"1\" and s.CoachingID=?");
			ps.setString(1, coachingID);
			ResultSet rs = ps.executeQuery();
			
			int i=0;
			while(rs.next())
			{
				String ID = rs.getString(1);
				String TeacherName = rs.getString(2);
				String CNo = rs.getString(3);
				String Email = rs.getString(4);
				htmlTable[i]=putTeachersDataIntoHTMLTable(ID, TeacherName, CNo,Email,"1"," ",2);
				i=i+1;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		return htmlTable;	
	}

	int getNumberOfStudentsInCoaching(String coachingID) 
	{
		PreparedStatement ps;
		int count=0;
		Connection con=connectToDB();
		try {			
			ps = con.prepareStatement("select Count(ID) from studentDetails where CoachingID=?");
			ps.setString(1, coachingID);
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
	
	int getNumberOfTeacherInCoaching(String coachingID) 
	{
		PreparedStatement ps;
		int count=0;
		Connection con=connectToDB();
		try {			
			ps = con.prepareStatement("select Count(ID) from teacherDetails where IsActive=\"1\" and CoachingID=?");
			ps.setString(1, coachingID);
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
	
	public void deleteTeacher(String teacherID)
	{
		Statement ps;
		Connection con=connectToDB();
		try {
			ps = con.createStatement();
			String sql="update teacherDetails set IsActive=0,UpdateDtTm=CURRENT_TIMESTAMP where Id="+teacherID;
			System.out.println(sql);
			ps.executeUpdate(sql);
		} catch (SQLException ex) 
		{
			ex.printStackTrace();
		}
		destroyConnection(con);
	}
	
	public String[] getSelectedTeacherStudentDetails(String coachingID)
	{
		int n= getNumberOfStudentsInCoaching(coachingID);
		String htmlTable[]= new String[n];
		Connection con=connectToDB();
		try {
			PreparedStatement ps=con.prepareStatement("select s.ID,s.StudentName,s.ContactNumber,s.Email,s.IsActive,c.CoachingName from studentDetails s inner join coachingdetails c on c.ID=s.CoachingID where s.CoachingID=?");
			ps.setString(1, coachingID);
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
	
	public String getCoachingIDFromLoginDetails(String username)
	{
		PreparedStatement ps;
		String ID = "1";
		Connection con=connectToDB();
		try {
			String query = "select LoginUserID from loginDetails where LoginTypeID = 2 and UserName =\""+username+"\"";
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			rs.next();
			ID = rs.getString("LoginUserID");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		destroyConnection(con);
		return ID;
	}
	
	public String getStudentIDFromLoginDetails(String username)
	{
		PreparedStatement ps;
		String ID = "1";
		Connection con=connectToDB();
		try {
			String query = "select LoginUserID from loginDetails where LoginTypeID = 4 and UserName =\""+username+"\"";
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			rs.next();
			ID = rs.getString("LoginUserID");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		destroyConnection(con);
		return ID;
	}
	
	public String getCoachingIDFromTeacherLoginDetails(String loginID)
	{
		PreparedStatement ps;
		String LoginUserID = "1";
		String cID = "";
		Connection con=connectToDB();
		try {
			String query = "select LoginUserID from loginDetails where ID =\""+loginID+"\"";
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			rs.next();
			LoginUserID = rs.getString("LoginUserID");
			query = "select CoachingID from teacherDetails where ID =\""+LoginUserID+"\"";
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			rs.next();
			cID = rs.getString("CoachingID");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		destroyConnection(con);
		return cID;
	}
	
	public String getTeacherIDFromTeacherLoginDetails(String loginID)
	{
		PreparedStatement ps;
		String LoginUserID = "1";
		Connection con=connectToDB();
		try {
			String query = "select LoginUserID from loginDetails where ID =\""+loginID+"\"";
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			rs.next();
			LoginUserID = rs.getString("LoginUserID");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		destroyConnection(con);
		return LoginUserID;
	}
	
	public String getCoachingIDFromCoachingLoginDetails(String loginID)
	{
		PreparedStatement ps;
		String LoginUserID = "1";
		Connection con=connectToDB();
		try {
			String query = "select LoginUserID from loginDetails where LoginTypeID = \"2\" and ID =\""+loginID+"\"";
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			rs.next();
			LoginUserID = rs.getString("LoginUserID");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		destroyConnection(con);
		return LoginUserID;
	}
	
	public String getStudentName(String studentID)
	{
		PreparedStatement ps;
		String studentName = "";
		Connection con=connectToDB();
		try {
			String query = "select StudentName from studentDetails where ID = \""+studentID+"\"";
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			rs.next();
			studentName = rs.getString("StudentName");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		destroyConnection(con);
		return studentName;
	}
	
	public String getStudentIDFromLoginName(String username)
	{
		PreparedStatement ps;
		String LoginUserID = "";
		Connection con=connectToDB();
		try {
			String query = "select LoginUserID from loginDetails where UserName = \""+username+"\"";
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			rs.next();
			LoginUserID = rs.getString("LoginUserID");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		destroyConnection(con);
		return LoginUserID;
	}
	
	public String getCoachingIDFromLoginName(String username)
	{
		PreparedStatement ps;
		String LoginUserID = "";
		Connection con=connectToDB();
		try {
			String query = "select LoginUserID from loginDetails where UserName = \""+username+"\" and LoginTypeID=\"2\"";
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			rs.next();
			LoginUserID = rs.getString("LoginUserID");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		destroyConnection(con);
		return LoginUserID;
	}
	
	public String getCoachingNameFromCoachingID(String coachingID)
	{
		PreparedStatement ps;
		String coachingName = "";
		Connection con=connectToDB();
		try {
			String query = "select CoachingName from coachingDetails where ID = \""+coachingID+"\"";
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			rs.next();
			coachingName = rs.getString("CoachingName");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		destroyConnection(con);
		return coachingName;
	}
	
	public String getStudentContactNumber(String studentID)
	{
		PreparedStatement ps;
		String contactNumber = "";
		Connection con=connectToDB();
		try {
			String query = "select ContactNumber from studentDetails where ID = \""+studentID+"\"";
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			rs.next();
			contactNumber = rs.getString("ContactNumber");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		destroyConnection(con);
		return contactNumber;
	}
	
	
	public String getStudentEmailAddress(String studentID)
	{
		PreparedStatement ps;
		String contactNumber = "";
		Connection con=connectToDB();
		try {
			String query = "select Email from studentDetails where ID = \""+studentID+"\"";
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			rs.next();
			contactNumber = rs.getString("Email");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		destroyConnection(con);
		return contactNumber;
	}
	
	public String getStudentPassword(String username)
	{
		PreparedStatement ps;
		String contactNumber = "";
		Connection con=connectToDB();
		try {
			String query = "select Password from logindetails where UserName = \""+username+"\"";
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			rs.next();
			contactNumber = rs.getString("Password");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		destroyConnection(con);
		return contactNumber;
	}
	
	public String getStudentAccountCreateDate(String studentID)
	{
		PreparedStatement ps;
		String createDateTime = "";
		Connection con=connectToDB();
		try {
			String query = "select CreateDtTm from studentdetails where ID = \""+studentID+"\"";
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			rs.next();
			createDateTime = rs.getString("CreateDtTm");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		destroyConnection(con);
		return createDateTime;
	}
	
	public String getStudentAccountStatus(String studentID)
	{
		PreparedStatement ps;
		String status = "";
		Connection con=connectToDB();
		try {
			String query = "select IsActive from studentdetails where ID = \""+studentID+"\"";
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			rs.next();
			status = rs.getString("IsActive");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		destroyConnection(con);
		if(status.equals("1"))
			status = "ACTIVE";
		else
			status = "IN-ACTIVE";
		
		return status;
	}
}
