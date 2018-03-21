<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<div class="table-responsive">
<display:table name="notifications" id="row" requestURI="${requestURI}" class="displaytag">
	
	<acme:column code="notification.moment" property="moment" format="{0,date,dd/MM/yyyy HH:mm}" />
	<acme:column code="notification.reason" property="reason" />
	
	<jstl:set var="substrDescription" value="${fn:substring(row.description, 0, 40)}" />
	
	<spring:message code="notification.description" var="descriptionHeader" />
	<display:column title="${descriptionHeader}" >
		<jstl:out value="${substrDescription }" />
		<jstl:if test="${fn:length(row.description)>40 }">
			<jstl:out value="..." />
		</jstl:if>
	</display:column>
	
	<display:column>
		<div class="btn-group" data-toggle="buttons">	
			<acme:link href="notification/actor/display.do?notificationId=${row.id}" code="notification.display"/>
			<acme:link href="notification/actor/delete.do?notificationId=${row.id}" code="notification.delete" type="danger"/>
		</div>
	</display:column>
	
</display:table>
</div>
<security:authorize access="hasRole('ADMIN')">
	<acme:link href="notification/admin/create.do" code="notification.create"/>
</security:authorize>
