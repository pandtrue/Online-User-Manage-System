package com.tianyi.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tianyi.domain.User;

public class SearchUserView extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<img src='image/Welcome_Friends.jpg' width='300px'/>" +
				"<a href='/UserManagement/MainInterface'>Back to Home Page</a><hr/>");
		out.println("<h3>Search User</h3>");
		
		User user = new User();
		out.println("<form action='/UserManagement/UserProcessingServlet?type=search' method='post'>");
		out.println("Name you want to serach: <input type='text' name='searchName'/><br/>");
		out.println("Fuzzy search<input type='radio' value='fuzzy' name='searchType'/> " + 
				"Exact match<input type='radio' checked value='exact' name='searchType'/> ");
		out.println("<input type='submit' value='Find'>");
		out.println("</form>");
		
		// Show searched result
		ArrayList<User> users = (ArrayList<User>) request.getAttribute("users");
		if(users!=null) {
			if(users.size()!=0) {
				out.println("<table border='1px' width='500px' bordercolor='green' cellspacing='0'>");
				out.println("<tr><th>User ID</th><th>Name</th><th>Email</th><th>Level</th></tr>");
				for(User u : users) {
					out.println("<tr><td>" + u.getId() + "</td><td>" + u.getName() + "</td><td>" + 
							u.getEmail() + "</td><td>" + u.getLvl() + "</td></tr>");
				}
				out.println("</table><br/>");
			} else {
				out.println("No Such User!");
			}
		}

		// Get number of viewed
		int viewedNumber = Integer.parseInt((String) this.getServletContext().getAttribute("viewedNumber"));
		out.println("<hr/>This website has been viewed " + viewedNumber + " times<br/>");
		out.println("<img src='image/thankyou.jpg' width='300px'/>");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
