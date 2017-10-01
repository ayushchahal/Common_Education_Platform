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
	
	public boolean insertTestDetails(String testName, String subjectName, String testType, String noq, String totalMarks, String totalTime)
	{
		Statement ps;
		boolean status=false;
		Connection con=connectToDB();
		try {
			ps = con.createStatement();
			String sql="insert into Tests (TestName,SubjectName,TestType,NumberOfQuestions,TotalMarks,TotalTime) values (\""+testName+"\",\""+subjectName+"\",\""+testType+"\",\""+noq+"\",\""+totalMarks+"\",\""+totalTime+"\")";
			System.out.println(sql);
			ps.executeUpdate("insert into Tests (TestName,SubjectName,TestType,NumberOfQuestions,TotalMarks,TotalTime) values (\""+testName+"\",\""+subjectName+"\",\""+testType+"\",\""+noq+"\",\""+totalMarks+"\",\""+totalTime+"\")");
			status=true;
		} catch (SQLException ex) 
		{
			ex.printStackTrace();
		}
		destroyConnection(con);
		return status;
	}
	
	public boolean insertQuestionDetails(String qtext, String qTypeID, String pMarks, String nMarks, String option1,String option2,String option3,String option4,String option5,String option6,String TestID)
	{
		Statement ps;
		boolean status=false;
		Connection con=connectToDB();
		try {
			ps = con.createStatement();
			String sql="insert into questions (QuestionText,QuestionTypeID,PositiveMarks,NegativeMarks,Option1,Option2,Option3,Option4,Option5,Option6,TestID) values (\""+qtext+"\",\""+qTypeID+"\",\""+pMarks+"\",\""+nMarks+"\",\""+option1+"\",\""+option2+"\""+"\",\""+option3+"\",\""+"\",\""+option4+"\""+"\",\""+option5+"\""+"\",\""+option6+"\""+TestID+"\")";
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
			String totalTime) {
		int testID=0;
		Connection con=connectToDB();
		try{ 
			//Connection con=connectToDB();
			PreparedStatement ps=con.prepareStatement("select ID from tests where TestName=?, SubjectName=?, TestType=?, NumberOfQuestions, TotalMarks, TotalTime");
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
		boolean status=false;
		Connection con=connectToDB();
		try {
			ps = con.createStatement();
			String sql="update questions set TestID="+testID+"where ";
			System.out.println(sql);
			ps.executeUpdate(sql);
			status=true;
		} catch (SQLException ex) 
		{
			ex.printStackTrace();
		}
		destroyConnection(con);
		
	}
	
	
}
