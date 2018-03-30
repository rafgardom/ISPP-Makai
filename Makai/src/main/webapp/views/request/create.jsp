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

<form:form action="${RequestURI}" modelAttribute="requestForm" enctype="multipart/form-data">
	<form:hidden path="id" />
	
	<jstl:if test="${errorMessage != null}">
		<acme:error code="${errorMessage}"/>
	</jstl:if>
	
	
	<div class="row">	
		<div class="col-md-7">	
					<spring:message code="request.description.ph" var="descriptionPH"/>
					<acme:textarea code="request.description" path="description" mandatory="true" rows="15" maxlength="2000" placeholder="${descriptionPH}"/>
		</div>
		<div class="col-md-5">
					<spring:message code="request.tags.ph" var="tagsPH"/>
					<acme:textarea code="request.tags" path="tags" maxlength="100" placeholder="${tagsPH}"/>
					<acme:selectNotEntity code="request.category" path="category" items="${categories}" mandatory="true" />
					<acme:select code="request.animal" path="animal" items="${animals}" itemLabel="name" disabled="false"/>
		
		</div>	

	</div>
	
		<acme:submit code="request.save" name="save" />
		<acme:cancel code="request.cancel" url="request/customer/myList.do" />
	<br/>
	
</form:form>