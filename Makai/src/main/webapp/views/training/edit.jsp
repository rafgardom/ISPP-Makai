<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<jstl:set var="lang" value="<%=LocaleContextHolder.getLocale()%>"/>

<form:form  action="${requestURI }" modelAttribute="training">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="trainer" />
<div class="row">
	<div class="col-md-5">
		<br>
		<h3><spring:message code="training.general" /></h3>
		<br>
		<acme:selectNotEntity items="${categories}" lang="${lang}" code="training.category" path="category" />
		
		<acme:input image="euro" code="training.price" path="price" min="0"/>
		<acme:textarea code="training.description" rows="7" path="description"/>
	</div>
	<div class="offset-md-1 col-md-5">
			<br>
			<h3><spring:message code="training.duration" /></h3>
			<br>
		  	<acme:input path="duration.day" type="range" code="training.duration.day" min="0" max="31" style="slider" id="myRange1"  /> 
  			<h6 class="text-center" id="total1"></h6>
  			<acme:input path="duration.month" type="range" code="training.duration.month" min="0" max="12" style="slider" id="myRange2" /> 
  			<h6 class="text-center" id="total2"></h6>
  		 	<acme:input path="duration.year" type="range" code="training.duration.year" min="0" max="5" style="slider" id="myRange3"/> 
  			<h6 class="text-center" id="total3"></h6>
	</div>
</div>
	<br>
	<acme:submit name="save" code="training.save" />
	<acme:cancel url="./training/trainer/list.do" code="training.cancel"/>
</form:form>

<!-- Slider Duration -->
<script type="text/javascript">
	var slider1 = document.getElementById("myRange1");
	var output1 = document.getElementById("total1");
	output1.innerHTML = slider1.value+" <spring:message code="training.duration.day"/>"; // Display the default slider value

	// Update the current slider value (each time you drag the slider handle)
	slider1.oninput = function() {
	    output1.innerHTML = this.value+" <spring:message code="training.duration.day"/>";
	};
	var slider2 = document.getElementById("myRange2");
	var output2 = document.getElementById("total2");
	output2.innerHTML = slider2.value+" <spring:message code="training.duration.month"/>"; // Display the default slider value

	// Update the current slider value (each time you drag the slider handle)
	slider2.oninput = function() {
	    output2.innerHTML = this.value+" <spring:message code="training.duration.month"/>";
	};
	var slider3 = document.getElementById("myRange3");
	var output3 = document.getElementById("total3");
	output3.innerHTML = slider3.value+" <spring:message code="training.duration.year"/>"; // Display the default slider value

	// Update the current slider value (each time you drag the slider handle)
	slider3.oninput = function() {
	    output3.innerHTML = this.value+" <spring:message code="training.duration.year"/>";
	};
</script>