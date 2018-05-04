<%--
 * textbox.tag
 *
 * Copyright (C) 2017 Universidad de Sevilla
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
 
<%@ attribute name="path" required="true"%>
<%@ attribute name="code" required="true"%>

<%@ attribute name="type" required="false"%>
<%@ attribute name="placeholder" required="false"%>
<%@ attribute name="step" required="false"%>
<%@ attribute name="min" required="false"%>
<%@ attribute name="max" required="false"%>
<%@ attribute name="mandatory" required="false" %>
<%@ attribute name="image" required="false" %>
<%@ attribute name="style" required="false" %>
<%@ attribute name="id" required="false" %>
<%@ attribute name="pattern" required="false" %>
<%@ attribute name="value" required="false" %>
<%@ attribute name="maxlength" required="false" %>



<spring:message code="tag.input.phone" var="tagPhone"/>
<spring:message code="tag.input.email" var="tagEmail"/>
<spring:message code="tag.input.username" var="tagUsername"/>
<spring:message code="tag.datepicker" var="tagDatepicker"/>
<spring:message code="tag.seats" var="tagSeat"/>

<%-- Definition --%>


<jstl:if test="${type == null}">
	<jstl:set var="type" value="text" />
</jstl:if>
<jstl:if test="${maxlength == null}">
	<jstl:set var="maxlength" value="100" />
</jstl:if>
<jstl:if test="${mandatory == null}">
	<jstl:set var="mandatory" value="false" />
</jstl:if>
<jstl:if test="${mandatory == true}">
	<jstl:set var="required" value="required" />
</jstl:if>

<div class="form-group">
	<form:label path="${path}">
		<spring:message code="${code}" />
		<jstl:if test="${mandatory == true}">
			<img src="images/asterisk.png"	width="16"/> 
		</jstl:if>
		<jstl:if test="${mandatory == false}">
			<small class="text-muted">(<spring:message code="optional"/>)</small>
		</jstl:if>
	</form:label>
	
	<jstl:if test="${image != null}">
	<div class="input-group"><!-- NO BORRAR -->
		<div class="input-group-prepend">
			<span class="input-group-text btn-light"><img src="images/${image}.png"/></span>
		</div>
	</jstl:if>
	
	<jstl:if test="${path == 'phone' }">
		<form:input class="form-control ${style}" path="${path}" type="${type }" placeholder="${placeholder }" step="${step }" min="${min }" max="${max }" id="${id }" pattern="((\+|00)\d{2,4}(\s)?)?\d{9,13}" value="${value}" maxlength="${maxlength}" required="${required}" title="${tagPhone }"/>
	</jstl:if>
	<jstl:if test="${path == 'email' }">
		<form:input class="form-control ${style}" path="${path}" type="${type }" placeholder="${placeholder }" step="${step }" min="${min }" max="${max }" id="${id }" pattern="${pattern }" value="${value}" maxlength="${maxlength}" required="${required}" title="${tagEmail }"/>
	</jstl:if>
	<jstl:if test="${path == 'username' }">
		<form:input class="form-control ${style}" path="${path}" type="${type }" placeholder="${placeholder }" step="${step }" min="${min }" max="${max }" id="${id }" pattern="${pattern }" value="${value}" maxlength="${maxlength}" required="${required}" title="${tagUsername }"/>
	</jstl:if>
	
	<jstl:if test="${id == 'datepicker' }">
		<form:input class="form-control ${style}" path="${path}" type="${type }" placeholder="${placeholder }" step="${step }" min="${min }" max="${max }" id="${id }" pattern="${pattern }" value="${value}" maxlength="${maxlength}" required="${required}" title="${tagDatepicker }"/>
	</jstl:if>
	
	<jstl:if test="${path == 'seats' }">
		<form:input class="form-control ${style}" path="${path}" type="${type }" placeholder="${placeholder }" step="${step }" min="${min }" max="${max }" id="${id }" pattern="${pattern }" value="${value}" maxlength="${maxlength}" required="${required}" title="${tagSeat }"/>
	</jstl:if>
	
	<jstl:if test="${path != 'phone' && path != 'email' && path != 'username' && id != 'datepicker' && path != 'seats'}">
		<form:input class="form-control ${style}" path="${path}" type="${type }" placeholder="${placeholder }" step="${step }" min="${min }" max="${max }" id="${id }" pattern="${pattern }" value="${value}" maxlength="${maxlength}" required="${required}"/>
	</jstl:if>
	
<jstl:if test="${image == null}">
	<form:errors cssClass="alert alert-danger form-control-sm d-block" path="${path}" />
</jstl:if>
	</div>
	
<jstl:if test="${image != null}">
	<form:errors cssClass="alert alert-danger form-control-sm d-block" path="${path}" />
	</div><!-- NO BORRAR -->
</jstl:if>
