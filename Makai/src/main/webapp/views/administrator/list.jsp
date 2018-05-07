<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<security:authentication var="principalUserAccount" property="principal" />

<spring:message code="administrator.actor.customers" var="customersHeader" />
<h3><jstl:out value="${customersHeader}"/></h3>
<div class="table-responsive">
<display:table name="customers" id="row" requestURI="${requestURI }" pagesize="4" class="displaytag">
	
	<acme:column code="administrator.actor.username" property="userAccount.username" sortable="true"/>
	<acme:column code="administrator.actor.name" property="name"/>	
	<acme:column code="administrator.actor.email" property="email"/>
	<acme:column code="administrator.actor.phone" property="phone"/>
		
	<spring:message code="administrator.actor.coordinates" var="coordinatesHeader" />
	<display:column title="${coordinatesHeader}" sortable="false">
		
				<spring:message code="administrator.actor.country" />: <jstl:out value="${row.coordinates.country }"></jstl:out>
			
			
			<jstl:if test="${not empty row.coordinates.state }">
				</br>
					<spring:message code="administrator.actor.state" />: <jstl:out value="${row.coordinates.state }"></jstl:out>
				
			</jstl:if>
			
			<jstl:if test="${not empty row.coordinates.province }">
				</br>
					<spring:message code="administrator.actor.provice" />: <jstl:out value="${row.coordinates.province }"></jstl:out>
				
			</jstl:if>
			
			</br>
				<spring:message code="administrator.actor.city" />: <jstl:out value="${row.coordinates.city }"></jstl:out> 
			
	</display:column>  
	
	<spring:message code="administrator.actor.profile" var="profileHeader" />
	<display:column title="${profileHeader}" sortable="false">
		<acme:link image="eye" href="profile/displayProfile.do?actorId=${row.id}"/>
	</display:column> 
	
	<display:column>
		<jstl:choose>
			<jstl:when test="${row.userAccount.enabled }">
				<form:form action="administrator/ban.do?actorId=${row.id}" modelAttribute="actor">
					<acme:submit image="ban" type="danger" name="ban" code="administrator.actor.ban" />
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
</div>


<spring:message code="administrator.actor.trainers" var="trainersHeader" />
<h3><jstl:out value="${trainersHeader}"/></h3>
<div class="table-responsive">
<display:table name="trainers" id="row" requestURI="${requestURI }" pagesize="4" class="displaytag">
	
	<acme:column code="administrator.actor.username" property="userAccount.username" sortable="true"/>
	<acme:column code="administrator.actor.name" property="name"/>	
	<acme:column code="administrator.actor.email" property="email"/>
	<acme:column code="administrator.actor.phone" property="phone"/>
		
	<spring:message code="administrator.actor.coordinates" var="coordinatesHeader" />
	<display:column title="${coordinatesHeader}" sortable="false">
		
				<spring:message code="administrator.actor.country" />: <jstl:out value="${row.coordinates.country }"></jstl:out>
			
			
			<jstl:if test="${not empty row.coordinates.state }">
				</br>
					<spring:message code="administrator.actor.state" />: <jstl:out value="${row.coordinates.state }"></jstl:out>
				
			</jstl:if>
			
			<jstl:if test="${not empty row.coordinates.province }">
				</br>
					<spring:message code="administrator.actor.provice" />: <jstl:out value="${row.coordinates.province }"></jstl:out>
				
			</jstl:if>
			
			</br>
				<spring:message code="administrator.actor.city" />: <jstl:out value="${row.coordinates.city }"></jstl:out> 
			
	</display:column>  
	
	<acme:column code="administrator.actor.rating" property="avgRating"/>
	
	<spring:message code="administrator.actor.profile" var="profileHeader" />
	<display:column title="${profileHeader}" sortable="false">
		<acme:link image="eye" href="profile/displayProfile.do?actorId=${row.id}"/>
	</display:column> 
	
	<display:column>
		<jstl:choose>
			<jstl:when test="${row.userAccount.enabled }">
				<form:form action="administrator/ban.do?actorId=${row.id}" modelAttribute="actor">
					<acme:submit image="ban" type="danger" name="ban" code="administrator.actor.ban" />
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
</div>


