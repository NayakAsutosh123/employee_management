package employee;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class AddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection con;
    public AddServlet() {
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
			String s1=request.getParameter("empid");
			String s2=request.getParameter("ename");
			String s3=request.getParameter("fname");
			String s4=request.getParameter("dob");
			String s5=request.getParameter("salary");
			String s6=request.getParameter("address");
			String s7=request.getParameter("phone");
			String s8=request.getParameter("email");
			String s9=request.getParameter("designation");
			Float n1=Float.parseFloat(s5);
			Float n2=Float.parseFloat(s7);
			PreparedStatement pstmt=con.prepareStatement("insert into employee values(?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1, s1);
			pstmt.setString(2, s2);
			pstmt.setString(3, s3);
			pstmt.setString(4, s4);
			pstmt.setFloat(5, n1);
			pstmt.setString(6, s6);
			pstmt.setFloat(7, n2);
			pstmt.setString(8, s8);
			pstmt.setString(9, s9);
			pstmt.executeUpdate();
			PrintWriter pw=response.getWriter();
			pw.println("<html><body bgcolor=cyan text=black><center>");
			pw.println("<h1>Data Inserted successfully...</h1>");
			pw.println("<a href=homepage.html>Back</a></center>");
			pw.println("</body><html>");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
