<%--
 * action-1.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
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

<div class="card-deck">
	<div class="card">
 		<div class="card-body">
		<p>
			<b><spring:message code="request.tags" />:</b>
			<jstl:out value="${request.tags}"  />
		</p>
		<p>
			<b><spring:message code="request.category" />:</b>
			<jstl:out value="${request.category}"  />
		</p>
		<p>
			<b><spring:message code="request.description" />:</b>
			<jstl:out value="${request.description}"  />
		</p>
	</div>
	</div>
	<jstl:if test="${request.animal != null }">
	<div class="card">
 		<br><h3 class="card-title"><spring:message code="request.animal" /></h3>
  		<div class="card-body">
			<p>
				<b><spring:message code="request.animal.name" />:</b>
				<a href="animal/display.do?animalId=${request.animal.id}"><jstl:out value="${request.animal.name}"/></a>
			</p>
		
		</div>
	</div>
	</jstl:if>
	<div class="card">
 		<br><h3 class="card-title"><spring:message code="request.customer" /></h3>
  		<div class="card-body">
			<p>
				<b><spring:message code="request.animal.name" />:</b>
				<a href="profile/displayProfile.do?actorId=${request.customer.id}"><jstl:out value="${request.customer.name}"/></a>
			</p>
		
		</div>
	</div>

</div>
