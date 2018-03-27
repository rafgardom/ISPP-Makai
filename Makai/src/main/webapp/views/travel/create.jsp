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


<jstl:if test="${successMessage != null}">
	<div class="alert alert-success"><spring:message code="${successMessage}" /></div>
</jstl:if>

<form:form action="${RequestURI}" modelAttribute="travelForm" enctype="multipart/form-data">
		<br />
		
		<jstl:if test="${errorMessage != null}">
			<acme:error code="${errorMessage}"/>
		</jstl:if>
		
		<div class="row">
		
			<div class="col-md-5">
				<h3><spring:message code="travel.origin" /></h3>
				<br />
				<acme:textbox code="travel.country" path="countryOrigin" mandatory="true" />
				<acme:textbox code="travel.state" path="stateOrigin" mandatory="false" />
				<acme:textbox code="travel.province" path="provinceOrigin" mandatory="false" />
				<acme:textbox code="travel.city" path="cityOrigin" mandatory="true" />
				<acme:textbox code="travel.zipcode" path="zip_codeOrigin" mandatory="true" />

			</div>
			<div class="offset-md-1 col-md-5">
				<h3><spring:message code="travel.destination" /></h3>
				<br />
				<acme:textbox code="travel.country" path="countryDestination" mandatory="true" />
				<acme:textbox code="travel.state" path="stateDestination" mandatory="false" />
				<acme:textbox code="travel.province" path="provinceDestination" mandatory="false" />
				<acme:textbox code="travel.city" path="cityDestination" mandatory="true" />
				<acme:textbox code="travel.zipcode" path="zip_codeDestination" mandatory="true" />
			
			</div>
	</div>
	<br>
	<h3><spring:message code="travel.details" /></h3>
	<div class="row">

			<div class="col-lg-2 col-sm-6 col-12">
				<acme:textbox code="travel.animalSeats" path="animalSeats" mandatory="false" />
				<acme:textbox code="travel.humanSeats" path="HumanSeats" mandatory="false" />
			</div>
			<div class="col-lg-3 col-sm-6 col-12">
				<acme:input code="travel.startMoment" path="startMoment" mandatory="true" image="calendar" />
				<acme:input code="travel.endMoment" path="endMoment" mandatory="true" image="calendar"/>
			</div>
			<div class="offset-lg-1 col-md-5 col-12">
				<acme:select code="travel.vehicle" path="vehicle" items="${vehicles}" itemLabel="license" />
			</div>
	</div>
	<br/>
	
		<acme:submit code="travel.create" name="save" />
		<acme:cancel code="travel.cancel" url="" />
</form:form>