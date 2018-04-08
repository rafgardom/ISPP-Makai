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
<form:hidden path="id"/>
<form:hidden path="animalSeats"/>
<form:hidden path="humanSeats"/>

<form:hidden path="startDate"/>
<form:hidden path="startTime"/>
<form:hidden path="duration"/>
<form:hidden path="vehicle"/>

<form:hidden path="origin.country"/>
<form:hidden path="origin.state"/>
<form:hidden path="origin.province"/>
<form:hidden path="origin.city"/>
<form:hidden path="origin.zip_code"/>

<form:hidden path="destination.country"/>
<form:hidden path="destination.state"/>
<form:hidden path="destination.province"/>
<form:hidden path="destination.city"/>
<form:hidden path="destination.zip_code"/>

<jstl:if test="${errorMessage != null}">
			<acme:error code="${errorMessage}"/>
</jstl:if>
<div class="card-deck">
	<div class="card">
		 	<br><h3 class="card-title"><spring:message code="travel.seats" /></h3>
		  	<div class="card-body">
		  		<p><b><spring:message code="travel.animalSeats" />:</b>
				<jstl:out value="${travelForm.animalSeats}"  /></p>
				<p><b><spring:message code="travel.humanSeats" />:</b>
				<jstl:out value="${travelForm.humanSeats}"  /></p>
		  	</div>
	</div>
	
	
	<div class="card">
		 	<br><h3 class="card-title"><spring:message code="travel.join" /></h3>
		  	<div class="card-body">
		  		<form:label path="principalPassenger">
					<b><spring:message code="travel.customer"/></b>
				</form:label>	
				<br>
				<form:checkbox path ="principalPassenger" value="${principal}"/><jstl:out value="${principal.name}"/>
				<br>
		  		<form:label path="animals">
					<b><spring:message code="travel.animals"/></b>
				</form:label>
				<br>
				<jstl:forEach var="animal" items="${animals}">
					<form:checkbox path ="animals" value="${animal}"/><jstl:out value="${animal.name}"/>
				</jstl:forEach>
				<br>

		  	</div>
	</div>

</div>

		<br>
		<acme:submit code="travel.save" name="save" />
		<acme:cancel code="travel.cancel" url="travel/list.do" />
</form:form>
