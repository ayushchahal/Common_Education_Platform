package dbActions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestAndCoachingAssoc {
	
	
	public Connection connectToCoachingManagementDB()
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
	
	public Connection connectToQuestionBankDB()
	{
		Connection con = null;
		try{   
			Class.forName("com.mysql.jdbc.Driver");  
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/questionbank","root","ayush");   				  
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
	
	public String[] getMyTestDetails(String StudentID)
	{
		//int n= getNumberOfStudents();
		int n = numberOfTestsForStudent(StudentID);
		String htmlTable[]= new String[n];
		Connection con1=connectToQuestionBankDB();
		try {
			PreparedStatement ps=con1.prepareStatement("select TestName,SubjectName,NumberOfQuestions,TotalMarks,TotalTime,ID from questionBank.tests2 t where t.Owner in (select CoachingID from coachingmanagement.studentdetails s where s.ID = ?) and CURDATE() > t.PublicDateTime");
			ps.setString(1, StudentID);
			ResultSet rs = ps.executeQuery();
			
			int i=0;
			while(rs.next())
			{
				String TestName = rs.getString(1);
				String SubjectName = rs.getString(2);
				String noq = rs.getString(3);
				String tmarks = rs.getString(4);
				String ttime = rs.getString(5);
				String testID = rs.getString(6);
				if(ttime.equals("0"))
					htmlTable[i]=putMyTestDataIntoHTMLTable(i+1,TestName, SubjectName, noq, tmarks, "Time Limit is on each question", testID, StudentID);
				else
					htmlTable[i]=putMyTestDataIntoHTMLTable(i+1,TestName, SubjectName, noq, tmarks, ttime, testID, StudentID);
				i=i+1;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		destroyConnection(con1);
		return htmlTable;
	}
	
	public boolean isTestTaken(String studentID, String testID)
	{
		PreparedStatement ps;
		Connection con=connectToQuestionBankDB();
		String flag="";
		try {			
			ps = con.prepareStatement("select IsCompleted from studenttestassociation where StudentID = ? and TestID = ?");
			ps.setString(1, studentID);
			ps.setString(2, testID);
			ResultSet rs = ps.executeQuery();
			rs.next();
			flag = rs.getString("IsCompleted");
		} catch (SQLException e) 
		{
			return false;
		}
		
		if(flag.equals("1"))
			return true;
		else
			return false;
	}
	
	public String putMyTestDataIntoHTMLTable(int SNo, String TestName, String SubjectName, String noq, String tmarks, String ttime, String testID, String StudentID)
	{
		String table = null;
		//table= "<tr name=\"ID\"><td>"+ID+"</td><td>"+Sname+"</td><td>"+C+"</td><td>"+e+"</td><td>"+S+"</td><td>"+Cname+"</td><td>"+"<a href=\"/StudentDetail\">Details</a>"+"</td></tr>";
		table= "<tr><td>"+SNo+"</td><td>"+TestName+"</td><td>"+SubjectName+"</td><td>"+noq+"</td><td>"+tmarks+"</td><td>"+ttime+"</td><td>"+"<form action=\"/TakeTest\" method=\"Post\">"+"<input type=\"hidden\" name=\"testID\" value=\""+testID+"\">"+"<input type=\"hidden\" name=\"studentID\" value=\""+StudentID+"\">"+"<input type=\"submit\" name=\"details\" value=\"TakeTest\"></form></td></tr>";
		
		if(new TestCreators().doesStudentTestAssociationExist(StudentID, testID))
		{
			table= "<tr><td>"+SNo+"</td><td>"+TestName+"</td><td>"+SubjectName+"</td><td>"+noq+"</td><td>"+tmarks+"</td><td>"+ttime+"</td><td>"+"<form action=\"/TakeTest\" method=\"Post\">"+"<input type=\"hidden\" name=\"testID\" value=\""+testID+"\">"+"<input type=\"hidden\" name=\"studentID\" value=\""+StudentID+"\">"+"<input type=\"submit\" name=\"details\" value=\"Continue Test\"></form></td></tr>";
		}
		if(isTestTaken(StudentID,testID))
		{
			table= "<tr><td>"+SNo+"</td><td>"+TestName+"</td><td>"+SubjectName+"</td><td>"+noq+"</td><td>"+tmarks+"</td><td>"+ttime+"</td><td>"+"<form action=\"/MyReport\" method=\"Post\">"+"<input type=\"hidden\" name=\"testID\" value=\""+testID+"\">"+"<input type=\"hidden\" name=\"studentID\" value=\""+StudentID+"\">"+"<input type=\"submit\" name=\"details\" value=\"View Report\"></form></td></tr>";
		}
		
		return table;
	}
	
	public int numberOfTestsForStudent(String studentID)
	{
		int count=0;
		Connection con=connectToCoachingManagementDB();
		try {			
			PreparedStatement ps=con.prepareStatement("select Count(*) from questionBank.tests2 t where t.Owner in (select CoachingID from coachingmanagement.studentdetails s where s.ID = ?)");
			ps.setString(1, studentID);
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
}
