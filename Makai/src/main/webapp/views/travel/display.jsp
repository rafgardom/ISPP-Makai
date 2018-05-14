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

<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<jstl:set var="lang" value="<%=LocaleContextHolder.getLocale()%>"/>
	
	<jsp:useBean id="util" class="utilities.Utilities" scope="page" />

<div class="card-deck">
	<div class="card shadow">
	 	<br><h3 class="card-title my-3"><spring:message code="travel.origin" /></h3>
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
				<fmt:formatDate value="${travel.startMoment}" pattern="dd/MM/yyyy hh:mm" />
			</p>
			<p>
				<b><spring:message code="travel.duration" />:</b>
				<jstl:if test="${horas > 0}">
					<jstl:out value="${horas} " />
					<jstl:if test="${horas == 1}">
						<spring:message code="travel.hour" />
					</jstl:if>
					<jstl:if test="${horas > 1}">
						<spring:message code="travel.hours" />
					</jstl:if>
				</jstl:if>
				<jstl:if test="${minutos > 0}">
					<jstl:out value="${minutos} " />
					<jstl:if test="${minutos == 1}">
						<spring:message code="travel.minute" />
					</jstl:if>
					<jstl:if test="${minutos > 1}">
						<spring:message code="travel.minutes" />
					</jstl:if>
				</jstl:if>
				
			</p>
	  	</div>
	</div>
	<div class="card shadow">
	 	<br><h3 class="card-title my-3"><spring:message code="travel.destination" /></h3>
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
<div class="card-deck text-center m-md-5 mt-5">
	 <div class="card shadow">
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
	 <div class="card shadow">
	 	<br><h3 class="card-title"><spring:message code="travel.species" /></h3>
	  		<div class="card-body">
	  		<jstl:forEach var="specie" items="${species}">
	  			<jstl:if test="${lang == 'es'}">
					<p><jstl:out value="${specie.typeSpa}"/></p>
				</jstl:if>
				<jstl:if test="${lang == 'en'}">
					<p><jstl:out value="${specie.typeEng}"/></p>
				</jstl:if>
			</jstl:forEach>
	  		</div>
	  </div>
</div>
<div class="card-deck m-md-5 mt-5 text-center">
	 <div class="card shadow">
	 	<br><h3 class="card-title"><spring:message code="travel.vehicle" /></h3>
	  	<div class="card-body">
	  		  	<jstl:if test="${vehicle.picture != null}">
					<img src="${util.showImage(vehicle.getPicture())} " class="rounded-circle" width="200px" height="200px">
	  			</jstl:if>
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
	 
	 <jstl:if test="${!empty passengers or !empty animals}">
	 <div class="card shadow">
	 	<br><h3 class="card-title"><spring:message code="travel.passengers" /></h3>
	  		<div class="card-body">
	  		<jstl:if test="${!empty passengers}">
	  		<h5><b><spring:message code="travel.persons" />:</b></h5>
	  		
	  		<jstl:forEach var="passenger" items="${passengers}">
	  			<div class="center-div">
			  		<img src="${util.showImage(passenger.getPicture())} " class="rounded-circle" width="200px" height="200px">
		  		</div>
		  		<p>
					<jstl:out value="${passenger.name}"/>
				</p>
				
			</jstl:forEach>
			</jstl:if>
			<jstl:if test="${!empty animals}">
			<h5><b><spring:message code="travel.animals" />:</b></h5>
			<jstl:forEach var="animal" items="${animals}">
				<div class="center-div">
			  		<img src="${util.showImage(animal.getPicture())} " class="rounded-circle" width="200px" height="200px">
		  		</div>
		  		<p>
					<jstl:out value="${animal.name}"/>
				</p>
			
			</jstl:forEach>
			</jstl:if>
	  		</div>
	  	</div>
	  	</jstl:if>
</div>
<div class="center-div">
	<acme:link href="travel/list.do" code="notification.goBack" image="arrow_left"/>
</div>