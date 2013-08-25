package com.tianyi.util;

/**
 * This servlet used for store and get total number of viewed of this website 
 * when server shut down and start up. So it can accumulate the viewed number
 * and prevent losing data from unintentional shut down of server.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class InitializeServlet extends HttpServlet {
	
	// Store number into servlet context from file
	public void destroy() {
		String path = this.getServletContext().getRealPath("record.txt");
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(path));
			String number = (String) this.getServletContext().getAttribute("viewedNumber");
			bufferedWriter.write(number);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(bufferedWriter!=null){
				try {
					bufferedWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			bufferedWriter = null;
		}
	}

	// Read number from file into servlet context
	public void init() throws ServletException {
		String path = this.getServletContext().getRealPath("record.txt");
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(path));
			String number = bufferedReader.readLine();
			this.getServletContext().setAttribute("viewedNumber", number);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(bufferedReader!=null){
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			bufferedReader = null;
		}
	}

}
