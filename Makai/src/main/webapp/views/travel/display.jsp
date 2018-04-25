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

<security:authorize access="hasAnyRole('PROFESSIONAL','CUSTOMER')">	

<div class="card-deck">
	<div class="card">
	 	<br><h3 class="card-title"><spring:message code="travel.origin" /></h3>
	  	<div class="card-body">
	  			<p>
			<b><spring:message code="travel.country" />:</b>
			<jstl:out value="${travel.origin.country}"  />
			</p>
			<p>
				<b><spring:message code="travel.state" />:</b>
				<jstl:out value="${travel.origin.state}"  />
			</p>
			<p>
				<b><spring:message code="travel.province" />:</b>
				<jstl:out value="${travel.origin.province}"  />
			</p>
			<p>
				<b><spring:message code="travel.city" />:</b>
				<jstl:out value="${travel.origin.city}"  />
			</p>
			<p>
				<b><spring:message code="travel.zipcode" />:</b>
				<jstl:out value="${travel.origin.zip_code}"  />
			</p>
			<p>
				<b><spring:message code="travel.startMoment" />:</b>
				<jstl:out value="${travel.startMoment}"  />
			</p>
			<p>
				<b><spring:message code="travel.duration" />:</b>
				<jstl:out value="${travel.duration}" />
			</p>
	  	</div>
	</div>
	<div class="card">
	 	<br><h3 class="card-title"><spring:message code="travel.destination" /></h3>
	  	<div class="card-body">
	  		<p>
			<b><spring:message code="travel.country" />:</b>
			<jstl:out value="${travel.destination.country}"  />
		</p>
		<p>
			<b><spring:message code="travel.state" />:</b>
			<jstl:out value="${travel.destination.state}"  />
		</p>
		<p>
			<b><spring:message code="travel.province" />:</b>
			<jstl:out value="${travel.destination.province}"  />
		</p>
		<p>
			<b><spring:message code="travel.city" />:</b>
			<jstl:out value="${travel.destination.city}"  />
		</p>
		<p>
			<b><spring:message code="travel.zipcode" />:</b>
			<jstl:out value="${travel.destination.zip_code}"  />
		</p>
	  	</div>
	 </div>

</div>
	 <div class="card my-3">
	 	<br><h3 class="card-title"><spring:message code="travel.seats" /></h3>
	  	<div class="card-body">
		  	<p>
				<b><spring:message code="travel.animalSeats" />:</b>
				<jstl:out value="${travel.animalSeats}"  />
			</p>
			<p>
				<b><spring:message code="travel.humanSeats" />:</b>
				<jstl:out value="${travel.humanSeats}"  />
			</p>
	  	</div>
	 </div>
	 <div class="card my-3">
	 	<br><h3 class="card-title"><spring:message code="travel.vehicle" /></h3>
	  	<div class="card-body">
		  	<p>
		  		<b><spring:message code="travel.vehicle.carType" />:</b>
				<jstl:out value="${vehicle.carType}"  />
			</p>
			<p>
				<b><spring:message code="travel.vehicle.brand" />:</b>
				<jstl:out value="${vehicle.brand}"  />
			</p>
			<p>
				<b><spring:message code="travel.vehicle.year" />:</b>
				<jstl:out value="${vehicle.year}"  />
			</p>
			<p>
				<b><spring:message code="travel.vehicle.color" />:</b>
				<jstl:out value="${vehicle.color}"  />
			</p>
			<p>
				<b><spring:message code="travel.vehicle.description" />:</b>
				<jstl:out value="${vehicle.description}"  />
			</p>
	  	</div>
	 </div>
	 <div class="card my-3">
	 	<br><h3 class="card-title"><spring:message code="travel.passengers" /></h3>
	  		<div class="card-body">
	  		<b><spring:message code="travel.persons" />:</b>
	  		<jstl:if test="${passengers.isEmpty()}"></br></jstl:if>
	  		<jstl:forEach var="passenger" items="${passengers}">
		  		<p>
					<jstl:out value="${passenger.name}"/>
				</p>
			</jstl:forEach>
			<b><spring:message code="travel.animals" />:</b>
			<jstl:forEach var="animal" items="${animals}">
		  		<p>
					<jstl:out value="${animal.name}"/>
				</p>
			</jstl:forEach>
	  		</div>
	  	</div>
	  
</security:authorize>