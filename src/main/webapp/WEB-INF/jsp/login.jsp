<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/common/include.jsp"%>
<title>Rideshare-Sign In</title>
<%
	// the following code is used to control the cache
	response.addHeader("Cache-Control", "no-store"); // HTTP 1.1
	response.addHeader("Cache-Control", "no-cache");
	response.addHeader("Cache-Control", "private");
	response.addHeader("Pragma", "no-cache"); // HTTP 1.0
	response.addDateHeader("Expires", 0); // prevents caching at the proxy
%>

<!-- Force IE9 to render in normla mode -->
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<!-- HTTP 1.1 -->
<meta http-equiv="Cache-Control" content="no-store" />
<!-- HTTP 1.0 -->
<meta http-equiv="Pragma" content="no-cache" />
<!-- Prevents caching at the Proxy Server -->
<meta http-equiv="Expires" content="0" />

  <!-- Bootstrap core CSS -->
  <link href="${ctx}/static/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>

  <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
  <link href="${ctx}/static/css/ie10-viewport-bug-workaround.css" rel="stylesheet" type="text/css"/>

  <!-- Custom styles for this template -->
  <link href="${ctx}/static/css/signin.css" rel="stylesheet" type="text/css"/>

  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>

<body>

    <div class="container">

      <form class="form-signin" method="post" action="login.do">
        <h2 class="form-signin-heading">Please sign in</h2>
        <label for="inputEmail" class="sr-only">Login Name</label>
        <input type="text" name="loginName" id="loginName" class="form-control" placeholder="Login Name" required="" autofocus="">
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" name="password" id="password" class="form-control" placeholder="Password" required="">
        <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me"> Remember me
          </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </form>

    </div> <!-- /container -->


    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="${ctx}/static/js/ie10-viewport-bug-workaround.js"></script>
  

</body></html>