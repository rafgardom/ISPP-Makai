<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:set var="i" value="0"/>
<div class="table-responsive">
<display:table name="vehicles" id="row" pagesize="5" requestURI="${requestURI}" class="displaytag">
	
	<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
	<jstl:set var="lang" value="<%=LocaleContextHolder.getLocale()%>"/>
	<jstl:set var="language" value="brand.spanishName"/>
	<jstl:if test="${lang == 'en'}">
		<jstl:set var="language" value="brand.name"/>
	</jstl:if>
	<acme:column code="vehicle.brand" property="${language}" />
	<acme:column code="vehicle.seats" property="seats" />
	
	<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
	<jstl:set var="lang" value="<%=LocaleContextHolder.getLocale()%>"/>
	<jstl:set var="language" value="carType.spanishName"/>
	<jstl:if test="${lang == 'en'}">
		<jstl:set var="language" value="carType.name"/>
	</jstl:if>
	<acme:column code="vehicle.carType" property="${language}" />
	<acme:column code="vehicle.accommodation" property="accommodation" />
	<acme:column code="vehicle.year" property="year" />
	<acme:column code="vehicle.description" property="description" />
	<acme:column code="vehicle.color" property="color" />
	<acme:column code="vehicle.license" property="license" />
	

	<display:column>
<%-- 		<jstl:if test="${row.picture != null}"> --%>
			<img src="${row.stringImage}" class="rounded-circle" alt="<spring:message code='vehicle.no.picture' />" width="100px" height="100px">
<%-- 		</jstl:if> --%>
		<%-- <jstl:if test="${row.picture == null}">
			<spring:message code='vehicle.no.picture' />
		</jstl:if> --%>
	</display:column>
	
	
	<display:column>
		<div class="btn-group" data-toggle="buttons">
			<jstl:if test="${row.hasNonStartedTrip == false }">
				<acme:link image="edit" href="vehicle/edit.do?vehicleId=${row.id}" type="warning"/>
			</jstl:if>
			<jstl:if test="${canDelete[i]}">
				<acme:delete href="vehicle/delete.do?vehicleId=${row.id}" id="${row.id}"/>
			</jstl:if>
		</div>
	</display:column>
	
	<jstl:set var="i" value="${i+1}"/>
</display:table>
</div>
<div class="center-div">
	<acme:link href="vehicle/register.do" code="vehicle.create" type="success mx-3" image="car"/>
	<acme:link href="travel/menu.do" code="notification.goBack" image="arrow_left"/>
</div>



