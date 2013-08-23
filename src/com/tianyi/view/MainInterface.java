package com.tianyi.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tianyi.domain.User;

public class MainInterface extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		// Protect illegal login 1. user method with session 2. use filter
		User user = (User) request.getSession().getAttribute("user");
		if(user==null) {
			request.setAttribute("err", "Please login frist");
			request.getRequestDispatcher("/LoginServlet").forward(request, response);
			return;
		}
		out.println("<img src='image/Welcome_Friends.jpg' width='300px'/>Hello XX! " +
				"<a href='/UserManagement/LoginServlet'>Logout</a><hr/>");
		out.println("<h3>Choose the action you want to perform</h3>");
		out.println("<a href='/UserManagement/ManageUser'>Manage User</a><br/>");
		out.println("<a href='/UserManagement/UserProcessingServlet?type=gotoAddUser'>Add New User</a><br/>");
		out.println("<a href='#'>Find User</a><br/>");
		out.println("<a href='/UserManagement/LoginServlet'>Exit</a><br/>");
		out.println("<hr/><img src='image/thankyou.jpg' width='300px'/>");
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Normally, developer always combine doGet() and doPost()
		this.doGet(request, response);
	}
}
