<%--
 * select.tag
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

<%@ attribute name="path" required="true" %>
<%@ attribute name="code" required="true" %>
<%@ attribute name="items" required="true" type="Object[]" %>
<%@ attribute name="lang" required="true" %>

<%@ attribute name="id" required="false" %>
<%@ attribute name="onchange" required="false" %>
<%@ attribute name="mandatory" required="false" %>

<jstl:if test="${mandatory == null}">
	<jstl:set var="mandatory" value="false" />
</jstl:if>
<jstl:if test="${mandatory == true}">
	<jstl:set var="required" value="required" />
</jstl:if>

<spring:message code="tag.noselectentry" var="tagSelectNoEntry"/>
<spring:message code="tag.noOneSelected" var="tagNoOneSelected"/>

<%-- Definition --%>
<jstl:set var="language" value="spanishName"/>
<jstl:if test="${lang == 'en'}">
	<jstl:set var="language" value="name"/>
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
	<form:select class="form-control" id="${id}" path="${path}"  required="${required }" title="${tagSelectNoEntry }">
		<form:option value="${null }" label="${tagNoOneSelected }" selected="selected" disabled="true"/>
		<form:options items="${items }" itemLabel="${language}"/>
	</form:select>
	<form:errors path="${path}" cssClass="alert alert-danger form-control-sm" />
</div>

