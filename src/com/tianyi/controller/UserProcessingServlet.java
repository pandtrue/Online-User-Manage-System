package com.tianyi.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tianyi.domain.User;
import com.tianyi.service.UsersService;

public class UserProcessingServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		UsersService usersService = new UsersService();
		// Get process type
		String type = request.getParameter("type");
		// Delete user
		if(type.equals("del")) {
			// Get Id
			int id = Integer.parseInt(request.getParameter("id"));
			if(usersService.deleteUser(id)) {
				request.setAttribute("info", "Delete success!");
				request.getRequestDispatcher("/OK").forward(request, response);
			} else {
				request.setAttribute("info", "Error occued during delete!");
				request.getRequestDispatcher("/Error").forward(request, response);
			}
			//Update user info
		} else if(type.equals("gotoUpdateView")) {
			// Get id
			String id = request.getParameter("id");
			// Get userbean
			User user = usersService.getUserById(id);
			// To let the next servlet use this users object, we can put this user object
			// into request object
			request.setAttribute("user", user);
			// forward to next servlet
			request.getRequestDispatcher("/UpdateUserView").forward(request, response);
			// Update user info
		} else if(type.equals("update")) {
			// Get new user info
			String id = request.getParameter("id");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String level = request.getParameter("level");
			
			User user = new User();
			user.setId(Integer.parseInt(id));
			user.setName(username);
			user.setEmail(email);
			user.setPwd(password);
			// Level can be null
			if(level!="") {
				user.setLvl(Integer.parseInt(level));
			} else {
				user.setLvl(1);
			}
			
			if(usersService.updateUser(user)) {
				request.setAttribute("info", "Update success!");
				request.getRequestDispatcher("/OK").forward(request, response);
			} else {
				request.setAttribute("info", "Error occued during update!");
				request.getRequestDispatcher("/Error").forward(request, response);
			}
		} else if(type.equals("gotoAddUser")) {
			// Nothing happen here except forward
			request.getRequestDispatcher("/AddUserView").forward(request, response);
		} else if(type.equals("add")) {
			// Get new user info
			String email = request.getParameter("email");
			String id = request.getParameter("id");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String level = request.getParameter("level");
			
			User user = new User();
			user.setName(username);
			user.setEmail(email);
			user.setPwd(password);
			// Level can be null
			if(level!="") {
				user.setLvl(Integer.parseInt(level));
			} else {
				user.setLvl(1);
			}
			
			if(usersService.addUser(user)) {
				request.setAttribute("info", "Add success!");
				request.getRequestDispatcher("/OK").forward(request, response);
			} else {
				request.setAttribute("info", "Error occued during add!");
				request.getRequestDispatcher("/Error").forward(request, response);
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
