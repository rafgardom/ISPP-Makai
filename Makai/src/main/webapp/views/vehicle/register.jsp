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

<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<jstl:set var="lang" value="<%=LocaleContextHolder.getLocale()%>"/>

<form:form action="${RequestURI}" modelAttribute="vehicleForm" enctype="multipart/form-data">
	<form:hidden path="id"/>
	
	<jstl:if test="${errorMessage != null}">
		<acme:error code="${errorMessage}"/>
	</jstl:if>
	
	<div class="row">
		<div class="col-md-5">
			<acme:selectNotEntity code="vehicle.brand" path="brand" items="${brands}" lang="${lang}" mandatory="true" />
			<acme:input code="vehicle.seats" path="seats" type="number" min="0" mandatory="true" />
			<acme:selectNotEntity code="vehicle.carType" path="carType" items="${carTypes}" lang="${lang}" mandatory="true" />
			<acme:textbox code="vehicle.accommodation" path="accommodation" mandatory="false" />
		</div>
		<div class="offset-md-1 col-md-5">
			<acme:textbox code="vehicle.year" path="year" mandatory="true" />
			<acme:textbox code="vehicle.description" path="description" mandatory="true" />
			<acme:textbox code="vehicle.color" path="color" mandatory="true" />
			<acme:textbox code="vehicle.license" path="license" mandatory="true" />
		</div>
		<div class="col-12">
			<acme:inputImage path="userImage" mandatory="false"/>
		</div>
	</div>		


		
	<security:authorize access="isAnonymous()">
	</security:authorize>
	<br/><br/>
	<acme:submit code="vehicle.register" name="save" />
	<acme:cancel code="vehicle.cancel" url="vehicle/list.do" />
	
</form:form>