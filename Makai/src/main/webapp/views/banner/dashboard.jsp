<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authentication var="principalUserAccount" property="principal" />

<ul>
	<li>
		<p><spring:message code="banner.sum.price" />: <jstl:out value="${bannersTotalBenefit }" /><p>
	</li>
	<li>
		<p><spring:message code="banner.avg.views" />: <jstl:out value="${bannersAvgBenefit }" /><p>
	</li>
	<li>
		<p><spring:message code="banner.monthly.earnings" />: <jstl:out value="${bannersTotalBenefit }" /><p>
	</li>
	<li>
		<p><spring:message code="banner.more.views" /><p>
		<div class="table-responsive">
		<display:table name="bannerMoreViews" id="row" pagesize="5" requestURI="${requestURI}" class="displaytag">
			
			<display:column>
				<img src="${row.stringImage}" class="rounded-circle" alt="<spring:message code='profile.no.picture' />" width="125px" height="125px">
			</display:column>
			
			<acme:column code="banner.price" property="price" sortable="true" format="{0,number, ,0.00}&euro;"/>
			
			<spring:message code="banner.zone" var="zoneHeader" />
			<display:column class="text-center" title="${zoneHeader}" sortable="true">
				<spring:message code="banner.zone.${row.zone}"  />
			</display:column>
			
			<spring:message code="banner.validated" var="validatedHeader" />
			<display:column class="text-center" title="${validatedHeader}" sortable="true">
				<img src="images/${row.validated}.png" title="<spring:message code='banner.validated.${row.validated}' />" >
			</display:column>
			
			<spring:message code="banner.paid" var="titleHeader" />
			<display:column class="text-center" title="${titleHeader}" sortable="true">
				<img src="images/${row.paid}.png" title="<spring:message code='banner.paid.${row.paid}' />" >
			</display:column>
			
			<security:authorize access="hasRole('ADMIN')">
				<display:column>
					<a href="profile/displayProfile.do?actorId=${row.actor.id}">${row.actor.name}</a>
				</display:column>
			</security:authorize>
			
			<acme:column code="banner.currentViews" property="currentViews" sortable="true" />
			<acme:column code="banner.clicksNumber" property="clicksNumber" sortable="true" />
			
		</display:table>
		</div>
	</li>
	<li>
		<p><spring:message code="banner.less.views" /><p>
		<div class="table-responsive">
		<display:table name="bannerLessViews" id="row" pagesize="5" requestURI="${requestURI}" class="displaytag">
			
			<display:column>
				<img src="${row.stringImage}" class="rounded-circle" alt="<spring:message code='profile.no.picture' />" width="125px" height="125px">
			</display:column>
			
			<acme:column code="banner.price" property="price" sortable="true" format="{0,number, ,0.00}&euro;"/>
			
			<spring:message code="banner.zone" var="zoneHeader" />
			<display:column class="text-center" title="${zoneHeader}" sortable="true">
				<spring:message code="banner.zone.${row.zone}"  />
			</display:column>
			
			<spring:message code="banner.validated" var="validatedHeader" />
			<display:column class="text-center" title="${validatedHeader}" sortable="true">
				<img src="images/${row.validated}.png" title="<spring:message code='banner.validated.${row.validated}' />" >
			</display:column>
			
			<spring:message code="banner.paid" var="titleHeader" />
			<display:column class="text-center" title="${titleHeader}" sortable="true">
				<img src="images/${row.paid}.png" title="<spring:message code='banner.paid.${row.paid}' />" >
			</display:column>
			
			<security:authorize access="hasRole('ADMIN')">
				<display:column>
					<a href="profile/displayProfile.do?actorId=${row.actor.id}">${row.actor.name}</a>
				</display:column>
			</security:authorize>
			
			<acme:column code="banner.currentViews" property="currentViews" sortable="true" />
			<acme:column code="banner.clicksNumber" property="clicksNumber" sortable="true" />
			
		</display:table>
		</div>
	</li>
	<li>
		<p><spring:message code="banner.more.clicks" /><p>
		<div class="table-responsive">
		<display:table name="bannerMoreClicks" id="row" pagesize="5" requestURI="${requestURI}" class="displaytag">
			
			<display:column>
				<img src="${row.stringImage}" class="rounded-circle" alt="<spring:message code='profile.no.picture' />" width="125px" height="125px">
			</display:column>
			
			<acme:column code="banner.price" property="price" sortable="true" format="{0,number, ,0.00}&euro;"/>
			
			<spring:message code="banner.zone" var="zoneHeader" />
			<display:column class="text-center" title="${zoneHeader}" sortable="true">
				<spring:message code="banner.zone.${row.zone}"  />
			</display:column>
			
			<spring:message code="banner.validated" var="validatedHeader" />
			<display:column class="text-center" title="${validatedHeader}" sortable="true">
				<img src="images/${row.validated}.png" title="<spring:message code='banner.validated.${row.validated}' />" >
			</display:column>
			
			<spring:message code="banner.paid" var="titleHeader" />
			<display:column class="text-center" title="${titleHeader}" sortable="true">
				<img src="images/${row.paid}.png" title="<spring:message code='banner.paid.${row.paid}' />" >
			</display:column>
			
			<security:authorize access="hasRole('ADMIN')">
				<display:column>
					<a href="profile/displayProfile.do?actorId=${row.actor.id}">${row.actor.name}</a>
				</display:column>
			</security:authorize>
			
			<acme:column code="banner.currentViews" property="currentViews" sortable="true" />
			<acme:column code="banner.clicksNumber" property="clicksNumber" sortable="true" />
			
		</display:table>
		</div>
	</li>
	<li>
		<p><spring:message code="banner.less.clicks" /><p>
		<div class="table-responsive">
		<display:table name="bannerLessClicks" id="row" pagesize="5" requestURI="${requestURI}" class="displaytag">
			
			<display:column>
				<img src="${row.stringImage}" class="rounded-circle" alt="<spring:message code='profile.no.picture' />" width="125px" height="125px">
			</display:column>
			
			<acme:column code="banner.price" property="price" sortable="true" format="{0,number, ,0.00}&euro;"/>
			
			<spring:message code="banner.zone" var="zoneHeader" />
			<display:column class="text-center" title="${zoneHeader}" sortable="true">
				<spring:message code="banner.zone.${row.zone}"  />
			</display:column>
			
			<spring:message code="banner.validated" var="validatedHeader" />
			<display:column class="text-center" title="${validatedHeader}" sortable="true">
				<img src="images/${row.validated}.png" title="<spring:message code='banner.validated.${row.validated}' />" >
			</display:column>
			
			<spring:message code="banner.paid" var="titleHeader" />
			<display:column class="text-center" title="${titleHeader}" sortable="true">
				<img src="images/${row.paid}.png" title="<spring:message code='banner.paid.${row.paid}' />" >
			</display:column>
			
			<security:authorize access="hasRole('ADMIN')">
				<display:column>
					<a href="profile/displayProfile.do?actorId=${row.actor.id}">${row.actor.name}</a>
				</display:column>
			</security:authorize>
			
			<acme:column code="banner.currentViews" property="currentViews" sortable="true" />
			<acme:column code="banner.clicksNumber" property="clicksNumber" sortable="true" />
			
		</display:table>
		</div>
	</li>
</ul>



