<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form  action="${requestURI }" modelAttribute="training">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="trainer" />
<div class="row">
	<div class="col-md-5">
		<br>
		<h3><spring:message code="training.general" /></h3>
		<br>
		<acme:selectNotEntity items="${categories}" code="training.category" path="category" />
		
		<acme:input code="training.price" path="price" type="range" min="0" max="4000" style="slider" id="myRange"/>
		<h6 class="text-center" id="total"></h6>
		<acme:textarea code="training.description" rows="5" path="description"/>
	</div>
	<div class="offset-md-1 col-md-5">
		<br>
		<h3><spring:message code="training.duration" /></h3>
		<br>
		<acme:input code="training.duration.year" path="duration.year" type="number" min="0" />
		<acme:input code="training.duration.month" path="duration.month" type="number" min="0" />
		<acme:input code="training.duration.week" path="duration.week" type="number" min="0" />
		<acme:input code="training.duration.day" path="duration.day" type="number" min="0" />	
	</div>
</div>
	<br>
	<acme:submit name="save" code="training.save" />
	<acme:cancel url="./training/trainer/list.do" code="training.cancel"/>
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