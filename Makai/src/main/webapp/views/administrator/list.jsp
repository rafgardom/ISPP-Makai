<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<security:authentication var="principalUserAccount" property="principal" />

<display:table name="actors" id="row" requestURI="${requestURI }" pagesize="4" class="displaytag">
	
	<acme:column code="administrator.actor.name" property="name"/>
	
	<acme:column code="administrator.actor.email" property="email"/>
	
	<acme:column code="administrator.actor.phone" property="phone"/>
		
	<spring:message code="administrator.actor.coordinates" var="coordinatesHeader" />
	<display:column title="${coordinatesHeader}" sortable="false">
		<ul>
			<li>
				<spring:message code="administrator.actor.country" />: <jstl:out value="${row.coordinates.country }"></jstl:out>
			</li>
			
			<jstl:if test="${not empty row.coordinates.state }">
				<li>
					<spring:message code="administrator.actor.state" />: <jstl:out value="${row.coordinates.state }"></jstl:out>
				</li>
			</jstl:if>
			
			<jstl:if test="${not empty row.coordinates.province }">
				<li>
					<spring:message code="administrator.actor.provice" />: <jstl:out value="${row.coordinates.province }"></jstl:out>
				</li>
			</jstl:if>
			
			<li>
				<spring:message code="administrator.actor.city" />: <jstl:out value="${row.coordinates.city }"></jstl:out> 
			</li>
		</ul>
	</display:column>  
	
	<display:column>
		<jstl:choose>
			<jstl:when test="${row.userAccount.enabled }">
				<form:form action="administrator/ban.do?actorId=${row.id}" modelAttribute="actor">
					<acme:submit image="ban" name="ban" code="administrator.actor.ban" />
				</form:form>
			</jstl:when>
			<jstl:otherwise>
				<form:form action="administrator/unban.do?actorId=${row.id}" modelAttribute="actor">
					<acme:submit image="ban" name="unban" code="administrator.actor.unban" />
				</form:form>
			</jstl:otherwise>
		</jstl:choose>
	</display:column> 
	
	
	
</display:table>