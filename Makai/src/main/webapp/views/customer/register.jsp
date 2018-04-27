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

<form:form action="${RequestURI}" modelAttribute="customerForm" enctype="multipart/form-data">
	<form:hidden path="id"/>
		
	<br>
	<jstl:if test="${errorMessage != null}">
		<acme:error code="${errorMessage}"/>
	</jstl:if>
	
	<div class="row">
		<div class="col-md-5">

			<h3><spring:message code="profile.personalDetails" /></h3>
			
			<acme:textbox code="customer.name" path="name" mandatory="true" />
			<acme:textbox code="customer.surname" path="surname" mandatory="true" />
			<acme:input image="phone" code="customer.phone" path="phone" mandatory="true" />
			<acme:input image="at" code="customer.email" path="email" type="email" mandatory="true" />
		</div>
		
		<div class="offset-md-1 col-md-5">
			
			<h3><spring:message code="customer.coordinates" /></h3>
	
			<acme:textbox code="coordinates.country" path="coordinates.country" mandatory="true" />
			<acme:textbox code="coordinates.state" path="coordinates.state" />
			<acme:textbox code="coordinates.province" path="coordinates.province" />
			<acme:textbox code="coordinates.city" path="coordinates.city" mandatory="true" />
			<acme:textbox code="coordinates.zipCode" path="coordinates.zip_code" mandatory="true" />
		</div>


	<div class="col-md-5">
		
			<h3><spring:message code="customer.userAccountDetails" /></h3>
			<br />
			<acme:input image="user-xs" code="customer.username" path="username" mandatory="true" />
			<acme:password image="lock1" code="customer.password" path="password" mandatory="true" infoButton="true" info="true" id="password"/></h6>
			<acme:password image="lock1" code="customer.repeatPassword" path="repeatPassword" mandatory="true" id="confirm_password"/></h6>
	</div>
	
	<div class="offset-md-1 col-md-5">
		<acme:inputImage path="userImage" imageError="${imageError}" />
	</div>
	
	<div class="col-md-12">
		<acme:acceptUseTerms href="misc/privacyPolicy.do?sc=false" code="customer.accept" path="acceptCondition" linkCode="customer.conditions"
			mandatory="true" target="_blank" />
	</div>

</div>
		<br/>
		<acme:submit code="customer.register" name="save" />
		<acme:cancel code="customer.cancel" url="" />
	


</form:form>
<!-- CONTROL PASSWORD 1 -->
<script type="text/javascript">
	$('#password').on('keyup', function () {
		var pass1 = $('#password').val();
		var message1 = $('#passwordmessage');
		
		if(pass1.toLowerCase() == pass1){
			message1.html('<spring:message code="password.no.capital" />').css('color', 'red');
		} else
		  if (!(/\d/.test(pass1))) {
			  message1.html('<spring:message code="password.no.number" />').css('color', 'red');
		  } else 
			  message1.html('<spring:message code="password.correct" />').css('color', 'green');
	});
</script>

<!-- CONTROL PASSWORD 2 -->
<script type="text/javascript">
	$('#password, #confirm_password').on('keyup', function () {
		var pass1 = $('#password').val();
		var pass2 = $('#confirm_password').val();
		var message2 = $('#confirm_passwordmessage');
		if (pass2 === ""){
		  	message2.html('');
	    } else
			if(pass2.toLowerCase() == pass2){
				message2.html('<spring:message code="password.no.capital" />').css('color', 'red');
			} else
			  if (!(/\d/.test(pass2))) {
				  message2.html('<spring:message code="password.no.number" />').css('color', 'red');
			  } else 
				  if (pass1 != pass2) {
					  message2.html('<spring:message code="password.same" />').css('color', 'red');
				  } else 
					  message2.html('<spring:message code="password.correct" />').css('color', 'green');
	});
</script>
