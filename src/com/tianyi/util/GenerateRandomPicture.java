package com.tianyi.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class GenerateRandomPicture extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 1. Generate one picture in memory
		BufferedImage image = new BufferedImage(60, 25,	BufferedImage.TYPE_INT_RGB);
		// 2. Generate graphic and set properties
		Graphics g = image.getGraphics();
		// Set background
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, 60, 25);
		// 3. Set font color and size
		g.setColor(Color.BLACK);
		g.setFont(new Font(null, Font.BOLD, 20));
		// 4. Write data to picture
		// Generate random number and store in session for later check
		String num = makeNum();
		request.getSession().setAttribute("number", num); 
		g.drawString(num, 0, 20);
		// 5.Send picture to browser
		ImageIO.write(image, "jpg", response.getOutputStream());
		// 6.Inform browse to open data in image form
		response.setHeader("Content-Type", "image/jpeg");
		// 7.½ûÖ¹ä¯ÀÀÆ÷»º´æËæ»úÍ¼Æ¬ Forbid browser store picture
		response.setDateHeader("Expires", -1);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
	}
	
	// Generate String with 4 random numbers
	public String makeNum() {
		Random r = new Random();
		String num = r.nextInt(9999) + ""; 
		StringBuffer sb = new StringBuffer();
		// Append 0 if number generated is not 4 digit
		for (int i = 0; i < 4 - num.length(); i++) {
			sb.append("0");
		}
		num = sb.toString() + num;
		return num;
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		this.doGet(request, response);
	}
}
