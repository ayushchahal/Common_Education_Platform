package servletActions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbActions.DBConnection;
import dbActions.TestCreators;
import dynamicResponses.Misc;

@SuppressWarnings("serial")
public class ViewBatches extends HttpServlet {  

	
	//public static PrintWriter Coaching_out;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");  
		PrintWriter out=response.getWriter();
		try {
			
			Cookie[] cookie = request.getCookies();
			String cookieValue=cookie[0].getValue();
			//System.out.println(cookieValue);
			
			HttpSession session = request.getSession(false);
			//System.out.println(session);
			String sessionID = session.getId();
			
			String username=(String) session.getAttribute("user");
			int loginType=new DBConnection().getLoginType(username);
			if(loginType == 2)
			{
				if(!sessionID.equals(cookieValue))
				{
					out.print("<html><body>User not correctly authenticated</body></html>");
				}
				else 
				{
					
					String coachingID = (String)session.getAttribute("coachingID");
					
					out.println("<html>"+"<head>");
					String bootstrapCDN = "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\">";
					out.print(bootstrapCDN);
					
					out.print("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\r\n" + 
							"\r\n" + 
							"<!-- Popper JS -->\r\n" + 
							"<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js\"></script>\r\n" + 
							"\r\n" + 
							"<!-- Latest compiled JavaScript -->\r\n" + 
							"<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\"></script>");
					
					out.print("<script>" + 
							"    $(\"#exampleModal\").modal();\r\n" + 
							"</script>");
					
					
					out.println("</head>");
					
					out.println("<body>");
					String dashboard = "Dashboard";
					if(loginType == 2)
						dashboard = "CoachingDashboard";
					else if(loginType == 3)
						dashboard = "TeacherDashboard";
					
					String breadcrumbs= "<nav aria-label=\"breadcrumb\">\r\n" + 
							"  <ol class=\"breadcrumb\">\r\n" + 
							"    <li class=\"breadcrumb-item\"><a href=\""+dashboard+"#\">Home</a></li>\r\n" + 
							"    <li class=\"breadcrumb-item active\" aria-current=\"page\">Batches</li>\r\n" + 
							"  </ol>\r\n" + 
							"</nav>";
					
					String header = new Misc().HtmlHeader();
					
					out.print(header);
					String cardBodyStart = "<div class=\"card-body\">";
					out.print(cardBodyStart);
					out.print(breadcrumbs);
					//out.print("<input type = \"submit\" class = \"btn btn-primary float-right\" value = \"Create Batch\">");
					
					out.print("<button type=\"button\" class=\"btn btn-primary float-right\" data-toggle=\"modal\" data-target=\"#exampleModal\">\r\n" + 
							"  Create Batch\r\n" + 
							"</button>");
					out.print("<br>");
					out.print("<br>");
					out.println("<table class=\"table table-striped table-bordered\">");
					String tableheaders = "<tr><th>Batch Name</th><th>Number Of Students</th><th>Details</th></tr>";
					out.println(tableheaders);
					TestCreators tc = new TestCreators();
					String[] batchNames = tc.getCoachingBatchNames(coachingID);
					String[] batchNamesHTML = tc.getCoachingBatchNamesInHTMLFormat(coachingID);
					
					for(int i = 0; i< batchNames.length; i++)
					{
						int n = tc.getNumberOfStudentsInABatch(batchNames[i]);
						out.print(batchNamesHTML[i]);
						out.print("<td>"+n+"</td>");
						out.print("<td><form method=\"Post\" action=\"BatchActions\">"+
						"<input class=\"btn btn-secondary\" type=\"submit\" name=\"Add\" value=\"Add Students\">&nbsp;&nbsp"
					+ 	"<input class=\"btn btn-secondary\" type=\"submit\" name=\"Remove\" value=\"Remove Students\"><p></p>"
					+ 	"<input class=\"btn btn-secondary\" type=\"submit\" name=\"Add\" value=\"View Students\">&nbsp;&nbsp"
					+ 	"<input class=\"btn btn-danger\" type=\"submit\" name=\"Delete\" value=\"Delete Batch\">"
					+ 	"</form></td>");
					
					}
					
					
					
					out.print("</tr>");
					
					String modalPopup = "<div class=\"modal fade\" id=\"exampleModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">\r\n" + 
							"	<div class=\"modal-dialog\" role=\"document\">\r\n" + 
							"		<div class=\"modal-content\">\r\n" + 
							"            <form action=\"/CreateBatch\" method=\"GET\">\r\n" + 
							"                <div class=\"modal-header\">\r\n" + 
							"                    <h5 class=\"modal-title\" id=\"exampleModalLiveLabel\">\r\n" + 
							"						Create Batch\r\n" + 
							"					</h5>\r\n" + 
							"					<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">\r\n" + 
							"						<span aria-hidden=\"true\">\r\n" + 
							"							X\r\n" + 
							"						</span>\r\n" + 
							"					</button>\r\n" + 
							"				</div>\r\n" + 
							"				\r\n" + 
							"				<div class=\"modal-body\">\r\n" + 
							"					<div class=\"input-group input-group-default\">\r\n" + 
							"						<div class=\"input-group-prepend\">\r\n" + 
							"							<span class=\"input-group-text\" id=\"inputGroup-sizing-lg\">\r\n" + 
							"								Batch Name\r\n" + 
							"							</span>\r\n" + 
							"						</div>\r\n" + 
							"						<input type=\"text\" class=\"form-control\" aria-label=\"Large\" name=\"batchName\" aria-describedby=\"inputGroup-sizing-sm\" placeholder=\"Enter batch name\" required>\r\n" + 
							"						<input type=\"hidden\" name=\"coachingID\" value=\"91\">\r\n" + 
							"					</div>\r\n" + 
							"				</div>\r\n" + 
							"				\r\n" + 
							"				<div class=\"modal-footer\">\r\n" + 
							"					<input type=\"submit\" class=\"btn btn-primary\">\r\n" + 
							"				</div>\r\n" + 
							"			</form>\r\n" + 
							"		</div>\r\n" + 
							"	</div>\r\n" + 
							"</div>";
					
					
					out.print(modalPopup);
					
					out.println("</table>");
					
					out.println("</div></div></body></html>");
				}
			}
			else
			{
				out.print("<html><body>You are not supposed to view that.</body></html>");
			}
			
			
			
		}catch(Exception e)
		{
			out.print("<html><body>Err...Something went wrong.</body></html>");
			e.printStackTrace();
		}
		
	}

}
