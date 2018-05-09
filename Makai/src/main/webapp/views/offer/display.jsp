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
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="util" class="utilities.Utilities" scope="page" />

<div class="card shadow text-center mb-3 offset-md-4 col-md-4">
	 	<div class="card-body">
		 	<p>
				<b><spring:message code="offer.price" />:</b>
				<jstl:out value="${offer.price}" />&euro;
			</p>
			
			<p>
				<b><spring:message code="offer.request" />:</b>
				<jstl:out value="${offer.request.tags}" />
			</p>
			<div class="center-div">
				<jstl:if test="${hasMilestones }">
					<acme:link href="milestone/list.do?offerId=${offer.id}" code="milestone.display" image="hito" type="primary mb-3"/>
				</jstl:if>
			</div>
	  	</div>
</div>

<div class="card-deck text-center my-3">
	
	<div class="card shadow">
	 	<br><h3 class="card-title mt-4"><spring:message code="offer.animal" /></h3>
	  	<div class="card-body">
	  	
		  	<div class="center-div">
				<img src="${util.showImage(offer.getAnimal().getPicture())} " class="rounded-circle" width="200px" height="200px">	
			</div>
	  		<p>
				<b><spring:message code="offer.animal.name" />:</b>
				<a href="animal/display.do?animalId=${offer.animal.id}"><jstl:out value="${offer.animal.name}"/></a>
			</p>
			
			<acme:link href="animal/display.do?animalId=${offer.animal.id}" code="view.animal" image="print"/>
	
	  	</div>
	</div>
	<div class="card shadow">
	 	<br><h3 class="card-title mt-4"><spring:message code="offer.trainer" /></h3>
	  	<div class="card-body">
	  	
	  		<div class="center-div">
				<img src="${util.showImage(offer.getTrainer().getPicture())} " class="rounded-circle" width="200px" height="200px">	
			</div>
	  		<p>
				<b><spring:message code="offer.trainer.name" />:</b>
				<a href="offer/customer/listTrainer.do?trainerId=${offer.trainer.id}"><jstl:out value="${offer.trainer.name}"/></a>
			</p>
			<jstl:if test="${offer.isAccepted }">
				<p>
					<b><spring:message code="offer.trainer.phone" />:</b>
					<jstl:out value="${offer.trainer.phone}"/>
				</p>
				<p>
					<b><spring:message code="offer.trainer.email" />:</b>
					<jstl:out value="${offer.trainer.email}"/>
				</p>
			</jstl:if>	
			<acme:link href="profile/displayProfile.do?actorId=${offer.trainer.id}" code="view.profile" image="user-white"/>	
	  	</div>
	</div>
	
</div>

<div class="card-deck mb-3 ">
	<div class="card shadow">
	 	<br><h3 class="card-title mt-4"><spring:message code="offer.destination" /></h3>
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
	<div class="card shadow">
	 	<br><h3 class="card-title mt-4"><spring:message code="offer.duration" /></h3>
	  	<div class="card-body">
			<p>
				<b><spring:message code="offer.startMoment" />:</b>
				<fmt:formatDate value="${offer.startMoment}" pattern="dd/MM/yyyy" />
			</p>
			<jstl:if test="${offer.duration.year !=0}">
				<p>
					<b><spring:message code="offer.duration.year" />:</b>
					<jstl:out value="${offer.duration.year}" />
				</p>
			</jstl:if>
			
			<jstl:if test="${offer.duration.month !=0}">
				<p>
					<b><spring:message code="offer.duration.month" />:</b>
					<jstl:out value="${offer.duration.month}" />
				</p>
			</jstl:if>
			<jstl:if test="${offer.duration.day !=0}">
				<p>
					<b><spring:message code="offer.duration.day" />:</b>
					<jstl:out value="${offer.duration.day}" />
				</p>
			</jstl:if>
		</div>
	</div>
</div>

<div class="card shadow text-center mb-3">
		<br><h3 class="card-title mt-4"><spring:message code="offer.comment" /></h3>
	 	<div class="card-body">	
			<p>
				<jstl:out value="${offer.comment}" />
			</p>
			

	  	</div>
	</div>

<div class="center-div">
	
	<security:authorize access="hasAnyRole('TRAINER')">
		<jstl:if test="${hasMilestones == false}">
		<acme:link href="milestone/create.do?offerId=${offer.id }" code="milestone.create" type="success mx-2"  image="target"/>
	</jstl:if>
	</security:authorize>

	<security:authorize access="hasAnyRole('CUSTOMER')">
		<acme:link href="request/customer/myList.do" code="welcome.go.to.request" image="arrow_left"/>
	</security:authorize>
	<jstl:if test="${(offer.trainer.id==principal.id) && (offer.isAccepted==false)}">
		<acme:link href="offer/trainer/edit.do?offerId=${offer.id}" code="offer.edit" type="warning" image="edit"/>
	</jstl:if>

</div>

