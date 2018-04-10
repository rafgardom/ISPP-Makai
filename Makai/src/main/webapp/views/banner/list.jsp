<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div class="table-responsive">
<display:table name="bannerForms" id="row" pagesize="5" requestURI="${requestURI}" class="displaytag">
	
	<display:column>
		<img src="${row.stringImage}" class="rounded-circle" alt="<spring:message code='profile.no.picture' />" width="125px" height="125px">
	</display:column>
	
	<acme:column code="banner.totalViews" property="totalViews" sortable="true" />
	<acme:column code="banner.currentViews" property="currentViews" sortable="true"/>
	<acme:column code="banner.price" property="price" sortable="true"/>
	<acme:column code="banner.zone" property="zone" sortable="true"/>
	
	<security:authorize access="hasRole('ADMIN')">
		<display:column>
				<a href="profile/displayProfile.do?actorId=${row.actor.id}">${row.actor.name}</a>
		</display:column>
	</security:authorize>
	
	<display:column>
		<div class="btn-group" data-toggle="buttons">	
			<acme:link image="eye" href="banner/actor/display.do?bannerId=${row.id}"/>
			
			<jstl:if test="${row.totalViews==row.currentViews }">
				<acme:link image="edit" href="banner/actor/edit.do?bannerId=${row.id}" type="warning"/>
			</jstl:if>
			
			<acme:delete href="banner/actor/delete.do?bannerId=${row.id}" id="${row.id}"/>
			
		</div>
	</display:column>
	
		
</display:table>
</div>