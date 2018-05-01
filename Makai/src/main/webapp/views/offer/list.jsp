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
	<acme:column code="offer.price" property="price" sortable="true" format="{0,number, ,000.00}&euro;"/>
	<acme:column code="offer.animal" property="animal.name" />
	<acme:column code="offer.request.owner" property="request.customer.userAccount.username" />
	
	<display:column>
		<security:authorize access="hasRole('CUSTOMER')">
			<a href="profile/displayProfile.do?actorId=${row.trainer.id}">${row.trainer.name}</a>
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
			<security:authorize access="hasRole('CUSTOMER')">
				<jstl:if test="${row.isAccepted==false}">
					<a class="btn btn-warning btn-lg" href="misc/conditions.do" target="_blank" data-load-url="misc/conditions.do" data-toggle="modal" data-target="#myModal">
						<img class="icon-button" src="images/paypal4.png"/>
						<spring:message code="offer.accept" />
					</a>
				</jstl:if>
				<jstl:if test="${row.isAccepted==true and !tieneRating[count]}">
					<acme:link image="star-white-32" href="rating/customer/createRequest.do?requestId=${row.request.id}" type="info" code="offer.rating"/>
				</jstl:if>
			</security:authorize>
		</div>
	<security:authorize access="hasRole('CUSTOMER')">
		<display:column>
			<jstl:if test="${row.isAccepted==false}">
				<div class="btn-group">	
					<acme:link image="paypal4" href="offer/customer/pilotPlan.do?offerId=${row.id}" type="warning" code="offer.pilotPlanAccept"/>
				</div>
			</jstl:if>
		</display:column>
	</security:authorize>
		<jstl:if test="${row.isAccepted==true}">
			<h6><spring:message code="offer.isAccepted" /></h6>
		</jstl:if>
	</display:column>
	
<jstl:set var = "count" value ="${count + 1}"></jstl:set>
</display:table>
</div>

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

