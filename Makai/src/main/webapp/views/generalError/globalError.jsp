<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
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

<div class="card">
	<div class="center-div pt-5">
		<img src="images/warning.png"/>
	</div>
	<h1 class="text-center pb-5">ERROR</h1>
	<h3 class="text-center pb-5"><spring:message code="globalError.message" /> </h3>
	
	<jstl:if test="${errorOffer != null}">
			<br />
			<h5><spring:message code="${errorOffer}" /></h5>
	</jstl:if>
</div>

