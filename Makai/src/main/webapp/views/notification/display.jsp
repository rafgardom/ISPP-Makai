<%--
 * action-1.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="row my-1">
	<div class="offset-md-4 col-md-4">
		<div class="card shadow">
		  	<div class="card-body"> 	
			<p><b><spring:message code="notification.moment" />:</b> 
			<fmt:formatDate value="${notification.moment}" pattern="dd/MM/yyyy HH:mm:ss" />
	
			<p><b><spring:message code="notification.reason" />:</b> <jstl:out
					value="${notification.reason}" /></p>
			</div>
		</div>
	</div>
	<div class="offset-md-3 col-md-6 my-3 text-center">
		<div class="card shadow">
		  	<div class="card-body"> 	
			<p><b><spring:message code="notification.description" />:</b> <jstl:out
					value="${notification.description}" /></p>
			</div>
		</div>
	</div>
	<div class="col-12 d-flex justify-content-center">
		<acme:link href="notification/actor/list.do" code="notification.goBack" image="arrow_left"/>
	</div>
</div>

