package com.tianyi.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<img src='image/Welcome_Friends.jpg' width='300px'/><hr/>");

		// Return a login interface
		out.println("<h1>User Login</h1>");
		// Action should be in form : Webapp name/ Servlet Url
		out.println("<form action='/UserManagement/LoginCheckServlet' method='post'>");
		out.println("User &nbsp;ID:<input type='text' name='id'/><br/>");
		out.println("Password:<input type='password' name='password'/><br/>");
		out.println("Verification:<input type='text' name='varification'/><img src='/UserManagement/GenerateRandomPicture'><br/>");
		out.println("<input type='submit' value='Login'/><br/>");
		out.println("</form>");
		String errInfo = (String) request.getAttribute("err");
		if(errInfo != null) {
			out.println("<font color='red'>" + errInfo + "</font>");
		}
		out.println("<hr/><img src='image/thankyou.jpg' width='300px'/>");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Normally, developer always combine doGet() and doPost()
				this.doGet(request, response);
	}
}
