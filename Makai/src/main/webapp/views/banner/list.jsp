<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authentication var="principalUserAccount" property="principal" />
<div class="table-responsive">
<display:table name="bannerForms" id="row" pagesize="5" requestURI="${requestURI}" class="displaytag">
	
<%-- 	<display:column>
		<img src="${row.stringImage}" class="rounded-circle" alt="<spring:message code='profile.no.picture' />" width="125px" height="125px">
	</display:column> --%>
	
	<acme:column code="banner.totalViews" property="totalViews" sortable="true" />
	<acme:column code="banner.currentViews" property="currentViews" sortable="true"/>
	<acme:column code="banner.price" property="price" sortable="true" format="{0,number, 0.00}&euro;"/>
	
	<spring:message code="banner.zone" var="zoneHeader" />
	<display:column class="text-center" title="${zoneHeader}" sortable="true">
		<spring:message code="banner.zone.${row.zone}"  />
	</display:column>
	
	<spring:message code="banner.validated" var="validatedHeader" />
	<display:column class="text-center" title="${validatedHeader}" sortable="true">
		<security:authorize access="isAuthenticated()">
			<security:authorize access="!hasRole('ADMIN')">
				<img src="images/${row.validated}.png" title="<spring:message code='banner.validated.${row.validated}' />" >
			</security:authorize>
			<security:authorize access="hasRole('ADMIN')">
				<jstl:if test="${row.validated==false }">
					<acme:link href="banner/admin/validate.do?bannerId=${row.id}" code="banner.validate" type="success"/>
				</jstl:if>
				<jstl:if test="${row.validated==true }">
					<img src="images/${row.validated}.png" title="<spring:message code='banner.validated.${row.validated}' />" >
				</jstl:if>
				
			</security:authorize>
		</security:authorize>
	</display:column>
	
	<spring:message code="banner.paid" var="titleHeader" />
	<display:column class="text-center" title="${titleHeader}" sortable="true">
		<jstl:choose>
			<jstl:when test="${row.paid == false && principalUserAccount.id == row.actor.userAccount.id && row.validated == true }">
				<acme:link href="banner/actor/pay.do?bannerId=${row.id}" code="banner.pay" type="success"/>
			</jstl:when>
			<jstl:otherwise>
				<img src="images/${row.paid}.png" title="<spring:message code='banner.paid.${row.paid}' />" >
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	<security:authorize access="hasRole('ADMIN')">
		<display:column>
				<a href="profile/displayProfile.do?actorId=${row.actor.id}">${row.actor.name}</a>
		</display:column>
	</security:authorize>
	
	<acme:column code="banner.clicksNumber" property="clicksNumber" sortable="true" />
	<acme:column code="banner.daily" property="dailyViews" sortable="true" />
	<acme:column code="banner.monthly" property="monthlyViews" sortable="true" />
	
	<display:column>
		<div class="btn-group" data-toggle="buttons">	
			<acme:link image="eye" href="banner/actor/display.do?bannerId=${row.id}"/>
			
			<jstl:if test="${principalUserAccount.id == row.actor.userAccount.id }">
				<jstl:if test="${row.totalViews==row.currentViews }">
					<acme:link image="edit" href="banner/actor/edit.do?bannerId=${row.id}" type="warning"/>
				</jstl:if>
				
				<acme:delete href="banner/actor/delete.do?bannerId=${row.id}" id="${row.id}"/>
			</jstl:if>
			
			<security:authorize access="hasRole('ADMIN')">
				<acme:delete href="banner/actor/delete.do?bannerId=${row.id}" id="${row.id}"/>
			</security:authorize>
			
		</div>
	</display:column>
	
		
</display:table>
</div>
