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

@SuppressWarnings("serial")
public class UpdateServlet extends HttpServlet {
       Connection con;
    
    public UpdateServlet() {
        super();
    }
// connection established code
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","lion");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
//connection destroyed code
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
			PreparedStatement pstmt=con.prepareStatement("Select * from employee where empid=?",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			pstmt.setString(1, s);
			ResultSet rs=pstmt.executeQuery();
			PrintWriter pw=response.getWriter();
			pw.println("<html><head><style>#add{height:350px; width:40%; margin:auto; border:3px solid black;}</style></head><body bgcolor=cyan text=black><center>");
			if(rs.next()) {
				pw.println("<div id=add>");
				pw.println("<form method=post action=updated>");
				pw.println("<center><h1>Update Employee Details</h1></center>");
				pw.println("<label for=\"EmpId\">EmpId :&nbsp;&emsp;&emsp;&emsp;&emsp;</label>");   
				pw.println("<input type=\"text\" name=\"empid\" value="+rs.getString(1)+"><br><br>");   
				pw.println("<label for=\"Employee Name\">Employee Name :&nbsp;</label>");    
				pw.println("<input type=\"text\" name=\"ename\" value="+rs.getString(2)+">&nbsp;&nbsp;");    
				pw.println("<label for=\"Father's Name\">Father's Name</label>");   
				pw.println("<input type=\"text\" name=\"fname\" value="+rs.getString(3)+"><br><br>");    
			    pw.println("<label for=Date Of Birth>Date Of Birth :&nbsp;&nbsp;&emsp;</label>");
				pw.println("<input type=date name=\"dob\" value="+rs.getString(4)+">&nbsp;&nbsp;");    
				pw.println("<label for=\"Salary\">Salary :&nbsp;&nbsp;&emsp;&emsp;&nbsp;</label>");    
				pw.println("<input type=\"number\" name=\"salary\" value="+rs.getString(5)+"><br><br>");    
				pw.println("<label for=\"Address\">Address :&emsp;&emsp;&emsp;&nbsp;&nbsp;&nbsp;</label>");    
			    pw.println("<input type=\"text\" name=\"address\" value="+rs.getString(6)+">&nbsp;&nbsp;");
				pw.println("<label for=\"Phone\">Phone :&emsp;&emsp;&nbsp;&nbsp;&nbsp;</label>");
				pw.println("<input type=\"number\" name=\"phone\" value="+rs.getString(7)+"><br><br>");
				pw.println("<label for=\"Email Id\">Email Id :&emsp;&emsp;&emsp;&nbsp;&nbsp; </label>");   
				pw.println("<input type=\"email\" name=\"email\" value="+rs.getString(8)+">&nbsp;&nbsp;");    
				pw.println("<label for=\"Designation\">Designation :&nbsp;&nbsp; </label>");    
			    pw.println("<input type=\"text\" name=\"designation\" value="+rs.getString(9)+"><br><br>");
				pw.println("<center><input type=submit value=update> <input type=reset></form>");    
				pw.println("</div>");
				
			}else {
				pw.println("<h1>EmpID is not valid.....<h1>");
			}
			pw.println("<a href=homepage.html>Back</a>");
			pw.println("</body></html>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
