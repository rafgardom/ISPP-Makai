<%--
 * action-1.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="row my-1">
	<div class="offset-md-4 col-md-4">
		<div class="card shadow">
		  	<div class="card-body"> 	
			<p><b><spring:message code="notification.moment" />:</b> 
			<fmt:formatDate value="${notification.moment}" pattern="dd/MM/yyyy HH:mm:ss" />
	
			<p><b><spring:message code="notification.reason" />:</b>
			
				<jstl:if test="${!notification.reason.substring(0,1).equals('#')}">
					<jstl:out value="${notification.reason}" />
				</jstl:if>
				<jstl:if test="${notification.reason.substring(0,2).equals('##')}">
					<jstl:out value="${notification.reason.substring(1)}" />
				</jstl:if>
				<jstl:if test="${notification.reason.substring(0,4).equals('#TD0')}">
					<spring:message code="notification.info.reason.travelDelete" />
				</jstl:if>
				<jstl:if test="${notification.reason.substring(0,4).equals('#TR0')}">
					<spring:message code="notification.info.reason.travelRegister" />
				</jstl:if>
				<jstl:if test="${notification.reason.substring(0,4).equals('#RC0')}">
					<spring:message code="notification.info.reason.requestCreate" />
				</jstl:if>
				<jstl:if test="${notification.reason.substring(0,4).equals('#RN0')}">
					<spring:message code="notification.info.reason.ratingNegative" />
				</jstl:if>
				<jstl:if test="${notification.reason.substring(0,4).equals('#OC0')}">
					<spring:message code="notification.info.reason.offerCreate" />
				</jstl:if>
				<jstl:if test="${notification.reason.substring(0,4).equals('#OA0')}">
					<spring:message code="notification.info.reason.offerAccept" />
				</jstl:if>
				<jstl:if test="${notification.reason.substring(0,4).equals('#OP0')}">
					<spring:message code="notification.info.reason.offerPet" />
				</jstl:if>
				<jstl:if test="${notification.reason.substring(0,4).equals('#BC0')}">
					<spring:message code="notification.info.reason.bannerCreate" />
				</jstl:if>
				<jstl:if test="${notification.reason.substring(0,4).equals('#BA0')}">
					<spring:message code="notification.info.reason.bannerAccept" />
				</jstl:if>
			</p>
			</div>
		</div>
	</div>
	<div class="offset-md-3 col-md-6 my-3 text-center">
		<div class="card shadow">
		  	<div class="card-body"> 	
			<p><b><spring:message code="notification.description" />:</b>
				<jstl:if test="${!notification.reason.substring(0,1).equals('#') || notification.reason.substring(0,2).equals('##')}">
					<jstl:out value="${notification.description}" />
				</jstl:if>
				<jstl:if test="${notification.reason.substring(0,4).equals('#TD0')}">
					<spring:message code="notification.info.description.travelDelete" />
				</jstl:if>
				<jstl:if test="${notification.reason.substring(0,4).equals('#TR0')}">
					<spring:message code="notification.info.description.travelRegister" />
				</jstl:if>
				<jstl:if test="${notification.reason.substring(0,4).equals('#RC0')}">
					<spring:message code="notification.info.description.requestCreate1" />
					<jstl:out value="${notification.description}" />
					<spring:message code="notification.info.description.requestCreate2" />
				</jstl:if>
				<jstl:if test="${notification.reason.substring(0,4).equals('#RN0')}">
					<jstl:set var="text" value="${notification.description.split('#RN0')}" />
					<jstl:if test="${text[0].equals('trainer')}">
						<spring:message code="notification.info.description.ratingNegative1.1" />
					</jstl:if>
					<jstl:if test="${text[0].equals('travel')}">
						<spring:message code="notification.info.description.ratingNegative1.2" />
					</jstl:if>
					<jstl:out value="${text[1]}" />
					<spring:message code="notification.info.description.ratingNegative2" />
					<jstl:out value="${text[2]}" />
					<spring:message code="notification.info.description.ratingNegative3" />
				</jstl:if>
				<jstl:if test="${notification.reason.substring(0,4).equals('#OC0')}">
					<spring:message code="notification.info.description.offerCreate" />
					<jstl:out value="${notification.description}" />
				</jstl:if>
				<jstl:if test="${notification.reason.substring(0,4).equals('#OA0')}">
					<spring:message code="notification.info.description.offerAccept" />
				</jstl:if>
				<jstl:if test="${notification.reason.substring(0,4).equals('#OP0')}">
					<spring:message code="notification.info.description.offerPet1" />
					<jstl:out value="${notification.description}" />
					<spring:message code="notification.info.description.offerPet2" />
				</jstl:if>
				<jstl:if test="${notification.reason.substring(0,4).equals('#BC0')}">
					<jstl:set var="text" value="${notification.description.split('#BC0')}" />
					<spring:message code="notification.info.description.bannerCreate1" />
					<jstl:out value="${text[0]}" />
					<spring:message code="notification.info.description.bannerCreate2" />
					<jstl:out value="${text[1]}" />
					<spring:message code="notification.info.description.bannerCreate3" />
					<jstl:out value="${text[2]}" />
				</jstl:if>
				<jstl:if test="${notification.reason.substring(0,4).equals('#BA0')}">
					<spring:message code="notification.info.description.bannerAccept" />
				</jstl:if>
			</p>
			</div>
		</div>
	</div>
	<div class="col-12 d-flex justify-content-center">
		<acme:link href="notification/actor/list.do" code="notification.goBack" image="arrow_left"/>
	</div>
</div>

