package com.tianyi.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tianyi.domain.User;
import com.tianyi.service.UsersService;

public class ManageUser extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		// Get user name from session
		String username = ((User) request.getSession().getAttribute("user")).getName();
		
		// JavaScript use to control the page jumping
		out.println("<script type='text/javascript' language='javascript'>");
		out.println("function gotoPage() { var pageWant = document.getElementById('pageWant').value;" +
				"window.open('/UserManagement/ManageUser?currentPage='+pageWant,'_self');}" +
				"function confirmation(){return  window.confirm('Are you sure to delete this user?');}");
//		return window.confirm('Are you sure to delete this user?');
		out.println("</script>");
		out.println("<img src='image/Welcome_Friends.jpg' width='300px'/> " +
				"<a href='/UserManagement/MainInterface'>Back to Home Page</a><hr/>");

		out.println("<h1>Manage User</h1>");
		
		//Define things needed by paging
		int currentPage = 1; 
		int pageCount = 0;
		int pageSize = 3; 	//Number of rows in every page
		
		// Get currentPage from user
		if(request.getParameter("currentPage")!=null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		try {
			UsersService userService = new UsersService();
			pageCount = userService.getPageCount(pageSize);
			
			ArrayList<User> al = userService.getUserByPage(currentPage, pageSize);
			out.println("<table border='1px' width='500px' bordercolor='green' cellspacing='0'>");
			out.println("<tr><th>User ID</th><th>Name</th><th>Email</th><th>Level</th><th>Delete User</th><th>Edit User</th></tr>");
			for(User u: al) {
				out.println("<tr><td>"+u.getId()+
						"</td><td>"+u.getName()+
						"</td><td>"+u.getEmail()+
						"</td><td>"+u.getLvl()+
						"</td><td><a href='/UserManagement/UserProcessingServlet?type=del&id="+u.getId()+"' onclick='return confirmation()'>Delete</a>"+
						"</td><td><a href='/UserManagement/UserProcessingServlet?type=gotoUpdateView&id="+u.getId()+"'>Edit</a></tr>");
			}
			out.println("</table><br/>");
			
			// Show previous page
			if(currentPage!=1) {
				out.println("<a href='/UserManagement/ManageUser?currentPage="+(currentPage-1)+"'>Previous</a>");
			}
			
			// Show paging
			for(int i=1;i<=pageCount;i++) {
				out.println("<a href='/UserManagement/ManageUser?currentPage="+i+"'><"+i+"></a>");
			}
			
			// Show next page
			if(currentPage!=pageCount) {
				out.println("<a href='/UserManagement/ManageUser?currentPage="+(currentPage+1)+"'>Next</a>");
			}

			// Show paging info
			out.println("&nbsp;Current page" + currentPage +" /Total page" + pageCount + "<br/>");
			
			// Use JS to implement jump
			out.println("Go to page<input type='text' id='pageWant' name='pageWant'/> <input type='button' value='Go' onClick='gotoPage()'><br/><br/>");
		} catch (Exception e) {
			e.printStackTrace();
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
