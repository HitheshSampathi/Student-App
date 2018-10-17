package com.student.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Driver;
@WebServlet("/viewAllStudents")
public class ViewAllStudentsServlet extends HttpServlet {
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	PrintWriter out=resp.getWriter();
	try {
		/*
		 * 1. Load the Driver
		 */
		Class.forName("com.mysql.jdbc.Driver");

		/*
		 * 2. Get the DB Connection via Driver
		 */
		String dbUrl="jdbc:mysql://localhost:3306/capsv4_db"
				+ "?user=root&password=root";
		con = DriverManager.getConnection(dbUrl); //1st version of getConnection

		System.out.println("Connected...");

		/*
		 * 3. Issue the SQL query via connection
		 */
		String sql = "select * from students_info ";

		stmt = con.createStatement();
		rs = stmt.executeQuery(sql);
		//stmt.getWarnings();
		/*
		 * 4. Process the results
		 */
		HttpSession session=req.getSession(false);
		
		if(session !=null) {

		out.println("<html>");
		out.println("<body>");
		out.println("<h1>Students Info</h1>");
		out.println("<table border='1px' cellspacing='0px' cellpadding='10px'>");
		out.print("<tr><th>sid</th>"
				+ "<th>firstname</th>"
				+ "<th>lastname</th>"
				+ "<th>gender</th>"
				+ "<th>password</th>"
				+ "<th>type</th></tr>");
		
		   while(rs.next()){
			
				
			int regno = rs.getInt("sid");
			
			String firstname = rs.getString("firstname");
			String lastname = rs.getString("lastname");
			String gender = rs.getString("gender");
			String passwd = rs.getString("password");
			String type = rs.getString("type");

			out.print("<tr><td>"+regno+"</td>"
					+ "<td>"+firstname+"</td>"
					+ "<td>"+lastname+"</td>"
					+ "<td>"+gender+"</td>"
					+ "<td>"+passwd+"</td>"
					+ "<td>"+type+"</td></tr>");
			
		}
		   out.println("</table>");
		   out.println("<a href="+"./logoutServ"+">LOGOUT </a>");
			out.println("</body>");
			out.println("</html>");
			}else {
				
				out.println("<a href="+"http://localhost:8090/StudentApp/Login.html"+">LOGIN to view all students</a>");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
}
