<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('CUSTOMER')">
	<div class="center-div mb-3">
		<acme:link href="request/customer/menu.do" code="notification.goBack" image="arrow_left"/>
		<acme:link href="request/customer/create.do" code="master.page.request.create" image="add1" type="success mx-3"/>
	</div>
</security:authorize>

<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<jstl:set var="lang" value="<%=LocaleContextHolder.getLocale()%>"/>

<jsp:useBean id="util" class="utilities.Utilities" scope="page" />
<jstl:set var="count" value="0"/>

<jstl:if test="si esta vacio"></jstl:if>
<jstl:forEach items = "${requests}"  var = "row">
	<br>
	<div class="row">
	<div class="offset-md-1 col-md-10 col-12">
	<div class="row">
		<div class="col-4">
		<div class="center-div">
				<img src="${util.showImage(row.getCustomer().getPicture(),'user')} " class="rounded-circle img-fluid" width="200px" height="200px">
		</div>
		</div>
		<div class="col-4">
		<br>
		<div class="center-div">
				<img src="${util.showImage(row.getAnimal().getPicture(),'interrogation')} " class="rounded-circle img-fluid" width="128px" height="128px">
		</div>
		</div>
		<div class="col-4">
				<div class="center-div">
				<jstl:if test="${trainers.get(count) != null}">
					<img src="${util.showImage(trainers.get(count).getPicture(),'user')} " class="rounded-circle img-fluid" width="200px" height="200px">
				</jstl:if>
				<jstl:if test="${trainers.get(count) == null}">
					<img src="${util.showImage(trainers.get(count).getPicture(),'interrogation')} " class="rounded-circle img-fluid" width="200px" height="200px">
				</jstl:if>
				</div>
		</div>
		</div>
	</div>	
		
   	<div class="offset-md-1 col-md-10 col-12 text-center" style="margin-top: -40px;">
	   	<div class="card shadow">
	   		<div class="row mt-5">
		   		<div class="col-4">
		   			<h6><spring:message code="request.customer"/></h6>
		   			<a href="profile/displayProfile.do?actorId=${row.customer.id}"><jstl:out value="${row.customer.name}"/></a>
		   		</div>
		   		<div class="col-4">
		   			<h6><spring:message code="request.animal"/></h6>
		   			<jstl:if test="${row.animal != null and row.animal.isHidden==false}">
						<a href="animal/display.do?animalId=${row.animal.id}"><jstl:out value="${row.animal.name}"/></a>
					</jstl:if>
					<jstl:if test="${row.animal != null and row.animal.isHidden==true}">
						<a><jstl:out value="${row.animal.name}"/></a>
					</jstl:if>
					<jstl:if test="${row.animal == null}">
						<jstl:out value="${none}" />
					</jstl:if>
		   		</div>
		   		<div class="col-4">
		   			<h6><spring:message code="request.trainer"/></h6>
		   			<a href="profile/displayProfile.do?actorId=${trainers.get(count).getId}"><jstl:out value="${trainers.get(count).getName()}"/></a>
		   		</div>
	   		</div>
	   		<p class="card-body">
		   		<jstl:if test="${lang == 'en'}">
					${row.category.name }
				</jstl:if>
				<jstl:if test="${lang == 'es'}">
					${row.category.spanishName }
				</jstl:if>
				<br>
		   		<jstl:out value="${row.tags}"/>	<br>
				<jstl:out value="${row.description}"></jstl:out>
			</p>
			<div class="card-body">
			   		<div class="btn-group">
				
					<acme:link image="eye" href="request/actor/display.do?requestId=${row.id}"/>
					
					<security:authorize access="hasRole('CUSTOMER')">
