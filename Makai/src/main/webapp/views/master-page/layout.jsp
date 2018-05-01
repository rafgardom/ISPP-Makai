<%--
 * layout.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="shortcut icon" href="favicon.ico"/> 

<meta name="viewport" content="width=device-width, user-scalable=no">
<!-- <meta name="viewport" content="width=device-width, initial-scale=1"> -->

<!-- <script type="text/javascript" src="scripts/jquery.js"></script> -->
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<!-- <script type="text/javascript" src="scripts/common.js"></script> -->
<script type="text/javascript" src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<!-- <script type="text/javascript" src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script> -->
<!-- <script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script>  -->
<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="scripts/datepickerES.js"></script>
<script type="text/javascript"  src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>



<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="styles/slider.css" type="text/css">
<link rel="stylesheet" href="styles/common.css" type="text/css">
<link rel="stylesheet" href="styles/cookiesBar.css" type="text/css">
<link rel="stylesheet" href="styles/minty.css" type="text/css"> 
<!-- <link rel="stylesheet" href="styles/jmenu.css" media="screen" type="text/css" /> -->
<link rel="stylesheet" href="styles/displaytag.css" type="text/css">
<link rel="stylesheet" href="styles/tooltip.css" type="text/css">


<title><tiles:insertAttribute name="title" ignore="true" /></title>

<script type="text/javascript">
	function askSubmission(msg, form) {
		if (confirm(msg))
			form.submit();
	}
</script>

</head>

<body style="background-color:#ECFAFB;">
<!-- 	<div class="container"> -->
	<div>
		<tiles:insertAttribute name="header" />
	</div>


		<br>
		<h1 class="text-center mt-3">
			<tiles:insertAttribute name="title" />
		</h1> 
		<div class="mx-5">
		<tiles:insertAttribute name="body" />	
		</div>
		<jstl:if test="${message != null}">
			<br />
			<span class="message"><spring:message code="${message}" /></span>
		</jstl:if>	
		<br>

		<tiles:insertAttribute name="footer" />

</body>
</html>