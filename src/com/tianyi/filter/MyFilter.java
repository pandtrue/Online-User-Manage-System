package com.tianyi.filter;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tianyi.domain.User;

public class MyFilter extends HttpServlet implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		// Get uri
		String uri = httpServletRequest.getRequestURI();
		if(uri.startsWith("/UserManagement/image") || uri.startsWith("/UserManagement/Login") ||
				uri.startsWith("/UserManagement/GenerateRandomPicture") || 
				uri.startsWith("/UserManagement/index.jsp")) {
			// Let it pass
			chain.doFilter(request, response);
		} else {
			HttpSession session = httpServletRequest.getSession();
			User user = (User)session.getAttribute("user");
			if(user != null) {
				// Valid user, let is pass
				System.out.println(user);
				chain.doFilter(request, response);	
			} else {
				request.setAttribute("err", "Please Login First");
				httpServletRequest.getRequestDispatcher("/LoginServlet").forward(request, response);
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
