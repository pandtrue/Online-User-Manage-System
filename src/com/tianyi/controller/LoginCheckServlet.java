package com.tianyi.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpCookie;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tianyi.domain.User;
import com.tianyi.service.UsersService;

/**
 * Use this class as a controller to:
 * 1. Receive the data from the login page.
 * 2. Check which page should it jump to.
 */
public class LoginCheckServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		// Receive username and password from the form.
		String userID = request.getParameter("id");
		String password = request.getParameter("password");
		String varification = request.getParameter("varification");
		String keepInfo = request.getParameter("keepInfo");
		
		// Receive real generated verification from session
		String generatedVerification = (String) request.getSession().getAttribute("number");
		
		// Check verification id correct or not
		if(!varification.equals(generatedVerification)) {
			request.setAttribute("err", "Verification is not correct!");
			request.getRequestDispatcher("/LoginServlet").forward(request, response);
			return;
		}
		// Verification correct, check userid and password in database
		UsersService usersService = new UsersService();
		User user = new User();
		user.setId(Integer.parseInt(userID));
		user.setPwd(password);
			if(usersService.checkUser(user)) {
				// User is valid
				// Save session for later use, prevent user from illegal login
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				
				// Set attribute to servlet context to show the number of people browse the website
				String viewedNumber = (String) this.getServletContext().getAttribute("viewedNumber");
				if(viewedNumber==null) {
					// First time being viewed
					this.getServletContext().setAttribute("viewedNumber", "1");
				} else {
					this.getServletContext().setAttribute("viewedNumber", String.valueOf(Integer.parseInt(viewedNumber)+1));
				}
				
				// Every info is correct, remember info in this computer
				if(keepInfo!=null && keepInfo.equals("keep")) {
					// Store info in cookie
					Cookie cookie = new Cookie("username",userID);
					Cookie cookie2 = new Cookie("password",password);
					cookie.setMaxAge(3600*24*7);
					cookie2.setMaxAge(3600*24*7);
					response.addCookie(cookie);
					response.addCookie(cookie2);
				}
				
				// Jump to main interface
				request.getRequestDispatcher("/MainInterface").forward(request,response);
			} else {
				request.setAttribute("err", "UserID or password not correct!");
				request.getRequestDispatcher("/LoginServlet").forward(request,response);
			}
		}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Normally, developer always combine doGet() and doPost()
		this.doGet(request, response);
	}
}
