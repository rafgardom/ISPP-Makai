<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<div class="table-responsive">
<display:table name="notifications" id="row" pagesize="10" requestURI="${requestURI}" class="displaytag">
	<display:column>
	<jstl:if test="${!row.isRead}">
		<img src="images/new.png" height="45rem"/>
	</jstl:if>	
	</display:column>
	
	<acme:column code="notification.moment" property="moment" format="{0,date,HH:mm:ss dd/MM/yyyy}" sortable="true"/>
	<acme:column code="notification.reason" property="reason" sortable="true"/>
	
	<jstl:set var="substrDescription" value="${fn:substring(row.description, 0, 40)}" />
	

	

	
	<spring:message code="notification.description" var="descriptionHeader" />
<%-- 	<display:column title="${descriptionHeader}" >
		<jstl:out value="${substrDescription }" />
		<jstl:if test="${fn:length(row.description)>40 }">
			<jstl:out value="..." />
		</jstl:if>
	</display:column> --%>
	
	<display:column>

		<div class="btn-group">	
			<acme:link image="eye" href="notification/actor/display.do?notificationId=${row.id}"/>
			<jstl:if test="${row.isRead}">
				<acme:link image="trash" href="notification/actor/delete.do?notificationId=${row.id}" type="danger"/>
			</jstl:if>
		</div>

	</display:column>
	
</display:table>
</div>
<security:authorize access="hasRole('ADMIN')">
	<acme:link href="notification/admin/create.do" code="notification.create" type="success"/>
</security:authorize>