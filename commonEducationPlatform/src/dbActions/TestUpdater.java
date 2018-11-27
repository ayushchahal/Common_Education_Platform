package dbActions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestUpdater {
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
	
	public void updateQuestion(String questionID, String questionText, String questionType, String pmarks, String nmarks, String o1, String o2, String o3, String o4, String o5, String o6, String answer, String timeLimit, String topics)
	{
		if(o3 == null)
			o3="null";
		if(o4 == null)
			o4="null";
		if(o5 == null)
			o5="null";
		if(o6 == null)
			o6="null";
		
		PreparedStatement ps;
		Connection con=connectToDB();
		try {			
			ps = con.prepareStatement("update questions set QuestionText=?, QuestionTypeID=?, PositiveMarks=?, NegativeMarks=?, Option1=?, Option2=?, Option3=?, Option4=?, Option5=?, Option6=?, Answer=?, TimeLimitInSeconds=?, Labels=? where ID="+questionID);
			ps.setString(1, questionText);
			ps.setString(2, questionType);
			ps.setString(3, pmarks);
			ps.setString(4, nmarks);
			ps.setString(5, o1);
			ps.setString(6, o2);
			ps.setString(7, o3);
			ps.setString(8, o4);
			ps.setString(9, o5);
			ps.setString(10, o6);
			ps.setString(11, answer);
			ps.setString(12, timeLimit);
			ps.setString(13, topics);
			ps.executeUpdate();
			
		}catch (SQLException e) 
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		
	}

}
