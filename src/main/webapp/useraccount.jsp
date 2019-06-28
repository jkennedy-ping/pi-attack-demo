<%@ page import="java.util.List, com.pingidentity.pingintelligence.demo.beans.Transaction" %>

<%
String username = (String)request.getAttribute("username");
List<Transaction> transactions = (List<Transaction>)request.getAttribute("transactions");
%>
<!doctype html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
  content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/styles.css">
<style>
body {
  background-image: url('bgAccount.jpg');
}
</style>

<title>My Account</title>
</head>
<body>
  <div id="main-wrapper" class="container bg-light">
  
    <h1>Welcome <%= username %></h1>

    <p><b>Recent Transactions</b></p>
    <div id="transactions" >
      <div class="row">
        <div class="col-sm-1 text-left"><i>ID</i></div>
        <div class="col-sm-3 text-left"><i>Date</i></div>
        <div class="col-sm-6 text-left"><i>Payee</i></div>
        <div class="col-sm-2 text-left"><i>Amount</i></div>
      </div>
      <%
      for(Transaction transaction : transactions) { 
      %>
      <div class="row">
        <div class="col-sm-1 text-left"><%= transaction.getId() %></div>
        <div class="col-sm-3 text-left"><%= transaction.getDate() %></div>
        <div class="col-sm-6 text-left"><%= transaction.getPayee() %></div>
        <div class="col-sm-2 text-left"><%= transaction.getAmount() %></div>
      </div>
      <%
      } 
      %>
  </div>    
  
    <div class="row">
      <div class="col-sm text-left">
        <div class="form-group">
          <label for="token"><a href="StolenToken" style="font-weight: bold; text-decoration:none; color: black;">Token</a></label>
          <textarea class="form-control" id="token" name="token" rows="3"><%= request.getAttribute("token") %></textarea>
        </div>
      </div>
    </div>

  </div>
  
  <!-- jQuery first, then Bootstrap JS -->
  <script src="js/jquery-3.4.1.min.js"></script>
  <script src="js/bootstrap.min.js"></script>
  
</body>
</html>
