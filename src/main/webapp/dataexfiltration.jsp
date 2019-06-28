<%@ page import="java.util.List" %>

<%
String server = (String)request.getAttribute("server");
String clientIp = (String)request.getAttribute("clientIp");
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
body {  background-image: url('bgStokenToken.png'); }
</style>

<title>Data Exfiltration Attack</title>
</head>
<body>
  <div id="main-wrapper" class="container bg-light">
    <p class="nav"><a href="StolenToken">Stolen Token</a> - <a href="DecoyApi">Decoy API</a></p>
    <h1>Data Exfiltration Attack</h1>
    
    <div id="attack-results-wrapper" style="display:none">
      <p><strong>Attack Results:</strong></p>
      <ul id="attack-results"></ul>
    </div>
    
    <form id="attack-form" method="POST" action="DataExfiltration">
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
      <div class="col-sm-4">
        Accounts
        <div class="form-group">
          <select id="data-users" name="dataUsers" class="form-control"  size="8" multiple="multiple">
            <option>asmith@anycompany.org</option>
            <option>bsmith@anycompany.org</option>
            <option>csmith@anycompany.org</option>
            <option>dsmith@anycompany.org</option>
            <option>esmith@anycompany.org</option>
            <option>fsmith@anycompany.org</option>
            <option>gsmith@anycompany.org</option>
            <option>hsmith@anycompany.org</option>
            <option>ismith@anycompany.org</option>
            <option>jsmith@anycompany.org</option>
            <option>ksmith@anycompany.org</option>
            <option>lsmith@anycompany.org</option>
            <option>msmith@anycompany.org</option>
            <option>nsmith@anycompany.org</option>
            <option>osmith@anycompany.org</option>
            <option>psmith@anycompany.org</option>
            <option>qsmith@anycompany.org</option>
            <option>rsmith@anycompany.org</option>
            <option>ssmith@anycompany.org</option>
            <option>tsmith@anycompany.org</option>
            <option>usmith@anycompany.org</option>
            <option>vsmith@anycompany.org</option>
            <option>wsmith@anycompany.org</option>
            <option>xsmith@anycompany.org</option>
            <option>ysmith@anycompany.org</option>
            <option>zsmith@anycompany.org</option>
          </select>
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
                  var url = $('#url').val();
                  var token = $('#token').val();
                  var ip  = $('#clientIp').val();
                  var iterations = $('#iterations').val();

                  if (!server || !url || !token || !iterations || !ip) {
                    alert("Server, URL, Token, IP, and Iterations fields are required")
                  } else {
                      $.post("DataExfiltration", $("#attack-form").serialize()).done(function(data) {

                      	// clear any results from a previous attack
      					$("#attack-results li").remove();
                      	
      					$("#attack-results-wrapper").show();
      					var attackResults = $("#attack-results");
      					
                          var poll = function() {
                            $.ajax({
                              url: 'DataExfiltration',
                              dataType: 'json',
                              type: 'get',
                              success: function(data) {
                              	console.log(data);
                                if (data == "complete") { 
                                  clearInterval(pollInterval); 
                                } else {
                                    $.each(data, function(index, data) { // Iterate over the JSON array.
                                        $("<li>").text(data).appendTo(attackResults); 
                                    });
                                }
                              },
                              error: function() { 
                                console.log('Error!');
                              }
                            })
                          };
                          
                          var delay = $("#delay").val();
                          delay = (delay == "0") ? 2000 : delay * 1000;
                          var pollInterval = setInterval(function() {
                            poll();
                          }, delay);
                      }); // $.post
                    }
  	          }); //function click end
                   
                
          // select all options in select lists
            $('#attack-form select option').prop('selected', true);
    }); // document.ready end
  </script>
</body>
</html>
