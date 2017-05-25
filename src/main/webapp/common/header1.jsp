<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<head>
<%@ include file="/common/include.jsp"%>
<title>Rideshare-Welcome</title>
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

  <link href="${ctx}/static/css/navbar-fixed-top.css" rel="stylesheet" type="text/css"/>
  
  <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
  <link href="${ctx}/static/css/ie10-viewport-bug-workaround.css" rel="stylesheet" type="text/css"/>

  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>

<body>