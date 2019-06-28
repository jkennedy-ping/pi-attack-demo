<%@ page import="java.util.List" %>

<%
String server = (String)request.getAttribute("server");
String port = (String)request.getAttribute("port");
String url = (String)request.getAttribute("url");
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
body {
  background-image: url('bgStokenToken.png');
}
</style>

<title>Stolen Token Attack</title>
</head>
<body>
  <div id="main-wrapper" class="container bg-light">
    <p class="nav"><a href="DataExfiltration">Data Exfiltration</a> - <a href="DecoyApi">Decoy API</a></p>
    <h1>Stolen Token Attack</h1>

    <div id="attack-results-wrapper" style="display:none">
      <p><strong>Attack Results:</strong></p>
      <ul id="attack-results"></ul>
    </div>
    
    <form id="attack-form" method="POST" action="StolenToken">
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
          <label for="url">URL</label> <input type="text" class="form-control" id="url" name="url" placeholder="" value="<%= url %>">
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

      <div class="col-sm-3 text-left">
        <div class="form-group">
          <label for="iterations">Iterations</label> <input type="text"
            class="form-control" name="iterations" id="iterations" value="10">
        </div>
      </div>

      <div class="col-sm-3 text-left">
        <div class="form-group">
          <label for="delay">Delay</label> <input type="text"
            class="form-control" name="delay" id="delay" value="0">
        </div>
      </div>

      <div class="col-sm-4 text-right">
        <div class="form-group" style="padding-top: 26px">
          <a class="btn btn-primary" data-toggle="collapse"
            href="#attack-data" role="button" aria-expanded="false"
            aria-controls="attack-data">View Attack Data</a>
        </div>
      </div>
      <div class="col-sm text-right">
        <div class="form-group" style="padding-top: 26px">
          <button id="attack-btn" type="button" class="btn btn-danger">Attack!</button>
        </div>
      </div>
    </div>
    <div class="row collapse" id="attack-data">
      <div class="col-sm-3">
        IP Addresses
        <div class="form-group">
          <select id="data-addresses" name="dataAddresses" class="form-control"  size="8" multiple="multiple">
            <option>8.8.8.9</option>
            <option>10.4.65.21</option>
            <option>127.0.0.2</option>
            <option>132.44.224.69</option>
            <option>216.0.0.1</option>
            <option>10.0.1.22</option>
            <option>10.22.55.34</option>
            <option>225.66.227.156</option>
          </select>
        </div>
      </div>
      <div class="col-sm-5">
        User Agents
        <div class="form-group">
          <select id="data-agents" name="dataAgents" class="form-control"  size="8" multiple="multiple">
            <option>Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.7.8) Gecko/20050513 Galeon/1.3.21</option>
            <option>Mozilla/5.0 (Windows; U; Windows NT 5.2; fr-FR; rv:1.7.8) Gecko/20050511 Firefox/1.0.4</option>
            <option>Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36</option>
            <option>Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36</option>
            <option>Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36</option>
            <option>Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36</option>
            <option>Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/602.4.8 (KHTML, like Gecko) Version/10.0.3 Safari/602.4.8</option>
            <option>Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:50.0) Gecko/20100101 Firefox/50.0</option>
          </select>
        </div>
      </div>
    </div>
  </form>
  </div>
  
  <!-- jQuery first, then Bootstrap JS -->
  <script src="js/jquery-3.4.1.min.js"></script>
  <script src="js/bootstrap.min.js"></script>
  <script src="js/jquery.color-2.1.2.min.js"></script>
  <script src="js/functions.js"></script>  

  <script type="text/javascript">
    $(document).ready(
          function() {

            $("#attack-btn").click(function() {
                  var server = $('#server').val();
                  var url = $('#url').val();
                  var token = $('#token').val();
                  var iterations = $('#iterations').val();

                  if (!server || !url || !token || !iterations) {
                    alert("Server, URL, Token, and Iterations fields are required")
                  } else {
                	  startAttack('StolenToken');
                  }
	          }); //function click end
                
          // select all options in select lists
            $('#attack-form select option').prop('selected', true);
    }); // document.ready end
  </script>
</body>
</html>
