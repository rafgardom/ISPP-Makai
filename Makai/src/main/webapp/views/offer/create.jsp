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
			<acme:input id="datepicker" image="calendar" code="offer.startMoment" path="startMoment" mandatory="true" placeholder="dd/MM/yyyy"/>
			<acme:input image="euro" code="offer.price" path="price" min="0"/>
			<acme:textarea code="offer.comment" path="comment" mandatory="true" rows="9" maxCharacters="2000" />
		</div>
		<div class="col-md-11">
			<h3><spring:message code="offer.duration" /></h3>
		  	<acme:input path="duration.day" type="range" code="offer.duration.day" min="0" max="31" style="slider" id="myRange1"  /> 
  			<h6 class="text-center" id="total1"></h6>
  		
  		</div>
  		<div class="col-md-6">
  			<acme:input path="duration.month" type="range" code="offer.duration.month" min="0" max="12" style="slider" id="myRange2" /> 
  			<h6 class="text-center" id="total2"></h6>

  		</div>
  		<div class="col-md-5">
  		 	<acme:input path="duration.year" type="range" code="offer.duration.year" min="0" max="5" style="slider" id="myRange3"/> 
  			<h6 class="text-center" id="total3"></h6>
		</div>
		<form:errors cssClass="alert alert-danger form-control" path="duration" />
		<br>
	</div>
	
	<br>
	<div class="row">
		
		<div class="col-md-5">
			<jstl:if test="${offerForm.request.animal==null}">
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

 <!-- Datepicker -->
<script>
$( function() {
    $( "#datepicker" ).datepicker({ 
    	dateFormat: 'dd/mm/yy', 
    	minDate: 0,
    	firstDay: 1
    	});
  } );
</script>

<!-- Slider Duration -->
<script type="text/javascript">
	var slider1 = document.getElementById("myRange1");
	var output1 = document.getElementById("total1");
	output1.innerHTML = slider1.value+" <spring:message code="offer.duration.day"/>"; // Display the default slider value

	// Update the current slider value (each time you drag the slider handle)
	slider1.oninput = function() {
	    output1.innerHTML = this.value+" <spring:message code="offer.duration.day"/>";
	};
	var slider2 = document.getElementById("myRange2");
	var output2 = document.getElementById("total2");
	output2.innerHTML = slider2.value+" <spring:message code="offer.duration.month"/>"; // Display the default slider value

	// Update the current slider value (each time you drag the slider handle)
	slider2.oninput = function() {
	    output2.innerHTML = this.value+" <spring:message code="offer.duration.month"/>";
	};
	var slider3 = document.getElementById("myRange3");
	var output3 = document.getElementById("total3");
	output3.innerHTML = slider3.value+" <spring:message code="offer.duration.year"/>"; // Display the default slider value

	// Update the current slider value (each time you drag the slider handle)
	slider3.oninput = function() {
	    output3.innerHTML = this.value+" <spring:message code="offer.duration.year"/>";
	};
</script>

