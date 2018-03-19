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
	<fieldset>
		<br />
		<p><b>
			<spring:message code="offer.destination" />
		</b></p>
		<br />
		<acme:textbox code="offer.coordinates.country" path="destination.country" mandatory="true" />
		<br />
		<acme:textbox code="offer.coordinates.state" path="destination.state" mandatory="false" />
		<br />	
		<acme:textbox code="offer.coordinates.province" path="destination.province" mandatory="false" />
		<br />
		<acme:textbox code="offer.coordinates.city" path="destination.city" mandatory="true" />
		<br />
		<acme:textbox code="offer.coordinates.zipCode" path="destination.zip_code" mandatory="true" />
		<br />
		<acme:textbox code="offer.startMoment" path="startMoment" mandatory="true" />
		<br />
		<acme:textbox code="offer.price" path="price" mandatory="true" />
		<br />
		<acme:textbox code="offer.comment" path="comment" mandatory="false" />
		<br />
		<acme:textbox code="offer.duration.year" path="duration.year" mandatory="false" />
		<br />
		<acme:textbox code="offer.duration.month" path="duration.month" mandatory="false" />
		<br />
		<acme:textbox code="offer.duration.week" path="duration.week" mandatory="false" />
		<br />
		<acme:textbox code="offer.duration.day" path="duration.day" mandatory="false" />
		<br />
		
		<jstl:if test="${offer.request.animal==null}">
			<spring:message code="offer.animal" />
				<form:select path="animal">
				<form:option value="${null }" label="----" />    
			<jstl:forEach items="${animal }" var="animal">
				<form:option value="${animal}" label="${animal.name}" />
			</jstl:forEach>
			</form:select>
		<br />
		</jstl:if>
		
		
	</fieldset>
	<br/>
	
	<jstl:if test="${errorMessage != null}">
		<spring:message code="${errorMessage}" var="error" />
		<font size="4" color="red"><jstl:out value="${error}"></jstl:out></font>
	</jstl:if>
	<br/>
	
		<acme:submit code="offer.create" name="save" />
		<acme:cancel code="travel.cancel" url="" />
	<br/>
	
</form:form>