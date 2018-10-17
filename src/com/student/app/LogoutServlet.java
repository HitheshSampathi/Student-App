package com.student.app;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/logoutServ")
public class LogoutServlet extends HttpServlet
{
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	HttpSession session=req.getSession(false);
	if(session !=null)
	{
		session.invalidate();
		Cookie[] cookies=req.getCookies();
		if(cookies != null) {
			for (Cookie c : cookies) {
				if(c.getName() =="JSESSIONID")
					System.out.println(c.getName());
				c.setMaxAge(0);
				resp.addCookie(c);
				}
		}
	
	}
	String url="http://localhost:8090/StudentApp/Login.html";
	resp.sendRedirect(url);
}
}
