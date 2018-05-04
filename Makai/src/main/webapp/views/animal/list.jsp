<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div class="table-responsive">
<display:table name="animals" id="row" pagesize="5" requestURI="${requestURI}" class="displaytag">
	
	<display:column>
		<img src="${row.stringImage}" class="rounded-circle" alt="<spring:message code='profile.no.picture' />" width="125px" height="125px">
	</display:column>
	
	<acme:column code="animal.name" property="name" />
	
	<spring:message code="animal.chipNumber" var="titleHeader" />
	<display:column class="text-center" title="${titleHeader}" sortable="true"  maxLength="30" >
		<jstl:if test="${row.chipNumber != null}">
		<jstl:out value="${row.chipNumber}"/>
		</jstl:if>
		<jstl:if test="${row.chipNumber == null }">
		<small><i>NO CHIP</i></small>
		</jstl:if>
	</display:column>
	<acme:column code="animal.birthday" property="birthday" sortable="true" format="{0,date,dd/MM/yyyy}"/>
<%--	
	<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
	<jstl:set var="lang" value="<%=LocaleContextHolder.getLocale()%>"/>
	<jstl:set var="language" value="sex.spanishName"/>
	<jstl:if test="${lang == 'en'}">
		<jstl:set var="language" value="sex.name"/>
	</jstl:if> 
	<acme:column code="animal.sex" property="${language}" sortable="true"/>
	--%>
	 
	<spring:message code="animal.sex" var="sexHeader"/>
	<display:column title="${sexHeader}" class="text-center">
		<acme:imageSex sexName="${row.sex.name}"/>
	</display:column>
	
	<display:column>
		<div class="btn-group" data-toggle="buttons">	
			<acme:link image="eye" href="animal/display.do?animalId=${row.id}"/>
			<acme:link image="edit" href="animal/edit.do?animalId=${row.id}" type="warning"/>
			<acme:delete href="animal/delete.do?animalId=${row.id}" id="${row.id}"/>
		</div>
	</display:column>
	
		
</display:table>
</div>

<div class="center-div">
	<acme:link href="animal/menu.do" code="notification.goBack" image="arrow_left"/>
</div>