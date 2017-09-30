package dbActions;

import java.sql.Connection;
import java.sql.DriverManager;
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
	
	
	
}
