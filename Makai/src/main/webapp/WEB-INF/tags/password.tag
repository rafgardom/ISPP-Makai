<%--
 * password.tag
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" body-content="empty" %>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%> 
 
<%@ attribute name="path" required="true" rtexprvalue="true" %>
<%@ attribute name="code" required="true" rtexprvalue="true" %>
<%@ attribute name="image" required="true" %>

<%@ attribute name="mandatory" required="false" %>
<%@ attribute name="id" required="false" %>
<%@ attribute name="placeholder" required="false" %>
<%@ attribute name="infoButton" required="false" %>
<%@ attribute name="info" required="false" %>

<jstl:if test="${mandatory == null}">
	<jstl:set var="mandatory" value="false" />
</jstl:if>
<jstl:if test="${mandatory == true}">
	<jstl:set var="required" value="required" />
</jstl:if>


<%-- Definition --%>

<spring:bind path="${path}">
	<div class="form-group ${status.error? 'has-error':''}">
		<form:label path="${path}">
				<spring:message code="${code}" />
				<jstl:if test="${mandatory == true}">
					<img src="images/asterisk.png"	width="16"/>
				</jstl:if>
		</form:label>
		
		<div class="input-group">
			<div class="input-group-prepend">
				<span class="input-group-text btn-light"><img src="images/${image}.png"/></span>
			</div>
			<form:password path="${path}" class="form-control" placeholder="${placeholder}" id="${id }" required="required"/>
		</div>
		<jstl:if test="${id != null}">
			<h6 id="${id}message" class="text-center mt-1"></h6>
		</jstl:if>
		
	<form:errors path="${path}" cssClass="alert alert-danger form-control-sm d-block" />
	<jstl:if test="${info != null}">
		<h6 class="form-text">*<spring:message code="customer.password.error1"/><br>
		*<spring:message code="customer.password.error2"/></h6>
	</jstl:if>
	
	</div>

</spring:bind>
