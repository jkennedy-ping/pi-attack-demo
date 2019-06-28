<!doctype html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
  content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="css/bootstrap.min.css">
<style>
@media ( min-width : 1200px) {
  .container {
    max-width: 440px;
  }
  .list-group-item {
    padding: 5px;
  }
}

body {
  margin: 0;
  background-image: url('bgLogin.jpg');
  background-size: 1440px 800px;
  background-repeat: no-repeat;
  display: compact;
  font: 13px/18px "Helvetica Neue", Helvetica, Arial, sans-serif;
}
</style>

<title>Login</title>
</head>
<body>
  <div class="container bg-light" style="margin-top: 50px">
    <h1>Login</h1>
    <%
    String status = request.getParameter("status");
    if("fail".equals(status)) {
    %>
    <p style="color:red"><b>User not found</b></p>
    <% } %>
    <form id="loginForm" method="POST" action="UserAccount">
    <div class="row">
      <div class="col-sm text-left">
        <div class="form-group">
          <label for="username">Username</label> <input type="text" class="form-control" id="username" name="username">
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm text-left">
        <div class="form-group">
          <label for="password">Password</label> <input type="password" class="form-control" id="password" name="password">
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-sm text-right">
        <div class="form-group" style="padding-top: 26px">
          <button id="loginBtn" type="button" class="btn btn-primary">Sign On</button>
        </div>
      </div>
    </div>
  </form>
  </div>
  
  <!-- jQuery first, then Bootstrap JS -->
  <script src="js/jquery-3.4.1.min.js"></script>
  <script src="js/bootstrap.min.js"></script>

  <script type="text/javascript">
    $(document).ready(function() {
        $("#loginBtn").click(function() {
              var username = $('#username').val();
              if (!username) {
                alert("Username is required")
              } else {
                $("#loginForm").submit();
              }
        }); //function click end
        
        $('.form-control').keypress(function (e) {
        	  if (e.which == 13) {
        	    $('#loginForm').submit();
        	    return false;
        	  }
       	});        
    }); // document.ready end
  </script>
</body>
</html>
