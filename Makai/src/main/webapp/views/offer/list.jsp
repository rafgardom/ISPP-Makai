<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<div class="table-responsive">
<jstl:set var = "count" value ="${0}"></jstl:set>

<display:table name="offers" id="row" pagesize="5" requestURI="${requestURI}" class="displaytag">
	
	<acme:column code="offer.startMoment" property="startMoment" sortable="true" format="{0,date,dd/MM/yyyy}"/>
	<acme:column code="offer.coordinates.city" property="destination.city" sortable="true"/>
	<acme:column code="offer.price" property="price" sortable="true" format="{0,number, 0.00}&euro;"/>
	
	<spring:message code="offer.animal" var="animalHeader" />
	<display:column title="${animalHeader}" >
		<jstl:if test="${row.animal != null and row.animal.isHidden==false}">
			<a href="animal/display.do?animalId=${row.animal.id}"><jstl:out value="${row.animal.name}"/></a>
		</jstl:if>
		<jstl:if test="${row.animal != null and row.animal.isHidden==true}">
			<a><jstl:out value="${row.animal.name}"/></a>
		</jstl:if>
	</display:column>
	
	<acme:column code="offer.request.owner" property="request.customer.name" />
	
	<%-- <display:column>
		<security:authorize access="hasRole('CUSTOMER')">
			<a href="profile/displayProfile.do?actorId=${row.trainer.id}">${row.trainer.name}</a>
		</security:authorize>
	</display:column> --%>
	
	<display:column>
		<security:authorize access="hasRole('CUSTOMER')">
			<a href="offer/customer/listTrainer.do?trainerId=${row.trainer.id}">${row.trainer.name}</a>
		</security:authorize>
	</display:column>
	
	
	<display:column>
		
		<div class="btn-group">
			<acme:link image="eye" href="offer/trainer/display.do?offerId=${row.id}"/>
			<security:authorize access="hasRole('TRAINER')">
				<jstl:if test="${row.isAccepted==false}">
					<acme:link image="edit" href="offer/trainer/edit.do?offerId=${row.id}" type="warning"/>
					<acme:delete href="offer/trainer/delete.do?offerId=${row.id}" id="${row.id}"/>
				</jstl:if>
			</security:authorize>
			<jstl:if test="${row.startMoment lt today}">
			<security:authorize access="hasRole('CUSTOMER')">
				<jstl:if test="${row.isAccepted==false}">
					<a class="btn btn-warning btn-lg disabled" href="misc/conditions.do" target="_blank" data-load-url="misc/conditions.do" data-toggle="modal" data-target="#myModal">
						sdfsdfsdfsdf
						<spring:message code="offer.accept" />
					</a>
				</jstl:if>
				<jstl:if test="${row.isAccepted==true and !tieneRating[count]}">
					<acme:link image="star-white-32" href="rating/customer/createRequest.do?requestId=${row.request.id}" type="info" code="offer.rating" disabled="true"/>
				</jstl:if>
			</security:authorize>
			</jstl:if>
			<jstl:if test="${row.startMoment gt today}">
				<security:authorize access="hasRole('CUSTOMER')">
					<jstl:if test="${row.isAccepted==false}">
						<jstl:if test="${row.animal.customer == null}">
							<a class="btn btn-warning btn-lg" href="misc/conditions.do?animal=false" target="_blank" data-load-url="misc/conditions.do?animal=false" data-toggle="modal" data-target="#myModal">
								<img class="icon-button" src="images/paypal4.png"/>
								<spring:message code="offer.accept" />
								<br/>
							<spring:message code="offer.transcationPrice" />
							<jstl:out value="${row.price*(trainingPrice*0.01)}"></jstl:out>
							<spring:message code="offer.euros" />
							
							</a>
						</jstl:if>
						<jstl:if test="${row.animal.customer != null}">
							<a class="btn btn-warning btn-lg" href="misc/conditions.do?animal=true" target="_blank" data-load-url="misc/conditions.do?animal=true" data-toggle="modal" data-target="#myModal">
								<img class="icon-button" src="images/paypal4.png"/>
								<spring:message code="offer.accept" />
								<br/>
							<spring:message code="offer.transcationPrice" />
							<jstl:out value="${row.price*(trainingPrice*0.01)}"></jstl:out>
							<spring:message code="offer.euros" />
							</a>
						</jstl:if>
					</jstl:if>
					<jstl:if test="${row.isAccepted==true and !tieneRating[count]}">
						<acme:link image="star-white-32" href="rating/customer/createRequest.do?requestId=${row.request.id}" type="info" code="offer.rating"/>
					</jstl:if>
				</security:authorize>
			</jstl:if>
		</div>
		
<%-- 	<security:authorize access="hasRole('CUSTOMER')"> --%>
<%-- 		<display:column> --%>
<%-- 			<jstl:if test="${row.isAccepted==false}"> --%>
<!-- 				<div class="btn-group">	 -->
<%-- 					<acme:link image="paypal4" href="offer/customer/pilotPlan.do?offerId=${row.id}" type="warning" code="offer.pilotPlanAccept"/> --%>
<!-- 				</div> -->
<%-- 			</jstl:if> --%>
<%-- 		</display:column> --%>
<%-- 	</security:authorize> --%>
		<jstl:if test="${row.isAccepted==true}">
			<h6><spring:message code="offer.isAccepted" /></h6>
		</jstl:if>
	</display:column>
	
<jstl:set var = "count" value ="${count + 1}"></jstl:set>
</display:table>
</div>

<security:authorize access="hasAnyRole('CUSTOMER')">
	<div class="center-div">
		<acme:link href="request/customer/myList.do" code="notification.goBack" image="arrow_left"/>
	</div>
</security:authorize>

<security:authorize access="hasAnyRole('TRAINER')">
	<div class="center-div">
		<acme:link href="" code="notification.goBack" image="arrow_left"/>
	</div>
</security:authorize>

<!-- The Modal -->
<div class="modal fade" id="myModal">
	<div class="modal-dialog modal-dialog-centered modal-lg">
		<div class="modal-content">
		
			<!-- Modal Header -->
			<div class="modal-header">
				<h3 class="modal-title"></h3>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>
		
			<!-- Modal body -->
			<div class="modal-body">
			</div>
			
			<!-- Modal footer -->
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" onclick="window.location='offer/customer/accept.do?offerId=${row.id}'"><spring:message code="offer.accept"/></button>
				<button type="button" class="btn btn-danger" data-dismiss="modal"><spring:message code="offer.cancel"/></button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$('#myModal').on('show.bs.modal', function (e) {
	    var loadurl = $(e.relatedTarget).data('load-url');
	    $modalTitle = $(this).find('.modal-title');
	    $modalTitle.load(loadurl+" .jumbotron h1", function(){
	            $modalTitle.find('h1').replaceWith(function(){
	                return $(this).text();
	            });
	        });
	    $(this).find('.modal-body').load(loadurl+" #conditions");
	});
</script>

