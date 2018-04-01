<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="trainings" id="row" pagesize="5" requestURI="${requestURI}" class="displaytag">
	
	<acme:column code="training.category" property="category" sortable="true"/>
	<acme:column code="training.price" property="price" format="{0,number, ,000.00}&euro;"/>
	<acme:column code="training.description" property="description" />
	
	<spring:message code="training.duration" var="durationHeader" />
	<display:column title="${durationHeader}" >
		<jstl:if test="${row.duration.day!=0 }">
			<jstl:out value="${row.duration.day}" />
			<spring:message code="training.duration.day" />
		</jstl:if>
		<jstl:if test="${row.duration.month!=0 }">
			<jstl:out value="${row.duration.month}" />
			<spring:message code="training.duration.month" />
		</jstl:if>
		<jstl:if test="${row.duration.year!=0 }">
			<jstl:out value="${row.duration.year}" />
			<spring:message code="training.duration.year" />
		</jstl:if>
	</display:column>
	
	<security:authorize access="hasRole('TRAINER')">		
		<display:column>
			<div class="btn-group" data-toggle="buttons">
				<acme:link href="training/trainer/display.do?trainingId=${row.id}" image="eye"/>
				<acme:link href="training/trainer/edit.do?trainingId=${row.id}" type="warning" image="edit"/>
				<acme:delete href="training/trainer/delete.do?trainingId=${row.id}" id="${row.id}" />
			</div>
		</display:column>
	</security:authorize>
	
</display:table>

<security:authorize access="hasRole('TRAINER')">
	<acme:link href="training/trainer/create.do" code="training.create" type="success"/>
</security:authorize>
