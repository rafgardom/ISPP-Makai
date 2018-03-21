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
			<b><spring:message code="offer.startMoment" />:</b>
			<jstl:out value="${offer.startMoment}"  />
		</li>
	</ul>
	
	
	<fieldset>
		<legend>
			<b><spring:message code="offer.destination" />:</b>
		</legend>
		
		<ul>
			<li>
			<b><spring:message code="offer.coordinates.country" />:</b>
			<jstl:out value="${offer.destination.country}" />
		</li>
		
		<li>
			<b><spring:message code="offer.coordinates.state" />:</b>
			<jstl:out value="${offer.destination.state}" />
		</li>
		
		<li>
			<b><spring:message code="offer.coordinates.province" />:</b>
			<jstl:out value="${offer.destination.province}" />
		</li>
		
		<li>
			<b><spring:message code="offer.coordinates.city" />:</b>
			<jstl:out value="${offer.destination.city}" />
		</li>
		
		<li>
			<b><spring:message code="offer.coordinates.zipCode" />:</b>
			<jstl:out value="${offer.destination.zip_code}" />
		</li>
				
		</ul>
		
	</fieldset>
	
	<fieldset>
		<legend>
			<b><spring:message code="offer.duration" />:</b>
		</legend>
		<ul>
			<li>
				<b><spring:message code="offer.duration.year" />:</b>
				<jstl:out value="${offer.duration.year}" />
			</li>
			
			<li>
				<b><spring:message code="offer.duration.month" />:</b>
				<jstl:out value="${offer.duration.month}" />
			</li>
			
			<li>
				<b><spring:message code="offer.duration.week" />:</b>
				<jstl:out value="${offer.duration.week}" />
			</li>
			
			<li>
				<b><spring:message code="offer.duration.day" />:</b>
				<jstl:out value="${offer.duration.day}" />
			</li>
		
		</ul>
	</fieldset>
	
	<fieldset>
		<legend>
			<b><spring:message code="offer.animal" />:</b>
		</legend>
		<ul>
			<li>
				<b><spring:message code="offer.animal.name" />:</b>
				<jstl:out value="${offer.animal.name}" />
			</li>
			
			<li>
				<b><spring:message code="offer.animal.chipNumber" />:</b>
				<jstl:out value="${offer.animal.chipNumber}" />
			</li>
			
			<li>
				<b><spring:message code="offer.animal.age" />:</b>
				<jstl:out value="${offer.animal.age}" />
			</li>
			
			<li>
				<b><spring:message code="offer.animal.sex" />:</b>
				<jstl:out value="${offer.animal.sex}" />
			</li>
			
			<li>
				<b><spring:message code="offer.animal.picture" />:</b>
				<img src="${offer.animal.picture}" alt="<spring:message code='offer.no.picture' />" width="200px" height="200px" >
			</li>
		
		</ul>
	</fieldset>
	
	
	<ul>
		<li>
			<b><spring:message code="offer.price" />:</b>
			<jstl:out value="${offer.price}" />
		</li>
		
		<li>
			<b><spring:message code="offer.comment" />:</b>
			<jstl:out value="${offer.comment}" />
		</li>
		
		<li>
			<b><spring:message code="offer.request" />:</b>
			<jstl:out value="${offer.request.tags}" />
		</li>
		
	</ul>
	<jstl:if test="${(offer.trainer.id==principal.id) && (offer.isAccepted==false)}">
		<acme:link href="offer/trainer/edit.do?offerId=${offer.id}" code="offer.edit"/>
	</jstl:if>
	
</div>