<spring:message code="administrator.actor.animalShelters" var="animalSheltersHeader" />
<h3><jstl:out value="${animalSheltersHeader}"/></h3>
<div class="table-responsive">
<display:table name="animalShelters" id="row" requestURI="${requestURI }" pagesize="4" class="displaytag">
	
	<acme:column code="administrator.actor.username" property="userAccount.username" sortable="true"/>
	<acme:column code="administrator.actor.name" property="name"/>	
	<acme:column code="administrator.actor.email" property="email"/>
	<acme:column code="administrator.actor.phone" property="phone"/>
		
	<spring:message code="administrator.actor.coordinates" var="coordinatesHeader" />
	<display:column title="${coordinatesHeader}" sortable="false">
		
				<spring:message code="administrator.actor.country" />: <jstl:out value="${row.coordinates.country }"></jstl:out>
			
			
			<jstl:if test="${not empty row.coordinates.state }">
				</br>
					<spring:message code="administrator.actor.state" />: <jstl:out value="${row.coordinates.state }"></jstl:out>
				
			</jstl:if>
			
			<jstl:if test="${not empty row.coordinates.province }">
				</br>
					<spring:message code="administrator.actor.provice" />: <jstl:out value="${row.coordinates.province }"></jstl:out>
				
			</jstl:if>
			
			</br>
				<spring:message code="administrator.actor.city" />: <jstl:out value="${row.coordinates.city }"></jstl:out> 
			
	</display:column>  
	
	<spring:message code="administrator.actor.profile" var="profileHeader" />
	<display:column title="${profileHeader}" sortable="false">
		<acme:link image="eye" href="profile/displayProfile.do?actorId=${row.id}"/>
	</display:column> 
	
	<display:column>
		<jstl:choose>
			<jstl:when test="${row.userAccount.enabled }">
				<form:form action="administrator/ban.do?actorId=${row.id}" modelAttribute="actor">
					<acme:submit image="ban" type="danger" name="ban" code="administrator.actor.ban" />
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
</div>


<spring:message code="administrator.actor.advertisings" var="advertisingsHeader" />
<h3><jstl:out value="${advertisingsHeader}"/></h3>
<div class="table-responsive">
<display:table name="advertisings" id="row" requestURI="${requestURI }" pagesize="4" class="displaytag">
	
	<acme:column code="administrator.actor.username" property="userAccount.username" sortable="true"/>
	<acme:column code="administrator.actor.name" property="name"/>	
	<acme:column code="administrator.actor.email" property="email"/>
	<acme:column code="administrator.actor.phone" property="phone"/>
		
	<spring:message code="administrator.actor.coordinates" var="coordinatesHeader" />
	<display:column title="${coordinatesHeader}" sortable="false">
		
				<spring:message code="administrator.actor.country" />: <jstl:out value="${row.coordinates.country }"></jstl:out>
			
			
			<jstl:if test="${not empty row.coordinates.state }">
				</br>
					<spring:message code="administrator.actor.state" />: <jstl:out value="${row.coordinates.state }"></jstl:out>
				
			</jstl:if>
			
			<jstl:if test="${not empty row.coordinates.province }">
				</br>
					<spring:message code="administrator.actor.provice" />: <jstl:out value="${row.coordinates.province }"></jstl:out>
				
			</jstl:if>
			
			</br>
				<spring:message code="administrator.actor.city" />: <jstl:out value="${row.coordinates.city }"></jstl:out> 
			
	</display:column>   
	
	<spring:message code="administrator.actor.profile" var="profileHeader" />
	<display:column title="${profileHeader}" sortable="false">
		<acme:link image="eye" href="profile/displayProfile.do?actorId=${row.id}"/>
	</display:column> 
	
	<display:column>
		<jstl:choose>
			<jstl:when test="${row.userAccount.enabled }">
				<form:form action="administrator/ban.do?actorId=${row.id}" modelAttribute="actor">
					<acme:submit image="ban" type="danger" name="ban" code="administrator.actor.ban" />
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
</div>