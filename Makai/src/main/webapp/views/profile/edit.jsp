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
	
			<acme:input code="profile.phone" path="phone" mandatory="true" image="phone" pattern="((\+|00)\d{2,4}(\s)?)?\d{9,13}" />
			<acme:input code="profile.email" path="email" mandatory="true" image="at" type="email" />
		</div>
		
		<div class="offset-md-1 col-md-5">
			
			<h3><spring:message code="profile.coordinates" /></h3>
			<br />
			<acme:textbox code="coordinates.country" path="coordinates.country" mandatory="true" />
			<acme:textbox code="coordinates.state" path="coordinates.state" />
			<acme:textbox code="coordinates.province" path="coordinates.province" />
			<acme:textbox code="coordinates.city" path="coordinates.city" mandatory="true" />
			<acme:textbox code="coordinates.zipCode" path="coordinates.zip_code" mandatory="true" />
		
		</div>	
		<div class="col-md-5">

	
		<div class="alert alert-primary">
		<h3>
			<spring:message code="profile.userAccountDetails" />
		</h3>
		<br>
		<h6><spring:message code="profile.editPassword"/><br><br>	
			<strong><input type="radio" id="radioPasswordY" name="answerPw" onclick="editPasswordYes()" /><spring:message code="profile.Yes"/>
			<input type="radio" id="radioPasswordN" name="answerPw" onclick="editPasswordNo()" checked="checked" /><spring:message code="profile.No"/></strong></h6>
			<fieldset id="fieldsetPassword" disabled>
				<br />
				<acme:password code="security.password" path="password" image="lock1" infoButton="true" info="true" />
				<acme:password code="security.password" path="repeatPassword" image="lock1"/>
			</fieldset>
		</div>
	
		</div>
		<div class="offset-md-1 col-md-5">
			<acme:inputImage path="userImage"/>
		</div>
	</div>
	<br>	
		
	
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
