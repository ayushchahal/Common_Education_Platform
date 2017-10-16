package dbActions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestCreators {

	public Connection connectToDB()
	{
		Connection con=null;
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
	
	public boolean insertTestDetails(String testName, String subjectName, String testType, String noq, String totalMarks, String totalTime, String username)
	{
		Statement ps;
		boolean status=false;
		Connection con=connectToDB();
		try {
			ps = con.createStatement();
			String sql="insert into Tests (TestName,SubjectName,TestType,NumberOfQuestions,TotalMarks,TotalTime,Owner,IsCompleted) values (\""+testName+"\",\""+subjectName+"\",\""+testType+"\",\""+noq+"\",\""+totalMarks+"\",\""+totalTime+"\",\""+username+"\",\""+0+"\")";
			System.out.println(sql);
			ps.executeUpdate(sql);
			status=true;
		} catch (SQLException ex) 
		{
			ex.printStackTrace();
		}
		destroyConnection(con);
		return status;
	}
	
	public boolean insertQuestionDetails(String qtext, String qTypeID, String pMarks, String nMarks, String option1,String option2,String option3,String option4,String option5,String option6,String TestID, String answer)
	{
		Statement ps;
		boolean status=false;
		Connection con=connectToDB();
		try {
			ps = con.createStatement();
			String sql="insert into questions (QuestionText,QuestionTypeID,PositiveMarks,NegativeMarks,Option1,Option2,Option3,Option4,Option5,Option6,TestID,Answer) values (\""+qtext+"\",\""+qTypeID+"\",\""+pMarks+"\",\""+nMarks+"\",\""+option1+"\",\""+option2+"\",\""+option3+"\",\""+option4+"\""+",\""+option5+"\",\""+option6+"\",\""+TestID+"\",\""+answer+"\")";
			System.out.println(sql);
			ps.executeUpdate(sql);
			status=true;
		} catch (SQLException ex) 
		{
			ex.printStackTrace();
		}
		destroyConnection(con);
		return status;
	}

	public int getTestID(String testName, String subjectName, String testType, String noq, String totalMarks,
			String totalTime) 
	{
		int testID=0;
		Connection con=connectToDB();
		try{ 
			//Connection con=connectToDB();
			PreparedStatement ps=con.prepareStatement("select ID from tests where TestName=? and SubjectName=? and TestType=? and NumberOfQuestions=? and TotalMarks=? and TotalTime=?");
			ps.setString(1, testName);
			ps.setString(2, subjectName);
			ps.setString(3, testType);
			ps.setString(4, noq);
			ps.setString(5, totalMarks);
			ps.setString(6, totalTime);
			ResultSet rs=ps.executeQuery(); 
			rs.next();
			testID=rs.getInt(1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}  
		destroyConnection(con);
		return testID;
	}


	public void insertTestID(int testID) 
	{
		Statement ps;
		Connection con=connectToDB();
		try {
			ps = con.createStatement();
			String sql="insert into QuestionsTestsAssociation (\""+testID+"\",)";
			System.out.println(sql);
			ps.executeUpdate(sql);
		} catch (SQLException ex) 
		{
			ex.printStackTrace();
		}
		destroyConnection(con);
		
	}
	
	public String[] getAllTests()
	{
		int n= getNumberOfTests();
		String htmlTable[]= new String[n];
		Connection con=connectToDB();
		try {
			PreparedStatement ps=con.prepareStatement("select Id, TestName, SubjectName, TestType, NumberOfQuestions, TotalMarks, TotalTime,Owner,IsCompleted from tests");
			ResultSet rs = ps.executeQuery();
			int i=0;
			while(rs.next())
			{
				String ID = rs.getString(1);
				String TestName = rs.getString(2);
				String SubjectName = rs.getString(3);
				String TestType = rs.getString(4);
				if(TestType.equals("1"))
					TestType="Standard";
				else
					TestType="Time-attack";
				String noq = rs.getString(5);
				String TotalMarks = rs.getString(6);
				String TotalTime = rs.getString(7);
				String owner = rs.getString(8);
				String isCompleted = rs.getString(9);
				if(isCompleted.equals("1"))
					isCompleted = "Yes";
				else isCompleted = "No";
				
				htmlTable[i]=putTestDataIntoHTMLTable(ID, TestName, SubjectName, TestType, noq, TotalMarks, TotalTime, owner, isCompleted);
				i=i+1;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		return htmlTable;
	}
	
	public int getNumberOfTests()
	{
		PreparedStatement ps;
		int count=0;
		Connection con=connectToDB();
		try {			
			ps = con.prepareStatement("select Count(ID) from tests");
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
	
	
	public String putTestDataIntoHTMLTable(String ID, String TestName, String SubjectName, String TestType,String noq, String TotalMarks, String TotalTime, String owner, String isCompleted)
	{
		String table = null;
		table= "<tr><td>"+ID+"</td><td>"+TestName+"</td><td>"+SubjectName+"</td><td>"+TestType+"</td><td>"+noq+"</td><td>"+TotalMarks+"</td><td>"+TotalTime+"</td><td>"+owner+"</td><td>"+isCompleted+"</td></tr>";
		return table;
	}
	
	
}
