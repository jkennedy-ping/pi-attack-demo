<%@ page import="java.util.List" %>

<%
String server = (String)request.getAttribute("server");
String clientIp = (String)request.getAttribute("clientIp");
String port = (String)request.getAttribute("port");
String endpointDecoy = (String)request.getAttribute("endpointDecoy");
String token = (String)request.getAttribute("token");
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
body {  background-image: url('bgStokenToken.png'); }
</style>

<title>Decoy API</title>
</head>
<body>
  <div id="main-wrapper" class="container bg-light">
    <p class="nav"><a href="StolenToken">Stolen Token</a> - <a href="DataExfiltration">Data Exfiltration</a></p>
    <h1>Decoy API</h1>
    <%
    List<String> results = (List<String>)request.getAttribute("results");
    if(results != null) {
    %>
    <p><strong>Attack Results:</strong></p>
    <ul>
    <% for(String result : results)  {%>
    <li><%= result %></li>
    <% } %>
    </ul>
    <%
    }
    %>
    
    <form id="attack-form" method="POST" action="DecoyApi">
    <div class="row">
      <div class="col-sm-8 text-left">
        <div class="form-group">
          <label for="server">Target Server</label> <input type="text" class="form-control" id="server" name="server" placeholder="127.0.0.1" value="<%= server %>">
        </div>
      </div>
      <div class="col-sm-4 text-left">
        <div class="form-group">
          <label for="port">Target Server Port</label> <input type="text" class="form-control" id="port" name="port" placeholder="443" value="<%= port %>">
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-sm text-left">
        <div class="form-group">
          <label for="clientIp">IP Address</label> <input type="text" class="form-control" id="clientIp" name="clientIp" placeholder="" value="<%= clientIp %>">
        </div>
      </div>    
      <div class="col-sm text-left">
        <div class="form-group">
          <label for="endpointDecoy">URL</label> <input type="text" class="form-control" id="endpointDecoy" name="endpointDecoy" placeholder="" value="<%= endpointDecoy %>">
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-sm text-left">
        <div class="form-group">
          <label for="token">Token</label>
          <textarea class="form-control" id="token" name="token" rows="3"><%= token %></textarea>
        </div>
      </div>
    </div>

    <div class="row">

      <div class="col-sm text-right">
        <div class="form-group" style="padding-top: 26px">
          <button id="attack-btn" type="button" class="btn btn-danger">Attack!</button>
        </div>
      </div>
     </div>
  </form>
  </div>
  
  <!-- jQuery first, then Bootstrap JS -->
  <script src="js/jquery-3.4.1.min.js"></script>
  <script src="js/bootstrap.min.js"></script>

  <script type="text/javascript">
    $(document).ready(
          function() {

            $("#attack-btn").click(function() {
                  var server = $('#server').val();
                  var url = $('#endpointDecoy').val();
                  var token = $('#token').val();
                  var ip  = $('#clientIp').val();

                  if (!server || !url || !token || !ip) {
                    alert("Server, URL, Token, and IP fields are required")
                  } else {
                    $("#attack-form").submit();
                  }
                }); //function click end
                
    }); // document.ready end
  </script>
</body>
</html>
