package com.student.app;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/loginServ")
public class LoginServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = req.getParameter("sid");
		String passwd = req.getParameter("password");
//		System.out.println(userId);
//		System.out.println(passwd);
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			/*
			 * 1. Load the Driver
			 */
			Class.forName("com.mysql.jdbc.Driver");
			
			/*
			 * 2. Get the DB Connection via Driver
			 */
						String dbUrl="jdbc:mysql://localhost:3306/capsv4_db?user=root&password=root";
						
			//2nd Version of getConnection
				FileReader in = new FileReader("E:/capsv4/db.properties");
				Properties prop = new Properties();
				prop.load(in);
				con = DriverManager.getConnection(dbUrl,prop);
						

			System.out.println("Connected...");
			
			/*
			 * 3. Issue the SQL query via connection
			 */
			String sql = "select * from students_info where "
					+ " sid=? and password=?";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(userId));
			pstmt.setString(2, passwd);
			rs = pstmt.executeQuery();

			/*
			 * 4. Process the results
			 */PrintWriter out = resp.getWriter();
			

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


			
			
			if(rs.next()){
				HttpSession session=req.getSession();
			
				int regno = rs.getInt("sid");
				String firstname = rs.getString("firstname");
				String lastname = rs.getString("lastname");
				String gender = rs.getString("gender");
				passwd = rs.getString("password");
				String type = rs.getString("type");

				out.print("<tr><td>"+regno+"</td>"
						+ "<td>"+firstname+"</td>"
						+ "<td>"+lastname+"</td>"
						+ "<td>"+gender+"</td>"
						+ "<td>"+passwd+"</td>"
						+ "<td>"+type+"</td></tr>");
				out.println("</table>");
				out.println("<a href="+"./viewAllStudents"+">view all students </a>");
				
				out.println("</body>");
				out.println("</html>");
			}else {
				out.println("Login Failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			// 5. close all the JDBC Objects
					try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					try {
						pstmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
		}
	}
}