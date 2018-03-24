 <%--
 * login.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="j_spring_security_check" modelAttribute="credentials">


<div class="row">
	<div class="offset-md-3 col-md-6 offset-md-3">
	<br>
	<jstl:if test="${showError == true}">
		<h6 class="alert alert-dimissible alert-warning"> 
			<button type="button" class="close" data-dismiss="alert">
    			<span >&times;</span>
  			</button>
 			<strong>Error:</strong> <spring:message code="security.login.failed" />
		</h6>
	</jstl:if>
	
	</div>
	<div class="offset-md-3 col-md-6 offset-md-3 ">
		
		<acme:textbox code="security.username" path="username" mandatory="true"/>
		<acme:password code="security.password" path="password" mandatory="true"/>
	
		<acme:submit name=" " code="security.login"/>
	
	</div>
</div>

</form:form>