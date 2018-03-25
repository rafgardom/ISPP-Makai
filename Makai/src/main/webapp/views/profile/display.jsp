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
		<li><b><spring:message code="profile.picture" />:</b> <img
			src="${pictureImage}"
			alt="<spring:message code='profile.no.picture' />" width="200px"
			height="200px"></li>
		<li><b><spring:message code="profile.name" />:</b> <jstl:out
				value="${actor.name}" /></li>

		<security:authorize
			access="hasAnyRole('CUSTOMER', 'ADMIN', 'TRAINER')">
			<li><b><spring:message code="profile.surname" />:</b> <jstl:out
					value="${actor.surname}" /></li>

			<li><b><spring:message code="profile.nid" />:</b> <jstl:out
					value="${actor.nid}" /></li>

		</security:authorize>

		<li><b><spring:message code="profile.phone" />:</b> <jstl:out
				value="${actor.phone}" /></li>
		<li><b><spring:message code="profile.email" />:</b> <jstl:out
				value="${actor.email}" /></li>
	</ul>

	<h3>
		<b><spring:message code="profile.coordinates" />:</b>
	</h3>

	<ul>
		<li><b><spring:message code="coordinates.country" />:</b> <jstl:out
				value="${actor.coordinates.country}" /></li>

		<li><b><spring:message code="coordinates.state" />:</b> <jstl:out
				value="${actor.coordinates.state}" /></li>

		<li><b><spring:message code="coordinates.province" />:</b> <jstl:out
				value="${actor.coordinates.province}" /></li>

		<li><b><spring:message code="coordinates.city" />:</b> <jstl:out
				value="${actor.coordinates.city}" /></li>

		<li><b><spring:message code="coordinates.zipCode" />:</b> <jstl:out
				value="${actor.coordinates.zip_code}" /></li>

	</ul>

	<acme:link href="profile/edit.do" code="profile.edit" type="warning"/>

</div>

