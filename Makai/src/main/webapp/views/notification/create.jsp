<%--
 * action-1.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<jstl:set var="lang" value="<%=LocaleContextHolder.getLocale()%>"/>

<form:form  action="${requestURI }" modelAttribute="notification">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="actor" />
	<form:hidden path="isRead" />
	
	<jstl:if test="${errorMessage != null}">
		<acme:error code="${errorMessage}"/>
	</jstl:if>
	
	<div class="row">
		<div class="col-md-6">
			<acme:selectNotEntity mandatory="true" items="${notificationTypes}" lang="${lang}" code="notification.type" path="type" />
			<acme:input path="reason" code="notification.reason" mandatory="true" maxlength="40"/>
		</div>
		<div class="col-md-6">
			<acme:textarea mandatory="true" code="notification.description" path="description" rows="4" maxlength="200"/>
		</div>
	</div>
	<acme:submit name="save" code="notification.save" />
	<acme:cancel url="./notification/actor/list.do" code="notification.cancel"/>
</form:form>
