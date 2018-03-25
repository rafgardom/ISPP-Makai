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
	
	<br>
	<jstl:if test="${errorMessage != null}">
		<acme:error code="${errorMessage}"/>
	</jstl:if>
	<div class="row">
		<div class="col-md-5">

			<h3><spring:message code="profile.personalDetails" /></h3>
			<br />
			
			<acme:textbox code="profile.name" path="name" mandatory="true" />
			
			<security:authorize access="hasAnyRole('CUSTOMER', 'ADMIN', 'TRAINER')">
				<acme:textbox code="profile.surname" path="surname" mandatory="true" />
				<acme:textbox code="profile.nid" path="nid" mandatory="true" />
			</security:authorize>
	
			<acme:textbox code="profile.phone" path="phone" mandatory="true" />
			<acme:textbox code="profile.email" path="email" mandatory="true" />
	
			<form:input path="userImage" type="file" />
			<spring:message code="image.formats" var="formats" /><p><jstl:out value="${formats}"/></p><br>
			<form:errors path="userImage" cssClass="error" />
		</div>
		
		<div class="offset-md-1 col-md-5">
			
			<h3><spring:message code="profile.coordinates" /></h3>
			<br />
			<acme:textbox code="coordinates.country" path="coordinates.country"
				mandatory="true" />
			<acme:textbox code="coordinates.state" path="coordinates.state" />
			<acme:textbox code="coordinates.province" path="coordinates.province" />
			<acme:textbox code="coordinates.city" path="coordinates.city"
				mandatory="true" />
			<acme:textbox code="coordinates.zipCode" path="coordinates.zip_code"
				mandatory="true" />
		
		</div>	
	</div>
	<br>	
		
		<fieldset id="fieldsetPassword" disabled>
			
			
			<h3>
				<spring:message code="profile.userAccountDetails" />
			</h3>
			<br />
			<acme:password code="profile.password" path="password" />
			<acme:password code="profile.repeatPassword" path="repeatPassword" />
		</fieldset>
		<h6 class="alert alert-info col-lg-5"><spring:message code="profile.editPassword"/><br><br>
			
	 		<strong><input type="radio" id="radioPasswordY" name="answerPw" onclick="editPasswordYes()" /><spring:message code="profile.Yes"/>
			<input type="radio" id="radioPasswordN" name="answerPw" onclick="editPasswordNo()" checked="checked" /><spring:message code="profile.No"/></strong>
		
		</h6>


	<br/>
	<br/>
	
	<acme:submit code="profile.save" name="save" />
	<acme:cancel code="profile.cancel" url="profile/display.do" />
	
</form:form>

<script>
	function editPasswordYes() {
    	document.getElementById("fieldsetPassword").disabled = false;
	}
	
	function editPasswordNo() {
    	document.getElementById("fieldsetPassword").disabled = true;
	}
</script>
