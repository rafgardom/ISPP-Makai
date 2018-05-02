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
	
	<display:column>

		<div class="btn-group">	
			<acme:link image="eye" href="notification/actor/display.do?notificationId=${row.id}"/>
			<acme:link image="trash" href="notification/actor/delete.do?notificationId=${row.id}" type="danger"/>
		</div>

	</display:column>
	
</display:table>
</div>
	<jstl:if test="${!empty notifications}">
		<acme:delete href="notification/actor/deleteAll.do?notificationId=${row.id}" id="${row.id}" code="notification.deleteall" cuestioncode="notification.deleteall.cuestion"/>
	</jstl:if>
	
<security:authorize access="hasRole('ADMIN')">
	<acme:link href="notification/admin/create.do" code="notification.create" type="success"/>
</security:authorize>