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

<form:form  action="${requestURI }" modelAttribute="notification">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="actors" />
	
	<form:label path="type"><spring:message code="notification.type"/></form:label>
	<form:select path="type">
		<form:option value="-" label="----" />
		<form:options items="${notificationTypes}" />
	</form:select>
	
	<acme:textbox code="notification.reason" path="reason" />
	<acme:textarea code="notification.description" path="description"/>
	
	<acme:submit name="save" code="notification.save" />
	<acme:cancel url="./notification/actor/list.do" code="notification.cancel"/>
</form:form>
