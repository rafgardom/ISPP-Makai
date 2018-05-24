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
	<acme:column code="offer.animal" property="animal.name" />
	<acme:column code="offer.request.category" property="request.category" />
	<acme:column code="offer.request.tags" property="request.tags" />
	
	<spring:message code="offer.request.rating" var="customerHeader"/>
	<display:column title="${customerHeader}">
		<jstl:if test="${puntuacionRating[count] == 0}">-</jstl:if>
		<jstl:if test="${puntuacionRating[count] > 0}">${puntuacionRating[count]}</jstl:if>
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

