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

<div>
	<ul>
		<li>
			<b><spring:message code="request.description" />:</b>
			<jstl:out value="${request.description}"  />
		</li>
		<li>
			<b><spring:message code="request.tags" />:</b>
			<jstl:out value="${request.tags}"  />
		</li>
		<li>
			<b><spring:message code="request.category" />:</b>
			<jstl:out value="${request.category}"  />
		</li>
	</ul>
	<jstl:if test="${request.animal != null }">
		<fieldset>
			<legend>
				<b><spring:message code="request.animal" />:</b>
			</legend>
			<ul>
				<li>
					<b><spring:message code="request.animal.name" />:</b>
					<jstl:out value="${request.animal.name}" />
				</li>
				
				<li>
					<b><spring:message code="request.animal.chipNumber" />:</b>
					<jstl:out value="${request.animal.chipNumber}" />
				</li>
				
				<li>
					<b><spring:message code="request.animal.age" />:</b>
					<jstl:out value="${request.animal.age}" />
				</li>
				
				<li>
					<b><spring:message code="request.animal.sex" />:</b>
					<jstl:out value="${request.animal.sex}" />
				</li>
				
				<li>
					<b><spring:message code="request.animal.picture" />:</b>
					<img src="${request.animal.picture}" alt="<spring:message code='request.no.picture' />" width="200px" height="200px" >
				</li>
			
			</ul>
		</fieldset>
	</jstl:if>
	
	
	<ul>
		<li>
			<b><spring:message code="request.customer" />:</b>
			<jstl:out value="${request.customer.name}" />
		</li>	
	</ul>
	
</div>
