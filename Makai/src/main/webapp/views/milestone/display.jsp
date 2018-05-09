<%--
 * action-1.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="card-deck">
	<div class="card shadow">
	 	<br>
	  	<div class="card-body">
		  	 <p>
				<b><spring:message code="milestone.title" />:</b>
				<jstl:out value="${milestoneForm.title}" />
			</p>
			
			<p>
				<b><spring:message code="milestone.description" />:</b>
				<jstl:out value="${milestoneForm.description}" />
			</p>
			
			<p>
				<b><spring:message code="milestone.comment" />:</b>
				<jstl:out value="${milestoneForm.comment}" />
			</p>
			
			<p>
				<b><spring:message code="milestone.problem" />:</b>
				<jstl:out value="${milestoneForm.problem}" />
			</p>
			
			<p>
				<b><spring:message code="milestone.importance" />:</b>
				<jstl:forEach var = "i" begin = "1" end = "${milestoneForm.importance}" >
					<img src="images/star-32.png">
				</jstl:forEach>
			</p>
			
			<p>
				<b><spring:message code="milestone.targetDate" />:</b>
<%-- 				<jstl:out value="${milestoneForm.targetDate}" /> --%>
				<fmt:formatDate value="${milestoneForm.targetDate}" pattern="dd/MM/yyyy" />
			</p>
			
			<p>
				<b><spring:message code="milestone.realMoment" />:</b>
<%-- 				<jstl:out value="${milestoneForm.realMoment}" /> --%>
				<fmt:formatDate value="${milestoneForm.realMoment}" pattern="dd/MM/yyyy" />
			</p>
	  	</div>
	</div>
	
</div>
	
<br/>
<acme:link href="milestone/list.do?offerId=${offerId}" code="notification.goBack" image="arrow_left"/>
	

