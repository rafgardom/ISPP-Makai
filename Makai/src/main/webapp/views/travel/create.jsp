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
<form:hidden path="principalPassenger"/>
<form:hidden path="animals"/>
		<br />
		
		<jstl:if test="${errorMessage != null}">
			<acme:error code="${errorMessage}"/>
		</jstl:if>
		
		<div class="row">
		
			<div class="col-md-5">
				<h3><spring:message code="travel.origin" /></h3>
				<br />
				<acme:textbox code="travel.country" path="origin.country" mandatory="true" />
				<acme:textbox code="travel.state" path="origin.state" mandatory="false" />
				<acme:textbox code="travel.province" path="origin.province" mandatory="false" />
				<acme:textbox code="travel.city" path="origin.city" mandatory="true" />
				<acme:textbox code="travel.zipcode" path="origin.zip_code" mandatory="true" />

			</div>
			<div class="offset-md-1 col-md-5">
				<h3><spring:message code="travel.destination" /></h3>
				<br />
				<acme:textbox code="travel.country" path="destination.country" mandatory="true" />
				<acme:textbox code="travel.state" path="destination.state" mandatory="false" />
				<acme:textbox code="travel.province" path="destination.province" mandatory="false" />
				<acme:textbox code="travel.city" path="destination.city" mandatory="true" />
				<acme:textbox code="travel.zipcode" path="destination.zip_code" mandatory="true" />
			
			</div>
	</div>
	<br>
	<h3><spring:message code="travel.details" /></h3>
	<div class="row">

			<div class="col-lg-2 col-sm-6 col-12">
				<acme:input code="travel.animalSeats" path="animalSeats" type="number" min="0" />
				<acme:input code="travel.humanSeats" path="humanSeats" type="number" min="0" />
			</div>
			<div class="col-lg-3 col-sm-6 col-12">
				<acme:select code="travel.vehicle" path="vehicle" items="${vehicles}" itemLabel="license" mandatory="true"/>
			</div>
			<div class="offset-lg-1 col-md-3 col-12">
				<acme:input code="travel.startMoment" path="startDate" mandatory="true" image="calendar" placeholder="dd/MM/yyyy" id="datepicker"/>
				<acme:input code="travel.startTime" path="startTime" mandatory="true" image="clock" placeholder="HH:mm"/>
			</div>
			<div class="col-md-11">
				<h3><spring:message code="travel.duration" /></h3>
			  	<acme:input path="duration" type="range" code="travel.duration" min="0" max="1200" style="slider" id="myRange"  /> 
  				<h6 class="text-center" id="total"></h6>
  			</div>
			<form:errors cssClass="alert alert-danger form-control" path="duration" />
	</div>
	<br/>
	
		<acme:submit code="travel.save" name="save" />
		<acme:cancel code="travel.cancel" url="travel/list.do" />
</form:form>

 <!-- Datepicker -->
<script>
$( function() {
    $( "#datepicker" ).datepicker({ 
    	dateFormat: 'dd/mm/yy', 
    	minDate: 0,
    	firstDay: 1
    	});
  } );
  
var slider1 = document.getElementById("myRange");
var output1 = document.getElementById("total");
output1.innerHTML = slider1.value+"&nbsp;<spring:message code="script.minutes" />"; // Display the default slider value

// Update the current slider value (each time you drag the slider handle)
slider1.oninput = function() {

	if (this.value>=60) {
		out= Math.trunc(this.value/60);
		out2="&nbsp;<spring:message code="script.hours" />&nbsp;";
		if(this.value%60>0){
			out3 = this.value%60;
			out4 = "&nbsp;<spring:message code="script.minutes" />&nbsp;";
			output1.innerHTML = out+out2+out3+out4;
		}else{
			output1.innerHTML = out+out2;
		}
	}else{
		out2="&nbsp;<spring:message code="script.minutes" />";
		output1.innerHTML = this.value+out2;
	}
   
};
</script>