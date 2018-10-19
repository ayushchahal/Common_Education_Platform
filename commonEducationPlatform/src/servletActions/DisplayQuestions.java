package servletActions;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbActions.TestCreators;

@SuppressWarnings("serial")
public class DisplayQuestions extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");  
		PrintWriter out= response.getWriter();
		
		try {
			HttpSession session = request.getSession(false);
			String sessionID = session.getId();
			
			Cookie[] cookie = request.getCookies();
			String cookieValue=cookie[0].getValue();

			//String username=(String) session.getAttribute("user");
			//int loginID=new DBConnection().getLoginID(username);
			if(!sessionID.equals(cookieValue))
			{
				out.print("<html><body>User not correctly authenticated</body></html>");
			}
			else 
			{
				//String testID = request.getParameter("testID");
				String testID = (String) request.getAttribute("testID");
				TestCreators tc = new TestCreators();
				int numberOfQuestions = tc.numberOfQuestionsInQuestionsTable(testID);
				String[] questionIDs = tc.getQuestionIDs(testID);
				
				String studentID = (String) request.getAttribute("studentID");
				/*
				Misc misc = new Misc();
				String questionID = misc.getRandomQuestionID(questionIDs);
				*/
				Object qNo = request.getAttribute("questionNumber");
				int questionNumber = 0;
				if(qNo == null)
				{
					questionNumber = 1;
				}
				else 
				{
					questionNumber = Integer.parseInt(qNo+"");
				}
				System.out.println("Question number: "+questionNumber);
				
				
				String questionID = questionIDs[questionNumber-1];
				System.out.println("QuestionID is: "+questionID);
				String hiddenQuestionID = "<input type=\"hidden\" name=\"questionID\" id=\"questionID\" value=\""+questionID+"\">";
				String hiddenStudentID = "<input type=\"hidden\" name=\"studentID\" id=\"studentID\" value=\""+studentID+"\">";			
				String hiddenTestID = "<input type=\"hidden\" name=\"testID\" id=\"testID\" value=\""+testID+"\">";
				String hiddenquestionNumber = "<input type=\"hidden\" name=\"questionNumber\" value=\""+questionNumber+"\">";
				
				int n = tc.getNumberOfOptionsInAQuestion(questionID);
				String[] options = new String[n];
				
				for(int i=0;i<n;i++)
				{
					options[i] = tc.getOption(questionID, (i+1)+"");
				}
				
				String questionText = tc.getQuestionText(questionID);
				String questionType = tc.getTypeOfQuestion(questionID);
				String positiveMarks = tc.getPositiveMarks(questionID);
				String negativeMarks = tc.getNegativeMarks(questionID);
				/*
				int totalTime = tc.getTestTime(testID);
				int hours = 0;
				
				if(totalTime<60)
					hours=0;
				else if(totalTime==60)
					hours=1;
				else
					hours=(int) Math.floor(totalTime/60);
				
				int minutes = totalTime - (60*hours);
				*/
				
				String timeLimitText = tc.getTestTimeLimitType(testID);
				int timeLimitType = 0;
				if(timeLimitText.contains("question"))
					timeLimitType = 1;
				else if(timeLimitText.contains("test"))
					timeLimitType = 2;
				
				System.out.println("TIME LIMIT TYPE IS: "+timeLimitType);
				
				int totalTimeInSeconds = 0;
				totalTimeInSeconds = tc.getQuestionTime(questionID);
				String hiddenDateTime = "<input type=\"hidden\" name=\"timerD\" id=\"timer1\" value=\""+0+"\">";
				String previousButton = "<input class=\"btn btn-primary float-left\" type=\"submit\" name=\"save\" id=\"prev\" value=\"To Previous Question\" style=\"margin-left:2%\">";
				String nextButton="<input class=\"btn btn-primary float-right\" type=\"submit\" name=\"save\" id=\"next\" value=\"To Next Question\" style=\"margin-right:2%\">";
				
				
				if(timeLimitType == 1)
				{
					
					String startTime = tc.insertFirstVisitedTime(studentID, questionID, testID, timeLimitType);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = df.parse(startTime);
					long epoch = date.getTime();
					
					System.out.println("##############EPOCH TIME IS: "+epoch);
					
					hiddenDateTime = "<input type=\"hidden\" name=\"timerD\" id=\"timer1\" value=\""+epoch+"\">";
					previousButton = "";
					nextButton="<input class=\"btn btn-primary float-right\" type=\"submit\" name=\"save\" id=\"next\" value=\"To Next Question\" style=\"margin-right:2%\" onclick=\"checkFraud()\">";
					
				}
				else if(timeLimitType == 2)
				{
					
					
					/*
					 * Get the test start time from table StudentTestAssociation (T1).
					 * If user is on first question, then the remaining time will be equal to the Total Time.
					 * When user clicks on 'To Next Question', record that time as well (T2).
					 * When Question 2 opens, calculate the time invested by subtracting T2 from T1 (let's say the result is T3).
					 * Now, subtract T3 from T and it will give us the total time left.
					 */	
					
					//Find out the total time allowed for test.
					totalTimeInSeconds = tc.getTestTime(testID)*60;
				    
					//Find out the test start time. (T1)
					String testStartTimestamp = tc.getTestStartTime(studentID, testID);
					long testStartTimeInEpoch = Long.parseLong(testStartTimestamp);
					testStartTimeInEpoch = (long) Math.floor(testStartTimeInEpoch/1000);
					System.out.println("Test Start Time is: "+testStartTimeInEpoch);
					
					
					String remainingTime = "";
					long questionCompletedTimeInEpoch = 0;
					int timeLeftInSeconds = 0;
					boolean isQuestionAlreadyVisited = tc.isQuestionAlreadyVisited(studentID, questionID);
					if(questionNumber == 1 && isQuestionAlreadyVisited == false)
					{
						remainingTime = ""+totalTimeInSeconds;
						timeLeftInSeconds = Integer.parseInt(remainingTime);
					}//Get time when user clicks on 'To Next Question' from SubmitQuestion servlet
					else
					{
						remainingTime  = (String) request.getAttribute("dateTimeInvested");
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date date2 = df.parse(remainingTime);
						questionCompletedTimeInEpoch = date2.getTime();
						questionCompletedTimeInEpoch = (long) Math.floor(questionCompletedTimeInEpoch/1000);
						System.out.println("Question submitted timestamp: "+ questionCompletedTimeInEpoch);
						long timeInvested = questionCompletedTimeInEpoch - testStartTimeInEpoch;
						System.out.println("Time invested is: "+timeInvested);
						timeLeftInSeconds = (int) (totalTimeInSeconds - timeInvested); 
					
					}
					System.out.println("Remaining time is: "+ timeLeftInSeconds );
					
					hiddenDateTime = "<input type=\"hidden\" name=\"timerD\" id=\"timer1\" value=\""+timeLeftInSeconds+"\">";
					 
					/* This code is not being used at the moment. 
					int hours = 0;
					if(totalTimeInSeconds<3600)
						hours=0;
					else if(totalTimeInSeconds==3600)
						hours=1;
					else
						hours=(int) Math.floor(totalTimeInSeconds/3600);
					
					int minutes = (int) Math.floor((totalTimeInSeconds - (3600*hours))/60);
					
					int seconds = totalTimeInSeconds - (60*60*hours+60*minutes);
					*/
					
				}
					
				String hiddenTimer = "<input type=\"hidden\" name=\"timerC\" id=\"timer\" value=\""+totalTimeInSeconds+"\">";
				
				/*
				 * Decide which javascript to execute for which time limit type
				 */
				String javascript = "";
				if(timeLimitType == 1)
					javascript = "timer.js";
				else 
					javascript = "timer1.js";
				
				
				//String html = "<html><head><link rel=\"stylesheet\" href=\"\\stylesheets\\displayQuestions.css\"><script src=\"\\javascript\\timer.js\"></script></head>";
				String html = "<html><head><meta charset=\"utf-8\">\r\n" + 
						"  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
						"  <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\">\r\n" + 
						"  <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\r\n" + 
						"  <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>\r\n" + 
						"    <link rel=\"stylesheet\" href=\"\\stylesheets\\displayQuestions.css\">"
						+ "<script src=\"https://unpkg.com/sweetalert/dist/sweetalert.min.js\"></script>"
						+ "<script src=\"\\javascript\\"+javascript+"\"></script>"
						+ "<script src=\"\\javascript\\"+"ajax.js"+"\"></script>"
						+ "<script src=\"\\javascript\\"+"clearAnswerFields.js"+"\"></script></head>"
						;
				
				String body = "<body><div class=\"container\">";
				String timer2 = "<div class=\"row\" style=\"margin-right:2%;\">\r\n" + 
						"    <div class=\"col-lg-9\"></div>\r\n" + 
						"    \r\n" + 
						"    <div class=\"col-lg-3\">\r\n" + 
						"    \r\n" + 
						"<label></label>"+
						"    <h3 style=\"margin-right:2%; position: fixed ; z-index: 1;\">    \r\n" + 
						"	<span class=\"badge badge-info\" id=\"badge\">Time left = <span id=\"timeleft1\"></span></span>\r\n" + 
						"    </h3>\r\n" + 
						"    </div>\r\n" + 
						"</div>";
				/*
				String timer2 = "<div class=\"row\" style=\"margin-right:2%;\">\r\n" + 
						"    <div class=\"col-lg-9\"></div>\r\n" + 
						"    \r\n" + 
						"    <div class=\"col-lg-3\">\r\n" + 
						"    \r\n" + 
						"<label></label>"+
						"    <h3 style=\"margin-right:2%;\">    \r\n" + 
						"	<span class=\"badge badge-info\">Time left = <span id=\"timeleft1\">"+hours+" h "+minutes+" m "+seconds+" s"+"</span></span>\r\n" + 
						"    </h3>\r\n" + 
						"    </div>\r\n" + 
						"</div>";
						*/
				
				//String timer = "<div style=\"margin-right:2%;\">Time left = <span id=\"timeleft\"></span></div>";
				String form = "<form action=\"SubmitQuestion\" method=\"Post\" id=\"questionPaper\"><div class=\"row\"><div class=\"col-sm-9\">";
				String questionCard="<div class=\"card border-transparent\" style=\"margin-left:2%;margin-right:2%;\">";
				String questionNumberlabel = "<h6 class=\"card-header\" id=\"questionHeader\">Question "+questionNumber+" of "+numberOfQuestions+"</h6>";
				String questionTextLabel = "<div class=\"card-body\"><p class=\"card-text\">"+questionText+"</p></div>";
				String positiveMarksLabel = "<footer class=\"card-footer text-right text-muted\"><small class=\"text-muted\">Correct answer: +"+positiveMarks+"<br> Incorrect answer: -"+negativeMarks+"</small></footer></div>";
				//String negativeMarksLabel = "<label> Incorrect answer: -"+negativeMarks+"</label>";
				//String previousButton = "<input class=\"lhs\" type=\"submit\" name=\"save\" value=\"To Previous Question\">";
				//String nextButton="<input class=\"rhs\" type=\"submit\" name=\"save\" value=\"To Next Question\">";
				//String submit="<input class=\"rhs\" type=\"submit\" name=\"save\" value=\"Submit test\">";
				String submit="<input value=\"Submit test\" name=\"save\" id=\"subm\" type=\"submit\" class=\"btn btn-success\" style=\"margin-left:45%;\">";
				String closeColSm9 = "</div>";
				
				String br = "<br>";
				String closeForm = "</form></div>";
				String closeBody = "</body>";
				String closehtml = "</html>";
				String rb1 = "";
				
				String optionsCard="<div class=\"card border-0\" style=\"margin-left:2%;margin-right:2%\">\r\n" + 
						"    <label><h6 class=\"card-header border\">Options</h6>\r\n" + 
						"    </label>";
				
				/*
				Object answer2 = request.getAttribute("answer");
				System.out.println(answer2);
				String a = "";
				*/
				String answer2 = tc.getAnswer(studentID, questionID);
				String a = "";
				if(answer2 == null)
				{
					a = "A";
				}
				else
				{
					a = (String) answer2;
				}
					
				System.out.println("Answer: "+a);
				
				String inputGroup="     <label>\r\n" + 
						"<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">\r\n" + 
						"  <div class=\"input-group-prepend\">\r\n" + 
						"    <div class=\"input-group-text\">";
				
				
				if(questionType.equals("1"))
				{
					//rb1 = "<input type=\"radio\" name=\"options\" class=\"radio\" value=\"true\" style=\"vertical-align: top;\">True<br><input type=\"radio\" name=\"options\" class=\"radio\" value=\"false\" style=\"vertical-align: top;\">False</div>";
					
					rb1="<label>\r\n" + 
							"<div class=\"input-group input-group-lg\" style=\"cursor:pointer\">\r\n" + 
							"  <div class=\"input-group-prepend\">\r\n" + 
							"    <div class=\"input-group-text\">\r\n" + 
							"    <input type=\"radio\" name=\"options\" value=\"true\">\r\n" + 
							"    </div>\r\n" + 
							"  </div>\r\n" + 
							"  <h7 class=\"form-control\">TRUE</h7>\r\n" + 
							"</div>\r\n" + 
							"</label>   \r\n" + 
							"\r\n" + 
							"\r\n" + 
							"<label><div class=\"input-group input-group-lg\" style=\"cursor:pointer\">\r\n" + 
							"  <div class=\"input-group-prepend\">\r\n" + 
							"    <div class=\"input-group-text\">\r\n" + 
							"    <input type=\"radio\" name=\"options\" value=\"false\">\r\n" + 
							"    </div>\r\n" + 
							"  </div>\r\n" + 
							"  <h7 class=\"form-control\">FALSE</h7>\r\n" + 
							"</div>\r\n" + 
							"    </label>";
				}
				
				else if(questionType.equals("2"))
				{
					for (int i=0;i<n;i++)
					{
						if(a.equals((i+1)+""))
							rb1 = rb1+inputGroup+ "<input type=\"radio\" name=\"options\" checked=\"checked\" value=\""+(i+1)+"\"></div></div><h7 class=\"form-control\">"+options[i]+"</h7>\r\n" + 
									"</div>\r\n" + 
									"    </label>";
							
						else
							rb1 = rb1+inputGroup+ "<input type=\"radio\" name=\"options\" value=\""+(i+1)+"\"></div></div><h7 class=\"form-control\">"+options[i]+"</h7>\r\n" + 
									"</div>\r\n" + 
									"    </label>";
					}
				}
				else if(questionType.equals("3"))
				{
					for (int i=0;i<n;i++)
					{
						rb1 = rb1 +inputGroup+ "<input type=\"checkbox\" name=\"options\" value=\""+(i+1)+"\">"+"</div></div><h7 class=\"form-control\">"+options[i]+"</h7>\r\n" + 
								"</div>\r\n" + 
								"    </label>";
					}
				}
				
				String resetButton = "<label>\r\n" + 
						" <input type=\"button\" class=\"btn btn-info\" value=\"Clear Answer\" onclick=\"clearAnswerFields()\">   \r\n" + 
						"</label>";
				
				
				String closeOptionsCard="</div>";
				
				String palleteDiv = "<div class=\"col-sm-3\">";
				String dataInPallete = "<br><br><h5>Question Pallete</h5>";
				String questionsInPallete = "";
				
				for(int i=0;i<numberOfQuestions;i++)
				{
					if(tc.isQuestionAlreadyVisited(studentID, questionIDs[i]) && timeLimitType == 1)
						questionsInPallete = questionsInPallete + "<input class=\"btn btn-basic\" type=\"submit\" name=\"pallete\" id=\""+questionIDs[i]+"\" value=\""+(i+1)+"\" style=\"margin-right:4%; margin-top: 2%;\" disabled>";
					else if(questionNumber == (i+1) && timeLimitType == 1)
						questionsInPallete = questionsInPallete + "<input class=\"btn btn-info\" type=\"submit\" name=\"pallete\" id=\""+questionIDs[i]+"\" value=\""+(i+1)+"\" style=\"margin-right:4%; margin-top: 2%;\" >";
					else
						questionsInPallete = questionsInPallete + "<input class=\"btn btn-basic\" type=\"submit\" name=\"pallete\" id=\""+questionIDs[i]+"\" value=\""+(i+1)+"\" style=\"margin-right:4%; margin-top: 2%;\" >";
				}
				
				String closePalleteDiv = "</div>";
				String closeRow = "</div>";
				
				int nextUnAnsweredQuestion = tc.getNextUnAnsweredQuestionNumber(questionNumber, testID, studentID, questionIDs);
				
				
				System.out.println("$$$$$$$$$$$  NEXT UNANSWERED QUESTION IS: "+nextUnAnsweredQuestion);
				
				if(questionNumber == 1)
					out.print(html+body+timer2+form+hiddenTimer+hiddenDateTime+hiddenQuestionID+hiddenStudentID+hiddenTestID+hiddenquestionNumber+questionCard+questionNumberlabel+questionTextLabel+positiveMarksLabel+br+optionsCard+rb1+resetButton+closeOptionsCard+br+nextButton+br+br+br+submit+closeColSm9+palleteDiv+dataInPallete+questionsInPallete+closePalleteDiv+closeRow+closeForm+closeBody+closehtml);
				else if((questionNumber == numberOfQuestions) && timeLimitType == 2)
					out.print(html+body+timer2+form+hiddenTimer+hiddenDateTime+hiddenQuestionID+hiddenStudentID+hiddenTestID+hiddenquestionNumber+questionCard+questionNumberlabel+questionTextLabel+positiveMarksLabel+br+optionsCard+rb1+resetButton+closeOptionsCard+br+previousButton+br+br+br+submit+closeColSm9+palleteDiv+dataInPallete+questionsInPallete+closePalleteDiv+closeRow+closeForm+closeBody+closehtml);
				else
				{
					if(timeLimitType == 1)
					{
						if(nextUnAnsweredQuestion == 0)
							out.print(html+body+timer2+form+hiddenTimer+hiddenDateTime+hiddenQuestionID+hiddenStudentID+hiddenTestID+hiddenquestionNumber+questionCard+questionNumberlabel+questionTextLabel+positiveMarksLabel+br+optionsCard+rb1+resetButton+closeOptionsCard+br+br+br+submit+closeColSm9+palleteDiv+dataInPallete+questionsInPallete+closePalleteDiv+closeRow+closeForm+closeBody+closehtml);
						else if(nextUnAnsweredQuestion < questionNumber)
							{
								previousButton = "<input class=\"btn btn-primary float-left\" type=\"submit\" name=\"save\" id=\"prev\" value=\"To Previous Question\" style=\"margin-left:2%\">";
								out.print(html+body+timer2+form+hiddenTimer+hiddenDateTime+hiddenQuestionID+hiddenStudentID+hiddenTestID+hiddenquestionNumber+questionCard+questionNumberlabel+questionTextLabel+positiveMarksLabel+br+optionsCard+rb1+resetButton+closeOptionsCard+br+previousButton+br+br+br+submit+closeColSm9+palleteDiv+dataInPallete+questionsInPallete+closePalleteDiv+closeRow+closeForm+closeBody+closehtml);
							}
						else if(nextUnAnsweredQuestion > questionNumber)
							out.print(html+body+timer2+form+hiddenTimer+hiddenDateTime+hiddenQuestionID+hiddenStudentID+hiddenTestID+hiddenquestionNumber+questionCard+questionNumberlabel+questionTextLabel+positiveMarksLabel+br+optionsCard+rb1+resetButton+closeOptionsCard+br+nextButton+br+br+br+submit+closeColSm9+palleteDiv+dataInPallete+questionsInPallete+closePalleteDiv+closeRow+closeForm+closeBody+closehtml);
						else
							out.print(html+body+timer2+form+hiddenTimer+hiddenDateTime+hiddenQuestionID+hiddenStudentID+hiddenTestID+hiddenquestionNumber+questionCard+questionNumberlabel+questionTextLabel+positiveMarksLabel+br+optionsCard+rb1+resetButton+closeOptionsCard+br+previousButton+nextButton+br+br+br+submit+closeColSm9+palleteDiv+dataInPallete+questionsInPallete+closePalleteDiv+closeRow+closeForm+closeBody+closehtml);
					}
					else
						out.print(html+body+timer2+form+hiddenTimer+hiddenDateTime+hiddenQuestionID+hiddenStudentID+hiddenTestID+hiddenquestionNumber+questionCard+questionNumberlabel+questionTextLabel+positiveMarksLabel+br+optionsCard+rb1+resetButton+closeOptionsCard+br+previousButton+nextButton+br+br+br+submit+closeColSm9+palleteDiv+dataInPallete+questionsInPallete+closePalleteDiv+closeRow+closeForm+closeBody+closehtml);
				}
			
			}
		}catch(Exception e)
		{
			out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();	
		}
	}
}
