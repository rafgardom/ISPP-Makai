<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div>

	<ul>
		<li>
			<b><spring:message code="training.category" />:</b>
			<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
			<jstl:set var="lang" value="<%=LocaleContextHolder.getLocale()%>"/>
			<jstl:if test="${lang == 'es'}">
				<jstl:out value="${training.category.spanishName}"  />
			</jstl:if>
			<jstl:if test="${lang == 'en'}">
				<jstl:out value="${training.category.name}"  />
			</jstl:if>
		</li>
		<li>
			<b><spring:message code="training.duration" />:</b>
			<jstl:if test="${training.duration.day!=0 }">
				<jstl:out value="${training.duration.day}" />
				<spring:message code="training.duration.day" />
			</jstl:if>
			<jstl:if test="${training.duration.month!=0 }">
				<jstl:out value="${training.duration.month}" />
				<spring:message code="training.duration.month" />
			</jstl:if>
			<jstl:if test="${training.duration.year!=0 }">
				<jstl:out value="${training.duration.year}" />
				<spring:message code="training.duration.year" />
			</jstl:if>
				
		</li>
		<li>
			<b><spring:message code="training.price" />:</b>
			<jstl:out value="${training.price}" />
		</li>
		<li>
			<b><spring:message code="training.description" />:</b>
			<jstl:out value="${training.description}" />
		</li>
				
	</ul>

	<acme:link href="training/trainer/edit.do?trainingId=${training.id}" code="training.edit"/>
	<acme:link href="training/trainer/list.do" code="training.goBack"/>

</div>

