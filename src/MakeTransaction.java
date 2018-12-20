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

public class MakeTransaction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MakeTransaction() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Statement stmt = null;
		String amount = request.getParameter("amount");
		HttpSession session = request.getSession();
		String userID = (String) session.getAttribute("userID");
		String btn = request.getParameter("action");
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		// can optimize code here
		try{
			
		}catch (Exception e) {
			System.out.println(e);
		}
		
		if(btn.equals("Logout")){
			session.invalidate();
			System.out.println("Logged out");
			getServletContext().removeAttribute("status");
			out.print("<p align='right'>Successfully Logged out</p>");
			request.getRequestDispatcher("Index.jsp").include(request, response);
		} else if(btn.equals("Deposit")){
			try{
				if(amount.equals("0") || amount.equals("") || Integer.parseInt(amount) < 0 ){
					getServletContext().setAttribute("status", 4);
				}else{
					// Connection
					Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FCUofNJIT","root","root");
					stmt = conn.createStatement();
					String query = "select Balance from accountinfo where UserId='"+userID+"'";
					ResultSet rs = stmt.executeQuery(query);
					rs.next();
	    		
					// Updating Variables
					String AvailableBalance = rs.getString(1);
					float updatedAmount = Float.valueOf(AvailableBalance)+Float.valueOf(amount);
					System.out.println("New Amount: "+amount+", TotalBalance: "+AvailableBalance+", UpdatedBalance: "+updatedAmount);
	    		
					// Response
					query = "update accountinfo set Balance="+updatedAmount+" where UserId="+userID;
					stmt.executeUpdate(query);getServletContext().setAttribute("status", 0);
					getServletContext().setAttribute("oldBal", AvailableBalance);
					getServletContext().setAttribute("depAmount", amount);
					getServletContext().setAttribute("totBal", updatedAmount);
				}
				request.getRequestDispatcher("Index.jsp").include(request, response);
			}catch (Exception e) {
				System.out.println(e);
			}
		} else if(btn.equals("Withdraw")){
			try{
				if(amount.equals("0") || amount.equals("") || Integer.parseInt(amount) < 0){
					getServletContext().setAttribute("status", 4);
				}else{
					// Connection
					Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FCUofNJIT","root","root");
					stmt = conn.createStatement();
					String query = "select Balance from accountinfo where UserId='"+userID+"'";
					ResultSet rs = stmt.executeQuery(query);
					rs.next();

					// Updating Variables
					String AvailableBalance = rs.getString(1);
					if(Float.valueOf(amount) > Float.valueOf(AvailableBalance)){
						getServletContext().setAttribute("status", 1);
						getServletContext().setAttribute("totBal", AvailableBalance);
					}else{
						float updatedAmount = Float.valueOf(AvailableBalance)-Float.valueOf(amount);
						System.out.println("New Amount: "+amount+", TotalBalance: "+AvailableBalance+", UpdatedBalance: "+updatedAmount);
						// Response
						query = "update accountinfo set Balance="+updatedAmount+" where UserId="+userID;
						stmt.executeUpdate(query);
						getServletContext().setAttribute("status", 2);
						getServletContext().setAttribute("oldBal", AvailableBalance);
						getServletContext().setAttribute("withDrawnAmount", amount);
						getServletContext().setAttribute("totBal", updatedAmount);
					}
				}
	    		request.getRequestDispatcher("Index.jsp").include(request, response);
			}catch (Exception e) {
				System.out.println(e);
			}
		} else if(btn.equals("View Balance")){
			try{
				// Connection
				Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	    		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FCUofNJIT","root","root");
	    		stmt = conn.createStatement();
	    		String query = "select Balance from accountinfo where UserId='"+userID+"'";
	    		ResultSet rs = stmt.executeQuery(query);
	    		rs.next();
	    		
	    		// Updating Variables
	    		String AvailableBalance = rs.getString(1);
	    		getServletContext().setAttribute("status", 3);
	    		getServletContext().setAttribute("totBal", AvailableBalance);
	    		request.getRequestDispatcher("Index.jsp").include(request, response);
			}catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}
