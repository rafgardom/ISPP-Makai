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

<form:form action="${RequestURI}" modelAttribute="trainerForm" enctype="multipart/form-data">
	<form:hidden path="id"/>
	
	<br>
	<jstl:if test="${errorMessage != null}">
		<acme:error code="${errorMessage}"/>
	</jstl:if>
	
	<div class="row">
		<div class="col-md-5">
			<h3>
				<spring:message code="trainer.personalDetails" />
			</h3>
	
			<acme:textbox code="trainer.name" path="name" mandatory="true" />
			<acme:textbox code="trainer.surname" path="surname" mandatory="true" />
			<acme:input image="phone" code="trainer.phone" path="phone" mandatory="true" />
			<acme:input image="at" code="trainer.email" path="email" mandatory="true" />
			<acme:textbox code="trainer.nid" path="nid" mandatory="true" />
		</div>
		<div class="offset-md-1 col-md-5">
			<h3>
				<spring:message code="trainer.coordinates" />
			</h3>
	
			<acme:textbox code="coordinates.country" path="coordinates.country" mandatory="true" />
			<acme:textbox code="coordinates.state" path="coordinates.state" />
			<acme:textbox code="coordinates.province" path="coordinates.province" />
			<acme:textbox code="coordinates.city" path="coordinates.city" mandatory="true" />
			<acme:textbox code="coordinates.zipCode" path="coordinates.zip_code" mandatory="true" />
		</div>

		<security:authorize access="isAnonymous()">
		<div class="col-md-5">
			<br>
			<h3>
				<spring:message code="trainer.userAccountDetails" />
			</h3>
			<br />
			<acme:input image="user-xs" code="trainer.username" path="username" mandatory="true" />
			<acme:password image="lock1" code="trainer.password" path="password" mandatory="true" infoButton="true" info="true" id="password"/>
			<acme:password image="lock1" code="trainer.repeatPassword" path="repeatPassword" mandatory="true" id="confirm_password" />
		</div>
		</security:authorize>
		<div class="offset-md-1 col-md-5">
			<acme:inputImage path="userImage" imageError="${imageError}" />
		</div>
		<div class="col-md-12">
			<acme:acceptUseTerms href="misc/privacyPolicy.do?sc=false" code="trainer.accept" path="acceptCondition" linkCode="trainer.conditions"
					mandatory="true" target="_blank" />
		</div>
	</div>
	<br><br>
	<acme:submit code="trainer.register" name="save" />
	<acme:cancel code="trainer.cancel" url="" />
	
</form:form>
<!-- CONTROL PASSWORD 1 -->
<script type="text/javascript">
	$('#password').on('keyup', function () {
		var pass1 = $('#password').val();
		var message1 = $('#passwordmessage');
		
		if (pass1 === ""){
		  	message1.html('');
	    } else
			if(pass1.toLowerCase() == pass1){
				message1.html('<spring:message code="password.no.capital" />').css('color', 'red');
			} else
				if(pass1.toUpperCase() == pass1){
		    		message1.html('<spring:message code="password.no.lower.case" />').css('color', 'red');
				} else
				  if (!(/\d/.test(pass1))) {
					  message1.html('<spring:message code="password.no.number" />').css('color', 'red');
				  } else
					if(pass1.length < 6){
					  message1.html('<spring:message code="password.6.character" />').css('color', 'red');
					}else
						if(pass1.length > 30){
						  message1.html('<spring:message code="password.30.character" />').css('color', 'red');
						}else
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
				if(pass2.toUpperCase() == pass2){
		    		message2.html('<spring:message code="password.no.lower.case" />').css('color', 'red');
				} else
				  if (!(/\d/.test(pass2))) {
					  message2.html('<spring:message code="password.no.number" />').css('color', 'red');
				  } else 
					  if(pass2.length < 6){
						  message2.html('<spring:message code="password.6.character" />').css('color', 'red');
						}else
							if(pass2.length > 30){
							  message2.html('<spring:message code="password.30.character" />').css('color', 'red');
							}else
							  if (pass1 != pass2) {
								  message2.html('<spring:message code="password.same" />').css('color', 'red');
							  } else 
								  message2.html('<spring:message code="password.correct" />').css('color', 'green');
	});
</script>

