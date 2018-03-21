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

<div>

	<!-- contenido informacion adicional -->
	<ul>
		<li><b><spring:message code="animal.picture" />:</b> <img
			src="${animal.stringImage}"
			alt="<spring:message code='animal.no.picture' />" width="200px"
			height="200px"></li>
		<li><b><spring:message code="animal.name" />:</b> <jstl:out
				value="${animal.name}" /></li>
		<li><b><spring:message code="animal.chipNumber" />:</b> <jstl:out
				value="${animal.chipNumber}" /></li>
		<li><b><spring:message code="animal.age" />:</b> <jstl:out
				value="${animal.age}" /></li>
		<li><b><spring:message code="animal.sex" />:</b> <jstl:out
				value="${animal.sex}" /></li>
	</ul>
	
	<acme:link href="animal/list.do" code="animal.goBack"/>

</div>

