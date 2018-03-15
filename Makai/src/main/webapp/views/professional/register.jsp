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

<form:form action="${RequestURI}" modelAttribute="professionalForm" enctype="multipart/form-data">
	<form:hidden path="id"/>
		
	<fieldset>
		<legend>
			<spring:message code="professional.personalDetails" />
		</legend>

		<acme:textbox code="professional.name" path="name" mandatory="true" />
		<br />

		<acme:textbox code="professional.phone" path="phone" mandatory="true" />
		<br />

		<acme:textbox code="professional.email" path="email" mandatory="true" />
		<br />

	</fieldset>
	<br />
	<fieldset>
		<legend>
			<spring:message code="professional.coordinates" />
		</legend>

		<acme:textbox code="coordinates.country" path="coordinates.country"
			mandatory="true" />
		<br />

		<acme:textbox code="coordinates.state" path="coordinates.state" />
		<br />

		<acme:textbox code="coordinates.province" path="coordinates.province" />
		<br />

		<acme:textbox code="coordinates.city" path="coordinates.city"
			mandatory="true" />
		<br />
		
		<acme:textbox code="coordinates.zipCode" path="coordinates.zip_code"
			mandatory="true" />
		<br />

	</fieldset>
	<br/>
	
	<security:authorize access="isAnonymous()">
			<br />
			<fieldset>
				<legend>
					<spring:message code="professional.userAccountDetails" />
				</legend>
				<br />
				<acme:textbox code="professional.username" path="userName"
					mandatory="true" />
				<br />
				<acme:password code="professional.password" path="password"
					mandatory="true" />
				<br />
				<acme:password code="professional.repeatPassword" path="repeatPassword"
					mandatory="true" />
				<br/>
				<br/>
					<spring:message code="image.formats" var="formats" />
					<spring:message code="professional.picture" var="picture" />
					<jstl:out value="${picture}"/>
					<form:input type="file" path="userImage" id="userImage" name="userImage" mandatory="true"
					class="form:input-large" enctype="multipart/form-data" code="customer.picture"></form:input>
					<jstl:out value="${formats}"/>
				<br/>
				<br/>
				<acme:checkbox code="professional.acceptCondition" path="acceptCondition"
					mandatory="true" />
			</fieldset>
	</security:authorize>
	<br/>
	
	<jstl:if test="${errorMessage != null}">
		<spring:message code="${errorMessage}" var="error" />
		<font size="4" color="red"><jstl:out value="${error}"></jstl:out></font>
	</jstl:if>
	<br/>
	<br/>
	
		<acme:submit code="professional.register" name="save" />
		<acme:cancel code="professional.cancel" url="" />
	<br/>
	
</form:form>