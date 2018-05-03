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

<form:form action="${RequestURI}" modelAttribute="requestForm" enctype="multipart/form-data">
	<form:hidden path="id" />
	
	<jstl:if test="${errorMessage != null}">
		<acme:error code="${errorMessage}"/>
	</jstl:if>
	
	
	<div class="row">	
		<div class="col-md-7">	
					<spring:message code="request.description.ph" var="descriptionPH"/>
					<acme:textarea code="request.description" path="description" mandatory="true" rows="15" maxCharacters="2000" placeholder="${descriptionPH}"/>
		</div>
		<div class="col-md-5">
					<spring:message code="request.tags.ph" var="tagsPH"/>
					<acme:textarea code="request.tags" path="tags" maxCharacters="100" placeholder="${tagsPH}" />
					<acme:selectNotEntity code="request.category" path="category" items="${categories}" lang="${lang}" mandatory="true" />
					<jstl:if test="${animals.size()!=0}">
					<acme:select code="request.animal" path="animal" items="${animals}" itemLabel="name" disabled="false"/>
					</jstl:if>
					
					<jstl:if test="${animals.size()==0}">
						<p><spring:message code="no.animal"/><a href="animal/register.do"><spring:message code="add.animal"/></a></p>
					</jstl:if>
		</div>	

	</div>
	
		<acme:submit code="request.save" name="save" />
		<acme:cancel code="request.cancel" url="request/customer/menu.do" />
	<br/>
	
</form:form>