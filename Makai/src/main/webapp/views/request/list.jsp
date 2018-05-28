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

<jstl:if test="${!empty requests}">
<jstl:forEach items = "${requests}"  var = "row">
	<br>
	<div class="row">
	<div class="offset-md-1 col-md-10 col-12">
	<div class="row">
		<div class="col-4">
		<div class="center-div">
				<img src="${util.showImage(row.getCustomer().getPicture(),'user')} " class="rounded-circle border border-primary" width="200px" height="200px">
		</div>
		</div>
		<div class="col-4">
		<br>
		<div class="center-div">
			<jstl:if test="${row.getAnimal().getPicture() != null}">
				<img src="${util.showImage(row.getAnimal().getPicture(),'interrogation')} " class="rounded-circle border border-primary" width="128px" height="128px">
			</jstl:if>
			<jstl:if test="${row.getAnimal().getPicture() == null}">
				<jstl:if test="${offers.get(count).getTrainer() == null}">
					<img src="${util.showImage(row.getAnimal().getPicture(),'interrogation')} " class="rounded-circle border border-primary" width="128px" height="128px">
				</jstl:if>
				<jstl:if test="${offers.get(count).getTrainer() != null}">
					<img src="${util.showImage(offers.get(count).getAnimal().getPicture(),'interrogation')} " class="rounded-circle border border-primary" width="128px" height="128px">
				</jstl:if>
			</jstl:if>
		</div>
		</div>
		<div class="col-4">
				<div class="center-div">
				<jstl:if test="${offers.get(count) != null}">
					<img src="${util.showImage(offers.get(count).getTrainer().getPicture(),'user')} " class="rounded-circle border border-primary" width="200px" height="200px">
				</jstl:if>
				<jstl:if test="${offers.get(count) == null}">
					<img src="${util.showImage(offers.get(count).getTrainer().getPicture(),'interrogation')} " class="rounded-circle border border-primary" width="200px" height="200px">
				</jstl:if>
				</div>
		</div>
		</div>
	</div>	
		
   	<div class="offset-md-1 col-md-10 col-12 text-center" style="margin-top: -40px;">
	   	<div class="card shadow">
	   		<div class="row mt-5">
		   		<div class="col-4">
		   			<h6><b><spring:message code="request.customer"/></b></h6>
		   			<a href="profile/displayProfile.do?actorId=${row.customer.id}"><jstl:out value="${row.customer.name}"/></a>
		   		</div>
		   		<div class="col-4">
		   			<h6><b><spring:message code="request.animal"/></b></h6>
		   			<jstl:if test="${row.animal != null and row.animal.isHidden==false}">
						<a href="animal/display.do?animalId=${row.animal.id}"><jstl:out value="${row.animal.name}"/></a>
					</jstl:if>
					<jstl:if test="${row.animal != null and row.animal.isHidden==true}">
						<a><jstl:out value="${row.animal.name}"/></a>
					</jstl:if>
					<jstl:if test="${row.animal == null}">
						<jstl:if test="${offers.get(count).getTrainer() == null}">
							<h6 class="text-muted font-italic"><spring:message code="request.pending"/></h6>
						</jstl:if>
						<jstl:if test="${offers.get(count).getTrainer() != null}">
							<a>${offers.get(count).getAnimal().getName()}</a>
						</jstl:if>
					</jstl:if>
		   		</div>
		   		<div class="col-4">
		   			<h6><b><spring:message code="request.trainer"/></b></h6>
		   			<jstl:if test="${offers.get(count) == null}">
						<h6 class="text-muted font-italic"><spring:message code="request.pending"/></h6>
					</jstl:if>
					<jstl:if test="${offers.get(count) != null}">
		   				<a href="profile/displayProfile.do?actorId=${offers.get(count).getTrainer().getId()}"><jstl:out value="${offers.get(count).getTrainer().getName()}"/></a>
		   			</jstl:if>
		   		</div>
	   		</div>
	   		<h5 class="card-body" style="padding-bottom: 0.5rem">
		   		<jstl:if test="${lang == 'en'}">
					${row.category.name }
				</jstl:if>
				<jstl:if test="${lang == 'es'}">
					${row.category.spanishName }
				</jstl:if>
				<br>
		   		<jstl:out value="${row.tags}"/>
		   	</h5>
		   	
	   		<div class="card-body collapse" id="collapse${count}">
				<jstl:out value="${row.description}"></jstl:out>
			</div>

			<div class="card-body" style="padding-top: 0">
			   		<div class="btn-group">
				
					<acme:link image="eye" href="request/actor/display.do?requestId=${row.id}"/>
					<a class="btn btn-info btn-lg" data-toggle="collapse" href="#collapse${count}" role="button" aria-expanded="false" aria-controls="collapse${count}">
					    <img class="icon-button" src="images/add1.png"/>
					    Info
					</a>
					<security:authorize access="hasRole('CUSTOMER')">
<%-- 						<acme:link href="request/customer/edit.do?requestId=${row.id}" code="offer.edit"/> --%>
						<jstl:if test="${requestsWithOffer.contains(row)}">
							<acme:link href="offer/customer/list.do?requestId=${row.id}" code="request.list.offer" type="dark" image="deal"/>
						</jstl:if>
						
						<jstl:set var="showDelete" value="${true}"/>
						<jstl:if test="${offers.get(count) != null}">
								<jstl:set var="showDelete" value="${false}"/>
						</jstl:if>
						<jstl:if test="${showDelete == true}">
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

<acme:pagination/>
</jstl:if>
<jstl:if test="${empty requests}">
	<div class="card shadow text-center my-5">
		<div class="card-body">
			<img src="images/noFound.png"/>
			<h3><spring:message code="no.elements"/></h3>
		</div>
	</div> 
</jstl:if>


