<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div class="table-responsive">
<display:table name="milestones" id="row" pagesize="10" requestURI="${RequestURI}" class="displaytag">
	

	<acme:column code="milestone.title" property="title" sortable="true"/>
	<acme:column code="milestone.targetDate" property="targetDate" format="{0,date,dd/MM/yyyy}" sortable="true"/>
	
	<spring:message code="milestone.importance" var="milestoneHeader" />
	<display:column class="text-center" title="${milestoneHeader}" sortable="true">
		<jstl:forEach var = "i" begin = "1" end = "${row.importance}" >
			<img src="images/star-32.png">
		</jstl:forEach>
	</display:column>
	<spring:message code="milestone.isComplete" var="titleHeader" />
	<display:column class="text-center" title="${titleHeader}" sortable="true" maxLength="30">
		<jstl:if test="${row.realMoment != null}">
			<img src="images/true.png"/>
		</jstl:if>
		<jstl:if test="${row.realMoment == null}">
		<img src="images/false.png"/>
		</jstl:if>
	</display:column>
	

	<display:column>
	<div class="btn-group">
		<acme:link href="milestone/display.do?milestoneId=${row.id}" image="eye"/>
		<acme:link image="edit" href="milestone/edit.do?milestoneId=${row.id}" type="warning"/>
		<jstl:if test="${row.offer.isAccepted and row.realMoment == null}">
			<acme:confirm href="milestone/complete.do?milestoneId=${row.id}" id="${row.id}" image="trophy"/>
		</jstl:if>
		<jstl:if test="${row.offer.isAccepted == false }">
				<acme:delete href="milestone/delete.do?milestoneId=${row.id}" id="${row.id}"/>
		</jstl:if>
	</div>
	</display:column>
	
</display:table>
</div>



<div class="center-div">
	<security:authorize access="hasAnyRole('CUSTOMER')">
			<acme:link href="offer/trainer/display.do?offerId=${offerId}" code="notification.goBack" image="arrow_left"/>
		</security:authorize>
	<security:authorize access="hasAnyRole('TRAINER')">
		<acme:link href="offer/trainer/display.do?offerId=${offerId}" code="notification.goBack" image="arrow_left"/>
	</security:authorize>
	<security:authorize access="hasAnyRole('TRAINER')">
		<acme:link href="milestone/create.do?offerId=${offerId}" code="milestone.create" type="success mx-2" image="target"/>
	</security:authorize>
</div>
