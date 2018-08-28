package servletActions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbActions.DBConnection;

@SuppressWarnings("serial")
public class ViewCoachingsInDropdown extends HttpServlet{
	//public static PrintWriter out;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");  
		PrintWriter out= response.getWriter();
		String[] data=new DBConnection().getAllCoachingName();
		String action= request.getRequestURI();
		String title = "student's";
		String hiddenParams = null;
		
		if(action.equals("/AddStudent1"))
		{
			action="AddStudent";
			String sname=request.getParameter("studentName");
			String sco=request.getParameter("cno1");
			String sEmail=request.getParameter("email");
			String sStatus=request.getParameter("IsActive");
			if(sStatus.equals("Yes"))
				sStatus="1";
			else
				sStatus="0";	
			hiddenParams="<input type=\"hidden\" name=\"sname\" value=\""+sname+"\">"+"<input type=\"hidden\" name=\"sco\" value=\""+sco+"\">"+"<input type=\"hidden\" name=\"sEmail\" value=\""+sEmail+"\">"+"<input type=\"hidden\" name=\"sStatus\" value=\""+sStatus+"\">";

		}
		else
		{
			action="AddTeacher";
			title="teacher's";
			String tname=request.getParameter("teacherName");
			String tco=request.getParameter("cno1");
			String tEmail=request.getParameter("email");
			String tStatus=request.getParameter("IsActive");
			if(tStatus.equals("Yes"))
				tStatus="1";
			else
				tStatus="0";
			
			hiddenParams="<input type=\"hidden\" name=\"tname\" value=\""+tname+"\">"+"<input type=\"hidden\" name=\"tco\" value=\""+tco+"\">"+"<input type=\"hidden\" name=\"tEmail\" value=\""+tEmail+"\">"+"<input type=\"hidden\" name=\"tStatus\" value=\""+tStatus+"\">";
			
		}
			
		out.println("<html>\r\n" + 
				"	<head>\r\n" + 
				"		<title>Specify "+title +" coaching</title>\r\n" +
				"<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">\r\n" + 
				"	<link rel=\"stylesheet\" href=\"coachingsDropdown.css\">\r\n" + 
				"	<link rel=\"stylesheet\" href=\"//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css\">"+
				"	</head>\r\n" + 
				"	\r\n" + 
				"	<body>\r\n" + 
				"	\r\n" + 
				"		<form action=\"/"+action+"\" method=\"post\">\r\n" + 
				"		\r\n" + 
				"		<select name=\"cname\">");
		
		out.println("<option disabled selected value > -- select a coaching -- </option>");
		
		for(int i=0;i<data.length;i++)
		{
			//String a="<option value=\""+(i+1)+"\">"+data[i]+"</option>";
			String a="<option>"+data[i]+"</option>";
			//System.out.println(a);
			out.println(a);
		}
		
		out.println(hiddenParams);
		
		
		out.println("</select>\r\n" + 
				"		\r\n" + 
				"<input type=\"submit\" class=\"bouton-contact\" value=\"Submit\">"+
				"		</form>\r\n" + 
				"	</body>\r\n" + 
				"\r\n" + 
				"</html>");
		
	}

}
