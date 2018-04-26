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


<div class="offset-md-4 col-md-4">
	
<div style="display: flex; justify-content:center; position:relative; z-index:2;">
		<img src="images/user3.png" alt="Login user"/>
</div>
<div class="card shadow" style="position:relative; z-index:1; margin-top:-2.5rem">
    <div class="card-body" style="margin-top:1.5rem">	
	    
	    <jstl:if test="${showError == true && bannedTrue == false}">
			<acme:error code="security.login.failed"/>
		</jstl:if>
		<jstl:if test="${bannedTrue == true}">
			<acme:error code="security.user.banned"/>
		</jstl:if>
		
		<h3 class="card-title text-muted text-center"><spring:message code="master.page.sign.in"/></h3>
		<acme:input image="user-xs" code="security.username" path="username" mandatory="true"/>
		<acme:password image="lock1" code="security.password" path="password" mandatory="true"/>
		</div>
	    <div class="card-footer"><acme:submit name=" " code="security.login" type="primary" image="login32" style="btn-block"/></div>

	
	</div>

</div>

</form:form>