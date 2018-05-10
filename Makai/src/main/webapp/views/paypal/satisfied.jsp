<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<div class="card text-center">
<div class="card-title mt-5">
	<h2><spring:message code="pay.success"/></h2>
	
	<img src="images/shield.png" style="margin-right: -2rem;"/>
	<img src="images/paypal.png" />
	<jstl:if test="${offerId}">
		<meta http-equiv="refresh" content="3;url=/makai/offer/customer/list.do?requestId=${requestId }" />
		<spring:message code="payment.redirecting"/> <img src="images/Loading_icon.gif" />
	</jstl:if>
	<jstl:if test="${banner}">
		<meta http-equiv="refresh" content="3;url=/makai/banner/actor/list.do" />
		<spring:message code="payment.redirecting"/> <img src="images/Loading_icon.gif" />
	</jstl:if>
</div>
</div>


