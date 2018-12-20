

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html");
    	PrintWriter out = response.getWriter();
    	String userID = request.getParameter("userID");
    	Statement stmt = null;
    	try{
    		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
    		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FCUofNJIT","root","root");
    		stmt = conn.createStatement();
    		String query = "select * from accountinfo where UserId='"+userID+"'";
    		ResultSet rs = stmt.executeQuery(query);
    		if(rs.next()){
    			HttpSession session = request.getSession();
    			session.setAttribute("userID", userID);
    			session.setAttribute("uname", rs.getString(2));
    			System.out.println("Logged In");
    			request.getRequestDispatcher("Index.jsp").include(request, response);
    		}else{
        		out.println("<p align='center'>Sorry, invalid UserID. Please try again.</p>");
          		System.out.println("Error");
          		request.getRequestDispatcher("Index.jsp").include(request, response);
    		}
    	}catch (Exception e) {
			System.out.println(e);
		}
		out.close();
	}

}
