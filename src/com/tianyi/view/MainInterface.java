package com.tianyi.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tianyi.domain.User;
import com.tianyi.service.UsersService;

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
		
		// Get user name
		int id = ((User) request.getSession().getAttribute("user")).getId();
		String username = new UsersService().getUserById(String.valueOf(id)).getName();
		// Store username in session for later use
		user.setName(username);
		
		// Get previous login time
		String lasttime = "";
		boolean notFirstTime = false;
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("lasttime")) {
					lasttime = "Your last login time is " + cookie.getValue();
					notFirstTime = true;
					break;
				}
			}
		}
		if(notFirstTime==false) {
			lasttime = "This if your first time login";
			// Save time to cookie
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
			String time = simpleDateFormat.format(new Date());
			Cookie cookie= new Cookie("lasttime", time);
			cookie.setMaxAge(3600*24*7);
			response.addCookie(cookie);
		}
		
		out.println("<img src='image/Welcome_Friends.jpg' width='300px'/>Hello " + username + "! &nbsp;&nbsp;" +
				lasttime + " &nbsp;&nbsp;");
		out.println("<h3>Choose the action you want to perform</h3>");
		out.println("<a href='/UserManagement/ManageUser'>Manage User</a><br/>");
		out.println("<a href='/UserManagement/UserProcessingServlet?type=gotoAddUser'>Add New User</a><br/>");
		out.println("<a href='/UserManagement/SearchUserView'>Search User</a><br/>");
		out.println("<a href='/UserManagement/LoginServlet'>Logout</a><br/>");
		
		// Get number of viewed
		int viewedNumber = Integer.parseInt((String) this.getServletContext().getAttribute("viewedNumber"));
		out.println("<hr/>This website has been viewed " + viewedNumber + " times<br/>");
		out.println("<img src='image/thankyou.jpg' width='300px'/>");
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Normally, developer always combine doGet() and doPost()
		this.doGet(request, response);
	}
}
