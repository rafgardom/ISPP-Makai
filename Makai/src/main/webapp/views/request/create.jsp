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
		
	<fieldset>
		<acme:textbox code="request.description" path="description" mandatory="true" />
		<br />
		<acme:input code="request.tags" path="tags" />
		<br />
		<acme:selectNotEntity code="request.category" path="category" items="${categories}" />
		<br />
		<%--
		<acme:select code="request.animal" path="animal" items="${animals}" itemLabel="name" />
		<br />
		 --%>
		 
		 <spring:message code="request.myAnimals" var="myA"/>
		 <spring:message code="request.otherAnimals" var="otherA"/>
		 
		 <form:label path="animal">
			<spring:message code="request.animal" />
		</form:label>	
		<form:select id="${UUID.randomUUID().toString()}" path="animal" onchange="javascript: return true;">
			<form:option value="0" label="----" selected="selected" disabled="true"/>
			<form:option value="0" label="${myA}" disabled="true"/>		
			<form:options items="${myAnimals}" itemValue="id" itemLabel="name" />
			<form:option value="0" label="${otherA}" disabled="true"/>
			<form:options items="${otherAnimals}" itemValue="id" itemLabel="name" />
		</form:select>
		<form:errors path="animal" cssClass="error" />
		 
	</fieldset>
	<br/>
	
	<jstl:if test="${errorMessage != null}">
		<spring:message code="${errorMessage}" var="error" />
		<font size="4" color="red"><jstl:out value="${error}"></jstl:out></font>
	</jstl:if>
	<br/>
	
		<acme:submit code="request.create" name="save" />
		<acme:cancel code="request.cancel" url="" />
	<br/>
	
</form:form>