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
	 	<br><h3 class="card-title"><spring:message code="offer.destination" /></h3>
	  	<div class="card-body">
		  	 <p>
				<b><spring:message code="offer.coordinates.country" />:</b>
				<jstl:out value="${offer.destination.country}" />
			</p>
			
			<p>
				<b><spring:message code="offer.coordinates.state" />:</b>
				<jstl:out value="${offer.destination.state}" />
			</p>
			
			<p>
				<b><spring:message code="offer.coordinates.province" />:</b>
				<jstl:out value="${offer.destination.province}" />
			</p>
			
			<p>
				<b><spring:message code="offer.coordinates.city" />:</b>
				<jstl:out value="${offer.destination.city}" />
			</p>
			
			<p>
				<b><spring:message code="offer.coordinates.zipCode" />:</b>
				<jstl:out value="${offer.destination.zip_code}" />
			</p>
	  	</div>
	</div>
	<div class="card">
	 	<br><h3 class="card-title"><spring:message code="offer.duration" /></h3>
	  	<div class="card-body">
			<p>
				<b><spring:message code="offer.startMoment" />:</b>
				<jstl:out value="${offer.startMoment}"  />
			</p>
			<p>
				<b><spring:message code="offer.duration.year" />:</b>
				<jstl:out value="${offer.duration.year}" />
			</p>
			
			<p>
				<b><spring:message code="offer.duration.month" />:</b>
				<jstl:out value="${offer.duration.month}" />
			</p>
			
			<p>
				<b><spring:message code="offer.duration.day" />:</b>
				<jstl:out value="${offer.duration.day}" />
			</p>
		</div>
	</div>
	</div>
<div class="card-deck my-3">
	<div class="card">
	 	<br><h3 class="card-title"><spring:message code="offer.animal" /></h3>
	  	<div class="card-body">
	  		<p>
				<b><spring:message code="offer.animal.name" />:</b>
				<jstl:out value="${offer.animal.name}" />
			</p>
			
			<p>
				<b><spring:message code="offer.animal.chipNumber" />:</b>
				<jstl:out value="${offer.animal.chipNumber}" />
			</p>
			
			<p>
				<b><spring:message code="offer.animal.age" />:</b>
				<jstl:out value="${offer.animal.age}" />
			</p>
			
			<p>
				<b><spring:message code="offer.animal.sex" />:</b>
				<jstl:out value="${offer.animal.sex}" />
			</p>
			
			<p>
				<b><spring:message code="offer.animal.picture" />:</b>
				<img src="${animalImage}" alt="<spring:message code='offer.no.picture' />" width="200px" height="200px" >
			</p>
	  	
	  	</div>
	</div>
	<div class="card">
	 	<div class="card-body">
		 	<p>
				<b><spring:message code="offer.price" />:</b>
				<jstl:out value="${offer.price}" />
			</p>
			
			<p>
				<b><spring:message code="offer.request" />:</b>
				<jstl:out value="${offer.request.tags}" />
			</p>
			
			<p>
				<b><spring:message code="offer.comment" />:</b>
				<jstl:out value="${offer.comment}" />
			</p>
			

	  	</div>
	</div>
</div>

	<jstl:if test="${(offer.trainer.id==principal.id) && (offer.isAccepted==false)}">
		<acme:link href="offer/trainer/edit.do?offerId=${offer.id}" code="offer.edit" type="warning" image="edit"/>
	</jstl:if>
	

