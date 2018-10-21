package dbActions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionsAndTestAssoc {
	
	public Connection connectToDB()
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
	
	
	public int getNumberOfQuestionInATest(String testID)
	{
		PreparedStatement ps;
		int n=0;
		Connection con=connectToDB();
		try {			
			ps = con.prepareStatement("select Count(1) from questions where TestID=?");
			ps.setString(1, testID);
			ResultSet rs = ps.executeQuery();
			rs.next();
			n = rs.getInt(1);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		return n;	
	}

}
