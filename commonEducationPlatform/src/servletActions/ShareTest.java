package servletActions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbActions.TestCreators;
import dynamicResponses.Misc;

@SuppressWarnings("serial")
public class ShareTest extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");  
		PrintWriter out= response.getWriter();
		
		try {
			HttpSession session = request.getSession(false);
			String sessionID = session.getId();
			
			Cookie[] cookie = request.getCookies();
			String cookieValue=cookie[0].getValue();

			
			if(!sessionID.equals(cookieValue))
			{
				out.print("<html><body>User not correctly authenticated</body></html>");
			}
			else
			{
				String testID=request.getParameter("testID");
				String shareDate=request.getParameter("odate");
				String shareTime=request.getParameter("otime");
				
				String scoreSetting = request.getParameter("scoreCard");
				String scoreDate=request.getParameter("scoreDate");
				String scoreTime=request.getParameter("scoreTime");
				
				
				String rankSetting = request.getParameter("rankCard");
				String rankDate=request.getParameter("rankDate");
				String rankTime=request.getParameter("rankTime");
				
				String closeDate=request.getParameter("cdate");
				String closeTime=request.getParameter("ctime");
				
				
				System.out.println("Open Date and Time is: "+ shareDate+" "+shareTime);
				System.out.println("Score Date and Time is: "+ scoreDate+" "+scoreTime);
				System.out.println("Rank Date and Time is: "+ rankDate+" "+rankTime);
				System.out.println("Close Date and Time is: "+ closeDate+" "+closeTime);
				
				
				TestCreators tc=new TestCreators();
				tc.insertShareDate(testID, shareDate,shareTime);
				
				tc.insertScoreCardSetting(testID, scoreSetting);
				if(scoreSetting.equals("2"))
				{
					tc.insertScoreCardDate(testID, scoreDate, scoreTime);
				}
				
				tc.insertRankSetting(testID, rankSetting);
				if(rankSetting.equals("2"))
				{
					tc.insertRankDate(testID, rankDate, rankTime);
				}
				tc.insertTestCloseDate(testID, closeDate, closeTime);
				
				
				String html="<html>";
				String head="<head>";
				String bootstrapCDN = "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\">";
				String closeHead="</head>";
				String body="<body>";
				
				String header = new Misc().HtmlHeader();
				
				String breadCrumb = "<nav aria-label=\"breadcrumb\">  <ol class=\"breadcrumb\">    <li class=\"breadcrumb-item\"><a href=\"/CoachingDashboard\">Home</a></li>    <li class=\"breadcrumb-item\" aria-current=\"page\">\r\n" + 
						"    <a href=\"/DisplayCreateTestPage\">Create Test</a></li>\r\n" + 
						"<li class=\"breadcrumb-item active\" aria-current=\"page\">Create Questions</li>" +
						"<li class=\"breadcrumb-item active\" aria-current=\"page\">Share Test</li>  </ol></nav>";
						
				
				String panel = "<div class=\"card-body\">" + "<div class=\"alert alert-success\" role=\"alert\" style=\"width:60%\">";
				String status = "Test successfully created.";
				String closePanel = "</div></div>";
				String closebody="</body>";
				String closeHtml="</html>";
				
				out.print(html+head+bootstrapCDN+closeHead+body+header+breadCrumb+panel+status+closePanel+closebody+closeHtml);
			}
		}catch(Exception e)
		{
			out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();
		}
	}

}
