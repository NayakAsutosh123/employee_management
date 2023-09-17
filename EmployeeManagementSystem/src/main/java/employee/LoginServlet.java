package employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection con;
    public LoginServlet() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","lion");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String s1=request.getParameter("uname");
			String s2=request.getParameter("pword");
			PreparedStatement pstmt=con.prepareStatement("select * from loginadmin where uname=? and pword=?",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			pstmt.setString(1, s1);
			pstmt.setString(2, s2);
			ResultSet rs=pstmt.executeQuery();
			PrintWriter pw=response.getWriter();
			pw.println("<html><body bgcolor=cyan text=black><center>");
			if(rs.next()) {
				RequestDispatcher rd=request.getRequestDispatcher("homepage.html");
				rd.forward(request, response);
			}
			else {
				
				pw.println("Invalid Username/Password");
				pw.println("<a href=login.html>Back</a>");
				pw.println("</center></body></html>");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
