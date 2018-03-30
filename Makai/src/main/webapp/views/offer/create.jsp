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

<form:form action="${RequestURI}" modelAttribute="offerForm" enctype="multipart/form-data">
		<form:hidden path="id"/>
		<form:hidden path="request"/>
		
		<br>
		<h3><spring:message code="offer.destination" /></h3>
			
	<jstl:if test="${errorMessage != null}">
		<acme:error code="${errorMessage}"/>
	</jstl:if>
			
	<div class="row">
		<div class="col-md-5">
			<acme:textbox code="offer.coordinates.country" path="destination.country" mandatory="true" />
			<acme:textbox code="offer.coordinates.state" path="destination.state" mandatory="false" />
			<acme:textbox code="offer.coordinates.province" path="destination.province" mandatory="false" />
			<acme:textbox code="offer.coordinates.city" path="destination.city" mandatory="true" />
			<acme:textbox code="offer.coordinates.zipCode" path="destination.zip_code" mandatory="true" />
		</div>
		<div class="offset-md-1 col-md-5">
			<acme:input image="calendar" code="offer.startMoment" path="startMoment" mandatory="true" placeholder="dd/MM/yyyy HH:mm"/>
			
  			<acme:input path="price" type="range" code="offer.price" min="0" max="4000" style="slider" id="myRange" mandatory="true" /> 
  			<h6 class="text-center" id="total"></h6>
	
			<acme:textarea code="offer.comment" path="comment" mandatory="true" rows="8"/>
		</div>
	</div>
	<br>
	<h3><spring:message code="offer.duration" /></h3>
	<div class="row">
		<div class="col-sm-2">
			<acme:textbox code="offer.duration.year" path="duration.year" mandatory="false" />
			<acme:textbox code="offer.duration.month" path="duration.month" mandatory="false" />
		</div>
		<div class="col-sm-2">
			<acme:textbox code="offer.duration.week" path="duration.week" mandatory="false" />
			<acme:textbox code="offer.duration.day" path="duration.day" mandatory="false" />
		</div>
		
		<div class="col-12">
			<jstl:if test="${offerForm.request.animal==null}">
				<br>
				<h3><spring:message code="offer.animal" /></h3>
				<acme:select items="${animals}" itemLabel="name" code="offer.animal" path="animal"/>
				<br>
			</jstl:if>
			<jstl:if test="${offerForm.request.animal!=null}">
				<form:hidden path="animal"/>
			</jstl:if>
		</div>
	<br/>
</div>
	<jstl:if test="${offerForm.id==0}">
		<acme:submit code="offer.create" name="save" />
	</jstl:if>
	<jstl:if test="${offerForm.id!=0}">
		<acme:submit code="offer.edit" name="save" />
	</jstl:if>
		<acme:cancel code="offer.cancel" url="" />
	<br/>
	
</form:form>

<!-- Slider Price -->
<script type="text/javascript">
	var slider = document.getElementById("myRange");
	var output = document.getElementById("total");
	output.innerHTML = slider.value+",00 &euro;"; // Display the default slider value

	// Update the current slider value (each time you drag the slider handle)
	slider.oninput = function() {
	    output.innerHTML = this.value+",00 &euro;";
	};
</script>