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

<form:form action="${RequestURI}" modelAttribute="profileForm" enctype="multipart/form-data">
	<fieldset>
		<legend>
			<spring:message code="profile.personalDetails" />
		</legend>

		<acme:textbox code="profile.name" path="name" mandatory="true" />
		<br />
		
		<security:authorize access="hasAnyRole('CUSTOMER', 'ADMIN', 'TRAINER')">
			<acme:textbox code="profile.surname" path="surname" mandatory="true" />
			<br />
			
			<acme:textbox code="profile.nid" path="nid" mandatory="true" />
			<br />
			
		</security:authorize>

		<acme:textbox code="profile.phone" path="phone" mandatory="true" />
		<br />

		<acme:textbox code="profile.email" path="email" mandatory="true" />
		<br />
		
		<form:input path="userImage" type="file" />
		<spring:message code="image.formats" var="formats" /><jstl:out value="${formats}"/>

	</fieldset>
	<br />
	<fieldset>
		<legend>
			<spring:message code="profile.coordinates" />
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
	
	<spring:message code="profile.editPassword"/><br> 
 	<input type="radio" id="radioPasswordY" name="answerPw" onclick="editPasswordYes()" /><spring:message code="profile.Yes"/>
	<input type="radio" id="radioPasswordN" name="answerPw" onclick="editPasswordNo()" checked="checked" /><spring:message code="profile.No"/>
	<fieldset id="fieldsetPassword" disabled>
		
		<legend>
			<spring:message code="profile.userAccountDetails" />
		</legend>
		<br />
		<acme:password code="profile.password" path="password" />
		<br />
		<acme:password code="profile.repeatPassword" path="repeatPassword" />
		<br/>
	</fieldset>
	
	<jstl:if test="${errorMessage != null}">
		<spring:message code="${errorMessage}" var="error" />
		<font size="4" color="red"><jstl:out value="${error}"></jstl:out></font>
	</jstl:if>
	<br/>
	<br/>
	
	<acme:submit code="profile.save" name="save" />
	<acme:cancel code="profile.cancel" url="profile/display.do" />
	<br/>
	
</form:form>

<script>
	function editPasswordYes() {
    	document.getElementById("fieldsetPassword").disabled = false;
	}
	
	function editPasswordNo() {
    	document.getElementById("fieldsetPassword").disabled = true;
	}
</script>
