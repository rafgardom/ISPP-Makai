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
		
		<acme:input code="training.price" path="price" type="number" min="0" step="1.0" image="euro"/>
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
