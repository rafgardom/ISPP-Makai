<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="notifications" id="row" requestURI="${requestURI}" class="displaytag">
	
	<acme:column code="notification.moment" property="moment" format="{0,date,dd/MM/yyyy HH:mm}" />
	<acme:column code="notification.reason" property="reason" />
	<acme:column code="notification.description" property="description" />
	
	<display:column>
		<a href="notification/actor/delete.do?notificationId=${row.id}">
			<spring:message	code="notification.delete" />
		</a>
	</display:column>
	
</display:table>

<security:authorize access="hasRole('ADMIN')">
	<a href="notification/admin/create.do"><spring:message code="notification.create" /></a>
</security:authorize>
