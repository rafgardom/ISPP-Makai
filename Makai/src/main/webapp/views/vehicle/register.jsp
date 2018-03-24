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

<form:form action="${RequestURI}" modelAttribute="vehicleForm" enctype="multipart/form-data">
		
	<acme:selectNotEntity code="vehicle.brand" path="brand" items="${brands}" mandatory="true" />
	<acme:textbox code="vehicle.seats" path="seats" mandatory="false" />
	<acme:selectNotEntity code="vehicle.carType" path="carType" items="${carTypes}" mandatory="true" />
	<acme:textbox code="vehicle.accommodation" path="accommodation" mandatory="false" />
	<acme:textbox code="vehicle.year" path="year" mandatory="false" />
	<acme:textbox code="vehicle.description" path="description" mandatory="true" />
	<acme:textbox code="vehicle.color" path="color" mandatory="true" />
	<acme:textbox code="vehicle.license" path="license" mandatory="true" />
	
	<spring:message code="vehicle.picture" /><strong>(*)</strong>
	<form:input class="form-control-file" path="picture" type="file" />
	<spring:message code="image.formats" var="formats" /><jstl:out value="${formats}"/><br>
	<form:errors path="picture" cssClass="error" />
	
	<jstl:if test="${errorMessage != null}">
		<spring:message code="${errorMessage}" var="error" />
		<font size="4" color="red"><jstl:out value="${error}"></jstl:out></font>
	</jstl:if>
		
	<security:authorize access="isAnonymous()">
	</security:authorize>
	
	<jstl:if test="${errorMessage != null}">
		<spring:message code="${errorMessage}" var="error" />
		<font size="4" color="red"><jstl:out value="${error}"></jstl:out></font>
	</jstl:if>
	<br/><br/>
	<acme:submit code="vehicle.register" name="save" />
	<acme:cancel code="vehicle.cancel" url="" />
	
</form:form>