package dbActions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

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
	
	public boolean insertTestDetails(String testName, String subjectName, String testType, String noq, String totalMarks, String totalTime, String username, String isCompleted, String isShared, String createdBy)
	{
		Statement ps;
		boolean status=false;
		Connection con=connectToDB();
		try {
			ps = con.createStatement();
			String sql="insert into Tests (TestName,SubjectName,TestType,NumberOfQuestions,TotalMarks,TotalTime,Owner,IsCompleted,IsShared,CreatedBy) values (\""+testName+"\",\""+subjectName+"\",\""+testType+"\",\""+noq+"\",\""+totalMarks+"\",\""+totalTime+"\",\""+username+"\",\""+isCompleted+"\",\""+isShared+"\",\""+createdBy+"\")";
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
			PreparedStatement ps=con.prepareStatement("select Id, TestName, SubjectName, TestType, NumberOfQuestions, TotalMarks, TotalTime, Owner, IsCompleted, IsShared, PublicDateTime from tests");
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
				String isShared = rs.getString(10);
				if(isShared == null)
				{
					isShared="No";
				}
				else {
					if(isShared.equals("1"))
						isShared = "Yes";
					else isShared = "No";
					
				}
				
				String publicDT = rs.getString(11);
		
				
				htmlTable[i]=putTestDataIntoHTMLTable(ID, TestName, SubjectName, TestType, noq, TotalMarks, TotalTime, owner, isCompleted, isShared, publicDT, 1);
				i=i+1;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		return htmlTable;
	}
	
	
	public String[] getAllCoachingTests(String loginID)
	{
		String coachingID = new DBConnection().getCoachingIDFromCoachingLoginDetails(loginID);
		int n= getNumberOfTestsInCoaching(coachingID);
		String htmlTable[]= new String[n];
		Connection con=connectToDB();
		try {
			PreparedStatement ps=con.prepareStatement("select Id, TestName, SubjectName, TestType, NumberOfQuestions, TotalMarks, TotalTime, Owner, IsCompleted, IsShared, PublicDateTime from tests where Owner = ?");
			ps.setString(1, coachingID);
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
				String isShared = rs.getString(10);
				if(isShared == null)
				{
					isShared="No";
				}
				else {
					if(isShared.equals("1"))
						isShared = "Yes";
					else isShared = "No";
					
				}
				
				String publicDT = rs.getString(11);
		
				
				htmlTable[i]=putTestDataIntoHTMLTable(ID, TestName, SubjectName, TestType, noq, TotalMarks, TotalTime, owner, isCompleted, isShared, publicDT, 2);
				i=i+1;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		return htmlTable;
	}
	
	
	public String[] getAllTeacherTests(String loginID)
	{
		String teacherID = new DBConnection().getTeacherIDFromTeacherLoginDetails(loginID);
		int n= getNumberOfTestsForTeacher(teacherID);
		String htmlTable[]= new String[n];
		Connection con=connectToDB();
		try {
			PreparedStatement ps=con.prepareStatement("select Id, TestName, SubjectName, TestType, NumberOfQuestions, TotalMarks, TotalTime, Owner, IsCompleted, IsShared, PublicDateTime from tests where CreatedBy=?");
			ps.setString(1, teacherID);
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
				String isShared = rs.getString(10);
				if(isShared == null)
				{
					isShared="No";
				}
				else {
					if(isShared.equals("1"))
						isShared = "Yes";
					else isShared = "No";
					
				}
				
				String publicDT = rs.getString(11);
		
				
				htmlTable[i]=putTestDataIntoHTMLTable(ID, TestName, SubjectName, TestType, noq, TotalMarks, TotalTime, owner, isCompleted, isShared, publicDT, 2);
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
	
	public int getNumberOfTestsInCoaching(String coachingID)
	{
		PreparedStatement ps;
		int count=0;
		Connection con=connectToDB();
		try {			
			ps = con.prepareStatement("select Count(ID) from tests where Owner=?");
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
	
	
	public int getNumberOfTestsForTeacher(String teacherID)
	{
		PreparedStatement ps;
		int count=0;
		Connection con=connectToDB();
		try {			
			ps = con.prepareStatement("select Count(ID) from tests where CreatedBy=?");
			ps.setString(1, teacherID);
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
	
	
	public String putTestDataIntoHTMLTable(String ID, String TestName, String SubjectName, String TestType,String noq, String TotalMarks, String TotalTime, String owner, String isCompleted, String isShared, String publicDateTime, int loginType)
	{
		String table = null;
		if(loginType == 1)
		{
			table= "<tr><td>"+ID+"</td><td>"+TestName+"</td><td>"+SubjectName+"</td><td>"+TestType+"</td><td>"+noq+"</td><td>"+TotalMarks+"</td><td>"+TotalTime+"</td><td>"+owner+"</td><td>"+isCompleted+"</td><td>"+isShared+"</td><td>"+publicDateTime+"</td></tr>";
		}
		else if (loginType == 2)
		{
			//"<form action=\"/EditOrShareTest\" method=\"Post\">"+"<input type=\"hidden\" name=\"tid\" value=\""+ID+"\">"+"<input type=\"submit\" name=\"editOrDeleteDetails\" value=\"Delete\" onClick=\"alert();\"> <input type=\"submit\" name=\"editOrDeleteDetails\" value=\"Edit\"></form>"
			if(isCompleted.equals("No"))
			{
				table= "<tr><td>"+ID+"</td><td>"+TestName+"</td><td>"+SubjectName+"</td><td>"+TestType+"</td><td>"+noq+"</td><td>"+TotalMarks+"</td><td>"+TotalTime+"</td><td>"+isCompleted+"</td><td>"+isShared+"</td><td>"+publicDateTime+"</td><td>"+"<form action=\"/ViewEditOrShareTest\" method=\"Post\">"+"<input type=\"hidden\" name=\"testID\" value=\""+ID+"\">"+"<input type=\"submit\" name=\"CompleteOrShare\" value=\"Complete Test\">"+"</form>"+"</td></tr>";
			}
			else if(isCompleted.equals("Yes") && isShared.equals("No"))
			{
				table= "<tr><td>"+ID+"</td><td>"+TestName+"</td><td>"+SubjectName+"</td><td>"+TestType+"</td><td>"+noq+"</td><td>"+TotalMarks+"</td><td>"+TotalTime+"</td><td>"+isCompleted+"</td><td>"+isShared+"</td><td>"+publicDateTime+"</td><td>"+"<form action=\"/ViewEditOrShareTest\" method=\"Post\">"+"<input type=\"hidden\" name=\"testID\" value=\""+ID+"\">"+"<input type=\"submit\" name=\"CompleteOrShare\" value=\"Share Test\"></form>"+"</td></tr>";
			}
			else 
			{
				table= "<tr><td>"+ID+"</td><td>"+TestName+"</td><td>"+SubjectName+"</td><td>"+TestType+"</td><td>"+noq+"</td><td>"+TotalMarks+"</td><td>"+TotalTime+"</td><td>"+isCompleted+"</td><td>"+isShared+"</td><td>"+publicDateTime+"</td><td>"+"<form action=\"/ViewEditOrShareTest\" method=\"Post\">"+"<input type=\"hidden\" name=\"testID\" value=\""+ID+"\">"+"<input type=\"submit\" name=\"CompleteOrShare\" value=\"View Test\"></form>"+"</td></tr>";
			}
			
		}
		return table;
	}
	
	public void insertShareDate(String testID, String date) 
	{
		Statement ps;
		Connection con=connectToDB();
		try {
			ps = con.createStatement();
			String sql="update tests set IsCompleted=1,IsShared=1, PublicDateTime=\""+date+"\" "+"where Id="+testID;
			System.out.println(sql);
			ps.executeUpdate(sql);
		} catch (SQLException ex) 
		{
			ex.printStackTrace();
		}
		destroyConnection(con);
		
	}
	
	public void updateIsCompletedToOne(String testID) 
	{
		Statement ps;
		Connection con=connectToDB();
		try {
			ps = con.createStatement();
			String sql="update tests set IsCompleted=1 where Id="+testID;
			System.out.println(sql);
			ps.executeUpdate(sql);
		} catch (SQLException ex) 
		{
			ex.printStackTrace();
		}
		destroyConnection(con);
	}
	
	public int getNumberOfQuestionsInATest(String testID)
	{
		PreparedStatement ps;
		int count=0;
		Connection con=connectToDB();
		try {			
			ps = con.prepareStatement("select NumberOfQuestions from tests where ID=?");
			ps.setString(1, testID);
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
	
	public int getNumberOfSavedQuestionsInATest(String testID)
	{
		PreparedStatement ps;
		int count=0;
		Connection con=connectToDB();
		try {			
			ps = con.prepareStatement("select Count(ID) from questions where TestID=?");
			ps.setString(1, testID);
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
	
	public String[] getQuestionIDs(String testID)
	{
		int n=getNumberOfSavedQuestionsInATest(testID);
		PreparedStatement ps;
		String[] qID=new String[n];
		Connection con=connectToDB();
		try {			
			ps = con.prepareStatement("select ID from questions where TestID=?");
			ps.setString(1, testID);
			ResultSet rs = ps.executeQuery();
			int i=0;
			while(rs.next())
			{
				qID[i] = rs.getString("ID");
				i++;
			}
			
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		return qID;	
	}
	
	public String getQuestionText(String questionID)
	{
		PreparedStatement ps;
		String text=null;
		Connection con=connectToDB();
		try {			
			ps = con.prepareStatement("select QuestionText from questions where ID=?");
			ps.setString(1, questionID);
			ResultSet rs = ps.executeQuery();
			rs.next();
			text = rs.getString("QuestionText");
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		return text;	
	}
	
	public String getTypeOfQuestion(String questionID)
	{
		PreparedStatement ps;
		String text=null;
		Connection con=connectToDB();
		try {			
			ps = con.prepareStatement("select QuestionTypeID from questions where ID=?");
			ps.setString(1, questionID);
			ResultSet rs = ps.executeQuery();
			rs.next();
			text = rs.getString("QuestionTypeID");
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		return text;	
	}
	
	public String getPositiveMarks(String questionID)
	{
		PreparedStatement ps;
		String text=null;
		Connection con=connectToDB();
		try {			
			ps = con.prepareStatement("select PositiveMarks from questions where ID=?");
			ps.setString(1, questionID);
			ResultSet rs = ps.executeQuery();
			rs.next();
			text = rs.getString("PositiveMarks");
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		return text;	
	}
	
	public String getNegativeMarks(String questionID)
	{
		PreparedStatement ps;
		String text=null;
		Connection con=connectToDB();
		try {			
			ps = con.prepareStatement("select NegativeMarks from questions where ID=?");
			ps.setString(1, questionID);
			ResultSet rs = ps.executeQuery();
			rs.next();
			text = rs.getString("NegativeMarks");
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		return text;	
	}
	
	public int getTestTime(String testID)
	{
		PreparedStatement ps;
		int text=0;
		Connection con=connectToDB();
		try {			
			ps = con.prepareStatement("select TotalTime from tests where ID=?");
			ps.setString(1, testID);
			ResultSet rs = ps.executeQuery();
			rs.next();
			text = rs.getInt(1);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		return text;	
	}
	
	public String getAnswer(String questionID)
	{
		PreparedStatement ps;
		String text=null;
		Connection con=connectToDB();
		try {			
			ps = con.prepareStatement("select Answer from questions where ID=?");
			ps.setString(1, questionID);
			ResultSet rs = ps.executeQuery();
			rs.next();
			text = rs.getString("Answer");
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		return text;	
	}
	
	public boolean isQuestionUpdated(String questionID, String cQuestionText, String cQuestionType, String cPMarks, String cNMarks, String cO1, String cO2, String cO3, String cO4, String cO5, String cO6, String cAnswer)
	{
		PreparedStatement ps;
		String qText=null;
		String qType,pmarks,nmarks,o1,o2,o3,o4,o5,o6,answer;
		Connection con=connectToDB();
		int isUpdated=0;
		int n=getNumberOfOptionsInAQuestion(questionID);
		try {			
			ps = con.prepareStatement("select QuestionText,QuestionTypeID,PositiveMarks,NegativeMarks,Option1,Option2,Option3,Option4,Option5,Option6,Answer from questions where ID=?");
			ps.setString(1, questionID);
			ResultSet rs = ps.executeQuery();
			rs.next();
			qText = rs.getString("QuestionText");
			qType = rs.getString("QuestionTypeID");
			pmarks = rs.getString("PositiveMarks");
			nmarks = rs.getString("NegativeMarks");
			o1 = rs.getString("Option1");
			o2 = rs.getString("Option2");
			o3 = rs.getString("Option3");
			o4 = rs.getString("Option4");
			o5 = rs.getString("Option5");
			o6 = rs.getString("Option6");
			answer = rs.getString("Answer");
			
			/*
			 * Code to find out number of options entered by the user.
			 */
			int m=0;
			if(cO3 == null && cO4 == null && cO5 == null && cO6 == null)
				m=2;
			else if((cO3 != null) && cO4 == null && cO5 == null && cO6 == null)
				m=3;
			else if((cO4 != null) && cO5 == null && cO6 == null)
			{
				m=4;
			}
			else if((cO5 != null) && cO6 == null)
				m=5;
			else if ((cO6 != null))
			{
				m=6;
			}
			
			if(n != m)
				return true;
			
			/*
			 * Code ends here
			 */
			if(!cQuestionText.equals(qText))
			{
				isUpdated=1;
				return true;
			}
				
			if(!cQuestionType.equals(qType))
			{
				isUpdated=1;
				return true;
			}				
			if(!cPMarks.equals(pmarks))
			{
				isUpdated=1;
				return true;
			}
			if(!cNMarks.equals(nmarks))
			{
				isUpdated=1;
				return true;
			}
			if(!qType.equals("1"))
			{
				if(!cO1.equals(o1))
				{
					isUpdated=1;
					return true;
				}
				if(!cO2.equals(o2))
				{
					isUpdated=1;
					return true;
				}
				if(n==3)
				{
					if(!cO3.equals(o3))
						isUpdated=1;
				}
				if(n==4)
				{
					if(!cO4.equals(o4))
						isUpdated=1;
				}
				if(n==5)
				{
					if(!cO5.equals(o5))
						isUpdated=1;
				}
				if(n==6)
				{
					if(!cO6.equals(o6))
						isUpdated=1;
				}
			}
				
			if(!cAnswer.equals(answer))
			{
				isUpdated=1;
				return true;
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		if(isUpdated == 1)
			return true;
		else
			return false;
	}
	
	
	public int getNumberOfOptionsInAQuestion(String questionID)
	{
		int min=2;
		PreparedStatement ps;
		String o3 = null,o4 = null,o5 = null,o6 = null;
		Connection con=connectToDB();
		try {			
			ps = con.prepareStatement("select Option3,Option4,Option5,Option6 from questions where ID=?");
			ps.setString(1, questionID);
			ResultSet rs = ps.executeQuery();
			rs.next();
			o3 = rs.getString("Option3");
			o4 = rs.getString("Option4");
			o5 = rs.getString("Option5");
			o6 = rs.getString("Option6");
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		if(o3.equals("null") && o4.equals("null") && o5.equals("null") && o6.equals("null"))
			min=2;
		else if((!o3.equals("null")) && o4.equals("null") && o5.equals("null") && o6.equals("null"))
			min=3;
		else if((!o4.equals("null")) && o5.equals("null") && o6.equals("null"))
		{
			min=4;
		}
		else if((!o5.equals("null")) && o6.equals("null"))
			min=5;
		else if ((!o6.equals("null")))
		{
			min=6;
		}
		System.out.println("Number of options: "+min);
		return min;
	}
	
	public String getOption(String questionID, String optionNumber)
	{
		PreparedStatement ps;
		String text=null;
		Connection con=connectToDB();
		try {			
			ps = con.prepareStatement("select Option"+optionNumber+" from questions where ID=?");
			ps.setString(1, questionID);
			ResultSet rs = ps.executeQuery();
			rs.next();
			text = rs.getString("Option"+optionNumber);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		return text;	
	}
	
	public int numberOfQuestionsInTestTable(String testID)
	{
		PreparedStatement ps;
		int n=0;
		Connection con=connectToDB();
		try {			
			ps = con.prepareStatement("select NumberOfQuestions from tests where ID=?");
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
	
	public int numberOfQuestionsInQuestionsTable(String testID)
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
	
	public void recordAnswer(String studentID, String questionID, String answer)
	{
		Statement ps;
		Connection con=connectToDB();
		try {
			ps = con.createStatement();
			String sql="insert into AnswerSheet (StudentID,QuestionID,Answer,CreateDateTime) values (\""+studentID+"\",\""+questionID+"\", "+answer+" , CURRENT_TIMESTAMP())";
			System.out.println(sql);
			ps.executeUpdate(sql);
			
		} catch (SQLException ex) 
		{
			ex.printStackTrace();
		}
		destroyConnection(con);
		
	}
	
	public boolean isAnswerUpdated(String studentID, String questionID, String answer)
	{
		PreparedStatement ps;
		Connection con=connectToDB();
		String oldAnswer = "";
		try {			
			ps = con.prepareStatement("select Answer from answersheet where StudentID = ? and QuestionID = ?");
			ps.setString(1, studentID);
			ps.setString(2, questionID);
			ResultSet rs = ps.executeQuery();
			rs.next();
			oldAnswer = rs.getString("Answer");
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		if(oldAnswer == null)
			oldAnswer = "null";
			
		if(oldAnswer.equals(answer))
			return false;
		else
			return true;
	}
	
	public boolean isQuestionAlreadyAnswered(String studentID, String questionID)
	{
		PreparedStatement ps;
		int count=0;
		Connection con=connectToDB();
		try {			
			ps = con.prepareStatement("select Count(ID) from answersheet where StudentID = ? and QuestionID = ?");
			ps.setString(1, studentID);
			ps.setString(2, questionID);
			ResultSet rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		
		if(count == 1)
			return true;
		else
			return false;
	}
	
	public String getAnswer(String studentID, String questionID)
	{
		PreparedStatement ps;
		Connection con=connectToDB();
		String answer = "";
		try {			
			ps = con.prepareStatement("select Answer from answersheet where StudentID = ? and QuestionID = ?");
			ps.setString(1, studentID);
			ps.setString(2, questionID);
			ResultSet rs = ps.executeQuery();
			rs.next();
			answer = rs.getString("Answer");
		} catch (SQLException e) 
		{
			return null;
		}
		
		return answer;
	}
	
	public void updateAnswer(String studentID, String questionID, String answer)
	{
		Statement ps;
		Connection con=connectToDB();
		try {
			ps = con.createStatement();
			String sql="update answersheet set Answer="+answer+", UpdateDateTime=CURRENT_TIMESTAMP() where studentID=\""+studentID+"\" and questionID=\""+questionID+"\"";
			System.out.println(sql);
			ps.executeUpdate(sql);
		} catch (SQLException ex) 
		{
			ex.printStackTrace();
		}
		destroyConnection(con);
	}	
	
	public boolean isQuestionAnswered(String studentID, String questionID)
	{
		PreparedStatement ps;
		Connection con=connectToDB();
		try {			
			ps = con.prepareStatement("select Answer from answersheet where StudentID = ? and QuestionID = ?");
			ps.setString(1, studentID);
			ps.setString(2, questionID);
			ResultSet rs = ps.executeQuery();
			rs.next();
		} catch (SQLException e) 
		{
			return false;
		}
		
		return true;
	}
	
	public boolean doesStudentTestAssociationExist(String studentID, String testID)
	{
		PreparedStatement ps;
		int count=0;
		Connection con=connectToDB();
		try {			
			ps = con.prepareStatement("select Count(ID) from studenttestassociation where StudentID = ? and TestID = ?");
			ps.setString(1, studentID);
			ps.setString(2, testID);
			ResultSet rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		
		if(count == 0)
			return false;
		else
			return true;
	}
	
	public void createStudentTestAssociation(String studentID, String testID, int isCompleted)
	{
		Statement ps;
		Connection con=connectToDB();
		try {
			ps = con.createStatement();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			long startTime = timestamp.getTime();
			String sql="insert into studenttestassociation (StudentID,TestID,IsCompleted,StartTimeStamp) values (\""+studentID+"\",\""+testID+"\",\""+isCompleted+"\",\""+startTime+"\")";
			System.out.println(sql);
			ps.executeUpdate(sql);			
		} catch (SQLException ex) 
		{
			ex.printStackTrace();
		}
		destroyConnection(con);
	}
	
	public void updateStudentTestAssociation(String studentID, String testID, int isCompleted)
	{
		Statement ps;
		Connection con=connectToDB();
		try {
			ps = con.createStatement();
			String sql="update studenttestassociation set IsCompleted=1 where StudentID="+studentID+" and TestID="+testID;
			System.out.println(sql);
			ps.executeUpdate(sql);
		} catch (SQLException ex) 
		{
			ex.printStackTrace();
		}
		destroyConnection(con);
	}
	
	@SuppressWarnings("null")
	public String[][] getCoachingAnswers(String testID)
	{
		PreparedStatement ps;
		int n = getNumberOfSavedQuestionsInATest(testID);
		String[][] a = new String[n][4];
		Connection con=connectToDB();
		int i=0;
		try {			
			ps = con.prepareStatement("select ID, Answer,PositiveMarks,NegativeMarks from questions where TestID = ?");
			ps.setString(1, testID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				a[i][0] = rs.getString("ID");
				a[i][1] = rs.getString("Answer");
				a[i][2] = rs.getString("PositiveMarks");
				a[i][3] = rs.getString("NegativeMarks");
				i=i+1;
			}			
			
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		
		/*
		for(int k=0;k<n;k++)
		{
			for(int j=0;j<2;j++)
			{
				System.out.println(a[k][j]);
			}
		}
		*/
		return a;
		
	}
	
	public String getStudentAnswer(String questionID, String studentID)
	{
		PreparedStatement ps;
		String answer = "";
		Connection con=connectToDB();
		try {			
			ps = con.prepareStatement("select Answer from answersheet where questionID=? and studentID = ?");
			ps.setString(1, questionID);
			ps.setString(2, studentID);
			ResultSet rs = ps.executeQuery();
			rs.next();
			answer=rs.getString(1);
			
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		
		return answer;
		
	}
	
	
	public int getTotalMarks(String testID)
	{
		PreparedStatement ps;
		int total=0;
		Connection con=connectToDB();
		try {			
			ps = con.prepareStatement("select SUM(PositiveMarks) from questions where TestID = ?");
			ps.setString(1, testID);
			ResultSet rs = ps.executeQuery();
			rs.next();
			total=rs.getInt(1);
			
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		destroyConnection(con);
		
		return total;
		
	}
}
