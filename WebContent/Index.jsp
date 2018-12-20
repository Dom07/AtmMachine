<!DOCTYPE html>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Welcome</title>
<style>
.submit{
  background-color: #4CAF50;
  border: none;
  color: white;
  padding: 10px 15px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 20px;
  margin: 1px 1px;
  cursor: pointer;
}
input[type=number], select {
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

</style>
</head>
<body bgcolor="#89cff0" style="font-family: Arial, Helvetica, sans-serif; font-size:20px;">
	<%
		if(null != session.getAttribute("userID")){
		out.print("<p align='right'>Logged in as "+session.getAttribute("uname")+"</p>"); 
		}
	%>
	<% if(null == session.getAttribute("userID")){ 
		getServletContext().removeAttribute("status");
		out.print("<br><br>");
	%>
	<div style="width:150px; margin: 0 auto; border: 2px solid; border-radius: 5px; padding:10px; background-color:lightblue;">
		<form action="LoginController" method = "post">
			Enter UserID:<br><input type="number" name="userID"><br>
			<input type="submit" class="submit" style="width:100%" value="Login">
		</form>
	</div>
	<%	} %>
	
	<% if(null != session.getAttribute("userID")){ %>
	<div style="width:480px; margin: 0 auto; border: 2px solid; border-radius: 5px; padding:10px;">
		<form action="MakeTransaction" method ="post">
			Enter Amount:<br><input type="number" name="amount"><br>
			<input type="submit" name="action" class="submit" value="Withdraw">
			<input type="submit" name="action" class="submit" value="Deposit">
			<input type="submit" name="action" class="submit"value="View Balance">
			<input type="submit" name="action" class="submit" value="Logout">
		</form>
	</div>
	<br>
	<% } %>
	
	<% if((Integer)getServletContext().getAttribute("status") == null) {%>
	<% } else if((Integer)getServletContext().getAttribute("status") == 0){ %>
		<div style="width:480px; margin: 0 auto; border: 2px solid; border-radius: 5px; padding:10px;">
		Old Balance:<%=getServletContext().getAttribute("oldBal")%><br>
		Deposit Amount:<%=getServletContext().getAttribute("depAmount")%><br>
		Total Balance:<%=getServletContext().getAttribute("totBal") %>
		</div>
	<% } else if((Integer)getServletContext().getAttribute("status") == 1){%>
		<div style="width:480px; margin: 0 auto; border: 2px solid; border-radius: 5px; padding:10px;">
		Insufficient Balance! Total Balance: <%=getServletContext().getAttribute("totBal")%>
		</div>
	<% } else if((Integer)getServletContext().getAttribute("status") == 2){ %>
		<div style="width:480px; margin: 0 auto; border: 2px solid; border-radius: 5px; padding:10px;">
		Old Balance:<%=getServletContext().getAttribute("oldBal")%><br>
		Withdrawn Amount:<%=getServletContext().getAttribute("withDrawnAmount") %><br>
		Total Balance:<%=getServletContext().getAttribute("totBal")%>
		</div>
	<% } else if((Integer)getServletContext().getAttribute("status") == 3){%>
		<div style="width:480px; margin: 0 auto; border: 2px solid; border-radius: 5px; padding:10px;">
		Total Balance: <%=getServletContext().getAttribute("totBal")%>
		</div>
	<% } else if((Integer)getServletContext().getAttribute("status") == 4){%>
		<div style="width:480px; margin: 0 auto; border: 2px solid; border-radius: 5px; padding:10px;">
		Amount field cannot be 0, negative or empty. Please try again.
		</div>
	<% } %>
	
</body>
</html>