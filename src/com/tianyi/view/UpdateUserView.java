package com.tianyi.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tianyi.domain.User;

public class UpdateUserView extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<img src='image/Welcome_Friends.jpg' width='300px'/>" + 
				"<a href='/UserManagement/MainInterface'>Back to Home Page</a><hr/>");
		out.println("<h3>Update User Information</h3>");
		User user = (User) request.getAttribute("user");
		out.println("<form action='/UserManagement/UserProcessingServlet?type=update' method='post'>");
		out.println("<table border='1px' width='250px' bordercolor='green' cellspacing='0'>");
		out.println("<tr><td>User ID</td><td><input type='text' name='id' readonly value='"+ user.getId() +"'></td></tr>");
		out.println("<tr><td>Name</td><td><input type='text' name='username' value='"+ user.getName() +"'></td></tr>");
		out.println("<tr><td>Password</td><td><input type='text' name='password' value='"+ user.getPwd() +"'></td></tr>");
		out.println("<tr><td>Email</td><td><input type='text' name='email' value='"+ user.getEmail() +"'></td></tr>");
		out.println("<tr><td>Level</td><td><input type='text' name='level' value='"+ user.getLvl() +"'></td></tr>");
		out.println("<tr><td><input type='submit' value='Update'></td><td><input type='reset' value='Reset'></td></tr>");
		out.println("</table>");
		out.println("</form>");
		
		out.println("<hr/><img src='image/thankyou.jpg' width='300px'/>");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}
}
