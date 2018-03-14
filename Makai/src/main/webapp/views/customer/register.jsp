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

<form:form action="${RequestURI}" modelAttribute="customerForm">
	<form:hidden path="id"/>
		
	<fieldset>
		<legend>
			<spring:message code="customer.personalDetails" />
		</legend>

		<acme:textbox code="customer.name" path="name" mandatory="true" />
		<br />

		<acme:textbox code="customer.surname" path="surname" mandatory="true" />
		<br />

		<acme:textbox code="customer.phone" path="phone" mandatory="true" />
		<br />

		<acme:textbox code="customer.email" path="email" mandatory="true" />
		<br />

		<acme:textbox code="customer.nid" path="nid" mandatory="true" />
		<br />

	</fieldset>
	<br />
	<fieldset>
		<legend>
			<spring:message code="customer.coordinates" />
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
					<spring:message code="customer.userAccountDetails" />
				</legend>
				<br />
				<acme:textbox code="customer.username" path="userName"
					mandatory="true" />
				<br />
				<acme:password code="customer.password" path="password"
					mandatory="true" />
				<br />
				<acme:password code="customer.repeatPassword" path="repeatPassword"
					mandatory="true" />
				<br>
				<acme:checkbox code="customer.acceptCondition" path="acceptCondition"
					mandatory="true" />
			</fieldset>
	</security:authorize>
	
	<br/>
	
		<acme:submit code="customer.register" name="save" />
		<acme:cancel code="customer.cancel" url="" />
</form:form>