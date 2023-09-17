package employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;
   
    public ViewServlet() {
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
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=stmt.executeQuery("select * from employee");
			
			ResultSetMetaData rm=rs.getMetaData();
			PrintWriter pw=response.getWriter();
           
			pw.println("<html><head><style>table{border:3px solid black width:70% height:70%}</style></head><body bgcolor=cyan text=black>");
			int n=rm.getColumnCount();
			pw.println("<table border=5 align=center height=30% width=90%>");
			for(int i=1;i<=n;i++) {
			pw.print("<th>"+rm.getColumnName(i)+"</th>");
			}	
				while(rs.next()) {
					pw.println("<tr align=center><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getString(4)+"</td><td>"+
				rs.getString(5)+"</td><td>"+rs.getString(6)+"</td><td>"+rs.getString(7)+"</td><td>"+rs.getString(8)+"</td><td>"+rs.getString(9)+"</td></tr>");
			}
			pw.println("</table>");
			//pw.println("<center><h1>Employee Details is not Available</h></center>");
			pw.println("<center><a href=homepage.html>Back</a></center>");
			pw.println("</body></html>");
		} catch (SQLException | IOException e) {
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

}