<%-- 						<acme:link href="request/customer/edit.do?requestId=${row.id}" code="offer.edit"/> --%>
						<jstl:if test="${requestsWithOffer.contains(row)}">
							<acme:link href="offer/customer/list.do?requestId=${row.id}" code="request.list.offer" type="dark" image="deal"/>
						</jstl:if>
						
						<jstl:set var="showDelete" value="${true}"/>
						<jstl:forEach var="offer" items="${offersAcepted}">
							<jstl:if test="${offer.request.id == row.id}">
								<jstl:set var="showDelete" value="${false}"/>
							</jstl:if> 
						</jstl:forEach> 
						<jstl:if test="${showDelete == true}">
							<acme:link image="edit" href="request/customer/edit.do?requestId=${row.id}" type="warning"/>
							<acme:delete href="request/customer/delete.do?requestId=${row.id}" id="${row.id}"/>
						</jstl:if>
					</security:authorize>
					
					<security:authorize access="hasRole('TRAINER')">
						<acme:link href="offer/trainer/create.do?requestId=${row.id}" code="offer.create" image="deal" type="dark"/>
					</security:authorize>
				</div>
	   		</div>
	   	</div>
   	</div>
   	</div>
	
	<jstl:set var="count" value="${count+1}"/>
</jstl:forEach>

<%-- <div class="table-responsive">
<display:table name="requests" id="row" pagesize="5" requestURI="${requestURI}" class="displaytag">


	<acme:column code="request.description" property="description"/>
	
	<security:authorize access="hasRole('TRAINER')">
		<spring:message code="request.customer" var="customerHeader" />
		<display:column title="${customerHeader}" class="text-center" sortable="true">
			<a href="profile/displayProfile.do?actorId=${row.customer.id}"><jstl:out value="${row.customer.name}"/></a>
		</display:column>
	</security:authorize>

	<acme:column code="request.tags" property="tags"/>
	
	
	<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
	<jstl:set var="lang" value="<%=LocaleContextHolder.getLocale()%>"/>
	<jstl:set var="language" value="row.category.spanishName"/>
	<jstl:if test="${lang == 'en'}">
		<jstl:set var="language" value="row.category.name"/>
	</jstl:if>
	<acme:column code="request.category" property="${language}" sortable="true" />
	
	
	<spring:message code="request.animal" var="animalHeader" />
	<spring:message code="request.none" var="none"/>
	<display:column title="${animalHeader}" >
		<jstl:if test="${row.animal != null and row.animal.isHidden==false}">
			<a href="animal/display.do?animalId=${row.animal.id}"><jstl:out value="${row.animal.name}"/></a>
		</jstl:if>
		<jstl:if test="${row.animal != null and row.animal.isHidden==true}">
			<a><jstl:out value="${row.animal.name}"/></a>
		</jstl:if>
		<jstl:if test="${row.animal == null}">
			<jstl:out value="${none}" />
		</jstl:if>
	</display:column>
	
	<display:column>
		<div class="btn-group">
		
			<acme:link image="eye" href="request/actor/display.do?requestId=${row.id}"/>
			
			<security:authorize access="hasRole('CUSTOMER')">
				<acme:link href="request/customer/edit.do?requestId=${row.id}" code="request.edit"/>
				<jstl:if test="${requestsWithOffer.contains(row)}">
					<acme:link href="offer/customer/list.do?requestId=${row.id}" code="request.list.offer" type="dark" image="deal"/>
				</jstl:if>
				
				<jstl:set var="showDelete" value="${true}"/>
				<jstl:forEach var="offer" items="${offersAcepted}">
					<jstl:if test="${offer.request.id == row.id}">
						<jstl:set var="showDelete" value="${false}"/>
					</jstl:if> 
				</jstl:forEach> 
				<jstl:if test="${showDelete == true}">
					<acme:link href="request/customer/edit.do?requestId=${row.id}" code="request.edit"/>
					<acme:delete href="request/customer/delete.do?requestId=${row.id}" id="${row.id}"/>
				</jstl:if>
			</security:authorize>
			
			<security:authorize access="hasRole('TRAINER')">
				<acme:link href="offer/trainer/create.do?requestId=${row.id}" code="offer.create" image="deal" type="dark"/>
			</security:authorize>
		</div>
	</display:column>

</display:table>
</div> --%>

<acme:pagination/>



