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
import java.sql.ResultSet;
import java.sql.SQLException;

public class RemoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection con;
    public RemoveServlet() {
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
			String s=request.getParameter("empid");
			PrintWriter pw=response.getWriter();
			pw.println("<html><body bgcolor=cyan text=black><center>");
			
				PreparedStatement pstmt=con.prepareStatement("select ename from employee where empid=? ",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				pstmt.setString(1, s);
				ResultSet rs=pstmt.executeQuery();
				if(rs.next()) {
				pstmt=con.prepareStatement("delete from employee where empid=?",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				pstmt.setString(1, s);
				pstmt.executeUpdate();
				
				pw.println("Removed employee details successfully....");
				}else {
					pw.println("<h1>Invalid empID......<h1>");
				}
			 
				pw.println("<a href=homepage.html>Back</a>");
			pw.println("</center></body></html>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
