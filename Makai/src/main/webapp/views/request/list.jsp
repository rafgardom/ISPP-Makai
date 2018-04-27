<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div class="table-responsive">
<display:table name="requests" id="row" pagesize="5" requestURI="${requestURI}" class="displaytag">

	<jstl:set var="substrDescription" value="${fn:substring(row.description, 0, 40)}" />
	<spring:message code="request.description" var="descriptionHeader" />
	<display:column title="${descriptionHeader}" >
		<jstl:if test="${fn:length(row.description)>40}">
			<jstl:out value="${substrDescription}..." />
		</jstl:if>
		<jstl:if test="${fn:length(row.description)<=40}">
			<jstl:out value="${substrDescription}" />
		</jstl:if>
	</display:column>
	<security:authorize access="hasRole('TRAINER')">
	
		<spring:message code="request.customer" var="customerHeader" />
		<display:column title="${customerHeader}" class="text-center" sortable="true">
			<a href="profile/displayProfile.do?actorId=${row.customer.id}"><jstl:out value="${row.customer.name}"/></a>
		</display:column>
	
	</security:authorize>
	<jstl:set var="substrTags" value="${fn:substring(row.tags, 0, 20)}" />
	<spring:message code="request.tags" var="tagsHeader" />
	<display:column title="${tagsHeader}" >
		<jstl:if test="${fn:length(row.tags)>20}">
			<jstl:out value="${substrTags}..." />
		</jstl:if>
		<jstl:if test="${fn:length(row.tags)<=20}">
			<jstl:out value="${substrTags}" />
		</jstl:if>
	</display:column>
	
	<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
	<jstl:set var="lang" value="<%=LocaleContextHolder.getLocale()%>"/>
	<jstl:set var="language" value="category.spanishName"/>
	<jstl:if test="${lang == 'en'}">
		<jstl:set var="language" value="category.name"/>
	</jstl:if>
	<acme:column code="request.category" property="${language}" sortable="true" />
	
	
	<spring:message code="request.animal" var="animalHeader" />
	<spring:message code="request.none" var="none"/>
	<display:column title="${animalHeader}" >
		<jstl:if test="${row.animal != null}">
			<a href="animal/display.do?animalId=${row.animal.id}"><jstl:out value="${row.animal.name}"/></a>
		</jstl:if>
		<jstl:if test="${row.animal == null}">
			<jstl:out value="${none}" />
		</jstl:if>
	</display:column>
	
	<display:column>
		<div class="btn-group">
		
			<acme:link image="eye" href="request/actor/display.do?requestId=${row.id}"/>
			
			<security:authorize access="hasRole('CUSTOMER')">
				<%-- <acme:link href="request/customer/edit.do?requestId=${row.id}" code="request.edit"/> --%>
				<jstl:if test="${requestsWithOffer.contains(row)}">
					<acme:link href="offer/customer/list.do?requestId=${row.id}" code="request.list.offer" type="dark" image="deal"/>
				</jstl:if>
				
				<jstl:set var="showDelete" value="${true}"/>
				<jstl:forEach var="offer" items="${offersAcepted}">
					<jstl:if test="${offer.request.id == row.id}">
						<jstl:set var="showDelete" value="${false}"/>
					</jstl:if>
				</jstl:forEach>
				<jstl:if test="${showDelete == true}">
					<%-- <acme:link href="request/customer/edit.do?requestId=${row.id}" code="request.edit"/> --%>
					<acme:delete href="request/customer/delete.do?requestId=${row.id}" id="${row.id}"/>
				</jstl:if>
			</security:authorize>
			
			<security:authorize access="hasRole('TRAINER')">
				<acme:link href="offer/trainer/create.do?requestId=${row.id}" code="offer.create" image="deal" type="dark"/>
			</security:authorize>
		</div>
	</display:column>

</display:table>
</div>
<security:authorize access="hasRole('CUSTOMER')">
	<br>
	<acme:link href="request/customer/create.do" code="request.create" type="success"/>
	<acme:link href="request/customer/menu.do" code="notification.goBack"/>
</security:authorize>

