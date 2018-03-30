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
			<acme:textbox code="customer.phone" path="phone" mandatory="true" />
			<acme:textbox code="customer.email" path="email" mandatory="true" />
			<acme:textbox code="customer.nid" path="nid" mandatory="true" />
		</div>
		
		<div class="offset-md-1 col-md-5">
			
			<h3><spring:message code="customer.coordinates" /></h3>
	
			<acme:textbox code="coordinates.country" path="coordinates.country" mandatory="true" />
			<acme:textbox code="coordinates.state" path="coordinates.state" />
			<acme:textbox code="coordinates.province" path="coordinates.province" />
			<acme:textbox code="coordinates.city" path="coordinates.city" mandatory="true" />
			<acme:textbox code="coordinates.zipCode" path="coordinates.zip_code" mandatory="true" />
		</div>

		<div class="col-md-12">
		<br/>
			
			<spring:message code="customer.picture"/>
			<form:input type="file" path="userImage" id="userImage" name="userImage" mandatory="true"
			enctype="multipart/form-data" code="customer.picture"></form:input>
			<br/>
			<p><spring:message code="image.formats"/></p>
			<jstl:if test="${imageError != null}">
				<acme:error code="${imageError}" type="danger"/>
			</jstl:if>
		<br/>
		</div>

<br/><br/><br/><br/>

	<security:authorize access="isAnonymous()">
		<div class="col-md-5">
			
				<h3><spring:message code="customer.userAccountDetails" /></h3>
				<br />
				<acme:textbox code="customer.username" path="username" mandatory="true" />
				<acme:password code="customer.password" path="password" mandatory="true" />
				<acme:password code="customer.repeatPassword" path="repeatPassword" mandatory="true" />
		</div>
	</security:authorize>
	
	<div class=" col-md-12">
		<acme:acceptUseTerms href="misc/privacyPolicy.do?sc=false" code="customer.accept" path="acceptCondition" linkCode="customer.conditions"
			mandatory="true" target="_blank" />
	</div>

</div>
		<br/>
		<acme:submit code="customer.register" name="save" />
		<acme:cancel code="customer.cancel" url="" />
	


</form:form>